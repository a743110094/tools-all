package site.heaven96.validate.util;

import cn.beecp.BeeDataSource;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.bee.BeeDSFactory;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.assertes.util.AssertUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * h4n数据验证SQL执行工具
 *
 * @author Heaven96
 * @date 2021/10/06
 */
@Slf4j(topic = "【h4n数据验证SQL执行工具】")
public class SqlExecutor{

    private static final String ERR_MSG_TEMPLATE = "\n===>执行{}操作发生错误：\n[sql] -> :{}\n[args] - > :{}\n[错误提示] -> :{}";

    private static DataSource ds ;
    /**
     * 计数
     *
     * @param sql SQL
     * @return long
     */
    public static long count(String sql, Object... args) throws SQLException {
            long count = getDb().count(StrUtil.format(sql, args));
            return ObjectUtil.isNull(count) ? 0 : count;
    }

    /**
     * 选择数字 发生错误返回null
     *
     * @param sql  SQL
     * @param args 参数
     * @return {@code Number}
     */
    public static Number selectNumber(String sql, Object... args) {
        try {return getDb().queryNumber(sql, args);
        } catch (Exception e) {
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
            return getDb().queryString(sql, args);
        } catch (Exception e) {
            log.error(ERR_MSG_TEMPLATE, Thread.currentThread().getStackTrace()[1].getMethodName(), sql, args, e.getMessage());
            e.printStackTrace();
            return "";
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
            return getDb().query(sql, String.class, args);
        } catch (Exception e) {
            log.error(ERR_MSG_TEMPLATE, Thread.currentThread().getStackTrace()[1].getMethodName(), sql, args, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static Db getDb(){
        return Db.use();
    }
}
