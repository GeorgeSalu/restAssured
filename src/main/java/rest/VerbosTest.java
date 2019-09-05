package rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
    public void deveSalvarUsuarioUsandoMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "Usuario via map");
        params.put("age", 25);


        given()
            .log().all()
            .contentType("application/json")
            .body(params)
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", Matchers.is(Matchers.notNullValue()))
            .body("name", Matchers.is("Usuario via map"))
            .body("age", Matchers.is(25))
        ;
    }

    @Test
    public void deveSalvarUsuarioUsandoObjeto() {
        User user = new User("Usuario via objeto", 35);

        given()
            .log().all()
            .contentType("application/json")
            .body(user)
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", Matchers.is(Matchers.notNullValue()))
            .body("name", Matchers.is("Usuario via objeto"))
            .body("age", Matchers.is(35))
        ;
    }

    @Test
    public void deveDeserializarAoSalvarUsuarioUsandoObjeto() {
        User user = new User("Usuario deserializado", 35);

        User usuarioInserido = given()
            .log().all()
            .contentType("application/json")
            .body(user)
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .extract().body().as(User.class)
        ;

        Assert.assertEquals("Usuario deserializado", usuarioInserido.getName());
        Assert.assertThat(usuarioInserido.getAge(), Matchers.is(35));
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

    @Test
    public void deveSalvarUsuarioViaXML() {
        given()
            .log().all()
            .contentType("application/xml")
            .body("<user><name>Jose</name><age>50</age></user>")
        .when()
            .post("https://restapi.wcaquino.me/usersXML")
        .then()
            .log().all()
            .statusCode(201)
            .body("user.@id", Matchers.is(Matchers.notNullValue()))
            .body("user.name", Matchers.is("Jose"))
            .body("user.age", Matchers.is("50"))
        ;
    }

    @Test
    public void deveSalvarUsuarioViaXMLUsandoObjeto() {
        User user = new User("Usuario XML", 40);

        given()
           .log().all()
            .contentType("application/xml")
            .body(user)
        .when()
            .post("https://restapi.wcaquino.me/usersXML")
        .then()
            .log().all()
            .statusCode(201)
            .body("user.@id", Matchers.is(Matchers.notNullValue()))
            .body("user.name", Matchers.is("Usuario XML"))
            .body("user.age", Matchers.is("40"))
        ;
    }


    @Test
    public void deveAlterarUsuario() {
        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"name\": \"Usuario Alterado\", \"age\": 50 }")
        .when()
            .put("https://restapi.wcaquino.me/users/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", Matchers.is(1))
            .body("name", Matchers.is("Usuario Alterado"))
            .body("age", Matchers.is(50))
        ;
    }

    @Test
    public void deveCustomizarURL() {
        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"name\": \"Usuario Alterado\", \"age\": 50 }")
        .when()
            .put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", Matchers.is(1))
            .body("name", Matchers.is("Usuario Alterado"))
            .body("age", Matchers.is(50))
        ;
    }

    @Test
    public void deveCustomizarURLParametrizado() {
        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"name\": \"Usuario Alterado\", \"age\": 50 }")
            .pathParam("entidade", "users")
            .pathParam("userId", 1)
        .when()
            .put("https://restapi.wcaquino.me/{entidade}/{userId}")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", Matchers.is(1))
            .body("name", Matchers.is("Usuario Alterado"))
            .body("age", Matchers.is(50))
        ;
    }

    @Test
    public void deveRemoverUsuario() {
        given()
            .log().all()
        .when()
            .delete("https://restapi.wcaquino.me/users/1")
        .then()
            .log().all()
            .statusCode(204)
        ;
    }

    @Test
    public void deveRemoverUsuarioInexistente() {
        given()
            .log().all()
        .when()
            .delete("https://restapi.wcaquino.me/users/100")
        .then()
            .log().all()
            .statusCode(400)
        .body("error", Matchers.is("Registro inexistente"))
        ;
    }

}