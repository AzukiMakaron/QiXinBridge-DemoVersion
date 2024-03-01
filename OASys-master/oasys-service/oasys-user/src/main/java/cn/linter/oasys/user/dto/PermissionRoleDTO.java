package cn.linter.oasys.user.dto;

import cn.linter.oasys.user.entity.Permission;
import cn.linter.oasys.user.entity.Role;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 权限和角色映射数据传输对象
 *
 * @author doufen
 * @since 2024/1/4
 */
@Data
@ToString
public class PermissionRoleDTO {

    private Permission permission;

    private List<Role> roles;

}
