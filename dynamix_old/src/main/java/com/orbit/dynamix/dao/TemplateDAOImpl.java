package com.orbit.dynamix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orbit.dynamix.entity.Template;
import com.orbit.dynamix.vo.TemplateVO;

/**
 * 
 * @author m.arbi
 * 
 */
@Repository
public class TemplateDAOImpl implements ITemplateDAO {

	@Autowired
	SessionFactory sesFactory;
	
	public int saveOrUpdate(Template template) {
		if (null != template) {
			sesFactory.getCurrentSession().saveOrUpdate(template);
			return null != template.getTempId() ? template.getTempId() : 0;
		}
		return 0;
	}
	
	
	public String getSourceHTML(Integer tempId) {
		return (String) sesFactory.getCurrentSession().createQuery("select t.text from Template t where t.tempId = :tempId").setParameter("tempId", tempId).uniqueResult();
	}
	
	public Template getTemplate(Integer tempId) {
		return (Template) sesFactory.getCurrentSession().get(Template.class, tempId);
	}
	
	public List<Template> getTemplates() {
		return sesFactory.getCurrentSession().createQuery("from Template where actif = 1 order by temp_id ").list();
	}
	
	public List<TemplateVO> getTemplatesVO() {
		return sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height "
				+ "from Template t where t.actif = 1 and t.valide = 1 order by temp_id ")
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class))
				.list();
	}
	
	public List<TemplateVO> geAlltTemplatesVO() {
		return sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height "
				+ "from Template t where t.valide = 1 order by temp_id ")
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class))
				.list();
	}
	
	public List<TemplateVO> getTemplatesVOByFormat(Integer width, Integer height) {
		return sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height from Template t where t.actif = 1 "
				+ "and t.width = :width and t.height = :height and t.valide = 1 ")
				.setParameter("width", width)
				.setParameter("height", height)
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class))
				.list();
	}
	
	public List<TemplateVO> getTemplatesVONoValide() {
		return sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height, t.valide as valide "
				+ "from Template t where t.valide = 0 order by temp_id ")
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class))
				.list();
	}
	
	public void deleteTemplate(Template template) {
		sesFactory.getCurrentSession().delete(template);
	}
	
//	public void deleteListTemplates(List<Integer> templatesIds){
//		if (null != templatesIds && !templatesIds.isEmpty()) {
//			for (Integer temp : templatesIds) {
//				if(null != temp){
//					Template template = getTemplate(temp);
//					sesFactory.getCurrentSession().delete(template);
//				}
//			}
//		}
//	}
	
	public List<TemplateVO> getTemplatesVOByFormat(String format) {
		return sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height "
				+ "from Template t where t.actif = 1 and t.format = :format and t.valide = 1 ")
				.setParameter("format", format)
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class))
				.list();
	}
	
	public List<TemplateVO> getTemplatesByUserVO(Integer userId){
		return sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height "
				+ "from Template t where t.actif = 1 and t.user.userId = :userId and t.valide = 1 ")
				.setParameter("userId", userId)
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class))
				.list();
	}
	
	public List<TemplateVO> getAllTemplatesByUserVO(Integer userId){
		return sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height "
				+ "from Template t where t.user.userId = :userId")
				.setParameter("userId", userId)
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class))
				.list();
	}
	
	public boolean deleteTemplatesByIdUser(List<Integer> listIds) {
		sesFactory.getCurrentSession().createQuery("delete from Template t where t.user.userId in (:usersId) ").setParameterList("usersId", listIds).executeUpdate();
		return true;
	}
	
	public TemplateVO getTemplateByName(String name){
		return (TemplateVO) sesFactory.getCurrentSession().createQuery("select t.tempId as tempId, t.text as text, t.actif as actif, t.name as name, t.type as type, t.user.userId as userId "
				+ ",t.creationDate as creationDate, t.urlImage as urlImage, t.urlTemplate as urlTemplate, t.format as format, t.width as width, t.height as height from Template t where t.name = :name")
				.setParameter("name", name)
				.setResultTransformer(Transformers.aliasToBean(TemplateVO.class)).uniqueResult();
	}
}
