<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Copyright (c) 2009-2012 DuraSpace. All rights reserved.--%><%-- Status
Page: Home page placeholder.
--%><%-- Author: Daniel Bernstein --%><%@include
file="../include/libraries.jsp"%>
<tiles:insertDefinition
 name="page-base"
 flush="true">
  <tiles:putAttribute
   name="title">Whoops!</tiles:putAttribute>

  <tiles:putAttribute
   name="body">
    <h1>Whoops: we've run into a problem:</h1> 
    <p>
      ${description}
    </p>
    
    <p>
      For more information or help resolving this issue, please contact 
      <spring:message code="admin.email"/>.  Thank you for your patience.
    </p>  
  </tiles:putAttribute>
</tiles:insertDefinition>

