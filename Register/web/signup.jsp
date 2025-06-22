<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Signup Page</title>

        <!-- Materialize CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    </head>

    <body style="background: url('<%= request.getContextPath()%>/img/pixelunsplash.jpg'); background-size: cover; background-position: center center; background-repeat: no-repeat; background-attachment: fixed;">

        <div class="container">
            <div class="row">
                <div class="col m6 offset-m3">
                    <div class="card">
                        <div class="card-content">
                            <h4 class="center-align">Register here!</h4>

                            <!-- ✅ FORM -->
                            <form id="myform">
                                <div class="input-field">
                                    <input type="text" id="username" name="username" required>
                                    <label for="username">Username</label>
                                </div>

                                <div class="input-field">
                                    <input type="email" id="email" name="email" required>
                                    <label for="email">Email</label>
                                </div>

                                <div class="input-field">
                                    <input type="password" id="password" name="password" required>
                                    <label for="password">Password</label>
                                </div>

                                <div class="file-field input-field">
                                    <div class="btn">
                                        <span>File</span>
                                        <input name ="image" type="file">
                                    </div>
                                    <div class="file-path-wrapper">
                                        <input class="file-path validate" type="text">
                                    </div>
                                </div>

                                <div class="center-align">
                                    <input type="submit" class="btn light-blue lighten-1" value="Register">
                                </div>

                                <!-- ✅ Loader (Initially Hidden) -->
                                <div class="progress" id="loader" style="display: none;">
                                    <div class="indeterminate"></div>
                                </div>
                                <h6 id="msg" class="center-align"></h6>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ✅ jQuery first -->
        <script
            src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>
        <!-- ✅ Then Materialize JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

        <!-- ✅ AJAX Handler -->
        <script>
            $(document).ready(function () {
                console.log("page is ready...");

                $(document).on('submit', '#myform', function (event) {
                    event.preventDefault();
                   // var f = $(this).serialize();
                   let f = new FormData(this);
                    console.log(f);
                    $("#loader").show(); // show loader
                    $("#msg").text("");  // clear message

                    $.ajax({
                        url: "Register", // Servlet or Controller
                        type: 'POST',
                        data: f,
                        success: function (data) {
                            console.log("Success:", data);
                            $("#msg").text("Registration successful!").css("color", "green");
                        },
                        error: function (xhr, status, error) {
                            console.log("Error:", status, error);
                            $("#msg").text("Registration failed!").css("color", "red");
                        },
                        complete: function () {
                            $("#loader").hide(); // hide loader regardless
                        }, 
                         processData : false,
                         contentType : false
                    });
                });
            });
        </script>

    </body>
</html>
