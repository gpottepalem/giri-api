# giri-api
[![Build Status](https://travis-ci.org/gpottepalem/giri-api.svg?branch=master)](https://travis-ci.org/gpottepalem/giri-api)

## Overview
Application development is finally moving towards simplicity and getting some REST. RESTful services have become a 
trend lately. For decades, application development has been known for putting complex solutions to simple problems. 
When any new idea hits the development world, it becomes a buzz so quickly and all kinds of trendy frameworks and 
complexities surround it suddenly. REST - an Architectural Pattern which is often considered simple is no 
exception to this trend. It is easy enough to take it to greater levels of complexities mainly because it is loosely 
defined style.

Grails is a super(ior) high-level framework built on the path of simplicity which further underpins many complexities of
the underlying frameworks. With it's continued principle of making development simple, it greatly enhanced it's REST 
support in version 3.x. 

## About this app
This is a simple Grails 3 application which demonstrates how easy and simple it is to develop a secured RESTful 
application which is complete by all means with even a well defined API documentation of end-points it provides. 

## The Domain Model
For demonstration, I chose my primary hobby Painting, an Artist's small artistic world as the domain model for this 
application. Like good developers, Artists do like simplicity. So, I kept this model very simple, simple enough to cover
all aspects of the features that I wanted to explore. The interests of an Artist's small world can be represented in a 
simple domain model as follows:
### Domain Objects

#### Artist
  * email
  * firstName
  * lastName
  * artWorks
#### ArtWork
  * type [Drawing, Painting, Sketch]
  * media [Pencil, Charcoal, Pen, BallpointPen, Watercolor, Oil, Acrylic]
  * surface [Paper, Canvas]
  * specification
  * forSale: yes/no
  * price (in US $)
#### Specification
  * width: 0..100 (in inches)
  * length: 0..100 (in inches)
  * framed: yes/no
### Relationships
  * An Artist can own many artWorks (one-to-many)
  * Each Artwork has a specification (one-to-one)
## Some basic requirements
  * Types of users: Admin, Artist
  * An Artist can register with an email and a password
  * Once registered, an Artist can create many artworks with specifications
  * Anybody can see list of Artists and artWorks
  * An Admin user can ONLY delete an Artist
  * An Artist can update his/her profile or artWorks
## Run the app
Clone the repository and run gradle task: `asciidoctor`

```
git clone https://github.com/gpottepalem/giri-api.git
cd giri-api
./gradlew asciidoctor
```

It runs all test-cases and generates api documentation under the app build directory: `giri-api/build/asciidoc`

View generated api doc for end-points:
```
open build/asciidoc/api-guide.html
```
## Handy curl commands for testing end-points
  * **Login**
    `curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d '{"username":"me","password":"password"}' http://localhost:8080/api/login`
    `curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d '{"username":"admin","password":"admin"}' http://localhost:8080/api/login`
  * **Validate token**
    `curl -i -H "Authorization: Bearer 664dkbafcuo4prsd02vocvlfvaok5nvl" http://localhost:8080/api/validate`
    `curl -i -H "X-Auth-Token: 664dkbafcuo4prsd02vocvlfvaok5nvl" http://localhost:8080/api/validate`
  * **Logout**
    `curl -i -H "Authorization: Bearer 664dkbafcuo4prsd02vocvlfvaok5nvl" http://localhost:8080/api/logout`
    `curl -i -H "X-Auth-Token: 664dkbafcuo4prsd02vocvlfvaok5nvl" http://localhost:8080/api/logout`
  * **Spring Boot Actuator Management end-points**
    `curl -i -H "X-Auth-Token: 664dkbafcuo4prsd02vocvlfvaok5nvl" http://localhost:8080/api/manamgement/info`

Changelog
---
#####1.0 Initial revision