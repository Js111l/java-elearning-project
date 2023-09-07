### Java e-learning project

The API provides basic functionality of managing courses and lessons.

## Table of contents

* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info

API provides a solution for managing courses.
Courses are composed of individual lessons, and each lesson contains text article objects and has
its own title.
Similarly, text article objects have manageable components like title, subtitle or article content.
This API enables users to perform essential operations such as creating, retrieving, modifying, and
deleting courses and
their associated elements.
With its flexible features, the API provides an efficient
and adaptable solution for managing courses and their various components.

#### Paths

Copy and paste ```openapi.yml``` file from ```java/resources``` to https://editor-next.swagger.io/
in order to view the swagger documentation for this api.
Alternatively, after you successfully launched the project, you can access the path
/swagger-ui-doc.html (
e.g. http://localhost:8080/swagger-ui-doc.html) and see full swagger documentation.

## Technologies

* Java 17
* Spring Boot 3.1.0
* Spring Data JPA (Hibernate) 3.0.0
* Podman 4.4.4
* MySQL 8.0.33

##### Libraries:

* Guava
* Lombok

##### Testing:

* WebTestClient

## Setup

In order to set up the project, you would need to have established connection to the database.
Firstly, make sure you have Docker installed on your machine. Then
issue the ```docker-compose -f docker_compose.yml up``` command in this directory to set up proper
environment.
