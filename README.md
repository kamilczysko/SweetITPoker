## Planning poker application second edition
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This is my version of planning poker. This application allows you to customize cards with your time unit (hours, days), set roles to participants and when voting is finished there is convinient summary for every role.
Communication between frontend and backend is made using with WebSocket and STOMP. And also UI is very pleasant ;)

## Technologies
Project is created with:
* Java 17
* Vue 3
* npm 9.2.0


## Setup
This application is moloith application. Maven builds backend and triggers npm to build frontend and copy it to resources of Spring.
To run this project, clone this repository and run commands as follows:
```
$ cd planning-poker/backend
$ mvn clean install
$ cd target
$ java -jar itpoker-0.0.1-SNAPSHOT.jar
```
