<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        footer {
            margin-top: auto;
        }

        .title {
            max-width: 50px;
            word-wrap: break-word;
            /*overflow: hidden;*/
            /*text-overflow: ellipsis;*/
        }
    </style>
</head>
<body class="container">
<c:choose>
    <c:when test="${sessionScope.role.equals('USER')}">
    <%-- ----------------------HEADER---------------- --%>
        <header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
                <%----------------------- LOCALIZATION BUTTONS ---------------------%>
            <div class="col-md-3 d-flex justify-content-start">
                <c:choose>
                    <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                        <form action="user" method="post">
                            <input type="hidden" name="lang" value="ua">
                            <input type="hidden" name="command" value="user">
                            <input type="hidden" name="login" value=${sessionScope.login}>
                            <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                        </form>
                        <form action="user" method="post" class="mx-2">
                            <input type="hidden" name="lang" value="en">
                            <input type="hidden" name="command" value="user">
                            <input type="hidden" name="login" value=${login}>
                            <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                        </form>
                    </c:when>
                    <c:when test="${'ua'.equals(sessionScope.lang)}">
                        <form action="user" method="post">
                            <input type="hidden" name="lang" value="ua">
                            <input type="hidden" name="command" value="user">
                            <input type="hidden" name="login" value=${login}>
                            <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                        </form>
                        <form action="user" method="post" class="mx-2">
                            <input type="hidden" name="lang" value="en">
                            <input type="hidden" name="command" value="user">
                            <input type="hidden" name="login" value=${login}>
                            <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                        </form>
                    </c:when>
                </c:choose>
            </div>

            <div class="d-flex justify-content-between">
                <%--some text in the center of header--%>
            </div>
             <%----------------------- NEW ORDER, MAIN and LOGOUTBUTTONS ---------------------%>
            <div class="col-md-3 d-flex justify-content-end">
                <form action="user/new_order" method="post">
                    <input type="hidden" name="command" value="newReceipt">
                    <input type="submit" value="<fmt:message key='NewOrder'/>" class="btn btn-outline-secondary me-2">
                </form>
                <form action="/repair" method="post">
                    <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2">
                </form>
                <form action="/repair/logout" method="post">
                    <input type="hidden" name="command" value="logOut" class="m-r-2">
                    <input type="submit" value="<fmt:message key='logout'/>" class="btn btn-secondary me-2">
                </form>
            </div>
        </header>


<%--------------- MAIN BODY -------------------%>
        <h3 class="fw-normal"><fmt:message key='Hello'/> <span style='color: blue;'>${sessionScope.login}</span>! </h3>
        <h3 class="fw-normal"><fmt:message key='YourBalance'/> <span style='color: green;'>${sessionScope.user.balance}</span> </h3>
<hr>
        Error = ${errorMessage}<br>
        Success = ${successMessage}<br>
        Registersuccess = ${registerSuccess}<br>
<hr>
        <br>
            <c:choose>
                <c:when test="${errorMessage == 'AlreadyLoggedIN'}">
                    <p style="color:#ff0000"><fmt:message key='AlreadyLoggedIN'/></p><br/>
                </c:when>
                <c:when test="${errorMessage == 'HasNoRights'}">
                    <p style="color:#ff0000"><fmt:message key='HasNoRights'/></p><br/>
                </c:when>
                <c:when test="${successMessage == 'true'}">
                    <h6 style='color: green' class="fw-normal"><fmt:message key='SuccessfullyCreated'/>
                </c:when>
                <c:when test="${registerSuccess == 'true'}"> <h6 style='color: green' ><fmt:message key='RegisterSuccess'/></h6> </c:when>
            </c:choose>

            <c:choose>
            <c:when test="${requestScope.receiptList.size() == 0}">
                        <c:choose>
                <c:when test="${registerSuccess != 'true'}">
                    <h5><fmt:message key='YouHaveNoReceipts'/></h5>
                </c:when>
                    </c:choose>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key='TableOfReceipts'/></h3>

                <table class="table table-bordered sortable">
                    <thead>
                    <tr>
                        <th class="title"><fmt:message key='Item'/></th>
                        <th class="title"><fmt:message key='ProblemDescription'/></th>
                        <th class="title"><fmt:message key='Price'/></th>
                        <th class="title"><fmt:message key='MasterId'/></th>
                        <th class="title"><fmt:message key='MasterLogin'/></th>
                        <th class="title"><fmt:message key='Status'/></th>
                        <th class="title"><fmt:message key='CreateDate'/></th>
                        <th class="title"><fmt:message key='LastUpdate'/></th>
                        <th class="title"><fmt:message key='Feedback'/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <c:forEach var="receipt" items="${receiptList}">
                    <tr>
                        <td class="title"> ${receipt.item} </td>
                        <td class="title">${receipt.description}</td>
                        <th class="title">${receipt.price}</th>
                        <td class="title">${receipt.master_id}</td>
                        <td class="title">${receipt.masterLogin}</td>
                        <td class="title">${receipt.status}</td>
                        <td class="title">${receipt.createDate}</td>
                        <td class="title"> todo... </td>    <%--${receipt.lastUpdate}--%>
                        <td class="title">${receipt.feedback}
                            <c:choose>
                                <c:when test="${receipt.status.equals('Done') && (receipt.feedback == null || ''.equals(receipt.feedback)) }">
                                    <form action="user/feedback" method="post">
                                        <input type="hidden" name="item" value="${receipt.item}">
                                        <%--<input type="hidden" name="description" value="${receipt.description}"> --%><%--// todo remove it after testing--%>
                                        <input type="hidden" name="id" value="${receipt.id}">
                                        <input type="hidden" name="command" value="feedback">     <%--todo hidden parameters change in command code--%>
                                        <input type="submit" value="<fmt:message key='PostFeedback'/>" class="btn btn-outline-secondary me-2">
                                    </form>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
        <footer>
            <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
                © 2021 Copyright:
                <a class="text-reset fw-bold">repair-agency.com</a>
            </div>
        </footer>
    </c:when>
    <c:when test="${!sessionScope.role.equals('USER')}">
                    <h2><fmt:message key='PleaseLoginAsUser'/></h2>  <%--// TODO BUTTON LOGIN--%>
    </c:when>
</c:choose>
</body>
</html>
