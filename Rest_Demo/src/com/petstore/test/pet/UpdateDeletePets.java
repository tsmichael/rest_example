package com.petstore.test.pet;

import com.petstore.data.ConstantVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

public class UpdateDeletePets {


    @Test
    void checkPetById() {
        Response response = RestAssured.given()
                .baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .pathParam("petId", 10)
                .contentType(ContentType.JSON)
                .when().get("/pet/{petId}")
                .then()
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void updatePetData() {
        Response response = RestAssured.given()
                .baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .contentType(ContentType.JSON)
                .body(new File("updatePet.json"))
                .when().put("/pet")
                .then()
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void updatePetDataWithNonExistingId() {
        Response response = RestAssured.given()
                .baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .contentType(ContentType.JSON)
                .body(new File("updatePet2.json"))
                .when().put("/pet")
                .then().statusCode(404)
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    void checkPetByNonExistingId() {
        Response response = RestAssured.given()
                .baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .pathParam("petId", 856)
                .contentType(ContentType.JSON)
                .when().get("/pet/{petId}")
                .then()
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void delete() {

        Response response = RestAssured.given()
                .baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .pathParam("petId", "856")
                .queryParam("api_key", "api_key")
                .when().delete("/pet/{petId}")
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.asString());
    }
}