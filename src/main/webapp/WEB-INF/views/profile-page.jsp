<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="elements/common-head.jsp"/>
    <title>Spring MVC Web Gallery — Your Profile</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container">

    <div class="span7 well">
        <div class="row">
            <div class="span3">
                <a href="http://gravatar.com/emails/" class="thumbnail" title="Change your Gravatar image">
                    <img height="420" width="420" src="${gravatarImageURL}" alt="Gravatar image">
                </a>
            </div>

            <div class="span4">
                <p>User #${id}</p>
                <p>User login: ${login}</p>
                <p><h2><strong>${name} ${surname} ${secondName}</strong></h2></p>
                <p>email: <strong>${email}</strong></p>
                <p>role: <strong>${role}</strong></p>
                <p>
                    <span class="badge badge-warning">${amountOfImages} images</span>
                    in
                    <span class="badge badge-info">${amountOfPhotosets} photosets</span>
                </p>
                <br />

                <p><a href="${pageContext.request.contextPath}/profile/edit"><button class="btn btn-large btn-primary">Edit Profile</button></a></p>
            </div>
        </div>
    </div>

</div>

<jsp:include page="elements/common-foot-javascript.jsp"/>

</body>
</html>