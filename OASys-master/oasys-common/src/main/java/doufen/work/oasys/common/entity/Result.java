package doufen.work.oasys.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 响应实体类
 *
 * @author doufen
 * @since 2023/10/2
 */
@Data
@ToString
@EqualsAndHashCode
public class Result<T> {

    /**
     * 状态码
     */
    private String code;
    /**
     * 状态信息
     */
    private String message;
    /**
     * 数据(可以是任意定义类型)
     */
    private T data;

    /**
     * 返回响应
     *
     * @param status 状态
     * @param data   数据
     * @return 响应
     */
    public static <T> Result<T> of(ResultStatus status, T data) {
        Result<T> result = new Result<>();
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
        result.setData(data);
        return result;
    }

}