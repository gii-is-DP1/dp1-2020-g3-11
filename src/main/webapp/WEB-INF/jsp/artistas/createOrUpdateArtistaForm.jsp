<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>

<springfest:layout pageName="artistas">
	<h2>
		<c:if test="${artista['new']}">Nuevo </c:if>
		Artista
	</h2>
	<form:form modelAttribute="artista" class="form-horizontal"
		id="add-artista-form">
		<div class="form-group has-feedback">
			<input type="hidden" name="version" value="${artista.version}"/>
			<springfest:inputField label="Nombre" name="name" />
			<springfest:inputField label="Correo Electr�nico" name="correo" />
			<div class="control-group">
				<springfest:selectField name="genero.name" label="G�nero " names="${generos}"
					size="${generos.size()}" />
			</div>
			<%-- <springfest:inputField label="G�nero" name="genero" /> --%>
			<springfest:inputField label="Tel�fono" name="telefono" />
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${artista['new']}">
						<button class="btn btn-default" type="submit">Registrar</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar
							datos</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</springfest:layout>
