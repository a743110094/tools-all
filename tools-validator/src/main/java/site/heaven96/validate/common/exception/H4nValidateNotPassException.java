package site.heaven96.validate.common.exception;

/**
 * H4N验证未通过异常
 *
 * @author Heaven96
 * @date 2021/10/09
 */
public class H4nValidateNotPassException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public H4nValidateNotPassException() {
    }

    public H4nValidateNotPassException(String message) {
        super(message);
    }
}
