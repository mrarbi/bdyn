package com.orbit.dynamix.dao;

import java.util.List;

import com.orbit.dynamix.entity.Creative;
import com.orbit.dynamix.vo.CreativeVO;

/**
 * 
 * @author m.arbi
 *
 */
public interface ICreativeDAO extends IGenericDAO<Creative, Integer>{

	
	public Creative getCreative(Integer id);
	
	public CreativeVO getCreativeVO(Integer idCrea);
	
	public Integer getTempIdByCrea(Integer idCrea);

	public List<CreativeVO> getCreativesVOByUser(int idUser);
	
	public void delete(Creative creative);
	
	public List<CreativeVO> getAllCreativesVO();
	
	public boolean deleteCreaByIdUser(List<Integer> idsUsers);
	
	public List<Creative> getCreativesByTemplatesId(List<Integer> idsTemplates);
	
}
