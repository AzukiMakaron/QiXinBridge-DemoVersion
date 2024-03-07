package doufen.work.Controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import doufen.work.Component.XhAiStreamClient;
import doufen.work.Config.AIConfig;
import doufen.work.Dto.MsgDTO;
import doufen.work.Listener.XhAiWebSocketListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;

/**
 *
 * @author doufen
 * @date 2024/3/7
 */
@RestController
@RequestMapping("/ai")
@Slf4j
public class AiRequestController {
    @Resource
    private XhAiStreamClient xhAiStreamClient;
    @Resource
    private AIConfig aiConfig;

    /**
     * 发送问题
     * @param question
     * @return
     */
    @GetMapping("/sendQuestion")
    public String sendQuestion(@RequestParam("question") String question){
        if(StrUtil.isBlank(question)){
            return "无效问题,请重新输入";
        }
        if(!xhAiStreamClient.operateToken(XhAiStreamClient.GET_TOKEN_STATUS)){
            return "当前AI使用人数过多,请您排队等候"+",目前使用人数:2人";
        }
        MsgDTO msgDTO = MsgDTO.createUserMsg(question);
        XhAiWebSocketListener listener = new XhAiWebSocketListener();
        WebSocket webSocket = xhAiStreamClient.sendMsg(UUID.randomUUID().toString().substring(0, 10), Collections.singletonList(msgDTO), listener);
        if(webSocket==null){
            //归还令牌
            xhAiStreamClient.operateToken(XhAiStreamClient.BACK_TOKEN_STATUS);
            return"系统内部错误,请联系蚊朔tEam技术部的部长";
        }
        try{
            int count=0;
            int maxCount=aiConfig.getMaxResponseTime()*5;
            while(count<=maxCount){
                Thread.sleep(200);
                if(listener.isWsCloseFlag()){
                    break;
                }
                count++;
            }
            if(count>maxCount){
                return "AI响应超时，请联系蚊朔tEam技术部的部长";
            }
        }catch (InterruptedException  e){
            log.error("错误：" + e.getMessage());
            return "系统内部错误，请联系蚊朔tEam技术部的部长";
        }finally {
            //关闭
            webSocket.close(1000,"");
            //归还令牌
            xhAiStreamClient.operateToken(XhAiStreamClient.BACK_TOKEN_STATUS);
        }
        return question;
    }
}
