package com.orbit.dynamix.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orbit.dynamix.entity.Creative;
import com.orbit.dynamix.vo.CreativeVO;

/**
 * 
 * @author m.arbi
 * 
 */
@Repository
public class CreativeDAOImpl extends AbstractGenericDAOImpl<Creative, Integer> implements ICreativeDAO {

	@Autowired
	SessionFactory sesFactory;
	
//	public void saveOrUpdate(Creative creative) {
//		if (null != creative) {
//			sesFactory.getCurrentSession().saveOrUpdate(creative);
//		}
//	}
	
//	public void delete(Creative creative) {
//		if (null != creative) {
//			sesFactory.getCurrentSession().delete(creative);
//		}
//	}
	
	public void deleteById(Integer idCrea) {
		if (null != idCrea) {
			sesFactory.getCurrentSession().createQuery("delete from Creative c where c.creativeId = :idCrea")
				.setParameter("idCrea", idCrea)
				.executeUpdate();
		}
	}
	
	public Creative getCreative(Integer id) {
		return (Creative) sesFactory.getCurrentSession().get(Creative.class, id);
	}
	
	public CreativeVO getCreativeVO(Integer idCrea) {
		return  (CreativeVO) sesFactory.getCurrentSession().createQuery("select c.creativeId as creativeId, c.url as url, c.fichier as fichier, c.typeFichier as typeFichier, c.separateur as separateur "
				+ ", c.user.userId as userId, c.matching as matching, t.tempId as tempId, t.text as text, c.xpath as xpath, c.loginUrl as loginUrl, c.passwdUrl as passwdUrl, c.etape as etape, c.actif as actif,"
				+ " c.typeGeo as typeGeo, c.geolocalisation as geolocalisation from Creative c left join c.template t where c.creativeId = :idCrea")
				.setParameter("idCrea", idCrea).setResultTransformer(Transformers.aliasToBean(CreativeVO.class)).uniqueResult();
	}
	
	public Integer getTempIdByCrea(Integer idCrea) {
		return (Integer) sesFactory.getCurrentSession().createQuery("select c.template.tempId from Creative c where c.creativeId = :idCrea").setParameter("idCrea", idCrea).uniqueResult();
	}
	
	
	public List<CreativeVO> getCreativesVOByUser(int idUser){
		Query query =  sesFactory.getCurrentSession().createQuery("select c.creativeId as creativeId, c.url as url, c.fichier as fichier, c.typeFichier as typeFichier, "
				+ "c.separateur as separateur, c.user.userId as userId, c.matching as matching, t.tempId as tempId, t.text as text, "
				+ "c.xpath as xpath, c.loginUrl as loginUrl, c.passwdUrl as passwdUrl, c.etape as etape, c.creationDate as creationDate , c.actif as actif, "
				+ "c.typeGeo as typeGeo, c.geolocalisation as geolocalisation from Creative c left join c.template t  where c.user.userId = :uid order by c.creativeId desc").setParameter("uid", idUser);
		return query.setResultTransformer(Transformers.aliasToBean(CreativeVO.class)).list();
	}
	
	public List<CreativeVO> getAllCreativesVO(){
		Query query =  sesFactory.getCurrentSession().createQuery("select c.creativeId as creativeId, c.url as url, c.fichier as fichier, c.typeFichier as typeFichier, "
				+ "c.separateur as separateur, c.user.userId as userId, c.matching as matching, t.tempId as tempId, t.text as text, c.actif as actif, "
				+ "c.xpath as xpath, c.loginUrl as loginUrl, c.passwdUrl as passwdUrl, c.etape as etape, c.creationDate as creationDate, "
				+ "c.typeGeo as typeGeo, c.geolocalisation as geolocalisation  from Creative c left join c.template t ");
		return query.setResultTransformer(Transformers.aliasToBean(CreativeVO.class)).list();
	}

	public boolean deleteCreaByIdUser(List<Integer> idsUsers) {
		if (null != idsUsers && !idsUsers.isEmpty()) {
			for (Integer userId : idsUsers) {
			sesFactory.getCurrentSession().createQuery("delete from Creative c where c.user.userId =:userId")
				.setParameter("userId", userId)
				.executeUpdate();
			}
		}
		return true;
	}

	public List<Creative> getCreativesByTemplatesId(List<Integer> idsTemplates){
		Query query =  sesFactory.getCurrentSession().createQuery("select c from Creative c where c.template.tempId in (:idsTemplates)");
		return query.setParameterList("idsTemplates", idsTemplates).list();
	}
	
}
