package org.gs;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(WiremockTvProxy.class)
class TvSeriesResourceTest {

  @Test
  void get() {
    given()
        .when()
        .param("title", "stranger things")
        .get("/tvseries")
        .then()
        .body("id", equalTo(2993))
        .body("name", equalTo("Stranger Things"))
        .body("url", equalTo("https://www.tvmaze.com/shows/2993/stranger-things"))
        .body("language", equalTo("English"))
        .body("officialSite", equalTo("https://www.netflix.com/title/80057281"))
        .statusCode(Response.Status.OK.getStatusCode());
  }

  @Test
  void getWithWireMock() {
    given()
        .when()
        .param("title", "myTvSerie")
        .get("/tvseries")
        .then()
        .body("id", equalTo(1))
        .body("name", equalTo("myTvSerie"))
        .body("url", equalTo("https://www.tvmaze.com/shows/2993/my-tv-series"))
        .body("language", equalTo("English"))
        .body("officialSite", equalTo("https://www.netflix.com/title/80057281"))
        .statusCode(Response.Status.OK.getStatusCode());
  }
}
