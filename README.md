## GOAL

* A Spring-Boot project to expose a GET API to fetch the Employee's corporate details along with his/her insurance
  details extracted from a 3rd party API on basis of `employee id`.

## Important Notes

* `H2` in-mem database is used in the application. The test data is stored in [data.sql](src/main/resources/data.sql) and the db
  configs are inside `application.yaml` file
* I'm using a third party API service [Mockaroo](https://www.mockaroo.com/), which creates a mock response
  for `insuranceDetails` POJO when called.
* The response coming from `Mockaroo` API is totally generated on random basis and might be different on each call.
* I have configured Swagger so the APIs can be tested directly from Swagger UI.
  URL: (http://localhost:8080/swagger-ui/index.html).
* Have added Jacoco for code coverage and reports.

## Test

* To test the application follow the below instructions:
    * Run the `mvn clean install` which will download all the required dependencies of the project
    * Now boot up the application.
    * Application will get started on `8080` port
* There's no local configurations required or no-dependency is there which would block the application from running on
  different systems.
* Covered 100% unit test cases for business logic classes.

### A Positive Test

#### Payload

```
http://localhost:8080/employee/insurance?empId=PS1234
```

#### Response body

```json
{
  "message": "Insurance details fetched successfully",
  "data": {
    "empId": "PS1234",
    "empName": "Rahul Kumar",
    "empEmail": "rahul@xyz.com",
    "isInsuranceEnrolled": true,
    "insuranceType": "LIFE"
  }
}
```

### A Negative Test

#### Payload

```
http://localhost:8080/employee/insurance?empId=PS11111
```

#### Response body

```json
{
  "message": "Employee not found with id: PS11111",
  "data": null
}
```

### An Error Case Test

* Without `empId` field.

#### Payload

```
http://localhost:8080/employee/insurance
```

#### Response body

```json
{
  "message": "Required request parameter 'empId' for method parameter type String is not present",
  "data": null
}
```
