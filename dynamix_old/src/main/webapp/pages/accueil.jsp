<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="js/html2canvas.js"></script>

<c:if test="${not empty listCreas }">
	<div class="page_acceuil_corps">
</c:if>
<c:if test="${empty listCreas }">
	<div class="page_acceuil_corps_img">
</c:if>
<div class="menu_verti">
	<div id="divTable">
		<a href="${pageContext.request.contextPath}/etape1.action"
			class="conteneur_btn_menu_1 ">
			<div class="text_menu">Flux de données</div>
			<div class="etap">(étape 1)</div>
		</a>
		<div id="valid_1"></div>
		
		<a href="${pageContext.request.contextPath}/etape2.action"
			class="conteneur_btn_menu_2 ${creativeVO.etape >= 2 ? '' : 'disabled'}">
			<div class="text_menu">Choix de template</div>
			<div class="etap">(étape 2)</div>
		</a>
		<div id="valid_2"></div>
		
		<a href="${pageContext.request.contextPath}/etape3.action"
			class="conteneur_btn_menu_3 ${creativeVO.etape >= 3 ? '' : 'disabled'}">
			<div class="text_menu">Matching</div>
			<div class="etap">(étape 3)</div>
		</a>
		<div id="valid_3"></div>
		
		<a href="${pageContext.request.contextPath}/etape4.action"
			class="conteneur_btn_menu_4 ${creativeVO.etape >= 4 ? '' : 'disabled'}">
			<div class="text_menu">Paramétrage</div>
			<div class="etap">(étape 4)</div>
		</a>
		<div id="valid_4"></div>
		
		<a href="${pageContext.request.contextPath}/etape5.action"
			class="conteneur_btn_menu_5 ${creativeVO.etape >= 5 ? '' : 'disabled'}">
			<div class="text_menu">Publication</div>
			<div class="etap">(étape 5)</div>
		</a>
		<div id="valid_5"></div>
		
	</div>
</div>
<c:if test="${not empty listCreas }">
	<div class="menu_table">
	<form id="accueilForm" action="suppCrea.action">
		<table id="TableCreas" class="display dataTable">
			<thead>
				<tr>
					<th>Sel</th>
					<th>Id</th>
					<th>Etape</th>
					<th>Fichier</th>
<!-- 					<th>Type</th> -->
					<th>Status</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listCreas" status="i" var="crea">
					<tr onclick="check('${crea.creativeId}');loadEtatsCreas('${crea.creativeId}','${crea.etape}');" id="tr_${crea.creativeId}">
						<td align="center">
						<input type="radio" name="idCrea"
							value="${crea.creativeId}" id="crea_${crea.creativeId}"
							onclick="check('${crea.creativeId}');loadEtatsCreas('${crea.creativeId}','${crea.etape}');" /></td>
						<td align="center">${crea.creativeId}</td>
						<td align="center">
							<s:if test="%{#crea.etape == 1}">
								<img style="vertical-align: middle; width: 26px;" src="<c:url value='/images/1.png'/>"/>
							</s:if>
							<s:if test="%{#crea.etape == 2}">
								<img style="vertical-align: middle;width: 26px;" src="<c:url value='/images/2.png'/>"/>
							</s:if>
							<s:if test="%{#crea.etape == 3}">
								<img style="vertical-align: middle;width: 26px;" src="<c:url value='/images/3.png'/>"/>
							</s:if>
							<s:if test="%{#crea.etape == 4}">
								<img style="vertical-align: middle;width: 26px;" src="<c:url value='/images/4.png'/>"/>
							</s:if>
							<s:if test="%{#crea.etape == 5}">
								<img style="vertical-align: middle;width: 26px;" src="<c:url value='/images/5.png'/>"/>
							</s:if>
						</td>	
						<s:if test="%{#crea.url != null && #crea.url != ''}">
							<td title="${crea.url}"><c:if
									test="${fn:length(crea.url) > 30}">
											${fn:substring(crea.url, 0, 30)}...
										</c:if> <c:if test="${fn:length(crea.url) <= 30}">
											${crea.url}
										</c:if></td>
						</s:if>
						<s:else>
							<td title="${crea.fichier}">${crea.fichier}</td>
						</s:else>
<%-- 						<td align="center">${crea.typeFichier}</td> --%>
						<td align="center"><s:if test="%{actif == true}">
								<img style="vertical-align: middle;"
									src="<c:url value='/images/actif.png'/>" title="Actif" />
							</s:if> <s:else>
								<img style="vertical-align: middle;"
									src="<c:url value='/images/inactif.png'/>" title="Inactif" />
							</s:else></td>
						<td align="center">
							<s:a href="javascript:void(0);" onclick="supprimCrea();"><img style="width: 18px; vertical-align: middle;"
								src="<c:url value='/images/supp.png'/>" title="Supprimer"/>
							</s:a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</form>
	</div>
</c:if>
</div>
<div id="toImage"></div>
<script type="text/javascript">
$(document).ready(function() {
// 	transformDataTables(10);

	var capture = {};
	$('#toImage').append('<b>Hello World!</b> Please goto <a title=\"Goto Google\" href=\"http://www.google.com\">Google</a>.');
	var target = $('#toImage');
	html2canvas(target, {
		onrendered: function(canvas) {
			capture.img = canvas.toDataURL( "image/png" );
			capture.data = { 'image' : capture.img };
			$.ajax({
				url: "captureImage.action",
				data: capture.data,
				type: 'post',
				success: function( result ) {
					console.log( result );
				}
			})
			.always(function() {
				$('#toImage').hide();
		  	});
		}
	});
});

function loadEtatsCreas(id, etape) {
	laoderDisplay();
	urls = "loadEtatsCreasById.action?idCrea=" + id;
	$.ajax({
		url : urls,
	});
	
  	if (etape == "1") {
  		$(".conteneur_btn_menu_2").removeClass("disabled");
  		$(".conteneur_btn_menu_3").addClass("disabled");
  		$(".conteneur_btn_menu_4").addClass("disabled");
  		$(".conteneur_btn_menu_5").addClass("disabled");
  		$("#valid_1").addClass("icon_valider");
  		$("#valid_2").removeClass("icon_valider");
  		$("#valid_3").removeClass("icon_valider");
  		$("#valid_4").removeClass("icon_valider");
  		$("#valid_5").removeClass("icon_valider");
  	} else if (etape == "2") {
  		$(".conteneur_btn_menu_2").removeClass("disabled");
  		$("#valid_1").addClass("icon_valider");
  		$("#valid_2").addClass("icon_valider");
  		$(".conteneur_btn_menu_3").removeClass("disabled");
  		$(".conteneur_btn_menu_4").addClass("disabled");
  		$(".conteneur_btn_menu_5").addClass("disabled");
  		$("#valid_3").removeClass("icon_valider");
  		$("#valid_4").removeClass("icon_valider");
  		$("#valid_5").removeClass("icon_valider");
  	} else if (etape == "3") {
  		$(".conteneur_btn_menu_2").removeClass("disabled");
  		$("#valid_1").addClass("icon_valider");
  		$("#valid_2").addClass("icon_valider");
  		$(".conteneur_btn_menu_3").removeClass("disabled");
  		$("#valid_3").addClass("icon_valider");
  		$(".conteneur_btn_menu_4").removeClass("disabled");
  		$(".conteneur_btn_menu_5").addClass("disabled");
  		$("#valid_4").removeClass("icon_valider");
  		$("#valid_5").removeClass("icon_valider");
  	} else if (etape == "4") {
  		$(".conteneur_btn_menu_2").removeClass("disabled");
  		$("#valid_1").addClass("icon_valider");
  		$("#valid_2").addClass("icon_valider");
  		$(".conteneur_btn_menu_3").removeClass("disabled");
  		$("#valid_3").addClass("icon_valider");
  		$(".conteneur_btn_menu_4").removeClass("disabled");
  		$("#valid_4").addClass("icon_valider");
  		$(".conteneur_btn_menu_5").removeClass("disabled");
  		$("#valid_5").removeClass("icon_valider");
  	} else if (etape == "5") {
  		$(".conteneur_btn_menu_2").removeClass("disabled");
  		$("#valid_1").addClass("icon_valider");
  		$("#valid_2").addClass("icon_valider");
  		$(".conteneur_btn_menu_3").removeClass("disabled");
  		$("#valid_3").addClass("icon_valider");
  		$(".conteneur_btn_menu_4").removeClass("disabled");
  		$("#valid_4").addClass("icon_valider");
  		$(".conteneur_btn_menu_5").removeClass("disabled");
  		$("#valid_5").addClass("icon_valider");
  	}
  	laoderRemove();
}

function check(id) {
	var checkBoxe = $("#crea_" + id);
    checkBoxe.prop("checked", "checked");
    $("#TableCreas tr").removeClass("tr_crea_selected");
    $("#tr_"+id).addClass("tr_crea_selected");
}

function supprimCrea() {
	confirmBox("Etes-vous sur de vouloir supprimer la création sélectionnée ?", $("#accueilForm"), "suppCrea.action");
}
</script>
