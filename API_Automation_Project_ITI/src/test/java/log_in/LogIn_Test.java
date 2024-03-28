package log_in;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

import org.testng.annotations.Test;

public class LogIn_Test {

    @Test
    public void user_login_test(){

        given()
                .baseUri("https://fakestoreapi.com")
                .header("Content-Type","application/json")
                .body("{\"username\": \"mor_2314\", \"password\": \"83r5^_\"}")
        .when()
                .post("auth/login")
        .then()
                .log().all()
                .body("token", not(empty()))
                .assertThat().statusCode(200)
                .time(lessThan(3000L));
    }
}

