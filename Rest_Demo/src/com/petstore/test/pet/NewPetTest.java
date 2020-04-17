package com.petstore.test.pet;
import com.petstore.data.ConstantVariables;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.ArrayList;
import static io.restassured.RestAssured.requestSpecification;

public class NewPetTest {

    private static final String CATEGORY = "category";
    private static final String STATUS = "status";
    private static final String URLS = "photoUrls";

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

    @AfterMethod
    public void tearDown(){
        RestAssured
                .given()
                .header("Content-Type","application/json")
                .delete("/pet/1");
    }

    @Test(priority = 0)
    void smokeAddPet() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/humsterphoto");

        pet.put("id", 1);
        pet.put("name", "Mickeyy");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 1)
    void addEmptyPet() {
        JSONObject pet = new JSONObject();
        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 500);
    }

    @Test(priority = 1)
    void addPetWithoutID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 2);
        category.put("name", "Cats");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 3);
        tag1.put("name", "black");
        tags.add(tag1);

        photoUrls.add("http://cat.ua/catphoto");

        pet.put("name", "Murka");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "sold");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 500);
    }

    @Test(priority = 2)
    void addPetWithIDOnly() {
        JSONObject pet = new JSONObject();
        pet.put("id", 1);
        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 3)
    void addPetWithStringID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Jun");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/hama");

        pet.put("id", "one");
        pet.put("name", "Mickey");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test(priority = 4)
    void addPetWithDigitName() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Sin");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/hamt");

        pet.put("id", 1);
        pet.put("name", 12345);
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 5)
    void addPetWithoutCategoryID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Fer");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/hamw");

        pet.put("id", 1);
        pet.put("name", "Binky");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 6)
    void addPetWithStringCategoryID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", "two");
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Rin");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/hamer");

        pet.put("id", 1);
        pet.put("name", "Linta");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test(priority = 7)
    void addPetWithoutCategoryName() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 3);

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Poon");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/hams");

        pet.put("id", 1);
        pet.put("name", "Linka");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 8)
    void addPetWithDigitCategoryName() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 3);
        category.put("name", 1234);

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Ticki");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/h");

        pet.put("id", 1);
        pet.put("name", "Lind");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 9)
    void addPetWithManyPhotoUrls() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Ricki");
        tags.add(tag1);

        photoUrls.add("http://humster.ua");
        photoUrls.add("http://cutie.ua");
        photoUrls.add("http://smallanimals.ua");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 10)
    void addPetWithStringTagID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", "first");
        tag1.put("name", "Hit");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/rin");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test(priority = 11)
    void addPetWithManyTags() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Wer");
        JSONObject tag2 = new JSONObject();
        tag2.put("id", 2);
        tag2.put("name", "Red");
        tags.add(tag1);
        tags.add(tag2);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "available");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 13)
    void addPetWithNotExistedStatus() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<>();
        ArrayList<JSONObject> tags = new ArrayList<>();
        JSONObject pet = new JSONObject();

        category.put("id", 6);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Ku");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put(CATEGORY, category);
        pet.put(URLS, photoUrls);
        pet.put("tags", tags);
        pet.put(STATUS, "asdf123#");

        RequestSpecification request = RestAssured
                .given()
                .spec(requestSpecification)
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }
}