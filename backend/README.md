# Books Library Backend

This is a practice project for the final of Enterprise Systems Integration Class.

The goal is to have the entire API navigable with HATEOAS. 

## Author

Brandon Autrey

## Description

This is a Book Library system.  This will list books in a library that are available or have been borrowed out to different people. 
It should be able to keep track of multiple copies of the same book.  The borrower will be tracked by whom the borrower is and when the book has been rented out.   


## Configuration

-   Install Java 14
-   Google Java Formatter plugin
-   IntelliJ IDE
-   Cool plugins:
    -   Atom Material Icons
    -   Material Theme UI

## Libraries:

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

## Database

- Local DB is H2
- Production DB on Heroku is Postgres

# Heroku

Deployed at `https://<create-app>.herokuapp.com/`

### Create Deployment 

* `heroku login` 
* `heroku create`
* `git push heroku master`
    * Pushes code to Heroku from master
* `heroku ps:scale web=1` 
* `heroku open`
    * Opens new tab in browser to show app

### Heroku useful commands

* `git push heroku <branch>:master`
    * Pushes branch/master to Heroku
* `heroku git:remote --app my-app-name`
    * Switch the remote Heroku is hooked too
* `heroku pg:psql postgresql-reticulated-84032 --app <create-app>`
    * Login to Postgres DB CLI
* `heroku info`
* `heroku config`
* `heroku logs --tail`
    * View only the tail of the logs on the server


# Domain Model

Book Entry is the book type. Book item is the actual book with serial code to identify it.  There can be multiple book items for a book type.
A borrower borrows a book item, which has a book type. 
When a borrower returns the book they can leave of review, consisting of a rate, text, and borrower information.

Books are listed shown by the book entry and they will return back a count of how many of available and taken.  This will show who has taken and when.


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
-   Status??

### Person

Fields:
-   Name
-   E-mail

### Borrow

Fields:
-   Status??
-   Person (OneToOne)
-   Book Item (OneToOne)

### Review

Fields:
-   Rating (1-5)
-   Review Text
-   Draft (Boolean)
-   Book Entry (ManyToOne)
-   Person (OneToOne)

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
    
-   ### `GET /api/book/item/:id`

    Retrieve a single book item by ID
    
-   ### `DELTETE /api/book/item/:id`   

    Deletes a single book item by ID.  API should be protected.

## Borrow

-   ### `POST /api/book/borrow`

    Borrow a book.  Needs to submit identifying information such as name of person.  
    The date will be populated automatically of the borrowed book.
    The borrower will borrow a book item as that represents the actual physical book. 

-   ### `POST /api/book/return`

    Returns a book to the library. 
    Will receive the option to submit a review.  Borrowers information is already populated in unpublished review.  Link to do review will be sent back with HATEOAS.

## Review

-   ### `POST /api/book/:id/review`

    Create review of the Book Entry without previously being borrowed.  The review is linked to the Book Entry.

-   ### `GET /api/book/:id/review`

    Retrieves all reviews for Book Entry

-   ### `PATCH /api/book/:id/review/:id`

    Create review of the Book Entry that was previously borrowed. After borrowing, an unpublished draft review is already created with borrower information.
    If the ID matches an unpublished review, then the review will be updated on that review.

# Future Work

