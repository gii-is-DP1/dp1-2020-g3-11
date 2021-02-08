<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<springfest:layout pageName="mispuestos">


	<h2>Puestos</h2>
	<table id="puestoTable" class="table table-striped">
		<thead>
			<tr>
			<sec:authorize access="hasAuthority('admin')">
				<th style="width: 25%;">Tipo del puesto</th>
				<th style="width: 25%;">Tamaño del puesto</th>
				<th style="width: 15%;">Precio</th>
				<th style="width: 30%;">Recinto asociado</th>
				<th></th>
				<th></th>
			</sec:authorize>
			
			<sec:authorize access="hasAuthority('sponsor')">
				<th style="width: 25%;">Tipo del puesto</th>
				<th style="width: 25%;">Tamaño del puesto</th>
				<th style="width: 15%;">Precio</th>
				<th style="width: 30%;">Recinto asociado</th>
			</sec:authorize>
			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${puestos}" var="puesto">
				<tr>
					<td><c:out value="${puesto.tipoPuesto}" /></td>
					<td><c:out value="${puesto.tipoTamanio}" /></td>
					<td><c:out value="${puesto.precio}" /></td>
					<td><c:out value="${puesto.recinto.name}" /></td>
					<sec:authorize access="hasAuthority('admin')">
						<td><a href="puestos/${puesto.id}/edit"> <span
								class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
						</a></td>
						<td><a href="puestos/${puesto.id}/delete"> <span
								class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<sec:authorize access="hasAuthority('admin')">
		<a href="puestos/new" class="btn btn-default"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
			puesto</a>
	</sec:authorize>
	<br>
	<br>
	<br>
	<br>
	<sec:authorize access="hasAuthority('admin')">
	<spring:url value="/mifestival" var="festivaUrl">
	</spring:url>
	<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver a mi festival</a>
	</sec:authorize>
</springfest:layout>
