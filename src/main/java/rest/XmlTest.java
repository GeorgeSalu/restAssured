package rest;

import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * @author george on 25/08/2019
 * @project trainingRestAssured
 */
public class XmlTest {

    @Test
    public void devoTrabalharComXml() {
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/3")
        .then()
            .statusCode(200)
                .rootPath("user")
            .body("name", Matchers.is("Ana Julia"))
            .body("@id", Matchers.is("3"))
            .body("filhos.name.size()", Matchers.is(2))
            .body("filhos.name[0]", Matchers.is("Zezinho"))
            .body("filhos.name[1]", Matchers.is("Luizinho"))
            .body("filhos.name", Matchers.hasItem("Luizinho"))
            .body("filhos.name", Matchers.hasItems("Luizinho", "Zezinho"))
        ;
    }

    @Test
    public void devoFazerPesquisasAvancadasComXml() {
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML")
        .then()
            .statusCode(200)
            .body("users.user.size()", Matchers.is(3))
            .body("users.user.findAll{it.age.toInteger() <= 25}.size()", Matchers.is(2))
            .body("users.user.@id", Matchers.hasItems("1","2","3"))
        ;
    }

}
