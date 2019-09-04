package rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
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
            .body("users.user.find{it.age == 25}.name", Matchers.is("Maria Joaquina"))
            .body("users.user.findAll{it.name.toString().contains('n')}.name", Matchers.hasItems("Maria Joaquina","Ana Julia"))
            .body("users.user.salary.find{it != null}.toDouble()", Matchers.is(1234.5678d))
        ;
    }

    @Test
    public void devoTrabalharComXmlEJava() {
        String nome = given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML")
        .then()
            .statusCode(200)
            .extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}")

        ;

        Assert.assertEquals("Maria Joaquina".toUpperCase(), nome.toUpperCase());
    }

    @Test
    public void devoFazerPesquisasAvancadasComXPath() {
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML")
        .then()
            .statusCode(200)
            .body(Matchers.hasXPath("count(/users/user)", Matchers.is("3")))
            .body(Matchers.hasXPath("/users/user[@id = '1']"))
            .body(Matchers.hasXPath("//user[@id = '2']"))
            .body(Matchers.hasXPath("//name[text() = 'Luizinho']/../../name", Matchers.is("Ana Julia")))

        ;

    }

}
