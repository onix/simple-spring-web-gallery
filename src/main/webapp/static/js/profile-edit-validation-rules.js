$(document).ready(function(){
    $("#generalInfo").validate({
        rules: {
            inputLogin: {
                required: true,
                minlength: 2,
                maxlength: 50,
                remote: "/profile/is-login-available"
            },

            inputEmail: {
                required: true,
                minlength: 5,
                maxlength: 50,
                email: true
            },

            inputName: {
                maxlength: 50
            },

            inputSurname: {
                maxlength: 50
            },

            inputSecondName: {
                maxlength: 50
            }
        },
        messages: {
            inputLogin: {
                required: "Please enter a login",
                minlength: "Your login must consist of at least 2 characters",
                maxlength: "Your login must consist of less than 50 characters",
                remote: jQuery.format("{0} is already in use")
            },

            inputEmail: {
                required: "Please enter an email",
                email: "Please enter a valid email address",
                minlength: "Your email must consist of at least 5 characters",
                maxlength: "Your email must consist of less than 50 characters"
            },

            inputName: "Your name must consist of less than 50 characters",
            inputSurname: "Your surname must consist of less than 50 characters",
            inputSecondName: "Your second name must consist of less than 50 characters"
        }
    });

    $("#newPassword").validate({
        debug: false,
        rules: {
            inputPassword: {
                required: true,
                minlength: 6,
                maxlength: 100
            },

            inputPasswordRepeat: {
                required: true,
                minlength: 6,
                maxlength: 100,
                equalTo: "#inputPassword"
            }
        },
        messages: {
            inputPassword: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                maxlength: "Your password must be less than 100 characters long"
            },

            inputPasswordRepeat: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                maxlength: "Your password must be less than 100 characters long",
                equalTo: "Please enter the same password as above"
            }
        }
    });
});