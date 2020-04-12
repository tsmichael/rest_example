package com.petstore.test.pet;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

public class UpdateDeletePets {


    @Test
    void checkPetById() {
        Response response = RestAssured.given()
                .baseUri("http://192.168.99.100:8080")
                .basePath("/api/v3/pet")
                .pathParam("petId", 10)
                .contentType(ContentType.JSON)
                .when().get("/{petId}")
                .then()
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void updatePetData() {
        Response response = RestAssured.given()
                .baseUri("http://192.168.99.100:8080")
                .basePath("/api/v3/pet")
                .contentType(ContentType.JSON)
                .body(new File("updatePet.json"))
                .when().put()
                .then()
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void updatePetDataWithNonExistingId() {
        Response response = RestAssured.given()
                .baseUri("http://192.168.99.100:8080")
                .basePath("/api/v3/pet")
                .contentType(ContentType.JSON)
                .body(new File("updatePet2.json"))
                .when().put()
                .then().statusCode(404)
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    void checkPetByNonExistingId() {
        Response response = RestAssured.given()
                .baseUri("http://192.168.99.100:8080")
                .basePath("/api/v3/pet")
                .pathParam("petId", 856)
                .contentType(ContentType.JSON)
                .when().get("/{petId}")
                .then()
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void delete() {

        Response response = RestAssured.given()
                .baseUri("http://192.168.99.100:8080/")
                .basePath("api/v3/pet/")
                .pathParam("petId", "856")
                .queryParam("api_key", "api_key")
                .when().delete("{petId}")
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.asString());
    }
}