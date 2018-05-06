# Overview

On the one hand, in the "Registration" case, the only state of inconsistency we should anticipate is an attempt to create a duplicate "UserID." We can avoid this situation by forcing the DBMS transaction manager to handle it. Perhaps if we had a more complex business rule like "not more than X students registering during a given timeframe, say, an hour or a day" established, it would create more complex transaction rules.

On the other hand, the "Course" case is a good candidate to become a transactional action if we add to it a business rule that limits the number of students registered for a given class.

So, in this assignment l would like you to modify the "Course" use case to test and demonstrate J2EE transaction management. Here are requirements:

* Instead of updating the database to register for a given course by the RegistrarCourse.jsp let's delegate this business function to a new CMT (container-managed transactions) entity bean, RegistrarCourseBean, to model the REGISTRAR database table. So, you need to design and implement this bean first.

* As a result, the RegistrarCourse.jsp will call RegistrarCourseBean to update appropriate properties.

* WebLogic Server implements pessimistic locking on an entity bean. It means that every entity bean instance gets locked during the execution of its method and that automatically provides a "serializable" mode of multiple transactions. So, to test and to demonstrate successful and unsuccessful transaction scenarios, we either have to redesign this bean to become of BMT (bean-managed trasactions) type or we have to find another way to simulate transaction failure with a rollback. I propose the latter approach.

* You have to model a transaction failure by running into a "timeout" exception. Please simulate a long-run method within a transaction context (say, create a conditional long-running "dummy" loop) and utilize WebLogic server container-managed transactions time-out capabilities. The long-running method should be controllable by the client that notifies the entity bean to run this method either as a "short-run" or a "long-run".

Tip for the RegistrarCourseBean entity bean implementation: To simulate long-running method, you can do something like:



try
{
Thread.sleep(15000); // 15 seconds
}
catch (InterruptedExpection ie)
{
throw new EJBException(ie);
}
Tip for the RegistrarCourseBean entity bean deployment: Read "Setting Transaction TimeOuts" in http://docs.oracle.com/cd/E24329_01/web.1211/e24377/trxejb.htm#autoId17

* In order to test, please create a Java application as a client of RegistrarCourseBean entity bean. Have this client accept a parameter to control a short-run/long-run mode when calling the entity bean. Run it in a loop with every iteration switching the mode. Print out the result demonstrating successful/unsuccessful transactions. You can make a client use a direct JDBC connection to the REGISTRAR table (in between running transactions with entity bean) to read the current number of students registered for a particular course. You should see this number being incremented by 1 after every successfully completed transaction or remaining the same after every unsuccessfully completed transaction.


# What to Submit

Please create and submit one 'master' jar file containing:

* A README file with your name and assignment number;
* All Java source files you developed;
* All HTML files;
* config.xml file with your modifications to deploy application components
* All screen shots of user screens during run-time of your application;
* Please provide a detailed instruction of how to deploy your application, such as what has to be modified, and where, and what is a directory location for different classes or other files;
* Also, please provide the database files you used in your application.
