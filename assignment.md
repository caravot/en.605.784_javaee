# Overview

This week students will continue enhancing a design of the SRS project focusing on the "Registration status report" use case.

As you remember, in the Module 8 assignment you've implemented the stateless session bean called Status. This session bean was responsible for building all the reports running the JHU database queries.

Now you have to design and implement two entity beans, RegistrarBean and CourseBean. With this enhancement, the Status session bean will no longer have to directly access the database. Instead, it will use the RegistrarBean and the CourseBean entity beans for data processing. Each entity bean represents the object view of the JHU database to be requested from the database in order to create the reports described in the Session 8 assignment.

I would like you to put some thought into design and implementation of those two entity beans. Specifically, think about the segregation of functionality between the Status session bean and those two entity beans, RegistrarBean and CourseBean, from the perspective of responsibilities of each bean and respectively, the methods you want to have in each bean.


# What to Submit
Please create and submit one 'master' jar file containing:

* A README file with your name and assignment number;
* All the Java source files you developed that are used to run your application;
* HTML documents
* WebLogic config.xml file;
* All screen shots of user screens during run-time of your application;
* Please provide detailed instructions of how to deploy your application, such as what has to be modified, and where, and what is a directory location for different classes or other files;
* Also, please provide the database files you used in your application.