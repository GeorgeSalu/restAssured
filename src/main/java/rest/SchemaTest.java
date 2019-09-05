package rest;

import io.restassured.matcher.RestAssuredMatchers;
import org.junit.Test;

import static io.restassured.RestAssured.*;
/**
 * @author george on 05/09/2019
 * @project trainingRestAssured
 */
public class SchemaTest {

    @Test
    public void deveValidarSchemaXml() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/usersXML")
        .then()
            .log().all()
            .statusCode(200)
            .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
        ;
    }

}
