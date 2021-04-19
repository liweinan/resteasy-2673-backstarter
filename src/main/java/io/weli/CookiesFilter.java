package io.weli;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.util.Map;

public class CookiesFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        System.out.println(".DUMP.OF.COOKIES.");
        for (Map.Entry c : requestContext.getCookies().entrySet()) {
            System.out.println(c);
        }
        System.out.println(".END.");
    }
}
