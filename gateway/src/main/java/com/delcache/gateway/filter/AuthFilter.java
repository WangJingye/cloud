package com.delcache.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.delcache.gateway.config.properties.IgnoreWhiteProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


/**
 * 网关鉴权
 *
 * @author ruoyi
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        if (ignoreWhite.getGatewayIgnores().contains(url)) {
            return chain.filter(exchange);
        }
        ServerHttpRequest req = exchange.getRequest();
        String token = req.getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)) {
            return setUnauthorizedResponse(exchange, "未登录");
        }
        if ("OPTIONS".equals(req.getMethodValue())) {
            return setUnauthorizedResponse(exchange, "options");
        }
        URI uri = exchange.getRequest().getURI();
        URI ex = UriComponentsBuilder.fromUri(uri).build(true).toUri();
        ServerHttpRequest request = exchange.getRequest().mutate().uri(ex).build();
//        String method = exchange.getRequest().getMethodValue();
//        if ("POST".equals(method)) {
//            return DataBufferUtils.join(exchange.getRequest().getBody())
//                    .flatMap(dataBuffer -> {
//                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(bytes);
//                        String bodyString = new String(bytes, StandardCharsets.UTF_8);
//                        System.out.println(bodyString);
//                        DataBufferUtils.release(dataBuffer);
//                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
//                            DataBuffer buffer = exchange.getResponse().bufferFactory()
//                                    .wrap(bytes);
//                            return Mono.just(buffer);
//                        });
//                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
//                                exchange.getRequest()) {
//                            @Override
//                            public Flux<DataBuffer> getBody() {
//                                return cachedFlux;
//                            }
//                        };
//                        ServerWebExchange nex = exchange.mutate().request(mutatedRequest).build();
//                        nex.getAttributes().put("POST_BODY", bodyString);
//                        System.out.println(  nex.hashCode());
//                        System.out.println( nex.getAttributes().get("POST_BODY"));
//                        return chain.filter(nex);
//                    });
//        }

        return chain.filter(exchange.mutate().request(request).build());
    }

    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            Map<String, Object> result = new HashMap<>();
            result.put("code", 999);
            result.put("message", msg);
            return bufferFactory.wrap(JSON.toJSONBytes(result));
        }));
    }

    @Override
    public int getOrder() {
        return -200;
    }
}