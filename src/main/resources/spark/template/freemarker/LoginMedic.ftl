<!Doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <!-- Title Page -->
    <title>Login</title>

    <!-- CSS -->
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/animate.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div id="container">
    <form action="http://localhost:4321/login_medic" method="post">
        <!-- Username -->
        <label for="matricula">Matricula:</label>
        <input type="number" name="matricula">
        <!-- Password -->
        <p><label for="Password">Password:</label>
            <input type="password" name="password"></p>
        <p><a href="#">Forgot your password?</a>
            <div id="lower">
                <input type="checkbox"><label class="check" for="checkbox">Keep me logged in</label>
                <!-- Submit Button -->
        <p><input type="submit" value="Login"></p>
    </form>
</div>
</body>
</html>