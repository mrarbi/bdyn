<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 5 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" media="screen" href="<c:url value='/themes/css/general.css'/>"/>
<title>Bienvenue</title>
</head>

<body>
	<div class="conteneur_head">
    	<a href="${pageContext.request.contextPath}/accueil.action"><img src="<c:url value='/themes/images/logo.png'/>" class="logo" /></a>
    </div>
    <div class="trait_gris"></div>
    
    <div class="conteneur">
    	<div class="page_login">
        	<c:if test="${not empty param.error}">
				<div style="margin-bottom: 10px;" class="">
					<!-- <div class="errors"> -->
					<font color="red">Login ou mot de passe incorrect </font>
				</div>
			</c:if>
	        <form class="form-inline" role="form" action="j_spring_security_check" method="post">
	        	<div class="ligne_form"><img src="<c:url value='/themes/images/login.jpg'/>" class="icon_champs"/>
	        		<input type="name" name="j_username" class="champs" tabindex="1" placeholder="Login"/>
	        	</div>
	            <div class="ligne_form"><img src="<c:url value='/themes/images/mdp.jpg'/>" class="icon_champs"/>
	            	<input type="password" name="j_password" class="champs" tabindex="2" placeholder="Mot de passe"/>
	            </div>
	            <div class="ligne_form">
	            	<input id="rememberMe" type="checkbox" class="check_box" name="_spring_security_remember_me"><label for="rememberMe" class="label_check">Se rappeler de moi</label>
	                <a href="#" class="mdp_oublie">Mot de passe oublié ?</a>
	            </div>
	            
	            
	            <div class="ligne_form">
	            	<input type="reset" class="btn_reset" value="Annuler"/>
	                <input type="submit" class="btn_submit" value="Valider"/>
	            </div>
	        </form>
        	
        </div>
    </div>
    
</body>
<footer>Copyright © - Orbit-Interactives</footer>
</html>
