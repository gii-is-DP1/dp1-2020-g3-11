<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>

<springfest:layout pageName="entradaComprada">

	<script type="text/javascript">
		function yesnoCheck() {
			window.alert("Debes ser mayor de edad para escoger esta oferta.")
		}
	</script>

	<h1>
		<b>Confirmar datos de entrada</b>
	</h1>
	<div class="row">
		<div class="col-sm-6">
			<div class="card">
				<div class="card-body">
					<h2 class="card-title">Detalles Festival</h2>
					<p class="card-text">
						Nombre:
						<c:out value="${datosFestival.name}" />
					</p>
					<p class="card-text">
						Fecha de Comienzo:
						<c:out value="${datosFestival.fechaCom}" />
					</p>
					<p class="card-text">
						Fecha de Fin:
						<c:out value="${datosFestival.fechaFin}" />
					</p>
					<p class="card-text">
						Localización:
						<c:out value="${datosFestival.localizacion}" />
					</p>

				</div>

				<br> <br> <br>
				<div class="card">
					<div class="card-body">
						<h2 class="card-title">Tus datos</h2>
						<p class="card-text">
							Nombre:
							<c:out value="${datosUsuario.firstName}" />
						</p>
						<p class="card-text">
							Apellidos:
							<c:out value="${datosUsuario.lastName}" />
						</p>
						<p class="card-text">
							DNI:
							<c:out value="${datosUsuario.DNI}" />
						</p>
						<p class="card-text">
							Teléfono:
							<c:out value="${datosUsuario.telefono}" />
						</p>
						<p class="card-text">
							Correo:
							<c:out value="${datosUsuario.correo}" />
						</p>
					</div>
				</div>
			</div>
			<br>
			<div class="card">
				<div class="card-body">
					<h2 class="card-title">Datos de la entrada</h2>
					<p class="card-text">
						Tipo de entrada:
						<c:out value="${datosEntrada.entradaType.name}" />
					</p>
					<p class="card-text">
						Precio:
						<c:out value="${datosEntrada.precio}" />
					</p>
				</div>
			</div>


		</div>
		<h1>
			<b>¿Quieres añadir una oferta?</b>
		</h1>
		<div class="col-sm-6">
			<div class="card">
				<div class="card-body">
					<c:if test="${datosOferta.size() == 0}">
						<p>No hay ofertas para asociar</p>

					</c:if>

					<c:if test="${datosOferta.size() > 0}">
						<h2 class="card-title">Listado de ofertas</h2>
						<c:forEach items="${datosOferta}" var="oferta">
							<p class="card-text">
								Nombre:
								<c:out value="${oferta.nombre}" />
							</p>
							<p class="card-text">
								Tipo:
								<c:out value="${oferta.tipoOferta}" />
							</p>
							<p class="card-text">
								Precio:
								<c:out value="${oferta.precioOferta}" />
							</p>
							<spring:url
								value="/festivales/${datosFestival.id}/entradas/{entradaId}/asociar/${oferta.id}"
								var="asociarOfertaUrl">
								<spring:param name="entradaId" value="${datosEntrada.id}" />
							</spring:url>
							<br>

							<c:if test="${edad < 18}">
								<c:if test="${oferta.tipoOferta.name == 'Pack bebidas'}">
									<a onclick="javascript:yesnoCheck();"
										href="${fn:escapeXml(asociarOfertaUrl)}">Asociar con mi
										entrada </a>
								</c:if>
								<c:if test="${oferta.tipoOferta.name != 'Pack bebidas'}">
									<a href="${fn:escapeXml(asociarOfertaUrl)}">Asociar con mi
										entrada </a>
								</c:if>
							</c:if>
							<c:if test="${edad >= 18}">
								<a href="${fn:escapeXml(asociarOfertaUrl)}">Asociar con mi
									entrada </a>
							</c:if>

						</c:forEach>
					</c:if>


					<br> <br> <br> <br> <br>

					<div class="card">
						<c:if test="${datosEntrada.ofertas.size() > 0}">
							<div class="card-body">

								<h2 class="card-title">Ofertas asociadas</h2>

								<c:forEach items="${datosEntrada.ofertas}" var="oferta">
									<p class="card-text">
										Oferta:
										<c:out value="${oferta.nombre}" />
									</p>
									<p class="card-text">
										Tipo oferta:
										<c:out value="${oferta.tipoOferta}" />
									</p>
									<p class="card-text">
										Precio:
										<c:out value="${oferta.precioOferta}" />
									</p>

									<spring:url
										value="/festivales/${datosFestival.id}/entradas/{entradaId}/quitar/${oferta.id}"
										var="asociarOfertaUrl">
										<spring:param name="entradaId" value="${datosEntrada.id}" />
									</spring:url>
									<a href="${fn:escapeXml(asociarOfertaUrl)}">Eliminar oferta</a>

								</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
			<br> <br> <br> <br> <br> <br> <br>
			<br> <br> <br> <br> <br> <br> <br>


			<h1>
				Precio Total:
				<c:out value="${precioTotal}" />
			</h1>
		</div>
		<br>

	</div>
	<br>

	<td><spring:url
			value="/festivales/{festivalId}/entradas/{entradaId}/gracias"
			var="festivalIdUrl">
			<spring:param name="festivalId" value="${datosFestival.id}" />
			<spring:param name="entradaId" value="${datosEntrada.id}" />
		</spring:url> <a href="${fn:escapeXml(festivalIdUrl)}"
		class="btn btn-primary btn-sm"> Confirmar compra</a></td>
</springfest:layout>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
