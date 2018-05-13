
<%@ page import="ravotta.carrie.CoursesSupportBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>SRS - Course Registration</title>
</head>
<body>
<h1>Course Registration</h1>
<form action="/registration" method="post">
    <input type="hidden" name="action" value="register">
    <select id="course" name="course">
        <%
            CoursesSupportBean coursesSupportBean = new CoursesSupportBean();
            List<String> courses = coursesSupportBean.getCourses();
            for (String cname : courses) {
        %>
            <option value="<%= cname %>"><%= cname %></option>
        <% } %>
    </select>

    <input type="submit" value="Register">
</form>
</body>
</html>