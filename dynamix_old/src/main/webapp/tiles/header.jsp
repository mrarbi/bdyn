<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<sec:authorize access="isAnonymous()">
	<c:redirect url="${pageContext.request.contextPath}/login.action" />
</sec:authorize>

<div class="conteneur_head">
	<a href="${pageContext.request.contextPath}/accueil.action"><img
		src="${pageContext.request.contextPath}/themes/images/logo.png"
		class="logo" /></a>
</div>
<div class="trait_gris_acceuil">
	<div class="page_acceuil">
		<div class="bienvenue">Bienvenue</div>
		<ul class="topMenu">
			<li class="user_admin">
				<sec:authentication property="principal.username" />
			</li>
			<li>|</li>
			<li class="connecte_text dropDown">
				<sec:authentication property="principal.profile" />
				<sec:authorize ifAnyGranted="Administrateur,Annonceur">
				<ul>
					<sec:authorize ifAnyGranted="Administrateur">
						<li><a
							href="${pageContext.request.contextPath}/admin/gestionTempl.action"
							class="lien_deconnextion">Gerer les templates</a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="Annonceur">
						<li><a
							href="${pageContext.request.contextPath}/admin/gestionTempl.action"
							class="lien_deconnextion">Gerer mes templates</a></li>
					</sec:authorize>
					
					<sec:authorize ifAnyGranted="Administrateur">
						<li><a
							href="${pageContext.request.contextPath}/admin/gestionTemplNonValide.action"
							class="lien_deconnextion">Gerer les templates annonceur</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/gestUser.action"
							class="lien_deconnextion">Gerer les utilisateurs</a></li>
					</sec:authorize>
				</ul>
				</sec:authorize>
			</li>
			<li class="connecte_text dropDown">
				<div class="lien_connextion">
					<img
						src="${pageContext.request.contextPath}/themes/images/connecte.png"
						class="connecte" /> Connecté
				</div>
				<ul>
					<li><a
						href="${pageContext.request.contextPath}/logout.action"
						class="lien_deconnextion">Se déconnecter</a></li>
				</ul>
			</li>
		</ul>

	</div>
</div>
<s:url id="localeFR" action="locale">
	<s:param name="request_locale">fr</s:param>
</s:url>
<s:url id="localeEN" action="locale">
	<s:param name="request_locale">en</s:param>
</s:url>
<s:url id="localeDE" action="locale">
	<s:param name="request_locale">de</s:param>
</s:url>
<%-- <s:a href="%{localeFR}" >France</s:a> --%>
<%-- <s:a href="%{localeEN}" >English</s:a> --%>
<%-- <s:a href="%{localeDE}" >German</s:a> --%>