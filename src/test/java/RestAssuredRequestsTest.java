import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssuredRequestsTest {

    RequestSpecification httpRequest = RestAssured.given();
    Response response = httpRequest.request(Method.GET, "/octocat");

    @BeforeAll
    static void prepare() {
        RestAssured.baseURI = "https://api.github.com/users";
    }

    @Test
    void shouldReturnStatusCode() {
        int statusCode = response.getStatusCode();

        assertEquals(statusCode, 200);
    }

    @Test
    void shouldReturnContentType() {
        String contentType = response.header("Content-Type");

        assertEquals(contentType, "application/json; charset=utf-8");
    }

    @Test
    void shouldReturnJsonPath() {
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.get("name");
        int id = jsonPath.get("id");
        String url = jsonPath.get("url");

        Assertions.assertAll(
                () -> assertEquals(name, "The Octocat"),
                () -> assertEquals(id, 583231),
                () -> assertEquals(url, "https://api.github.com/users/octocat")
        );
    }
}
