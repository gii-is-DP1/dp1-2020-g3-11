<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="artistas">


	<h2>Artistas Disponibles</h2>
	<table id="artistaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 75%;">Nombre</th>
				<th style="width: 12%;"></th>
				<th style="width: 13%;"></th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${artistasDisponibles}" var="artista">

				<tr>
					<td><c:out value="${artista.name}" /></td>


					<td><spring:url
							value="/mifestival/artistas/{artistaId}/add"
							var="artistaUrl">
							<spring:param name="artistaId" value="${artista.id}" />
						</spring:url> <a href="${fn:escapeXml(artistaUrl)}">Asociar con mi festival</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<spring:url
							value="/festivales/{festivalId}"
							var="festivaUrl">
							<spring:param name="festivalId" value="${festival.id}" />
						</spring:url> <a href="${fn:escapeXml(festivaUrl)}"><b>Volver a mi festival</b></a></td>
</petclinic:layout>
