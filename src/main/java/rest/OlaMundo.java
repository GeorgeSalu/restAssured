package rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

/**
 * @author george on 24/08/2019
 * @project trainingRestAssured
 */
public class OlaMundo {
    public static void main(String[] args) {

        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }
}
