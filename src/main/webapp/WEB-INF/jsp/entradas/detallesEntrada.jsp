<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<springfest:layout pageName="misEntradas">


	<h2>Detalles entrada</h2>
	<table id="entradaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Festival</th>
				<th style="width: 15%;">Precio Entrada + Oferta</th>
				<th style="width: 15%;">Tipo de Entrada</th>
			</tr>
		</thead>
		<tbody>

			<tr>
				<td><c:out value="${festival.name}" /></td>
				<td><c:out value="${precioTotal}" /></td>
				<td><c:out value="${entrada.entradaType}" /></td>
			</tr>
		</tbody>
	</table>
	
	<h3>Ofertas</h3>
	<c:if test="${ofertas.size() == 0}">
		<p>No hay ofertas asociadas</p>
	</c:if>
	<c:forEach items="${ofertas}" var="oferta">
		<ul>
			<li>${oferta.nombre}</li>
		</ul>
	</c:forEach>
	
	<br>
	
	<spring:url value="/festivales" var="festivaUrl">
	</spring:url>
	<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver
		a festivales</a>
</springfest:layout>