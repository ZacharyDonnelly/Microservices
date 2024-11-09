package com.zachdonnelly.order.config;

import com.zachdonnelly.order.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;

@Configuration
public class ClientConfig {

    @Bean
    Client productServiceClient() {
        WebClient client = WebClient
                .builder()
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, resp ->
                        resp.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
                )
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, resp ->
                        Mono.just(new RuntimeException(resp.statusCode().toString()))
                )
                .baseUrl("http://localhost:8081")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(Client.class);
    }
}
