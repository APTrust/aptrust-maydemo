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
    name="head-extension">
    <script>
    	$(function(){
    	    var objectSelect = $("#objectId");
    	    
    	    objectSelect.change(function(){
    	        $(this).closest("form").submit();
    	    });
    	    
    	    (function(){
        	    var optionList = $.makeArray(objectSelect.find("option"));
        	    var filterList = function(val){
    				var copy = optionList.slice();
    				objectSelect.empty();
          	        $.map(copy, function(obj,i){
          	            var option = $(obj);
          	            var title = option.html();
          	            if(title.indexOf(val) > -1){
          	                objectSelect.append(option);
          	            }
          	        });
        	    };
        	    
        	    var filter = $("#filter");
        	    filterList(filter.val());
        	    
          	    filter.keyup(function(){
          	        filterList($(this).val());
          	    });
    	        
    	    })();
  	    });
    </script>
  </tiles:putAttribute>



  <tiles:putAttribute
    name="content"
    cascade="true">

    <h1>Package Details for "${packageDetail.name}"</h1>

    <div id="package">

      <ul class="dates">
        <li>Ingested ${packageDetail.ingestDate} by ${packageDetail.ingestedBy}</li>
        <li>Modified ${packageDetail.modifiedDate} by ${packageDetail.modifiedBy}</li>
      </ul>

      <div id="package-items">
        <form>
        <input
          id="filter"
          name="filter"
          value="${filter}"
          type="text" 
          placeholder="Filter object by title">
          <select id="objectId" name="objectId" size="8">
          <c:forEach
            var="objectDescriptor"
            items="${packageDetail.objectDescriptors}">
            <option value="${objectDescriptor.id}" title="(${objectDescriptor.id}) ${objectDescriptor.title}"
                 <c:if test="${objectDetail.objectId == objectDescriptor.id}">
                  selected="true"
                 </c:if>
             >(${objectDescriptor.id}) ${objectDescriptor.title}</option>
          </c:forEach>
        </select>
        </form>
      </div>
    </div>

    <div id="object">
      <h2 id="object-title">${objectDetail.title}</h2>

      <h3>Object Properties</h3>
      <table
        class="object-properties"
        cellpadding="0"
        cellspacing="0">
        <tr>
          <th>Object ID</th>
          <td id="${objectDetail.objectId}">${objectDetail.localId}</td>
        </tr>

      </table>

      <h3>Data Streams</h3>
      <c:choose>
        <c:when test="${not empty objectDetail.contentSummaries}">
      <table class="data-streams">
        <thead>
          <tr>
            <th>Title</th>
            <th>last health check</th>
            <th>healthy</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="cs" items="${objectDetail.contentSummaries}">
          <tr>
            <td>${cs.name}</td>
            <td>${cs.lastFixityCheck}</td>
            <td>${cs.passed}</td>
                  <td><a
                    href="#"
                    onclick="alert('this feature is not yet implemented.')">Open</a></td>
                </tr>
          </c:forEach>
        </tbody>
      </table>

        </c:when>
        <c:otherwise>
          <p>There are no datastreams associated with this object.</p>
        </c:otherwise>        
      </c:choose>
    </div>
  </tiles:putAttribute>
</tiles:insertDefinition>

