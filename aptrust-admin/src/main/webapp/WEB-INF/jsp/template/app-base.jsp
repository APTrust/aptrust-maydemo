<?xml version="1.0" encoding="ISO-8859-1"?>
<%@include file="../include/libraries.jsp"%>

<tiles:importAttribute name="institution" scope="request"/>
<c:url var="institutionBaseUrl" value="/html/${institution.id}"/>
<c:set var="discoveryUrl" value="${institutionBaseUrl}/discovery"/>

<sec:authentication property="principal" var="principal"  />

<header role="banner">
  <hgroup>
    <h1><a href="${pageContext.request.contextPath}">AP Trust</a></h1>
    <h2>${institution.fullName }</h2>
  </hgroup>
  <div id="courtesy">
    <sec:authorize access="hasAnyRole('ROLE_ROOT','ROLE_USER')" >
    <p>
      Welcome, ${principal.username } ! <a href="${pageContext.request.contextPath}/j_spring_security_logout">Sign Out</a>
    </p>
    <form
      action="${discoveryUrl}"
      method="get"
      accept-charset="utf-8">
      <input
        type="search"
        name="query"
        id="search"
        placeholder="enter search terms"
        value=""/>
    </form>
    </sec:authorize>
    
  </div>
</header>
<nav
  id="primary-nav"
  role="navigation">
  <ul>
    <li><a href="${institutionBaseUrl}">Dashboard</a></li>
    <li><a href="${discoveryUrl}">Discovery</a></li>
  </ul>
</nav>
<div role="main">
  <tiles:insertAttribute name="content" />
</div>

