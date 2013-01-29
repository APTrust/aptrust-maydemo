<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Copyright (c) 2009-2012 DuraSpace. All rights reserved.--%>
<%-- Status
Page: Package Discovery
--%>
<%-- Author: Daniel Bernstein --%><%@include file="../include/libraries.jsp"%>
<tiles:importAttribute name="institution"/>
<c:url var="institutionBaseUrl" value="/html/${institution.id}"/>
<c:set var="packageBaseUrl" value="${institutionBaseUrl}/package"/>

<tiles:insertDefinition
  name="app-base"
  flush="true">


  <tiles:putAttribute
    name="content"
    cascade="true">
    <div>
      <form:form modelAttribute="searchParams" method="POST">
        <form:input placeholder="enter search terms" path="query" type="search" />
      </form:form>
    </div>
    <h1>Search Results</h1>
    <c:set var="facets" value="${queryResponse.facetFields}"/>
    <c:if test="${not empty facets}">
      <div id="facets">
        <c:forEach
          var="facet"
          items="${facets}">

          <div class="facet">
            <h3>${facet.name}</h3>
            <ul>
              <c:forEach
                var="value"
                items="${facet.values}">
                <li><a
                  href="?${results.queryString}&facet.${facet.name}=${value.name}">${value.name}
                    (${value.count})</a></li>

              </c:forEach>
            </ul>

          </div>

        </c:forEach>
      </div>
    </c:if>


    <div id="results">
      <c:set var="packages" value="${queryResponse.packageSummaries}"/>
      <c:choose>
        <c:when test="${not empty packages}">
          <c:forEach var="package" items="${packages}">
          <div class="result">
            <c:set var="packageUrl" value="${packageBaseUrl}/${package.id}"/>
            <h2>
              <a href="${packageUrl}">${package.name}</a>
            </h2>
            <p class="meta">
              <a href="${packageUrl}">${package.objectCount} Objects</a> | Added ${package.ingestDate} |  ${package.institutionName}
            </p>
            <p class="health-check ${package.healthCheckInfo.good ? 'good' : ''}">Last Health Check: ${package.healthCheckInfo.date}</p>
          </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          No packages found.        
        </c:otherwise>
      </c:choose>
     </div>
  </tiles:putAttribute>
</tiles:insertDefinition>

