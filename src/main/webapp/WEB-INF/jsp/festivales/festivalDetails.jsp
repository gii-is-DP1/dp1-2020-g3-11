<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="artistas">


	<h2>Artistas</h2>
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

			<c:forEach items="${artistas}" var="artista">
				<tr>
					<td><c:out value="${artista.name}" /></td>
			
					<td><spring:url
							value="/festivales/{festivalId}/artistas/{artistaId}/delete"
							var="artistaUrl">
							<spring:param name="festivalId" value="${festival.id}" />
							<spring:param name="artistaId" value="${artista.id}" />
						</spring:url> <a href="${fn:escapeXml(artistaUrl)}">Borrar</a></td> 

				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="/festivales/${festival.id}/artistas/listdisponibles"
		class="btn btn-default"><span class="glyphicon glyphicon-plus"
		aria-hidden="true"></span> Ver artistas disponibles</a>
		
		<br><br><br><br><br>
		<h2>Conciertos</h2>
		<a href="/festivales/${festival.id}/conciertos"
		class="btn btn-default"><span
		aria-hidden="true"></span> Conciertos</a>
</petclinic:layout>
