package cart;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Cart_Test {

    @Test
    public void get_all_carts(){
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("carts")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("[0].id", equalTo(1))
                .assertThat().body("[0].products[0].productId", equalTo(1))
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(7));
    }
    @Test
    public void get_a_single_cart(){
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("carts/5")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("id", equalTo(5))
                .time(lessThan(3000L));
    }
    @Test
    public void limit_results_of_carts() {
        given()
                .baseUri("https://fakestoreapi.com")
                .queryParam("limit", 5)
        .when()
                .get("/carts")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("[4].id", equalTo(5))
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(5));
    }
    @Test
    public void Sort_result_of_carts_DESC() {
        given()
                .baseUri("https://fakestoreapi.com")
                .queryParam("sort", "desc")
        .when()
                .get("carts")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("[0].id", equalTo(7))
                .assertThat().body("[6].id", equalTo(1))
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(7));;

    }
    @Test
    public void get_carts_in_specific_date() {
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .queryParam("enddate", "2020-01-01")
                .get("/carts")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .time(lessThan(3000L))
                .body("size()", equalTo(1))
                .assertThat().body("[0].date", equalTo("2020-01-01T00:00:00.000Z"));
    }
    @Test
    public void get_carts_in_a_date_range() {
        given()
                .baseUri("https://fakestoreapi.com")
                .param("startdate", "2019-01-01")
                .param("enddate", "2020-01-02")
        .when()
                .get("carts")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().time(lessThan(3000L))
                .body("[0].date", equalTo("2020-01-02T00:00:00.000Z"))
                .body("[1].date", equalTo("2020-01-01T00:00:00.000Z"));
    }
    @Test
    public void get_carts_in_specific_user() {
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("/carts/user/2")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .time(lessThan(3000L))
                .body("size()", equalTo(1))
                .assertThat().body("[0].userId", equalTo(2));
    }
    @Test
    public void add_a_new_product_in_carts() {
        given()
                .baseUri("https://fakestoreapi.com")
                .header("Content-Type","application/json")
                .body("{\"date\": \"2020-03-03T00:00:00.000Z\"}")
        .when()
                .post("carts")
        .then()
                .log().all()
                .body("id", not(empty()))
                .assertThat().body("id", equalTo(11))
                .assertThat().body("userid", nullValue())
                .assertThat().statusCode(200)
                .time(lessThan(3000L));
    }
    @Test
    public void update_a_product_PUT() {
        baseURI = "https://fakestoreapi.com";
        given()
                .contentType("application/json")
                .body("{\"userId\": 8,\"date\": \"2020-03-03T00:00:00.000Z\"}")
        .when()
                .put("/carts/5")
        .then()
                .body("id", not(empty()))
                .assertThat().statusCode(200)
                .assertThat().body("userId", equalTo(8))
                .assertThat().body("id",equalTo(5))
                .time(lessThan(3000L));
    }
    @Test
    public void update_a_product_using_PATCH() {
        baseURI = "https://fakestoreapi.com";
        given()
                .contentType("application/json")
                .body("{\"userId\": 8,\"date\": \"2020-03-03T00:00:00.000Z\"}")
        .when()
                .patch("/carts/5")
        .then()
                .body("id", not(empty()))
                .assertThat().statusCode(200)
                .assertThat().body("userId", equalTo(8))
                .assertThat().body("id",equalTo(5))
                .time(lessThan(3000L));
    }
    @Test
    public void delete_a_specific_cart() {
        baseURI = "https://fakestoreapi.com";
        when()
                .delete("/carts/5")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("id", equalTo(5))
                .time(lessThan(5000L));;
    }
}
