package cn.linter.oasys.attendance.controller;

import cn.linter.oasys.attendance.entity.Attendance;
import cn.linter.oasys.attendance.service.AttendanceService;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤控制器
 *
 * @author doufen
 * @since 2023/11/15
 */
@RestController
@RequestMapping("attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public Result<List<Attendance>> listAttendance(@RequestParam Long userId, @RequestParam Integer year,
                                                   @RequestParam Integer month, Integer day) {
        return Result.of(ResultStatus.SUCCESS, attendanceService.listByUserIdAndClockDate(userId, year, month, day));
    }

    @PostMapping
    public Result<Attendance> clockIn(@RequestParam Long userId) {
        return Result.of(ResultStatus.SUCCESS, attendanceService.create(userId));
    }

    @PutMapping
    public Result<Attendance> clockOut(@RequestParam Long id) {
        return Result.of(ResultStatus.SUCCESS, attendanceService.update(id));
    }

}