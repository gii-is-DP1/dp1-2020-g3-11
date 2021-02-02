<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags" %>


<springfest:layout pageName="ofertas">
    
    <h2>
        <c:if test="${oferta['new']}">Nuevo </c:if> Oferta
    </h2><br>
    
    <form:form modelAttribute="oferta" class="form-horizontal" id="add-oferta-form">
        <div class="form-group has-feedback">
        	<springfest:inputField label="Nombre" name="nombre"/>
			<springfest:inputField label="Precio" name="precioOferta"/>
                          <div class="control-group">
                    <springfest:selectField name="tipoOferta.name" label="Tipo de oferta " names="${tiposOferta}" size="${tiposOferta.size()}"/>
                </div>          
	   
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${oferta['new']}">
                        <button class="btn btn-default" type="submit">Añadir Oferta</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </form:form>
    </springfest:layout> 