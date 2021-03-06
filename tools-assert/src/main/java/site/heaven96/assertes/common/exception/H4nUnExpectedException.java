package site.heaven96.assertes.common.exception;

/**
 * H4N意外异常
 *
 * @author Heaven96
 * @date 2021/10/17
 */
public class H4nUnExpectedException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public H4nUnExpectedException() {
    }

    public H4nUnExpectedException(String message) {
        super(message);
    }
}
