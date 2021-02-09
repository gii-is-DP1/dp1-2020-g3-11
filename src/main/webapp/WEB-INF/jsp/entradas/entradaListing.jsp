<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<springfest:layout pageName="entradas">


	<h2>Entradas</h2>
	<table id="entradaTable" class="table table-striped">
		<thead>
			<tr>
				<sec:authorize access="hasAuthority('admin')">
					<th style="width: 33%;">ID</th>
				</sec:authorize>
				<th style="width: 30%;">Precio</th>
				<th style="width: 50%;">Tipos de Entrada</th>
				<th  style="width: 20%;"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entradas}" var="entrada">
				<tr>
					<td><c:out value="${entrada.precio}" /></td>
					<td><c:out value="${entrada.entradaType}" /></td>

					<td><a href="entradas/${entrada.id}/comprar"> Comprar entrada <span
							class="glyphicon glyphicon-eur" aria-hidden="true"></span>
					</a></td>
					<sec:authorize access="hasAuthority('admin')">
						<td><c:out value="${entrada.id}" /></td>

						<td><a href="entradas/${entrada.id}/edit"> <span
								class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
						</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<sec:authorize access="hasAuthority('admin')">
		<a href="/entradas/new" class="btn btn-default"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
			entrada</a>
	</sec:authorize>
	
	<div>
		<br> <br> <br>

		<spring:url value="/festivales" var="festivaUrl">
		</spring:url>
		<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver
			a festivales</a>
	</div>
</springfest:layout>