package org.mitprog1.gatewayservice.routelocator.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfiguration {

    @Value("${app.provider.uri}")
    private String dataProviderUri;

    @Value("${app.receiver.uri}")
    private String dataReceiverUri;

//    @Autowired
//    RouteLocatorBuilder builder;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.
//                                path("/services/resourcemock/v1/upload**")
                                path("/services/resourcemock/v1/**")
                                .filters(f -> f//.rewritePath("/download(?.*)", "/${remains}")
                                                .addRequestHeader("Hey", "RS-Mock")
//                                .hystrix(c -> c.setName("hystrix")
//                                        .setFallbackUri("forward:/fallback/first"))
                                )
//                                .uri("/resource-service-mock/")
                                .uri(dataProviderUri/*"lb://FIRST-SERVICE/"*/)
//                        .id("provider-der-service")
                )

                .route(r -> r.path("/services/songs/v1/**")
                                .filters(f -> f//.rewritePath("/api/v1/second/(?.*)", "/${remains}")
                                                .addRequestHeader("Yo", "Songs-s")
//                                .hystrix(c -> c.setName("hystrix")
//                                        .setFallbackUri("forward:/fallback/second"))
                                )
                                .uri(dataReceiverUri/*"lb://FIRST-SERVICE/"*/)
//                        .uri("/song-server/")
//                        .uri("lb://song-server/")
//                        .id("ss-on-gg-service")
                )
                .build();
    }
}

