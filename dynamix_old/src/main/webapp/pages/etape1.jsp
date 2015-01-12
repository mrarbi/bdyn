<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="page_acceuil_intern">
	<a href="${pageContext.request.contextPath}/etape1.action"
		class="conteneur_btn_menu">
		<div class="text_menu">Flux de données</div>
		<div class="etap">(étape 1)</div>
	</a>
	<div class="box_etape_1">
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
		<form method="post" action="etape1v.action" id="formEtape1ID"
			enctype="multipart/form-data">
			<s:hidden name="fileUploadFileName" id="fileUploadFileName"
				value="%{creativeVO.fichier}" />
			<s:hidden name="creativeVO.typeFichier"
				value="%{creativeVO.typeFichier}" />
			<s:hidden name="creativeVO.fichier" id="creative_fichier"
				value="%{creativeVO.fichier}" />
			<s:hidden name="creativeVO.creativeId" id="creative_creativeId"
				value="%{creativeVO.creativeId}" />
			<s:hidden name="creativeVO.creationDate" id="creative_creationDate"
				value="%{creativeVO.creationDate}" />
			<s:hidden name="creativeVO.xpath" id="creative_xpath"
				value="%{creativeVO.xpath}" />
			<s:hidden name="creativeVO.matching" id="creative_matching"
				value="%{creativeVO.matching}" />
			<s:hidden name="creativeVO.tempId" id="creative_tempId"
				value="%{creativeVO.tempId}" />

			<table style="width: 100%; padding: 10px;">
				<tr>
					<td width="20%"><input type="radio" name="urlOrFile"
						value="url" id="radio_url" onclick="javascript:disableFileUp()" />
						URL : <br>&nbsp;</td>
					<td><input type="text" name="creativeVO.url" size="60"
						maxlength="255" class="champs_intern" placeholder="http://"
						id="creative_url" value="${creativeVO.url}" /> <br> <span
						class="par_defaut" id="withAuth"> <input type="checkbox"
							id="authUrl" name="authUrl" onchange="toggleZoneAuth();" /> <label
							for="authUrl">Avec authentification</label>
					</span></td>
				</tr>
				<tr>
					<td></td>
					<td id="zone_auth">
						<table>
							<tr>
								<td>login:</td>
								<td><input type="text" name="creativeVO.loginUrl"
									value="${creativeVO.loginUrl}" size="16" /></td>
							</tr>
							<tr>
								<td>mot de passe :</td>
								<td><input type="password" name="creativeVO.passwdUrl"
									value="${creativeVO.passwdUrl}" size="16" /></td>
							</tr>
						</table> <br> <%-- 									login : <input type="text" name="creativeVO.loginUrl" value="${creativeVO.loginUrl}" size="16"/><br> --%>
						<%-- 	                    			mot de passe : <input type="password" name="creativeVO.passwdUrl" value="${creativeVO.passwdUrl}" size="16"/> --%>
						<!-- 	                    			<br>&nbsp; -->
					</td>
				</tr>
				<tr>
					<td><input type="radio" name="urlOrFile" value="file"
						id="radio_file" onclick="javascript:disableUrl()" />Import : <br>&nbsp;
					</td>
					<td><input type="file" name="fileUpload" accept=".csv,.xml"
						id="fileUpload" /> &nbsp;|&nbsp;<span id="fichierCharge">Fichier
							chargé : <b>${creativeVO.fichier}</b>
					</span> <br>&nbsp;</td>
				</tr>
				<tr>
					<td>Séparateur CSV :</td>
					<td><input type="text" name="creativeVO.separateur"
						class="champs_intern" size="4" value="${creativeVO.separateur}" />
						<span class="par_defaut">Par défaut ";"</span>
				</tr>
				<tr>
					<td colspan="2"><input type="button" class="btn_suivant"
						value="Terminer" onclick="postForm($('#formEtape1ID'));" /></td>
				</tr>

			</table>

			<!-- 	                	<div > -->
			<!-- 	                    	<div class="ligne_text"> -->
			<!-- 	                    	</div> -->
			<!-- 	                        <div class="ligne_text"></div> -->
			<!-- 	                        <div class="ligne_text" id="lab_sep" style="display:none"></div> -->
			<!-- 	                    </div> -->

			<!-- 						<div class="parti_champs"> -->
			<!-- 							<div class="ligne_champs"> -->

			<!-- 								<br> -->

			<!-- 							</div> -->
			<!-- 							<div class="ligne_champs"> -->

			<!-- 							</div> -->
			<!-- 							<div class="ligne_champs" id="txt_sep" style="display:none"> -->
			<!-- 								<input type="text" name="creativeVO.separateur" class="champs_intern" -->
			<%-- 									size="4" value="${creativeVO.separateur}" /> <span --%>
			<%--  									class="par_defaut">Par défaut ";"</span> --%>
			<!-- 							</div> -->
			<!-- 							<div class="ligne_champs"> -->
			<!-- 								<input type="submit" class="btn_suivant" value="Terminer" /> -->
			<!-- 							</div> -->
			<!-- 						</div> -->

		</form>
	</div>

	<div class="menu_verti_intern">
		<a href="${pageContext.request.contextPath}/etape2.action"
			class="conteneur_btn_menu_2 ${creativeVO.etape >= 2 ? ''  : 'disabled'}">
			<div class="text_menu">Choix de template</div>
			<div class="etap">(étape 2)</div>
		</a>
		<c:if test="${creativeVO.etape >= 2 }">
			<div class="icon_valider"></div>
		</c:if>

		<a href="${pageContext.request.contextPath}/etape3.action"
			class="conteneur_btn_menu_3 ${creativeVO.etape >= 3 ? ''  : 'disabled'}">
			<div class="text_menu">Matching</div>
			<div class="etap">(étape 3)</div>
		</a>
		<c:if test="${creativeVO.etape >= 3 }">
			<div class="icon_valider"></div>
		</c:if>

		<a href="${pageContext.request.contextPath}/etape4.action"
			class="conteneur_btn_menu_4 ${creativeVO.etape >= 4 ? ''  : 'disabled'}">
			<div class="text_menu">Paramétrage</div>
			<div class="etap">(étape 4)</div>
		</a>
		<c:if test="${creativeVO.etape >= 4 }">
			<div class="icon_valider"></div>
		</c:if>


		<a href="${pageContext.request.contextPath}/etape5.action"
			class="conteneur_btn_menu_5 ${creativeVO.etape eq 5 ? ''  : 'disabled'}">
			<div class="text_menu">Publication</div>
			<div class="etap">(étape 5)</div>
		</a>
		<c:if test="${ creativeVO.etape >= 5 }">
			<div class="icon_valider"></div>
		</c:if>

	</div>

</div>

<script type="text/javascript">
	$(function() {
		$("#zone_auth").hide();
		manageUrlFileZone();
	});

	function manageUrlFileZone() {
		if ("" != "${creativeVO.fichier}") {
			$("#radio_url").attr("checked", "");
			$("#radio_file").attr("checked", "checked");
			disableUrl();
		} else if ("" != "${creativeVO.url}") {
			$("#radio_file").attr("checked", "");
			$("#radio_url").attr("checked", "checked");
			disableFileUp();
		} else {
			$("#radio_file").attr("checked", "");
			$("#radio_url").attr("checked", "checked");
			disableFileUp();
		}
	}

	function disableFileUp() {
		$("#fileUpload").attr("disabled", "true");
		// 	$("#fichierCharge").html("");
		// 	$("#fileUploadFileName").attr("value","");
		// 	$("#creative_fichier").attr("value","");
		$("#creative_url").removeAttr("disabled");
		$("#withAuth").show();
	}

	function disableUrl() {
		$("#creative_url").attr("disabled", "true");
		$("#fileUpload").removeAttr("disabled");
		$("#withAuth").hide();
	}

	function hideSeparator(file) {
		var fileUp = $(file).val();
		var type = fileUp.substr(fileUp.lastIndexOf("."));
		if (type == ".csv") {
			$("#lab_sep").attr("style", "display:block");
			$("#txt_sep").attr("style", "display:block");
		} else {
			$("#lab_sep").attr("style", "display:none");
			$("#txt_sep").attr("style", "display:none");
		}
	}

	function toggleZoneAuth() {
		$("#zone_auth").toggle();
	}
</script>
