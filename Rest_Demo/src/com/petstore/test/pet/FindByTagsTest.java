package com.petstore.test.pet;
import com.petstore.data.ConstantVariables;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FindByTagsTest {

    @BeforeClass
    public static void setup() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConstantVariables.API_URL)
                .setPort(ConstantVariables.API_PORT)
                .setBasePath(ConstantVariables.API_PATH)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = requestSpec;
    }

    @Test
    void checkBodyContainsTag() {
        List<Entity> list = new ArrayList<>();
        list = given()
                .when()
                .get("/pet/findByTags?tags=tag1")
                .then()
                .extract().body().as(list.getClass());
        for (Object pet : list) {
            Assert.assertTrue(pet.toString().contains("tag1"));
        }
    }

    @Test
    void checkBodyContainsManyTags() {
        boolean flag = false;
        List<Entity> list = new ArrayList<>();
        list = given()
                .when()
                .get("/pet/findByTags?tags=tag1&tags=tag2")
                .then()
                .extract().body().as(list.getClass());

        for (Object pet : list) {
            if (pet.toString().contains("name=tag1")||pet.toString().contains("name=tag2")) {
                flag = true;
            }
            Assert.assertTrue(flag);
        }
    }

    @Test
    void checkWithOneIncorrectTag() {

        List<Entity> list = new ArrayList<>();
        list = given()
                .when()
                .get("/pet/findByTags?jkdljffg&tags=tag1")
                .then()
                .statusCode(200)
                .extract().body().as(list.getClass());

        for (Object pet : list) {
            Assert.assertTrue(pet.toString().contains("name=tag1"));
        }
    }

    @Test
    void checkIcorrectDataTags() {
        given()
                .expect()
                .statusCode(200)
                .when()
                .get("/pet/findByTags?tags=4--5'2")
                .then()
                .extract().body().jsonPath().toString().contains("[]");
    }

    @Test
    void checkTooLongTagsData() {
        String randString = randomAlphabetic(100);
        given()
                .expect()
                .statusCode(200)
                .when()
                .get("/pet/findByTags?tags=" + randString)
                .then()
                .extract().body().jsonPath().toString().contains("[]");
    }
}