<?xml version="1.0" encoding="ISO-8859-1"?>
<%@include file="../include/libraries.jsp"%>
<div>
  <div id="header">
    <div id="logo">AP Trust Admin</div>
  </div>

  <div id="content">
    <tiles:insertAttribute name="content" />
  </div>

  <div id="footer">
    <ul>
      <li><a href="http://www.aptrust.org/">AP Trust</a> Admin: v${project.version} ${buildNumber}</li>
      <li><a href="http://www.duraspace.org">DuraSpace</a></li>

    </ul>
  </div>
</div>

