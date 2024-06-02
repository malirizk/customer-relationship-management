package org.example.telephonyservice.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration
public class CallLogConfig {

    @Value("${call-log.url}")
    private String callLogBaseUrl;
    @Value("${call-log.timeout-in-millis}")
    private int timeout;

    @Bean
    public WebClient callLogApiClient() {
        final HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(timeout))
                                        .addHandlerLast(new WriteTimeoutHandler(timeout))));

        return WebClient.builder()
                .baseUrl(callLogBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
