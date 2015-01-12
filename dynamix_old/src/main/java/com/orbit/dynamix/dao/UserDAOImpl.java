package com.orbit.dynamix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orbit.dynamix.entity.Template;
import com.orbit.dynamix.entity.User;
import com.orbit.dynamix.vo.UserVO;

/**
 * 
 * @author m.arbi
 * 
 */
@Repository
public class UserDAOImpl implements IUserDAO {

	@Autowired
	SessionFactory sesFactory;
	
	public User getUserById(Integer id) {
		return (User) sesFactory.getCurrentSession().get(User.class, id);
	}
	
	public UserVO getUserVOById(Integer id) {
		return (UserVO) sesFactory.getCurrentSession().createQuery("select u.userId as userId, u.contactName as contactName, u.emailAddress as emailAddress"
				+ ", u.username as username, u.password as password, u.language as language, u.active as active, u.dateCreated as dateCreated"
				+ ", u.dateLastLogin as dateLastLogin, p.profileId as profileId, p.profileName as profileName "
				+ " from User u left join u.profile p where u.userId = :userId ")
				.setParameter("userId", id)
				.setResultTransformer(Transformers.aliasToBean(UserVO.class))
				.uniqueResult();
	}
	
	@Override
	public List<UserVO> getAllUsersVO() {
		return sesFactory
				.getCurrentSession()
				.createQuery("select u.userId as userId, u.contactName as contactName, u.emailAddress as emailAddress"
								+ ", u.username as username, u.password as password, u.language as language, u.active as active, u.dateCreated as dateCreated"
								+ ", u.dateLastLogin as dateLastLogin, p.profileId as profileId, p.profileName as profileName from User u left join u.profile p")
				.setResultTransformer(Transformers.aliasToBean(UserVO.class)).list();
	}

	@Override
	public void saveOrUpdate(User user) {
		sesFactory.getCurrentSession().saveOrUpdate(user);
	}
	
	public void deleteListUsers(List<Integer> idsUsers){
		if (null != idsUsers && !idsUsers.isEmpty()) {
			for (Integer user : idsUsers) {
				if(null != user){
					User userDel = getUserById(user);
					sesFactory.getCurrentSession().delete(userDel);
				}
			}
		}	
	}
}
