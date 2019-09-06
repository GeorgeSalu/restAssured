package wcaquino.rest.test;

import org.hamcrest.Matchers;
import org.junit.Before;
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

    private String TOKEN;

    @Before
    public void login() {
        Map<String, String> login = new HashMap<>();
        login.put("email", "george.salu10@gmail.com");
        login.put("senha", "saludasilva");

        TOKEN = given()
            .body(login)
        .when()
            .post("/signin")
        .then()
            .statusCode(200)
            .extract().path("token")
            ;
    }

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

    @Test
    public void deveAlterarContaComSucesso() {

        given()
            .header("Authorization", "JWT "+TOKEN)
            .body("{ \"nome\": \"conta alterada\"}")
        .when()
            .put("/contas/31977")
        .then()
            .statusCode(200)
        ;
    }

    @Test
    public void naoDeveInserirContaComMesmoNome() {

        given()
            .header("Authorization", "JWT "+TOKEN)
           .body("{ \"nome\": \"conta alterada\"}")
        .when()
            .post("/contas")
        .then()
            .statusCode(400)
        ;
    }

    @Test
    public void deveInserirMovimentacaoComSucesso() {
        Movimentacao mov = new Movimentacao();
        mov.setConta_id(31977);
        //mov.setUsuario_id();
        mov.setDescricao("Descricao da movimentacao");
        mov.setEnvolvida("Envolvido na mov");
        mov.setTipo("REC");
        mov.setData_transacao("01/01/2000");
        mov.setData_pagamento("10/05/2010");
        mov.setValor(100f);
        mov.setStatus(true);


        given()
            .header("Authorization", "JWT "+TOKEN)
            .body(mov)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(201)
        ;
    }

    @Test
    public void deveValidarComposObrigatoriosMovimentacao() {

        given()
            .header("Authorization", "JWT "+TOKEN)
            .body("{}")
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
            .body("$", Matchers.hasSize(8))
        ;
    }

    @Test
    public void naoDeveInserirMovimentacoesComDataFutura() {
        Movimentacao mov = new Movimentacao();
        mov.setData_transacao("20/05/2019");

        given()
            .header("Authorization", "JWT "+TOKEN)
            .body(mov)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
            .body("error", Matchers.is("Data da Movimentação deve ser menor ou igual a data atual"))
        ;
    }

    @Test
    public void naoDeveRemoverContaComMovimentacao() {

        given()
            .header("Authorization", "JWT "+TOKEN)
        .when()
            .post("/contas/1223")
        .then()
            .statusCode(400)
        ;
    }

    @Test
    public void deveCalcularSaldoContas() {

        given()
            .header("Authorization", "JWT "+TOKEN)
        .when()
            .post("saldo")
        .then()
            .statusCode(400)
            .body("find{it.conta_id == 1223}.saldo", Matchers.is("100.00"))
        ;
    }

}