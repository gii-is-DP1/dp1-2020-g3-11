<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<springfest:layout pageName="festivales">


	<h2>Festivales</h2>
	<table id="festivalTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Nombre</th>
				<th style="width: 20%;">Aforo Máximo</th>
				<th style="width: 20%;">Fecha Comienzo</th>
				<th style="width: 20%;">Fecha Fin</th>
				<th style="width: 20%;">Localización</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${festivales}" var="festival">
				<tr>
					<spring:url value="/festivales/{festivalId}/cartel"
						var="festivalUrl">
						<spring:param name="festivalId" value="${festival.id}" />
					</spring:url>
					<td><a href="${fn:escapeXml(festivalUrl)}"><c:out
								value="${festival.name}" /></a></td>

					<td><c:out value="${festival.aforoMax}" /></td>
					<td><c:out value="${festival.fechaCom}" /></td>
					<td><c:out value="${festival.fechaFin}" /></td>
					<td><c:out value="${festival.localizacion}" /></td>

					<sec:authorize access="hasAuthority('sponsor')">
						<td><a href="/festivales/${festival.id}/puestos"> Ver
								puestos</a></td>
					</sec:authorize>

					<sec:authorize access="hasAuthority('usuario')">
						<c:if test="${festival.entradasRestantes > 0}">
							<td><spring:url value="/festivales/{festivalId}/entradas"
									var="entradaUrl">
									<spring:param name="festivalId" value="${festival.id}" />
								</spring:url> <a href="${fn:escapeXml(entradaUrl)}">Comprar entradas</a></td>
						</c:if>
						<td><spring:url value="/festivales/{festivalId}/valoraciones"
								var="valoracionUrl">
								<spring:param name="festivalId" value="${festival.id}" />
							</spring:url> <a href="${fn:escapeXml(valoracionUrl)}">Valorar</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<sec:authorize access="hasAuthority('admin')">
		<a href="/festivales/new" class="btn btn-default"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
			festival</a>
	</sec:authorize>
</springfest:layout>
