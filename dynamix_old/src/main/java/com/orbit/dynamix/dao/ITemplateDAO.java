package com.orbit.dynamix.dao;

import java.util.List;

import com.orbit.dynamix.entity.Template;
import com.orbit.dynamix.vo.TemplateVO;

/**
 * 
 * @author m.arbi
 *
 */
public interface ITemplateDAO {

	
	public int saveOrUpdate(Template creative);
	
	public String getSourceHTML(Integer tempId);
	
	public Template getTemplate(Integer tempId);
	
	public List<Template> getTemplates();
	
	public List<TemplateVO> getTemplatesVO();
	
	public List<TemplateVO> geAlltTemplatesVO();
	
	public List<TemplateVO> getTemplatesVOByFormat(Integer width, Integer height);
	
	public void deleteTemplate(Template template);
	
//	public void deleteListTemplates(List<Integer> templatesIds);

	public List<TemplateVO> getTemplatesVOByFormat(String format);
	
	public List<TemplateVO> getTemplatesByUserVO(Integer userId);
	
	public List<TemplateVO> getAllTemplatesByUserVO(Integer userId);
	
	public List<TemplateVO> getTemplatesVONoValide();

	public boolean deleteTemplatesByIdUser(List<Integer> listIds);

	public TemplateVO getTemplateByName(String name);
	
}
