## GOAL

* This is a Spring-Boot project to expose a GET API to fetch the Employee's corporate details along with the insurance
  details fetched from 3rd party API

## Important Notes

* `H2` in mem database is used in the application. The test data is stored in `src/main/resources/data.sql` and the db
  configs are inside `application.yaml` file
* I'm using a third party API service ([Mockaroo](www.mockaroo.com)) which creates a mock response
  for `insuranceDetails` POJO when called.
* I have configured Swagger so the APIs can be tested directly from Swagger UI.
  URL: (http://localhost:8080/swagger-ui/index.html).
* Have added Jacoco for code coverage and reports.

## Test

* To test the application follow the below instructions:
    * Run the `mvn clean install` which will download all the required dependencies of the project
    * Just boot up the application.
    * Application will get started on `8080` port
* There's no local configurations required or no-dependency is there which would block the application from running on
  different systems.
* Covered 100% unit test cases for `EmployeeService` class.
