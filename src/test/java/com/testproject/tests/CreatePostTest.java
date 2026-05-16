package com.testproject.tests;

import com.testproject.model.Post;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("POST /posts Endpoint Testleri")
class CreatePostTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("TC-06: Map body ile yeni post oluştur")
    void createPost_withMapBody_shouldReturn201() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", 1);
        requestBody.put("title",  "Rest Assured ile Otomatik Test");
        requestBody.put("body",   "Bu post Rest Assured test projesi kapsamında oluşturuldu.");

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("id",     notNullValue())
                .body("userId", equalTo(1))
                .body("title",  equalTo("Rest Assured ile Otomatik Test"))
                .body("body",   containsString("Rest Assured"))
                .time(lessThan((long) MAX_RESP_MS));
    }

    @Test
    @Order(2)
    @DisplayName("TC-07: POJO body ile yeni post oluştur ve response'u doğrula")
    void createPost_withPojoBody_shouldReturnCreatedPost() {
        Post newPost = new Post(
                5,
                "Yazılım Test Mühendisliği Projesi",
                "JUnit5 + Rest Assured kullanarak regresyon testi yazıyoruz."
        );

        Response response = jsonRequest()
                .body(newPost)
                .when()
                .post("/posts");

        assertEquals(201, response.getStatusCode(),
                "Yeni kayıt için 201 Created bekleniyor");

        long responseTime = response.getTime();
        assertTrue(responseTime < MAX_RESP_MS,
                "Response " + responseTime + "ms sürdü, limit: " + MAX_RESP_MS + "ms");

        Post createdPost = response.as(Post.class);

        assertAll("Oluşturulan post doğrulaması",
                () -> assertNotNull(createdPost.getId(),       "ID null olmamalı"),
                () -> assertTrue(createdPost.getId() > 0,      "ID pozitif olmalı"),
                () -> assertEquals(5, createdPost.getUserId(), "UserId eşleşmeli"),
                () -> assertEquals(newPost.getTitle(), createdPost.getTitle(), "Title eşleşmeli"),
                () -> assertEquals(newPost.getBody(),  createdPost.getBody(),  "Body eşleşmeli")
        );
    }

    @Test
    @Order(3)
    @DisplayName("TC-08: Boş body ile POST — API davranışını gözlemle")
    void createPost_withEmptyBody_shouldHandleGracefully() {
        Map<String, Object> emptyBody = new HashMap<>();

        Response response = jsonRequest()
                .body(emptyBody)
                .when()
                .post("/posts");

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 201 || statusCode == 400,
                "Beklenen status: 201 veya 400, gelen: " + statusCode);

        assertTrue(response.getTime() < MAX_RESP_MS,
                "Response time " + MAX_RESP_MS + "ms altında olmalı");
    }

    @Test
    @Order(4)
    @DisplayName("TC-09: Farklı userId değerleriyle post oluşturma")
    void createPost_multipleUsers_shouldAllReturn201() {
        int[] userIds = {1, 2, 3};

        for (int userId : userIds) {
            Map<String, Object> body = new HashMap<>();
            body.put("userId", userId);
            body.put("title",  "User " + userId + " tarafından oluşturulan post");
            body.put("body",   "Test içeriği");

            given()
                    .contentType("application/json")
                    .body(body)
                    .when()
                    .post("/posts")
                    .then()
                    .statusCode(201)
                    .body("userId", equalTo(userId))
                    .time(lessThan(5000L));

            System.out.println("userId=" + userId + " için post oluşturuldu ✓");
        }
    }
}