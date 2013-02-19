<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Author: Daniel Bernstein --%><%@include file="../include/libraries.jsp"%>
<tiles:insertDefinition
  name="page-base"
  flush="true">
  <tiles:putAttribute name="title">AP Trust</tiles:putAttribute>

  <tiles:putAttribute name="body">
    <header role="banner">
      <hgroup>
        <h1>Access Denied</h1>
      </hgroup>
      <div id="courtesy"></div>
    </header>

    <div role="main">
      <nav id="primary-nav" role="navigation">
        
      </nav>

      <h2>Access Denied</h2>
        <p>
          You are not authorized to access this resource. Please contact admin@aptrust.org if you 
          need assistance.
        </p>      

      </section>
      
  </tiles:putAttribute>
</tiles:insertDefinition>

