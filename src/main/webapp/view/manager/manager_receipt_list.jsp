<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Manager</title>
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
<c:when test="${sessionScope.role.equals('MANAGER')}">
    <%-- ----------------------HEADER---------------- --%>
<header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
    <%----------------------- LOCALIZATION BUTTONS ---------------------%>
    <div class="col-md-3 d-flex justify-content-start">
        <c:choose>
            <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                <form action="manager" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="hidden" name="receiptListForLang" value="${requestScope.receiptList}">
                    <input type="hidden" name="command" value="managerReceiptList"> <%--TODO hide .jsp here--%>
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                </form>
                <form action="manager" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="hidden" name="receiptListForLang" value="${requestScope.receiptList}">
                    <input type="hidden" name="command" value="managerReceiptList"> <%--TODO hide .jsp here--%>
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                </form>
            </c:when>

            <c:when test="${'ua'.equals(sessionScope.lang)}">
                <form action="manager" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="hidden" name="receiptListForLang" value="${requestScope.receiptList}">
                    <input type="hidden" name="command" value="managerReceiptList"> <%--TODO hide .jsp here--%>
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                </form>
                <form action="manager" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="hidden" name="receiptListForLang" value="${requestScope.receiptList}">
                    <input type="hidden" name="command" value="managerReceiptList"> <%--TODO hide .jsp here--%>
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                </form>
            </c:when>
        </c:choose>
    </div>
    <div class="d-flex justify-content-between">
            <%--some text in the center of header--%>
    </div>
    <%-- ---------------MAIN and LOGOUT BUTTONS --------------- --%>
    <div class="col-md-3 d-flex justify-content-end">
                    <%--<form action="top_up_account" method="post">
                        <input type="hidden" name="command" value="topUpAccount">
                        <input type="submit" value="<fmt:message key='TopUpAccount'/>" class="btn btn-outline-secondary me-2">
                    </form>--%>
                <form action="/repair" method="post"> <%--TODO strange /repair--%>
                    <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2">
                </form>
                <form action="/repair/logout" method="post" class="m-r-2">
                    <input type="hidden" name="command" value="logOut">
                    <input type="submit" value="<fmt:message key='logout'/>" class="btn btn-secondary me-2">
                </form>
            </div>
</header>
    <%--------------- MAIN BODY -------------------%>
    <h3 class="fw-normal"><fmt:message key='Hello'/> <span style='color: blue;'>${sessionScope.login}</span>! </h3>
        <%--------------- ERRORS MESSAGES -------------------%>
<%--<h3 class="fw-normal"><fmt:message key='Hello'/> <span style='color: blue;'>${sessionScope.login}</span>! </h3>--%>
    <%--<h3 class="fw-normal"><fmt:message key='YourBalance'/> <span style='color: green;'>${sessionScope.user.balance}</span>$--%>
        <br>

    <c:choose>
        <c:when test="${errorMessage == 'AlreadyLoggedIN'}">
            <p style="color:#ff0000"><fmt:message key='AlreadyLoggedIN'/></p><br/>
        </c:when>
        <c:when test="${errorMessage == 'HasNoRights'}">
            <p style="color:#ff0000"><fmt:message key='HasNoRights'/></p><br/>
        </c:when>
    </c:choose>


    <%----------------------- TABLE HEADER and SORTING BUTTONS ---------------------%>
    <div class="d-flex flex-wrap justify-content-between py-1 mb-3">

            <div>
                <h3><fmt:message key='TableOfReceipts'/></h3>
            </div>
        <c:choose>
            <c:when test="${applicationScope.loggedUsers == null}">Logged users == null</c:when>
        </c:choose>

            <div class="d-flex justify-content-end">
                <%-------------- SORT BY ------------%>
                <div class="dropdown d-flex justify-content-end mx-1">  <%--d-flex justify-content-end--%>
                    <%--<div class="dropdown">--%>
                        <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="SortBy"/>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <c:forEach var="sort" items="${sortBy}">
                                <form action="manager" method="post">
                                    <input type="hidden" name="command" value="managerReceiptList">
                                    <input type="hidden" name="sortingType" value="${sort}">
                                    <input class="dropdown-item" type="submit" value="<fmt:message key="${sort}"/>">
                                </form>
                            </c:forEach>
                        </div>
                    <%--</div>--%>
                </div>
                <%-------------- MASTER FILTER ------------%>
                <div class="dropdown mx-1">
                    <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                            data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="MasterFilter"/>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <form action="manager" method="post">
                            <input type="hidden" name="command" value="managerReceiptList">
                            <input type="hidden" name="masterLogin" value="All">
                            <input class="dropdown-item" type="submit" value="All engineers">
                        </form>
                        <c:forEach var="master" items="${masterList}">
                            <form action="manager" method="post">
                                <input type="hidden" name="command" value="managerReceiptList">
                                <input type="hidden" name="masterLogin" value="${master.login}">
                                <input class="dropdown-item" type="submit" value="${master.login}">
                            </form>
                        </c:forEach>
                    </div>
                </div>
                <%-------------- STATUS FILTER ------------%>
                <div class="dropdown mx-1">
                    <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownStatusButton"
                            data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="StatusFilter"/>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownStatusButton">
                        <c:forEach var="status" items="${statusList}">
                            <form action="manager" method="post">
                                <input type="hidden" name="command" value="managerReceiptList">
                                <input class="dropdown-item" type="submit" name="status" value="${status}">
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
    </div>

    <c:choose>
        <c:when test="${requestScope.receiptList.size() == 0}">
            <h5><fmt:message key='YouHaveNoReceipts'/></h5>
        </c:when>
    </c:choose>  <%--NO RECEIPTS MESSAGE--%>

    <%----------------------- TABLE of RECEIPTS ---------------------%>
    <table class="table table-bordered sortable">
        <thead>
            <tr>
                <th class="title"><fmt:message key='Id'/></th>
                <th class="title"><fmt:message key='Item'/></th>
                <th class="title"><fmt:message key='ProblemDescription'/></th>
                <th class="title"><fmt:message key='Price'/></th>
                <th class="title"><fmt:message key='Date'/></th>
                <th class="title"><fmt:message key='Status'/></th>
                <th class="title"><fmt:message key='UserId'/></th>
                <th class="title"><fmt:message key='UserLogin'/></th>
                <th class="title"><fmt:message key='MasterId'/></th>
                <th class="title"><fmt:message key='MasterLogin'/></th>
                <th class="feedback"><fmt:message key='Feedback'/></th>
            </tr>
        </thead>

        <tbody>
        <%--<tr>--%>
            <c:forEach var="receipt" items="${receiptList}">
                <tr>
                    <td class="title">${receipt.id}</td>
                    <td class="title">${receipt.item}
                        <form action="manager/editReceipt" method="get" class="m-r-2">
                            <input type="hidden" name="receiptID" value="${receipt.id}">
                            <input type="hidden" name="command" value="editReceipt">
                            <input type="submit" value="<fmt:message key='Edit'/>" class="btn btn-secondary me-1">
                        </form>
                    </td>
                    <td class="title">${receipt.description}</td>
                    <td class="title">${receipt.price}</td>
                    <td class="title">${receipt.createDate}</td>
                    <td class="title">${receipt.status}</td>
                    <td class="title">${receipt.user_id}</td>
                    <td class="title">${receipt.userLogin}</td>
                    <td class="title">${receipt.master_id}</td>
                    <td class="title">${receipt.masterLogin}</td>
                    <td class="feedback">
                        <c:choose>
                            <c:when test="${receipt.feedback != null}">
                                ${receipt.feedback}
                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>



            <footer>
                    <%--${receiptList}--%>
                <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
                    © 2021 Copyright:
                    <a class="text-reset fw-bold">repair-agency.com</a>
                </div>
            </footer>
            </c:when>
            <c:when test="${!sessionScope.role.equals('MANAGER')}">
                <h2><fmt:message key='PleaseLoginAsManager'/></h2> <%--// TODO BUTTON LOGIN--%>
            </c:when>
            </c:choose>
</body>
</html>

