<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Main page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        html,
        body {
            height: 100%;
        }

        /*body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background-color: #f5f5f5;
        }*/

        footer {
            margin-top: auto;
        }

        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }

        .form-signin .checkbox {
            font-weight: 400;
        }

        .form-signin .form-floating:focus-within {
            z-index: 2;
        }

        /* .form-signin input[type="email"] {
             margin-bottom: -1px;
             border-bottom-right-radius: 0;
             border-bottom-left-radius: 0;
         }*/

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>
<body class="container">

<header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
    <%----------------------- LOCALIZATION BUTTONS ---------------------%>
    <div class="col-md-3 d-flex justify-content-start">
        <c:choose>
            <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                <form action="${pageContext.request.requestURI}" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                </form>
                <form action="${pageContext.request.requestURI}" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                </form>
            </c:when>

            <c:when test="${'ua'.equals(sessionScope.lang)}">
                <form action="${pageContext.request.requestURI}" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                </form>
                <form action="${pageContext.request.requestURI}" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                </form>
            </c:when>
        </c:choose>
    </div>

    <%----------------------- ACCOUNT BUTTON ---------------------%>
    <div class="col-md-3 d-flex justify-content-end">
        <c:choose>
            <c:when test="${sessionScope.role.equals('MANAGER')}">
                <form action="manager" method="post">
                    <input type="hidden" name="command" value="managerReceiptList" class="m-r-2">
                    <input type="submit" value="<fmt:message key='Receipts'/>" class="btn btn-outline-secondary me-2"> <%--//todo button USERS--%>
                </form>
            </c:when>
            <c:when test="${sessionScope.role.equals('MASTER')}">
                <form action="master" method="post">
                    <input type="hidden" name="command" value="master" class="m-r-2">
                    <input type="hidden" name="login" value=${login} class="m-r-2"> <%--todo redundant - can do via sessionContext.user.login--%>
                    <input type="submit" value="<fmt:message key='Receipts'/>" class="btn btn-outline-secondary me-2">
                </form>
            </c:when>
            <c:when test="${sessionScope.role.equals('USER')}">
                <form action="user" method="post">
                    <input type="hidden" name="command" value="user" class="m-r-2">
                    <input type="hidden" name="login" value=${login} class="m-r-2"> <%--todo redundant - can do via sessionContext.user.login--%>
                    <input type="submit" value="<fmt:message key='Receipts'/>" class="btn btn-outline-secondary me-2">
                </form>
            </c:when>
        </c:choose>

        <%----------------------- LOGIN and REGISTER BUTTONS ---------------------%>
        <c:choose>
            <c:when test="${!sessionScope.role.equals('USER') && !sessionScope.role.equals('MANAGER') && !sessionScope.role.equals('MASTER')}">
            <form action="login" method="post">
                <input type="hidden" name="command" value="login" class="m-r-2">
                <input type="submit" value="<fmt:message key='Login'/>" class="btn btn-outline-secondary me-2">
            </form>
            <form action="register" method="post" class="form">
                <input type="hidden" name="command" value="register">
                <input type="submit" value="<fmt:message key='Register'/>" class="btn btn-secondary">
            </form>
            </c:when>
            <c:when test="${sessionScope.role.equals('USER') || sessionScope.role.equals('MANAGER') || sessionScope.role.equals('MASTER')}">
            <form action="logout" method="post" class="m-r-2">
                    <input type="hidden" name="command" value="logOut">
                    <input type="submit" value="<fmt:message key='LogOut'/>" class="btn btn-secondary me-2">
                </form>
            </c:when>
        </c:choose>
    </div>
</header>

<h2 align="center"><fmt:message key='Welcome'/></h2> <br>
<%--Lang = ${sessionScope.lang}--%>
<%--Role: "${sessionScope.role}"--%>
<br/>

<div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
    <img align="center" height="540" width="960" src="https://i.gifer.com/8AIa.gif">
</div>
</body>
</html>