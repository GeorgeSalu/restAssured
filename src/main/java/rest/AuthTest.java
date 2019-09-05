package rest;

import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.*;

/**
 * @author george on 05/09/2019
 * @project trainingRestAssured
 */
public class AuthTest {

    @Test
    public void deveAcessarSWAPI() {
        given()
            .log().all()
        .when()
            .get("https://swapi.co/api/people/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", Matchers.is("Luke Skywalker"))
        ;
    }

}
