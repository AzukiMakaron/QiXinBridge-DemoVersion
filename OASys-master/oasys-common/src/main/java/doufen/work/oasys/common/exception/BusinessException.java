package doufen.work.oasys.common.exception;

import doufen.work.oasys.common.entity.ResultStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 业务异常类
 *
 * @author doufen
 * @date 2023/10/1
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    /**
     * 状态
     */
    private ResultStatus status;

    public BusinessException(ResultStatus status) {
        this.status = status;
    }

}
