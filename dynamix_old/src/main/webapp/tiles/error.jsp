<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>      
<div id="error" style="margin-left: 10%; height: 750px;">
<h3>Une erreur s'est produite! Merci de contacter le support technique :(</h3>
<br/>
<a href="${pageContext.request.contextPath}/accueil.action" style="color:blue">
	<i>Continuer...</i>
</a>
<s:debug/>
</div>