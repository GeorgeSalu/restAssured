package rest;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.junit.Test;

/**
 * @author george on 04/09/2019
 * @project trainingRestAssured
 */
public class EnvioDadosTest {

    @Test
    public void deveEnviarValorViaQuery() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/v2/users?format=xml")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.XML)
        ;
    }

    @Test
    public void deveEnviarValorViaQueryViaParam() {
        given()
            .log().all()
                .queryParam("format", "xml")
        .when()
            .get("https://restapi.wcaquino.me/v2/users")
        .then()
           .log().all()
            .statusCode(200)
            .contentType(ContentType.XML)
        ;
    }

}
