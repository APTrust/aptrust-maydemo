<sec:authorize access="hasAnyRole('ROLE_ROOT','ROLE_USER')">
  <p>
    Welcome, ${principal.username } ! <a
      href="${pageContext.request.contextPath}/j_spring_security_logout">Sign
      Out</a>
  </p>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
  <a href="${pageContext.request.contextPath}/login">Sign In</a>
</sec:authorize>
