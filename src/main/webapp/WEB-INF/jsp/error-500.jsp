<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="error500">
<center><h1>Error 500</h1>
<spring:url value="/resources/images/error500.jpg" var="error"/>
<img src="${error}"/>
<br><br><br><br>
<h2>No se puede acceder al servidor. Por favor, inténtelo más tarde.</h2>
</center>
</petclinic:layout>