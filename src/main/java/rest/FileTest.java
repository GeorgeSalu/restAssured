package rest;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @author george on 05/09/2019
 * @project trainingRestAssured
 */
public class FileTest {

    @Test
    public void deveObrigarEnvioArquivo() {
        given()
            .log().all()
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .statusCode(404)
            .body("error", is("Arquivo não enviado"))
        ;
    }

}
