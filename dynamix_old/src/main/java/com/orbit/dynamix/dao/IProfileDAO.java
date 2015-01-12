package com.orbit.dynamix.dao;

import java.util.List;

import com.orbit.dynamix.entity.Profile;
import com.orbit.dynamix.vo.ProfileVO;

/**
 * 
 * @author m.arbi
 *
 */
public interface IProfileDAO {

	public List<ProfileVO> getAllProfilesVO();
	
	public Profile getProfileById(Integer idProfile);
	
}
