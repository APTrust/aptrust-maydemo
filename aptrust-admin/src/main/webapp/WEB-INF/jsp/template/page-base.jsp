<%-- Copyright (c) 2009-2012 AP Trust. All rights reserved.--%>
<%-- Template base for all pages --%>
<%-- Author: Daniel Bernstein --%>

<%@include file="../include/libraries.jsp"%>


<html>
<jsp:directive.page contentType="text/html; charset=utf-8" />

<head>
    <meta charset="utf-8">
    <title>AP Trust Admin</title>
<link
  rel="stylesheet"
  type="text/css"
  href="${pageContext.request.contextPath}/static/css/styles.css" />
  <link
  rel="stylesheet"
  type="text/css"
  href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
  
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/init.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<tiles:insertAttribute name="head-extension" ignore="true"/>

</head>
<body>
  <tiles:insertAttribute name="body" />

<footer role="banner">
<hgroup>
<ul>
  <li>APTrust Admin v.<spring:message code="version"/> rev.<spring:message code="revision"/></li>
</ul>
</hgroup>

</footer>
</body>
</html>
