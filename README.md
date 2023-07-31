# Flight planner

A simple flight planning REST API built with Java Spring Boot framework and Postgresql database. Supports creating, searching, deleting flight and airport data. It has two storage options - in memory storage to Java data structures and a Postgresql database storage option.  

## Running the API

To run the ```flight-planner API``` clone this repo using `git clone` and build the project and run the application.

---
## Configuration options

### Selecting storage option

In the file ``src/main/resources/applications.properties``  set `version` variable to `database` or `in-memory` to choose storage option.

```
flightplanner.service.version=database
```

By default, all database tables are dropped and recreated on every application launch. Set `spring.liquibase.drop-first` to `false` in `application.properties` to make writing to database persistent.

```
spring.liquibase.drop-first=true
```
---

## Endpoints

The API supports 7 endpoints with 3 users - an admin user with power to add and delete flights, a testing user for clearing the database for testing purposes, a general customer for searching flights.

### GET

#### /admin-api/flights/{id}

#### /admin-api/airports

#### /api/flights/{id}

### PUT

#### /admin-api/flights

### POST

#### /testing-api/clear

#### /testing-api/clear

### DELETE

#### /admin-api/flights/{id}


---
