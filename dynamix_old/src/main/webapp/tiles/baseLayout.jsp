<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <tiles:insertAttribute name="imports" />
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
		<s:head/>
    </head>
    <body>
        <tiles:insertAttribute name="header" />
    	<div class="conteneur">
    		<tiles:insertAttribute name="body" />
    	</div>
    	<div class="footer_intern">Copyright © - Orbit-Interactives</div>
		
		<script type="text/javascript">
			$(document).ready(function() {
				var msgInfo = "${msgInfo}";
				var msgError = "${msgError}";
				var opts = {};
				if ('' != msgInfo) {
					opts.title = 'Succès';
					opts.text = msgInfo;
					opts.type = 'infos';
					new PNotify(opts);
				}
				if ('' != msgError) {
					opts.title = 'Erreur';
					opts.text = msgError;
					opts.type = 'error';
					new PNotify(opts);
				}
				transformDataTables();
			});
		</script>
	</body>
</html>