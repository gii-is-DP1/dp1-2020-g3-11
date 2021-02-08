<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags" %>


<springfest:layout pageName="MiFestival">
    
    <h2>
        <c:if test="${concert['new']}">Nuevo </c:if> Concierto
    </h2><br>
    
	<h4> Recordatorio: El festival comienza el <b>${festival.fechaCom}</b> y termina el <b>${festival.fechaFin}</b>. 
			Los conciertos deben estar en esa franja horaria. </h4><br>
    <form:form modelAttribute="concert" class="form-horizontal" id="add-concert-form">
        <div class="form-group has-feedback">
        	<input type="hidden" name="version" value="${concert.version}"/>
			<springfest:localDate label="Fecha" name="fecha" id="fecha"></springfest:localDate>            
			<springfest:localDateTime label="Hora comienzo" name="horaCom" id="horaCom"></springfest:localDateTime>            
			<springfest:localDateTime label="Hora fin" name="horaFin" id="horaFin"></springfest:localDateTime>            
			<div class="control-group">

				<springfest:selectField name="artista.name" label="Artista " names="${artistas}"
					size="${artistas.size()}" />
			</div>
			<div class="control-group">
			
				<springfest:selectField name="recinto.name" label="Recinto " names="${recintos}"
					size="${recintos.size()}" />
			</div>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${concert['new']}">
                        <button class="btn btn-default" type="submit">A�adir Concierto</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Concierto</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </springfest:layout> 