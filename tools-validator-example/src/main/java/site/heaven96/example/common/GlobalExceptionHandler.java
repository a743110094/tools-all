package site.heaven96.example.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.heaven96.example.result.Result;
import site.heaven96.validate.common.exception.H4nBeforeValidateCheckException;
import site.heaven96.validate.common.exception.H4nValidateNotPassException;

/**
 * 全局异常处理程序
 *
 * @author Heaven96
 * @date 2021/10/15
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * H4n参数验证不通过 异常
     * 捕获  MethodArgumentNotValidException 异常 参数验证异常
     *
     * @param e exception
     * @return 响应结果
     */
    @ExceptionHandler(H4nValidateNotPassException.class)
    public Result h4nValidateNotPassException(final H4nValidateNotPassException e) {
        final String defaultMessage = e.getMessage();
        return new Result().failed(HttpStatus.PRECONDITION_FAILED, "H4n参数验证失败：" + defaultMessage);
    }


    /**
     * H4n参数验证前的预检查异常
     * 捕获  MethodArgumentNotValidException 异常 参数验证异常
     *
     * @param e exception
     * @return 响应结果
     */
    @ExceptionHandler(H4nBeforeValidateCheckException.class)
    public Result h4nBeforeValidateCheckException(final H4nBeforeValidateCheckException e) {
        final String defaultMessage = e.getMessage();
        return new Result().failed(HttpStatus.PRECONDITION_FAILED, "H4n参数验证前的预检查异常：" + defaultMessage);
    }

    /**
     * 方法参数不是有效
     * 捕获  MethodArgumentNotValidException 异常 参数验证异常
     *
     * @param e exception
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException e) {
        final String defaultMessage = e.getGlobalError().getDefaultMessage();
        return new Result().failed(HttpStatus.PRECONDITION_FAILED, "请求参数验证失败：" + defaultMessage);
    }

    /**
     * 异常处理程序 500 服务器内部错误
     *
     * @param e exception
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(final Exception e) {
        final String message = e.getMessage();
        e.printStackTrace();
        return new Result().failed(HttpStatus.INTERNAL_SERVER_ERROR, "其他异常：" + message);
    }

}
