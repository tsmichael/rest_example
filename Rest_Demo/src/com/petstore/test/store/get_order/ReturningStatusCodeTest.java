package com.petstore.test.store.get_order;

import com.petstore.data.ConstantVariables;
import com.petstore.data.OrderRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ReturningStatusCodeTest {

    @BeforeClass
    public void setRequestSpec(){
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConstantVariables.API_URL)
                .setPort(ConstantVariables.API_PORT)
                .setBasePath(ConstantVariables.API_PATH + ConstantVariables.STORE_PATH)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        RestAssured.requestSpecification = requestSpec;

        given().spec(requestSpecification).body(OrderRepository.validOrder()).
        when().post().
        then().statusCode(200);
    }

    @AfterMethod
    public void clean(){
        given()
                .spec(requestSpecification)
                .pathParam("id", 1).
        when()
                .delete(ConstantVariables.STORE_ID).
        then()
                .statusCode(200);
    }

    @Test
    public void whenSendingFindOrderRequest_ShouldReturn200(){
        given().
                spec(requestSpecification).
                pathParam("id", 1).
        when().
                get(ConstantVariables.STORE_ID).
        then().
                statusCode(200);
    }

    @Test
    public void whenSendingFindOrderRequest_ShouldReturn404() {
        given()
                .spec(requestSpecification)
                .pathParam("id", 10000).
        when()
                .get(ConstantVariables.STORE_ID).
        then()
                .statusCode(404);
    }

    @Test
    public void whenSendingFindOrderRequest_ShouldReturn400() {
        given()
                .spec(requestSpecification).
        when()
                .get("/id").
        then()
                .statusCode(400);
    }

    @Test
    public void whenSendingFindOrderRequest_ShouldReturn405() {
        given()
                .spec(requestSpecification).
        when()
                .get("/").
        then()
                .statusCode(405);
    }
}
