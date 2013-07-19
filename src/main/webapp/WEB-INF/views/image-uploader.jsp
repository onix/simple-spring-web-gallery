<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../views/elements/common-head.jsp"/>

    <!-- Bootstrap Picture Gallery styles -->
    <link rel="stylesheet" href="static/libs/bootstrap-image-gallery/css/bootstrap-image-gallery.min.css">
    <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
    <link rel="stylesheet" href="static/libs/jquery-file-uploader/css/jquery.fileupload-ui.css">
    <!-- CSS adjustments for browsers with JavaScript disabled -->
    <noscript><link rel="stylesheet" href="static/libs/jquery-file-uploader/css/jquery.fileupload-ui-noscript.css"></noscript>

    <title>Spring MVC Web Gallery Photostream</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container">
    <h1>Upload photos to your photostream</h1>

    <!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload" action="${pageContext.request.contextPath}/photo/upload" method="POST" enctype="multipart/form-data">
        <!-- Redirect browsers with JavaScript disabled to the origin page -->
        <noscript><input type="hidden" name="redirect" value="http://blueimp.github.com/jQuery-File-Upload/"></noscript>
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="span7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="icon-plus icon-white"></i>
                    <span>Add files...</span>
                    <input type="file" name="files[]" multiple>
                </span>
                <button type="submit" class="btn btn-primary start">
                    <i class="icon-upload icon-white"></i>
                    <span>Start upload</span>
                </button>
                <button type="reset" class="btn btn-warning cancel">
                    <i class="icon-ban-circle icon-white"></i>
                    <span>Cancel upload</span>
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="icon-trash icon-white"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" class="toggle">
                <!-- The loading indicator is shown during file processing -->
                <span class="fileupload-loading"></span>
            </div>
            <!-- The global progress information -->
            <div class="span5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="bar" style="width:0%;"></div>
                </div>
                <!-- The extended global progress information -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <table role="presentation" class="table table-striped"><tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody></table>
    </form>

    <!-- modal-gallery is the modal dialog used for the image gallery -->
    <div id="modal-gallery" class="modal modal-gallery hide fade" data-filter=":odd" tabindex="-1">
        <div class="modal-header">
            <a class="close" data-dismiss="modal">&times;</a>
            <h3 class="modal-title"></h3>
        </div>
        <div class="modal-body"><div class="modal-image"></div></div>
        <div class="modal-footer">
            <a class="btn modal-download" target="_blank">
                <i class="icon-download"></i>
                <span>Download</span>
            </a>
            <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
                <i class="icon-play icon-white"></i>
                <span>Slideshow</span>
            </a>
            <a class="btn btn-info modal-prev">
                <i class="icon-arrow-left icon-white"></i>
                <span>Previous</span>
            </a>
            <a class="btn btn-primary modal-next">
                <span>Next</span>
                <i class="icon-arrow-right icon-white"></i>
            </a>
        </div>
    </div>
    <jsp:include page="../views/elements/image-uploader-templates.jsp"/>

    <div id="gallery" data-toggle="modal-gallery" data-target="#modal-gallery"></div>
</div>

<jsp:include page="../views/elements/common-foot-javascript.jsp"/>

<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="static/libs/jquery-file-uploader/js/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="static/libs/tmpl.min.js"></script>
<!-- The Load Picture plugin is included for the preview images and image resizing functionality -->
<script src="static/libs/load-image.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="static/libs/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS and Bootstrap Picture Gallery are not required, but included for the demo -->
<script src="static/libs/bootstrap-image-gallery/js/bootstrap-image-gallery.min.js"></script>
<!-- The basic File Upload plugin -->
<script src="static/libs/jquery-file-uploader/js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="static/libs/jquery-file-uploader/js/jquery.fileupload-process.js"></script>
<!-- The File Upload image resize plugin -->
<script src="static/libs/jquery-file-uploader/js/jquery.fileupload-resize.js"></script>
<!-- The File Upload validation plugin -->
<script src="static/libs/jquery-file-uploader/js/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="static/libs/jquery-file-uploader/js/jquery.fileupload-ui.js"></script>
<!-- The main application script -->
<script src="static/js/jquery-image-upload-implementation.js"></script>
</body>
</html>