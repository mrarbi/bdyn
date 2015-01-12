<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



<div class="box_etape_2" id="divBans">
		<form method="post" action="etape2v.action" id="formID" novalidate="novalidate">
		<div class="format">
			<span class="text_format">Format : <select class="select_format">
					<option>300 x 250</option>
					<option>160 x 600</option>
					<option>728 x 90</option>
				</select>
			</span>	
			<span style="float: right;"  > 
				<a	href="#"  onclick="afficherFormAjoutTemplate();"> <img src="${pageContext.request.contextPath}/images/new.png" title="Ajouter une template" /></a>
			</span>
		</div>

		<div class="conteneur_format" id="divBans">
			<s:hidden id="tempSelect" name="tempSelect" />
			<s:iterator value="templates" var="temp" status="i">
				<div class="conteneur_crea">
					<div class="ligne_radio">
						<input type="radio" id="img_${i.index}" name="crea" ${i.index eq 0 ? 'checked' : ''}>
					</div>
					<label for="img_${i.index}" class="image_radio" onclick="javascript:checkCrea('${tempId}')"><img
						src="${urlImage}" class="image_intern" /></label>
				</div>
			</s:iterator>
		</div>
		<div class="conteneur_btn_suivant">
			<input type="submit" class="terminer_etape_2" value="Terminer" />
		</div>
		</form>
	</div>


	<div class="box_etape_2" id="divAjoutBans"  style="display: none;">
		<form method="post" action="saveTemplate.action" method="POST"
			name="myform" novalidate="novalidate" enctype="multipart/form-data">
			<div class="format">
				<span style="float: right;">
				<a href="#" onclick="afficherBarTemplates();"> <img src="${pageContext.request.contextPath}/images/ico_cancel.png"
					title="Afficher les templates" /></a>
				</span>
				<span style="float: left;">
					&nbsp;Ajout d'une nouvelle template
				</span>
			</div>
			<div class="info_sup" style="display: block;">
					<ul class="info_sup_part_g">
						<li><s:file name="fileUpload" label="Url Image" id="idfile"
								accept="image/gif, image/jpeg, image/png" /> <br>
						<br></li>
						<li><label>height x width :</label>
						<s:textfield name="template.height" placeholder="%{getText('height en px')}" size="10"></s:textfield> x <s:textfield name="template.width" placeholder="%{getText('width en px')}" size="10"></s:textfield><br> <br> 
						</li>
						<li><label>Actif: </label><span class="bold"><s:select list="mapListStateTemplate" name="template.actif" /></span>	</li>
						<li><label>Banniere html :</label> <br> <span class="bold">
								<br> <s:textarea rows="6" name="template.text"
									id="textHtml" style="width: 900px; height: 100px;"
									placeholder="%{getText('Banniere html code')}"></s:textarea>
						</span><br> </li>
					</ul>

				</div>



			<div class="conteneur_btn_suivant">
				<input type="button" class="terminer_etape_2"
					onclick="validerForm();" value="Ajouter" />
			</div>
		</form>

	</div>