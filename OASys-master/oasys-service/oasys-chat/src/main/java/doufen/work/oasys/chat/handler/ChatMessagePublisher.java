package doufen.work.oasys.chat.handler;

import doufen.work.oasys.chat.entity.Message;
import doufen.work.oasys.chat.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 消息发布
 *
 * @author doufen
 * @date 2023/12/6
 */
@Slf4j
@Component
public class ChatMessagePublisher {

    private final MessageRepository messageRepository;
    private final Producer<String> pulsarPublisher;
    private final ObjectMapper objectMapper;

    public ChatMessagePublisher(MessageRepository messageRepository, Producer<String> pulsarPublisher, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.pulsarPublisher = pulsarPublisher;
        this.objectMapper = objectMapper;
    }

    public void publish(Map<String, Object> attributes, Message.Type type, String content) {
        Message message = Message.builder()
                .type(type)
                .content(content)
                .username((String) attributes.get("username"))
                .fullName((String) attributes.get("fullName"))
                .profilePicture((String) attributes.get("profilePicture"))
                .createTime(LocalDateTime.now())
                .build();
        if (message.getType() != Message.Type.SYSTEM) {
            messageRepository.save(message);
        }
        try {
            String messageString = objectMapper.writeValueAsString(message);
            pulsarPublisher.sendAsync(messageString);
        } catch (JsonProcessingException e) {
            log.error("Json处理失败", e);
        }
    }

}
