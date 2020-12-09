<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Opciones del menu: inicio, login, entradas, festivales, mis puestos o error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">

			<ul class="nav navbar-nav">
				<!--LO QUE SE VE SIN INICIAR SESION			-->
				<sec:authorize access="!isAuthenticated()">


					<petclinic:menuItem active="${name eq 'home'}" url="/"
						title="home page">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>

						<span>Inicio</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'about'}" url="/about"
						title="about">
						<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
						<span>Sobre nosotros</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'login'}" url="/login"
						title="login">
						<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
						<span>Login</span>
					</petclinic:menuItem>
					
					<petclinic:menuItem active="${name eq 'registroSpringfest'}" url="/usuarios/new"
						title="registroUsuario">
						<span class="glyphicon glyphicon-copy" aria-hidden="true"></span>
						<span>Registro de SpringFest</span>
					</petclinic:menuItem>

				</sec:authorize>
				<!--LO QUE VE EL ADMIN			-->
				<sec:authorize access="hasAuthority('admin')">

					<petclinic:menuItem active="${name eq 'home'}" url="/"
						title="home page">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Inicio</span>
					</petclinic:menuItem>


					<petclinic:menuItem active="${name eq 'mifestival'}"
						url="/mifestival" title="find festival">
						<span class="glyphicon glyphicon-music" aria-hidden="true"></span>
						<span>Mi Festival</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'miperfil'}" url="/miperfil"
						title="mi  perfil">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mi Perfil</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'about'}" url="/about"
						title="about">
						<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
						<span>Sobre nosotros</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'artistas'}" url="/artistas"
						title="find artistas">
						<span class="glyphicon glyphicon-headphones" aria-hidden="true"></span>
						<span>Artistas</span>
					</petclinic:menuItem>
				</sec:authorize>

				<!--LO QUE VE EL SPONSOR			-->
				<sec:authorize access="hasAuthority('sponsor')">

					<petclinic:menuItem active="${name eq 'festivales'}" url="/festivales"
						title="home page">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Inicio</span>
					</petclinic:menuItem>
					
					<petclinic:menuItem active="${name eq 'mispuestos'}" url="/mispuestos"
						title="mis puestos">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mis Puestos</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'miperfil'}" url="/miperfil"
						title="mi  perfil">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mi Perfil</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'about'}" url="/about"
						title="about">
						<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
						<span>Sobre nosotros</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'artistas'}" url="/artistas"
						title="find artistas">
						<span class="glyphicon glyphicon-headphones" aria-hidden="true"></span>
						<span>Artistas</span>
					</petclinic:menuItem>
				</sec:authorize>
				<!--LO QUE VE EL USUARIO CLIENTE			-->
				<sec:authorize access="hasAuthority('usuario')">

					<petclinic:menuItem active="${name eq 'home'}" url="/festivales"
						title="home page">
						<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						<span>Inicio</span>
					</petclinic:menuItem>
					
					<petclinic:menuItem active="${name eq 'miperfil'}" url="/misentradas"
						title="mi  perfil">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mis Entradas</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'miperfil'}" url="/miperfil"
						title="mi  perfil">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mi Perfil</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'about'}" url="/about"
						title="about">
						<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
						<span>Sobre nosotros</span>
					</petclinic:menuItem>

					<petclinic:menuItem active="${name eq 'artistas'}" url="/artistas"
						title="find artistas">
						<span class="glyphicon glyphicon-headphones" aria-hidden="true"></span>
						<span>Artistas</span>
					</petclinic:menuItem>
				</sec:authorize>
				<!--AQUI ACABA LO QUE VE EL USUARIO CLIENTE			-->
			</ul>
			<ul class="nav navbar-nav navbar-right">

				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
