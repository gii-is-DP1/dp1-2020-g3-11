a<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags" %>

<springfest:layout pageName="festivales">
 
    <jsp:attribute name="customScript">
	
        <script>
            $(function () {
                $("#fechaCom").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
        <script>
            $(function () {
                $("#fechaFin").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
      
    </jsp:attribute>
    <jsp:body>
    
       <h2>
        <c:if test="${festival['new']}">Nuevo </c:if> Festival
    </h2>
    
    
    <form:form modelAttribute="festival" class="form-horizontal" id="add-festival-form">
        <div class="form-group has-feedback">
            <springfest:inputField label="Nombre" name="name"/>
            <springfest:inputField label="Aforo Maximo" name="aforoMax"/>
            <springfest:inputField label="Fecha Comienzo" name="fechaCom"/>
            <springfest:inputField label="Fecha Fin" name="fechaFin"/>  
            <springfest:inputField label="Localizacion" name="localizacion"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${festival['new']}">
                        <button class="btn btn-default" type="submit">A�adir Festival</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Festival</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </jsp:body>
</springfest:layout> 