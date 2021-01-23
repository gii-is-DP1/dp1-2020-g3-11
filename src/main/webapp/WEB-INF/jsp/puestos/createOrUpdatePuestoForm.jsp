<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="puestos">


	<h2>
		<c:if test="${puesto['new']}">Registro de un nuevo </c:if>
		Puesto
	</h2>
	<form:form modelAttribute="puesto" class="form-horizontal"
		id="add-puesto-form">
		<div class="form-group has-feedback">
			
			<input type="hidden" name="version" value="${puesto.version}"/>
			<div class="control-group">
				<petclinic:selectField name="tipoPuesto.name"
					label="Tipo " names="${tipos_puesto}"
					size="${tipos_puesto.size()}" />
			</div>
			
			<div class="control-group">
				<petclinic:selectField name="tipoTamanio.name"
					label="Tamaño " names="${tipos_tamaño}"
					size="${tipos_tamaño.size()}" />
			</div>
			
			<petclinic:inputField label="Precio" name="precio" />
			
			<div class="control-group">
			
				<petclinic:selectField name="recinto.name" label="Recinto " names="${recintos}"
					size="${recintos.size()}" />
			</div>

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${puesto['new']}">
						<button class="btn btn-default" type="submit">Registro</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar
							Puesto</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>
