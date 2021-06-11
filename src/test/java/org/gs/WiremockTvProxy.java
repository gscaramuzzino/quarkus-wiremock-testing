package org.gs;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockTvProxy implements QuarkusTestResourceLifecycleManager {

  private WireMockServer wireMockServer;

  @Override
  public Map<String, String> start() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
    configureFor(8089);

    stubFor(
        get(urlEqualTo("/singlesearch/shows?q=myTvSerie"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        "{\n"
                            + "\"id\": 1,\n"
                            + "\"name\": \"myTvSerie\",\n"
                            + "\"url\": \"https://www.tvmaze.com/shows/2993/my-tv-series\",\n"
                            + "\"language\": \"English\",\n"
                            + "\"officialSite\": \"https://www.netflix.com/title/80057281\"\n"
                            + "}")));

    stubFor(
        get(urlMatching(".*"))
            .atPriority(10)
            .willReturn(aResponse().proxiedFrom("http://api.tvmaze.com")));

    return Collections.singletonMap("org.gs.TvSeriesProxy/mp-rest/url", wireMockServer.baseUrl());
  }

  @Override
  public void stop() {
    if (wireMockServer != null) {
      wireMockServer.stop();
    }
  }
}
