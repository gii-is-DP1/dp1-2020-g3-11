<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="conciertos">


<h2>Conciertos</h2>
	  <table id="conciertoTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">ID</th>
				<th style="width: 20%;">Fecha</th>
				<th style="width: 20%;">Hora Comienzo</th>
				<th style="width: 20%;">Hora Fin</th>
				<th style="width: 20%;">Artista</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${conciertos}" var="concierto">
				<tr>
					<td><c:out value="${concierto.id}" /></td>
					<td><c:out value="${concierto.fecha}" /></td>
					<td><c:out value="${concierto.horaCom}" /></td>
					<td><c:out value="${concierto.horaFin}" /></td>
					<td><c:out value="${concierto.artista.name}" /></td>
					<td><a href="/conciertos/${concierto.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					<td><a href="/conciertos/${concierto.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<a href="/conciertos/new" class="btn btn-default"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
			concierto</a>
</petclinic:layout>
