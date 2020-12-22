<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="ofertas">


	<h2>Ofertas</h2>
	<br>
	<table id="ofertaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 33%;">Nombre</th>
				<th style="width: 33%;">Tipo de oferta</th>
				<th style="width: 33%;">Precio</th>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${ofertas}" var="oferta">

				<tr>
					<td><c:out value="${oferta.nombre}" /></td>
					<td><c:out value="${oferta.tipoOferta}" /></td>
					<td><c:out value="${oferta.precioOferta}" /></td>
					<td><a href="ofertas/${oferta.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="ofertas/new" class="btn btn-default"><span
		class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
		oferta</a>
	<br><br><br><br>
	<spring:url value="/mifestival" var="festivaUrl">
	</spring:url>
	<a href="${fn:escapeXml(festivaUrl)}"><b>Volver a mi festival</b></a>
</petclinic:layout>
