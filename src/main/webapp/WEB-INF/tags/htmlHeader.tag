<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- The above 4 meta tags must come first in the head; any other head content must come after these tags --%>

    <spring:url value="/resources/images/favicon.png" var="favicon"/>
    <link rel="shortcut icon" type="image/x-icon" href="${favicon}">

    <title>SpringFest</title>

    <%-- CSS generated from LESS --%>
    <spring:url value="/resources/css/petclinic.css" var="petclinicCss"/>
    <link href="${petclinicCss}" rel="stylesheet"/>


    <%-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries --%>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js%22%3E</script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js%22%3E</script>
    <![endif]-->

    <!-- Only datepicker is used -->
    <spring:url value="/webjars/jquery-ui/1.11.4/jquery-ui.min.css" var="jQueryUiCss"/>
    <link href="${jQueryUiCss}" rel="stylesheet"/>
    <spring:url value="/webjars/jquery-ui/1.11.4/jquery-ui.theme.min.css" var="jQueryUiThemeCss"/>
    <link href="${jQueryUiThemeCss}" rel="stylesheet"/>
     
     <!-- Only datetimepicker is used -->
    <spring:url value="/webjars/datetimepicker/2.5.20-1/jquery.datetimepicker.css" var="jQueryDTPCss"/>
    <link href="${jQueryDTPCss}" rel="stylesheet"/>
    
    
    <spring:url value="/webjars/datetimepicker/2.5.20-1/jquery.js" var="jQueryDTP"/>
	<script src="${jQueryDTP}"></script>
    
    <spring:url value="/webjars/datetimepicker/2.5.20-1/build/jquery.datetimepicker.full.min.js" var="DTPjS"/>
	<script src="${DTPjS}"></script>
    
     <!-- Only ckeditor is used -->

    <spring:url value="/webjars/ckeditor/4.14.0/standard/ckeditor.js" var="ckeditorJs"/>
	<script src="${ckeditorJs}"></script>
	
</head>