package com.orbit.dynamix.action;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.orbit.dynamix.security.Utilisateur;
import com.orbit.dynamix.utils.Constants;
import com.orbit.dynamix.vo.CreativeVO;

/**
 * 
 */
@Namespace(value = "/")
public class AbstarctAction extends ActionSupport implements SessionAware, Constants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CreativeVO creativeVO = new CreativeVO();
	
	@Autowired
	protected Utilisateur user;// = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	protected Integer idCrea;
	
	protected String msgInfo;
	
	protected String msgError;
	
	protected String checkOptionParams;
	
	protected int validForm = 0;
	
	protected int validFormUpload = 0;
	
	protected boolean valide = false;
	
	protected Map<String, Object> session;
	
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	public CreativeVO getCreativeVO() {
		return creativeVO;
	}

	public void setCreativeVO(CreativeVO creativeVO) {
		this.creativeVO = creativeVO;
	}

	public Integer getIdCrea() {
		return idCrea;
	}

	public void setIdCrea(Integer idCrea) {
		this.idCrea = idCrea;
	}

	
	public String getMsgInfo() {
		return StringEscapeUtils.escapeJava(msgInfo);
	}

	
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	
	public String getMsgError() {
		return StringEscapeUtils.escapeJava(msgError);
	}

	
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	public int getValidForm() {
		return validForm;
	}

	public void setValidForm(int validForm) {
		this.validForm = validForm;
	}

	public boolean isValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

	public String getCheckOptionParams() {
		return checkOptionParams;
	}

	public void setCheckOptionParams(String checkOptionParams) {
		this.checkOptionParams = checkOptionParams;
	}

	public int getValidFormUpload() {
		return validFormUpload;
	}

	public void setValidFormUpload(int validFormUpload) {
		this.validFormUpload = validFormUpload;
	}

}
