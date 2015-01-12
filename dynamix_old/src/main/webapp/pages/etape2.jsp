<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<link rel="stylesheet" href="<c:url value='/css/owl.carousel/owl.carousel.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/owl.carousel/owl.theme.css'/>" />
<script src="<c:url value='/js/owl.carousel/owl.carousel.js'/>" charset="utf-8"></script>

<div class="page_acceuil_intern">
	<div class="menu_verti_intern_haut">
		<a href="${pageContext.request.contextPath}/etape1.action"
			class="conteneur_btn_menu_1">
			<div class="text_menu">Flux de données</div>
			<div class="etap">(étape 1)</div>
		</a>
		<div class="icon_valider"></div>
<%-- 		<span class='line' style="float: left;">  --%>
<%-- 			<s:fielderror cssClass="msgError"> --%>
<%-- 				<s:param value="%{'TemplateError'}" /> --%>
<%-- 			</s:fielderror> --%>
<%-- 		</span> --%>
	</div>
	<a href="${pageContext.request.contextPath}/etape2.action"
		class="conteneur_btn_menu_2_intern">
		<div class="text_menu">Choix de template</div>
		<div class="etap">(étape 2)</div>
	</a>
	
	
	<div class="box_etape_2" id="divTemps">
		<jsp:include page="etape2search.jsp"></jsp:include>
	</div>
	<div class="box_etape_2" id="divAjoutTemps" style="${validForm == 1 ? ''  : 'display:block;'}">
		
		<span style="float: right;">
			<a href="#" onclick="afficherBarTemplates();"> <img src="${pageContext.request.contextPath}/images/ico_cancel.png"
				title="Afficher les templates" /></a>
		</span>
		
		<s:if test="hasActionErrors()">
			<div class="errors">
				<s:actionerror />
			</div>
		</s:if>
		<s:if test="hasActionMessages()">
			<div class="infos">
				<s:actionmessage />
			</div>
		</s:if>
		
		<div class="format">
			<span style="float: left;">
				&nbsp;Ajout d'une nouvelle template
			</span>
		</div>
		
		<form method="post" action="saveTemplate.action" method="POST"
			name="myform" id="addTemps" novalidate="novalidate" enctype="multipart/form-data">
			<div class="conteneur_format">
				<table style="width: 100%">
					<tr>
						<td class="tdLabel" rowspan="2">Name : </td>
						<td><s:textfield name="template.name"
									placeholder="%{getText('Name de template')}" size="40"
									cssClass="champs_intern"></s:textfield></td>
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Url image</td>
						<td><s:file name="fileUpload" id="idfile"
										accept="image/gif, image/jpeg, image/png" /> 
		           		</td>			
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Format : </td>
						<td><s:select list="mapListFormatTemplate"
									name="template.format" cssClass="champs_intern" /></td>
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Etat : </td>
						<td><s:select list="mapListStateTemplate"
									name="template.actif" cssClass="champs_intern" /></td>
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Banniere html : </td>
						<td></td>
					</tr>
				</table>
				<textarea id="example_1" name="texEditor" class="champs_intern"
								style="height: 400px; width: 100%;">${template.text}</textarea>
				<s:hidden name="template.text" id="idtempText"></s:hidden>
			</div>
			<div class="conteneur_btn_suivant">
				<input type="button" class="terminer_etape_2"
					onclick="validerForm();" value="Ajouter" />
			</div>
		</form>
	</div>
	<!--<div class="test"></div>-->
	<div class="menu_verti_intern">
		<a href="${pageContext.request.contextPath}/etape3.action" class="conteneur_btn_menu_3 ${creativeVO.etape >= 3 ? ''  : 'disabled'}">
			<div class="text_menu">Matching</div>
			<div class="etap">(étape 3)</div>
		</a> 
		<c:if test="${ creativeVO.etape >= 3 }">
			<div class="icon_valider"></div>
		</c:if>
		<a href="${pageContext.request.contextPath}/etape4.action" class="conteneur_btn_menu_4 ${creativeVO.etape >= 4 ? ''  : 'disabled'}">
			<div class="text_menu">Paramétrage</div>
			<div class="etap">(étape 4)</div>
		</a>
		<c:if test="${ creativeVO.etape >= 4 }">
			<div class="icon_valider"></div>
		</c:if>
		<a href="${pageContext.request.contextPath}/etape5.action" class="conteneur_btn_menu_5 ${creativeVO.etape >= 5 ? ''  : 'disabled'}">
			<div class="text_menu">Publication</div>
			<div class="etap">(étape 5)</div>
		</a>
		<c:if test="${ creativeVO.etape >= 5 }">
			<div class="icon_valider"></div>
		</c:if>
	</div>
</div>
<style>
#owl-demo .item{
    margin: 3px;
}
#owl-demo .item img{
    display: block;
    width: 100%;
    height: auto;
}

</style>
<script type="text/javascript">
function afficherFormAjoutTemplate() {
	$("#divAjoutTemps").show();
	$("#divTemps").hide();
}

function afficherBarTemplates() {
	$("#divAjoutTemps").hide();
	$("#divTemps").show();
}

function validerForm(){
	$("#idtempText").val(editAreaLoader.getValue("example_1"));
	$("#addTemps").submit();
}

$(document).ready(function() {
	$("#divAjoutTemps").hide();
	$("#owl-demo").owlCarousel({
		items :4,
		lazyLoad : true,
		navigation : true
	});
	
	editAreaLoader.init({
		id: "example_1"	// id of the textarea to transform		
		,start_highlight: true	// if start with highlight
		,allow_resize: "none"
		,allow_toggle: false
		,word_wrap: false
		,language: "fr"
		,syntax: "html"
		,EA_load_callback: "editAreaLoaded"
		,show_line_colors: true
		,is_editable:true
        ,min_height: 350
       
	/*	start_highlight: true
		,allow_toggle: false
		,language: "fr"
		,syntax: "html"	
		,toolbar: "search, go_to_line, |, undo, redo, |, select_font, |, syntax_selection, |, change_smooth_selection, highlight, reset_highlight, |, help"
		,syntax_selection_allow: "css,html,js,php,python,vb,xml,c,cpp,sql,basic,pas,brainfuck"
		,EA_load_callback: "editAreaLoaded"
		,show_line_colors: true
		,is_editable:true
		,allow_resize: "no"   */
	});
	
	//document.getElementsByName("template.text").value = $("#example_1").val();
	if($("#validForm").val() == 1){
		$("#divTemps").hide();
		$("#divAjoutTemps").show();
	}
});

</script>