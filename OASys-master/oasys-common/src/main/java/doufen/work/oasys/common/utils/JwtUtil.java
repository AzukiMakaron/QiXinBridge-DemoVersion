package doufen.work.oasys.common.utils;

import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

/**
 * JWT工具类
 * 开启日志记录！！
 * @author doufen
 * @since 2023/10/2
 */
@Slf4j
public class JwtUtil {

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "bearer ";

    /**
     * 从Token获取用户名
     *
     * @param token Token
     * @return username
     */
    public static String getUsername(String token) {
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token.replace(TOKEN_PREFIX, "")); //去掉Token前缀
        } catch (ParseException e) {
            log.error("Token获取失败", e);
        }
//        return jwsObject=null?null:(String)jwsObject.getPayload().toJSONObject().get("user_name"); 废弃方案，报错
        return jwsObject != null ? (String) jwsObject.getPayload().toJSONObject().get("user_name") : null;
    }

}
