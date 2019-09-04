package rest;

import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.*;

/**
 * @author george on 03/09/2019
 * @project trainingRestAssured
 */
public class VerbosTest {

    @Test
    public void deveSalvarUsuario() {
        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"name\": \"jose\", \"age\": 50 }")
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", Matchers.is(Matchers.notNullValue()))
            .body("name", Matchers.is("jose"))
            .body("age", Matchers.is(50))
        ;
    }

    @Test
    public void naoDeveSalvarUsuarioSemNome() {
        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"age\": 50 }")
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(400)
            .body("id", Matchers.is(Matchers.nullValue()))
            .body("error", Matchers.is("Name é um atributo obrigatório"))
        ;
    }


}
