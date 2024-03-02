package doufen.work.oasys.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * Oauth2.0授权服务器配置类
 *
 * @author doufen
 * @since 2023/9/9
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig {

    @Value("#{'${security.oauth2.authorization.jwt.key-store}'.substring(10)}") //从classpath：往后截取字符串
    private String keyLocation; //JWT密钥的存储位置
    @Value("${security.oauth2.authorization.jwt.key-store-password}")
    private String keyPassword; //JWT秘钥存储密码
    @Value("${security.oauth2.authorization.jwt.key-alias}")
    private String keyAlias; //JWT秘钥别名

    @Bean
    public KeyPair keyPairFactory() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyLocation), keyPassword.toCharArray());
        return keyStoreKeyFactory.getKeyPair(keyAlias, keyPassword.toCharArray());
    }

}
