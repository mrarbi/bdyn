<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

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
				class="conteneur_btn_menu_4_intern ${creative.etape >= 4 ? ''  : 'disabled'}">
				<div class="text_menu">Publication</div>
				<div class="etap">(étape 4)</div>
			</a>
			<c:if test="${ creative.etape >= 4 }">
				<div class="icon_valider"></div>
			</c:if>
			<div class="box_etape_4">
				<div class="titre_publication">Flux de données</div>
				<div class="conteneur_tableau" style="width: 98%;">
					<table class="tableau_etape_3">
						<tr class="ligne_principal">
							<s:iterator var="counter" begin="1" end="%{dataSize}">
								<!-- current iteration value (1, ... 5) -->
								<td class="case_tab">${counter}</td>
							</s:iterator>
						</tr>
						<s:iterator value="dataList" var="data" status="it1" end="2">
							<tr class="ligne_secondaire">
								<s:iterator value="data" var="pair" status="it2">
									<td class="case_tab" id="flux_${pair.id}"><s:if
											test="%{#pair.value.contains('.jpg') || #pair.value.contains('.gif') || #pair.value.contains('.png')}">
											<img src="${pair.value}" height="80" width="200" />
										</s:if> <s:else>
											${pair.value}
										</s:else></td>
								</s:iterator>
							</tr>
						</s:iterator>
					</table>
				</div>
				<div class="titre_publication">Paramétrage de la banniére</div>
				<br>
				<form method="post" action="etape4v.action" id="formEtape4ID"
					novalidate="novalidate">

					<div style="background: #444; color: #fafafa; padding: 10px;">
						<span> <input type="checkbox" id="aleatoir"
							name="checkOptionParams" value="aleatoir"
							${creativeVO.typeGeo eq 'aleatoir' ? "checked" : ""}
							onclick="showDivAleatoir();" /></span> <span> Aléatoir :</span>
					</div>
					<br>
					<!-- 					Partie geolocalisation			 -->
					<div style="background: #444; color: #fafafa; padding: 10px;">
						<span> <input type="checkbox" id="geolocalisation"
							name="checkOptionParams" value="geo"
							${creativeVO.typeGeo eq 'geo' ? "checked" : ""}
							onclick="showDivGeolocalisation();" /></span> <span>Geolocalisation
							:</span> <span style="float: right;"> <a
							href="javascript:void(0);"><img
								src="<c:url value='/images/sortir.gif'/>"
								onclick="hideDivGeolocalisation();" title="supprimer une ligne" /></a>
						</span>
					</div>
					<br>
					<div id="divGeolocalisation" style="width: 794px; display: none;">
						<div class="titre_tableau">Geolocalisation banniere</div>
						<table id="divTableGeo" style="width: 100%">
							<tr>
								<th align="center">Column</th>
								<!-- 								<TH align="center">Operateur</TH> -->
								<th align="center">Valeur</th>
							</tr>
							<s:iterator status="i" value="critereParamGeo">
								<tr>
									<td align="center"><select
										name="critereParamGeo[${i.index}].column">
											<s:iterator var="counter" begin="1" end="%{dataSize}">
												<option value="${counter}"
													${critereParamGeo[i.index].column eq counter ? "selected" : ""}>${counter}</option>
											</s:iterator>
									</select></td>
									<td align="center"><select
										name="critereParamGeo[${i.index}].valeur">
											<option value="1"
												${critereParamGeo[i.index].valeur eq '1' ? "selected" : ""}>Ville</option>
											<option value="2"
												${critereParamGeo[i.index].valeur eq '2' ? "selected" : ""}>Region</option>
											<option value="3"
												${critereParamGeo[i.index].valeur eq '3' ? "selected" : ""}>Pays</option>
											<option value="4"
												${critereParamGeo[i.index].valeur eq '4' ? "selected" : ""}>Zip
												Code</option>
											<option value="5"
												${critereParamGeo[i.index].valeur eq '5' ? "selected" : ""}>GPS</option>
									</select></td>
								</tr>
							</s:iterator>

						</table>

						<div class="titre_tableau">
							<span style="float: right;"> <a href="javascript:void(0);"><img
									src="<c:url value='/images/plus.png'/>"
									onclick="addLigneTableGeo();" title="Ajouter une ligne" /></a>
								&nbsp;&nbsp;<a href="javascript:void(0);"><img
									src="<c:url value='/images/moins.png'/>"
									onclick="SuppLigneTableGeo();" title="supprimer une ligne" /></a>
							</span>
						</div>
					</div>

					<div class="conteneur_btn_suivant">
						<input type="button" class="terminer_etape_4" value="Terminer"
							onclick="postForm($('#formEtape4ID'));" />
					</div>
				</form>

			</div>
		</div>
		<div class="menu_verti_intern">

			<a href="${pageContext.request.contextPath}/etape5.action"
				class="conteneur_btn_menu_5 ${creativeVO.etape >= 5 ? ''  : 'disabled'}">
				<div class="text_menu">Publication</div>
				<div class="etap">(étape 5)</div>
			</a>
			<c:if test="${ creativeVO.etape >= 5 }">
				<div class="icon_valider"></div>
			</c:if>
		</div>
	</div>
</div>

<script type="text/javascript">
	//initialisation

	$(document).ready(function() {
		/*
		 *  Simple image gallery. Uses default settings
		 */
		$('.fancybox').fancybox({
			/*maxWidth	: 1000,
			maxHeight	: 700,*/
			padding : 20,
			fitToView : false,
			width : '315px',
			height : '265px',
			autoSize : false,
			closeClick : true,
			openEffect : 'elastic',
			closeEffect : 'elastic',
			title : 'Aperçu de votre bannière'
		});

// 		editAreaLoader.init({
// 			id : "example_1" // id of the textarea to transform		
// 			,
// 			start_highlight : true // if start with highlight
// 			,
// 			allow_resize : "none",
// 			allow_toggle : false,
// 			word_wrap : false,
// 			language : "fr",
// 			syntax : "js",
// 			is_editable : false
// 		});

		if ('${creativeVO.typeGeo}' == 'geo') {
			$("#geolocalisation").prop("checked", true);
			$("#aleatoir").prop("checked", false);
			$('#divGeolocalisation').show();

			// 			if (indexGeo == 0) {
			// 				initLigneTableGeo();
			// 			}
		} else if ('${creativeVO.typeGeo}' == 'aleatoir') {
			$("#geolocalisation").prop("checked", false);
			$("#aleatoir").prop("checked", true);
			$('#divGeolocalisation').hide();
			$('#divTableGeo tr:not(:first)').remove();

		} else {
			$("#aleatoir").prop("checked", true);
		}

	});

	function showDivPersonalisation() {
		$('#divPersonalisation').show();
		$('#divGeolocalisation').hide();

		$("#aleatoir").prop("checked", false);
		//$("#geolocalisation").prop("checked", false );
		//$("#selectionMultiple").prop("checked", false );
	}

	function hideDivPersonalisation() {
		$('#divPersonalisation').hide();
		//$('#divTablePerso tr:not(:first)').remove();
	}

	function hideDivGeolocalisation() {
		$('#divGeolocalisation').hide();
		$("#geolocalisation").prop("checked", false);
		//$('#divTableGeo tr:not(:first)').remove();
	}

	function showDivGeolocalisation() {
		//$('#divPersonalisation').hide();
		$('#divGeolocalisation').show();

		$("#aleatoir").prop("checked", false);
		initLigneTableGeo();
		//$("#geolocalisation").prop("checked", false );
		//$("#selectionMultiple").prop("checked", false );
	}

	function showDivAleatoir() {
		$('#divGeolocalisation').hide();

		//$("#aleatoir").prop("checked", false );
		$("#geolocalisation").prop("checked", false);
		$("#selectionMultiple").prop("checked", false);
		$('#divTableGeo tr:not(:first)').remove();
	}

	function showDivSelectionMultiple() {
		$('#divGeolocalisation').show();

		$("#aleatoir").prop("checked", false);
		$("#geolocalisation").prop("checked", false);
		//$("#selectionMultiple").prop("checked", false );
	}

	var persotIndex = 0;
	var geotIndex = 0;

	var indexPerso = 0;
	var indexGeo = $('#divTableGeo tr').length - 1;

	/*if($('#divTableGeo').length){
	 //indexPerso =$('#divTableGeo').length;
	 indexGeo = $('#divTableGeo').length;
	 }*/

	function addLigneTableGeo() {
		var template = '<TR> ' + '<TD align="center">' + '<select name="critereParamGeo['+indexGeo+'].column">' + '<s:iterator var="counter" begin="1" end="%{dataSize}">'
				+ '<option value="${counter}">${counter}</option>' + '</s:iterator>' + '</select>' + '</TD>' + '<TD align="center">' + '<select name="critereParamGeo['+indexGeo+'].valeur">'
				+ '<option value="1">Ville</option>' + '<option value="2">Region</option>' + '<option value="3">Pays</option>' + '<option value="4">Zip Code</option>'
				+ '<option value="5">GPS</option>' + '</select>' + '</TD>' + '</TR>';
		$('#divTableGeo').append(template);
		indexGeo++;
	}

	function initLigneTableGeo() {
		var template = '<TR> ' + '<TD align="center">' + '<select name="critereParamGeo[0].column">' + '<option value="0"></option>' + '<s:iterator var="counter" begin="1" end="%{dataSize}">'
				+ '<option value="${counter}">${counter}</option>' + '</s:iterator>' + '</select>' + '</TD>' + '<TD align="center">' + '<select name="critereParamGeo[0].valeur">'
				+ '<option value="0"></option>' + '<option value="1">Ville</option>' + '<option value="2">Region</option>' + '<option value="3">Pays</option>' + '<option value="4">Zip Code</option>'
				+ '<option value="5">GPS</option>' + '</select>' + '</TD>' + '</TR>';
		$('#divTableGeo').append(template);
		indexGeo++;
	}

	function SuppLigneTableGeo() {
		$('#divTableGeo tr:last').remove();
		indexGeo--;
	}
	
</script>