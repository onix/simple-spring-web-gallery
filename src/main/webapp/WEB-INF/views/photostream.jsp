<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../views/elements/common-head.jsp"/>

    <link rel="stylesheet" href="static/libs/bootstrap-image-gallery/css/bootstrap-image-gallery.min.css">
    <title>Spring MVC Web Gallery Photostream</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <h1>Hello, <%= request.getUserPrincipal().getName() %>, here are your last photos.</h1>

    <p>
        <button id="start-slideshow" class="btn btn-large btn-success" data-slideshow="5000" data-target="#modal-gallery" data-selector="#gallery a[rel=gallery]">Start Slideshow</button>
        <button id="toggle-fullscreen" class="btn btn-large btn-primary" data-toggle="button">Toggle Fullscreen</button>
    </p>

    <div id="LoadingImage" style="display: none">
        <img src="static/libs/bootstrap-image-gallery/img/loading.gif"/>
    </div>
    <div id="gallery" data-toggle="modal-gallery" data-target="#modal-gallery"></div>
</div>

<!-- modal-gallery is the modal dialog used for the image gallery -->
<div id="modal-gallery" class="modal modal-gallery hide fade" tabindex="-1">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
        <h3 class="modal-title"></h3>
    </div>
    <div class="modal-body"><div class="modal-image"></div></div>
    <div class="modal-footer">
        <a class="btn btn-primary modal-next">Next <i class="icon-arrow-right icon-white"></i></a>
        <a class="btn btn-info modal-prev"><i class="icon-arrow-left icon-white"></i> Previous</a>
        <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000"><i class="icon-play icon-white"></i> Slideshow</a>
        <a class="btn modal-download" target="_blank"><i class="icon-download"></i> Download</a>
    </div>
</div>

<jsp:include page="../views/elements/common-foot-javascript.jsp"/>
<script src="static/libs/load-image.min.js"></script>
<script src="static/libs/bootstrap-image-gallery/js/bootstrap-image-gallery.min.js"></script>

<script src="static/js/photostream-processor.js"></script>
<script src="static/js/bootstrap-image-gallery-implementation.js"></script>
</body>
</html>