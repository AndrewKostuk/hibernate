<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" type="text/css" >
</head>
<body>

<div class="form-style-2">
    <form method="post" action="home">
    <div class="form-style-2">
        <div class="form-style-2-heading">
            Hello! Welcome to my own web app. Now you can login or sing up if you didn't login before
        </div>

        <input type="submit" name="action" value="Sign Up">
        <input type="submit" name="action" value="Login">

    </div>
</form>
</div>

</body>
</html>
