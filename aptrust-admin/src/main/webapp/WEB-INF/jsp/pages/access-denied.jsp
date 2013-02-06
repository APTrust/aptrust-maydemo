<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Copyright (c) 2009-2012 DuraSpace. All rights reserved.--%><%-- Status
Page: Home page placeholder.
--%><%-- Author: Daniel Bernstein --%><%@include
file="../include/libraries.jsp"%>
<tiles:insertDefinition
 name="app-base"
 flush="true">
  <tiles:putAttribute
   name="title">Access Denied 403</tiles:putAttribute>

  <tiles:putAttribute
   name="content">
    <h1>Access Denied - 403</h1> 
    <p>
    You are not authorized to view this page.
    </p>
  </tiles:putAttribute>
</tiles:insertDefinition>

