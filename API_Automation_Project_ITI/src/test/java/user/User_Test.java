package user;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class User_Test {

    @Test
    public void get_all_users(){
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("users")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(10));
    }
    @Test
    public void get_a_single_user(){
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("users/2")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("id", equalTo(2))
                .time(lessThan(3000L));
    }
    @Test
    public void limit_results_of_users() {
        given()
                .baseUri("https://fakestoreapi.com")
                .queryParam("limit", 5)
        .when()
                .get("/users")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(5));
    }
    @Test
    public void Sort_result_of_users_DESC() {
        given()
                .baseUri("https://fakestoreapi.com")
                .queryParam("sort", "desc")
        .when()
                .get("users")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("[0].id", equalTo(10))
                .assertThat().body("[9].id", equalTo(1))
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(10));;

    }
    @Test
    public void add_a_new_user() {
        given()
                .baseUri("https://fakestoreapi.com")
                .header("Content-Type","application/json")
                .body("{\"id\": \"11\"}")
        .when()
                .post("carts")
        .then()
                .log().all()
                .body("id", not(empty()))
                .assertThat().body("id", equalTo(11))
                .assertThat().statusCode(200)
                .time(lessThan(3000L));
    }
    @Test
    public void update_a_user_using_PUT() {
        baseURI = "https://fakestoreapi.com";
        given()
                .contentType("application/json")
                .body("{\"id\": 2}")
        .when()
                .put("/users/2")
        .then()
                .body("id", not(empty()))
                .assertThat().statusCode(200)
                .assertThat().body("id",equalTo(2))
                .time(lessThan(3000L));
    }
    @Test
    public void update_a_user_using_PATCH() {
        baseURI = "https://fakestoreapi.com";
        given()
                .contentType("application/json")
                .body("{\"id\": 2}")
        .when()
                .patch("/users/2")
        .then()
                .body("id", not(empty()))
                .assertThat().statusCode(200)
                .assertThat().body("id",equalTo(2))
                .time(lessThan(3000L));
    }
    @Test
    public void delete_a_specific_cart() {
        baseURI = "https://fakestoreapi.com";
        when()
                .delete("/users/2")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("id", equalTo(2))
                .time(lessThan(3000L));;
    }
}
