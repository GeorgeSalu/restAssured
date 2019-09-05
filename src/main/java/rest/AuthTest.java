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


    //api.openweathermap.org/data/2.5/weather?q=Fortaleza,BR$appid=03b1a2926d557b8971950da3747ad7e1&units=metric

    @Test
    public void deveObterClima() {
        given()
            .log().all()
            .queryParam("q", "Fortaleza,BR")
            .queryParam("appid", "03b1a2926d557b8971950da3747ad7e1")
            .queryParam("units", "metric")
        .when()
            .get("http://api.openweathermap.org/data/2.5/weather")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", Matchers.is("Fortaleza"))
            .body("coord.lon", Matchers.is(-38.52f))
        ;
    }

    @Test
    public void naoDeveAcessarSemSenha() {
        given()
            .log().all()
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(401)
        ;
    }

    @Test
    public void deveFazerAutenticacaoBasica() {
        given()
            .log().all()
        .when()
            .get("https://admin:senha@restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(200)
        ;
    }

}
