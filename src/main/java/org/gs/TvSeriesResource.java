package org.gs;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tvseries")
public class TvSeriesResource {

  @RestClient
  TvSeriesProxy proxy;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@QueryParam("title") String title) {
    TvSeries tvSeries = proxy.get(title);
    return Response.ok(tvSeries).build();
  }
}
