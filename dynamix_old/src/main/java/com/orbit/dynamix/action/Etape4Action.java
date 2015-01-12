package com.orbit.dynamix.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.service.IMatchingService;
import com.orbit.dynamix.service.ITemplateService;
import com.orbit.dynamix.utils.Constants;
import com.orbit.dynamix.utils.Critere;
import com.orbit.dynamix.vo.Pair;

/**
 * 
 */
public class Etape4Action extends AbstarctAction implements SessionAware {
	
	private static final long serialVersionUID = 1L;
	
	private String script;
	
	@Autowired
	ICreativeService creativeService;
	
	@Autowired
	ITemplateService templateService;
	
	private Map<Integer, String> mapListOperateurs;
	
	private Map<Integer, String> mapListGeolocal;
	
	
	private List<Critere> critereParam = new ArrayList<Critere>();
	
	private List<Critere> critereParamGeo = new ArrayList<Critere>();
	
	private Integer dataSize = 0;
	
	private List<List<Pair<String, String>>> dataList = new ArrayList<List<Pair<String,String>>>(0);
	
	@Autowired
	IMatchingService matchingService;
	
	@SkipValidation

	@Action(value = "/etape4", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "etape4.tiles"), 
			@Result(name = INPUT, type = "tiles", location = "etape3.tiles")
	})
	public String etape4(){
		try {

			if (null != user) {
				Integer idCrea = (Integer) session.get("idCrea");
				creativeVO = creativeService.getCreativeVO(idCrea);
				if (null != creativeVO) {
					session.put("idCrea", creativeVO.getCreativeId());
					// preparer les elements pour le matching
					if (StringUtils.isNotEmpty(creativeVO.getFichier())) {
						dataList = matchingService.getDataList(creativeVO);
					} else if (StringUtils.isNotEmpty(creativeVO.getUrl())) {
						dataList = matchingService.getDataListFromUrl(creativeVO);
					}
					if (null != dataList && !dataList.isEmpty()) {
						dataSize = dataList.get(0).size();
					}
					
					if(Constants.GEO.equals(creativeVO.getTypeGeo())){
						if(creativeVO.getGeolocalisation() != null){
							String[] objetListToSplit;
							String[] objeCritereToSplit;
							
							objetListToSplit = creativeVO.getGeolocalisation().split(SEPARATOR_PARAMETRAGE_LIG);
							
							if(objetListToSplit != null && objetListToSplit.length != 1){
								
								for(int i =0; i < objetListToSplit.length ; i++){
									objeCritereToSplit = objetListToSplit[i].split("\\|",-1);
									
									Critere crit = new Critere();
									crit.setColumn(objeCritereToSplit[0]);
									crit.setOperateur("1");
									crit.setValeur(objeCritereToSplit[1]);
									
									critereParamGeo.add(crit);
								}
							}
						}
					}	
				}
			}	
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
	
	@SkipValidation
	@Action(value = "/etape4v", results = {
			@Result(name = SUCCESS, type = "redirectAction", location = "etape5.action"),
			@Result(name = INPUT, type = "tiles", location = "etape4.tiles") })
	public String etape4v(){
		try {
			
			initListGeoloca();
			initListOperateurs();

			Integer idCrea = (Integer) session.get("idCrea");
			creativeVO = creativeService.getCreativeVO(idCrea);
			if (null != creativeVO) {
				
				String matching = "";
				
				if(GEO.equals(checkOptionParams)){
					
					if (null != critereParamGeo && !critereParamGeo.isEmpty()) {
						for (Critere elt: critereParamGeo) {
							if(elt!= null){
								matching += elt.getColumn() + SEPARATOR_PARAMETRAGE_COL + elt.getValeur() + SEPARATOR_PARAMETRAGE_LIG;
							}
						}
					}
					
				}
				
				if(ALEATOIR.equals(checkOptionParams)){
					
					Random rand = new Random();

				    int colVal = rand.nextInt(dataSize + 1);
				    int OperVal = rand.nextInt(1);
				    int zonVal = rand.nextInt(4);
				    matching = 	  colVal  + SEPARATOR_PARAMETRAGE_COL + 
				    			+ OperVal + SEPARATOR_PARAMETRAGE_COL + 
				    		    + zonVal  + SEPARATOR_PARAMETRAGE_LIG;
					
				}
				
				
//				System.out.println(matching);
				creativeVO.setGeolocalisation(matching);
				creativeVO.setTypeGeo(checkOptionParams);
				//////////////////////////
				// creative.setMatching(matching);
				creativeVO.setModifDate(new Date());
				creativeVO.setEtape(4);
				creativeService.majCreativeEtape3v(creativeVO);
				// creativeService.saveOrUpdate(creative);
				msgInfo = "L'étape " + creativeVO.getEtape() + " est terminée avec succès.";
				session.put("msgInfo", msgInfo);
			}
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
			return INPUT;
		}
	}

	
	private void initListOperateurs() {
		mapListOperateurs = new LinkedHashMap<Integer, String>();
		mapListOperateurs.put(0, getText("="));
		mapListOperateurs.put(1, getText("!="));
		mapListOperateurs.put(2, getText("<="));
		mapListOperateurs.put(3, getText(">"));
		mapListOperateurs.put(4, getText(">="));
	}
	
	
	private void initListGeoloca() {
		mapListGeolocal = new LinkedHashMap<Integer, String>();
		mapListGeolocal.put(0, getText("Ville"));
		mapListGeolocal.put(1, getText("Zip"));
		mapListGeolocal.put(2, getText("Pays"));
		mapListGeolocal.put(3, getText("Region"));
		mapListGeolocal.put(4, getText("GPS"));
	}
	
	public String getScript() {
		return script;
	}
	
	public void setScript(String script) {
		this.script = script;
	}

	public List<List<Pair<String, String>>> getDataList() {
		return dataList;
	}

	public void setDataList(List<List<Pair<String, String>>> dataList) {
		this.dataList = dataList;
	}
	public Integer getDataSize() {
		return dataSize;
	}
	
	public void setDataSize(Integer dataSize) {
		this.dataSize = dataSize;
	}

	public Map<Integer, String> getMapListOperateurs() {
		return mapListOperateurs;
	}

	public void setMapListOperateurs(Map<Integer, String> mapListOperateurs) {
		this.mapListOperateurs = mapListOperateurs;
	}

	public List<Critere> getCritereParam() {
		return critereParam;
	}

	public void setCritereParam(List<Critere> critereParam) {
		this.critereParam = critereParam;
	}

	public List<Critere> getCritereParamGeo() {
		return critereParamGeo;
	}

	public void setCritereParamGeo(List<Critere> critereParamGeo) {
		this.critereParamGeo = critereParamGeo;
	}

	public Map<Integer, String> getMapListGeolocal() {
		return mapListGeolocal;
	}

	public void setMapListGeolocal(Map<Integer, String> mapListGeolocal) {
		this.mapListGeolocal = mapListGeolocal;
	}
	
}
