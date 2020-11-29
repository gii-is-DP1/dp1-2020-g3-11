<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="recintos">
	<h2>
		<c:out value="${recinto.name}" />
	</h2>
	<h3>Lista Puestos</h3>
	<table id="puestos_recinto_table" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Nombre</th>
			</tr>
		</thead>
	</table>
	<c:if test="${recinto.tipoRecinto == 'Escenario'}">
		<h3>Lista Conciertos</h3>
		<table id="conciertos_recinto_table" class="table table-striped">
			<thead>
				<tr>
					<th style="width: 20%;">Nombre</th>
				</tr>
			</thead>
		</table>
	</c:if>
</petclinic:layout>
