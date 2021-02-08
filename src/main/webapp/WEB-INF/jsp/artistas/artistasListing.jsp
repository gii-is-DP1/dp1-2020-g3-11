<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>


<springfest:layout pageName="artistasRegistradosEnLaWeb">


	<h2>Artistas</h2>

	<table id="artistaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Nombre</th>
				<th style="width: 20%;">Telefono</th>
				<th style="width: 30%;">Correo</th>
				<th style="width: 30%;">Género</th>
				<th style="width: 20%"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="artista">
				<tr>
					<td><c:out value="${artista.name}" /></td>
					<td><c:out value="${artista.telefono}" /></td>
					<td><c:out value="${artista.correo}" /></td>
					<td><c:out value="${artista.genero}" /></td>
					<td><spring:url value="/artistas/{artistaId}/edit"
							var="artista2Url">
							<spring:param name="artistaId" value="${artista.id}" />
						</spring:url> <a href="${fn:escapeXml(artista2Url)}">Editar</a></td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
	<nav aria-label="Page navigation example">
		<ul class="pagination">
			<c:forEach items="${pages}" var="pages">
				<li class="page-item"><a class="page-link" href="?page=${pages}">${pages}</a></li>
			</c:forEach>

		</ul>
	</nav>

	<h3>
		<spring:url value="/artistas/new" var="newArtistaUrl">
		</spring:url>
		<a href="${fn:escapeXml(newArtistaUrl)}" class="btn btn-default">Añadir
			artista</a>
	</h3>
</springfest:layout>
