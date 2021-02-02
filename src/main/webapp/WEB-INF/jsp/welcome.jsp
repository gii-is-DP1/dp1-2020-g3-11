<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<springfest:layout pageName="home">
	<center><h1>
		¡Bienvenido a SpringFest!
	</h1></center>
	<div class="row">
		<div class="col-md-12">
			<center><spring:url value="/resources/images/logo_mainSpringFest.png" htmlEscape="true"
					var="logoImage"/>
				<img class="img-responsive" src="${logoImage}" width="721" height="583"/></center>
		</div>
	</div>
</springfest:layout>