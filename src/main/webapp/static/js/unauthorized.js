$().ready(
    $("#signinButton").click(function() {
        var user_name = $("#j_username").val();
        var user_pass = $("#j_password").val();
        var alertBootstrapWrongAuth = "" +
            "<div class=\"alert\">" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" +
            "Wrong user or password." +
            "</div>"

        $.ajax({
            type: "POST",
            url: "j_spring_security_check",
            data: { j_username: user_name, j_password: user_pass },
            cache: false,
            timeout: 20000,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-Ajax-call", "true");
            },
            success: function(result) {
                if (result == "authenticated") {
                    window.location.href = "/";
                    return true;
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                $('#signin').append(alertBootstrapWrongAuth);
                return false;
            }
        });
    })
);