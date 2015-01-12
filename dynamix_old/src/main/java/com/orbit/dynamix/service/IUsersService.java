package com.orbit.dynamix.service;

import java.util.List;

import com.orbit.dynamix.entity.User;
import com.orbit.dynamix.vo.ProfileVO;
import com.orbit.dynamix.vo.UserVO;

/**
 * 
 * @author m.arbi
 *
 */
public interface IUsersService {

	public User getUserById(Integer id);
	
	public UserVO getUserVOById(Integer id) throws Exception;
	
	public List<UserVO> getListUsers();
	
	public List<ProfileVO> getAllProfilesVO();

	public void saveOrUpdateUser(UserVO userVO) throws Exception;

	public void deleteListUsers(List<Integer> idsUsers);
	
}
