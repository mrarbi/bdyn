package com.orbit.dynamix.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.orbit.dynamix.service.ICreativeService;

/**
 * 
 */
public class Etape5Action extends AbstarctAction implements SessionAware {
	
	private static final long serialVersionUID = 1L;
	
	private String script;
	
	@Autowired
	ICreativeService creativeService;
	
	@SkipValidation
	@Action(value = "/etape5", results = { 
			@Result(name = SUCCESS, type = "tiles", location = "etape5.tiles")
	})
	public String etape5() throws Exception {
		try {
			
			idCrea = (Integer) session.get("idCrea");
			if (null != idCrea) {
				creativeVO = creativeService.getCreativeVO(idCrea);
				HttpServletRequest request = ServletActionContext.getRequest();
				
				String path = StringUtils.replace(request.getRequestURL().toString(), request.getServletPath(), "") + "/rest";
//				System.out.println(path);
				
				script = "<script src=\"//code.jquery.com/jquery-2.1.1.min.js\"></script>\n" +
				"<script src=\"//cdn.jsdelivr.net/bxslider/4.1.1/jquery.bxslider.min.js\"></script>\n" +
				"<script type=\"text/javascript\">\n" +
				"\t$(function(){\n" + 
//				"\tfunction initialize() {\n" +
				"\t\tvar wsUri = '" + path + "/getCreative?crea=" + idCrea + "';\n" +
//				"\t\t$.ajax({type: 'GET', url:wsUri, dataType:'html', async:'true', crossDomain: true})\n"+
//				"\t\t.done(function(response){\n"+
//				"\t\t$(\"#_orbDynCr\").html(response); \n"+
//				"\t\t}).fail(function(error){});\n"+
//				"\t}\n"+
				"\t\t$(\"#_orbDynCr\").load(wsUri);\n" +
				"\t});\n" +
				"</script><div id=\"_orbDynCr\"></div>\n";
				session.put("script", script);
			}
			msgError = (String) session.get("msgError");
			session.remove("msgError");
			if (StringUtils.isEmpty(msgError)) {
				msgInfo = (String) session.get("msgInfo");
			}
			session.remove("msgInfo");
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "/preview", results = { 
			@Result(name = SUCCESS, location = "/pages/preview.jsp")
	})
	public String preview() throws Exception {
		try {
			script = (String) session.get("script");
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
		}
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "/activateBan", results = { 
			@Result(name = SUCCESS, type = "redirectAction", location = "etape5.action")
	})
	public String activateBan() throws Exception {
		try {
			
			if (null != idCrea) {
				creativeService.majCreativeEtape5(idCrea);
				msgInfo = "La bannière \"" + idCrea+ "\" est activée et publiée avec succès.";
				session.put("msgInfo", msgInfo);
			}
			
		} catch (Exception e) {
			LOG.error("Erreur", e);
			msgError = MSG_ERREUR + e.getMessage();
			session.put("msgError", msgError);
		}
		return SUCCESS;
	}
	
	public String getScript() {
		return script;
	}
	
	public void setScript(String script) {
		this.script = script;
	}

}
