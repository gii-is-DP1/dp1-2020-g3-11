<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="conciertos">

	<jsp:attribute name="customScript">
	
        <script>
            $(function () {
                $("#fecha").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
        <script>
            $(function () {
                $("#horaCom").datepicker({dateFormat: 'yy/mm/dd HH:xx'});
            });
        </script>
        <script>
            $(function () {
                $("#horaFin").datepicker({dateFormat: 'yy/mm/dd HH:xx'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    
    <h2>
        <c:if test="${concierto['new']}">Nuevo </c:if> Concierto
    </h2>
    <form:form modelAttribute="concierto" class="form-horizontal" id="add-concierto-form">
        <div class="form-group has-feedback">
        
            <petclinic:inputField label="Fecha" name="fecha"/>
            <petclinic:inputField label="Hora Comienzo" name="horaCom"/>
            <petclinic:inputField label="Hora Fin" name="horaFin"/>  
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
                    <c:when test="${concierto['new']}">
                        <button class="btn btn-default" type="submit">Añadir Concierto</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Concierto</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </jsp:body>
    </petclinic:layout> 