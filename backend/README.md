# Books Library Backend

This is a practice project for the final of Enterprise Systems Integration Class

By: Brandon Autrey

## Description

This is a Book Library system.  This will list books in a library that are available or have been borrowed out to different people. 
It should be able to keep track of multiple copies of the same book.  The borrower will be tracked by whom the borrower is and when the book has been rented out.   


## Configuration

-   Install Java 14
-   Use Google Java Formatter plugin
-   Use IntelliJ IDE
-   Cool plugins:
    -   Atom Material Icons
    -   Material Theme UI
    

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

Book Entry is the book type. Book item is the actual book with serial code to identify it.  There can be multiple book items for a book type.
A borrower borrows a book item, which has a book type. 
When a borrower returns the book they can leave of review, consisting of a rate, text, and borrower information.

Books are listed shown by the book entry and they will return back a count of how many of available and taken.  This will show who has taken and when.


### Book Entry

### Book Item

### Borrower

Fields:
-   Name

### Review

# API

## Books

-   ### `GET /api/book`

    Retrieve all book entries. 
    Will include how many book items are available, borrowed, and information on who has borrowed the book and when.

-   ### `POST /api/book`

    Add a new book entry to the library. Will automatically create a book item to correspond.  If book entry aleady exists, just create new book item. 
    
-   ### `DELETE /api/book`

    Deletes Book Entry and all associated Book Items.  API should be protected.
    
-   ### `GET /api/book/:id`

    Retrieve a single book entry by ID.
    
-   ### `GET /api/book/item/:id`

    Retrieve a single book item by ID
    
-   ### `DELTETE /api/book/item/:id`   

    Deletes a single book item by ID.  API should be protected.
    
-   ### `POST /api/book/borrow`

    Borrow a book.  Needs to submit identifying information such as name of person.  
    The date will be populated automatically of the borrowed book.
    The borrower will borrow a book item as that represents the actual physical book. 

-   ### `POST /api/book/return`

    Returns a book to the library. 
    Will receive the option to submit a review.  Borrowers information is already populated in unpublished review.  Link to do review will be sent back with HATEOAS.

-   ### `POST /api/book/review`

    Create review of the Book Entry without previously being borrowed.  The review is linked to the Book Entry.
    
-   ### `PATCH /api/book/review/:id`

    Create review of the Book Entry that was previously borrowed. After borrowing, an unpublished draft review is already created with borrower information.
    If the ID matches an unpublished review, then the review will be updated on that review.

# Future Work

