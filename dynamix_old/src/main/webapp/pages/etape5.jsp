<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<!-- OSX Style CSS files -->
<!-- <link type='text/css' href='css/osx/osx.css' rel='stylesheet' media='screen' /> -->
<!-- Load JavaScript files -->
<%-- <script type='text/javascript' src='js/osx/jquery.simplemodal.js'></script> --%>
<%-- <script type='text/javascript' src='js/osx/osx.js'></script> --%>



<script type="text/javascript">
	//initialisation
	
	$(document).ready(function() {
		/*
		 *  Simple image gallery. Uses default settings
		 */

		$('.fancybox').fancybox({
			/*maxWidth	: 1000,
			maxHeight	: 700,*/
			padding		: 20,
			fitToView	: false,
			width		: '315px',
			height		: '265px',
			autoSize	: false,
			closeClick	: true,
			openEffect	: 'elastic',
			closeEffect	: 'elastic',
			title		: 'Aperçu de votre bannière'
		});
		
		<s:if test="%{creativeVO.actif != 0}">
		editAreaLoader.init({
			id: "example_1"	// id of the textarea to transform		
			,start_highlight: true	// if start with highlight
			,allow_resize: "none"
			,allow_toggle: false
			,word_wrap: false
			,language: "fr"
			,syntax: "js"
			,is_editable:false
		});
		</s:if>
		
	});
</script>

<div class="page_acceuil_intern">


	<div class="menu_verti_intern_haut">

		<div class="ligne_menu">
			<a href="${pageContext.request.contextPath}/etape1.action"
				class="conteneur_btn_menu_1">
				<div class="text_menu">Flux de données</div>
				<div class="etap">(étape 1)</div>
			</a>
			<div class="icon_valider"></div>
		</div>

		<div class="ligne_menu">
			<a href="${pageContext.request.contextPath}/etape2.action"
				class="conteneur_btn_menu_2">
				<div class="text_menu">Choix de template</div>
				<div class="etap">(étape 2)</div>
			</a>
			<div class="icon_valider"></div>
		</div>
		<div class="ligne_menu">
			<a href="${pageContext.request.contextPath}/etape3.action"
				class="conteneur_btn_menu_3">
				<div class="text_menu">Matching</div>
				<div class="etap">(étape 3)</div>
			</a>
			<div class="icon_valider"></div>
		</div>

		<div class="ligne_menu">
			<a href="${pageContext.request.contextPath}/etape4.action"
			class="conteneur_btn_menu_4">
				<div class="text_menu">Paramétrage</div>
				<div class="etap">(étape 4)</div>
			</a>
			<div class="icon_valider"></div>
		</div>

	</div>


	<div class="ligne_menu">
		<a href="${pageContext.request.contextPath}/etape5.action"
			class="conteneur_btn_menu_5_intern ">
			<div class="text_menu">Publication</div>
			<div class="etap">(étape 5)</div>
		</a>
	</div>
	<div class="box_etape_5">
		<s:if test="%{creativeVO.actif == 0}">
			<div class="titre_publication">
				<span style="float: left;">
				</span>
				<span style="text-decoration: underline;float: right;">
					<a class="fancybox fancybox.iframe apercu" href="${pageContext.request.contextPath}/preview.action"><img style="width: 27px;margin-right: 10px;" src="${pageContext.request.contextPath}/images/preview.png" alt="Aperçu">Aperçu</a>
				</span>
			</div>
			<div class="titre_publication">
				<form action="activateBan.action" method="post" id="formEtape5ID" >
					<input type="hidden" name="idCrea" value="${idCrea}" />
					Pour publier la bannière il faut qu'elle soit active!
					<br>
					Pour activer la bannière cliquer sur le bouton activer si-dessous
					<br>
					<div class="conteneur_btn_suivant">
						<input type="button" class="terminer_etape_5" value="Activer" onclick="postForm($('#formEtape5ID'));"/>
					</div>
				</form>
			</div>
		</s:if>
		<s:else>
			<div class="titre_publication">
				<span style="float: left;">
					Script
				</span>
				<span style="text-decoration: underline;float: right;">
					<s:if test="%{script != null}">
						<a class="fancybox fancybox.iframe" href="${pageContext.request.contextPath}/preview.action"><img style="width: 27px;margin-right: 10px;" src="${pageContext.request.contextPath}/images/preview.png" alt="Aperçu"></a>
					</s:if>
				</span>
			</div>
			<textarea id="example_1" style="height: 200px; width: 100%;" name="script">${script}</textarea>
		</s:else>
	</div>

</div>
