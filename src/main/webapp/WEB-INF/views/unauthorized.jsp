<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/validation-errors.css">
    <jsp:include page="elements/common-head.jsp"/>
    <title>Spring MVC Web Gallery Main Page</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container">
    <h1>Unauthorized</h1>

    <p>Please, login or register and login.</p>
</div>

<!-- Modal Windows -->
<jsp:include page="elements/unauthorized-login-modal-window.jsp"/>
<jsp:include page="elements/unauthorized-register-modal-window.jsp"/>

<jsp:include page="elements/common-foot-javascript.jsp"/>

<script src="${pageContext.request.contextPath}/static/libs/jquery.validate.min.js"></script>

<script src="${pageContext.request.contextPath}/static/js/unauthorized.js"></script>
<script src="${pageContext.request.contextPath}/static/js/unauthorized-forms-validation-rules.js"></script>
</body>
</html>