<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<span style="float: right;"> <a href="#"
	onclick="closeSaveUser();"> <img
		src="${pageContext.request.contextPath}/images/ico_cancel.png"
		title="Afficher les utilisateurs" /></a>
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
	<span style="float: left;"><b>&nbsp;${titreAction}</b></span>
</div>

<form action="saveUser.action" method="POST" id="formSaveUser">
<input type="hidden" name="userVO.userId" value="${userVO.userId}">
<input type="hidden" name="userVO.password" value="${userVO.password}">
	<div class="conteneur_format">
		<table style="width: 100%">
			<tr>
				<td class="tdLabel" rowspan="2">Contact :</td>
				<td><s:textfield name="userVO.contactName" size="40"
						class="form-control" cssClass="champs_intern validate[required]"></s:textfield></td>
			</tr>
			<tr>
				<td class="tdLabel" rowspan="2">E-mail :</td>
				<td><s:textfield name="userVO.emailAddress" size="40"
						class="form-control" cssClass="champs_intern validate[required,custom[email]]"></s:textfield></td>
			</tr>
			<tr>
				<td class="tdLabel" rowspan="2">Login :</td>
				<td><s:textfield name="userVO.username" size="40"
						class="form-control" cssClass="champs_intern validate[required]"></s:textfield></td>
			</tr>
			<tr>
				<td class="tdLabel" rowspan="2">Mot de passe :</td>
				<td>
					<td><s:password name="userVO.newPassword" size="40"
						class="form-control" cssClass="champs_intern validate[required]"></s:password></td>
			</tr>
			<tr>
				<td class="tdLabel" rowspan="2">Confirmation mot de passe :</td>
				<td><s:password name="userVO.passwordConfirm" size="40"
						class="form-control" cssClass="champs_intern validate[required]"></s:password></td>
			</tr>
			<tr>
				<td class="tdLabel" rowspan="2">Profile :</td>
				<td>
<%-- 				<select name="userVO.profileId"> --%>
<%-- 				<s:iterator value="listProfiles" var="prof" status="i"> --%>
<%-- 					<option value="${prof.profileId}" >${prof.profileName}</option> --%>
<%-- 				</s:iterator> --%>
<%-- 				</select> --%>
				
				<s:select list="listProfiles" name="userVO.profileId"
 						headerKey="0" headerValue="--- choisir profile---"
 						cssClass="champs_intern" listKey="profileId"
 						listValue="profileName" value="%{userVO.profileId}"/>
						
				</td>
			</tr>
		</table>
	</div>
	<div class="conteneur_btn_suivant">
		<input type="submit" class="terminer_etape_4" onclick="validerFormUser();"
			value="Enregistrer" />
	</div>
</form>

<script type="text/javascript">

function validerFormUser(){
	jQuery("#formSaveUser").validationEngine('attach');
	$("#formSaveUser").submit();
}
</script>
