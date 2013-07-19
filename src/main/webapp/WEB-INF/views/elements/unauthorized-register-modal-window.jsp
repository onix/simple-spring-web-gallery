<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="register" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>Register</h3>
    </div>
    <form name="registrationForm" id="registrationForm" class="form-register validErrors" action="register" method="post">
        <div class="modal-body">
            <h2 class="form-register-heading">Register please</h2>
            <input type="text" class="input-block-level" placeholder="Login" id="loginRegistrationField" name="loginRegistrationField">
            <input type="text" class="input-block-level" placeholder="Email address" id="emailRegistrationField" name="emailRegistrationField">
            <input type="password" class="input-block-level" placeholder="Password" id="passwordRegistrationField" name="passwordRegistrationField">
            <input type="password" class="input-block-level" placeholder="Confirm password" id="passwordConfirmRegistrationField" name="passwordConfirmRegistrationField">
            <input type="hidden" class="input-block-level" placeholder="Hillde, don't fill" id="hiddenRegistrationField" name="hiddenRegistrationField">
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
            <button class="btn btn-primary" type="submit">Register</button>
        </div>
    </form>
</div>