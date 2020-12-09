<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="opinions">
	
	<jsp:attribute name="customScript">
	<script>     
    var filtro = ['puta', 'zorra', 'marica'];
    $("#descripcion").text(function(i, txt){
      for(var i=0; i<filtro.length; i++){
		if(txt.indexOf(filtro[i])!=-1){
			var patron = new RegExp(filtro[i]);
	    	  var s = '*'.repeat(filtro[i].length);
	    	  txt = txt.replace(patron, s);
	    	 
		}		
    	  
      }
      return txt;
    });</script>
	
	</jsp:attribute>
	<jsp:body>

	<h2>
		<c:if test="${opinion['new']}">Nueva </c:if>
		Valoración
	</h2>


	<form:form modelAttribute="opinion" class="form-horizontal"
		id="add-festival-form">
		<div class="form-group has-feedback">
			<petclinic:richTextArea label="Descripción" name="descripcion"
				id="description" />
			<label class="col-sm-2 control-label">Puntuación</label> <span
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
						<button class="btn btn-default" id="boton" type="submit">Añadir
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
	</jsp:body>
</petclinic:layout>