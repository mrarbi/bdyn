package com.orbit.dynamix.service;

import java.io.File;
import java.util.List;

import com.orbit.dynamix.entity.Creative;
import com.orbit.dynamix.security.Utilisateur;
import com.orbit.dynamix.vo.CreativeVO;


public interface ICreativeService {

//	public CreativeVO getCreativeVOByUser(int id);

	public List<CreativeVO> getCreativesVOByUser(int id) throws Exception;

//	public CreativeVO getCreativeVOById(Integer idCreas);

	public int majCreativeEtape1(CreativeVO creativeVO, Utilisateur user, String urlOrFile, String authUrl, File fileUpload, String fileUploadFileName, String fileUploadContentType) throws Exception;
	
	public void majCreativeEtape2(CreativeVO creativeVO, Integer tempSelect) throws Exception;

	public void majCreativeEtape3v(CreativeVO creativeVO) throws Exception;
	
	public void saveOrUpdate(Creative creative) throws Exception;
	
	public Creative getCreative(Integer id, Integer uid) throws Exception;
	
	public Creative getCreative(Integer id) throws Exception;
	
	public CreativeVO getCreativeVO(Integer idCrea) throws Exception;
	
	public Integer getTempIdByCrea(Integer idCrea) throws Exception;
	
	public boolean deleteCreaById(Integer idCrea) throws Exception;

	public Boolean deleteCreaByIdUser(List<Integer> idsUsers) throws Exception;

	public List<CreativeVO> getAllCreativesVO() throws Exception;
	
	public boolean majCreativeEtape5(Integer idCrea) throws Exception;
	
	public List<Creative> getCreativesAssocieTemplates(List<Integer> idTemplates) throws Exception;
	
}
