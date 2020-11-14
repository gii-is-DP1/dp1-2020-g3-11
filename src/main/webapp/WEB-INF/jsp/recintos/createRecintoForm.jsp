<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="recintos">
	<h2>
		<c:if test="${recinto['new']}">Nuevo </c:if>
		recinto
	</h2>
	<h3>Lista Puestos</h3>
	<form:form modelAttribute="recinto" class="form-horizontal"
		id="add-recinto-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Nombre" name="name" />
			<petclinic:inputField label="Aforo máximo de recinto"
				name="aforoMaxRec" />
			<petclinic:inputField label="Nº huecos de puestos" name="huecos" />
			<%-- 			<petclinic:inputField label="Tipo del recinto" --%>
			<%-- 				name="tipoRecinto.name" /> --%>
			<div class="control-group">
				<petclinic:selectField name="tipoRecinto.name"
					label="Tipo de recinto" names="${tipos_recinto}"
					size="${tipos_recinto.size()}" />
			</div>
<%-- 			<c:if test="${tipoRecinto.name == 'Escenario'}"> --%>
<%-- 				<petclinic:inputField label="Nº máximo de escenarios" --%>
<%-- 					name="numMaxEscenarios" /> --%>
<%-- 			</c:if> --%>

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Añadir
					Recinto</button>
			</div>
		</div>
	</form:form>
</petclinic:layout>