<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sorttable.js"
            language="JavaScript"></script>
</head>
<body>
<style>
    * {
        margin: 0;
        padding: 0;
    }

    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin: 100px;
        max-width: 700px;
        height: 650px;
        border-radius: 45px;
        background-color: #a0a0a0;
    }

    .btn {
        margin-top: 10px;
        padding: 10px;
        width: 300px;
        font-size: 22px;
    }

    .text-center {
        margin-bottom: 20px;
        color: #ffffff;
    }

    .background {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .oops {
        margin-bottom: 25px;
        height: 250px;
    }
</style>

<%--<div class="background">
    <div class="container">
        <h1 class="text-center"><fmt:message key="Oops"/></h1><br>
        <img class="oops" src="https://www.pngkit.com/png/full/125-1258059_sorry-cat-cartoon.png">
        <h4 class="text-center"><fmt:message key="SomethingBadHappened"/></h4>
        <h4 class="text-center"><fmt:message key="PleaseGoToMainPage"/></h4>
        <a href="/repair/" class="btn btn-primary"><fmt:message key="GoBackToMain"/></a>
    </div>
</div>--%>

<header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
    <%----------------------- LOCALIZATION BUTTONS ---------------------%>
    <div class="col-md-3 d-flex justify-content-start">
        <c:choose>
            <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                <form action="error" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                </form>
                <form action="error" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                </form>
            </c:when>

            <c:when test="${'ua'.equals(sessionScope.lang)}">
                <form action="error" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                </form>
                <form action="error" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                </form>
            </c:when>
        </c:choose>
    </div>

        <div class="col-md-3 d-flex justify-content-end">
            <form action="/repair" method="post">
                <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2">
            </form>
                <c:choose>
                    <c:when test="${sessionScope.role.equals('USER') || sessionScope.role.equals('MANAGER') || sessionScope.role.equals('MASTER')}">
                        <form action="/repair/logout" method="post">
                            <input type="submit" value="<fmt:message key='logout'/>" class="btn btn-secondary me-2">  <%--todo if loggged in - logout , of  isnt logged in - log in!--%>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="login" method="post">
                            <input type="submit" value="<fmt:message key='LogIn'/>" class="btn btn-secondary me-2">  <%--todo if loggged in - logout , of  isnt logged in - log in!--%>
                        </form
                    </c:otherwise>
                </c:choose>

        </div>
</header>

<h2 align="center"><fmt:message key='SomethingWentWrong'/></h2> <br>
<%----------------------- VALIDATION ERROR MESSAGES ---------------------%>
<c:choose>
    <c:when test="${'HasNoRights'.equals(errorMessage)}">
        <p class="h3 mb-3 fw-normal" style="color:#ff0000"><fmt:message key='HasNoRights'/></p>
    </c:when>
    <c:when test="${'AlreadyLoggedIN'.equals(errorMessage)}">
        <p class="h3 mb-3 fw-normal" style="color:#ff0000"><fmt:message key='AlreadyLoggedIn'/></p>
    </c:when>
    <c:otherwise>
        <h6 align="center" style="color: red">${errorMessage}</h6> <br>
    </c:otherwise>
</c:choose>
<h2 align="center"><fmt:message key='PleaseTryAgain'/></h2> <br>

<div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
    <img align="center" height="540" width="960" src="https://i.gifer.com/8AIa.gif">
</div>
<br/>

</body>
</html>