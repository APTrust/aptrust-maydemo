<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Copyright (c) 2009-2012 DuraSpace. All rights reserved.--%><%-- Status
Page: Home page placeholder.
--%><%-- Author: Daniel Bernstein --%><%@include
file="../include/libraries.jsp"%>
<tiles:insertDefinition
 name="app-base"
 flush="true">
  <tiles:putAttribute
   name="title">Oops!</tiles:putAttribute>

  <tiles:putAttribute
   name="content">
    <h1>Error</h1> 
  </tiles:putAttribute>
</tiles:insertDefinition>

