package io.weli;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.PermitAll;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

@Singleton
@Startup
public class Environment {

    private ResteasyClient internalClient;

    private String cookieName;

    @PostConstruct
    public void init() {

        cookieName = System.getProperty("TEST_COOKIE_NAME");
        if (cookieName == null) {
            cookieName = "JSESSIONID";
        }


        internalClient = (ResteasyClient) ResteasyClientBuilder.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

    }

    @PreDestroy
    public void destroy() {
    }

    @PermitAll
    public ResteasyClient getClient() {
        return internalClient;
    }

    @PermitAll
    public String getCookieName() {
        return cookieName;
    }

}
