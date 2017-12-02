package com.giri

import geb.spock.GebSpec
import grails.plugins.rest.client.RestBuilder
import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import groovy.json.JsonSlurper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.junit.Rule
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation

import static org.springframework.http.HttpStatus.*
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration

@Integration
@Rollback
class ApiDocumentationArtistSpec extends GebSpec {

    static final String LOGIN_ENDPOINT = '/api/login'
    static final String ARTISTS_ENDPOINT = '/api/artists'

    @Value('${local.server.port}')
    protected int port

    @Rule
    protected JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation('build/docs/generated-snippets')

    private RequestSpecification documentationSpec

    def setupSpec() {
    }

    def setup() {
        //set documentation specification
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(this.restDocumentation))
                .build()
    }

    /**
     * Helper method, authenticates the given user and returns the security token.
     *
     * @param username the user id
     * @param password the password
     * @return security token once successfully authenticated
     */
    protected String authenticateUser(String username, String password) {
        String authResponse = RestAssured.given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(""" {"username" : "$username", "password" : "$password"} """)
            .when()
            .port(this.port)
            .post(LOGIN_ENDPOINT)
            .body()
            .asString()
        return new JsonSlurper().parseText(authResponse).'access_token'
    }

    void "Test and document list Artists request (GET request, index action) to end-point: /api/artists"() {
        given: "An Artist is created by Admin in the system"
        String accessToken = authenticateUser('admin', 'admin')
        RestAssured.given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-Auth-Token", "${accessToken}")
            .body("""{ "firstName" : "Giridhar", "lastName" : "Pottepalem" }""")
            .when()
            .port(this.port)
            .post(ARTISTS_ENDPOINT)
            .then().assertThat().statusCode(HttpStatus.CREATED.value())

        and: "request specification for documenting list Artists API"
        RequestSpecification requestSpecification = RestAssured.given(this.documentationSpec)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .filter(
                RestAssuredRestDocumentation.document(
                    'artists-list-example',
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath('[]').type(JsonFieldType.ARRAY).description('Artists'),
                        PayloadDocumentation.fieldWithPath('[].id').type(JsonFieldType.STRING).description('Artist id'),
                        PayloadDocumentation.fieldWithPath('[].firstName').type(JsonFieldType.STRING).description('Artist first name'),
                        PayloadDocumentation.fieldWithPath('[].lastName').type(JsonFieldType.STRING).description('Artist last name'),
                        PayloadDocumentation.fieldWithPath('[].dateCreated').type(JsonFieldType.STRING).description("Date Created (format:MM/dd/yyyy)"),
                        PayloadDocumentation.fieldWithPath('[].lastUpdated').type(JsonFieldType.STRING).description("Last Updated (format:MM/dd/yyyy)")
                    )
                )
             )
        println "port:${port}"

        when: "get request is made to end-point"
        def response = requestSpecification
            .when()
            .port(this.port)
            .get(ARTISTS_ENDPOINT)

        then: "status is OK"
        response.then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
    }

    void "Test and document show Artist request (GET request, show action) to end-point: /api/artists"() {
        given: "Pick an artist to show"
        Artist artist = Artist.first()

        and: "user logs in by a POST request to end-point: /api/login"
        String accessToken = authenticateUser('me', 'password')

        and: "documentation specification for showing an Artist"
        RequestSpecification requestSpecification = RestAssured.given(this.documentationSpec)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .filter(
                RestAssuredRestDocumentation.document(
                    'artists-retrieve-specific-example',
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath('id').type(JsonFieldType.STRING).description('Artist id'),
                        PayloadDocumentation.fieldWithPath('firstName').type(JsonFieldType.STRING).description('Artist first name'),
                        PayloadDocumentation.fieldWithPath('lastName').type(JsonFieldType.STRING).description('Artist last name'),
                        PayloadDocumentation.fieldWithPath('dateCreated').type(JsonFieldType.STRING).description("Date Created (format:MM/dd/yyyy)"),
                        PayloadDocumentation.fieldWithPath('lastUpdated').type(JsonFieldType.STRING).description("Last Updated Date (format:MM/dd/yyyy)")
                    )
                )
            )

        when: "GET request is sent"
        def response = requestSpecification
            .header("X-Auth-Token", "${accessToken}")
            .when()
            .port(this.port)
            .get("${ARTISTS_ENDPOINT}/${artist.id}")

        def responseJson = new JsonSlurper().parseText(response.body().asString())

        then: "The response is correct"
        response.then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())

        and: "response contains the id of Artist asked for"
        responseJson.id
    }

    void "Test and document create Artist request (POST request, save action) to end-point: /api/artists"() {
        given: "current number of Artists"
        int nArtists = Artist.count()

        and: "admin logs in by a POST request to end-point: /api/login"
        String accessToken = authenticateUser('admin', 'admin')

        and: "documentation specification for creating an Artist"
        RequestSpecification requestSpecification = RestAssured.given(this.documentationSpec)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(
            RestAssuredRestDocumentation.document(
                'artists-create-example',
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath('firstName').description('Artist first name'),
                    PayloadDocumentation.fieldWithPath('lastName').description('Artist last name')
                ),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath('id').type(JsonFieldType.STRING).description('Artist id'),
                    PayloadDocumentation.fieldWithPath('firstName').type(JsonFieldType.STRING).description('Artist first name'),
                    PayloadDocumentation.fieldWithPath('lastName').type(JsonFieldType.STRING).description('Artist last name'),
                    PayloadDocumentation.fieldWithPath('dateCreated').type(JsonFieldType.STRING).description("Date Created (format:MM/dd/yyyy)"),
                    PayloadDocumentation.fieldWithPath('lastUpdated').type(JsonFieldType.STRING).description("Last Updated Date (format:MM/dd/yyyy)")
                )
            )
        )

        when: "POST request is sent with valid data"
        def response = requestSpecification
            .header("X-Auth-Token", "${accessToken}")
            .body("""{ "firstName" : "Bhuvan", "lastName" : "Pottepalem" }""")
            .when()
            .port(this.port)
            .post(ARTISTS_ENDPOINT)
        def responseJson = new JsonSlurper().parseText(response.body().asString())

        then: "The response is correct"
        response.then()
            .assertThat()
            .statusCode(HttpStatus.CREATED.value())

        and: "response contains the id of Artist created"
        responseJson.id

        and: "Number of Artists in the system goes up by one"
        Artist.count() == nArtists + 1

        //ERROR tests
//        when: "The save action is executed with no content"
//        def response = requestSpecification
//            .body('')
//            .when()
//            .port(port)
//            .post(ARTISTS_ENDPOINT)
//
//        then:"The response is correct"
//        response.then()
//            .assertThat()
//            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
//
//        when: "The save action is executed with invalid data"
//        response = requestSpecification
//            .body('{ "firstName": "Giri", "lastName": "Potte" ')
//            .when()
//            .port(port)
//            .post(ARTISTS_ENDPOINT)
//
//        then: "The response is correct"
//        response.then()
//            .assertThat()
//            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
    }

    void "Test and document update Artist request (PUT request, update action) to end-point: /api/artists"() {
        given: "Pick an artist to update"
        Artist artist = Artist.first()

        and: "documentation specification for updating an Artist"
        RequestSpecification requestSpecification = RestAssured.given(this.documentationSpec)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(
            RestAssuredRestDocumentation.document(
                'artists-update-example',
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath('lastName').description('Artist last name')
                ),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath('id').type(JsonFieldType.STRING).description('Artist id'),
                    PayloadDocumentation.fieldWithPath('firstName').type(JsonFieldType.STRING).description('Artist first name'),
                    PayloadDocumentation.fieldWithPath('lastName').type(JsonFieldType.STRING).description('Artist last name'),
                    PayloadDocumentation.fieldWithPath('dateCreated').type(JsonFieldType.STRING).description("Date Created (format:MM/dd/yyyy)"),
                    PayloadDocumentation.fieldWithPath('lastUpdated').type(JsonFieldType.STRING).description("Last Updated Date (format:MM/dd/yyyy)")
                )
            )
        )

        when: "user logs in by a POST request to end-point: /api/login"
        String accessToken = authenticateUser('me', 'password')

        and: "PUT request is sent with valid data"
        def response = requestSpecification
            .header("X-Auth-Token", "${accessToken}")
            .body("""{ "lastName" : "${artist.lastName}(updated)" }""")
            .when()
            .port(this.port)
            .put("${ARTISTS_ENDPOINT}/${artist.id}")
        def responseJson = new JsonSlurper().parseText(response.body().asString())

        then: "The response is correct"
        response.then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())

        and: "response contains the Artist updated"
        responseJson.id == artist.id.toString()
        responseJson.lastName == "${artist.lastName}(updated)"
    }

    void "Test and document delete Artist request (DELETE request, delete action) to end-point: /api/artists"() {
        given: "current number of Artists"
        int nArtists = Artist.count()

        and: "Pick an artist to delete"
        Artist artist = Artist.first()

        and: "admin logs in by a POST request to end-point: /api/login"
        String accessToken = authenticateUser('admin', 'admin')

        and: "documentation specification for deleting an Artist"
        RequestSpecification requestSpecification = RestAssured.given(this.documentationSpec)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .filter( RestAssuredRestDocumentation.document('artists-delete-example') )

        when: "DELETE request is sent"
        def response = requestSpecification
            .header("X-Auth-Token", "${accessToken}")
            .when()
            .port(this.port)
            .delete("${ARTISTS_ENDPOINT}/${artist.id}")

        then: "The response is correct"
        response.then()
            .assertThat()
            .statusCode(HttpStatus.NO_CONTENT.value())

        and: "Number of Artists in the system goes down by one"
        Artist.count() == nArtists - 1

        and: "Deleted Artist is not there anymore in the system"
        !Artist.exists(artist.id)
    }

}