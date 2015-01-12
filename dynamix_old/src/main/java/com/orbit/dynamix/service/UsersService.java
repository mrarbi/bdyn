package com.orbit.dynamix.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.orbit.dynamix.dao.IProfileDAO;
import com.orbit.dynamix.dao.IUserDAO;
import com.orbit.dynamix.entity.Profile;
import com.orbit.dynamix.entity.User;
import com.orbit.dynamix.utils.Utils;
import com.orbit.dynamix.vo.ProfileVO;
import com.orbit.dynamix.vo.UserVO;

/**
 * 
 * @author m.arbi
 *
 */
@Service
@Transactional
public class UsersService implements IUsersService {
	
	private static Logger log = LoggerFactory.getLogger(CreativeService.class);
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IProfileDAO profileDAO;
	
	public User getUserById(Integer id) {
		return (User) userDAO.getUserById(id);
	}
	
	public UserVO getUserVOById(Integer id) throws Exception {
		UserVO userVO = new UserVO();
		try {
			userVO = userDAO.getUserVOById(id);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return userVO;
	}

	@Override
	public List<UserVO> getListUsers() {
		return userDAO.getAllUsersVO();
	}

	@Override
	public List<ProfileVO> getAllProfilesVO() {
		return profileDAO.getAllProfilesVO();
	}

	@Override
	public void saveOrUpdateUser(UserVO userVO) throws Exception {
		User user = null;
		try {
			if (null != userVO.getUserId()) {
				user = userDAO.getUserById(userVO.getUserId());
			} else {
				user = new User();
				user.setDateCreated(new Date());
			}
			user.setActive(true);
			user.setContactName(userVO.getContactName());
			user.setEmailAddress(userVO.getEmailAddress());
			user.setLanguage(userVO.getLanguage());
			String password = userVO.getPassword();
			if (StringUtils.isNotEmpty(userVO.getNewPassword())) {
				password = Utils.encodePassword(userVO.getNewPassword());
			}
			user.setPassword(password);
			user.setUsername(userVO.getUsername());
			if (null != userVO.getProfileId()) {
				Profile profile = profileDAO.getProfileById(userVO.getProfileId());
				user.setProfile(profile);
			}
			userDAO.saveOrUpdate(user);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}
	
	
	@Override
	public void deleteListUsers(List<Integer> idsUsers) {
		userDAO.deleteListUsers(idsUsers);
	}

}
