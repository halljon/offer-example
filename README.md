[![Build Status](https://travis-ci.com/halljon/offer-example.svg?branch=master)](https://travis-ci.com/halljon/offer-example)

# offer-example
An example project based around a 'offer' demonstrating Spring Boot and current best practices

Technologies used:
* Spring Boot
* H2 database

Description of modules:
* Database - contains schema DDL and DML to populate tables with initial data.
    * Database creation and data population performed by running standard Maven clean install.
* Domain
* Persistence
* Service
* Web

![Alt text](https://g.gravizo.com/source/svg?https://raw.githubusercontent.com/halljon/offer-example/master/use-case-overview.plantuml)
