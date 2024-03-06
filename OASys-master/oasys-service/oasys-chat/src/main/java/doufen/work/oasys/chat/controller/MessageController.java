package doufen.work.oasys.chat.controller;

import doufen.work.oasys.chat.entity.Message;
import doufen.work.oasys.chat.service.MessageService;
import doufen.work.oasys.common.entity.Page;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 聊天消息控制器
 *
 * @author doufen
 * @since 2023/12/4
 */
@RestController
@RequestMapping("messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 获取聊天框信息
     * @param start
     * @param end
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result<Page<Message>> listMessages(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                              @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        org.springframework.data.domain.Page<Message> messages = messageService.listMessage(start, end, pageNumber, pageSize);
        return Result.of(ResultStatus.SUCCESS, Page.of(messages.getContent(), messages.getTotalElements()));
    }

}
