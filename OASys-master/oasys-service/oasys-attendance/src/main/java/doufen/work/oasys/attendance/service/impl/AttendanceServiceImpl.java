package doufen.work.oasys.attendance.service.impl;

import doufen.work.oasys.attendance.dao.AttendanceDao;
import doufen.work.oasys.attendance.entity.Attendance;
import doufen.work.oasys.attendance.service.AttendanceService;
import doufen.work.oasys.common.entity.ResultStatus;
import doufen.work.oasys.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 考勤服务实现类
 *
 * @author doufen
 * @since 2023/11/15
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    /**
     * 列出用户签到日期和签退日期
     * @param userId 用户ID
     * @param year   年
     * @param month  月
     * @param day    日
     * @return
     */
    @Override
    public List<Attendance> listByUserIdAndClockDate(Long userId, Integer year, Integer month, Integer day) {
        return attendanceDao.listByUserIdAndClockDate(userId, year, month, day);
    }

    /**
     * 签到
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    @Override
    public Attendance create(Long userId) throws BusinessException {
        LocalDateTime nowDateTime = LocalDateTime.now();
        int year = nowDateTime.getYear();
        int month = nowDateTime.getMonthValue();
        int day = nowDateTime.getDayOfMonth();
        if (attendanceDao.listByUserIdAndClockDate(userId, year, month, day).size() > 0) {
            throw new BusinessException(ResultStatus.TODAY_HAS_CLOCKED_IN);
        }
        Attendance attendance = new Attendance();
        attendance.setUserId(userId);
        String workingHoursStart = attendanceDao.selectSettingValueByName("working_hours_start");
        Duration duration = Duration.between(LocalTime.parse(workingHoursStart), LocalTime.now());
        long lateMinutes = duration.toMinutes();
        if (lateMinutes > 0) {
            attendance.setComeLateMinutes((int) lateMinutes);
        }
        attendance.setClockDate(LocalDate.now());
        attendance.setClockInTime(LocalTime.now());
        attendanceDao.insert(attendance);
        return attendance;
    }

    @Override
    public Attendance update(Long id) throws BusinessException {
        Attendance attendance = attendanceDao.selectById(id);
        if (attendance == null) {
            throw new BusinessException(ResultStatus.TODAY_HAS_NOT_CLOCKED_IN);
        }
        if (attendance.getClockOutTime() != null) {
            throw new BusinessException(ResultStatus.TODAY_HAS_CLOCKED_OUT);
        }
        String workingHoursEnd = attendanceDao.selectSettingValueByName("working_hours_end");
        Duration duration = Duration.between(LocalTime.now(), LocalTime.parse(workingHoursEnd));
        long earlyMinutes = duration.toMinutes();
        if (earlyMinutes > 0) {
            attendance.setLeaveEarlyMinutes((int) earlyMinutes);
        }
        attendance.setClockOutTime(LocalTime.now());
        attendanceDao.updateById(attendance);
        return attendanceDao.selectById(attendance.getId());
    }

}