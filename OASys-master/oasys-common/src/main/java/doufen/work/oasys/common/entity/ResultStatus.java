package doufen.work.oasys.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 响应状态枚举类
 *
 * @author doufen
 * @date 2023/10/2
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultStatus {

    /**
     * 响应状态
     */
        SUCCESS("0000", "操作成功"),
    UNAUTHORIZED("1000", "未授权"),
    FORBIDDEN("1001", "权限不足"),
    TOKEN_IS_INVALID("1002", "Token无效"),
    ARGUMENT_NOT_VALID("2000", "参数无效"),
    USER_NOT_FOUND("3000", "用户不存在"),
    USERNAME_ALREADY_EXISTS("3001", "用户名已存在"),
    TODAY_HAS_CLOCKED_IN("5000", "今日已经签到过"),
    TODAY_HAS_CLOCKED_OUT("5001", "今日已经签签退过"),
    TODAY_HAS_NOT_CLOCKED_IN("5002", "今日还没有签到"),
    FILE_NOT_FOUND("6001", "文件不存在"),
    USER_ARE_TOO_MORE("6002","当前AI使用人数过多,请您排队等候"+",目前使用人数:2人"),
    QUESTIONS_ARE_NOT_AVAILABLE("6003","无效问题,请重新输入"),
    AI_SYSTEM_INNO_PROBLEM("6004","系统内部错误,请联系蚊朔tEam技术部的部长"),
    AI_ARE_NO_RESPONSE("6005","AI响应超时，请联系蚊朔tEam技术部的部长");



    /**
     * 状态码
     */
    private final String code;
    /**
     * 状态信息
     */
    private final String message;

    ResultStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}