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
    <h1>Search Results</h1>
    <div id="facets">
      <h2>Filter Results By:</h2>

      <div class="facet">
        <h3>Ingest Date Range</h3>
      </div>

      <div class="facet">
        <h3>DPN Bound</h3>
      </div>

      <div class="facet">
        <h3>Access</h3>
      </div>

      <div class="facet">
        <h3>Health Check</h3>
      </div>

    </div>

    <div id="results">

      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check bad">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check bad">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
      <div class="result">
        <h2>
          <a href="${packageBaseUrl}">Lorem Ipsum</a>
        </h2>
        <p class="meta">
          <a href="${packageBaseUrl}">50 Objects</a> | Added 09/15/2012 |
          University of Virginia
        </p>
        <p class="health-check good">Last Health Check: 10/01/2012 9:00 AM</p>
      </div>
    </div>
  </tiles:putAttribute>
</tiles:insertDefinition>

