package doufen.work.oasys.common.handler;

import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import doufen.work.oasys.common.exception.BusinessException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author doufen
 * @date 2023/10/1
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultStatus businessExceptionHandler(BusinessException be) {
        return be.getStatus();
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> illegalArgumentExceptionHandler(Exception e) {
        return Result.of(ResultStatus.ARGUMENT_NOT_VALID, e.getMessage());
    }

    /**
     * 处理参数赋值异常
     */
    @ExceptionHandler(BindException.class)
    public Result<List<String>> bindExceptionHandler(BindException be) {
        return Result.of(ResultStatus.ARGUMENT_NOT_VALID, be.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
    }

    /**
     * 处理无效参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<List<String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me) {
        return Result.of(ResultStatus.ARGUMENT_NOT_VALID, me.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
    }

}
