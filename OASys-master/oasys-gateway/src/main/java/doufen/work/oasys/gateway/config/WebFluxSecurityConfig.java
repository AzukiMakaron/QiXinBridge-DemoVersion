package doufen.work.oasys.gateway.config;

import doufen.work.oasys.common.entity.ResultStatus;
import doufen.work.oasys.gateway.manager.AuthorizationManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Security配置
 *
 * @author doufen
 * @since 2023/10/15
 */
@Slf4j
@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorizationManager authorizationManager;

    /**
     * 聊天拦截，生成token拦截，用户认证拦截
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers("/api/chat").permitAll()
                .pathMatchers("/api/oauth/token").permitAll()
                .pathMatchers("/api/oauth/user/**").authenticated()
                .anyExchange().access(authorizationManager)
                .and().exceptionHandling()
                .authenticationEntryPoint((exchange, exception) -> sendRestResponse(exchange,
                        HttpStatus.UNAUTHORIZED, ResultStatus.UNAUTHORIZED)
                )
                .accessDeniedHandler((exchange, exception) -> sendRestResponse(exchange,
                        HttpStatus.FORBIDDEN, ResultStatus.FORBIDDEN)
                )
                .and().csrf().disable()
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(getJwtAuthenticationConverter()))
                        .authenticationEntryPoint((exchange, exception) -> sendRestResponse(exchange,
                                HttpStatus.UNAUTHORIZED, ResultStatus.TOKEN_IS_INVALID)
                        ));
        return http.build();
    }

    /**
     * 获取权限信息，并将权限信息转换为具有响应式的Mono型
     * @return
     */
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> getJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    /**
     * 使用响应式编程，向HTTP发送响应
     * @param exchange WebFLUX核心
     * @param httpStatus
     * @param resultStatus
     * @return
     */
    private Mono<Void> sendRestResponse(ServerWebExchange exchange, HttpStatus httpStatus, ResultStatus resultStatus) {
        ServerHttpResponse httpResponse = exchange.getResponse();
        httpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        httpResponse.setStatusCode(httpStatus);
        String body;
        try {
            body = objectMapper.writeValueAsString(resultStatus);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            log.error("Json转对象过程失败..", e);
        }
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8); //将JSON字符串转换为UTF-8编码的字节数组
        DataBuffer buffer = httpResponse.bufferFactory().wrap(bytes); //转换为DataBuffer型，在响应流中使用
        return httpResponse.writeWith(Mono.just(buffer)); //异步将数据写入http响应中
    }

}