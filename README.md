# giri-api
## Domain Model
I chose my primary hobby Painting, an Artist's small world as the domain model for this application. Like good developers, Artists also like simplicity. So, I kept this model very simple, simple enough to cover all aspects of the features that I was exploring.
The interests of an Artist's small world can be represented in a simple domain model as follows:
### Domain Objects

#### Artist
  * email
  * firstname
  * lastname
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
  * Any Artist can register with an email and a password
  * Once registered, an Artist can create many artworks with specifications
  * Anybody can see list of Artists and artWorks

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
