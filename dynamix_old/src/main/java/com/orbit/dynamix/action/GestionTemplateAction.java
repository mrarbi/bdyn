package com.orbit.dynamix.action;


import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.service.ISaveCreaServiceAws;
import com.orbit.dynamix.service.ITemplateService;
import com.orbit.dynamix.utils.Constants;
import com.orbit.dynamix.utils.UnZip;
import com.orbit.dynamix.utils.Utils;
import com.orbit.dynamix.vo.CreativeVO;
import com.orbit.dynamix.vo.TemplateVO;

/**
 * 
 */
public class GestionTemplateAction extends AbstarctAdminAction implements SessionAware { 
	
	private static final long serialVersionUID = 4418697921380052754L;
	
	@Autowired
	ICreativeService creativeService;
	
	@Autowired
	ITemplateService templateService;
	
	@Autowired
	ISaveCreaServiceAws saveCreaServiceAws;
	
	private List<TemplateVO> templates;
	
	private Map<Integer, String> mapListStateTemplate;
	
	private Map<String, String> mapListFormatTemplate;
	
	private TemplateVO template;
	
	private File fileUpload;
	
	private File tempZipFile;
	
	private String tempZipFileContentType;
	
	private String tempZipFileFileName;
	
	private String fileUploadContentType;
	
	private String fileUploadFileName;
	
	private Integer idFindTemplate = 0;
	
	//private List<Integer> checkboxTemplate = new ArrayList<Integer>();
	protected Map<Integer, String> checkboxTemplate = new LinkedHashMap<Integer, String>();

	private void initEtatTemplate() {
		mapListStateTemplate = new LinkedHashMap<Integer, String>();
		mapListStateTemplate.put(1, getText("Actif"));
		mapListStateTemplate.put(0, getText("Inactif"));
	}
	
	private void initFormatTemplate() {
		mapListFormatTemplate = new LinkedHashMap<String, String>();
		mapListFormatTemplate.put("", getText(""));
		mapListFormatTemplate.put("250x250", getText("250x250"));
		mapListFormatTemplate.put("300x250", getText("300x250"));
		mapListFormatTemplate.put("300x300", getText("300x300"));
		mapListFormatTemplate.put("160x600", getText("600x150"));
		mapListFormatTemplate.put("728x90", getText("728x90"));
	}

	@SkipValidation
	@Action(value = "/gestionTempl", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "gestTempl.tiles")
	})
	public String gestionTempl() {
		try {
			initEtatTemplate();
			initFormatTemplate();
			if (null != user) {

				if (Constants.ADMINISTRATEUR.equalsIgnoreCase(user.getProfile())) {
					templates = templateService.getAllTemplatesVO();
				} else {
					templates = templateService.getAllTemplatesVO(user.getId());
				}

			}
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
	@Action(value = "/gestionTemplNonValide", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "gestTempl.tiles")
	})
	public String gestionTemplNonValide() {
		try {
			initEtatTemplate();
			initFormatTemplate();
			if (null != user) {
				if (Constants.ADMINISTRATEUR.equalsIgnoreCase(user.getProfile())) {
					templates = templateService.getTemplatesVONoValide();
				}
			}
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
	
	@Override
	public void validate() {
		if(null != template){
			
			session.put("imageUrl", template.getUrlImage());
		//	String tempRecup = (String) session.get("oldTemplate");
			if (StringUtils.isEmpty(template.getName())) {
				addActionError(getText("Nom de template est obligatoir."));
			}
			if (fileUpload == null) {
				if (StringUtils.isEmpty(template.getUrlImage())) {
					addActionError(getText("Vous devez choisir une image de votre template."));
				}
			}
			if (StringUtils.isEmpty(template.getFormat())) {
				addActionError(getText("Vous devez choisir un format."));
			}
			if (StringUtils.isEmpty(template.getText())) {
				addActionError(getText("Html de la banniere est obligatoir."));
			} else {
				String text =  validateHtml(template.getText());
				if (!valide) {
					addActionError(getText(text));
				}
			}
			validFormUpload = 1;
			validForm = 1;
			gestionTempl();
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
	
	@SkipValidation
	@Action(value = "/findTemplate", results = { 
			@Result(name = SUCCESS, location = "/pages/admin/saveTemp.jsp")
	})
	public String findTemplate() {
		try {
			if (idFindTemplate != 0) {
				template = templateService.getTemplateVO(idFindTemplate);
				titreAction = "Modification template \"" + template.getName() + "\"";
				// session.put("oldTemplate", template.getActif());
			}
			initEtatTemplate();
			initFormatTemplate();
			validFormUpload = 1;
			validForm = 1;
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = "Une erreur s'est produite! Merci de contacter le support technique </br> Erreur : "
					+ e.getMessage();
		}
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "/deleteTemplate", results = {
		@Result(name = SUCCESS, type = "redirectAction", params = {"actionName", "gestionTempl"})
	})
	public String deleteTemplate() {
		try {
			if (null != checkboxTemplate && !checkboxTemplate.isEmpty()) {
				List<Integer> listIds = Utils.genererListCheckedIds(checkboxTemplate);
				//creativeService.deleteCreaByIdTemplate();
				templateService.deleteListTemplates(listIds);
				msgInfo = "Les templates sélectionnées ont été supprimées avec succès.";
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
	@Action(value = "/prepareAjout", results = {
			@Result(name = SUCCESS, location = "/pages/admin/saveTemp.jsp") })
	public String prepareAjout() {
		try {
			template = new TemplateVO();
			titreAction = "Ajout nouvelle template";
			initEtatTemplate();
			initFormatTemplate();
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return SUCCESS;
	}
	
	@Action(value = "/createTemplate", results = {
			@Result(name = SUCCESS, type = "redirectAction", params = {"actionName", "gestionTempl"}),
			@Result(name = INPUT, type = "tiles", location = "gestTempl.tiles") })
	public String createTemplate() {
		try {
			if (null != template) {
				Integer idTemplUpdated = template.getTempId();
				if (null != user) {
					templateService.majTemplateEtape2(template, user.getId(), fileUploadFileName, fileUpload);
				}
				if (null != idTemplUpdated && 0 != idTemplUpdated.intValue()) {
					msgInfo = "La template \"" + template.getName() + "\" a été modifiée avec succès.";
				} else {
					msgInfo = "La template \"" + template.getName() + "\" a été ajoutée avec succès.";
				}
				session.put("msgInfo", msgInfo);
			}
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return INPUT;
	}
	
	@SkipValidation
	@Action(value = "/chargerZipTempl", results = { 
		@Result(name = SUCCESS, type = "tiles", location = "gestTempl.tiles"),
		@Result(name = "redirect", type = "redirectAction", location = "gestionTempl.action")
	})
	public String chargerZipTempl() throws Exception {
		try {
			if (StringUtils.isEmpty(template.getName())) {
				addActionError("Veuillez renseigner le nom de la template");
				validFormUpload = 0;
				return SUCCESS;
			} 
			if (tempZipFile == null) {
				addActionError("Vous devez choisir le dossier zip de votre template.");
				return SUCCESS;
			}
			if (StringUtils.isEmpty(template.getFormat())) {
				addActionError(getText("Vous devez choisir un format."));
				return SUCCESS;
			}
			UnZip unZip = new UnZip();
			String chemin = (user.getId() + "_" + template.getName() + "_" + new Date().getTime()).replaceAll("\\s+", "");
			String directoryBan = UPLOAD_PATH + chemin;
			List<String> list = unZip.unZipIt(tempZipFile.getAbsolutePath(), directoryBan);
			String banHtmlPath = "";
			if (null != list && !list.isEmpty()) {
				for (String elt : list) {
					elt = elt.replace("\\", "/");
					String pathFileToSave = elt.replace(directoryBan, "");
					pathFileToSave = pathFileToSave.replace("C:/", "");
					File file = new File(elt);
					saveCreaServiceAws.saveCrea(file, "templates/" + chemin + "/" + pathFileToSave);
					if (".html".equals(StringUtils.substring(pathFileToSave, StringUtils.lastIndexOf(pathFileToSave, ".")))) {
						banHtmlPath = "templates/" + chemin + "/" + pathFileToSave;
					}
				}
				banHtmlPath = saveCreaServiceAws.getS3Path() + saveCreaServiceAws.getS3Bucket() + "/" + banHtmlPath;
				template.setUrlTemplate(banHtmlPath);
				String html = getHtmlFromFile(banHtmlPath);
				System.out.println(html);
				template.setText(html);
				validFormUpload = 1;
			}
			// Pour le cas de l'annonceur on creer une template
			if (Constants.ANNONCEUR.equalsIgnoreCase(user.getProfile())) {
				templateService.majTemplateEtape2(template, user.getId(), fileUploadFileName, fileUpload);
				return "redirect";
			}
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		} finally {
			validForm = 1;
			initEtatTemplate();
			initFormatTemplate();
			if (Constants.ADMINISTRATEUR.equalsIgnoreCase(user.getProfile())) {
				templates = templateService.getAllTemplatesVO();
			} else {
				templates = templateService.getAllTemplatesVO(user.getId());
			}
		}
		return SUCCESS;
	}
	
	public String getHtmlFromFile(String banHtmlPath) throws Exception {
		URL url = new URL(banHtmlPath);
		String tDir = System.getProperty("java.io.tmpdir");
		String path = tDir + "tmp" + ".html";
		File file = new File(path);
		file.deleteOnExit();
		FileUtils.copyURLToFile(url, file);
		Document doc = Jsoup.parse(file, "utf-8");
		String html = doc.html();
		return html;
	}
	
	public CreativeVO getCreativeVO() {
		return creativeVO;
	}

	public void setCreativeVO(CreativeVO creativeVO) {
		this.creativeVO = creativeVO;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<Integer, String> getMapListStateTemplate() {
		return mapListStateTemplate;
	}

	public void setMapListStateTemplate(Map<Integer, String> mapListStateTemplate) {
		this.mapListStateTemplate = mapListStateTemplate;
	}

	public List<TemplateVO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateVO> templates) {
		this.templates = templates;
	}

	public Map<String, String> getMapListFormatTemplate() {
		return mapListFormatTemplate;
	}

	public void setMapListFormatTemplate(Map<String, String> mapListFormatTemplate) {
		this.mapListFormatTemplate = mapListFormatTemplate;
	}
	
	public TemplateVO getTemplate() {
		return template;
	}
	
	public void setTemplate(TemplateVO template) {
		this.template = template;
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
	
	public int getIdFindTemplate() {
		return idFindTemplate;
	}

	public void setIdFindTemplate(int idFindTemplate) {
		this.idFindTemplate = idFindTemplate;
	}

	public Map<Integer, String> getCheckboxTemplate() {
		return checkboxTemplate;
	}

	public void setCheckboxTemplate(Map<Integer, String> checkboxTemplate) {
		this.checkboxTemplate = checkboxTemplate;
	}

	public void setIdFindTemplate(Integer idFindTemplate) {
		this.idFindTemplate = idFindTemplate;
	}
	
	public File getTempZipFile() {
		return tempZipFile;
	}
	
	public void setTempZipFile(File tempZipFile) {
		this.tempZipFile = tempZipFile;
	}

	public String getTempZipFileContentType() {
		return tempZipFileContentType;
	}

	public void setTempZipFileContentType(String tempZipFileContentType) {
		this.tempZipFileContentType = tempZipFileContentType;
	}

	public String getTempZipFileFileName() {
		return tempZipFileFileName;
	}

	public void setTempZipFileFileName(String tempZipFileFileName) {
		this.tempZipFileFileName = tempZipFileFileName;
	}
	
}
