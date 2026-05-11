package com.levraijmk.apigatewapi.route;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;



@Configuration
public class BookingServiceRoutes {

    @Bean
    public RouterFunction<ServerResponse> bookingRoutes(){
        return GatewayRouterFunctions.route("booking-service")//id
                .route(
                        RequestPredicates.POST("/api/v1/booking"),//ce que le client va appler
                        HandlerFunctions.http()
                        )
                .before(BeforeFilterFunctions.uri("http://localhost:9091/api/v1/booking")) // la redirection que la gateway va faire
                .build();
    }
}
