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
    name="head-extension">

  <script type="text/javascript" src="https://uva.duracloud.org/duradmin/jquery/dc/ext/jquery.dc.common.js"></script>
  <script type="text/javascript" src="https://uva.duracloud.org/duradmin/jquery/dc/ext/jquery.dc.chart.js"></script>
  <script type="text/javascript" src="https://uva.duracloud.org/duradmin/jquery/dc/api/durastore-api.js"></script>

  <script
    type="text/javascript"
    src="https://uva.duracloud.org/duradmin/jquery/plugins/jquery.flot/jquery.flot.js"></script>


  <script>
  	$(function(){

        var that = this;


        var _getSummaries = function(/*boolean*/ staging){
			
	        return $.ajax("${pageContext.request.contextPath}/storage-report/${institution.id}?staging="+staging, {dataType:"json"});
        }

        var _initTimeSeriesGraph = function(summaries, graphElement, legendElement){
                    var that = this;
                    var min = null;
                    var max = 0;
                    var sizeData = [];
                    var countData = [];
                    var summariesGraph = $("#"+graphElement);
                    var countMin = null;
                    $.each(summaries, function(i,summary){
                        
                        if (summary.date > max){
                            max = summary.date;
                        }
                        
                        if(min == null || min > summary.date){
                            min = summary.date;
                        }
                        
                        if(countMin == null || countMin > summary.totalItems){
                            countMin = summary.totalItems;
                        }
                        
                        
                        sizeData.push([summary.date, summary.totalSize, summary.reportId]);
                        countData.push([summary.date, summary.totalItems, summary.reportId]);

                    });
                    
                    var sizeSeries = {
                        label: "Bytes",
                        lines: { show: true, lineWidth:5},
                        points: { show: true},                    
                        data: sizeData,    
                        yaxis: 1
                    };
                    
                    var countSeries = {
                        label: "Files",
                        lines: { show: true, lineWidth:5},
                        points: { show: true},                    
                        data: countData,
                        yaxis: 2
                    };
                    
                    var plot = $.plot(summariesGraph, [sizeSeries,countSeries], {
                        xaxes: [{
                            color: "#EEE",
                            mode: "time",
                            min: min,
                            max: max
                        }],
                        yaxes: [
                         {
                            color: "#EEE",
                            minTickSize: 1,
                            show: true, 
                            tickFormatter: function(tickValue, axis){
                                return dc.formatBytes(tickValue);
                            }
                         }, 
                         {
                             color: "#EEE",
                             tickDecimals: 0,
                             minTickSize: 1,
                             show: true, 
                             position: "right",
                             tickFormatter: function(tickValue, axis){
                                 return tickValue + " files";
                             },
                             
                         }
                        ],
                        legend: {
                            show: true, 
                            container: $("#"+legendElement),
                            noColumns: 7,
                        },
                        
                        grid: { hoverable: true, clickable: true },
                    });   
                    
                    var previousPoint = null;
                    
                    summariesGraph.bind("plothover", function (event, pos, item) {
                        $("#x").text(pos.x.toFixed(2));
                        $("#y").text(pos.y.toFixed(2));
                        
                        if (item) {
                            if (previousPoint != item.dataIndex) {
                                previousPoint = item.dataIndex;
                                
                                $("#tooltip").remove();
                                var x = parseInt(item.datapoint[0]),
                                    y = item.datapoint[1].toFixed(0),
                                    reportId = item.series.data[item.dataIndex][2];
                                var value = item.seriesIndex == 0 ? dc.formatBytes(y) : y + " Items";
                                
                                dc.chart.showTooltip(item.pageX, item.pageY,
                                             new Date(x).toString("MMM d yyyy")  + " - " +  value);
                            }
                            
                        }else {
                            $("#tooltip").remove();
                            previousPoint = null;            
                        }
                    });
                };
                
                $.when(_getSummaries(true)).done(function(result) {
                    var summaries = result.summaries;
                    _initTimeSeriesGraph(summaries, "chart-staging", "legend-staging");
                }).fail(function(err) {
                    alert("failed to retrieve time series");
                });

                $.when(_getSummaries()).done(function(result) {
                    var summaries = result.summaries;
                    _initTimeSeriesGraph(summaries, "chart", "legend");
                }).fail(function(err) {
                    alert("failed to retrieve time series");
                });

            });
        </script>    
        

  </tiles:putAttribute>
  

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
        <li><a href="${discoveryUrl}?constraints[0].name=dpn_bound&constraints[0].value=true">${summary.dpnBoundPackageCount}
            packages</a> are <strong>DPN Bound</strong></li>
        <li><a href="${discoveryUrl}?constraints[0].name=access_control_policy&constraints[0].value=world">${summary.publicPackageCount} packages</a>
          are <strong>Public</strong></li>
        <li><a href="${discoveryUrl}constraints[0].name=access_control_policy&constraints[0].value=private">${summary.privatePackageCount}
            packages</a> are <strong>Private</strong></li>
        <li><a href="${discoveryUrl}?constraints[0].name=institution_only&constraints[0].value=true">${summary.institutionPackageCount}
            packages</a> are <strong>Institution Only</strong></li>
        <li><a href="${discoveryUrl}?constraints[0].name=failed_health_check&constraints[0].value=true">${summary.failedPackageCount}
            packages</a> have <strong>Failed Health Checks</strong></li>
      </ul>
      <p>
        <a href="${discoveryUrl}">Browse ${institution.fullName} Packages</a>
      </p>

      <div id="monthly-activity">
        <h2>Repository Storage</h2>
        <div
          id="chart"
          class="chart"></div>
        <div
          id="legend"
          class="legend"></div>
      </div>

      <div id="monthly-activity">
        <h2>Staging Storage</h2>
        <div
          id="chart-staging"
          class="chart"></div>
        <div
          id="legend-staging"
          class="legend"></div>
      </div>

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

