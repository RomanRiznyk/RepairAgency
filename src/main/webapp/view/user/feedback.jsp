<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
  <title>Feedback</title>
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
  </style>
</head>

<body class="container">

<c:choose>
  <c:when test="${sessionScope.role.equals('USER')}">
    <header class="d-flex flex-wrap justify-content-between py-3 mb-4 border-bottom">
        <%----------------------- LOCALIZATION BUTTONS ---------------------%>
      <div class="col-md-3 d-flex justify-content-start">
        <c:choose>
          <c:when test="${sessionScope.lang == null || 'en'.equals(sessionScope.lang)}">
            <form action="feedback" method="post">
              <input type="hidden" name="lang" value="ua">
              <input type="hidden" name="login" value=${login}>
              <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-outline-secondary">
            </form>
            <form action="feedback" method="post" class="mx-2">
              <input type="hidden" name="lang" value="en">
              <input type="hidden" name="login" value=${login}>
              <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-secondary">
            </form>
          </c:when>
          <c:when test="${'ua'.equals(sessionScope.lang)}">
            <form action="feedback" method="post">
              <input type="hidden" name="lang" value="ua">
              <input type="hidden" name="login" value=${login}>
              <input type="submit" value="<fmt:message key='Ua'/>" class="w-100 btn btn-secondary">
            </form>
            <form action="feedback" method="post" class="mx-2">
              <input type="hidden" name="lang" value="en">
              <input type="hidden" name="login" value=${login}>
              <input type="submit" value="<fmt:message key='En'/>" class="w-100 btn btn-outline-secondary">
            </form>
          </c:when>
        </c:choose>
      </div>
        <%----------------------- RECEIPTS and MAIN PAGE BUTTONS ---------------------%>
      <div class="col-md-3 d-flex justify-content-end">
        <form action="/repair/user" method="post" class="m-r-2">
          <input type="submit" value="<fmt:message key="Receipts"/>" class="btn btn-secondary me-2">
        </form>
        <form action="/repair" method="post">
          <input type="submit" value="<fmt:message key='MainPage'/>" class="btn btn-outline-secondary me-2">
        </form>
        <form action="/repair/logout" method="post">
          <input type="submit" value="<fmt:message key='logout'/>" class="btn btn-outline-secondary me-2">
        </form>
      </div>
    </header>

    <%--------------- MAIN BODY -------------------%>

    <h3><fmt:message key='LeaveYourFeedbackOn'/> ${item}: </h3>
    <form action="addFeedback" method="post" class="m-r-2">
      <textarea class="form-control my-5" rows="10" name="feedback" placeholder="<fmt:message key='WriteYourFeedbackHere'/>" required></textarea>
      <input type="hidden" name="id" value="${id}">
      <input type="submit" value="<fmt:message key='PostFeedback'/>" class="w-25 btn btn-secondary">
    </form>
    <footer>
    </footer>
  </c:when>
  <c:when test="${!sessionScope.role.equals('USER')}">
    <h2 style="color: coral"><fmt:message key='PleaseLoginAsUser'/>!</h2>
  </c:when>
</c:choose>
</body>
</html>
