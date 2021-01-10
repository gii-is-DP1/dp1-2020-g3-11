<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="festivales">

	<c:if test="${opinions.size() > 0}">
		<h2>Últimas 5 valoraciones</h2>

		<table id="festivalTable" class="table table-striped">
			<thead>
				<tr>
					<!-- <th style="width: 25%;">Usuario</th> -->
					<th style="width: 70%;">Descripción</th>
					<th style="width: 1%;">Puntuación</th>
					<th style="width: 14%;">Fecha</th>
					<th style="width: 15%;">Usuario</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${opinions}" var="opinion">
					<tr>

						<%-- <td><c:out value="${opinion.usuario}" /></td> --%>
						<td>${opinion.descripcion}</td>
						<td><c:out value="${opinion.puntuacion}" /></td>
						<td><c:out value="${opinion.fecha}" /></td>
						<td><c:out value="${opinion.opinionUsuario.user.username}" /></td>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<br>
	<a href="valoraciones/new" class="btn btn-default"><span
		class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
		valoración</a>

	<br>
	<br>
	<br>
	<br>

	<h2>Valoración media del festival</h2>

	<c:if test="${average == null}">
		<span class="star-rating star-5"> </span>
		<br>
		<br>

		<h4>¡Sé el primero en valorar el festival!</h4>
	</c:if>

	<c:if test="${average == 1}">
		<span class="star-rating star-5"> <input type="radio"
			name="puntuacion" value="1" checked="checked" disabled="disabled"><i></i>
		</span>
	</c:if>

	<c:if test="${average == 2}">
		<span class="star-rating star-5"> <input type="radio"
			name="puntuacion" value="2" checked="checked" disabled="disabled"><i></i>
			<input type="radio" name="puntuacion" value="2" checked="checked"
			disabled="disabled"><i></i>
		</span>
	</c:if>

	<c:if test="${average == 3}">
		<span class="star-rating star-5"> <input type="radio"
			name="puntuacion" value="3" checked="checked" disabled="disabled"><i></i>
			<input type="radio" name="puntuacion" value="3" checked="checked"
			disabled="disabled"><i></i> <input type="radio"
			name="puntuacion" value="3" checked="checked" disabled="disabled"><i></i>
		</span>
	</c:if>

	<c:if test="${average == 4}">
		<span class="star-rating star-5"> <input type="radio"
			name="puntuacion" value="4" checked="checked" disabled="disabled"><i></i>
			<input type="radio" name="puntuacion" value="4" checked="checked"
			disabled="disabled"><i></i> <input type="radio"
			name="puntuacion" value="4" checked="checked" disabled="disabled"><i></i>
			<input type="radio" name="puntuacion" value="4" checked="checked"
			disabled="disabled"><i></i>
		</span>
	</c:if>

	<c:if test="${average == 5}">
		<span class="star-rating star-5"> <input type="radio"
			name="puntuacion" value="4" checked="checked" disabled="disabled"><i></i>
			<input type="radio" name="puntuacion" value="5" checked="checked"
			disabled="disabled"><i></i> <input type="radio"
			name="puntuacion" value="5" checked="checked" disabled="disabled"><i></i>
			<input type="radio" name="puntuacion" value="5" checked="checked"
			disabled="disabled"><i></i> <input type="radio"
			name="puntuacion" value="5" checked="checked" disabled="disabled"><i></i>
		</span>
	</c:if>

	<div>
		<br> <br> <br>

		<spring:url value="/festivales" var="festivaUrl">
		</spring:url>
		<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver
			a festivales</a>
	</div>
</petclinic:layout>