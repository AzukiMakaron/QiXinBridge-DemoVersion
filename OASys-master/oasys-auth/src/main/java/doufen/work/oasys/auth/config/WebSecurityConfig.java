package doufen.work.oasys.auth.config;

import doufen.work.oasys.auth.client.UserClient;
import doufen.work.oasys.auth.entity.Role;
import doufen.work.oasys.auth.entity.User;
import doufen.work.oasys.common.entity.ResultStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Security配置
 *
 * @author doufen
 * @since 2023/9/9
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserClient userClient;

    public WebSecurityConfig(UserClient userClient) {
        this.userClient = userClient;
    }

    @Bean
    public PasswordEncoder passwordEncoderFactory() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBeanFactory() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsServiceFactory() {
        return username -> {
            User user = userClient.queryUser(username).getData();
            if (user == null) {
                throw new UsernameNotFoundException(ResultStatus.USER_NOT_FOUND.getMessage());
            }
            List<Role> roles = userClient.listRoleOfUser(username).getData();
            List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            user.setAuthorities(authorities);
            return user;
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize
                .anyRequest().permitAll())
                .csrf().disable();
    }

}