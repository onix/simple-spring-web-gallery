<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/validation-errors.css">
    <jsp:include page="elements/common-head.jsp"/>
    <title>Spring MVC Web Gallery — Your Profile</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container">

    <div class="well">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
            <li><a href="#profile" data-toggle="tab">Password</a></li>
        </ul>
        <div id="myTabContent" class="tab-content validErrors">
            <div class="tab-pane active in" id="home">
                <form id="generalInfo" action="${pageContext.request.contextPath}/profile/edit" method="POST">
                    <label>Login</label><input id="inputLogin" name="inputLogin" type="text" value="${login}" class="input-xlarge">
                    <label>Email</label><input id="inputEmail" name="inputEmail" type="text" value="${email}" class="input-xlarge">
                    <label>Name</label><input id="inputName" name="inputName" type="text" value="${name}" class="input-xlarge">
                    <label>Surname</label><input  id="inputSurname" name="inputSurname" type="text" value="${surname}" class="input-xlarge">
                    <label>Second Name (Patronymic)</label><input id="inputSecondName" name="inputSecondName" type="text" value="${secondName}" class="input-xlarge">
                    <div>
                        <button class="btn btn-primary">Update</button>
                    </div>
                </form>
            </div>
            <div class="tab-pane fade validErrors" id="profile">
                <form id="newPassword" action="${pageContext.request.contextPath}/profile/edit/change-password" method="POST">
                    <label>New Password</label><input id="inputPassword" name="inputPassword" type="password" class="input-xlarge">
                    <label>New Password Again</label><input id="inputPasswordRepeat" name="inputPasswordRepeat" type="password" class="input-xlarge">
                    <div>
                        <button class="btn btn-primary">Update</button>
                    </div>
                </form>
            </div>
        </div>

</div>

<jsp:include page="elements/common-foot-javascript.jsp"/>

<script src="${pageContext.request.contextPath}/static/libs/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/profile-edit-validation-rules.js"></script>

</body>
</html>