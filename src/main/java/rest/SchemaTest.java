package rest;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Test;
import org.xml.sax.SAXParseException;

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

    @Test(expected = SAXParseException.class)
    public void deveValidarSchemaXmlInvalido() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/invalidUsersXML")
        .then()
            .log().all()
            .statusCode(200)
            .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
        ;
    }

    @Test
    public void deveValidarSchemaJson() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))
        ;
    }

}
