package com.calpullix.service.sales.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.sales.model.SalesRequestDTO;
import com.calpullix.service.sales.service.SalesService;
import com.calpullix.service.sales.util.AbstractWrapper;
import com.calpullix.service.sales.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SalesHandler {

	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;

	@Autowired
	private SalesService salesService;

	@Autowired
	private ValidationHandler validationHandler;

	@Timed(value = "calpullix.service.sales.metrics", description = "Retrieve sales ")
	public Mono<ServerResponse> getSales(ServerRequest serverRequest) {
		log.info(":: Retrieve Sales Handler {} ", serverRequest);
		return validationHandler.handle(
				input -> input.flatMap(request -> AbstractWrapper.async(() -> salesService.getSales(request)))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, SalesRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

}
