package com.testproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSONPlaceholder /posts endpoint'ine karşılık gelen model sınıfı.
 *
 * @JsonIgnoreProperties: API'den fazladan alan gelse bile hata vermez.
 *
 * Örnek JSON:
 * {
 *   "userId": 1,
 *   "id": 1,
 *   "title": "sunt aut facere ...",
 *   "body": "quia et suscipit ..."
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    // ===== Constructors =====

    public Post() {}

    public Post(int userId, String title, String body) {
        this.userId = userId;
        this.title  = title;
        this.body   = body;
    }

    // ===== Getters & Setters =====

    public int getUserId()          { return userId; }
    public void setUserId(int v)    { this.userId = v; }

    public int getId()              { return id; }
    public void setId(int v)        { this.id = v; }

    public String getTitle()        { return title; }
    public void setTitle(String v)  { this.title = v; }

    public String getBody()         { return body; }
    public void setBody(String v)   { this.body = v; }

    @Override
    public String toString() {
        return "Post{id=" + id + ", userId=" + userId +
                ", title='" + title + "'}";
    }
}
