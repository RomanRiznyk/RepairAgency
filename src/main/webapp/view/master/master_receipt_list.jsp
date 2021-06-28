<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Master</title>
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
        .form {
            float: right;
        }
    </style>
</head>
<body class="container">
<c:choose>
    <c:when test="${sessionScope.role.equals('MASTER')}">
        <header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
                <%----------------------- LOCALIZATION BUTTONS ---------------------%>
            <div class="col-md-3 d-flex justify-content-start">
                <c:choose>
                    <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                        <form action="master" method="post">
                            <input type="hidden" name="lang" value="ua">
                            <input type="hidden" name="receiptList" value="${requestScope.receiptList}">
                            <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                        </form>
                        <form action="master" method="post" class="mx-2">
                            <input type="hidden" name="lang" value="en">
                            <input type="hidden" name="receiptList" value="${requestScope.receiptList}">
                            <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                        </form>
                    </c:when>

                    <c:when test="${'ua'.equals(sessionScope.lang)}">
                        <form action="master" method="post">
                            <input type="hidden" name="lang" value="ua">
                            <input type="hidden" name="receiptList" value="${requestScope.receiptList}">
                            <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                        </form>
                        <form action="master" method="post" class="mx-2">
                            <input type="hidden" name="lang" value="en">
                            <input type="hidden" name="receiptList" value="${requestScope.receiptList}">
                            <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                        </form>
                    </c:when>
                </c:choose>
            </div>

            <div class="col-md-3 d-flex justify-content-end">
                <form action="/repair" method="post">
                    <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2 mx-2">
                </form>
                <form action="/repair/logout" method="post" class="form">
                    <input type="submit" value="<fmt:message key='logout'/>" class="btn btn-secondary me-2">
                </form>
            </div>
        </header>


    <%--------------- MAIN BODY -------------------%>
<h3 class="fw-normal"><fmt:message key='Hello'/> <span style='color: blue;'>${sessionScope.login}</span>! </h3>
        <%--------------- ERRORS MESSAGES -------------------%>
        <c:choose>
            <c:when test="${errorMessage == 'AlreadyLoggedIN'}">
                <p style="color:#ff0000"><fmt:message key='AlreadyLoggedIN'/></p><br/>
            </c:when>
            <c:when test="${errorMessage == 'HasNoRights'}">
                <p style="color:#ff0000"><fmt:message key='HasNoRights'/></p><br/>
            </c:when>
        </c:choose>

        <h3><fmt:message key='TableOfReceipts'/></h3>
        <table class="table table-bordered sortable">
            <thead>
            <tr>
                <th class="title"><fmt:message key='Id'/></th>
                <th class="title"><fmt:message key='UserLogin'/></th>
                <th class="title"><fmt:message key='MasterLogin'/></th>
                <th class="title"><fmt:message key='Item'/></th>
                <th class="title"><fmt:message key='Description'/></th>
                <th class="title"><fmt:message key='Price'/></th>
                <th class="title"><fmt:message key='CreateDate'/></th>
                <th class="title"><fmt:message key='LastUpdate'/></th>
                <th class="title"><fmt:message key='Status'/></th>
                <th class="title"><fmt:message key='Feedback'/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <c:forEach var="receipt" items="${receiptList}">
            <tr>
                <td class="title"> ${receipt.id} </td>
                <td class="title">${receipt.userLogin}</td>
                <td class="title">${receipt.masterLogin}</td>
                <td class="title"> ${receipt.item} </td>
                <td class="title">${receipt.description}</td>
                <td class="title">${receipt.price}</td>
                <td class="title">${receipt.createDate}</td>
                <td class="title">- todo </td>
                <td class="title">
                            ${receipt.status}
                    <c:choose>
                        <c:when test="${!receipt.status.equals('Waiting for payment')
                                && !receipt.status.equals('Canceled')
                                && sessionScope.role.equals('MASTER')
                                && (receipt.feedback == null || ''.equals(receipt.feedback)) }">
                            <div class="dropdown">
                                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownStatusButton1"
                                        data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <fmt:message key='SetStatus'/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <form action="master" method="post">
                                        <input type="hidden" name="receiptId" value="${receipt.id}">
                                        <input style="color: orange" class="dropdown-item" type="submit" name="newStatus"
                                               value="In work">
                                    </form>
                                    <form action="master" method="post">
                                        <input type="hidden" name="receiptId" value="${receipt.id}">
                                        <input style="color: #1aa016" class="dropdown-item" type="submit" name="newStatus" value="Done">
                                    </form>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </td>
                <td class="title">${receipt.feedback}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <footer>
        </footer>
    </c:when>
    <c:when test="${!sessionScope.role.equals('MASTER')}">
        <h2 style="color: coral"><fmt:message key='PleaseLoginAsMaster'/>!</h2>
    </c:when>
</c:choose>
</body>
</html>
