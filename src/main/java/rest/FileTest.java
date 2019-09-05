package rest;

import org.junit.Test;

import java.io.File;

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

    @Test
    public void deveFazerUploadArquivo() {
        given()
            .log().all()
            .multiPart("arquivo", new File("src/main/resources/user.pdf"))
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("user.pdf"))
        ;
    }

}
