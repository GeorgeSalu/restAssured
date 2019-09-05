package rest;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.*;

/**
 * @author george on 04/09/2019
 * @project trainingRestAssured
 */
public class Html {

    @Test
    public void deveFazerBuscasComHtml() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/v2/users")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.HTML)
            .body("html.body.div.table.tbody.tr.size()", Matchers.is(3))
            .body("html.body.div.table.tbody.tr[1].td[2]", Matchers.is("25"))

        ;
    }

    @Test
    public void deveFazerBuscasXPathComHtml() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/v2/users?format=clean")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.HTML)
            .body(Matchers.hasXPath("count(//table/tr)", Matchers.is("4")))

        ;
    }


}
