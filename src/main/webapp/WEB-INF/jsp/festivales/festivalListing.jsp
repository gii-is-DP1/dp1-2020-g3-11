<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="festivales">


<h2>Festivales</h2>
	  <table id="festivalTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Nombre</th>
				<th style="width: 20%;">Aforo Máximo</th>
				<th style="width: 20%;">Fecha Comienzo</th>
				<th style="width: 20%;">Fecha Fin</th>
				<th style="width: 20%;">Localización</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${festivales}" var="festival">
				<tr>
				<spring:url value="/festivales/{festivalId}" var="festivalUrl">
                        <spring:param name="festivalId" value="${festival.id}"/>
                    </spring:url>
					<td> <a href="${fn:escapeXml(festivalUrl)}"><c:out value="${festival.name}" /></a></td>
					<td><c:out value="${festival.aforoMax}" /></td>
					<td><c:out value="${festival.fechaCom}" /></td>
					<td><c:out value="${festival.fechaFin}" /></td>
					<td><c:out value="${festival.localizacion}" /></td>
					<td><a href="/festivales/${festival.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					<td><a href="/festivales/${festival.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<a href="/festivales/new" class="btn btn-default"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Añadir
			festival</a>
</petclinic:layout>
