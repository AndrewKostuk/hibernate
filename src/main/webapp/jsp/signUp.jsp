<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        Please sign up!
    </div>

    <form method="post" action="signUp">
        <label for="name">User name
            <input class="input-field" type="text" id="name" name="name">
        </label>

        <label for="password">Password
            <input class="input-field" type="password" id="password" name="password">
        </label>

        <label for="birthDate">Birth date
            <input class="input-field" type="text" id="birthDate" name="birthDate">
        </label>
        <input type="submit" value="Sign Up">
    </form>
</div>
</body>
</html>
