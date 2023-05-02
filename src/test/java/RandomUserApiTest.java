import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

public class RandomUserApiTest {
    private static final String BASE_URL = "https://randomuser.me/api/";

    @DisplayName("Get a single user")
    @Test
    public void testGetSingleUser() {
        RestAssured.baseURI = BASE_URL;
        Response response = given().log().all().
                when().
                get();
        List<Object> result = response.jsonPath().getList("results");
        List<String> gender = response.jsonPath().getList("results.gender");
        result.stream().forEach(x-> System.out.println(x));
        assertThat(result, hasSize(1));
        assertThat(gender.stream().allMatch(n -> n.matches("female||male")), is(true));
    }

    @DisplayName("Get list of users")
    @Test
    public void testGetListOfUsers() {
        RestAssured.baseURI = BASE_URL;
        Response response = given().log().all().
                queryParam("results", "5").
                queryParam("nat", "US").
                when().
                get();
        List<Object> result = response.jsonPath().getList("results");
        List<String> nationality = response.jsonPath().getList("results.nat");
        result.stream().forEach(x-> System.out.println(x));
        assertThat(result, hasSize(5));
        assertThat(nationality.stream().allMatch(n -> n.equals("US")), is(true));
    }

    @DisplayName("Filter users by gender")
    @Test
    public void testFilterUsersByGender() {
        RestAssured.baseURI = BASE_URL;
        Response response = given().log().all().
                queryParam("gender", "female").
                queryParam("results", "50").
                when().
                get();
        List<Object> result = response.jsonPath().getList("results");
        List<String> gender = response.jsonPath().getList("results.gender");
        List<String> title = response.jsonPath().getList("results.name.title");
        result.stream().forEach(x-> System.out.println(x));
        assertThat(result, hasSize(50));
        assertThat(gender.stream().allMatch(n -> n.equals("female")), is(true));
        title.stream().forEach(x-> System.out.println(x));
        assertThat(title.stream().allMatch(n -> n.matches("Ms||Miss||Mrs||Mademoiselle||Madame")), is(true));
    }

    @DisplayName("Filter users by nationality")
    @Test
    public void testFilterUsersByNationality() {
        RestAssured.baseURI = BASE_URL;
        Response response = given().log().all().
                queryParam("nat", "GB").
                queryParam("results", "10").
                when().
                get();
        List<Object> result = response.jsonPath().getList("results");
        List<String> nationality = response.jsonPath().getList("results.nat");
        List<String> country = response.jsonPath().getList("results.location.country");
        result.stream().forEach(x-> System.out.println(x));
        assertThat(result, hasSize(10));
        assertThat(nationality.stream().allMatch(n -> n.equals("GB")), is(true));
        country.stream().forEach(x-> System.out.println(x));
        assertThat(country.stream().allMatch(n -> n.equals("United Kingdom")), is(true));

    }

    @DisplayName("Invalid endpoint")
    @Test
    public void testInvalidEndpoint() {
        RestAssured.baseURI = BASE_URL;
        given().log().all().
                when().
                get("/invalid").
                then().
                statusCode(404);
    }

    @DisplayName("Invalid parameters")
    @Test
    public void testInvalidParameters() {
        RestAssured.baseURI = BASE_URL;
        given().log().all().
                queryParam("invalid", "invalid").
                when().
                get().
                then().log().ifValidationFails(LogDetail.BODY).
                statusCode(400);
    }
}
