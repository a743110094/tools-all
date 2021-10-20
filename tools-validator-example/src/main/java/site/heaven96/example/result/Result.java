package site.heaven96.example.result;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Date;

/**
 * 统一封装 Restful 结果
 *
 * @author lgw3488
 * @date 2021/10/15
 */
@Data
public class Result<T> {
    /**
     * 业务执行状态
     */
    private boolean flag;

    /**
     * HTTP状态
     */
    private int status = HttpStatus.OK.value();

    /**
     * 消息
     */
    private String message = "No Message.";

    /**
     * 时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timestamp = DateUtil.date().toSqlDate();

    /**
     * 数据
     */
    private T data = null;

    /**
     * 用户令牌
     */
    private String userToken;

    /**
     * 跟踪ID
     */
    private String traceId;


    /**
     * 成功
     *
     * @param data 数据
     * @param msg  消息
     * @return {@code Result<T>}
     */
    public Result<T> success(String msg, T data) {
        this.flag = true;
        this.message = StrUtil.isBlank(msg) ? this.message : msg;
        this.data = data;
        return this;
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@code Result<T>}
     */
    public Result<T> success(T data) {
        return success(null, data);
    }

    /**
     * 失败
     *
     * @param httpStatus HTTP状态
     * @param msg        味精
     * @return {@code Result<T>}
     */
    public Result<T> failed(HttpStatus httpStatus, String msg) {
        return failed(httpStatus, msg, null);
    }

    /**
     * 失败
     *
     * @param data 数据
     * @return {@code Result<T>}
     */
    public Result<T> failed(HttpStatus httpStatus, String msg, T data) {
        this.flag = false;
        this.status = httpStatus.value();
        this.message = msg;
        this.data = data;
        return this;
    }

}