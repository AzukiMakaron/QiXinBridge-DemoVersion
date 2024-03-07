package doufen.work.Component;

import com.alibaba.fastjson.JSONObject;
import doufen.work.Config.AIConfig;
import doufen.work.Dto.MsgDTO;
import doufen.work.Dto.RequestDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author doufen
 * @date 2024/3/7
 */
@Component
@Slf4j
public class XhAiStreamClient {

    @Resource
    private AIConfig aiConfig;
    //最大连接数
    @Value("${xhai.QPS}")
    private Integer connectionTokenCount;
    public static int GET_TOKEN_STATUS=0;
    public static int BACK_TOKEN_STATUS=1;

    public synchronized boolean operateToken(int status){
        if(status==GET_TOKEN_STATUS){
            //获取令牌
            if(connectionTokenCount!=0){
                //说明有人连接，将令牌数减一
                connectionTokenCount-=1;
                return true;
            }else{
                return false;
            }
        }else{
            //放回令牌
            connectionTokenCount+=1;
            return true;
        }
    }
    /**
     * 发送消息
     */
    public WebSocket sendMsg(String uid, List<MsgDTO> msgList, WebSocketListener listener){
        String authUrl=this.getAuthUrl();
        if(authUrl==null){
            return null;
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String url=authUrl.replace("http://","ws://").replace("https://","wss://");
        Request request = new Request.Builder().url(url).build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, listener);
        RequestDTO requestDTO=getRequestParam(uid,msgList);
        webSocket.send(JSONObject.toJSONString(requestDTO));
        return webSocket;
    }

    /**
     * 获得请求参数
     * @param uid
     * @param msgList
     * @return
     */
    public RequestDTO getRequestParam(String uid, List<MsgDTO> msgList) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setHeader(new RequestDTO.HeaderDTO(aiConfig.getAppId(),uid));
        requestDTO.setParameter(new RequestDTO.ParameterDTO(
                new RequestDTO.ParameterDTO.ChatDTO(aiConfig.getDomain(),aiConfig.getTemperature(),aiConfig.getMaxTokens())));
        requestDTO.setPayload(new RequestDTO.PayloadDTO(
                new RequestDTO.MessageDTO(msgList)));
        return  requestDTO;
    }

    /**
     * 生成鉴权方法
     */
    public String getAuthUrl(){
        try {
            URL url = new URL(aiConfig.getHostUrl());
            //时间:date参数生成规则
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date=format.format(new Date());
            //拼接:利用上方的date动态拼接生成字符串tmp，这里以星火url为例，实际使用需要根据具体的请求url替换host和path。
            String tmp="host:"+url.getHost()+"\n"+
                            "date"+date+"\n"+
                                "GET"+url.getPath()+" HTTP/1.1";
            //利用hmac-sha256算法结合APISecret对上一步的tmp签名，获得签名后的摘要spec。
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(aiConfig.getApiSecret().getBytes(StandardCharsets.UTF_8), "hmacsha256");
            mac.init(spec);
            byte[] hexDights= mac.doFinal(date.getBytes(StandardCharsets.UTF_8));
            //将上方的tmp进行base64编码生成signature
            String signature= Base64.getEncoder().encodeToString(hexDights);
            //利用上面生成的signature，拼接下方的字符串生成authorization_origin
            String authorization_origin=String.format(
                    "api_key=\"%s\"," +
                    "algorithm=\"%s\", " +
                    "headers=\"%s\", " +
                    "signature=\"%s\"",
                    aiConfig.getApiKey(), "hmac-sha256", "host date request-line", signature);
            //最后再将上方的authorization_origin进行base64编码,生成最终的authorization
            //将鉴权参数组合成最终的键值对，并urlencode生成最终的握手url。开发者可先根据上面的步骤一步步进行参数校验，确保生成的参数无误。
            HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" +
                            url.getHost() +
                            url.getPath())
                    .newBuilder()
                    .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization_origin.getBytes(StandardCharsets.UTF_8)))
                    .addQueryParameter("date", date)
                    .addQueryParameter("host", url.getHost())
                    .build());
            return httpUrl.toString();
        } catch (Exception e) {
            log.error("鉴权方法中发生错误：" + e.getMessage());
            return null;
        }
    }
}
