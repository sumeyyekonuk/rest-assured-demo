# Rest-Assured API Test Automation Demo

Bu proje, RESTful web servislerinin test otomasyonunu gerçekleştirmek amacıyla Rest-Assured kütüphanesi kullanılarak geliştirilmiş bir API test projesidir. Projede BDD (Behavior Driven Development) yaklaşımı (given-when-then) benimsenerek test senaryoları yapılandırılmıştır.

## 🚀 Kullanılan Teknolojiler
* Java - Programlama Dili
* Rest-Assured - API Test Framework'ü
* Maven - Bağımlılık ve Proje Yönetimi
* JUnit 5 - Test Yönetimi, Test Sıralama (@Order) ve Doğrulamalar

## 📦 Proje Yapısı
rest-assured-demo
├── src
│   └── test/java
│       └── com.testproject
│           ├── model        # Veri modelleri (Post vb.)
│           └── tests        # API test senaryoları (BaseTest, CreatePostTest, GetPostsTest)
├── pom.xml                  # Maven bağımlılıkları
└── README.md                # Proje dokümantasyonu

## 🛠️ Kurulum ve Çalıştırma

1. Projeyi bilgisayarınıza klonlayın:
git clone https://github.com/sumeyyekonuk/rest-assured-demo.git

2. Proje dizinine gidin:
cd rest-assured-demo

3. Testleri terminal üzerinden çalıştırmak için:
mvn clean test

## 📝 Örnek Test Yapısı
@Test
@Order(2)
@DisplayName("TC-02: Tüm postları listele ve liste boyutunu doğrula")
void getAllPosts_shouldReturn100Posts() {
    given()
    .when()
        .get("/posts")
    .then()
        .statusCode(200)
        .body("$", hasSize(100))
        .body("id", hasItem(1));
}
