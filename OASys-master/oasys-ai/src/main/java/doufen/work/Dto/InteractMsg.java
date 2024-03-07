package doufen.work.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author doufen
 * @date 2024/3/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractMsg {
    private MsgDTO userMsg;
    private MsgDTO assistantMsg;
}
