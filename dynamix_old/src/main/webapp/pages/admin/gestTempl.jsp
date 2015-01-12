<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="page_acceuil_intern">

	<a href="#"
		class="conteneur_btn_menu_2_intern menu_verti">
		<div class="text_menu">Gestion des templates</div>
	</a>
	<div class="box_etape_2" id="divTempl">
		<div class="format">
			<span style="float: right;"> <a href="#"
				onclick="afficherFormAjoutTemplate();"> <img
					src="${pageContext.request.contextPath}/images/new.png"
					title="Ajouter une template" /></a>
			</span>
		</div>
		<form name="formTable" id="formTable" action="deleteTemplate.action" method="POST">
			<div class="conteneur_format">
				<c:if test="${not empty templates }">
					<div class="menu_table" style="margin-top: 4px; width: 97%">
						<table id="TableTempl" class="dataTable display">
							<thead>
								<tr>
									<th></th>
									<th></th>
									<th>Name</th>
									<th>Format</th>
									<th>Type</th>
									<th>Date</th>
									<th>Status</th>
									<sec:authorize ifNotGranted="Annonceur">
									<th></th>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty templates }">
									<s:iterator value="templates" var="temp" status="i">
										<tr onclick="checkLigne('img_${i.index}');">
											<td align="center">
												<s:set var="idTempalte">
													<s:property value="tempId" />
												</s:set> 
												<input type="checkbox" name="checkboxTemplate[${i.index}]"	onclick="checkLigne('img_${i.index}');" id="img_${i.index}"
													value="${tempId}" ${tempId eq idTemp ? 'checked' : ''} />
											</td>
											
											<td align="center">
												<label for="img_${i.index}"	class="image_radio"	onclick="checkLigne('img_${i.index}');">
<%-- 													<a class="fancybox fancybox.iframe"	href="${pageContext.request.contextPath}/rest/previewCrea/${tempId}"> --%>
													<a class="fancybox fancybox.iframe"	href="${urlTemplate}">
														<img src="${urlImage}"  class="lazyOwl" onclick="checkLigne('img_${i.index}');" height="80" width="80" /></a>
												</label>
											</td>
											
											<td align="center">${name}</td>
											<td align="center">${format}</td>
											<td align="center">${type}</td>
											<td align="center">${creationDate}</td>
											<td align="center"><s:if test="%{actif == true}">
													<img style="vertical-align: middle;"
														src="<c:url value='/images/actif.png'/>" title="Actif" />
												</s:if> <s:else>
													<img style="vertical-align: middle;"
														src="<c:url value='/images/inactif.png'/>" title="Inactif" />
												</s:else></td>
											<sec:authorize ifNotGranted="Annonceur">
											<td align="center"><a href="javascript:void(0);"
												onclick="javascript:updateTempById('${tempId}')"><img
													style="height: 20px; vertical-align: middle;"
													src="<c:url value='/images/edit.png'/>" title="Modifier" />
											</a></td>
											</sec:authorize>
										</tr>
									</s:iterator>
								</c:if>
							</tbody>
						</table>
					</div>
				</c:if>
				<c:if test="${empty templates }">
					<p>Aucune template a afficher.</p>
					<p>Cliquer sur l'icone à droite pour ajouter des templates.</p>
				</c:if>
			</div>
			<div class="conteneur_btn_suivant">
				<c:if test="${not empty templates }">
					<input type="button" class="terminer_etape_2" onclick="deleteTemplates();" value="Supprimer la selection" />
				</c:if>
			</div>
		</form>
	</div>
	<div class="box_etape_2" id="divAjoutTempl" style="display: none;">
		<div id="divChampsTempl">
			<jsp:include page="saveTemp.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">

	function afficherFormAjoutTemplate() {
		laoderDisplay();
		$("#divChampsTempl").load("prepareAjout.action",function(){
			laoderRemove();
			showSaveTempl();
		});
	}

	function closeSaveTempl() {
		$("#divAjoutTempl").hide();
		$("#divTempl").show();
	}
	
	function showSaveTempl() {
		$("#divAjoutTempl").show();
    	$("#divTempl").hide();
	}
	
	$(document).ready(function() {
	    /*$('.dataTable').dataTable({
			"iDisplayLength": 5
	    });*/
// 	    transformDataTables(5);
	    if('${validForm}' == '1') {
	    	showSaveTempl();
	    }
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
		
	});

	function updateTempById(id) {
		laoderDisplay();
		$("#divChampsTempl").load("findTemplate.action?idFindTemplate="+id,function(){
			laoderRemove();
			showSaveTempl();
		});
	}

	function deleteTemplates() {
		confirmBox("Voulez vous vraiment supprimer les templates selectionnées ?", $("#formTable"), "deleteTemplate.action");
	} 

</script>