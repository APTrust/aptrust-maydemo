<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Copyright (c) 2009-2012 DuraSpace. All rights reserved.--%>
<%-- Status
Page: Home page placeholder.
--%>
<%-- Author: Daniel Bernstein --%><%@include file="../include/libraries.jsp"%>
<tiles:importAttribute name="institution" scope="request"/>
<c:url var="institutionBaseUrl" value="/html/${institution.id}"/>
<c:set var="discoveryUrl" value="${institutionBaseUrl}/discovery"/>
<c:set var="packageBaseUrl" value="${institutionBaseUrl}/package"/>


<tiles:insertDefinition  
  name="app-base"
  flush="true">



  <tiles:putAttribute
    name="content"
    cascade="true">


    <section id="institutional-activity">
      <h1>Institutional Activity</h1>
      <p>
        ${institution.fullName} has <strong>${summary.packageCount}
          packages</strong> containing <strong>${summary.objectCount}
          objects</strong>, <br/>using <strong><fmt:formatNumber value="${summary.bytesUsed /( 1024*1024*1024)}" minFractionDigits="1" maxFractionDigits="1"/> GBs of storage</strong>.
      </p>
      <ul>
        <li><a href="${discoveryUrl}">${summary.dpnBoundPackageCount}
            packages</a> are <strong>DPN Bound</strong></li>
        <li><a href="${discoveryUrl}">${summary.publicPackageCount} packages</a>
          are <strong>Public</strong></li>
        <li><a href="${discoveryUrl}">${summary.privatePackageCount}
            packages</a> are <strong>Private</strong></li>
        <li><a href="${discoveryUrl}">${summary.institutionPackageCount}
            packages</a> are <strong>Institution Only</strong></li>
        <li><a href="${discoveryUrl}">${summary.failedPackageCount}
            packages</a> have <strong>Failed Health Checks</strong></li>
      </ul>
      <p>
        <a href="${discoveryUrl}">Browse ${institution.fullName} Packages</a>
      </p>

      <h2>Monthly Activity</h2>
      <div id="chart"></div>
    </section>

    <section id="recent-ingests">
      <h1>Recent Ingests</h1>
      <c:choose>
        <c:when test="${empty recentIngests}">
          <p class="info">There are no recent ingests.</p>
        </c:when>
        <c:otherwise>
          <ul class="ingests">
            <c:forEach
              var="ingest"
              items="${recentIngests}">
              <li class="ingest ${fn:toLowerCase(ingest.status)}">
                <h2>${ingest.name}</h2>
                <p class="info">
                  Started <strong class="date date-started">${ingest.startDate}
                    </strong> by <strong class="user">${ingest.initiatingUser}</strong>
                </p>
                <p class="progress">
                  <progress
                    max="100"
                    value="${ingest.progress}"></progress>
                  <strong> <c:choose>
                      <c:when test="${not empty ingest.message}">
                        ${ingest.message}
                      </c:when>
                      <c:otherwise>
                         ${ingest.progress}% done
                      </c:otherwise>
                    </c:choose>
                  </strong>
                </p>
              </li>
            </c:forEach>
          </ul>
        </c:otherwise>
      </c:choose>
    </section>
  </tiles:putAttribute>
</tiles:insertDefinition>

