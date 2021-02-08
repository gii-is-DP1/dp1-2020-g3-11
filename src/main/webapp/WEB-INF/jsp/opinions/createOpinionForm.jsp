<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>

<springfest:layout pageName="opinions">
	
	<h2>
		<c:if test="${opinion['new']}">Nueva </c:if>
		Valoraci�n
	</h2>


	<form:form modelAttribute="opinion" class="form-horizontal"
		id="add-festival-form">
		<div class="form-group has-feedback">
			<springfest:richTextArea label="Descripci�n" name="descripcion"
				id="description" />
			<label class="col-sm-2 control-label">Puntuaci�n</label> <span
				class="star-rating star-5"> <input type="radio"
				name="puntuacion" value="1"><i></i> <input type="radio"
				name="puntuacion" value="2"><i></i> <input type="radio"
				name="puntuacion" value="3"><i></i> <input type="radio"
				name="puntuacion" value="4"><i></i> <input type="radio"
				name="puntuacion" value="5"><i></i> 
		
			</span>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${opinion['new']}">
						<button class="btn btn-default" id="boton" type="submit">A�adir
							Opinion</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" id="boton" type="submit">Actualizar
							Opinion</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
	<div>
		<br> <br> <br>

		<spring:url value="/festivales" var="festivaUrl">
		</spring:url>
		<a href="${fn:escapeXml(festivaUrl)}" class="btn btn-default">Volver
			a festivales</a>
	</div>
</springfest:layout>