$().ready(function () {
    $("#signinForm").validate({ //TODO validate or not vaildate? That is the question
        rules: {
            j_username: {
                required: true,
                minlength: 2,
                maxlength: 50
            },

            j_password: {
                required: true,
                minlength: 6,
                maxlength: 100
            }
        },
        messages: {
            j_username: {
                required: "Please enter login",
                minlength: "Your login must consist of at least 2 characters",
                maxlength: "Your login must consist of less than 50 characters"
            },

            j_password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                maxlength: "Your password must be less than 100 characters long"
            }
        }
    });

    $("#registrationForm").validate({
        rules: {
            loginRegistrationField: {
                required: true,
                minlength: 2,
                maxlength: 50,
                remote: "/profile/is-login-available"
            },

            emailRegistrationField: {
                required: true,
                minlength: 5,
                maxlength: 50,
                email: true
            },

            passwordRegistrationField: {
                required: true,
                minlength: 6,
                maxlength: 100
            },

            passwordConfirmRegistrationField: {
                required: true,
                minlength: 6,
                maxlength: 100,
                equalTo: "#passwordRegistrationField"
            },

            hiddenRegistrationField: {
                required: {
                    depends: function () {
                        return $("#hiddenRegistrationField").is(":empty");
                    }
                }
            }
        },
        messages: {
            loginRegistrationField: {
                required: "Please enter a login",
                minlength: "Your login must consist of at least 2 characters",
                maxlength: "Your login must consist of less than 50 characters",
                remote: jQuery.format("{0} is already in use")
            },

            emailRegistrationField: {
                required: "Please enter an email",
                email: "Please enter a valid email address",
                minlength: "Your email must consist of at least 5 characters",
                maxlength: "Your email must consist of less than 50 characters"
            },

            passwordRegistrationField: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                maxlength: "Your password must be less than 100 characters long"
            },

            passwordConfirmRegistrationField: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                maxlength: "Your password must be less than 100 characters long",
                equalTo: "Please enter the same password as above"
            }
        }
    });
});