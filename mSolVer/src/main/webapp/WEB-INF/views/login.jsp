<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false"%>
<%@ include file="/WEB-INF/include/header.jspf"  %>
<!DOCTYPE html >
<html>
<head>
<title>Login</title>
</head>
<body>
      <br><br>
      <div class="container text-center">
          <h1>MSolution Login</h1><br>
      </div>
      <div class="container col-md-4">
	      <form class="px-4 py-3" action='<c:url value="/login"/>' method="post">
	          <div class="form-group">
	              <label for="exampleDropdownFormEmail1">ID</label>
	              <input type="text" class="form-control" name="userId" placeholder="userId" value="${userId}">
	          </div>
	          <div class="form-group">
	              <label for="exampleDropdownFormPassword1">Password</label>
	              <input type="password" class="form-control" name="password" placeholder="Password" value="${password}">
	          </div>
				<c:if test="${not empty ERRORMSG}">
					<font color="red">
				  		<p>Your login attempt was not successful due to <br/>
				  		${ERRORMSG}</p>
					</font>
				</c:if>
	          <div class="form-check">
	              <label class="form-check-label">
	              <input type="checkbox" class="form-check-input">
	              Remember me
	              </label>
	          </div>
	          <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
	          <button type="submit" class="btn btn-primary">Sign in</button>
	      </form>
	      <div class="dropdown-divider"></div>
	      <a class="dropdown-item" href="#">New around here? Sign up</a>
	      <a class="dropdown-item" href="#">Forgot password?</a>
	  </div>
</body>
</html>
