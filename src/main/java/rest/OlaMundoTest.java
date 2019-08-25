package rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author george on 24/08/2019
 * @project trainingRestAssured
 */
public class OlaMundoTest {

    @Test
    public void testeOlaMundo() {
        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.getStatusCode() == 200);
        Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 200);
        Assert.assertEquals(200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured() {

        get("http://restapi.wcaquino.me/ola")
                .then()
                .statusCode(200);

        //Modo fluente
        given()
                //pré condicoes
                .when()
                .get("http://restapi.wcaquino.me/ola")
                .then()
                .statusCode(200);

    }

    @Test
    public void devoConhecerMatchersHamcrest() {

        Assert.assertThat("Maria", Matchers.is("Maria"));
        Assert.assertThat(128, Matchers.is(128));
        Assert.assertThat(128, Matchers.isA(Integer.class));
        Assert.assertThat(128d, Matchers.isA(Double.class));
        Assert.assertThat(128d, Matchers.greaterThan(120d));
        Assert.assertThat(128d, Matchers.lessThan(130d));

        List<Integer> impares = Arrays.asList(1,3,5,7,9);
        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1,3,5,7,9));
        assertThat(impares, containsInAnyOrder(1,3,5,9,7));
        assertThat(impares, hasItem(1));

        assertThat("maria", is(not("joao")));
        assertThat("maria", not("joao"));
        assertThat("joaquina", anyOf(is("maria"), is("joaquina")));
    }

    @Test
    public void devoValidarBody() {
        given()
                //pré condicoes
            .when()
                .get("http://restapi.wcaquino.me/ola")
            .then()
                .statusCode(200)
                .body(Matchers.is("Ola Mundo!"))
                .body(containsString("Mundo"))
                .body(is(not(nullValue())));

    }

}