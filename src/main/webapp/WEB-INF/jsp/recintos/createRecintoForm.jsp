<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="recintos">


	<script type="text/javascript">
		function yesnoCheck() {
			if (document.getElementById('yesCheck').checked) {
				document.getElementById('ifYes').style.visibility = 'visible';
			} else
				document.getElementById('ifYes').style.visibility = 'hidden';

		}
	</script>

	<h2>
		<c:if test="${recinto['new']}">Nuevo </c:if>
		recinto
	</h2>
	<form:form modelAttribute="recinto" class="form-horizontal"
		id="add-recinto-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Nombre" name="name" />
			<petclinic:inputField label="Nº huecos de puestos" name="huecos" />
			
			<h4>Tipo del recinto</h4>
			Escenario <input type="radio" onclick="javascript:yesnoCheck();"
				name="tipoRecinto.name" id="yesCheck" value="Escenario"><br>
			Parking <input type="radio"
				onclick="javascript:yesnoCheck();" name="tipoRecinto.name" id="noCheck" value="Parking"><br>
			Camping <input type="radio"
				onclick="javascript:yesnoCheck();" name="tipoRecinto.name" id="mCheck" value="Camping"><br>
			<div id="ifYes" style="visibility: hidden">
				<petclinic:inputField label="Nº máximo de escenarios"
					name="numMaxEscenarios" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Añadir
					Recinto</button>
			</div>
		</div>

	</form:form>

</petclinic:layout>