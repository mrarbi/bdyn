package com.orbit.dynamix.service;

import java.io.File;
import java.util.List;

import com.orbit.dynamix.entity.Template;
import com.orbit.dynamix.vo.TemplateVO;


public interface ITemplateService {
	
	public TemplateVO getTemplateVO(Integer id) throws Exception;

	public List<TemplateVO> getTemplatesVO() throws Exception;
	
	public List<TemplateVO> getAllTemplatesVO() throws Exception;
	
	public List<TemplateVO> getTemplatesByFormat(Integer width, Integer height) throws Exception;

	public int saveOrUpdate(Template template) throws Exception;

	public String getSourceHTML(Integer tempId) throws Exception;
	
	public Template getTemplate(Integer tempId) throws Exception;
	
	public List<Template> getTemplates() throws Exception;
	
	public Integer majTemplateEtape2(TemplateVO templateVO , Integer userId, String fileUploadFileName, File fileUpload) throws Exception;

	void deleteTemplates(Integer idTemp) throws Exception;

	void deleteListTemplates(List<Integer> templatesIds) throws Exception;
	
	public List<TemplateVO> getTemplatesByFormat(String format) throws Exception;
	
	public List<TemplateVO> getTemplatesVO(Integer userId) throws Exception;
	
	public List<TemplateVO> getAllTemplatesVO(Integer userId) throws Exception;

	public boolean deleteTemplatesByIdUser(List<Integer> listIds) throws Exception;

	public TemplateVO getTemplateByName(String name) throws Exception;
	
	public List<TemplateVO> getTemplatesVONoValide() throws Exception;
}
