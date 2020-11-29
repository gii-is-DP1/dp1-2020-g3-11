<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">
	<center>
    <spring:url value="/resources/images/error.png" var="errorImage"/>
    <img src="${errorImage}"/>
	</center>
    
    <p>${exception.message}</p>

</petclinic:layout>
