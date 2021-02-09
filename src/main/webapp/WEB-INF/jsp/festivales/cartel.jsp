<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>


<springfest:layout pageName="artistasRegistradosEnLaWeb">


	<h2>Cartel del festival</h2>

	<table id="artistaTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 70%;">Nombre</th>
				<th style="width: 20%;">Género</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${artistas}" var="artista">
				<tr>
					<td><c:out value="${artista.name}" /></td>
					<td><c:out value="${artista.genero}" /></td>
					
				</tr>
			</c:forEach>
		</tbody>

	</table>

	
</springfest:layout>
