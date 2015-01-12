package com.orbit.dynamix.vo;

import java.io.Serializable;
import java.util.Date;

import com.orbit.dynamix.utils.Utils;

/**
 * 
 * @author m.arbi
 *
 */
public class CreativeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer creativeId;

	private String url;

	private String fichier;

	private String typeFichier;
	
	private String separateur;
	
	private Integer userId;
	
	private String matching;
	
	private Integer tempId;

	private String text;
	
	private String xpath;
	
	private String loginUrl;
	
	private String passwdUrl;
	
	private int etape;
	
	private String creationDate;
	
	private String modifDate;
	
	private String geolocalisation;
	
	private String typeGeo;
	
	private int actif;

	public CreativeVO() {
	}

	public Integer getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(Integer creativeId) {
		this.creativeId = creativeId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFichier() {
		return fichier;
	}
	
	public void setFichier(String fichier) {
		this.fichier = fichier;
	}
	
	public String getTypeFichier() {
		return typeFichier;
	}
	
	public void setTypeFichier(String typeFichier) {
		this.typeFichier = typeFichier;
	}
	
	public String getSeparateur() {
		return separateur;
	}
	
	public void setSeparateur(String separateur) {
		this.separateur = separateur;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMatching() {
		return matching;
	}

	public void setMatching(String matching) {
		this.matching = matching;
	}

	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}
	
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	public String getPasswdUrl() {
		return passwdUrl;
	}
	
	public void setPasswdUrl(String passwdUrl) {
		this.passwdUrl = passwdUrl;
	}

	public int getEtape() {
		return etape;
	}

	public void setEtape(int etape) {
		this.etape = etape;
	}

	
	public String getCreationDate() {
		return creationDate;
	}

	
	public void setCreationDate(Date creationDate) {
		this.creationDate = Utils.getString(creationDate);
	}

	public String getModifDate() {
		return modifDate;
	}

	public void setModifDate(Date modifDate) {
		this.modifDate = Utils.getString(modifDate);
	}

	public String getGeolocalisation() {
		return geolocalisation;
	}

	public void setGeolocalisation(String geolocalisation) {
		this.geolocalisation = geolocalisation;
	}

	public String getTypeGeo() {
		return typeGeo;
	}

	public void setTypeGeo(String typeGeo) {
		this.typeGeo = typeGeo;
	}

	public int getActif() {
		return actif;
	}

	public void setActif(int actif) {
		this.actif = actif;
	}
	
}
