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
            overflow: hidden;
            text-overflow: ellipsis;
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
                    <input type="hidden" name="receiptListForLang" value="${sessionScope.receiptList}">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                </form>
                <form action="manager" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="hidden" name="receiptListForLang" value="${sessionScope.receiptList}">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                </form>
            </c:when>

            <c:when test="${'ua'.equals(sessionScope.lang)}">
                <form action="manager" method="post">
                    <input type="hidden" name="lang" value="ua">
                    <input type="hidden" name="receiptListForLang" value="${sessionScope.receiptList}">
                    <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                </form>
                <form action="manager" method="post" class="mx-2">
                    <input type="hidden" name="lang" value="en">
                    <input type="hidden" name="receiptListForLang" value="${sessionScope.receiptList}">
                    <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                </form>
            </c:when>
        </c:choose>
    </div>
    <div class="d-flex justify-content-between">
    </div>
    <%-- ---------------MAIN and LOGOUT BUTTONS --------------- --%>
    <div class="col-md-3 d-flex justify-content-end">
                <form action="/repair" method="post"> <%--TODO strange /repair--%>
                    <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2">
                </form>
                <form action="/repair/logout" method="post" class="m-r-2">
                    <input type="submit" value="<fmt:message key='logout'/>" class="btn btn-secondary me-2">
                </form>
            </div>
</header>
    <%--------------- MAIN BODY -------------------%>
    <h3 class="fw-normal"><fmt:message key='Hello'/> <span style='color: green;'>${sessionScope.login}</span>! </h3>
        <%--------------- ERRORS MESSAGES -------------------%>
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
    <div class="d-flex flex-wrap justify-content-end py-1 mb-3">
        <c:choose>
            <c:when test="${applicationScope.loggedUsers == null}">Logged users == null</c:when>
        </c:choose>

            <div class="d-flex justify-content-end">
                    <%-------------- SORT BY DATE------------%>
                    <div class="dropdown d-flex justify-content-end mx-1">
                        <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="SortByDate"/>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <c:forEach var="sortAscDesc" items="${sortByAsc}">
                                <form action="manager" method="post">
                                    <input type="hidden" name="sortType" value='ByDate'>
                                    <input type="hidden" name="sortAscDesc" value="${sortAscDesc}">
                                    <input class="dropdown-item" type="submit" value="<fmt:message key="${sortAscDesc}"/>">
                                </form>
                            </c:forEach>
                        </div>
                    </div>
                    <%-------------- SORT BY PRICE------------%>
                    <div class="dropdown d-flex justify-content-end mx-1">
                        <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="SortByPrice"/>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <c:forEach var="sortAscDesc" items="${sortByAsc}">
                                <form action="manager" method="post">
                                    <input type="hidden" name="sortType" value='ByPrice'>
                                    <input type="hidden" name="sortAscDesc" value="${sortAscDesc}">
                                    <input class="dropdown-item" type="submit" value="<fmt:message key="${sortAscDesc}"/>">
                                </form>
                            </c:forEach>
                        </div>
                    </div>

                <div class="dropdown d-flex justify-content-end mx-1">
                        <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="SortByStatus"/>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <c:forEach var="sortAscDesc" items="${sortByAsc}">
                                <form action="manager" method="post">
                                    <input type="hidden" name="sortType" value='ByStatus'>
                                    <input type="hidden" name="sortAscDesc" value="${sortAscDesc}">
                                    <input class="dropdown-item" type="submit" value="<fmt:message key="${sortAscDesc}"/>">
                                </form>
                            </c:forEach>
                        </div>
                </div>
                <%-------------- MASTER FILTER ------------%>
                <div class="dropdown mx-1">
                    <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                            data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="FilterByMaster"/>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <form action="manager" method="post">
                            <input type="hidden" name="masterLogin" value="All masters">
                            <input class="dropdown-item" type="submit" value="All masters">
                        </form>
                        <c:forEach var="master" items="${masterList}">
                            <form action="manager" method="post">
                                <input type="hidden" name="filterType" value="ByMaster">
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
                        <fmt:message key="FilterByStatus"/>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownStatusButton">
                        <c:forEach var="status" items="${statusList}">
                            <form action="manager" method="post">
                                <input type="hidden" name="filterType" value="ByStatus">
                                <input class="dropdown-item" type="submit" name="status" value="${status}">
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
    </div>
    <%------------------------ RECEIPTS MESSAGE------------------------%>
    <c:choose>
        <c:when test="${sessionScope.receiptList.size() == 0}">
            <h5><fmt:message key='YouHaveNoReceipts'/></h5>
        </c:when>
    </c:choose>
    <%------------------------ PAGINATION------------------------%>
<%--<div class="d-flex flex-wrap justify-content-between py-1 mb-3">--%>
    <div class="d-flex flex-wrap justify-content-between py-1 mb-3">
        <div class="col-md-3 d-flex justify-content-start">
            <div>
                <h3><fmt:message key='TableOfReceipts'/></h3>
            </div>
        </div>

    <div class="d-flex flex justify-content-center">
        <c:forEach var="i" begin="1" end="${pages}">
            <c:choose>
                <c:when test="${pages != 1}">
                    <form action="manager" method="get">
                        <input type="hidden" name="page" value="${i}">
                        <%--<input type="hidden" name="rowNumber" value="${rowNumber}">--%>
                        <input type="submit" class="btn btn-outline-secondary mx-1" value="${i}">
                    </form>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>

    <div class="d-flex flex justify-content-end">
        <div class="dropdown mx-1">
            <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                    data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <fmt:message key="ReceiptsOnPage"/>
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <c:forEach var="rowNumber" items="${rowNumbers}">
                    <form action="manager" method="post">
                        <input type="hidden" name="rowNumber" value="${rowNumber}">
                        <input class="dropdown-item" type="submit" value="${rowNumber}">
                    </form>
                </c:forEach>
            </div>
        </div>
    </div>
    </div>
<%--</div>--%>
   <%-- <div class="d-flex flex justify-content-end">
        <div class="dropdown-menu" aria-labelledby="dropdownStatusButton">
            <c:forEach var="rowNumber" items="${sessionScope.rowNumbers}">
                <form action="manager" method="post">
                    <input type="hidden" name="rowNumber" value="${rowNumber}">
                    <input class="dropdown-item" type="submit" name="rowNumber" value="${rowNumber}">
                </form>
            </c:forEach>
        </div>
    </div>--%>
    <%----------------------- TABLE of RECEIPTS ---------------------%>
    <br>
    <table class="table table-bordered sortable">
        <thead>
            <tr>
                <th class="title"><fmt:message key='Id'/></th>
                <th class="title"><fmt:message key='Item'/></th>
                <th class="title"><fmt:message key='Description'/></th>
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
            <c:forEach var="receipt" items="${pageReceiptList}">
                <tr>
                    <td class="title">${receipt.id}</td>
                    <td class="title">${receipt.item}
                        <form action="manager/editReceipt" method="get" class="m-r-2">
                            <input type="hidden" name="receiptID" value="${receipt.id}">
                            <input type="submit" value="<fmt:message key='Edit'/>" class="btn btn-secondary me-1">
                        </form>
                    </td>
                    <td class="title">${receipt.description}</td>
                    <td class="title">${receipt.price}</td>
                    <td class="title">${receipt.createDate}</td>
                    <td class="title">
                        <c:choose>
                            <c:when test="${'Paid'.equals(receipt.status)}">
                                <p style="color: green"><fmt:message key='StatusPaid'/>
                            </c:when>
                            <c:when test="${'Canceled'.equals(receipt.status)}">
                                <p style="color: red"><fmt:message key='StatusCanceled'/>
                            </c:when>
                            <c:when test="${'Waiting for payment'.equals(receipt.status)}">
                                <p style="color: dodgerblue"><fmt:message key='StatusWaitingPayment'/>
                            </c:when>
                            <c:when test="${'In work'.equals(receipt.status)}">
                                 <p style="color: coral"><fmt:message key='StatusInWork'/>
                            </c:when>
                            <c:when test="${'Done'.equals(receipt.status)}">
                                 <p style="color: lightseagreen"><fmt:message key='StatusDone'/>
                            </c:when>
                        </c:choose>
                    </td>
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

            </footer>
            </c:when>
            <c:when test="${!sessionScope.role.equals('MANAGER')}">
                <h2 style="color: coral"><fmt:message key='PleaseLoginAsManager'/>!</h2>
            </c:when>
            </c:choose>
</body>
</html>

