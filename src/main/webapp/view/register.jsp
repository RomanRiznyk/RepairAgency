<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Register page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
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
           /* border-top-left-radius: 0;
            border-top-right-radius: 0;*/
        }
    </style>
</head>
<body class="text-center">
<%----------------------- HEADER ---------------------%>

<header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
    <%----------------------- LOCALIZATION BUTTONS ---------------------%>
    <div class="col-md-3 d-flex justify-content-start">
        <c:choose>
            <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                <form action="register" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                </form>
                <form action="register" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                </form>
            </c:when>

            <c:when test="${'ua'.equals(sessionScope.lang)}">
                <form action="register" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                </form>
                <form action="register" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                </form>
            </c:when>
        </c:choose>
    </div>
        <%----------------------- LOGIN and REGISTER BUTTONS ---------------------%>
        <div class="col-md-3 d-flex justify-content-end">
            <c:choose>
                <c:when test="${!sessionScope.role.equals('USER') && !sessionScope.role.equals('MANAGER') && !sessionScope.role.equals('MASTER')}">
                    <form action="login" method="post">
                        <input type="submit" value="<fmt:message key='Login'/>" class="btn btn-outline-secondary me-2">
                    </form>
                    <form action="/repair" method="post" class="form">
                        <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-secondary me-2">
                    </form>
                </c:when>
            </c:choose>
        </div>
</header>

<main class="form-signin">
    <form allign="center" action="processRegister" method="post"> <%--// todo path account????--%>
        <img class="mb-4" <%--src="https://w7.pngwing.com/pngs/118/466/png-transparent-maintenance-computer-icons-innovation-repair-angle-service-logo.png"--%>
             alt="" width="100">
        <h1 class="h3 mb-3 fw-normal"><fmt:message key='RegisterMessage'/></h1>

        <%----------------------- VALIDATION ERROR MESSAGES ---------------------%>

        ${errorMessage}
        <c:choose>
            <c:when test="${'ShouldBeNotNull'.equals(errorMessage)}">
                <p class="h5 mb-3 fw-normal" style="color:#ff0000"><fmt:message key='ShouldBeNotNull'/></p>
            </c:when>
            <c:when test="${'ShouldBeValidLogin'.equals(errorMessage)}">
                <p class="h5 mb-3 fw-normal" style="color:#ff0000"><fmt:message key='ShouldBeValidLogin'/></p>
            </c:when>
            <c:when test="${'ShouldBeValidPassword'.equals(errorMessage)}">
                <p class="h5 mb-3 fw-normal" style="color:#ff0000"><fmt:message key='ShouldBeValidPassword'/></p>
            </c:when>
            <c:when test="${'ShouldBeValidEmail'.equals(errorMessage)}">
                <p class="h5 mb-3 fw-normal" style="color:#ff0000"><fmt:message key='ShouldBeValidEmail'/></p>
            </c:when>
            <c:when test="${errorMessage.contains('AlreadyExist')}">
                <p class="h5 mb-3 fw-normal" style="color:#ff0000"> ${errorMessage.replace('AlreadyExist', '')} <fmt:message key='AlreadyExist'/></p>
            </c:when>
            <c:when test="${'ShouldBeEquals'.equals(errorMessage)}">
                <p class="h5 mb-3 fw-normal" style="color:#ff0000"><fmt:message key='ShouldBeEquals'/></p>
            </c:when>
        </c:choose>

        <div class="form-floating">
            <input type="text" class="form-control" id="login" placeholder="login" name="login">
            <label for="login"><fmt:message key='Login'/></label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
            <label for="floatingPassword"><fmt:message key='Password'/></label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="confirmPassword" placeholder="Password" name="confirmPassword">
            <label for="confirmPassword"><fmt:message key='ConfirmPassword'/></label>
        </div>
        <div class="form-floating">
            <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" name="email">
            <label for="floatingInput"><fmt:message key='Email'/></label>
        </div>
        <input class="w-100 btn btn-lg btn-secondary" type="submit" value="<fmt:message key='Register'/>">
        <p class="mt-5 mb-3 text-muted">&copy; 2021</p>
    </form>
</main>
</body>
</html>