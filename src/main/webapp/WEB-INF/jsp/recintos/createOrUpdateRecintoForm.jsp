<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>

<springfest:layout pageName="MiFestival">



	<h2>
		<c:if test="${recinto['new']}">Nuevo </c:if>
		Recinto
	</h2>
	<form:form modelAttribute="recinto" class="form-horizontal"
		id="add-recinto-form">
		<div class="form-group has-feedback">
			<input type="hidden" name="version" value="${recinto.version}"/>
			<springfest:inputField label="Nombre" name="name" />
			<springfest:inputField label="N� huecos de puestos" name="huecos" />

			<h4>Tipo del recinto</h4>
			Escenario <input type="radio" name="tipoRecinto.name"
				value="Escenario"><br> Parking <input type="radio"
				name="tipoRecinto.name" value="Parking"><br> Camping <input
				type="radio" name="tipoRecinto.name" value="Camping"><br>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${recinto['new']}">
						<button class="btn btn-default" type="submit">A�adir
							Recinto</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar
							Recinto</button>
					</c:otherwise>
				</c:choose>
				<br> <br>
				<spring:url value="/mifestival" var="festivaUrl">
				</spring:url>
				<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver
					a mi festival</a>
			</div>
		</div>

	</form:form>


</springfest:layout>