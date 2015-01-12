<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<span style="float: right;"> <a href="#"
	onclick="closeSaveTempl();"> <img
		src="${pageContext.request.contextPath}/images/ico_cancel.png"
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
	<span style="float: left;"> <b>&nbsp;${titreAction}</b>
	</span>
</div>
	<div class="conteneur_format">
		<div align="center">
			<c:if test="${not empty template.urlImage }">
				<img src="${template.urlImage}"
					style="border: 1px; border-style: dashed;" />
			</c:if>
		</div>
		<div style="display: block;" id="divFormUpload">
			<form action="chargerZipTempl.action" method="POST" name="formSave"
				id="chargerZipTempl" enctype="multipart/form-data">
				<input type="hidden" name="template.tempId" value="${template.tempId}">
				<div style="clear:both;">&nbsp;</div>
				<table style="width: 100%">
					<tr>
						<td class="tdLabel" rowspan="2">Nom :</td>
						<td><s:textfield name="template.name"
								placeholder="%{getText('Name de template')}" size="40"
								class="form-control" cssClass="champs_intern validate[required]"></s:textfield></td>
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Template Zip</td>
						<td><s:file name="tempZipFile" id="idTempFile"
								accept="application/zip, application/rar, application/7z " 
								class="form-control" cssClass="validate[required]"/></td>
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Format :</td>
						<td><s:select list="mapListFormatTemplate"
								name="template.format"
								class="form-control" cssClass="champs_intern validate[required]"/></td>
					</tr>
					<sec:authorize ifAnyGranted="Annonceur">
					<tr>
						<td class="tdLabel" rowspan="2">Image :</td>
						<td><s:file name="fileUpload" id="idfile"
								accept="image/gif, image/jpeg, image/png" /></td>
					</tr>
					</sec:authorize>
				</table>
				<div class="conteneur_btn_suivant">
					<input type="button" onclick="uploadZipTemplate();" class="terminer_etape_2" value="Charger" />
				</div>
			</form>
		</div>
		<div style="display: none;" id="divFormHtml">
			<form action="createTemplate.action" method="POST" name="formSave"
				id="saveTempl" enctype="multipart/form-data">
				<input type="hidden" name="template.tempId" value="${template.tempId}">
				<s:hidden name="template.text" id="idtempText"></s:hidden>
				<s:hidden name="template.name" id="idtempName"></s:hidden>
				<s:hidden name="template.urlImage" id="idtempUrlImage"></s:hidden>
				<table style="width: 100%;">
				<c:if test="${empty template.urlImage}">
					<tr>
						<td class="tdLabel" rowspan="2">Image :</td>
						<td><s:file name="fileUpload" id="idfile"
 								accept="image/gif, image/jpeg, image/png" /></td>
					</tr>
				</c:if>
					<tr>
						<td class="tdLabel" rowspan="2">Format :</td>
						<td><s:select list="mapListFormatTemplate"
								name="template.format" cssClass="champs_intern" /></td>
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Etat :</td>
						<td><s:select list="mapListStateTemplate"
								name="template.actif" cssClass="champs_intern" /></td>
					</tr>
					<sec:authorize ifAnyGranted="Administrateur">
						<tr>
							<td class="tdLabel" rowspan="2">Valide :</td>
							<td><s:select list="#{'1':'Oui', '0':'Non'}"
									name="template.valide" cssClass="champs_intern" /></td>
						</tr>
					</sec:authorize>
					<tr>
						<td class="tdLabel" rowspan="2">Url :</td>
						<td><s:textfield name="template.urlTemplate" readonly="true"
							size="80" cssClass="champs_intern" /></td>
					</tr>
					<tr>
						<td class="tdLabel" rowspan="2">Banniere html :</td>
						<td></td>
					</tr>
				</table>
				<textarea id="example_1" class="champs_intern"
					style="height: 400px; width: 100%;">${template.text}</textarea>
				<div class="conteneur_btn_suivant" id="divSubmit"
					style="display: none;">
					<input type="button" onclick="passerValeurText();" class="terminer_etape_2" value="Enregistrer" />
				</div>
			</form>
		</div>
	</div>

<script type="text/javascript">
	$(document).ready(function() {
		editAreaLoader.init({
			id : "example_1" // id of the textarea to transform		
			,start_highlight : true // if start with highlight
			,allow_resize : "both"
			,allow_toggle : true
			,word_wrap : true
			,language : "fr"
			,syntax : "html"
			,is_editable : true
			,toolbar : "search, go_to_line, |, undo, redo, |, select_font, |, syntax_selection, |, change_smooth_selection, highlight, reset_highlight"
			,syntax_selection_allow : "css,html,js,xml"
		});

		if ('${validFormUpload}' == '1' && '${validForm}' == '1') {
			$("#divFormHtml").show();
			$("#divFormUpload").hide();
			$("#divSubmit").show();
		}
		
// 		jQuery(document).ready(function() {
// 			// binds form submission and fields to the validation engine
// 			jQuery("#chargerZipTempl").validationEngine('attach');
// 		});
	});

	function passerValeurText() {
		//alert(editAreaLoader.getValue("example_1"));
		$("#idtempText").val(editAreaLoader.getValue("example_1"));
		postForm($("#saveTempl"));
	}

	function uploadZipTemplate() {
// 		$("#saveTemps").attr("action","chargerZipTempl.action");
		jQuery("#chargerZipTempl").validationEngine('attach');
		postForm($("#chargerZipTempl"));
// 		$("#chargerZipTempl").submit();
	}
	
// 	function uploadZipTemplateAnnonceur() {
// 		$("#saveTemps").attr("action","chargerZipTemplAnnonceur.action");
// 		postForm($("#saveTemps"));
// 	}

	
</script>