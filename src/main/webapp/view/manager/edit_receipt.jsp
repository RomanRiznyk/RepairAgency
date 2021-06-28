<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>EditReceipt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
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
            min-width: 250px;
            max-width: 200px;
        }
    </style>
</head>
<body class="container">

<%----------- ---------------HEADER---------------- --%>
        <header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
                <%----------------------- LOCALIZATION BUTTONS ---------------------%>
            <div class="col-md-3 d-flex justify-content-start">
                <c:choose>
                    <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
                        <form action="editReceipt" method="post">
                            <input type="hidden" name="lang" value="ua">
                            <input type="hidden" name="receiptID" value="${receipt.getId()}">
                            <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
                        </form>
                        <form action="editReceipt" method="post" class="mx-2">
                            <input type="hidden" name="lang" value="en">
                            <input type="hidden" name="receiptID" value="${receipt.getId()}">
                            <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
                        </form>
                    </c:when>

                    <c:when test="${'ua'.equals(sessionScope.lang)}">
                        <form action="editReceipt" method="post">
                            <input type="hidden" name="lang" value="ua">
                            <input type="hidden" name="receiptID" value="${receipt.getId()}">
                            <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
                        </form>
                        <form action="editReceipt" method="post" class="mx-2">
                            <input type="hidden" name="lang" value="en">
                            <input type="hidden" name="receiptID" value="${receipt.getId()}">
                            <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
                        </form>
                    </c:when>
                </c:choose>
            </div>
            <div class="col-md-3 d-flex justify-content-end">
                <c:choose>
                    <c:when test="${sessionScope.role.equals('MANAGER')}">
                        <form action="/repair" method="post">
                            <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2">
                        </form>
                        <form action="/repair/manager" method="post" class="m-r-2">
                            <input type="submit" value="<fmt:message key='Receipts'/>" class="btn btn-outline-secondary me-2">
                        </form>
                        <form action="/repair/logout" method="post" class="m-r-2">
                            <input type="submit" value="<fmt:message key='logout'/>" class="btn btn-secondary me-2">
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </header>
<c:choose>
    <c:when test="${sessionScope.role.equals('MANAGER') || sessionScope.role.equals('MASTER')}">
<%----------- ---------------RECEIPT TABLE---------------- --%>
        <h3><fmt:message key='EditReceipt'/></h3><br>
        <%--ReceiptId = ${receipt.id}--%>
        <table class="table table-bordered sortable">
            <tr>
                <th class="title"><fmt:message key='Position'/></th>
                <th class="title"><fmt:message key='CurrentValues'/></th>
                <th class="title"><fmt:message key='EditableValues'/></th>
            </tr>
            <tr>
                <th class="title"><fmt:message key='Id'/></th>
                <td class="title">${receipt.id}</td>
                <td class="title"></td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='MasterId'/></th>
                <td class="title">${receipt.master_id}</td>
                <td class="title"></td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='MasterLogin'/></th>
                <td class="title">${receipt.masterLogin}</td>
                <td class="title"></td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='UserId'/></th>
                <td class="title">${receipt.user_id}</td>
                <td class="title"></td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='UserLogin'/></th>
                <td class="title">${receipt.userLogin}</td>
                <td class="title"></td>
            </tr>
            <tr>
                    <th class="title"><fmt:message key='UserBalance'/></th>
                    <td class="title">${balance}$</td>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.role.equals('MANAGER')}">
                                <form action="editReceipt" method="post">
                                    <input type="hidden" name="receiptID" value="${receipt.id}">
                                    <div class="input-group mb-3 w-75">
                                        <span class="input-group-text">$</span>
                                        <input name="add balance" type="number" class="form-control" min="0" max="1000000" value="0" required>
                                        <span class="input-group-text">.00</span>
                                        <input type="submit" name="addBalance" value="<fmt:message key='TopUpBalance'/>" class="btn btn-outline-secondary">
                                    </div>
                                </form>
                            </c:when>
                        </c:choose>
                    </td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='Item'/></th>
                <td class="title">${receipt.item}</td>
                <td class="title"></td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='Description'/></th>
                <td class="title">${receipt.description}</td>
                <td class="title"></td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='Price'/></th>
                <td class="title">${receipt.price} $</td>
                <td>
                    <c:choose>
                        <c:when test="${sessionScope.role.equals('MANAGER') && (receipt.status == 'Waiting for payment')}">
                            <form action="editReceipt" method="post">
                                <input type="hidden" name="receiptID" value="${receipt.id}">
                                <div class="input-group mb-3 w-75">
                                    <span class="input-group-text">$</span>
                                    <input name="price" type="number" class="form-control" id="floatingInput" min="0" max="1000000" value="0" required>
                                    <span class="input-group-text">.00</span>
                                    <input type="submit" value="<fmt:message key='SetPrice'/>" class="btn btn-outline-secondary">
                                </div>
                            </form>
                        </c:when>
                    </c:choose>
                    <c:choose>
                    <c:when test="${requestScope.balance >= requestScope.receipt.price
                        && sessionScope.role.equals('MANAGER')
                        && requestScope.receipt.status.equals('Waiting for payment')}">
                        <form action="editReceipt" method="post">
                            <input type="hidden" name="receiptID" value="${receipt.id}">
                            <input type="hidden" name="userLogin" value="${receipt.userLogin}">
                            <input type="hidden" name="newBalance" value="${requestScope.balance.subtract(requestScope.receipt.price)}">
                                <div class="form-floating d-flex flex-grow-1">
                                    <input type="submit" value="<fmt:message key='PayForRepair'/>" class="btn btn-success me-2">
                                </div>
                        </form>
                    </c:when>
                    <c:when test="${requestScope.balance < requestScope.receipt.price
                                                                        && sessionScope.role.equals('MANAGER')
                                                                        && requestScope.receipt.status.equals('Waiting for payment')}">
                        <h6 style="color:#ff0000"><fmt:message key='NotEnoughBalance'/>
                    </c:when>
                    </c:choose>

                </td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='Feedback'/></th>
                <td class="title">${receipt.feedback}</td>
                <td class="title"></td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='Status'/></th>
                <td class="title">
                    <c:choose>
                        <c:when test="${'Paid'.equals(receipt.status)}">
                            <h6 style="color: green"><fmt:message key='StatusPaid'/>
                        </c:when>
                        <c:when test="${'Canceled'.equals(receipt.status)}">
                            <h6 style="color: red"><fmt:message key='StatusCanceled'/>
                        </c:when>
                         <c:when test="${'Waiting for payment'.equals(receipt.status)}">
                             <h6 style="color: dodgerblue"><fmt:message key='StatusWaitingPayment'/>
                         </c:when>
                         <c:when test="${'In work'.equals(receipt.status)}">
                             <h6 style="color: coral"><fmt:message key='StatusInWork'/>
                         </c:when>
                         <c:when test="${'Done'.equals(receipt.status)}">
                                 <h6 style="color: lightseagreen"><fmt:message key='StatusDone'/>
                         </c:when>
                        <c:when test="${'true'.equals(requestScope.paidSuccess)}">
                            <h6 style="color: green"><fmt:message key='SuccessfullyPaid'/>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${!requestScope.receipt.status.equals('Done')
                        && !requestScope.receipt.status.equals('In work')
                        && sessionScope.role.equals('MANAGER')}">
                            <div class="dropdown">
                                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownStatusButton"
                                        data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <fmt:message key='SetStatus'/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <form action="editReceipt" method="post">
                                        <input type="hidden" name="receiptID" value="${receipt.id}">
                                        <input type="hidden" name="oldStatus" value="${receipt.status}">
                                        <input style="color: dodgerblue" class="dropdown-item" type="submit" name="status" value="Waiting for payment">
                                    </form>
                                    <c:choose>
                                        <c:when test="${receipt.status != 'Paid' && requestScope.balance >= requestScope.receipt.price }">
                                            <form action="editReceipt" method="post">
                                                <input type="hidden" name="receiptID" value="${receipt.id}">
                                                <input type="hidden" name="userLogin" value="${receipt.userLogin}">
                                                <input type="hidden" name="newBalance" value="${requestScope.balance.subtract(requestScope.receipt.price)}">
                                                <input style="color: green" class="dropdown-item" type="submit" name="status" value="Paid (pay for receipt)">
                                            </form>
                                        </c:when>
                                    </c:choose>
                                    <form action="editReceipt" method="post">
                                        <input type="hidden" name="receiptID" value="${receipt.id}">
                                        <input type="hidden" name="oldStatus" value="${receipt.status}">
                                        <input style="color:red" class="dropdown-item" type="submit" name="status" value="Canceled">
                                    </form>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th class="title"><fmt:message key='Master'/></th>
                <td class="title">${receipt.masterLogin}</td>
                <td>
                    <c:choose>
                        <c:when test="${'MANAGER'.equals(sessionScope.role) && 'Paid'.equals(receipt.status)}">
                            <div class="dropdown">
                                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownEngineerButton"
                                        data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <fmt:message key='SetMaster'/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <c:forEach var="master" items="${masterList}">
                                        <form action="editReceipt" method="post">
                                            <input type="hidden" name="masterId" value="${master.id}">
                                            <input type="hidden" name="receiptID" value="${receipt.id}">
                                            <input class="dropdown-item" type="submit" value="${master.login}">
                                        </form>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </table>

        <c:choose>
            <c:when test="${requestScope.balance >= requestScope.receipt.price
                        && sessionScope.role.equals('MANAGER')
                        && requestScope.receipt.status.equals('Waiting for payment')}">
                <form action="editReceipt" method="post">
                    <input type="hidden" name="receiptID" value="${receipt.id}">
                    <input type="hidden" name="userLogin" value="${receipt.userLogin}">
                    <input type="hidden" name="newWallet" value="${requestScope.balance.subtract(requestScope.receipt.price)}">
                </form>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${!sessionScope.role.equals('MANAGER') && !sessionScope.role.equals('MASTER')}">
        <h2 style="color: coral"><fmt:message key='PleaseLoginAsManager'/>!</h2>
        
    </c:when>
</c:choose>
        <footer>
        </footer>

</body>
</html>
