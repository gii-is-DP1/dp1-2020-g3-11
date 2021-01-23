<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="entradas">

	<h2>
		<c:if test="${entrada['new']}">Nueva </c:if>
		Entrada
	</h2>
	<form:form modelAttribute="entrada" class="form-horizontal"
		id="add-entrada-form">
		<div class="form-group has-feedback">
			
			<input type="hidden" name="version" value="${entrada.version}"/>
			
			<petclinic:inputField label="Precio" name="precio" />
			<div class="control-group">
				<petclinic:selectField name="entradaType.name"
					label="Tipo de entrada " names="${entradatype}"
					size="${entradatype.size()}" />
			</div>

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${entrada['new']}">
						<button class="btn btn-default" type="submit">Añadir
							Entrada</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar
							Entrada</button>
					</c:otherwise>
				</c:choose>
				<br>
				<br>
				<spring:url value="/mifestival" var="festivaUrl">
				</spring:url>
				<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver
					a mi festival</a>
			</div>
		</div>
	</form:form>


</petclinic:layout>
