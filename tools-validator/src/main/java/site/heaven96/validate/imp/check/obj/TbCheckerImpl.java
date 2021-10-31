package site.heaven96.validate.imp.check.obj;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.beetl.sql.annotation.entity.Column;
import org.beetl.sql.annotation.entity.Table;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.annotation.H4nColumn;
import site.heaven96.validate.common.annotation.H4nTbCheck;
import site.heaven96.validate.common.enums.TbCheck;
import site.heaven96.validate.util.SqlExecutor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 表检查实现类
 *
 * @author heaven96
 * @date 2021/10/31 17:48:12
 */
public class TbCheckerImpl extends AbObjectChecker<H4nTbCheck>{
    /**
     * 检查
     *
     * @param check        检查
     * @param beCheckedObj 检查obj
     * @return boolean
     */
    @Override
    public boolean check(H4nTbCheck check, Object beCheckedObj) {
        /*用户指定要检验的属性名称*/
        String[] fieldNames = check.fieldName();
        /*获取被检验对象的类*/
        Class<?> objClass = beCheckedObj.getClass();
        /*用户指定的属性都存在无误*/
        Set<String> declaredFields =  Arrays.stream(objClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
        boolean allFieldExists = Arrays.stream(fieldNames).allMatch(fieldName -> CollUtil.contains(declaredFields, fieldName));
        AssertUtil.isTrueThrowBeforeExp(allFieldExists,"您提供的字段[{}]不完全存在", fieldNames);
        /*获得sql*/
        String sql = sqlBuilder(check, objClass);
        /*sql 参数*/
        Object[] params = Arrays.stream(fieldNames).map(fieldName-> ReflectUtil.getFieldValue(beCheckedObj,fieldName)).toArray();
        /**
         * 执行SQL
         */
        long count = SqlExecutor.selectNumber(sql, params).longValue();
        /**
         * 判定结果
         */
        TbCheck checkModel = check.check();
        if (checkModel == TbCheck.PRIMARY_KEY_CHECK){
            return count == 0 ;
        }
        if (checkModel == TbCheck.EXIST_CHECK){
            return count >0 ;
        }
        if (checkModel == TbCheck.EXISTS_AND_UNIQUE){
            return count == 1;
        }
        return false;
    }

    /**
     * sql builder
     *
     * @param check    检查
     * @param objClass obj类
     * @return {@link String}
     */
    private String sqlBuilder(H4nTbCheck check ,Class<?> objClass){
        final String sqlTemp = "SELECT COUNT(*) FROM {} T WHERE 1=1 {}";
        final String tbName = getSchemaTable( check , objClass);
        String where = getWhere(check.fieldName(),objClass);
        return StrUtil.format(sqlTemp,tbName,where);
    }

    /**
     * 获取表和表模式
     *
     * @param check    检查
     * @param objClass obj类
     * @return {@link String}
     */
    private String getSchemaTable(H4nTbCheck check ,Class<?> objClass){
        TableName mybatisPlusAnnotation = objClass.getDeclaredAnnotation(TableName.class);
        if (mybatisPlusAnnotation!=null){
            return (mybatisPlusAnnotation.schema()+ mybatisPlusAnnotation.value());
        }
        Table beetlSqlAnnotation = objClass.getDeclaredAnnotation(Table.class);
        if (beetlSqlAnnotation!=null){
            return beetlSqlAnnotation.name();
        }
        return StrUtil.toUnderlineCase(objClass.getSimpleName());
    }

    /**
     * 获取where子句
     *
     * @param checkFieldNames 检查字段名称
     * @param objClass        obj类
     * @return {@link String}
     */
    private String getWhere(String[] checkFieldNames, Class<?> objClass){
        Set<Field> declaredFields =  Arrays.stream(objClass.getDeclaredFields()).collect(Collectors.toSet());
        StringBuilder sb = new StringBuilder("");
        declaredFields.stream().forEach(
                field-> {
                    if (ArrayUtil.contains(checkFieldNames,field.getName())) {
                        //用户要求检验此字段
                        TableField mybatisPlusAnnotation = field.getDeclaredAnnotation(TableField.class);
                        if (mybatisPlusAnnotation!=null){
                            sb.append(" AND T.").append(mybatisPlusAnnotation.value()).append(" = ? ");
                            return;
                        }
                        Column beetlSqlAnnotation = field.getDeclaredAnnotation(Column.class);
                        if (beetlSqlAnnotation!=null){
                            sb.append(" AND T.").append(beetlSqlAnnotation.value()).append(" = ? ");
                            return;
                        }
                        H4nColumn h4nColumn = field.getDeclaredAnnotation(H4nColumn.class);
                        if (h4nColumn != null){
                            sb.append(" AND T.").append(h4nColumn.value()).append(" = ? ");
                            return;
                        }
                        sb.append(" AND T.").append(field.getName()).append(" = ? ");
                        //用户要求检验此字段
                    }
                }
        );
        return sb.toString();
    }
}
