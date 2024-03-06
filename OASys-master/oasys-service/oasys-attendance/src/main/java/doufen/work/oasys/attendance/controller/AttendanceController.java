package doufen.work.oasys.attendance.controller;

import doufen.work.oasys.attendance.entity.Attendance;
import doufen.work.oasys.attendance.service.AttendanceService;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AttendanceService attendanceService;

    /**
     * 查看考勤信息
     * @param userId
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GetMapping
    public Result<List<Attendance>> listAttendance(@RequestParam Long userId, @RequestParam Integer year,
                                                   @RequestParam Integer month, Integer day) {
        return Result.of(ResultStatus.SUCCESS, attendanceService.listByUserIdAndClockDate(userId, year, month, day));
    }

    /**
     * 签到
     * @param userId
     * @return
     */
    @PostMapping
    public Result<Attendance> clockIn(@RequestParam Long userId) {
        return Result.of(ResultStatus.SUCCESS, attendanceService.create(userId));
    }

    /**
     * 签退
     * @param id
     * @return
     */
    @PutMapping
    public Result<Attendance> clockOut(@RequestParam Long id) {
        return Result.of(ResultStatus.SUCCESS, attendanceService.update(id));
    }

}