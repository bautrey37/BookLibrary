# Book Library

[![Build Status](https://travis-ci.com/bautrey37/BookLibrary.svg?branch=master)](https://travis-ci.com/bautrey37/BookLibrary)
[![versionjava](https://img.shields.io/badge/jdk-13-brightgreen.svg?logo=java)](https://github.com/spring-projects/spring-boot)

#### Author: Brandon Autrey

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/made-with-crayons.svg)](https://forthebadge.com)

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

## Project setup

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

## Docker

Build docker container: `docker build . --tag library:latest`

Run Docker container: `docker run -d -p 8090:8090 --name library library`

View running Docker containers: `docker ps`

View logs: `docker logs <container id> --follow`

Stop Docker app: `docker stop <container id>`

Remove Docker app: `docker rm <container id>`