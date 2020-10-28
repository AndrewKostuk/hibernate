<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<html>
<head>
    <title>Order</title>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <style>
        .hidden {
            display: none;
        }
    </style>
</head>

<script src="${pageContext.request.contextPath}/js/onClick.js"></script>

<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        <span>Make your new order! ${user} </span>
    </div>
    <form method="post" action="order">
        <label for="name">Order name
            <input class="input-field" type="text" name="name" id="name">
        </label>

        <label for="color">Order color
            <select class="select-field" name="color" id="color">
                <option hidden selected value></option>
                <option value="red">red</option>
                <option value="green">green</option>
                <option value="blue">blue</option>
            </select>
        </label>

        <label for="cost">Order cost
            <input class="input-field" type="text" name="cost" id="cost">
        </label>

        <label for="color_text">Text color
            <select class="select-field" name="color_text" id="color_text">
                <option hidden selected value></option>
                <option value="red">Красный</option>
                <option value="green">Зеленый</option>
                <option value="blue">Голубой</option>
            </select>

        </label>
        <input type="submit" value="order">
    </form>
</div>


<sql:setDataSource var="snapshot" driver="org.postgresql.Driver"
                   url="jdbc:postgresql://localhost:5432/db_example"
                   user="postgres" password="admin"/>

<sql:query dataSource="${snapshot}" var="result">
    select purchase.* from purchase left join web_user on purchase.owner_id = web_user.id where web_user.name='${sessionScope.user}'
</sql:query>


<table style="color: ${cookie.color.value}">
    <tr>
        <th>name</th>
        <th>color</th>
        <th>cost</th>
    </tr>

    <c:forEach var="row" items="${result.rows}">
        <tr>
            <td><c:out value="${row.name}"/></td>
            <td><c:out value="${row.color}"/></td>
            <td><c:out value="${row.cost}"/></td>
            <td>
                <form class="form-style-2" method="post" action="change">
                    <input type="hidden" name="id" value="${row.id}">
                    <input type="submit" name="action" value="delete">
                </form>
            </td>
            <td>
                <button class="js-show-form" onclick="main(${row.id})">update</button>
            </td>
        </tr>


        <tr>
            <td>
                <form class="js-form hidden" method="post" action="change" id="change"></form>
                <label>
                    <input class="js-form ${row.id} hidden" type="text" name="new_name" value="${row.name}" form="change">
                </label>
            </td>

            <td>
                <label>
                    <select class="js-form ${row.id} hidden" name="new_color" form="change">
                        <option selected>${row.color}</option>
                        <option value="red">red</option>
                        <option value="green">green</option>
                        <option value="blue">blue</option>
                    </select>
                </label>
            </td>

            <td>
                <label>
                    <input class="js-form ${row.id} hidden" type="text" name="new_cost" value="${row.cost}" form="change">
                </label>
            </td>

            <td>
                <input class="js-form hidden" type="hidden" name="id" value="${row.id}" form="change">
                <input class="js-form ${row.id} hidden" type="submit" name="action" value="update" form="change">
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
