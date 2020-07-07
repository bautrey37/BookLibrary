# Book Library

[![Build Status](https://travis-ci.com/bautrey37/BookLibrary.svg?branch=master)](https://travis-ci.com/bautrey37/BookLibrary)
![CI](https://github.com/bautrey37/BookLibrary/workflows/CI/badge.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/bautrey37/BookLibrary/badge.svg)](https://coveralls.io/github/bautrey37/BookLibrary)
[![Deployed on Heroku](https://img.shields.io/badge/heroku-deployed-blueviolet.svg?logo=heroku)](https://tartu-library.herokuapp.com/)
[![License](http://img.shields.io/:license-mit-blue.svg)](https://github.com/bautrey37/BookLibrary/blob/master/LICENSE)

#### Author: Brandon Autrey

## Project setup

[Backend README](backend/README.md)

[Frontend README](frontend/README.md)

```
library
├─┬ backend     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```



## Motivation

This is a practice project to use the skills I learned in Enterprise Systems Integration Master class and to expand upon what I've learned.
This project also borrows ideas from this spring-boot-vuejs repository. https://github.com/jonashackt/spring-boot-vuejs

## Goal

To write a library book rental application for a small group of people, such as an office.  This is inspired from real-life as a way to manage technical books meant to share with other co-workers.
The books are donated to the library but yet still have the owner attached to the book. The owner is the person who bought the book. 
Other members can borrow a book, which is then put into the system for the other members to see. When a book is returned, the library gets updated that the book is available.

### Additional features

1.  A member can write a review for a book. When the book is returned, the member that borrowed the book is prompted to write a review and will be marked as verified, as in they read the book.
2.  A member can be put on a watch list for a book if the book is not available. When the book is returned, all the member who are watching the book are notified by email that the book is now available.



# Deployment

Deployed at Heroku at: `https://tartu-library.herokuapp.com/`

### Create Deployment 

- `heroku login` 
- `heroku create`
- `git push heroku master`
    - Pushes code to Heroku from master
- `heroku ps:scale web=1` 
- `heroku open`
    - Open new tab in the browser to show app

### Heroku useful commands

- `git push heroku <branch>:master`
    - Pushes branch/master to Heroku
- `heroku git:remote --app my-app-name`
    - Switch the remote Heroku is hooked too
- `heroku pg:psql postgresql-globular-10076 --app tartu-library`
    - Login to Postgres DB CLI
- `heroku info`
- `heroku config`
    - Set config variables
    - `heroku config:set _JAVA_OPTIONS="--enable-preview -XX:+ShowCodeDetailsInExceptionMessages"`
        - Sets the java preview features to be ran with app
- `heroku logs --tail`
    - View only the tail of the logs on the server
    
### Postgres

Useful commands:
[Link](http://www.emblocsoft.com/About/PG/Useful-PostgreSQL-commands)
-   `\dt` - show all tables in the current scheme
    -   `\dt+` - show table sizes
-   `\q` - quit psql
-   `heroku pg:psql postgresql-globular-10076 --app tartu-library < src/main/resources/seed.sql`
    -   run seed.sql file on the postgres database  


## Docker

Build docker container: `docker build . --tag library:latest`

Run Docker container: `docker run -d -p 8090:8090 --name library library`

View running Docker containers: `docker ps`

View logs: `docker logs <container id> --follow`

Stop Docker app: `docker stop <container id>`

Remove Docker app: `docker rm <container id>`
