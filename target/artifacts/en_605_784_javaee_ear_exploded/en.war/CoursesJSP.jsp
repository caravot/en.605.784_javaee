<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- include the course support bean --%>
<jsp:useBean id="coursesBean" class="ravotta.carrie.CoursesSupportBean" scope="application"/>

<%-- get datasource name from session --%>
<% coursesBean.dsName = (String)request.getSession().getAttribute("DATASOURCE_NAME"); %>
<% coursesBean.wlsUrl = (String)request.getSession().getAttribute("WLS_URL"); %>

<html>
<head>
    <title>SRS - Course Registration</title>
</head>
<body>
<h2>Course List:</h2>
<form action="RegistrarCourse.jsp" method="post">
    <select name="course" id="course">
        <%-- loop through each course offered --%>
        <c:forEach var="item" items="${coursesBean.courses}">
            <option value="${item}">${item}</option>
        </c:forEach>
    </select>
    <button type="submit">Register</button>
</form>
</body>
</html>
