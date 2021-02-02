<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springfest" tagdir="/WEB-INF/tags"%>

<springfest:layout pageName="sponsor">

	<jsp:attribute name="customScript">
	<script type="text/javascript">
		function yesnoCheck() {
			if (document.getElementById('yesCheck').checked) {
				document.getElementById('ifYes').style.visibility = 'visible';
			} else
				document.getElementById('ifYes').style.visibility = 'hidden';

		}
	</script>
	
	
    <script>
					$(function() {
						$("#fechaNacimiento").datepicker({
							dateFormat : 'yy/mm/dd'
						});
					});
				</script>

    </jsp:attribute>
	<jsp:body>
	<h2>
		<c:if test="${usuario['new']}">Nuevo </c:if>
		Usuario
	</h2>
	<form:form modelAttribute="usuario" class="form-horizontal"
			id="add-usuario-form">
		<div class="form-group has-feedback">
			<h4>Tipo de usuario</h4>
			<div class="tipoUsuario">
			Sponsor <input type="radio" onclick="javascript:yesnoCheck();"
						name="tipoUsuario.name" id="yesCheck" value="Sponsor"><br>
					
			Usuario <input type="radio" onclick="javascript:yesnoCheck();"
						name="tipoUsuario.name" id="noCheck" value="Usuario"><br>
</div>
			<springfest:inputField label="Nombre" name="firstName" />
			<springfest:inputField label="Apellidos" name="lastName" />
			<springfest:inputField label="Correo" name="correo" />
			<springfest:inputField label="DNI" name="DNI" />
			<springfest:inputField label="Telefono" name="telefono" />
			<springfest:inputField label="Fecha de Nacimiento"
					name="fechaNacimiento" />
			<springfest:inputField label="Usuario" name="user.username" />
			<springfest:inputField label="Contraseña" name="user.password" />
			<div id="ifYes" style="visibility: hidden">
				<springfest:inputField label="Marca" name="marca" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${usuario['new']}">
						<button class="btn btn-default" type="submit">Registrarme</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar
							mis datos</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
	 </jsp:body>
</springfest:layout>
