<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="entradas">


<h2>Entradas</h2>
	  <table id="entradaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 33%;">ID</th>
				<th style="width: 33%;">Precio</th>
				<th style="width: 33%;">Tipos de Entrada</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entradas}" var="entrada">
				<tr>
					<td><c:out value="${entrada.id}" /></td>
					<td><c:out value="${entrada.precio}" /></td>
					<td><c:out value="${entrada.entradaType}" /></td>
					<td><a href="entradas/${entrada.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					<td><a href="/entradas/${entrada.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<a href="/entradas/new" class="btn btn-default"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
			entrada</a>
</petclinic:layout>