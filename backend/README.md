# Books Library Backend

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)

## Goal

To have the entire API navigable with HATEOAS. A client does not need to know the API structure. From the first link, they will be able to navigate throughout the whole API with the links provided back by the server.

## Description

This is a Book Library system.  This will list books in a library that are available or have been borrowed out to different people. 
It should be able to keep track of multiple copies of the same book.  The borrower will be tracked by whom the borrower is and when the book has been rented out.   


## Configuration

-   [![versionjava](https://img.shields.io/badge/jdk-14-brightgreen.svg?logo=java)](https://github.com/spring-projects/spring-boot)
-   Google Java Formatter plugin
-   IntelliJ IDE
-   Cool plugins:
    -   Atom Material Icons
    -   Material Theme UI

## Libraries:

-   [![versionspringboot](https://img.shields.io/badge/dynamic/xml?color=brightgreen&url=https://raw.githubusercontent.com/bautrey37/BookLibrary/master/pom.xml&query=%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27parent%27%5D%2F%2A%5Blocal-name%28%29%3D%27version%27%5D&label=springboot)](https://github.com/spring-projects/spring-boot)
    -   cloud-connectors
    -   data-jpa
    -   HATEOAS
    -   web
    -   devtools
    -   test
-   postgresql
-   h2
-   lombok

## Database

- Local DB is H2
- Production DB on Heroku is Postgres

# Domain Model

Book Entry is the book type. Book item is the actual book with serial code to identify it.  There can be multiple book items for a book type.
A borrower borrows a book item, which has a book type. 
When a borrower returns the book they can leave of review, consisting of a rate, text, and borrower information.

![domain model](https://app.lucidchart.com/publicSegments/view/5fea11c4-a3c9-4cc2-b6b0-ec44ce5f6193/image.png)

([edit](https://app.lucidchart.com/invitations/accept/caa84f8a-745d-4361-8ca2-80775415196a))

### Book Entry

Fields:
-   ISBN (International Standard Book Number)
-   Book Name
-   Author
-   Publish Date

If only ISBN is posted, then the other book information will be pulled. If no ISBN is posted, then the other book information is required.

### Book Item

Fields:
-   Owner (OneToOne) as Person
-   Book Entry (ManyToOne)
-   Book Status

### Book Status Enum

-   States: AVAILABLE, BORROWED

### Borrow Log

Fields:
-   Book Item (One to One)
-   Borrower (One to One) as Person
-   Borrow date
-   Return Date

### Person

Fields:
-   Name
-   E-mail

### Review

Fields:
-   Rating (1-5)
-   Review Text
-   Draft (Boolean)
-   Book Entry (ManyToOne)
-   Person (OneToOne)

## Sequence Diagram

![createBook diagram](https://app.lucidchart.com/publicSegments/view/82312fe8-f152-4964-b5f7-3fcb3f954364/image.png)

([edit](https://app.lucidchart.com/invitations/accept/0d4bae44-7fee-4c91-b27a-cf4a7934029d))

# API

## API Response Structure

Successful request is responded with HTTP status 2XX and response value in body (JSON). Value can be any valid JSON data type.

```json
{
  "id": 1,
  "description": "book stuff"
}
``` 

Failed request is responded with appropriate HTTP status code 4XX or 5XX and error object in body (JSON).
Suberror field is either null or populated with errors associated with the main error such as validation of fields in request body.
```json
{
  "apierror": {
    "message": "why something failed",
    "status": "NOT_FOUND",
    "suberror": null
  }
}
```

## Book Entry

-   ### `GET /api/book`

    Retrieve all book entries. 
    Will include how many book items are available, borrowed, and information on who has borrowed the book and when.

-   ### `POST /api/book`

    Add a new book entry to the library. Will automatically create a book item to correspond.  If book entry aleady exists, just create new book item. 

-   ### `GET /api/book/:id`

    Retrieve a single book entry by ID.
    
-   ### `PATCH /api/book/:id`

    Update information on Book Entry. API should be protected.
    
-   ### `DELETE /api/book/:id`

    Deletes Book Entry by ID and all associated Book Items.  API should be protected.
    
## Book Item
    
-   ### `GET /api/book/item`

    Retrieves all book items
    
-   ### `GET /api/book/item/:id`

    Retrieve a single book item by ID
    
-   ### `DELTETE /api/book/item/:id`   

    Deletes a single book item by ID.  API should be protected.
    
-   ### `POST /api/book/item/:id/borrow`

    Borrow a book.  Needs to submit identifying information such as name of person.  
    The date will be populated automatically of the borrowed book.
    The borrower will borrow a book item as that represents the actual physical book.
    Creates a Borrow Log entry. 

-   ### `POST /api/book/item/:id/return`

    Returns a book to the library. 
    Will receive the option to submit a review.  Borrowers information is already populated in unpublished review.  Link to do review will be sent back with HATEOAS.
    Creates a Borrow Log entry
    
-   ### `GET /api/book/item/:id/logs`

    Retrieves all the Borrow Log entries for this Book Item which are creating by borrowing and returning books. 

    
## Person

-   ### `GET /api/person`

    Retrieve all person;s

-   ### `POST /api/person`

    Create a new person.
    
-   ### `GET /api/person/:id`

    Retrieves person by Id. 


## Review

-   ### `POST /api/book/:id/review`

    Create review of the Book Entry without previously being borrowed.  The review is linked to the Book Entry.

-   ### `GET /api/book/:id/review`

    Retrieves all reviews for Book Entry

-   ### `PATCH /api/book/:id/review/:id`

    Create review of the Book Entry that was previously borrowed. After borrowing, an unpublished draft review is already created with borrower information.
    If the ID matches an unpublished review, then the review will be updated on that review.

