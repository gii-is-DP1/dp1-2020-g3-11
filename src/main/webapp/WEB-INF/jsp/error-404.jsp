<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags" %>
<springfest:layout pageName="error404">
<center><h1>Error 404</h1>
<spring:url value="/resources/images/error.png" var="error"/>
<img src="${error}"/>
<br><br><br><br>
<h2>El enlace está roto, defectuoso o que ya no existe. Inténtelo más tarde</h2>
</center>
</springfest:layout>