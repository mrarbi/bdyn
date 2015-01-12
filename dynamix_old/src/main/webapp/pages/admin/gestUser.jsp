<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="page_acceuil_intern">

	<a href="#" class="conteneur_btn_menu_4_intern menu_verti">
		<div class="text_menu">Gestion des utilisateurs</div>
	</a>

	<div class="box_etape_4" id="divUsers">
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
			<span style="float: right;"> <a href="#"
				onclick="afficherFormUser('newUser.action');"> <img
					src="${pageContext.request.contextPath}/images/new.png"
					title="Ajouter un utilisateur" /></a>
			</span>
		</div>
		<form name="formTable" id="formDelUsers" action="deleteUsers.action" method="POST">
			<div class="conteneur_format" id="divBans">
				<c:if test="${not empty users}">
					<div class="menu_table" style="margin-top: 4px; width: 97%">
						<table id="TableUsers" class="display dataTable">
							<thead>
								<tr>
									<th>Sel</th>
									<th>Login</th>
									<th>Contact</th>
									<th>E-mail</th>
									<th>Date création</th>
									<th>Profile</th>
									<th>Statut</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="users" var="user" status="i">
									<tr onclick="checkLigne('usr_${i.index}');">
										<td align="center"><input type="checkbox"
											name="checkboxUsers[${i.index}]"
											onclick="checkLigne('usr_${i.index}');" id="usr_${i.index}"
											value="${userId}" /></td>
										<td align="center">${username}</td>
										<td align="center">${contactName}</td>
										<td align="center">${emailAddress}</td>
										<td align="center">${dateCreated}</td>
										<td align="center">${profileName}</td>
										<td align="center"><s:if test="%{active == true}">
												<img style="vertical-align: middle;"
													src="<c:url value='/images/actif.png'/>" title="Actif" />
											</s:if> <s:else>
												<img style="vertical-align: middle;"
													src="<c:url value='/images/inactif.png'/>" title="Inactif" />
											</s:else></td>
										<td align="center"><a href="javascript:void(0);"
											onclick="javascript:afficherFormUser('findUser.action?userIdForUpdate=${userId}')"><img
												style="height: 20px; vertical-align: middle;"
												src="<c:url value='/images/edit.png'/>" title="Modifier" />
										</a></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>

					</div>
				</c:if>
			</div>
			<div class="conteneur_btn_suivant">
				<input type="button" class="terminer_etape_4"
					onclick="deleteUsers();" value="Supprimer la selection" />
			</div>
		</form>
	</div>
	<div class="box_etape_4" id="divAjoutUser" style="display:none">
		<jsp:include page="saveUser.jsp"></jsp:include>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
// 		transformDataTables(10);
	 	if ('${validForm}' == '1') {
	 		showSaveUser();
	    }
	});
	
	function afficherFormUser(url) {
		laoderDisplay();
		$("#divAjoutUser").load(url,function(){
			laoderRemove();
			showSaveUser();
		});
	}
	
	function showSaveUser() {
		$("#divAjoutUser").show();
    	$("#divUsers").hide();
	}
	
	function closeSaveUser() {
		$("#divAjoutUser").hide();
		$("#divUsers").show();
	}
	
	function deleteUsers() {
		confirmBox("La suppression des utilisateurs sélectionnés supprimera toutes leurs créations!</br> Voulez vous continuez ?", $("#formDelUsers"), "deleteUsers.action");
	} 
</script>
