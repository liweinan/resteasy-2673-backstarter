package io.weli;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;
import java.util.Map;

public class CookiesFilter implements ClientRequestFilter, ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        System.out.println("REQUEST.DUMP.OF.COOKIES.");
        for (Map.Entry c : requestContext.getCookies().entrySet()) {
            System.out.println(c);
        }
        System.out.println(".END.");
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        System.out.println("RESPONSE.DUMP.OF.COOKIES.");
        for (Map.Entry c : requestContext.getCookies().entrySet()) {
            System.out.println(c);
        }
        System.out.println(".END.");
    }
}
