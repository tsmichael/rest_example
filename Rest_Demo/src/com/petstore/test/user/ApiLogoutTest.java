package com.petstore.test.user;

import com.petstore.data.ConstantVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiLogoutTest {



    @Test(priority = 1)
    public void checkLogout() {

        Response response = RestAssured.given().baseUri(ConstantVariables.API_URL)
                .port(ConstantVariables.API_PORT)
                .basePath(ConstantVariables.API_PATH)
                .accept(ContentType.JSON)
                .when().get("/user/logout")
                .then()
                .extract().response();
        String statusLine =response.statusLine().substring(13,15);
        String jsonBody = response.getBody().asString();
        try {
            Assert.assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(statusLine,"OK");
    }
}
