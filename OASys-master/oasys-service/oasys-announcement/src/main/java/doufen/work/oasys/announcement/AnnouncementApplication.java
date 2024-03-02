package doufen.work.oasys.announcement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author doufen
 * @since 2023/11/1
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AnnouncementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnouncementApplication.class, args);
    }

}
