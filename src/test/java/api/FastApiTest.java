package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class FastApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8000;
    }

    @Test
    public void testRoot() {
        RestAssured
            .get("/")
            .then()
            .statusCode(200)
            .body("message", equalTo("Dummy API is running"));
    }

    @Test
    public void testCreateItem() {
        String jsonBody = """
            {
                "id": 1,
                "name": "Laptop",
                "description": "Dell XPS"
            }
            """;

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(jsonBody)
            .post("/items")
            .then()
            .statusCode(200)
            .body("name", equalTo("Laptop"));
    }

    @Test
    public void testGetItems() {
        RestAssured
            .get("/items")
            .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));
    }
}
