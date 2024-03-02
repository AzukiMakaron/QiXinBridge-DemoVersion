package doufen.work.oasys.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 搜索服务启动类
 *
 * @author doufen
 * @since 2023/12/26
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
