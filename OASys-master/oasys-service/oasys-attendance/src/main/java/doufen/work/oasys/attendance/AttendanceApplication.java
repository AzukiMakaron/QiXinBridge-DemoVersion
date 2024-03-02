package doufen.work.oasys.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 考勤服务启动类
 *
 * @author doufen
 * @since 2023/11/15
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);
    }

}
