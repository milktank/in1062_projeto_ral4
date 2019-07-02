# Sock Shop with CQRS : A Microservice Demo Application to IN1062 Class Project

The application is the user-facing part of an online shop that sells socks. It is intended to aid the demonstration and testing of microservice and cloud native technologies.

It is built using [Spring Boot](http://projects.spring.io/spring-boot/), [Go kit](http://gokit.io) and [Node.js](https://nodejs.org/) and is packaged in Docker containers.

You can read more about the [application design](./internal-docs/design.md).

## Deployment Platforms

The [deploy folder](./deploy/) contains scripts and instructions to provision the application onto your docker platform. 

## To Deploy It

git clone https://github.com/microservices-demo/microservices-demo
cd microservices-demo

git clone https://github.com/milktank/in1062_projeto_ral4

cd orders <br />
mvn -DskipTests package <br />
mv target/orders.jar docker/orders/ <br />
docker build -t orders:1.0 docker/orders/ <br />

cd sqlrepo <br />
mvn -DskipTests package <br />
mv target/sqlrepo.jar docker/sqlrepo/ <br />
docker build -t sqlrepo:1.0 docker/sqlrepo/ <br />

cd queue-db <br />
mvn -DskipTests package <br />
mv target/queue-db.jar docker/queue-db/ <br />
docker build -t queue-db:1.0 docker/queue-db/ <br />

Move docker-compose.yml to [microservices-demo_path]/deploy/docker-compose/

## 
