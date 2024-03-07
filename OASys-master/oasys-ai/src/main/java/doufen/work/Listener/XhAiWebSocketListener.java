package doufen.work.Listener;

import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocketListener;

/**
 *
 * @author doufen
 * @date 2024/3/7
 */
@Slf4j
public class XhAiWebSocketListener extends WebSocketListener {
    private StringBuilder answer=new StringBuilder();
    private boolean wsCloseFlag=false;

}
