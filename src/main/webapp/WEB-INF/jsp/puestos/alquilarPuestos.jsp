<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<springfest:layout pageName="MiFestival">


	<h2>Puestos</h2>
	<table id="puestoTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Tipo del puesto</th>
				<th style="width: 25%;">Tamaño del puesto</th>
				<th style="width: 15%;">Precio</th>
				<th style="width: 29%;">Recinto asociado</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${puestos}" var="puesto">
				<tr>
					<td><c:out value="${puesto.tipoPuesto}" /></td>
					<td><c:out value="${puesto.tipoTamanio}" /></td>
					<td><c:out value="${puesto.precio}" /></td>
					<td><c:out value="${puesto.recinto.name}" /></td>
					<td><spring:url
							value="/festivales/{festivalId}/puestos/{puestoId}/alquilar"
							var="puestoUrl">
							<spring:param name="puestoId" value="${puesto.id}" />
							<spring:param name="festivalId" value="${datosFestival.id}" />
						</spring:url> <a href="${fn:escapeXml(puestoUrl)}" >Alquilar puesto</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</springfest:layout>
