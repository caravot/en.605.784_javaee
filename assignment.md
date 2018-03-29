# Overview

In this assignment you have to develop a session bean to implement the "Registration status report" use case. The following is a description of this use case.

Imagine that the Registration Administration Office has to produce a report about current number of students registered for any given course or for all courses. In order to get such report, they have to have online access to the SRS system and the capability to generate the report.

To start off, you should create a simple HTML static page, Status.html, with one HTML form in it. This form will have one 'text' field labeled "Course Id" and a button.

There should be two registration status reports provided back to a client:

* Status report for all the courses
* Status report for a specific course

You should develop an EJB session bean called Status that will generate both reports. The first report will be provided as a result of clicking on "Submit" button with no specific "Course Id' parameter in the text field. The second report, for a given course, will be generated if a user has entered the required "Course Id".

In this assignment, please use the RegistrationController servlet (you have it developed in an earlier assignment) will call this Status EJB.

While calling Status EJB, I would like you to implement the following functionality:

* As a client to Status enterprise stateless session bean, it will get a reference to this bean and then call one of its business methods, getStatus(CourseId cid), passing an input parameter, Course Id, or calling getAllStatus() with no input parameters.
* After receiving back from the session bean some information, it should generate a dynamic HTML page to be sent back to web browser.
The session bean Status will do the rest of work generating a requested report. Status EJB will connect to the JHU database, select requested data (course id and number of registered students - from REGISTRAR table, course title - from COURSE table) and return it back for creating HTML page to be sent back web browser.

Based on a requested report, you should develop the bean to return one row or multiple rows. Each row will have a course id, course title and current number of registered students.

Finally, if a user entered invalid course Id (that does not exist in COURSE table of the JHU database), then Status EJB should generate error message and pass it back to a user in HTML format.

# What to Submit
Please create and submit one 'master' jar file containing:

* A README file with your name and assignment number;
* All Java source files you developed so far;
* All HTML files;
* All other supporting static content;
* All screen shots of user screens during run-time of your application;
* Please provide detailed instructions of how to deploy and run your application;
* In addition, please don't forget to include the database directory into your submission.