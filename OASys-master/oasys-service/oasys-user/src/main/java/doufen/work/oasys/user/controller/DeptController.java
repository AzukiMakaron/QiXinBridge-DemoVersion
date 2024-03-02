package doufen.work.oasys.user.controller;

import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import doufen.work.oasys.user.entity.Dept;
import doufen.work.oasys.user.service.DeptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 *
 * @author doufen
 * @since 2024/1/1
 */
@RestController
@RequestMapping("depts")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping
    public Result<List<Dept>> listDept() {
        return Result.of(ResultStatus.SUCCESS, deptService.list());
    }

    @PostMapping
    public Result<Dept> createDept(@RequestBody @Validated({Dept.Create.class}) Dept dept) {
        return Result.of(ResultStatus.SUCCESS, deptService.create(dept));
    }

    @PutMapping
    public Result<Dept> updateDept(@RequestBody @Validated({Dept.Update.class}) Dept dept) {
        return Result.of(ResultStatus.SUCCESS, deptService.update(dept));
    }

    @DeleteMapping("{id}")
    public ResultStatus deleteDept(@PathVariable Integer id) {
        deptService.delete(id);
        return ResultStatus.SUCCESS;
    }

}