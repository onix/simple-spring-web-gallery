<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="signin" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>Login</h3>
    </div>
    <div class="modal-body">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" id="j_username" class="input-block-level" placeholder="User login" id="loginSigninField">
        <input type="password" id="j_password" class="input-block-level" placeholder="Password" id="passwordSigninField">
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
        <button name="signinButton" id="signinButton" class="btn btn-primary">Sign in</button>
    </div>
</div>