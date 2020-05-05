# Books Library Backend

This is a practice project for the final of Enterprise Systems Integration Class

By: Brandon Autrey

## Description

This is a Book Library system.  This will list books in a library that are available or have been borrowed out to different people. 
It should be able to keep track of multiple copies of the same book.  The borrower will be tracked by whom the borrower is and when the book has been rented out.   


## Configuration

-   Install Java 14
-   Use Google Java Formatter plugin

### Libraries:

-   Spring Boot 2.2.7
    -   cloud-connectors
    -   data-jpa
    -   HATEOAS
    -   web
    -   devtools
    -   test
-   postgresql
-   h2
-   lombok

# Domain Model

### Book Entry

### Book Item

### Borrower

# API

## Books

-   `GET /api/books`

    Retrieve all books
    
-   `GET /api/books/:id`

    Retrieve a single book by ID.

# Future Work

