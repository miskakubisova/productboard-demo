# Productboard demo app for interview purposes
This is a small demo app I created for interview purposes. Its functionality is very simple. 
It has one REST endpoint `/language-stats` which stores actual language statistics of Productboard GitHub repositories.
This endpoint was not the goal of the assignment, but I created it to help me test the functionality. The main purpose of 
the assignment was to create application that once a day stores the language statistics of Productboard GitHub public repositories.
To do that I created a [cron job](src/main/java/com/productboard/productboarddemo/cron/CronJob.java) that runs once a day at 6am and 
stores data into DB table.

## Technologies used
Of course this is a Java REST application built on Spring Boot. I have very small experiences with Kotlin, that is why I decided to choose Java.
For my comfort I used Lombok to help me code all the getters/setters of each class. I also used one library StreamEx, which I like to say is better
version of Java streams. Database runs in Docker.

## Database
To store tha data I used Postgres database. It runs in Docker. I was not sure if the goal is to always overwrite data in database or
to create historical overview, so I decided to create both cases. Therefore, I have two tables `language_stats` and `language_stats_history`.
The first mentioned is used for the case of overwriting the data each time I load them from GitHub API. the second one is used for storing
historical overview. I also split the functionality in such way that the REST endpoint `/language-stats` is storing data to the `language_stats`
table and the [cron job](src/main/java/com/productboard/productboarddemo/cron/CronJob.java) stores data to the `language_stats_history`. 
To access the data and tables from code I used Spring Data JPA and Hibernate.

## Tests
Testing is probably my weakest point, and you probably can see it :). I made a couple of test that I thought should cover the basic functionality of
this application, but to be honest I spend the least time on testing.

## How to run application
1. Create executable -> run `mvn clean package` from the root
2. Build docker compose -> run `docker-compose up` from the root
3. Now the DB should be up and running
4. Just run the app by running the configuration file from Intellij IDEA
5. Now you can either wait until 6am :) or call the REST endpoint to see the functionality.


Hope you enjoy reviewing this assignment.
Author: Kubisova Michaela.