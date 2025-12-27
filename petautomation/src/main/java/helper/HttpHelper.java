package helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.HashMap;

import static io.restassured.RestAssured.given;


public class HttpHelper {

    public HttpHelper() {

    }

    public Response SendRequestPost(String url, String request) {
        RestAssured.baseURI = url;
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(request)
                .post();
    }

    public Response SendRequestPut(String url, String request) {
        RestAssured.baseURI = url;
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(request)
                .put();
    }

    public Response SendRequestGet(String url) {
        RestAssured.baseURI = url;
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .get();
    }

    public Response SendRequestGet(String url, HashMap<String, Object> urlParams) {
        RestAssured.baseURI = url;
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .params(urlParams)
                .when()
                .get();
    }

    public Response SendRequestDelete(String url) {
        RestAssured.baseURI = url;
        return given()
                .log().all()
                .header("accept", "application/json")
                .when()
                .delete();
    }

    public static boolean isSuccess(int statusCode) {
        return statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED || statusCode == HttpStatus.SC_ACCEPTED;
    }

}
