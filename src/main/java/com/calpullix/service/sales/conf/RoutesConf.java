package com.calpullix.service.sales.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.sales.handler.SalesHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-retrieve-sales}")
	private String pathRetrieveSales;
	
	
		
	@Bean
	public RouterFunction<ServerResponse> routesLogin(SalesHandler purchaseOrderHandler) {
		return route(POST(pathRetrieveSales), purchaseOrderHandler::getSales);
	}
	
}
