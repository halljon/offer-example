[![Build Status](https://travis-ci.com/halljon/offer-example.svg?branch=master)](https://travis-ci.com/halljon/offer-example)

# offer-example
An example project based around a 'offer' demonstrating Spring Boot and current best practices.

Technologies used:
* Spring Boot
* H2 database
* PlantUML for diagrams.  Diagrams generated by gravizo.com.

# How to get started
Clone this repository and then run:

`mvn clean install`

# How to run and expose REST API
After building the project:

`cd offer-example-web`

then:

`mvn spring-boot:run`

This will start the Spring Boot fat JAR webapp.

# Modules

## Module overview
This project has a number of modules, which albeit don't contain much code, but are there to show how a complex system could be split up:

* Database - contains schema DDL and DML to populate tables with initial data.
    * Database creation and data population performed by running standard Maven clean install, there is a simple Spring Boot application and this effectively bootstraps the database creation using some POC (Proof Of Concept) type features that Spring Boot has (schema.sql and data.sql).
* Domain - domain object(s).
* Persistence - Persistence layer for domain objects.
* Service - Service layer interacting with persistence and controlling transactions.
* Web - REST controllers.

## Alternative module structures
The project could have been split into other module structures, namely something like the below:

### Alternative module structure 1
* Core
* Web

### Alternative module structure 2
* Single module - i.e. like Spring Boot starter.


# Use cases
![Alt text](https://g.gravizo.com/source/svg?https://raw.githubusercontent.com/halljon/offer-example/master/docs/use-case-overview.plantuml)

# Basic REST API
Please note: this is basic API, see things to do where there is already a ticket raised to document properly)

## Create offer
```
URL: /v1/offers/{merchantIdentifier}
Method: POST  <-- Note: this is POST instead of PUT because it is not idempotent - the Offer Identifier is generated by the server-side.
Request body: Offer JSON
Response code: 200 for OK, 400 for bad request - invalid offer.
Response body: Offer Identifier of newly created Offer
```

## Cancel offer
```
URL: /v1/offers/{merchantIdentifier}/{offerIdentifier}  
Method: DELETE  
Response code: Return 200 for OK, 404 for Offer not found
```

## Find active offers
```
URL: /v1/offers/{merchantIdentifier}
Method: GET
Response code: Return 200 for OK
Response body: active Offers JSON
```

## Find active offer
```
URL: /v1/offers/{merchantIdentifier}/{offerIdentifier}
Method: GET
Response code: Return 200 for OK, 404 for Offer not found
Response body: active Offer
```

# Things to do 
* [Exception handling configured for controllers to give a 500 whenever anything goes wrong](https://github.com/halljon/offer-example/issues/30) 
* Change timestamp to local date time.
* [Document REST API](https://github.com/halljon/offer-example/issues/26)
* Add caching of currencies, so that currency code can be quickly checked, rather than simply relying on the database foreign key constraint.
* Tidy up application.properties, as currently in a few modules.
* Look at the //TODO: comments, add issues or remove.
* Auditing and accountability columns omitted from schema.
* Request correlation id.

# Points to note
* Application context slicing by virtue of repository module.
* Application context slicing using Spring Boot's support.
* DateTimeService created so more control over dates could be added - i.e. to control what date/time it is beyond simply getting current system time.
* Please note: be careful with H2 local file locking - i.e. make sure it isn't locked by another process or a locking exception will be raised.
* Offer identifier, merchant identifier and offering identifier (the link to a product/service) are strings to allow UUID values, helping bridge microservices, where unique identifiers might need to be embedded from one microservice to another based on the service's bound context.  For example, product/service and merchant might be separate microservices.

# Assumptions
Timezones have been ignored.
