<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="concerts">
    
    <h2>
        <c:if test="${concert['new']}">Nuevo </c:if> Concierto
    </h2><br>
    
	<h4> Recordatorio: El festival comienza el <b>${festival.fechaCom}</b> y termina el <b>${festival.fechaFin}</b>. 
			Los conciertos deben estar en esa franja horaria. </h4><br>
    <form:form modelAttribute="concert" class="form-horizontal" id="add-concert-form">
        <div class="form-group has-feedback">
        	<input type="hidden" name="version" value="${concert.version}"/>
			<petclinic:localDate label="Fecha" name="fecha" id="fecha"></petclinic:localDate>            
			<petclinic:localDateTime label="Hora comienzo" name="horaCom" id="horaCom"></petclinic:localDateTime>            
			<petclinic:localDateTime label="Hora fin" name="horaFin" id="horaFin"></petclinic:localDateTime>            
			<div class="control-group">

				<petclinic:selectField name="artista.name" label="Artista " names="${artistas}"
					size="${artistas.size()}" />
			</div>
			<div class="control-group">
			
				<petclinic:selectField name="recinto.name" label="Recinto " names="${recintos}"
					size="${recintos.size()}" />
			</div>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${concert['new']}">
                        <button class="btn btn-default" type="submit">Añadir Concierto</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Concierto</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </petclinic:layout> 