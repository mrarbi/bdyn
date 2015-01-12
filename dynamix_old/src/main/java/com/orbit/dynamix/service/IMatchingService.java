package com.orbit.dynamix.service;

import java.util.List;
import java.util.Map;

import com.orbit.dynamix.vo.CreativeVO;
import com.orbit.dynamix.vo.Pair;

/**
 * 
 * @author m.arbi
 *
 */
public interface IMatchingService {

	public List<List<Pair<String, String>>> getDataList(CreativeVO creativeVO) throws Exception;
	
	public List<List<Pair<String, String>>> getDataListFromUrl(CreativeVO creativeVO) throws Exception;
	
	public List<Map<String, String>> getLignesAleratoire(int nbrIterat, CreativeVO creativeVO) throws Exception;

//	public List<Map<String, String>> getLignesFromFlux(int i, CreativeVO creativeVO);
	
	public void fillMapEntries() throws Exception;
	
//	public void fillCreaInMapEntries(CreativeVO creativeVO);
	
	public void fillDataIntoMongo(CreativeVO elt);
	
//	public void removeCreaFromMapEntries(Integer idCrea);
	
	public void removeCreaFromMongo(Integer idCrea);

	public List<Map<String, String>> getDataFromMongo(int nbrLigne, CreativeVO creativeVO);
	
}
