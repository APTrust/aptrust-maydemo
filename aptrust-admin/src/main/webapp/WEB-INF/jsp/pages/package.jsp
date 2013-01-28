<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Copyright (c) 2009-2012 DuraSpace. All rights reserved.--%>
<%-- Status
Page: Home page placeholder.
--%>
<%-- Author: Daniel Bernstein --%><%@include file="../include/libraries.jsp"%>
<tiles:insertDefinition
  name="app-base"
  flush="true">


  <tiles:putAttribute
    name="content"
    cascade="true">

            <h1>Package Details for "${packageDetail.name}"</h1>

        <div id="package">

            <ul class="dates">
                <li>Ingested ${packageDetail.ingestDate} by ??</li>
                <li>Modified ?? by ??</li>
            </ul>

            <div id="package-items">
                <input type="text" placeholder="Filter by Title">
                <select>
                    <c:forEach var="objectDescriptor" items="${packageDetail.objectDescriptors}">
                      <option value="${objectDescriptor.id}">${objectDescriptor.title}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        
        <div id="object">
            <em class="kicker">Object in "${packageDetail.name}"</em>
            <h2>${objectDetail.title}</h2>

            <h3>Object Properties</h3>
            <table class="object-properties" cellpadding="0" cellspacing="0">
                <tr>
                    <th>Property Name</th>
                    <td>Lorem ipsum dolor sit amet, consectetur adipisicing elit</td>
                </tr>
            </table>

            <h3>Data Streams</h3>
            
            <table class="data-streams">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Date Created</th>
                        <th>Type</th>
                        <th>Size</th>
                        <th> </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Dublin Core Record</td>
                        <td>2004-12-10T00:21:50.000Z</td>
                        <td>text/xml</td>
                        <td>488</td>
                        <td><a href="#">Open</a></td>
                    </tr>
                    <tr>
                        <td>Fedora Object-to-Object</td>
                        <td>2004-12-10T00:21:50.000Z</td>
                        <td>text/xml</td>
                        <td>752</td>
                        <td><a href="#">Open</a></td>
                    </tr>
                    <tr>
                        <td>Image of Pavilion III, University of Virginia</td>
                        <td>2004-12-10T00:21:50.000Z</td>
                        <td>image/x-mrsid-image</td>
                        <td>752</td>
                        <td><a href="#">Open</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
  </tiles:putAttribute>
</tiles:insertDefinition>

