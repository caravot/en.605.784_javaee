<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- include the registration bean --%>
<jsp:useBean id="registrationBean" class="ravotta.carrie.RegistrationSupportBean" scope="application"/>

<%-- get datasource name from session --%>
<% registrationBean.dsName = (String)request.getSession().getAttribute("DATASOURCE_NAME"); %>
<% registrationBean.wlsUrl = (String)request.getSession().getAttribute("WLS_URL"); %>

<%-- get the course name --%>
<% registrationBean.setCourse(request.getParameter("course")); %>

<%-- get the course capacity value from the JSP initial parameter --%>
<%!
    int CourseCapacity = 0;
    public void jspInit() {
        ServletConfig config = getServletConfig();
        CourseCapacity = Integer.parseInt(config.getInitParameter("CourseCapacity"));

    }
%>

<%-- set the course capacity --%>
<% registrationBean.setCourseCapacity(CourseCapacity); %>
<html>
<head>
    <title>SRS - Course Registration</title>
</head>
<body>

<%-- attempt to register the student --%>
<h2><%= registrationBean.addRegistrar() %></h2>
</body>
</html>
