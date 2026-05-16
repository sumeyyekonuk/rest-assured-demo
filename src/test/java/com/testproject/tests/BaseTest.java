package com.testproject.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected static final String BASE_URL    = "https://jsonplaceholder.typicode.com";
    protected static final int    MAX_RESP_MS = 3000;

    @BeforeAll
    static void globalSetup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new RequestLoggingFilter()); // ResponseLoggingFilter KALDIRILDI
        System.out.println("====================================");
        System.out.println("  Test Suite Başlatıldı");
        System.out.println("  Base URL: " + BASE_URL);
        System.out.println("====================================");
    }

    protected RequestSpecification jsonRequest() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}