<%-- Copyright (c) 2009-2013 AP Trust. All rights reserved.--%>
<%-- Author: Daniel Bernstein --%>
<%@include file="./libraries.jsp"%>

<c:if test="${not empty message}">
  <div class="${fn:toLowerCase(message.severity)}">
      <p>
                ${message.text}
      </p>
  </div>
</c:if>
