package com.petstore.test.user;

import com.petstore.data.ConstantVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;

import static org.testng.AssertJUnit.assertNotNull;



public class ApiPostAndGetTest {



    @BeforeClass
    public void setUp(){
        RestAssured.given().baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .accept(ContentType.JSON)
                .queryParam("username", "theUser")
                .queryParam("password", "12345")
                .when().get("/user/login");
    }

    @AfterClass
    public void tearDown(){
        RestAssured.given().baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .accept(ContentType.JSON)
                .when().get("/user/logout");
    }

    @Test(priority = 1)
    public void checkCreateUser() {

        Response response = RestAssured.given().baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .contentType(ContentType.JSON)
                .body(new File("data.json"))
                .when().post("/user")
                .then()
                .extract().response();
        String statusLine =response.statusLine().substring(13,15);
        String jsonBody = response.getBody().asString();

        try {
            assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(statusLine,"OK");
    }

    @Test(priority = 2)
    public void checkGetUserByUserName() {

        Response response = RestAssured.given().baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .accept(ContentType.JSON)
                .pathParam("username", "Jones")
                .when().get("/user/{username}")
                .then()
                .extract().response();
        String statusLine =response.statusLine().substring(13,15);
        String jsonBody = response.getBody().asString();
        try {
            assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(statusLine,"OK");
    }
}
