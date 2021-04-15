package io.weli;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestResource {

    @Inject
    Environment environment;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/foo")
    public String foo() {
        return "foo";
    }

    public static final String SEG = "back-starter";

    @GET
    public Response exportFile(@QueryParam("token") String token) throws Exception {
        String path = String.format("http://localhost:8080/%s/resources/test/set_cookie", SEG);
        //Make a call to the "set_cookie" endpoint expecting a cookie (JSESSIONID by default) to be set.
        WebTarget target = environment.getClient()
                .target(path);
        Response response = target.request()
                .post(Entity.entity("<test><a>b</a></test>", MediaType.APPLICATION_XML));
        if (response.getStatus() != 200) {
            //the set_cookie enpoint signalled that a JSESSIONID cookie has been passed to it
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(response.readEntity(String.class))
                    .build();
        }
        //the cookie was successfully set
        return Response.status(Response.Status.OK)
                .entity(String.join("=", environment.getCookieName(), response.getCookies().get(environment.getCookieName()).getValue()))
                .build();
    }

    @POST
    @Path("set_cookie")
    public Response setCookie() {
        //if the request contains a cookie with the specified name (JSESSIONID by default), return a different response.
        if (request.getCookies() != null) {
            List<Cookie> cookies = Arrays.asList(request.getCookies());
            Optional<Cookie> sessionCookie = cookies
                    .stream()
                    .filter(
                            x -> x
                                    .getName()
                                    .equals(environment.getCookieName()))
                    .findFirst();
            if (sessionCookie.isPresent()) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Cookie already set")
                        .build();
            }
        }

        //No cookie was found, so set the cookie (JSESSIONID by default)
        return Response.status(Response.Status.OK)
                .cookie(NewCookie.valueOf(String.join("=", environment.getCookieName(), UUID.randomUUID().toString())))
                .build();
    }

}
