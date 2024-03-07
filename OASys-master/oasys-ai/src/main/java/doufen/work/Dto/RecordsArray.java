package doufen.work.Dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doufen
 * @date 2024/3/7
 */
public class RecordsArray {
    /**
     * true 锁定用户
     * false 未锁
     */
    private boolean lock;
    private int status;
    /**
     * 数组最大容量，
     */
    private final int arrayMaxSize;
    private int front;
    private int rear;
    private final InteractMsg[] interactMsgsArr;
    public RecordsArray(int maxInteractCount){
        this.arrayMaxSize=maxInteractCount+1;
        this.interactMsgsArr=new InteractMsg[arrayMaxSize];
    }
    /**
     * 判断队列是否满
     */
    public boolean isFull(){
        return(rear+1)%arrayMaxSize==front;
    }
    /**
     * 添加交互消息
     */
    public void addInteractMsg(InteractMsg interactMsg){
        if(isFull()){
            //队满
            front=(front+1)%arrayMaxSize;
        }
        //队列未满，无需将最旧的数据出队列，即 front 无需移动
        // 无论是否队满，新的交互信息还是要添加的，因此 rear 处设置为最新的交互消息，并向后移动
        interactMsgsArr[rear]=interactMsg;
        rear=(rear+1)%arrayMaxSize;

    }
    /**
     * 获取存储的交互消息
     */
    public List<MsgDTO> getAllInteractMsg(){
        int realSize=size();
        ArrayList<MsgDTO> msgList = new ArrayList<>(realSize * 2);
        for(int i=front;i<front+realSize;i++){
            InteractMsg interactMsg=interactMsgsArr[i%arrayMaxSize];
            if(interactMsg.getUserMsg()!=null){
                msgList.add(interactMsg.getUserMsg());
            }
            if(interactMsg.getAssistantMsg()!=null){
                msgList.add(interactMsg.getAssistantMsg());
            }
        }
        return msgList;
    }

    private int size() {
        return (rear+arrayMaxSize-front)%arrayMaxSize;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
