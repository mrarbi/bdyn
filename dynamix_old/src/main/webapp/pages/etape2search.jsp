<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<link rel="stylesheet" href="<c:url value='/css/owl.carousel/owl.carousel.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/owl.carousel/owl.theme.css'/>" />
<script src="<c:url value='/js/owl.carousel/owl.carousel.js'/>" charset="utf-8"></script>

	<form method="post" action="etape2v.action" id="formTemplID" novalidate="novalidate">
		<div class="format">
			<span class="text_format">
				<label>Filtrer par format : </label>
				<span class="bold">
					<s:select list="mapListFormatTemplate" id="findformat" name="formatSearch" 
						cssClass="champs_intern" onchange="searchByformats();" headerKey="" headerValue="--tous--"/>
				</span>
			</span>	
<%-- 			<span style="float: right;"  >  --%>
<%-- 				<a	href="#" onclick="afficherFormAjoutTemplate();"> <img src="${pageContext.request.contextPath}/images/new.png" title="Ajouter une template" /></a> --%>
<%-- 			</span> --%>
			</div>
			<div class="conteneur_format">
				<c:if test="${not empty templates }">
	              <div id="owl-demo" class="owl-carousel">
					<s:hidden id="tempSelect" name="tempSelect" />
						<s:iterator value="templates" var="temp" status="i">
							<div class="item">
								<div class="ligne_radio">
									<input type="radio" id="img_${i.index}" name="crea"	${tempId eq creativeVO.tempId ? 'checked' : ''}>${name}
									<label for="img_${i.index}" class="image_radio"	onclick="javascript:checkTemplate('${tempId}')"> 
											<img src="${urlImage}" class="lazyOwl" /></label>
								</div>
							</div>
						</s:iterator>
				 </div>	
				</c:if>
				<c:if test="${empty templates }">
					<p>Aucune template avec le format selectionné!</p>
				</c:if>
			</div>
		<div class="conteneur_btn_suivant">
			<c:if test="${not empty templates }">
				<input type="button" class="terminer_etape_2" value="Terminer" onclick="valider();" />
			</c:if>
		</div>
	</form>
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
function valider() {
	if ("" == $("#tempSelect").val()) {
		infoBox("Veuillez choisir une template!");
	} else {
		postForm($("#formTemplID"));
	}
}

function checkTemplate(val){
	$("#tempSelect").val(val);
}

function afficherFormAjoutTemplate() {
	$("#divAjoutTemps").show();
	$("#divTemps").hide();
}

function afficherBarTemplates() {
	$("#divAjoutTemps").hide();
	$("#divTemps").show();
}

function validerForm(){
	$("#addTemps").submit();
}

$(document).ready(function() {
	
	$("#divAjoutTemps").hide();
	$("#owl-demo").owlCarousel({
		items :4,
		lazyLoad : true,
		navigation : true
	});
});

function searchByformats() {
	var find = $("#findformat").val();
 	urls = "searchByformats.action?formatSearch=" + find;
	laoderDisplay();
	$("#divTemps").load(urls, function(){
		laoderRemove();
	});
}

</script>