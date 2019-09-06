package rest;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void devefazerAutenticacaoBasica2() {
        given()
            .log().all()
            .auth().basic("admin", "senha")
        .when()
            .get("https://restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(200)
        ;
    }

    @Test
    public void devefazerAutenticacaoComTokenJWT() {
        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "george.salu10@gmail.com");
        login.put("senha", "saludasilva");

        String token = given()
            .log().all()
            .body(login)
            .contentType(ContentType.JSON)
        .when()
            .post("http://barrigarest.wcaquino.me/signin")
        .then()
            .log().all()
            .statusCode(200)
            .extract().path("token")
        ;

        //obter contas
        given()
            .log().all()
            .header("Authorization", "JWT "+token)
        .when()
            .get("http://barrigarest.wcaquino.me/contas")
        .then()
            .log().all();




    }

    @Test
    public void deveAcessarAplicacaoWeb() {
        given()
            .log().all()
                .formParam("email","george.salu10@gmail.com")
                .formParam("senha", "sssasas")
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
        .when()
            .post("http://seubarriga.wcaquino.me/login")
        .then()
            .log().all()
            .statusCode(200)
        ;
    }

}
