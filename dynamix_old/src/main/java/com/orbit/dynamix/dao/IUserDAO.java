package com.orbit.dynamix.dao;

import java.util.List;

import com.orbit.dynamix.entity.User;
import com.orbit.dynamix.vo.UserVO;

/**
 * 
 * @author m.arbi
 *
 */
public interface IUserDAO {

	public User getUserById(Integer id);
	
	public UserVO getUserVOById(Integer id);
	
	public void saveOrUpdate(User user);
	
	public List<UserVO> getAllUsersVO();

	public void deleteListUsers(List<Integer> idsUsers);
}
