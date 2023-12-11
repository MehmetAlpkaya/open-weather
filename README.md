# Open Weather Service Application

---


### The service provides an API
#### `WeatherAPI`

## Technologies

---
- Java 17
- Spring Boot 3.0
- Open API Documentation
- Spring Data JPA
- H2 In Memory Database
- Restful API
- Maven
- Docker
- Docker Compose
- Github Actions
- Prometheus
- Grafana


## Prerequisites

---
- Maven or Docker
---

## Docker Run
The application can be built and run by the `Docker` engine. The `Dockerfile` has multistage build, so you do not need to build and run separately.

Please follow the below directions in order to build and run the application with Docker Compose;

```sh
$ cd open-weather
$ docker-compose up -d
```

Docker compose creates 3 replicas (instances) of the application on port range 9595-9597

---
## Maven Run
To build and run the application with `Maven`, please follow the directions below;

```sh
$ cd open-weather
$ mvn clean install
$ mvn spring-boot:run
```

