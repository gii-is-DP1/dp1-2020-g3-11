<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags" %>
<springfest:layout pageName="error500">
<center><h1>Error 500</h1>
<spring:url value="/resources/images/error500.jpg" var="error"/>
<img src="${error}"/>
<br><br><br><br>
<h2>No se puede acceder al servidor. Por favor, inténtelo más tarde.</h2>
</center>
</springfest:layout>