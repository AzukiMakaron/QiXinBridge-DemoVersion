package doufen.work.oasys.gateway.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * 权限实体DTO
 *
 * @author doufen
 * @since 2023/10/17
 */
@Data
public class PermissionDTO {

    /**
     * 资源路径
     */
    private String resourcePath;
    /**
     * 请求方法
     */
    private HttpMethod requestMethod;

}