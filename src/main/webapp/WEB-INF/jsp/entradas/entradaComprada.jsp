<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="entradaComprada">

	<h1><b>Confirmar datos de entrada</b></h1>
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
			</div>
		</div>

		<div class="col-sm-6">
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
	</div>
	<br>
	<div class="col-sm-6">
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


	<td><spring:url value="/festivales/{festivalId}/entradas/{entradaId}/gracias"
			var="festivalIdUrl">
			<spring:param name="festivalId" value="${datosFestival.id}" />
			<spring:param name="entradaId" value="${datosEntrada.id}" />
		</spring:url> <a href="${fn:escapeXml(festivalIdUrl)}"
		class="btn btn-primary btn-sm"> Confirmar compra</a></td>


</petclinic:layout>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
