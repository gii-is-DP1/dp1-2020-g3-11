<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="error">
<center><h1>Error</h1>
<spring:url value="/resources/images/error.jpg" var="error"/>
<img src="${error}"/>
<br><br><br><br>
<h2>Ha ocurrido un error. Por favor, inténtelo más tarde.</h2>
</center>
</petclinic:layout>