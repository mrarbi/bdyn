package com.orbit.dynamix.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.orbit.dynamix.dao.ICreativeDAO;
import com.orbit.dynamix.entity.Creative;
import com.orbit.dynamix.entity.Template;
import com.orbit.dynamix.entity.User;
import com.orbit.dynamix.security.Utilisateur;
import com.orbit.dynamix.utils.Constants;
import com.orbit.dynamix.utils.Utils;
import com.orbit.dynamix.vo.CreativeVO;

@Service
@Transactional
public class CreativeService implements ICreativeService {
	
	private static Logger log = LoggerFactory.getLogger(CreativeService.class);
	
	@Autowired
	ICreativeDAO creativeDAO;
	
	@Autowired
	IUsersService usersService;
	
	@Autowired
	ITemplateService templateService;
	
	@Autowired
	IMatchingService matchingService;
	
	
	public void saveOrUpdate(Creative creative) throws Exception {
		try {
			creativeDAO.saveOrUpdate(creative);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}

	public Creative getCreative(Integer id, Integer uid) throws Exception {
		try {
			return creativeDAO.getCreative(id);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}

	public Creative getCreative(Integer id) throws Exception {
		try {
			return creativeDAO.getCreative(id);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}

	public CreativeVO getCreativeVO(Integer idCrea) throws Exception {
		try {
			return creativeDAO.getCreativeVO(idCrea);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}

	public Integer getTempIdByCrea(Integer idCrea) throws Exception {
		try {
			return creativeDAO.getTempIdByCrea(idCrea);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean deleteCreaById(Integer idCrea) throws Exception {
		try {
			if (null != idCrea) {
				creativeDAO.deleteById(idCrea);
				matchingService.removeCreaFromMongo(idCrea);
				return true;
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return false;
	}
	
	@Override
	public List<CreativeVO> getCreativesVOByUser(int id) throws Exception {
		List<CreativeVO> list = new ArrayList<CreativeVO>(0);
		try {
			list = creativeDAO.getCreativesVOByUser(id);
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return list;
	}
	
	@Override
	public List<CreativeVO> getAllCreativesVO() throws Exception {
		List<CreativeVO> list = new ArrayList<CreativeVO>(0);
		try {
			list = creativeDAO.getAllCreativesVO();
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return list;
	}

	@Override
	public int majCreativeEtape1(CreativeVO creativeVO, Utilisateur utilisateur, String urlOrFile, String authUrl, File fileUpload, String fileUploadFileName, String fileUploadContentType) throws Exception {
		int idCrea = 0;
		Creative creative = null;
		try {
			if (null != creativeVO && null != utilisateur) {
				if (null != creativeVO.getCreativeId()) {
					creative = creativeDAO.getCreative(creativeVO.getCreativeId());
				}
				if (null == creative) {
					creative = new Creative();
					// utilisateur
					User user = usersService.getUserById(utilisateur.getId());
					creative.setUser(user);
					creative.setCreationDate(new Date());
				} else {
					// date creation/modification
					creative.setModifDate(new Date());
				}
				// etape suivante
				creative.setEtape(creativeVO.getEtape());
				creative.setActif(0);
				// separateur CSV
				if (StringUtils.isEmpty(creativeVO.getSeparateur())) {
					creative.setSeparateur(";");
				} else {
					creative.setSeparateur(creativeVO.getSeparateur());
				}
				// template
				if (null != creativeVO.getTempId()) {
					Template template = templateService.getTemplate(creativeVO.getTempId());
					if (null != template) {
						creative.setTemplate(template);
					}
				}
				if ("url".equalsIgnoreCase(urlOrFile)) {
					creative.setUrl(creativeVO.getUrl());
					creative.setFichier(null);
					creative.setTypeFichier(null);
					creative.setLoginUrl(null);
					creative.setPasswdUrl(null);
					if ("on".equalsIgnoreCase(authUrl)) {
						creative.setLoginUrl(creativeVO.getLoginUrl());
						creative.setPasswdUrl(creativeVO.getPasswdUrl());
					}
					creativeDAO.saveOrUpdate(creative);
					idCrea = creative.getCreativeId();
				} else if (null != fileUpload) {
					creative.setUrl(null);
					creative.setLoginUrl(null);
					creative.setPasswdUrl(null);
					creative.setFichier(fileUploadFileName);
					creative.setTypeFichier(StringUtils.substring(fileUploadFileName, StringUtils.lastIndexOf(fileUploadFileName, ".") + 1));
					creativeDAO.saveOrUpdate(creative);
					idCrea = creative.getCreativeId();
					if (idCrea != 0) {
						File saveFilePath = new File(Constants.UPLOAD_PATH + utilisateur.getId() + "/" + idCrea + "/" + fileUploadFileName);
						try {
							FileUtils.copyFile(fileUpload, saveFilePath);
						} catch (IOException ex) {
							throw ex;
						}
					}
				} else {
					creativeDAO.saveOrUpdate(creative);
					idCrea = creative.getCreativeId();
				}
				if (null != creativeVO && 0 != idCrea) {
					creativeVO.setCreativeId(idCrea);
					matchingService.fillDataIntoMongo(creativeVO);
				}
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return idCrea;
	}
	
	@Override
	public void majCreativeEtape2(CreativeVO creativeVO, Integer tempSelect)
			throws Exception {
		try {
			if (null != creativeVO && null != tempSelect) {
				Creative creative = creativeDAO.getCreative(creativeVO
						.getCreativeId());
				if (null != creative) {
					creative.setEtape(creativeVO.getEtape());
					creative.setActif(0);
					Template template = templateService.getTemplate(tempSelect);
					if (null != template) {
						creative.setTemplate(template);
					}
				}
				creativeDAO.saveOrUpdate(creative);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}
	
	@Override
	public void majCreativeEtape3v(CreativeVO creativeVO)
			throws Exception {
		try {
			if (null != creativeVO) {
				Creative creative = creativeDAO.getCreative(creativeVO.getCreativeId());
				if (null != creative) {
					creative.setMatching(creativeVO.getMatching());
					creative.setModifDate(Utils.getDate(creativeVO.getModifDate()));
					creative.setEtape(creativeVO.getEtape());
					creative.setGeolocalisation(creativeVO.getGeolocalisation());
					creative.setTypeGeo(creativeVO.getTypeGeo());
					creative.setActif(0);
					creativeDAO.saveOrUpdate(creative);
				}
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
	}
	
	public Boolean deleteCreaByIdUser(List<Integer> idsUsers) throws Exception {
		return creativeDAO.deleteCreaByIdUser(idsUsers);
	}
	
	public boolean majCreativeEtape5(Integer idCrea) throws Exception {
		try {
			if (null != idCrea) {
				Creative creative = creativeDAO.getCreative(idCrea);
				if (null != creative) {
					creative.setActif(1);
					creative.setEtape(5);
					creativeDAO.saveOrUpdate(creative);
					return true;
				}
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return false;
	}
	
	public List<Creative> getCreativesAssocieTemplates(List<Integer> idTemplates) throws Exception {
		List<Creative> listCreatives = new ArrayList<Creative>(0);
		try {
			if (null != idTemplates && !idTemplates.isEmpty()) {
				listCreatives = creativeDAO.getCreativesByTemplatesId(idTemplates);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return listCreatives;
	}
	
}
