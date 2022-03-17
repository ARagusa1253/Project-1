Project 1 - Custom Object Relational Mapping Framework

- Antonio Ragusa
- Eris Balic
- Travis Stephens

Description
This ORM provides a platform for the transactions of a grocery store, from the perspective of both Customers and Employees.
The program utilizes several technology stacks to allow Customers to shop for items, and Employees to handle
the supply chain operations of the store. From the main menu, a user is given the opportunity to login as either a Customer,
or an Employee. Once logged in, the corresponding DAO layer contains the code and the methods necessary to perform
the user-specific tasks associated with their role.

Customers:
The Customer DAO connects with the Customer Database, which will allow for Customers to view and/or update their customer wallet. The
Customer wallet dictates their ability to purchase products. The Customer is able to add money to their wallet in order to have 
more money to spend on the groceries. In addition, a Customer can view their account status to see if they are eligible for the 
discounted prices on products, which kick in after a customer has spent a certain amount of money.

Employees:
The Employee DAO connects with the Employee Database, which will allow for Employees to manage both the HR concerns and the
inventory concerns for the store. From this menu, a new Employee may be added or a former Employee may be deleted.
The Employees can then reference methods from the Product DAO in order to add new Products or delete discontinued 
Products, or they can modify exisiting Product prices and quantities.

Products:
The Product DAO connects with the Product Database and contains the methods which commit changes to that Database, such as:
adding a new Product, deleting a discontinued Product, or changing any Product information or price.

To Install:
Java Reflection API Framework must be downloaded, then create a file called 'application.properties' with the following content:
url=jdbc:postgresql://team-4-enterprise.cvtq9j4axrge.us-east-1.rds.amazonaws.com:5432/postgres?currentSchema=project1
username=postgres
password=postgres

and place it in the following directory:
src\main\resources\application.properties




Project GitHub Repository:
https://github.com/ARagusa1253/Project-1

Project Trello Board:
https://trello.com/b/MgaX7N8h/project-1-workspace

Tech Stacks Utilized:
 Java 8
 JUnit
 Apache Maven
 PostGreSQL deployed on AWS RDS
 Git VCS (on GitHub)

Presentation:
 10-15 minute live demonstration of the implemented features using a simple demo application to showcase the ORM's functionality
 Presented to batch on Friday March 18th, 2022