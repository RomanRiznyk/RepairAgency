<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Login page</title>
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
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>



<body class="container">
<%-- ---------------HEADER---------------- --%>
<header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
    <%----------------------- LOCALIZATION BUTTONS ---------------------%>
    <div class="col-md-3 d-flex justify-content-start">
        <c:choose>
            <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                <form action="login" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="hidden" name="command" value="login">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                </form>
                <form action="login" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="hidden" name="command" value="login">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                </form>
            </c:when>

            <c:when test="${'ua'.equals(sessionScope.lang)}">
                <form action="login" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="hidden" name="command" value="login">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                </form>
                <form action="login" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="hidden" name="command" value="login">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                </form>
            </c:when>
        </c:choose>
    </div>

        <%----------------------- LOGIN and REGISTER BUTTONS ---------------------%>
        <div class="col-md-3 d-flex justify-content-end">
   <%--         <c:choose>
                <c:when test="${!sessionScope.role.equals('USER') && !sessionScope.role.equals('MANAGER') && !sessionScope.role.equals('MASTER')}">--%>



                    <form action="/repair" method="post" class="form">
                        <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2">
                    </form>
                    <form action="/repair/register" method="post" class="form">
                        <input type="hidden" name="command" value="register">
                        <input type="submit" value="<fmt:message key='Register'/>" class="btn btn-secondary me-2">
                    </form>


           <%--     </c:when>
            </c:choose>--%>
        </div>
</header>

<%-- ---------------LOGIN FORM---------------- --%>
<main class="form-signin"> <%--// todo path account????--%>
    <form allign="center" action="/repair/account" method="post">  <%--// todo path account????--%>
        <img class="mb-4"<%-- src="https://previews.123rf.com/images/defmorph/defmorph1805/defmorph180500037/101018773-microchip-line-icon-cpu-central-processing-unit-computer-processor-chip-symbol-in-circle-abstract-te.jpg"--%>
             alt="" width="100">
        <h1 class="h3 mb-3 fw-normal"><fmt:message key='PleaseLogIn'/></h1>

        <c:choose>
            <c:when test="${'LoggedOnOtherDevice'.equals(errorMessage)}">
                <p style="color:#ff0000"><fmt:message key='LoggedOnOtherDevice'/></p><br/>
            </c:when>
            <c:when test="${'NotLoggedIn'.equals(access)}">
                <p style="color:#ff0000"><fmt:message key='HaveToLogin'/></p><br/>
            </c:when>

            <c:otherwise>
                <p style="color:#ff0000">${errorMessage}</p><br/>
            </c:otherwise>
        </c:choose>
        <%--<div class="form-floating">
            <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" name="email" value="admin@gmail.com">
            <label for="floatingInput"><fmt:message key='Email'/></label>
        </div>--%>
        <div class="form-floating">
            <input type="text" class="form-control" id="login" placeholder="login" name="login" value="Manager">
            <label for="login"><fmt:message key='Login'/></label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password" value="123">
            <label for="floatingPassword"><fmt:message key='Password'/></label>
        </div>

        <input class="btn btn-secondary" type="hidden" name="command" value="account">
        <input class="w-100 btn btn-lg btn-secondary" type="submit" value="<fmt:message key='LogIn'/>">
        <p class="mt-5 mb-3 text-muted"><%--&copy; 2021--%></p>
    </form>
</main>

</body>
</html>
