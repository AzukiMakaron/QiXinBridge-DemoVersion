package doufen.work.oasys.auth.controller;

import doufen.work.oasys.auth.client.UserClient;
import doufen.work.oasys.auth.entity.Permission;
import doufen.work.oasys.auth.entity.User;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制器
 *
 * @author doufen
 * @since 2023/10/1
 */
@RestController
@RequestMapping("oauth")
public class UserController {

    private final UserClient userClient;

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("user")
    public Result<User> getUser(@RequestHeader("Authorization") String token) {
        Result<User> result = userClient.queryUser(JwtUtil.getUsername(token));
        result.getData().setPassword(null);
        return result;
    }

    @GetMapping("user/permissions")
    public Result<List<Permission>> getPermissionsOfUser(@RequestHeader("Authorization") String token) {
        return userClient.listPermissionOfUser(JwtUtil.getUsername(token), true);
    }

}
