package com.orbit.dynamix.action;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.orbit.dynamix.entity.Creative;
import com.orbit.dynamix.entity.User;
import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.service.ITemplateService;
import com.orbit.dynamix.service.IUsersService;
import com.orbit.dynamix.utils.Utils;
import com.orbit.dynamix.vo.CreativeVO;
import com.orbit.dynamix.vo.ProfileVO;
import com.orbit.dynamix.vo.UserVO;

/**
 * 
 */
public class GestionUserAction extends AbstarctAdminAction implements SessionAware { 
	
	private static final long serialVersionUID = 4418697921380052754L;
	
	@Autowired
	IUsersService usersService;
	
	@Autowired
	ICreativeService creativeService;
	
	@Autowired
	ITemplateService templateService;
	
	private List<UserVO> users;
	
	private UserVO userVO = new UserVO();
	
	private Integer userIdForUpdate;
	
	protected Map<Integer, String> checkboxUsers = new LinkedHashMap<Integer, String>();
	
	@SkipValidation
	@Action(value = "/gestUser", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "gestUser.tiles")
	})
	public String gestUser() {
		try {
			users = usersService.getListUsers();
			msgError = (String) session.get("msgError");
			session.remove("msgError");
			if (StringUtils.isEmpty(msgError)) {
				msgInfo = (String) session.get("msgInfo");
			}
			session.remove("msgInfo");
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "/newUser", results = { 
		@Result(name = SUCCESS, location = "/pages/admin/saveUser.jsp")
	})
	public String newUser() {
		try {
			userVO = new UserVO();
			titreAction = "Ajout nouvel utilisateur";
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "/findUser", results = { 
		@Result(name = SUCCESS, location = "/pages/admin/saveUser.jsp")
	})
	public String findUser() {
		try {
			if (null != userIdForUpdate && 0 != userIdForUpdate.intValue()) {
				userVO  = usersService.getUserVOById(userIdForUpdate);
				titreAction = "Modification utilisateur \"" + userVO.getContactName() + "\"";
			}
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return SUCCESS;
	}
	
	@Action(value = "/saveUser", results = { 
		@Result(name = SUCCESS, type = "redirectAction", params = {"actionName", "gestUser"}),
		@Result(name = INPUT, type = "tiles", location = "gestUser.tiles")
	})
	public String saveUser() {
		try {
			if (null != userVO) {
				Integer idUserUpdated = userVO.getUserId();
				//String imageUrl = (String) session.get("imageUrl");
				if (null != user) {
					usersService.saveOrUpdateUser(userVO);
				}
				if (null != idUserUpdated && 0 != idUserUpdated.intValue()) {
					msgInfo = "L'utilisateur \"" + userVO.getContactName() + "\" a été modifié avec succès.";
				} else {
					msgInfo = "L'utilisateur \"" + userVO.getContactName() + "\" a été ajouté avec succès.";
				}
				session.put("msgInfo", msgInfo);
			}
			
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
			session.put("msgError", msgError);
		}
		return SUCCESS;
	}
	
	
	@SkipValidation
	@Action(value = "/deleteUsers", results = {
		@Result(name = SUCCESS, type = "redirectAction", params = {"actionName", "gestUser"})
	})
	public String deleteUsers() {
		try {
			
			
			if (null != userVO) {
				
				if (null != checkboxUsers && !checkboxUsers.isEmpty()) {
					
					List<Integer> listIds = Utils.genererListCheckedIds(checkboxUsers);
					if(null != listIds && !listIds.isEmpty()){
						creativeService.deleteCreaByIdUser(listIds);
						templateService.deleteTemplatesByIdUser(listIds);
						usersService.deleteListUsers(listIds);
					}
				}
				
				userVO = usersService.getUserVOById(userVO.getUserId());

				if (null == userVO) {
					msgInfo = "L'utilisateur a été supprimé avec succès.";
				} else {
					msgInfo = "L'utilisateur a été supprimé avec succès.";
				}
				
				session.put("msgInfo", msgInfo);
			}
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
			session.put("msgError", msgError);
		}
		return SUCCESS;
	}

	@Override
	public void validate() {
		if (StringUtils.isEmpty(userVO.getContactName())) {
			addActionError("Le champ contact est obligatoir");
		}
		if (StringUtils.isEmpty(userVO.getEmailAddress())) {
			addActionError("Le champ e-mail est obligatoir");
		}
		if (StringUtils.isEmpty(userVO.getUsername())) {
			addActionError("Le champ login est obligatoir");
		}
		if (null == userVO.getUserId() || userVO.getUserId().intValue() == 0) {
			if (StringUtils.isEmpty(userVO.getNewPassword())) {
				addActionError("Le champ mot de passe est obligatoir");
			}
		}
		if (StringUtils.isNotEmpty(userVO.getNewPassword())) {
			if(!userVO.getNewPassword().equals(userVO.getPasswordConfirm())) {
				addActionError("Le champ confirmation mot de passe doit correspondre au mot de passe saisi");
			}
		}
		if (null == userVO.getProfileId() || userVO.getProfileId().intValue() == 0) {
			addActionError("Le champ profile est obligatoir");
		}
		
		validForm = 1;
		gestUser();
	}
	
	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public Integer getUserIdForUpdate() {
		return userIdForUpdate;
	}

	public void setUserIdForUpdate(Integer userIdForUpdate) {
		this.userIdForUpdate = userIdForUpdate;
	}
	
	public List<ProfileVO> getListProfiles() {
		return usersService.getAllProfilesVO();
	}

	public Map<Integer, String> getCheckboxUsers() {
		return checkboxUsers;
	}

	public void setCheckboxUsers(Map<Integer, String> checkboxUsers) {
		this.checkboxUsers = checkboxUsers;
	}

}
