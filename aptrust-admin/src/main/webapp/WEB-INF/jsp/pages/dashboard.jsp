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

        var _getSummaries = function(spaceId){


            return {
                        "summaries" : [
                                {
                                    "date" : 1362187371000,
                                    "reportId" : "report/storage-report-2013-03-02T01:22:51.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1362092722000,
                                    "reportId" : "report/storage-report-2013-02-28T23:05:22.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1362090852000,
                                    "reportId" : "report/storage-report-2013-02-28T22:34:12.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1362087938000,
                                    "reportId" : "report/storage-report-2013-02-28T21:45:38.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1362084357000,
                                    "reportId" : "report/storage-report-2013-02-28T20:45:57.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1361832270000,
                                    "reportId" : "report/storage-report-2013-02-25T22:44:30.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1361828029000,
                                    "reportId" : "report/storage-report-2013-02-25T21:33:49.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1361826884000,
                                    "reportId" : "report/storage-report-2013-02-25T21:14:44.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1361825892000,
                                    "reportId" : "report/storage-report-2013-02-25T20:58:12.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1361571814000,
                                    "reportId" : "report/storage-report-2013-02-22T22:23:34.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1361566849000,
                                    "reportId" : "report/storage-report-2013-02-22T21:00:49.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1361492057000,
                                    "reportId" : "report/storage-report-2013-02-22T00:14:17.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1360977035000,
                                    "reportId" : "report/storage-report-2013-02-16T01:10:35.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1360372250000,
                                    "reportId" : "report/storage-report-2013-02-09T01:10:50.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1359767408000,
                                    "reportId" : "report/storage-report-2013-02-02T01:10:08.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1359162703000,
                                    "reportId" : "report/storage-report-2013-01-26T01:11:43.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1358832583000,
                                    "reportId" : "report/storage-report-2013-01-22T05:29:43.xml",
                                    "totalItems" : 22,
                                    "totalSize" : 60472157
                                },
                                {
                                    "date" : 1357323260000,
                                    "reportId" : "report/storage-report-2013-01-04T18:14:20.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357320521000,
                                    "reportId" : "report/storage-report-2013-01-04T17:28:41.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357317678000,
                                    "reportId" : "report/storage-report-2013-01-04T16:41:18.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357315048000,
                                    "reportId" : "report/storage-report-2013-01-04T15:57:28.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357312754000,
                                    "reportId" : "report/storage-report-2013-01-04T15:19:14.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357310227000,
                                    "reportId" : "report/storage-report-2013-01-04T14:37:07.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357307807000,
                                    "reportId" : "report/storage-report-2013-01-04T13:56:47.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357305341000,
                                    "reportId" : "report/storage-report-2013-01-04T13:15:41.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357303043000,
                                    "reportId" : "report/storage-report-2013-01-04T12:37:23.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357300493000,
                                    "reportId" : "report/storage-report-2013-01-04T11:54:53.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357298248000,
                                    "reportId" : "report/storage-report-2013-01-04T11:17:28.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357295735000,
                                    "reportId" : "report/storage-report-2013-01-04T10:35:35.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357293319000,
                                    "reportId" : "report/storage-report-2013-01-04T09:55:19.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357290898000,
                                    "reportId" : "report/storage-report-2013-01-04T09:14:58.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357288507000,
                                    "reportId" : "report/storage-report-2013-01-04T08:35:07.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357286164000,
                                    "reportId" : "report/storage-report-2013-01-04T07:56:04.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357283771000,
                                    "reportId" : "report/storage-report-2013-01-04T07:16:11.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357281484000,
                                    "reportId" : "report/storage-report-2013-01-04T06:38:04.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357279019000,
                                    "reportId" : "report/storage-report-2013-01-04T05:56:59.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357276639000,
                                    "reportId" : "report/storage-report-2013-01-04T05:17:19.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357274300000,
                                    "reportId" : "report/storage-report-2013-01-04T04:38:20.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357271616000,
                                    "reportId" : "report/storage-report-2013-01-04T03:53:36.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357269075000,
                                    "reportId" : "report/storage-report-2013-01-04T03:11:15.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357266395000,
                                    "reportId" : "report/storage-report-2013-01-04T02:26:35.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357263714000,
                                    "reportId" : "report/storage-report-2013-01-04T01:41:54.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357260925000,
                                    "reportId" : "report/storage-report-2013-01-04T00:55:25.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357258287000,
                                    "reportId" : "report/storage-report-2013-01-04T00:11:27.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357255673000,
                                    "reportId" : "report/storage-report-2013-01-03T23:27:53.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357252449000,
                                    "reportId" : "report/storage-report-2013-01-03T22:34:09.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357249676000,
                                    "reportId" : "report/storage-report-2013-01-03T21:47:56.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357246742000,
                                    "reportId" : "report/storage-report-2013-01-03T20:59:02.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357243599000,
                                    "reportId" : "report/storage-report-2013-01-03T20:06:39.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357240756000,
                                    "reportId" : "report/storage-report-2013-01-03T19:19:16.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357237548000,
                                    "reportId" : "report/storage-report-2013-01-03T18:25:48.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357234533000,
                                    "reportId" : "report/storage-report-2013-01-03T17:35:33.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357231637000,
                                    "reportId" : "report/storage-report-2013-01-03T16:47:17.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357228831000,
                                    "reportId" : "report/storage-report-2013-01-03T16:00:31.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357226385000,
                                    "reportId" : "report/storage-report-2013-01-03T15:19:45.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357223822000,
                                    "reportId" : "report/storage-report-2013-01-03T14:37:02.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                },
                                {
                                    "date" : 1357221405000,
                                    "reportId" : "report/storage-report-2013-01-03T13:56:45.xml",
                                    "totalItems" : 21,
                                    "totalSize" : 60471892
                                } ]
                    };

                };

                
                
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
                
                $.when(_getSummaries('${institution.id}staging')).done(function(result) {
                    var summaries = result.summaries;
                    _initTimeSeriesGraph(summaries, "chart-staging", "legend-staging");
                }).fail(function(err) {
                    alert("failed to retrieve time series");
                });

                $.when(_getSummaries('${institution.id}')).done(function(result) {
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

