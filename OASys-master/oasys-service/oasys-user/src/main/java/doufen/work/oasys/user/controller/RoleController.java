package doufen.work.oasys.user.controller;

import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import doufen.work.oasys.user.entity.Permission;
import doufen.work.oasys.user.entity.Role;
import doufen.work.oasys.user.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author doufen
 * @since 2024/1/2
 */
@RestController
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Result<List<Role>> listRole() {
        return Result.of(ResultStatus.SUCCESS, roleService.list());
    }

    @PostMapping
    public Result<Role> createRole(@RequestBody @Validated({Role.Create.class}) Role role) {
        return Result.of(ResultStatus.SUCCESS, roleService.create(role));
    }

    @PutMapping
    public Result<Role> updateRole(@RequestBody @Validated({Role.Update.class}) Role role) {
        return Result.of(ResultStatus.SUCCESS, roleService.update(role));
    }

    @DeleteMapping("{id}")
    public ResultStatus deleteRole(@PathVariable Integer id) {
        roleService.delete(id);
        return ResultStatus.SUCCESS;
    }

    @GetMapping("{id}/permissions")
    public Result<List<Permission>> listPermission(@PathVariable Integer id, @RequestParam(defaultValue = "false") boolean treeMode) {
        return Result.of(ResultStatus.SUCCESS, roleService.listPermission(id, treeMode));
    }

    @PostMapping("{id}/permissions")
    public ResultStatus createPermission(@PathVariable Integer id, @RequestBody List<Permission> permissions) {
        roleService.createPermission(id, permissions);
        return ResultStatus.SUCCESS;
    }

    @PutMapping("{id}/permissions")
    public ResultStatus updatePermission(@PathVariable Integer id, @RequestBody List<Permission> permissions) {
        roleService.updatePermission(id, permissions);
        return ResultStatus.SUCCESS;
    }

}