package com.petstore.test.pet;
import com.petstore.data.ConstantVariables;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FindByStatusTest {

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String API_STATUS = "/pet/findByStatus?status=";

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
    void testContentType() {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/pet/findByStatus?status=available");
        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType, "application/json");
    }

    @Test
    void checkAvailableStatus() {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/pet/findByStatus?status=available");
        List<LinkedHashMap> jsonResponse = response.jsonPath().getList("$");
        for (int i = 0; i < jsonResponse.size(); i++) {
            Assert.assertEquals(jsonResponse.get(i).get(STATUS), "available");
        }
    }

    @Test
    void checkPendingStatus() {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/pet/findByStatus?status=pending");
        List<LinkedHashMap> jsonResponse = response.jsonPath().getList("$");
        for (int i = 0; i < jsonResponse.size(); i++) {
            Assert.assertEquals(jsonResponse.get(i).get(STATUS), "pending");
        }
    }

    @Test
    void checkSoldStatus() {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/pet/findByStatus?status=sold");
        List<LinkedHashMap> jsonResponse = response.jsonPath().getList("$");
        for (int i = 0; i < jsonResponse.size(); i++) {
            Assert.assertEquals(jsonResponse.get(i).get(STATUS), "sold");
        }
    }

    @Test
    void checkEmptyStatus() {
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, API_STATUS);

        Assert.assertEquals(response.getStatusCode(), 400);
        JsonPath jsonResponse = response.jsonPath();
        Assert.assertTrue(jsonResponse.getString(MESSAGE).contains("status value `` is not in the allowable values"));
    }

    @Test
    void checkIncorrectDataStatus() {
        String STATUSVALUE = "dfdgdhj";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, API_STATUS + STATUSVALUE);
        Assert.assertEquals(response.getStatusCode(), 400);

        JsonPath jsonResponse = response.jsonPath();
        Assert.assertTrue(jsonResponse.getString(MESSAGE).contains("status value `" + STATUSVALUE +
                "` is not in the allowable values"));
    }

    @Test
    void checkTooLongStatusdata() {
        String randString = randomAlphabetic(100);
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, API_STATUS + randString);
        Assert.assertEquals(response.getStatusCode(), 400);

        JsonPath jsonResponse = response.jsonPath();
        Assert.assertTrue(jsonResponse.getString(MESSAGE).contains("status value `" + randString +
                "` is not in the allowable values"));
    }
}
