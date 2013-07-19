<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="#">Spring MVC Web Gallery</a>

            <div class="nav-collapse collapse">

                <sec:authorize access="isAnonymous()">
                <ul class="nav"> <!-- TODO autogenerated li class="active" -->
                    <li><a href="">Home</a></li>
                    <li><a href="#signin" role="button" data-toggle="modal">Sign In</a></li>
                    <li><a href="#register" role="button" data-toggle="modal">Register</a></li>
                </ul>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                <ul class="nav">
                    <li><a href="${pageContext.request.contextPath}/photostream">Photostream</a></li>
                    <li><a href="${pageContext.request.contextPath}/image-uploader" role="button">Uploader</a></li>
                    <li><a href="${pageContext.request.contextPath}/profile" role="button">Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/photosets" role="button">Photosets</a></li>
                    <li><a href="${pageContext.request.contextPath}/j_spring_security_logout" role="button">Logout</a></li>
                    <sec:authorize ifAllGranted="ROLE_ADMIN">
                        <li><a href="admin" role="button">Admin Dashboard</a></li>
                    </sec:authorize>
                </ul>
                </sec:authorize>

            </div>

        </div>
    </div>
</div>