package com.levraijmk.apigatewapi.route;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class InventoryServiceRoutes {


    @Bean
    public RouterFunction<ServerResponse> inventoryRoutes() {

        return GatewayRouterFunctions.route("inventory-service")

                .route(
                        RequestPredicates.path("/api/v1/inventory/**"),
                        HandlerFunctions.http()
                )

                .before(BeforeFilterFunctions.uri("http://localhost:9090/api/v1/inventory/**"))

                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceApiDocs(){
        return GatewayRouterFunctions.route("inventory-service-api-docs")
                .route(RequestPredicates.path("/docs/inventoryservice/v3/api-docs"),
                        HandlerFunctions.http()
                )
                .before(BeforeFilterFunctions.uri("http://localhost:9090"))
                .filter(setPath("/v3/api-docs"))
                .build();

    }


}
