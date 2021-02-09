<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>

<springfest:layout pageName="gracias">

	<div class="col-sm-6">
		<div class="card">
			<div class="card-body">
				<h2 class="card-title">¡GRACIAS!</h2>
				<p class="card-text">
					Muchas gracias por tu compra, gracias por confiar en nosotros
					<c:out value="${datosUsuario.firstName}"/>
					. Se le mandará su entrada a
					<c:out value="${datosUsuario.correo}"/>
					. Si no le llega asegúrese de que no se encuentra en el apartado de
					'Spam'.
				</p>
			</div>
		</div>
	</div>

	<form>
		<a href="/festivales" class="btn btn-primary btn-sm"> Seguir comprando</a>
	</form>

</springfest:layout>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>