package doufen.work.oasys.announcement.controller;

import doufen.work.oasys.announcement.entity.Announcement;
import doufen.work.oasys.announcement.service.AnnouncementService;
import doufen.work.oasys.common.entity.Page;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公告控制器
 *
 * @author doufen
 * @since 2023/11/1
 */
@RestController
@RequestMapping("announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

//    public AnnouncementController(AnnouncementService announcementService) {
//        this.announcementService = announcementService;
//    }

    @GetMapping
    public Result<Page<Announcement>> listAnnouncement(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Announcement> pageInfo = announcementService.list(pageNumber, pageSize);
        return Result.of(ResultStatus.SUCCESS, Page.of(pageInfo.getList(), pageInfo.getTotal()));
    }

    @PostMapping
    public Result<Announcement> createAnnouncement(@RequestBody @Validated({Announcement.Create.class}) Announcement announcement) {
        return Result.of(ResultStatus.SUCCESS, announcementService.create(announcement));
    }

    @PutMapping
    public Result<Announcement> updateAnnouncement(@RequestBody @Validated({Announcement.Update.class}) Announcement announcement) {
        Announcement updatedAnnouncement = announcementService.update(announcement);
        return Result.of(ResultStatus.SUCCESS, updatedAnnouncement);
    }

    @DeleteMapping("{id}")
    public Result<String> deleteAnnouncement(@PathVariable Long id) {
        announcementService.delete(id);
        return Result.of(ResultStatus.SUCCESS, "删除成功！");
    }

}