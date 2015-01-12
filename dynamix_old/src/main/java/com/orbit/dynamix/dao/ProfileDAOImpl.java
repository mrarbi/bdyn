package com.orbit.dynamix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orbit.dynamix.entity.Profile;
import com.orbit.dynamix.vo.ProfileVO;

/**
 * 
 * @author m.arbi
 * 
 */
@Repository
public class ProfileDAOImpl implements IProfileDAO {

	@Autowired
	SessionFactory sesFactory;
	
	@Override
	public List<ProfileVO> getAllProfilesVO() {
		return sesFactory
				.getCurrentSession()
				.createQuery("select p.profileId as profileId, p.profileName as profileName "
								+ "from Profile p")
				.setResultTransformer(Transformers.aliasToBean(ProfileVO.class)).list();
	}

	@Override
	public Profile getProfileById(Integer idProfile) {
		return (Profile) sesFactory.getCurrentSession().get(Profile.class, idProfile);
	}
}
