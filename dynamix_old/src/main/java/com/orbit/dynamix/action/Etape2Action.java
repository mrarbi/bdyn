package com.orbit.dynamix.action;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.service.ITemplateService;
import com.orbit.dynamix.service.IUsersService;
import com.orbit.dynamix.utils.Utils;
import com.orbit.dynamix.vo.TemplateVO;

/**
 * 
 */
public class Etape2Action extends AbstarctAction {
	
	private static final long serialVersionUID = 1L;
	
	private File fileUpload;
	
	private String fileUploadContentType;
	
	private String fileUploadFileName;
	
	private int etape;
	
	private TemplateVO template;
	
	private List<TemplateVO> templates;
	
	private Integer tempSelect;
	
	private String formatSearch;
	
	@Autowired
	ICreativeService creativeService;
	
	@Autowired
	ITemplateService templateService;
	
	@Autowired
	IUsersService userservice;
	
//	@Autowired
//	ISaveCreaServiceAws saveCreaServiceAws;
	
//	@Autowired
//	IFtpService ftpService;
	
	private Map<Integer, String> mapListStateTemplate;
	
	private Map<String, String> mapListFormatTemplate;
	
	@Action(value = "/etape2", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "etape2.tiles")
	})
	public String etape2() {
		try {
			templates = templateService.getTemplatesVO();
//			if (null != templates) {
//				tempSelect = templates.get(0).getTempId();
//			}
			Integer idCrea = (Integer) session.get("idCrea");
			if (null != user) {
				creativeVO = creativeService.getCreativeVO(idCrea);
				if (null != creativeVO) {
					idCrea = creativeVO.getCreativeId();
					session.put("etape", creativeVO.getEtape());
					session.put("idCrea", idCrea);
				}
			}
			initEtatTemplate();
			initFormatTemplate();
			msgError = (String) session.get("msgError");
			if (StringUtils.isEmpty(msgError)) {
				msgInfo = (String) session.get("msgInfo");
			}
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		session.remove("msgError");
		session.remove("msgInfo");
		return SUCCESS;
	}
	
	private void initEtatTemplate() {
		mapListStateTemplate = new LinkedHashMap<Integer, String>();
		mapListStateTemplate.put(1, getText("Actif"));
		mapListStateTemplate.put(0, getText("Inactif"));
	}
	
	private void initFormatTemplate() {
		mapListFormatTemplate = new LinkedHashMap<String, String>();
		mapListFormatTemplate.put("250x250", getText("250x250"));
		mapListFormatTemplate.put("300x250", getText("300x250"));
		mapListFormatTemplate.put("300x300", getText("300x300"));
		mapListFormatTemplate.put("160x600", getText("600x150"));
		mapListFormatTemplate.put("728x90", getText("728x90"));
	}
	
	@Action(value = "/saveTemplate", results = {
			@Result(name = "success", type = "redirectAction", params = {"actionName", "etape2"}),
			@Result(name = "input", type = "tiles", location = "etape2.tiles") })
	public String saveTemplate() throws Exception {
		try {
			if (null != user && fileUpload != null) {
				templateService.majTemplateEtape2(template, user.getId(), fileUploadFileName, fileUpload);
			}
			initEtatTemplate();
			initFormatTemplate();
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return INPUT;
	}
	
	@Override
	public void validate() {
		if(null != template){
			
			session.put("imageUrl", template.getUrlImage());
		//	String tempRecup = (String) session.get("oldTemplate");
			
			if (("").equals(template.getName()) || template.getName() == null) {
				addActionError(getText("Nom de template est obligatoir."));
			}
			if (fileUpload == null) {
				if (("").equals(template.getUrlImage()) || template.getUrlImage() == null) {
					addActionError(getText("Vous devez choisir une image de votre template."));
				}
			}
			if (("").equals(template.getFormat()) || template.getFormat() == null) {
				addActionError(getText("Vous devez choisir une format."));
			}
			if (("").equals(template.getText()) || template.getText() == null) {
				addActionError(getText("Html de la banniere est obligatoir."));
			}else{
				String text =  validateHtml(template.getText());
				if (!valide) {
					addActionError(getText(text));
				}
			}
			validForm = 1;
		}
	}
	
	public String validateHtml(String text){
		String error = "";
		String arroInterogat = "@!";
		String interogatArro = "!@";
		int arroInt = Utils.stringOccur(text, arroInterogat);
		int intArro = Utils.stringOccur(text, interogatArro);

		if (arroInt != intArro) {
			error ="Vous devez valider vos parametres html.";
			valide = false;
		} else {
			valide = true;
		}
		
		return  error;
	}

	
	@Action(value = "/searchByformats", results = { @Result(name = SUCCESS, location = "/pages/etape2search.jsp") })
	public String searchByformats() throws Exception {
		try {
			if (null != formatSearch && !"".equals(formatSearch)) {
				templates = templateService.getTemplatesByFormat(formatSearch);
			} else if ("".equals(formatSearch)) {
				templates = templateService.getTemplatesVO();
			}
			Integer idCrea = (Integer) session.get("idCrea");
			creativeVO = creativeService.getCreativeVO(idCrea);
			if (null != creativeVO) {
				idCrea = creativeVO.getCreativeId();
				session.put("etape", creativeVO.getEtape());
				session.put("idCrea", idCrea);
			}
			initEtatTemplate();
			initFormatTemplate();
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return SUCCESS;
	}
	
	
	@Action(value = "/etape2v", results = { 
			@Result(name = SUCCESS, type = "redirectAction", location = "etape3.action"), 
			@Result(name = INPUT, type = "redirectAction", location = "etape2.action")
	})
	public String etape2v() throws Exception {
		try {
			Integer idCrea = (Integer) session.get("idCrea");
			if (null != idCrea) {
				creativeVO = creativeService.getCreativeVO(idCrea);
				if (null != creativeVO) {
					creativeVO.setEtape(2);
					creativeService.majCreativeEtape2(creativeVO, tempSelect);
					msgInfo = "L'étape " + creativeVO.getEtape() + " est terminée avec succès.";
					session.put("msgInfo", msgInfo);
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
			session.put("msgError", msgError);
			return INPUT;
		}
	}
	
	
	public Integer getTempSelect() {
		return tempSelect;
	}
	
	public void setTempSelect(Integer tempSelect) {
		this.tempSelect = tempSelect;
	}

	public List<TemplateVO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateVO> templates) {
		this.templates = templates;
	}

	public File getFileUpload() {
		return fileUpload;
	}
	
	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	public String getFileUploadContentType() {
		return fileUploadContentType;
	}
	
	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}
	
	public String getFileUploadFileName() {
		return fileUploadFileName;
	}
	
	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public Map<Integer, String> getMapListStateTemplate() {
		return mapListStateTemplate;
	}
	
	public void setMapListStateTemplate(Map<Integer, String> mapListStateTemplate) {
		this.mapListStateTemplate = mapListStateTemplate;
	}

	public int getEtape() {
		return etape;
	}

	public void setEtape(int etape) {
		this.etape = etape;
	}

	
	public TemplateVO getTemplate() {
		return template;
	}

	public void setTemplate(TemplateVO template) {
		this.template = template;
	}
	
	public Map<String, String> getMapListFormatTemplate() {
		return mapListFormatTemplate;
	}

	public void setMapListFormatTemplate(Map<String, String> mapListFormatTemplate) {
		this.mapListFormatTemplate = mapListFormatTemplate;
	}

	public String getFormatSearch() {
		return formatSearch;
	}

	public void setFormatSearch(String formatSearch) {
		this.formatSearch = formatSearch;
	}
}
