<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="about">

	<h1>SpringFest</h1>
	<h2>Integrantes</h2>
	<ul>
		<li>Bueno G�mez, Manuel</li>
		<li>Calvo Dur�n, Fernando</li>
		<li>Gonz�lez Boza, Enrique</li>
		<li>Manzano Dorado, Alejandro</li>
		<li>Rodr�guez Santiago, Javier</li>
		<li>Santos Ortiz, Pablo</li>
	</ul>

	<h3>Descripci�n</h3>

	 <p>Aplicaci�n web para gestionar festivales. </p>

	 <p>Servir� tanto para
		poder organizar cada festival, como para comprar entradas y alquilar
		puestos de venta de productos en un festival.</p>
		
  	 <p>La aplicaci�n se dividir� en m�dulos comunes para todos los
		festivales, como la secci�n de compra de entradas de diferentes tipos,
		las ofertas aplicables a cada festival, poder ver cada cartel y los
		horarios de los conciertos, entre otras muchas cosas.</p>
	
	<p>Se pretende facilitar la organizaci�n de cualquiera de los
		festivales en diversos aspectos, simplificando algunos que, de no ser
		v�a online, ser�an m�s complicados, como la gesti�n de los puestos por
		parte de los due�os de estos o la posibilidad de poder elegir ofertas
		con antelaci�n y no tener que hacer cola fisica para comprar alguno de
		estos paquetes una vez llegados al festival.</p>
		
	<p> Si quieres usar nuestra aplicaci�n para gestionar tu festival, m�ndanos un correo a springfest@springfest.com </p>


</petclinic:layout>
