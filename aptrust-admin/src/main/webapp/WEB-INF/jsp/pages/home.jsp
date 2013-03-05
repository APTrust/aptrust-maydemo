<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Author: Daniel Bernstein --%><%@include file="../include/libraries.jsp"%>
<tiles:insertDefinition
  name="page-base"
  flush="true">
  <tiles:putAttribute name="title">AP Trust Admin</tiles:putAttribute>

  <tiles:putAttribute name="body">
    <header role="banner">
      <hgroup>
        <h1>AP Trust</h1>
      </hgroup>
      <div id="courtesy"></div>
    </header>

    <div role="main">
      <nav id="primary-nav" role="navigation">
        
      </nav>
    
      <h2>Select an institution:</h2>
      <ul>
        <c:forEach var="i" items="${institutions}">
        <li><a href="${pageContext.request.contextPath}/html/${i.id}">${i.fullName}</a></li>
        </c:forEach>
      
      </ul>
    </div>
  </tiles:putAttribute>
</tiles:insertDefinition>

