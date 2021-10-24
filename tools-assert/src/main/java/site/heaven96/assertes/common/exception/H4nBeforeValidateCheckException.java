package site.heaven96.assertes.common.exception;

/**
 * 执行验证前的检查发生异常
 *
 * @author Heaven96
 * @date 2021/10/09
 */
public class H4nBeforeValidateCheckException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public H4nBeforeValidateCheckException() {
    }

    public H4nBeforeValidateCheckException(String message) {
        super(message);
    }
}
