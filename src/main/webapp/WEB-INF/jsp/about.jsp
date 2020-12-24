<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="about">

	<h1>SpringFest</h1>
	<h2>Integrantes</h2>
	<ul>
		<li>Bueno Gómez, Manuel</li>
		<li>Calvo Durán, Fernando</li>
		<li>González Boza, Enrique</li>
		<li>Manzano Dorado, Alejandro</li>
		<li>Rodríguez Santiago, Javier</li>
		<li>Santos Ortiz, Pablo</li>
	</ul>

	<h3>Descripción</h3>

	 <p>Aplicación web para gestionar festivales. </p>

	 <p>Servirá tanto para
		poder organizar cada festival, como para comprar entradas y alquilar
		puestos de venta de productos en un festival.</p>
		
  	 <p>La aplicación se dividirá en módulos comunes para todos los
		festivales, como la sección de compra de entradas de diferentes tipos,
		las ofertas aplicables a cada festival, poder ver cada cartel y los
		horarios de los conciertos, entre otras muchas cosas.</p>
	
	<p>Se pretende facilitar la organización de cualquiera de los
		festivales en diversos aspectos, simplificando algunos que, de no ser
		vía online, serían más complicados, como la gestión de los puestos por
		parte de los dueños de estos o la posibilidad de poder elegir ofertas
		con antelación y no tener que hacer cola fisica para comprar alguno de
		estos paquetes una vez llegados al festival.</p>
		
	<p> Si quieres usar nuestra aplicación para gestionar tu festival, mándanos un correo a springfest@springfest.com </p>


</petclinic:layout>
