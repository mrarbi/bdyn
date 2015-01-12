<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Erreur 404</title>
		<link href="<c:url value='/themes/page_404/css/style.css'/>" rel="stylesheet" type="text/css"  media="all" />
	</head>
	<body>
		<!--start-wrap--->
		<div class="wrap">
			<!---start-header---->
				<div class="header">
					<div class="logo">
						<h1><a href="${pageContext.request.contextPath}/accueil.action">Ohh</a></h1>
					</div>
				</div>
			<!---End-header---->
			<!--start-content------>
			<div class="content">
				<img src="<c:url value='/themes/page_404/images/error-img.png'/>" title="error" />
				<p><span><label>O</label>hh.....</span>Page introuvable!</p>
				<a href="${pageContext.request.contextPath}/accueil.action">page d'accueil</a>
				<div class="copy-right">
					<p></p>
				</div>
   			</div>
			<!--End-Cotent------>
		</div>
		<!--End-wrap--->
	</body>
</html>

