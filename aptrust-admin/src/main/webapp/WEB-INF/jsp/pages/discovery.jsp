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
    name="head-extension">
    <script>
    $(function() {
      $( "#startDate, #endDate" )
      	.datepicker()
      	.datepicker("option", "dateFormat","mm/dd/yy");
    });
    </script>
  
  </tiles:putAttribute>
  <tiles:putAttribute
    name="content"
    cascade="true">
    <c:set var="facets" value="${queryResponse.facetFields}"/>
    <c:if test="${not empty facets}">
      <div id="facets">
          <div class="facet">
            <h3>Creation Date (Range)</h3>
          <form:form  method="GET"  modelAttribute="searchParams" >
            <fieldset>
                <form:input id="startDate" path="startDate" placeholder="Start: e.g. 01/01/1999" />
                <form:input id="endDate" path="endDate" placeholder="End: e.g. 12/31/2012"/>
                <button type="submit">go</button>

            <c:forEach items="${searchParams.constraints}" var="c" varStatus="status"> 
              <input type="hidden" name="constraints[${status.index}].name" value="${c.name}"/>
              <input type="hidden" name="constraints[${status.index}].value" value="${c.value}"/>
            </c:forEach>

            </fieldset>
          </form:form>
        </div>
      
        <c:forEach
          var="facet"
          items="${facets}">
          <c:if test="${not empty facet.values}">
          <div class="facet">
            <h3><spring:message code="${facet.name}" text="${facet.name}"/></h3>
            <ul>
              <c:forEach
                var="value"
                items="${facet.values}">
                <li>
                  <a href="?${searchParams.toQueryStringWithConstraint(facet.name, value.name)}">
                   <spring:message code="${value.name}" text="${value.name}"/> (${value.count})
                  </a>
                </li>
              </c:forEach>
            </ul>
          </div>
          </c:if>
        </c:forEach>
      </div>
    </c:if>


    <div id="results">
      <div >
        <form:form modelAttribute="searchParams" method="GET">
          <form:input id="search-text-field" placeholder="enter search terms: partial word search with *" path="query"  />
        </form:form>
      </div>
      
      <c:if test="${not empty searchParams.constraints or not empty searchParams.startDate or not empty searchParams.endDate}">
        <div id="filters">
            <h3>Filters:</h3> 
            <ul>
            <c:forEach items="${searchParams.constraints}" var="c"> 
              <li>
                
                <div>
                <a href="?${searchParams.toQueryStringWithout(c)}">
                <div><spring:message code="${c.name}" text="${c.name}"/></div>
                <div>
                <spring:message code="${c.value}" text="${c.value}"/>
                <span class="x">X</span>
                </div>
                </a>
                </div>
                
              </li>
            </c:forEach>
            <c:if test="${not empty searchParams.startDate}">
              <li>
                <div>
                <a href="?${searchParams.toQueryStringWithout(null, true, false)}">
                <div><spring:message code="after" text="After"/></div>
                <div>
                <fmt:formatDate pattern="MM/dd/yyyy" value="${searchParams.startDate}"/>
                <span class="x">X</span>
                </div>
                </a>
                </div>
              </li>
            </c:if>
            <c:if test="${not empty searchParams.endDate}">
              <li>
                <div>
                <a href="?${searchParams.toQueryStringWithout(null, false, true)}">
                <div><spring:message code="before" text="Before"/></div>
                <div>
                <fmt:formatDate pattern="MM/dd/yyyy" value="${searchParams.endDate}"/>
                <span class="x">X</span>
                </div>
                </a>
                </div>
              </li>
            </c:if>

            </ul>
        </div>
      </c:if>
          
      <c:set var="packages" value="${queryResponse.packageSummaries}"/>
      <c:choose>
        <c:when test="${not empty packages}">
          <div>
            <p>
              <c:set var="endIndex" value="${searchParams.startIndex + packages.size()}"/>
              <c:set var="nextIndex" value="${endIndex >= queryResponse.totalFound ? -1 : endIndex}"/>
              <c:set var="previousIndex" value="${searchParams.startIndex-searchParams.pageSize < 0 ? 0 : searchParams.startIndex-searchParams.pageSize}"/>
              
              ${searchParams.startIndex + 1} - ${endIndex} of ${queryResponse.totalFound} matches                   

               <c:if test="${searchParams.startIndex > 0}">
                <a href="?startIndex=${previousIndex}&${searchParams.toQueryStringWithout(null)}">Previous</a> 
              </c:if>
              <c:if test="${nextIndex > 0}">
                <a href="?startIndex=${nextIndex}&${searchParams.toQueryStringWithout(null)}">Next</a>
              </c:if>              
            </p>
          </div>
        
          <c:forEach var="package" items="${packages}">
          <div class="result">
            <c:set var="packageUrl" value="${packageBaseUrl}/${package.id}"/>
            <h2>
              <a href="${packageUrl}">${package.name}</a>
            </h2>
            <p class="meta">
              <a href="${packageUrl}">${package.objectCount} Objects</a> | Added ${package.ingestDate} |  ${package.institutionName}
            </p>
            <p class="health-check ${package.healthCheckInfo.good ? 'good' : 'bad'}">Last Health Check: ${package.healthCheckInfo.date}</p>
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

