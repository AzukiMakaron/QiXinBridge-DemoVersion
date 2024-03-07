package doufen.work.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author doufen
 * @date 2024/3/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgDTO {
    private String role;
    private String content;
    private Integer index;
    public static final String ROLE_USER="user";
    public static final String ROLE_ASSISTANT="assistant";

    public static MsgDTO createUserMsg(String content){
        return new MsgDTO(ROLE_USER,content,null);
    }
    public static MsgDTO createAssistantMsg(String content){
        return new MsgDTO(ROLE_ASSISTANT,content,null);
    }

}
