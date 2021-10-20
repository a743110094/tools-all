package site.heaven96.validate.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.sql.SQLException;
import java.util.List;

/**
 * h4n数据验证SQL执行工具
 *
 * @author lgw3488
 * @date 2021/10/06
 */
@Slf4j(topic = "【h4n数据验证SQL执行工具】")
public class SqlExecutor {

    private static final String ERR_MSG_TEMPLATE = "\n===>执行{}操作发生错误：\n[sql] -> :{}\n[args] - > :{}\n[错误提示] -> :{}";

    /**
     * 计数
     *
     * @param sql SQL
     * @return long
     */
    public static long count(String sql, Object... args) {
        try {
            long count = Db.use().count(StrUtil.format(sql, args));
            return ObjectUtil.isNull(count) ? 0 : count;
        } catch (SQLException e) {
            log.error(ERR_MSG_TEMPLATE, Thread.currentThread().getStackTrace()[1].getMethodName(), sql, args, e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 选择数字 发生错误返回null
     *
     * @param sql  SQL
     * @param args 参数
     * @return {@code Number}
     */
    public static Number selectNumber(String sql, Object... args) {
        try {
            return Db.use().queryNumber(sql, args);
        } catch (SQLException e) {
            log.error(ERR_MSG_TEMPLATE, Thread.currentThread().getStackTrace()[1].getMethodName(), sql, args, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 选择字符串 发生错误返回空字符串
     *
     * @param sql  SQL
     * @param args 参数
     * @return {@code String}
     */
    public static String selectStr(String sql, Object... args) {
        try {
            return Db.use().queryString(sql, args);
        } catch (SQLException e) {
            log.error(ERR_MSG_TEMPLATE, Thread.currentThread().getStackTrace()[1].getMethodName(), sql, args, e.getMessage());
            e.printStackTrace();
            return Strings.EMPTY;
        }
    }

    /**
     * 选择多行字符串 发生错误返回空
     *
     * @param sql  SQL
     * @param args 参数
     * @return {@code String}
     */
    public static List<String> selectStrs(String sql, Object... args) {
        try {
            return Db.use().query(sql, String.class, args);
        } catch (SQLException e) {
            log.error(ERR_MSG_TEMPLATE, Thread.currentThread().getStackTrace()[1].getMethodName(), sql, args, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
