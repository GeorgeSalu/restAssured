package wcaquino.rest.test;

import org.junit.Test;
import wcaquino.rest.core.BaseTest;
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

}
