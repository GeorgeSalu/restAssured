package wcaquino.rest.test;

import org.junit.Test;
import wcaquino.rest.core.BaseTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
/**
 * @author george on 05/09/2019
 * @project trainingRestAssured
 */
public class BarrigaTest extends BaseTest {

    @Test
    public void naoDeveAcessarAPISemToken() {
        given()
        .when()
            .get("/contas")
        .then()
            .statusCode(401)
        ;
    }

    @Test
    public void deveIncluirContaComSucesso() {
        Map<String, String> login = new HashMap<>();
        login.put("email", "george.salu10@gmail.com");
        login.put("senha", "saludasilva");

        String token = given()
            .body(login)
        .when()
            .post("/signin")
        .then()
            .statusCode(200)
            .extract().path("token")
        ;

        given()
            .header("Authorization", "JWT "+token)
            .body("{ \"nome\": \"conta qualquer\"}")
        .when()
            .post("/contas")
        .then()
            .statusCode(201)
        ;
    }

}