<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="puestos">


<h2>Puestos</h2>
	  <table id="puestoTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">ID</th>
				<th style="width: 20%;">Tipo del puesto</th>
				<th style="width: 20%;">Tamaño del puesto</th>
				<th style="width: 20%;">Precio</th>
				<th style="width: 20%;">Recinto asociado</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${puestos}" var="puesto">
				<tr>
					<td><c:out value="${puesto.id}" /></td>
					<td><c:out value="${puesto.tipoPuesto}" /></td>
					<td><c:out value="${puesto.tipoTamanio}" /></td>
					<td><c:out value="${puesto.precio}" /></td>
					<td><c:out value="${puesto.recinto.name}" /></td>
					<td><a href="puestos/${puesto.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					<td><a href="puestos/${puesto.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<a href="puestos/new" class="btn btn-default"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
			puesto</a>
			
<br><br><br><br>
	<spring:url value="/mifestival" var="festivaUrl">
	</spring:url>
	<a href="${fn:escapeXml(festivaUrl)}"><b>Volver a mi festival</b></a>
</petclinic:layout>
