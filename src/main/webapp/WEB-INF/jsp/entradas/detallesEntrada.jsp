<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="entradas">


	<h2>Detalles entrada</h2>
	<table id="entradaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 33%;">Festival</th>
				<th style="width: 33%;">Precio Entrada + Oferta</th>
				<th style="width: 33%;">Tipo de Entrada</th>
				<th style="width: 33%;">Ofertas</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<tr>
				<td><c:out value="${festival.name}" /></td>
				<td><c:out value="${precioTotal}" /></td>
				<td><c:out value="${entrada.entradaType}" /></td>
				<td><c:if test="${ofertas.size() == 0}">
					<p>No hay ofertas para asociar</p>
				</c:if></td>
				<c:forEach items="${ofertas}" var="oferta">
					<td><c:out value="${oferta.nombre}" /></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</petclinic:layout>