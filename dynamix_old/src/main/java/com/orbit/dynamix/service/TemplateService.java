package com.orbit.dynamix.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.orbit.dynamix.dao.ITemplateDAO;
import com.orbit.dynamix.entity.Creative;
import com.orbit.dynamix.entity.Template;
import com.orbit.dynamix.vo.TemplateVO;

@Service
@Transactional
public class TemplateService implements ITemplateService {

	private static Logger log = LoggerFactory.getLogger(TemplateService.class);

	@Autowired
	private ITemplateDAO templateDAO;

	@Autowired
	IUsersService usersService;
	
	@Autowired
	ICreativeService creativeService;
	
	@Autowired
	ISaveCreaServiceAws saveCreaServiceAws;

//	@Autowired
//	IFtpService ftpService;

	public int saveOrUpdate(Template template) {
		return templateDAO.saveOrUpdate(template);
	}

	public String getSourceHTML(Integer tempId) {
		return templateDAO.getSourceHTML(tempId);
	}

	public Template getTemplate(Integer tempId) {
		return templateDAO.getTemplate(tempId);
	}

	public List<Template> getTemplates() {
		return templateDAO.getTemplates();
	}

	@Override
	public TemplateVO getTemplateVO(Integer id) throws Exception {
		TemplateVO templateVO = new TemplateVO();
		try {
			Template template = templateDAO.getTemplate(id);
			if (null != template) {
				BeanUtils.copyProperties(templateVO, template);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templateVO;
	}

	@Override
	public List<TemplateVO> getTemplatesVO() throws Exception {
		List<TemplateVO> templatesVO = new ArrayList<TemplateVO>();
		;
		try {
			templatesVO = templateDAO.getTemplatesVO();
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templatesVO;
	}

	@Override
	public List<TemplateVO> getAllTemplatesVO() throws Exception {

		List<TemplateVO> templatesVO = new ArrayList<TemplateVO>();
		;
		try {
			templatesVO = templateDAO.geAlltTemplatesVO();
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templatesVO;
	}

	@Override
	public List<TemplateVO> getTemplatesByFormat(Integer width, Integer height)
			throws Exception {

		List<TemplateVO> templatesVO = new ArrayList<TemplateVO>();
		try {
			templatesVO = templateDAO.getTemplatesVOByFormat(width, height);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templatesVO;
	}

	@Override
	public Integer majTemplateEtape2(TemplateVO templateVO, Integer userId,
			String fileUploadFileName, File fileUpload) throws Exception {
		int idTemp = 0;
		Template template;
		try {
			if (null != templateVO.getTempId()) {
				template = templateDAO.getTemplate(templateVO.getTempId());
			} else {
				template = new Template();
			}
			template.setType("ban");
			template.setCreationDate(new Date());
			template.setUser(usersService.getUserById(userId));
			template.setName(templateVO.getName());
			template.setText(templateVO.getText());
			template.setWidth(templateVO.getWidth());
			template.setActif(templateVO.getActif());
			template.setUrlTemplate(templateVO.getUrlTemplate());
			template.setValide(templateVO.getValide());
			// template.setHeight(templateVO.getHeight());
			// template.setActif(templateVO.getActif());
			template.setFormat(templateVO.getFormat());
			template.setModifDate(new Date());
			idTemp = templateDAO.saveOrUpdate(template);
			if (null != fileUpload && null != fileUploadFileName) {
				String tempName = fileUploadFileName.replaceAll("\\s+", "");
				String nameTemplates = saveCreaServiceAws.getS3Path()+"/"+ saveCreaServiceAws.getS3Bucket()+"/templates/"+ idTemp + "/image/"
						+ tempName;
				// String nameTemplates = s4Path + tempName;
				
				saveCreaServiceAws.saveCrea(fileUpload, "templates/"+ idTemp + "/image/" + tempName);
				//ftpService.storeOnServer(tempName, fileUpload, idTemp, exist);
				template.setUrlImage(nameTemplates);
				idTemp = templateDAO.saveOrUpdate(template);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return idTemp;
	}

	@Override
	public void deleteTemplates(Integer idTemp) throws Exception {
		try {
			if (null != idTemp) {
				Template template = templateDAO.getTemplate(idTemp);
				if (null != template) {
					templateDAO.deleteTemplate(template);
				}
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void deleteListTemplates(List<Integer> idsTemps) throws Exception {
		try {
			if (null != idsTemps && !idsTemps.isEmpty()) {
				// recuperer la liste des crea associé
				List<Creative> listCreatives = creativeService.getCreativesAssocieTemplates(idsTemps);
				// mettre à jour la liste des template
				for (Creative creative : listCreatives) {
					creative.setTemplate(null);
					creative.setMatching(null);
					creative.setEtape(1);
					creative.setActif(0);
					creativeService.saveOrUpdate(creative);
				}
				for (Integer tempId : idsTemps) {
					if (null != tempId) {
						Template template = templateDAO.getTemplate(tempId);
						templateDAO.deleteTemplate(template);
					}
				}
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<TemplateVO> getTemplatesByFormat(String format)
			throws Exception {
		List<TemplateVO> templatesVO = new ArrayList<TemplateVO>();
		try {
			templatesVO = templateDAO.getTemplatesVOByFormat(format);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templatesVO;
	}

	@Override
	public List<TemplateVO> getTemplatesVO(Integer userId) throws Exception {
		List<TemplateVO> templatesVO = new ArrayList<TemplateVO>();
		try {
			templatesVO = templateDAO.getTemplatesByUserVO(userId);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templatesVO;
	}

	@Override
	public List<TemplateVO> getAllTemplatesVO(Integer userId) throws Exception {

		List<TemplateVO> templatesVO = new ArrayList<TemplateVO>();
		try {
			templatesVO = templateDAO.getAllTemplatesByUserVO(userId);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templatesVO;
	}
	
	@Override
	public List<TemplateVO> getTemplatesVONoValide() throws Exception {
		
		List<TemplateVO> templatesVO = new ArrayList<TemplateVO>();
		try {
			templatesVO = templateDAO.getTemplatesVONoValide();
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return templatesVO;
	}

	@Override
	public boolean deleteTemplatesByIdUser(List<Integer> listIds)
			throws Exception {
		try {
			if (null != listIds && !listIds.isEmpty()) {
				return templateDAO.deleteTemplatesByIdUser(listIds);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return false;
	}
	
	@Override
	public TemplateVO getTemplateByName(String name) throws Exception {
		
		try {
			if (!name.isEmpty()) {
				return templateDAO.getTemplateByName(name);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		
		return null;
	}

}
