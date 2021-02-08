<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<springfest:layout pageName="misEntradas">


	<h2>Mis entradas</h2>
	<table id="entradaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 30%;">Precio</th>
				<th style="width: 50%;">Tipos de Entrada</th>
				<th style="width: 20%;"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entradas}" var="entrada">
				<tr>

					<td><c:out value="${entrada.precio}" /></td>
					<td><c:out value="${entrada.entradaType}" /></td>
					<td><spring:url value="/misEntradas/${entrada.id}"
							var="entradaUrl">
						</spring:url> <a href="${fn:escapeXml(entradaUrl)}">Ver detalles entrada</a></td>


				</tr>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="/festivales" var="festivaUrl">
	</spring:url>
	<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver a festivales</a>
</springfest:layout>