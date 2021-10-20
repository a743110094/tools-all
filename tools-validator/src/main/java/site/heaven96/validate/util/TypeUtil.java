package site.heaven96.validate.util;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.AutoID;
import org.beetl.sql.annotation.entity.SeqID;
import org.beetl.sql.annotation.entity.Table;
import site.heaven96.validate.common.annotation.H4TbId;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类操作Util
 *
 * @author lgw3488
 * @date 2021/10/11
 */
@Slf4j(topic = "TypeUtil")
public class TypeUtil {

    public static final String K_SCHEMA = "schema";
    public static final String K_TABLE_NAME = "tableName";
    /**
     * 类型类包含注释 入口日志消息
     */
    public static final String TYPE_CLASS_CONTAINS_ANNOTATION_ENTRANCE_LOG_MSG = "\n===> 开始判断类[{}]是否包含注解[{}]";

    /**
     * 获取实体获取 【表名称】
     * 通过试错 尝试找到正确的ORM框架 或者根据类名来转下划线 从而确定  表名
     *
     * @param clazz
     * @return
     */
    public static Map<String, String> getEntitySchemaAndTable(Class<?> clazz) {
        Map<String, String> map = new ConcurrentHashMap<>();
        String schemaName = "";
        String tableName = "";
        //-----------------  先看声明的表名   含Mybatis Plus 和 BeetlSql两个框架
        try {
            //MybatisPlus
            TableName annotation = clazz.getAnnotation(TableName.class);
            tableName = annotation.value().toUpperCase(Locale.ROOT);
            schemaName = annotation.schema().toUpperCase(Locale.ROOT);
        } catch (Exception e) {
            //Ignore
        }

        if (StrUtil.isBlank(tableName)) {
            try {
                //BeetlSql
                Table annotation = clazz.getAnnotation(Table.class);
                String tableNameAndSchema = annotation.name().toUpperCase(Locale.ROOT);
                String[] split = tableNameAndSchema.split("\\.");
                if (ArrayUtil.length(split) == 1) {
                    tableName = split[0];
                }
                if (ArrayUtil.length(split) == 2) {
                    schemaName = split[0];
                    tableName = split[1];
                }
            } catch (Exception e) {
                //Ignore
            }
        }
        //-----------------  先看声明的表名

        //-----------------  无声明 则转驼峰为下划线
        if (StrUtil.isBlank(tableName)) {
            try {
                //转驼峰
                tableName = StrUtil.toUnderlineCase(clazz.getSimpleName()).toUpperCase(Locale.ROOT);
            } catch (Exception e) {
                //Ignore
            }
        }
        //-----------------  无声明 则转驼峰为下划线


        map.put(K_TABLE_NAME, tableName);
        map.put(K_SCHEMA, schemaName);
        return map;
    }

    /**
     * 获取 实体类 主键的 【字段名】
     *
     * @param clazz 克拉兹
     * @return {@code Set<Map<String, String>>}
     */
    public static String getEntityKeyColumn(Class<?> clazz) {
        Set<Map<String, String>> tbIds = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableId.class)) {
                //Mybatis Plus
                TableId declaredAnnotation = field.getDeclaredAnnotation(TableId.class);
                //表字段名称:可能来自@TableId或者@TableField
                String columnName = declaredAnnotation.value();
                if (StrUtil.isBlank(columnName)) {
                    if (field.isAnnotationPresent(TableField.class)) {
                        TableField tableField = field.getDeclaredAnnotation(TableField.class);
                        String tableFieldValue = tableField.value();
                        columnName = tableFieldValue;
                    }
                }
                return StrUtil.isBlank(columnName) ? StrUtil.toUnderlineCase(field.getName()) : columnName;

            }
        }
        return "";
    }

    /**
     * 获取 实体类 主键的 【别名/Java属性名】
     *
     * @param clazz 克拉兹
     * @return {@code Set<Map<String, String>>}
     */
    public static String getEntityKeyAlias(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();

            if (field.isAnnotationPresent(TableId.class)) {
                //Mybatis Plus
                return field.getName();
            }
            if (field.isAnnotationPresent(AssignID.class) || field.isAnnotationPresent(AutoID.class) || field.isAnnotationPresent(SeqID.class)) {
                //beetlSql
                return field.getName();
            }
            if (field.isAnnotationPresent(H4TbId.class)) {
                //userDefined
                return field.getName();
            }
        }
        return "";
    }

    /**
     * 判断类 的属性 是否包含某个注解
     *
     * @param oClazz o Clazz
     * @param aClazz A Clazz
     * @return boolean
     */
    public static boolean containAnnotation(@NotNull Class<?> oClazz, @NotNull Class<? extends Annotation> aClazz) {
        log.info(TYPE_CLASS_CONTAINS_ANNOTATION_ENTRANCE_LOG_MSG, oClazz.getSimpleName(), aClazz.getSimpleName());
        if (ObjectUtil.isNull(oClazz) || ObjectUtil.isNull(aClazz) || ClassUtil.isJdkClass(oClazz)) {
            return false;
        }
        Field[] oFields = oClazz.getDeclaredFields();
        if (ArrayUtil.isEmpty(oFields)) {
            return false;
        }
        // 判断类中是否包括注解
        return Arrays.stream(oFields).anyMatch(field -> field.isAnnotationPresent(aClazz));
    }
}
