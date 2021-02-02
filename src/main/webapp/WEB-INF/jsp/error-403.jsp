<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags" %>
<springfest:layout pageName="error403">
<center><h1>Error 403</h1>
<spring:url value="/resources/images/error403.jpg" var="error"/>
<img src="${error}"/>
<br><br><br><br>
<h2>No tienes permiso para acceder a este enlace.</h2>
</center>
</springfest:layout>