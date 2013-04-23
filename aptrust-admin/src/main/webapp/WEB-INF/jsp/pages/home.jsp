<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Author: Daniel Bernstein --%>
<%@include file="../include/libraries.jsp"%>
<tiles:insertDefinition
  name="page-base"
  flush="true">
  <tiles:putAttribute name="title">AP Trust Admin</tiles:putAttribute>

  <tiles:putAttribute name="body">
    <header role="banner">
      <hgroup>
        <h2>AP Trust</h2>
      </hgroup>
      <div id="courtesy">
          <%@include file="../include/signin-links.jsp"%>
      </div>
    </header>

    <div role="main">
      <nav id="primary-nav" role="navigation">
        
      </nav>
      <%@include file="../include/flash.jsp"%>

      <h2>Select an institution:</h2>
      <ul id="institution-list">
        <c:forEach var="i" items="${institutions}">
        <li><a href="${pageContext.request.contextPath}/html/${i.id}">${i.fullName}</a></li>
        </c:forEach>
      
      </ul>

      <sec:authorize access="hasRole('ROLE_ROOT')">
      <section>
        <h2>Admin Tools</h2>
        <ul>
          <li>
            <form method="post">
              <input
                name="action"
                type="hidden"
                value="rebuildIndex" />
              <button type="submit">Rebuild Index</button>
            </form>
          </li>
        </ul>      
      </section>
      </sec:authorize>

    </div>
    
    
  </tiles:putAttribute>
</tiles:insertDefinition>

