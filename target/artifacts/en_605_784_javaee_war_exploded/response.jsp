<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>SRS Reponse</title>
</head>
<body>
    <h2><%= request.getAttribute("message") %></h2>
    <p>Select your next action:</p>

    <p>
        <input type="radio" name="action" value="register"/> Register for the course<br />
        <input type="radio" name="action" value="logout"/> Logout
    </p>

    <a href="login.html">Back</a>
</body>
</html>