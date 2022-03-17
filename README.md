# Grocery Service Application
Deploy on Heroku Cloud platform: https://cs6310gp54.herokuapp.com/

## Introduction

This repository contains the source codes for the Grocery Service Application we developed. This application is web-based and linked to database through PostgreSQL. Users are able to access to different functions within the application based on the roles signed. And the manager is capable of monitoring of the system and actions through some architectural improvements in this application.    

## Quickstart
We can simply run `make && make up` to start our service. For those that do not have `make` installed, we can effectively do the same thing by running the following:
```
docker build -t gatech/backend -f ./images/Dockerfile.backend ./backend
docker build -t gatech/frontend -f ./images/Dockerfile.frontend ./frontend
docker-compose -p gatech -f docker-compose.yml up -d
```
The first two commands are building the backend and frontend images defined by our dockerfiles, and the third is using docker-compose to deploy the service locally.
To break down the first command we can read it as "build an imageusing the `./images/Dockerfile.backend` file from the `./backend` directory and tag it as `gatech/backend`"
To break down the third command, we can read it as "deploy a service called `gatech` as defined in file `docker-compose.yml`

### docker-compose.yml
This file is well commented for additional details, and we recommend reading over this file to understand what is happening.

### Frontend
The front end service defines a single html file that calls our backend service and displays the delivery app. Our web service is deployed on port 8080
You should be able to navigate to [http://localhost:8080](http://localhost:8080) to view the page during design.

### Backend
Our backend is a simple application controller that creates the services, saves the information to the database, and returns it as a response.
This service (as defined in our docker-compose file) is exposed on port 8080.

### Database
The database is postgres 9.6.12 for Docker.
