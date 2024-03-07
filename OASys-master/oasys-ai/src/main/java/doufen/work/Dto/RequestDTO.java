package doufen.work.Dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author doufen
 * @date 2024/3/7
 */
@NoArgsConstructor
@Data
public class RequestDTO {
    @JsonProperty("header")
    private HeaderDTO header;
    @JsonProperty("paramter")
    private ParameterDTO parameter;
    @JsonProperty("payload")
    private PayloadDTO payload;

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class HeaderDTO {
        @JSONField(name = "app_id")
        private String appId;
        @JSONField(name = "uid")
        private String uid;
    }

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class ParameterDTO {
        private ChatDTO chat;

        @NoArgsConstructor
        @Data
        @AllArgsConstructor
        public static class ChatDTO {
            @JsonProperty("domain")
            private String domain;
            @JsonProperty("temperature")
            private Float temperature;
            @JSONField(name = "max_tokens")
            private Integer maxTokens;
        }
    }

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class PayloadDTO {
        @JsonProperty("message")
        private MessageDTO message;
    }
    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class MessageDTO {
        @JsonProperty("text")
        private List<MsgDTO> text;
    }
}
