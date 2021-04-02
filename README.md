# ANAGRAMS API REST

> API REST to verify If words are Anagrams and, If sentences share anagrams, eg:  
> 1. angela es conservadora
> 2. ellos alegan que ella es muy conversadora
> - "angela" and "alegan" are words with the same letters but in distinct order, are anagrams.
> - Same case with "conservadora" and "conversadora".

## Prerequisites

You will need the following services properly installed on your computer.

* [Git](http://git-scm.com/)
* [Gradle](https://gradle.org)
* [Maven](https://maven.apache.org/)
* [Docker](https://docs.docker.com/)

## Installation

This SpringBoot project use [Gradle](http://www.gradle.org), 
is an open-source build automation tool that is designed to be flexible enough to build almost any type of software.
If you prefer [install Gradle](http://www.gradle.org/installation) or use a [Gradle wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) inside this project.

* Execute: `git clone git@github.com:camilodiazj/anagrams.git`.
* Change into the new directory `cd anagrams`

## Build project

```bash
./gradlew clean build
```

## Run tests

```bash
./gradlew clean test
```

## Run
* This application runs over port 8080, so you should prefix endpoint with http://localhost:8080/

```bash
java -jar build/libs/anagrams-*.jar
```

## Swagger Documentation
* When application is running use the following link:
http://localhost:8080/swagger-ui.html
  * Is recommended use [POSTAM](https://www.postman.com/) to consume the Endpoints.
    
