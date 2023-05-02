import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class CleanUriAPITest {
    private static final String BASE_URL = "https://cleanuri.com/api/v1";
    private final List<String> lines = reader();
    private final String positiveStr = lines.get(0);
    private final String negativeStr = lines.get(1);
    private final String negativeStr2 = lines.get(2);
    private final String emptyStr = lines.get(3);
    private final String strWithSpaces = lines.get(4);
    private final String positiveStr2 = lines.get(5);

    public CleanUriAPITest() throws IOException {
    }


    @Test
    void positiveTestShortenUrl() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(positiveStr);

        Response response = given()
                .param("url", positiveStr)
                .post("/shorten");

        response.print();
        response.then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json")
                .body("result_url", Matchers.notNullValue())
                .body("result_url", Matchers.containsString("https://cleanuri.com/"));
    }

    @Test
    void positiveTestShortenUrl2() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(positiveStr2);

        Response response = given()
                .param("url", positiveStr2)
                .post("/shorten");

        response.print();
        response.then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json")
                .body("result_url", Matchers.notNullValue())
                .body("result_url", Matchers.containsString("https://cleanuri.com/"));
    }

    @Test
    void negativeTestShortenUrl2() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(negativeStr);

        Response response = given()
                .param("url", negativeStr)
                .post("/shorten");

        response.print();
        response.then()
                .assertThat()
                .statusCode(400)
                .contentType("application/json")
                .body("error", Matchers.containsString("API Error: URL is invalid. (check #2)"));
    }

    @Test
    void negativeTestShortenUrl1() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(negativeStr2);

        Response response = given()
                .param("url", negativeStr2)
                .post("/shorten");

        response.print();
        response.then()
                .assertThat()
                .statusCode(400)
                .contentType("application/json")
                .body("error", Matchers.containsString("API Error: URL is invalid (check #1)"));
    }

    @Test
    void negativeTestShortenUrlEmpty() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(emptyStr);

        Response response = given()
                .param("url", emptyStr)
                .post("/shorten");

        response.print();
        response.then()
                .assertThat()
                .statusCode(400)
                .contentType("application/json")
                .body("error", Matchers.containsString("URL is empty"));
    }

    @Test
    void negativeTestShortenUrlWithSpaces() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(strWithSpaces);

        Response response = given()
                .param("url", strWithSpaces)
                .post("/shorten");

        response.print();
        response.then()
                .assertThat()
                .statusCode(400)
                .contentType("application/json")
                .body("error", Matchers.containsString("URL is empty"));
    }

    List<String> reader() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\urls"), StandardCharsets.UTF_8);

        return lines;
    }
}
