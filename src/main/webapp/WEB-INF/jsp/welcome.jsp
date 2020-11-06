<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<petclinic:layout pageName="home">
	<h2>
		<fmt:message key="welcome" />
	</h2>
	<div class="row">
		<div class="col-md-12">
			<center>

				<spring:url value="/resources/images/logo_mainSpringFest.png" htmlEscape="true"
					var="logoImage" />
				<img class="img-responsive" src="${logoImage}" />

			</center>
		</div>
	</div>
</petclinic:layout>