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
* If you are in Windows, is recommended to use Git Bash to run the bash commands.

```bash
./gradlew clean build 
```

## Run tests

```bash
./gradlew clean test
```

## Run
- You need a Database to persist Data
- Before run application, follow the next steps:
> Deploy MariaDb:
> 1. Change into `cd docker-db`, in terminal use:
```bash
docker-compose up
```
In linux:
```bash
sudo docker-compose up
```

> If you already have [Mysql](https://www.mysql.com/) or [MariaDB](https://mariadb.org/) installed in
your computer, you can modify the DataBase port, db username and db password in file `application.properties`,
>  * Current port is `33020`
>  * Current username is `root`
>  * Current password is `root`
>  - Properties to update: 
>  - jdbc:mysql://localhost:`33020`/anagrams?createDatabaseIfNotExist=true 
>  - spring.datasource.username=`root`
>  - spring.datasource.password=`root`

> Located into `cd src/main/resources`

---
Once you have an active database, open another terminal, go to the root folder `cd anagrams`, and use:

```bash
./gradlew clean build
java -jar build/libs/anagrams-*.jar
```

* This application runs over port 8080, so you should prefix endpoints with http://localhost:8080/
## Swagger Documentation
* When application is running use the following link:
http://localhost:8080/swagger-ui.html

> Is recommended to use [POSTMAN](https://www.postman.com/) to consume the Endpoints.
> Postman Collection in `anagrams/postman_collection.json`  
