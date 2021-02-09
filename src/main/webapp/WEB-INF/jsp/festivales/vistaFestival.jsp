<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>


<springfest:layout pageName="MiFestival">


	<div style="width: 100%;">
		<div style="float: top; width: 45%;">
			<h1>${festival.name}</h1>
		</div>

	</div>
	<br>
	<br>



	<div style="width: 100%;">
		<div style="float: left; width: 30%; margin-left: 3%;">
			<h2>Artistas</h2>
			<table id="artistaTable" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 85%;">Nombre</th>
						<th></th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${artistas}" var="artista">
						<tr>
							<td><c:out value="${artista.name}" /></td>

							<td><spring:url
									value="/mifestival/artistas/{artistaId}/delete"
									var="artistaUrl">
									<spring:param name="artistaId" value="${artista.id}" />
								</spring:url> <a href="${fn:escapeXml(artistaUrl)}" class="glyphicon glyphicon-trash"></a></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>


			<a href="/mifestival/artistas/listdisponibles"
				class="btn btn-default"><span class="glyphicon glyphicon-plus"
				aria-hidden="true"></span> Ver artistas disponibles</a>
		</div>
		<div style="float: left; width: 30%; margin-left: 3%;">
			<h2>Recintos</h2>
			<table id="recintosTable" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 50%;">Nombre</th>
						<th style="width: 20%;"></th>
						<th style="width: 5%;"></th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${recintos}" var="recinto">
						<tr>
							<td><spring:url
									value="/mifestival/recintos/{recintoId}/edit"
									var="recintoUrl">
									<spring:param name="recintoId" value="${recinto.id}" />
								</spring:url> <a href="${fn:escapeXml(recintoUrl)}">${recinto.name}</a></td>

							<td><spring:url
									value="/mifestival/recintos/{recintoId}/detalles"
									var="recintoUrl">
									<spring:param name="recintoId" value="${recinto.id}" />
								</spring:url> <a href="${fn:escapeXml(recintoUrl)}">Detalles</a></td>

							<td><spring:url
									value="/mifestival/recintos/{recintoId}/delete"
									var="recintoUrl">
									<spring:param name="recintoId" value="${recinto.id}" />
								</spring:url> <a href="${fn:escapeXml(recintoUrl)}" class="glyphicon glyphicon-trash"></a></td>



						</tr>
					</c:forEach>
				</tbody>
			</table>


			<a href="/mifestival/recintos/new" class="btn btn-default"><span
				class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
				recinto</a>
		</div>
		<div style="float: left; width: 30%; margin-left: 3%;">
			<h2>Entradas</h2>
			<table id="entradasTable" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 40%;">Tipo de Entrada</th>
						<th style="width: 33%;">Precio</th>
						<th style="width: 12, 5%;"></th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${entradas}" var="entrada">
						<tr>
							<td><c:out value="${entrada.entradaType}" /></td>
							<td><c:out value="${entrada.precio}" /></td>
							<td><spring:url
									value="/mifestival/entradas/{entradaId}/edit" var="entradaUrl">
									<spring:param name="entradaId" value="${entrada.id}" />
								</spring:url> <a href="${fn:escapeXml(entradaUrl)}" class="glyphicon glyphicon-pencil"></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


			<a href="/mifestival/entradas/new" class="btn btn-default"><span
				class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
				entrada</a>
		</div>
		<br style="clear: left;" />
	</div>
	<br>
	<br>
	<br>
	<div style="width: 100%;">
		<div style="float: left; width: 30%; margin-left: 3%;">
			<a href="/mifestival/ofertas" class="btn btn-default"> <span
				aria-hidden="true"></span> Ofertas
			</a>
		</div>
		<div style="float: left; width: 30%; margin-left: 3%;">
			<a href="/mifestival/conciertos" class="btn btn-default"> <span
				aria-hidden="true"></span> Conciertos
			</a>
		</div>
		<div style="float: left; width: 30%; margin-left: 3%;">
			<a href="/mifestival/puestos" class="btn btn-default"> <span
				aria-hidden="true"></span> Puestos
			</a>
		</div>
	</div>
</springfest:layout>