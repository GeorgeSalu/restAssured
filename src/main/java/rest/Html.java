package rest;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;

/**
 * @author george on 04/09/2019
 * @project trainingRestAssured
 */
public class Html {

    @Test
    public void deveFazerBuscasComHtml() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/v2/users")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.HTML)
        ;
    }

}
