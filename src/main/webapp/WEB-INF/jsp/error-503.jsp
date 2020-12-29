<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="error503">
<center><h1>Error 503</h1>
<spring:url value="/resources/images/error503.jpg" var="error"/>
<img src="${error}"/>
<br><br><br><br>
<h2>El servidor no está disponible en este momento. Por favor, inténtelo más tarde.</h2>
</center>
</petclinic:layout>