package doufen.work.oasys.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Client配置类
 * 设置负载均衡
 * @author doufen
 * @since 2023/10/15
 */
@Configuration
public class ClientConfig {
    /**
     * 当这个loadBalancedWebClientBuilder方法被调用并注入到其他bean中时，
     * 由于@LoadBalanced注解的存在，Spring Cloud会拦截这个bean的创建，
     * 并确保它返回的WebClient实例是支持负载均衡的。
     * 这意味着，当你使用这个WebClient实例发送请求时，请求会被自动路由到一个可用的服务实例上，从而实现负载均衡。
     * @return
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

}
