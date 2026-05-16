package com.testproject.tests;

import com.testproject.model.Post;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /posts Endpoint Testleri")
class GetPostsTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("TC-01: Belirli bir post'u ID ile getir")
    void getPostById_shouldReturn200AndCorrectData() {
        given()
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id",     equalTo(1))
                .body("userId", equalTo(1))
                .body("title",  not(emptyOrNullString()))
                .body("body",   not(emptyOrNullString()))
                .time(lessThan((long) MAX_RESP_MS));
    }

    @Test
    @Order(2)
    @DisplayName("TC-02: Tüm postları listele ve liste boyutunu doğrula")
    void getAllPosts_shouldReturn100Posts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("$",        hasSize(100))
                .body("id",       hasItem(1))
                .body("title[0]", not(emptyOrNullString()))
                .time(lessThan((long) MAX_RESP_MS));
    }

    @Test
    @Order(3)
    @DisplayName("TC-03: userId=1 olan postları filtrele")
    void getPostsByUserId_shouldReturnFilteredResults() {
        given()
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("$",      hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)))
                .time(lessThan((long) MAX_RESP_MS));
    }

    @Test
    @Order(4)
    @DisplayName("TC-04: Response JSON'ı Post objesine dönüştür ve doğrula")
    void getPost_shouldDeserializeCorrectly() {
        Response response = given()
                .pathParam("id", 5)
                .when()
                .get("/posts/{id}");

        assertEquals(200, response.getStatusCode(), "Status code 200 olmalı");

        long responseTime = response.getTime();
        assertTrue(responseTime < MAX_RESP_MS,
                "Response time " + responseTime + "ms, beklenen: <" + MAX_RESP_MS + "ms");

        Post post = response.as(Post.class);

        assertAll("Post alanları doğrulama",
                () -> assertEquals(5,  post.getId(),     "ID 5 olmalı"),
                () -> assertEquals(1,  post.getUserId(), "UserId 1 olmalı"),
                () -> assertNotNull(post.getTitle(),     "Title null olmamalı"),
                () -> assertFalse(post.getTitle().isBlank(), "Title boş olmamalı")
        );
    }

    @Test
    @Order(5)
    @DisplayName("TC-05: Olmayan ID için 404 döndüğünü doğrula")
    void getPostById_withInvalidId_shouldReturn404() {
        try {
            given()
                    .pathParam("id", 99999)
                    .when()
                    .get("/posts/{id}")
                    .then()
                    .statusCode(404)
                    .time(lessThan((long) MAX_RESP_MS));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("404"),
                    "404 hatası bekleniyor, gelen: " + e.getMessage());
        }
    }}