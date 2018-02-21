Starting from this assignment and through the end of semester you will be developing Java EE-centric web application called "Student Registration System" to support the school's registration online process.

You will need to design and implement the Student Registration System (SRS). In this system a student who would like to register for a desired course should either already have UserId/Password or he/she should obtain UserId/Password by filling out the registration form. After logging into the system a student can pick up a course that is available and to register for the course. The school registration office should be able to inquire about current status in a form of the online report showing the list of all students registered for a particular course.


This week we are going to start designing the first two parts of the application that will include working on web components, Java Servlets, and JavaServer Pages (JSPs). This week's programming assignment is divided into two parts:

Part 1. The Java Servlets Assignment
Part 2. The JavaServer Pages Assignment
Obviously, there will be numerous client interactions with a system. Each request coming from the client has to be processed considering potentialy having errors, so that an appropriate error message has to be sent back.

Now, read the attached Assignment Instructions (in pdf format) for details on this assignment.

## What to Submit
Please create and submit one 'master' jar file containing the following directory structure:

* LastnameFirstname/
* /database (Database directory)
* /webapp (Your SRS Web Application, JSPs and HTMLs)
* /webapp/WEB-INF/src (Your Servlet source code)
* /srs.resources (Files required to run SRS, like application.xml)
* /src (Source code for your stand alone application)
* /resources (Files required to run your stand alone application)
* /screenshots

When generating jar files for this course, make sure you name it as LastnameFirstnameAssignment.jar, example: FeliksonLeonidHW1.jar.

## Example
````
$jar tvf FeliksonLeonidHW1.jar
$feliksonleonid/database/
$feliksonleonid/webapp/login.html
$feliksonleonid/webapp/registrarCourse.jsp
$feliksonleonid/webapp/WEB-INF/web.xml
$feliksonleonid/webapp/WEB-INF/src/edu/jhu/JavaEE/felikson/leonid/LoginServlet.java
$feliksonleonid/srs.resources/application.xml
$feliksonleonid/src/edu/jhu/JavaEE/felikson/leonid/DatabasePopulator.java
$feliksonleonid/sa.resources/populateCourses.sql
$feliksonleonid/screenshots/01_welcome.jpg
````

## Use of Directories

database:

* Submit your database with actual data that you loaded running your stand alone application.
* Make sure that a student with UserId "weblogic" and with password "weblogic" exists in your database and is available for use (this UserID/Password will be used during grading process).
* All courses in the REGISTRAR table should have NUMBER_STUDENTS_REGISTERED less then the value you defined in the web.xml.

webapp:

All JSPs and HTMLs should go under this directory. Your web.xml should be under webapp/WEB-INF. The source code for your Servlets and any supporting classes should be on webapp/WEB-INF/src directory.

src:

The source code for your Servlets and any supporting classes should be on webapp/WEB-INF/src directory. The source code that represents your stand-alone application has to be located in src directory.

resources:

Files required by your web application should be under the srs.resources directory. Files required by your stand alone application should be under the sa.resources directory.

screenshots:

Put all screenshots under screenshots directory. Screenshots file should be named NN_DESCRIPTION.jpg. Example: 01_welcome.jpg, 02_loginFailed.jpg.

Packaging:

Make sure that all classes you create in your assignment belong to a package named "edu.jhu.JavaEE.lastname.firstname", example: edu.jhu.Java EE.felikson.leonid.

Submit your file to this Assignment item.