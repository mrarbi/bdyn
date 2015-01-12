package com.orbit.dynamix.action;

//import gui.ava.html.image.generator.HtmlImageGenerator;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.utils.Constants;
import com.orbit.dynamix.vo.CreativeVO;

/**
 * 
 */
public class AccueilAction extends AbstarctAction { 
	
	private static final long serialVersionUID = 4418697921380052754L;

	@Autowired
	ICreativeService creativeService;
	
	private List<CreativeVO> listCreas = new ArrayList<CreativeVO>();
	
	@SkipValidation
	@Action(value = "/accueil", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "accueil.tiles"),
			@Result(name = "gestTemplate", type = "redirectAction", location = "gestionTempl.action")
	})
	public String accueil() throws Exception {
		try {
			session.remove("idCrea");
			msgError = (String) session.get("msgError");
			session.remove("msgError");
			if (StringUtils.isEmpty(msgError)) {
				msgInfo = (String) session.get("msgInfo");
			}
			session.remove("msgInfo");
			if (null != user) {
				if (Constants.ADMINISTRATEUR.equalsIgnoreCase(user.getProfile())) {
					listCreas = creativeService.getAllCreativesVO();
				} else {
					listCreas = creativeService.getCreativesVOByUser(user.getId());
				}
				if (Constants.GRAPHISTE.equalsIgnoreCase(user.getProfile())) {
					return "gestTemplate";
				}
			}
			
		} catch (Exception e) {
			LOG.error("Erreur", e);
			throw e;
		}
		return SUCCESS;
	}
	
	private String image;
	
	
	public String getImage() {
		return image;
	}

	
	public void setImage(String image) {
		this.image = image;
	}

	@SkipValidation
	@Action(value = "/captureImage")
	public void captureImage() {
		if (StringUtils.isNotEmpty(image)) {
			System.out.println(image);

			byte[] data = Base64.decodeBase64(image.replace("data:image/png;base64,", "").getBytes());
			try {
				OutputStream stream = new FileOutputStream("c://image_cap.png");
				stream.write(data);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SkipValidation
	@Action(value = "/loadEtatsCreasById")
	public void loadEtatsCreasById() {
		session.put("idCrea", idCrea);
	}
	
	@SkipValidation
	@Action(value = "/suppCrea", results = { 
			@Result(name = SUCCESS, type = "redirectAction", location = "accueil.action"),
			@Result(name = INPUT, type = "tiles", location = "accueil.tiles")
	})
	public String suppCrea() {
		try {
			if (null != idCrea && creativeService.deleteCreaById(idCrea) == true) {
				msgInfo = "La créa n°:" + idCrea + " a été supprimé avec succès!";
				session.put("msgInfo", msgInfo);
			}
			return SUCCESS;
		} catch (Exception e) {
			msgError = MSG_ERREUR + e.getMessage();
		}
		return INPUT;
	}

	public List<CreativeVO> getListCreas() {
		return listCreas;
	}

	public void setListCreas(List<CreativeVO> listCreas) {
		this.listCreas = listCreas;
	}

//	public static void main(String[] args) {
//		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
////		imageGenerator.loadHtml("<b>Hello World!</b> Please goto <a title=\"Goto Google\" href=\"http://www.google.com\">Google</a>.");
////		imageGenerator.loadUrl("http://dev05.orbit-interactive.fr/adops/mailorama/mailorama.html");
//		imageGenerator.loadUrl("https://www.google.co.ma/");
//		imageGenerator.saveAsImage("c:\\mailorama.jpeg");
////		imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
//	}
}
