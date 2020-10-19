<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags%22%%3E
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt%22%%3E
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core%22%%3E
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<petclinic:layout pageName="home">
    <h2>
        <fmt:message key="welcome" />
    </h2>
    <div class="row">
        <h2>ProyectoDP1 ${title}</h2>
        <p>
        <h2>Grupo ${Grupo}</h2>
        </p>
        <p><ul>
            <c:forEach items="${persons}" var="persona">
                <li>${persona.firstName}${persona.lastName}</li>
            </c:forEach>
        </ul></p>
    </div>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/pets.png" htmlEscape="true"
                var="petsImage" />
            <img class="img-responsive" src="${petsImage}" />
        </div>
    </div>
</petclinic:layout>