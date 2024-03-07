package doufen.work.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author doufen
 * @date 2024/3/7
 */
@Configuration
@ConfigurationProperties(prefix = "xhai") //调用星火AI接口
@Data
public class AIConfig {
    private String hostUrl;
    private String domain;
    private Float temperature;
    private Integer maxTokens;
    private Integer maxResponseTime;
    private String appId;
    private String apiKey;
    private String apiSecret;
}
