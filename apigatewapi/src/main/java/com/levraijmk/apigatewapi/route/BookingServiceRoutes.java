package com.levraijmk.apigatewapi.route;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.*;

import java.net.URI;




@Configuration
public class BookingServiceRoutes {

    @Bean
    public RouterFunction<ServerResponse> bookingRoutes(){
        return GatewayRouterFunctions.route("booking-service")//id
                .route(
                        RequestPredicates.POST("/api/v1/booking"),//ce que le client va appeler
                        HandlerFunctions.http()
                        )
                .before(BeforeFilterFunctions.uri("http://localhost:9091/api/v1/booking")) // la redirection que la gateway va faire

                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookingServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute"))) //transferer les requettes une fois le circuit est ouvert (pannes du service)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute(){
        return GatewayRouterFunctions.route("fallbackRoute")
                .POST("/fallbackRoute",
                        request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Booking Service is down"))
                .build();
    }
}
