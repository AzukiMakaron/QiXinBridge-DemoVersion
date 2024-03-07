package doufen.work.Listener;

import com.alibaba.fastjson.JSONObject;
import doufen.work.Dto.MsgDTO;
import doufen.work.Dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * WebSocket协议通用鉴权URL生成
 * @author doufen
 * @date 2024/3/7
 */
@Slf4j
public class XhAiWebSocketListener extends WebSocketListener {
    private StringBuilder answer=new StringBuilder();
    private boolean wsCloseFlag=false;
    public StringBuilder getAnswer(){
        return answer;
    }
    public boolean isWsCloseFlag(){
        return wsCloseFlag;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket,response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        //将大模型回复的数据转换为object对象形式
        ResponseDTO responseDTO= JSONObject.parseObject(text, ResponseDTO.class);
        if(responseDTO.getHeader().getCode()!=0){
            //如果状态码不为0则是错误的
            log.error("发生错误，错误码为：" + responseDTO.getHeader().getCode() + "; " + "信息：" + responseDTO.getHeader().getMessage());
            //设置回答
            this.answer=new StringBuilder("小豆当前响应错误哦，请稍后再试");
            wsCloseFlag=true;//关闭
            return;
        }
        for (MsgDTO msgDTO : responseDTO.getPayload().getChoices().getText()) {
            this.answer.append(msgDTO.getContent());
        }
        //会话状态，取值为[0,1,2]；0代表首次结果；1代表中间结果；2代表最后一个结果
        if(2==responseDTO.getHeader().getStatus()){
            wsCloseFlag=true;
        }

    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }
}
