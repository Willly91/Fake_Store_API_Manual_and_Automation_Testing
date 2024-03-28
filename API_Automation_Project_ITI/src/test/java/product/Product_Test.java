package product;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

import org.testng.annotations.Test;

public class Product_Test {

    @Test
    public void get_all_product(){
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("products")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("[0].id", equalTo(1))
                .assertThat().body("[19].id", equalTo(20))
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(20));
    }
    @Test
    public void get_a_single_product(){
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("products/1")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("id", equalTo(1))
                .time(lessThan(3000L))
                .assertThat().body("rating.rate", equalTo(3.9f));
    }
    @Test
    public void limit_results_of_product() {
        given()
              .baseUri("https://fakestoreapi.com")
              .queryParam("limit", 5)
        .when()
              .get("/products")
        .then()
              .log().all()
              .assertThat().statusCode(200)
              .assertThat().body("[4].id", equalTo(5))
              .time(lessThan(3000L))
              .assertThat().body("size()", equalTo(5));
  }
    @Test
    public void Sort_result_of_products_DESC() {
        given()
                .baseUri("https://fakestoreapi.com")
                .queryParam("sort", "desc")
        .when()
                .get("products")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("[0].id", equalTo(20))
                .assertThat().body("[19].id", equalTo(1))
                .time(lessThan(3000L))
                .assertThat().body("size()", equalTo(20));;

    }
    @Test
    public void get_all_categories() {
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("/products/categories")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("size()", equalTo(4))
                .assertThat().body("", hasItems("electronics", "jewelery", "men's clothing", "women's clothing"))
                .time(lessThan(3000L));
    }
    @Test
    public void get_product_in_specific_categories() {
        given()
                .baseUri("https://fakestoreapi.com")
        .when()
                .get("/products/category/jewelery")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .time(lessThan(3000L))
                .body("size()", equalTo(4))
                .body("category", everyItem(equalTo("jewelery")));
    }
    @Test
    public void update_a_product_PUT() {
        baseURI = "https://fakestoreapi.com";
        given()
                .contentType("application/json")
                .body("{\"id\": 1,\"title\": \"test product\",\"price\": 13.5,\"description\":\"lorem ipsum\",\"image\":\"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg\",\"category\":\"electronic\"}") // Update title and price
        .when()
                .put("/products/1")
        .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo("test product"))
                .assertThat().body("id",equalTo(1))
                .time(lessThan(3000L));
    }
    @Test
    public void update_a_product_using_BATCH() {
        baseURI = "https://fakestoreapi.com";
        given()
                .contentType("application/json")
                .body("{\"id\": 1,\"title\": \"test\",\"price\": 13.5,\"description\":\"lorem ipsum\",\"image\":\"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg\",\"category\":\"electronic\"}") // Update the price of the product
        .when()
                .patch("/products/1")
        .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo("test"))
                .body("id", equalTo(1))
                .time(lessThan(3000L));
    }
    @Test
    public void delete_a_specific_product() {
        baseURI = "https://fakestoreapi.com";
        when()
                .delete("/products/1")
        .then()
                .assertThat().statusCode(200)
                .body("id", equalTo(1))
                .time(lessThan(6000L));;
    }
}




