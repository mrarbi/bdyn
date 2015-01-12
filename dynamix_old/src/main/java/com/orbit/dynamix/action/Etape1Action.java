package com.orbit.dynamix.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.service.IUsersService;
import com.orbit.dynamix.utils.XMLUtils;

/**
 * 
 */
public class Etape1Action extends AbstarctAction {

	private static final long serialVersionUID = 1L;

	private File fileUpload;

	private String fileUploadFileName;

	private String fileUploadContentType;
	
	private String urlOrFile;
	
	private String authUrl;
	
	@Autowired
	private ICreativeService creativeService;
	
	@Autowired
	private IUsersService usersService;
	
	@SkipValidation
	@Action(value = "/etape1", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "etape1.tiles"), 
			@Result(name = INPUT, type = "tiles", location = "etape1.tiles") })
	public String etape1() {
		try {
			refreshCrea();
			msgError = (String) session.get("msgError");
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
			return INPUT;
		} finally {
			msgInfo = (String) session.get("msgInfo");
			session.remove("msgError");
			session.remove("msgInfo");
		}
	}
	
	/**
	 * 
	 */
	private void refreshCrea() {
		try {
			Integer idCrea = (Integer) session.get("idCrea");
			session.remove("idCrea");
			session.remove("etape");
			if (null != user) {
				creativeVO = creativeService.getCreativeVO(idCrea);
				if (null != creativeVO) {
					idCrea = creativeVO.getCreativeId();
					session.put("etape", creativeVO.getEtape());
					session.put("idCrea", idCrea);
				}
			}
			fileUploadFileName = null != creativeVO ? creativeVO.getFichier() : "";
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
	}

	@Action(value = "/etape1v", results = { 
			@Result(name = SUCCESS, type = "redirectAction", location = "etape2.action"), 
			@Result(name = INPUT, type = "tiles", location = "etape1.tiles") })
	public String etape1v() throws Exception {
		int idCrea = 0;
		try {
			if (null != creativeVO) {
				creativeVO.setEtape(1);
				idCrea = creativeService.majCreativeEtape1(creativeVO, user, urlOrFile, authUrl, fileUpload, fileUploadFileName, fileUploadContentType);
				session.put("idCrea", idCrea);
				session.put("etape", creativeVO.getEtape());
				msgInfo = "L'étape " + creativeVO.getEtape() + " est terminée avec succès.";
				session.put("msgInfo", msgInfo);
			}
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return INPUT;
	}

	@Override
	public void validate() {
		//refreshCrea();
		if (StringUtils.isEmpty(fileUploadFileName) && StringUtils.isEmpty(creativeVO.getUrl())) {
			addActionError("Veuillez choisir un URL ou importer un fichier");
		}
		
		if (StringUtils.isNotEmpty(creativeVO.getUrl())) {
			Pattern pattern = Pattern.compile(URL_PATTERN);
			Matcher matcher = pattern.matcher(creativeVO.getUrl());
			if (!matcher.matches()) {
				addActionError("Veuillez entrer un URL valide");
//				addFieldError("creative.url","Veuillez entrer un URL valide");
			}
		}
		
		if (StringUtils.equals(fileUploadContentType, "text/xml")) {
			String xpath = XMLUtils.getXpath(fileUpload);
			if (StringUtils.isEmpty(xpath) || "//".equals(xpath)) {
				addActionError("Veuillez choisir un fichier xml valide");
			} else {
				creativeVO.setXpath(xpath);
			}
		}
		
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}
	
	public String getUrlOrFile() {
		return urlOrFile;
	}
	
	public void setUrlOrFile(String urlOrFile) {
		this.urlOrFile = urlOrFile;
	}
	
	public String getAuthUrl() {
		return authUrl;
	}
	
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(new Date());
	}
}
