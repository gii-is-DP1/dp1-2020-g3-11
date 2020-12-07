<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="miFestival">


	<h2>Artistas</h2>
	<table id="artistaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 75%;">Nombre</th>
				<th style="width: 25%;"></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${artistas}" var="artista">
				<tr>
					<td><c:out value="${artista.name}" /></td>

					<td><spring:url
							value="/mifestival/artistas/{artistaId}/delete" var="artistaUrl">
							<spring:param name="artistaId" value="${artista.id}" />
						</spring:url> <a href="${fn:escapeXml(artistaUrl)}">Borrar</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>


	<a href="/mifestival/artistas/listdisponibles" class="btn btn-default"><span
		class="glyphicon glyphicon-plus" aria-hidden="true"></span> Ver
		artistas disponibles</a>

	<h2>Recintos</h2>
	<table id="recintosTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 75%;">Nombre</th>
				<th style="width: 12, 5%;"></th>
				<th style="width: 12, 5%;"></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${recintos}" var="recinto">
				<tr>
					<td><spring:url value="/mifestival/recintos/{recintoId}/edit"
							var="recintoUrl">
							<spring:param name="recintoId" value="${recinto.id}" />
						</spring:url> <a href="${fn:escapeXml(recintoUrl)}">${recinto.name}</a></td>

					<td><spring:url
							value="/mifestival/recintos/{recintoId}/detalles_recinto"
							var="recintoUrl">
							<spring:param name="recintoId" value="${recinto.id}" />
						</spring:url> <a href="${fn:escapeXml(recintoUrl)}">Detalles recinto</a></td>

					<td><spring:url
							value="/mifestival/recintos/{recintoId}/delete" var="recintoUrl">
							<spring:param name="recintoId" value="${recinto.id}" />
						</spring:url> <a href="${fn:escapeXml(recintoUrl)}">Borrar</a></td>



				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="/mifestival/recintos/new"
		class="btn btn-default"><span class="glyphicon glyphicon-plus"
		aria-hidden="true"></span> Añadir recinto</a>
	<br>
	<h3>Entradas</h3>
	<table id="entradasTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 33%;">Tipo de Entrada</th>
				<th style="width: 33%;">Precio</th>
				<th style="width: 12, 5%;"></th>
				<th style="width: 12, 5%;"></th>

			</tr>
		</thead>
		<tbody>

			<c:forEach items="${entradas}" var="entrada">
				<tr>
					<td><c:out value="${entrada.entradaType}" /></td>
					<td><c:out value="${entrada.precio}" /></td>
					<td><spring:url
							value="/mifestival/entradas/{entradaId}/edit"
							var="entradaUrl">
							<spring:param name="entradaId" value="${entrada.id}" />
						</spring:url> <a href="${fn:escapeXml(entradaUrl)}">Editar</a></td>

					<td><spring:url
							value="/mifestival/entradas/{entradaId}/delete"
							var="entradaUrl">
							<spring:param name="entradaId" value="${entrada.id}" />
						</spring:url> <a href="${fn:escapeXml(entradaUrl)}">Borrar</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<a href="/mifestival/entradas/new"
		class="btn btn-default"><span class="glyphicon glyphicon-plus"
		aria-hidden="true"></span> Añadir entrada</a>
	<br>
	<br>
	<br>
	<br>
	<br>
	<h4>Conciertos</h4>
	<a href="/mifestival/conciertos" class="btn btn-default"><span
		aria-hidden="true"></span> Ver conciertos</a>
</petclinic:layout>
