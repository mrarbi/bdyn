package com.orbit.dynamix.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "creative")
public class Creative implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "creative_id")
	private Integer creativeId;

	private String url;

	private String fichier;

	@Column(name = "type_fichier")
	private String typeFichier;
	
	private String separateur;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	private int actif;
	
	private Integer volume;
	
	private String xpath;
	
	@Column(length = 1000)
	private String matching;
	
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "modif_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifDate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "temp_id")
	private Template template;
	
	@Column(name = "etape", nullable = false, columnDefinition = "int default 0")
	private int etape;
	
	@Column(name = "login_url")
	private String loginUrl;
	
	@Column(name = "passwd_url")
	private String passwdUrl;
	
	private String geolocalisation;
	
	private String typeGeo;
	
	public Creative() {
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

	public String getSeparateur() {
		return separateur;
	}

	public void setSeparateur(String separateur) {
		this.separateur = separateur;
	}

	public String getTypeFichier() {
		return typeFichier;
	}

	public void setTypeFichier(String typeFichier) {
		this.typeFichier = typeFichier;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getActif() {
		return actif;
	}

	public void setActif(int actif) {
		this.actif = actif;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifDate() {
		return modifDate;
	}

	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}
	
	public Integer getVolume() {
		return volume;
	}
	
	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getMatching() {
		return matching;
	}

	public void setMatching(String matching) {
		this.matching = matching;
	}
	
	public Template getTemplate() {
		return template;
	}
	
	public void setTemplate(Template template) {
		this.template = template;
	}

	public int getEtape() {
		return etape;
	}
	
	public void setEtape(int etape) {
		this.etape = etape;
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
	
//	@PrePersist
//	protected void onCreate() {
//	    creationDate = new Date();
//	}
//	
//	@PrePersist
//	protected void onUpdate() {
//		modifDate = new Date();
//	}

}
