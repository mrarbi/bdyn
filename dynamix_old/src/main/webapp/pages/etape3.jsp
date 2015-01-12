<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="page_acceuil_intern">
	<div class="menu_verti_intern_haut">
		<div class="ligne_menu">
			<a href="${pageContext.request.contextPath}/etape1.action" class="conteneur_btn_menu_1">
				<div class="text_menu">Flux de données</div>
				<div class="etap">(étape 1)</div>
			</a>
			<div class="icon_valider"></div>
		</div>
		<div class="ligne_menu">
			<a href="${pageContext.request.contextPath}/etape2.action" class="conteneur_btn_menu_2">
				<div class="text_menu">Choix de template</div>
				<div class="etap">(étape 2)</div>
			</a>
			<div class="icon_valider"></div>
		</div>
	</div>
	<div class="ligne_menu">
		<a href="${pageContext.request.contextPath}/etape3.action" class="conteneur_btn_menu_3_intern">
			<div class="text_menu">Matching</div>
			<div class="etap">(étape 3)</div>
		</a>
	</div>
	<div class="box_etape_3">
		<div class="titre_tableau">Flux de données</div>
		<div class="conteneur_tableau">
			<table class="tableau_etape_3"  id="tableId">
			 <thead>
				<tr class="ligne_principal">
					<s:iterator var="counter" begin="1" end="%{dataSize}">
						<!-- current iteration value (1, ... 5) -->
						<td class="case_tab drag_tab">${counter}</td>
					</s:iterator>
				</tr>
			 </thead>
			 <tbody>
				<s:iterator value="dataList" var="data" status="it1" end="2">
					<tr class="ligne_secondaire">
						<s:iterator value="data" var="pair" status="it2">
							<td class="case_tab" id="flux_${pair.id}">
								${pair.value}
							</td>
						</s:iterator>
					</tr>
				</s:iterator>
			</tbody>
			</table>
		</div>
		<div class="titre_tableau">&nbsp;&nbsp;Template</div>
		<div class="conteneur_template">
			<%-- 			<img src='<c:url value="themes/images/banniere_page_jaune_3-2.jpg" />' class="ban" /> --%>
<!-- 			<iframe id="DynCr" -->
<%-- 				src="${pageContext.request.contextPath}/rest/previewCrea/${creativeVO.tempId}" class="ban" --%>
<!-- 				style="border: 1px dashed; overflow: scroll; width: 310px; height: 260px"></iframe> -->
			<div id="DynCr" style="border: 1px dashed; width: 300px; height: 250px"></div>
		</div>
		<div class="ligne_grise"></div>
		<div class="ligne_blanche"></div>
		<form method="post" action="etape3v.action" id="formEtape3ID" novalidate="novalidate">
			<input type="hidden" id="paramMat">
			<div class="conteneur_preview">
				Faites référence aux colonnes du tableau ci-dessus en mettant le numéro de colonne entre []
				<div class="previe_g">
					<table style="width: 100%" id="textTable">
						<s:iterator value="mats" var="m" status="inc">
							<s:set id="index">${inc.index}</s:set>
							<s:hidden name="mats[%{index}].id" value="%{id}"/>
							<tr>
								<td><s:textfield name="mats[%{index}].value" label="%{id} " id="mats_%{index}" value="%{value}" onmousedown="this.clicked = 1;"
									onfocus="if (!this.clicked) this.select(); else this.clicked = 2;"
									onclick="if (this.clicked == 2) this.select(); this.clicked = 0;" cssClass="champs_intern" size="36"/></td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<div class="previe_d">
					<a href="javascript:void(0);" class="bt_previe" onclick="javascript:reloadBan()">Aperçu</a>
				</div>
			</div>
			<div class="conteneur_btn_suivant">
				<input type="button" class="terminer_etape_3" value="Terminer" onclick="postForm($('#formEtape3ID'));"/>
			</div>
		</form>
	</div>
	<div class="menu_verti_intern">
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
<script type="text/javascript">
	function reloadBan(){
		<s:iterator value="mats" var="m" status="inc">
			var id = "${m.id}";
			var val = $("#mats_${inc.index}").val();
			<s:iterator var="counter" begin="1" end="%{dataSize}">val = val.replace("[${counter}]", $("#flux_${counter}").html());</s:iterator>
			if (id.indexOf(">") > -1) {
				var attr = id.substring(id.indexOf(">") + 1);
				var subId = id.substring(0, id.indexOf(">"));
				$("#DynCr").contents().find("#" + subId).attr(attr, val);
			} else {
				$("#DynCr").contents().find("#" + id).html(val);
			}
		</s:iterator>
	}

	$(window).load(function(){

		$('#tableId thead tr td').draggable({
		       cursorAt: {
		        left: -5,
		        bottom: 5
		    },
		    cursor: 'move',
			helper: 'clone',
		    distance: 10,
		    delay: 100,
		    scope: 'textTable-item',
		    revert: 'invalid'
		});
		
		$('#textTable tr td input[type=text]').droppable({
		    scope: 'textTable-item',
		    activeClass: 'active',
		    hoverClass: 'drop_hover',
		    tolerance: 'pointer',
		    drop: function(event, ui) {
		        var id = ui.draggable.text();
		      	$(this).val($(this).val() + '[' + id + ']');
		    }
		});
		
		
		$("#DynCr").load('${pageContext.request.contextPath}/rest/previewCrea/${creativeVO.tempId}');
		
	}); 
</script>
