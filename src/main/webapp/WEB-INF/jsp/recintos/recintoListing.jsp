<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="recintos">


	<h2>Recintos</h2>
	<table id="recintosTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 70%;">Nombre</th>
				<th style="width: 13%;"></th>
				<th style="width: 12%;"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${recintos}" var="recinto">
				<tr>
					<td><c:out value="${recinto.name}" /></td>
					<td><a href="/recintos/${recinto.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					<td><a href="/recintos/${recinto.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="/recintos/new" class="btn btn-default"><span
		class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
		recinto</a>
</petclinic:layout>