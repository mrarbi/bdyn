package com.orbit.dynamix.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orbit.dynamix.vo.ProfileVO;

/**
 * 
 * @author m.arbi
 *
 */
@Service
@Transactional
public class ProfileService implements IProfileService {

	@Override
	public List<ProfileVO> getListProfilesVO() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
