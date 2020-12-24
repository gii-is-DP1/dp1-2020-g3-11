<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="entradas">


	<h2>Mis entradas</h2>
	<table id="entradaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 33%;">Precio</th>
				<th style="width: 33%;">Tipos de Entrada</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entradas}" var="entrada">
				<tr>
					<td><c:out value="${entrada.precio}" /></td>
					<td><c:out value="${entrada.entradaType}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>