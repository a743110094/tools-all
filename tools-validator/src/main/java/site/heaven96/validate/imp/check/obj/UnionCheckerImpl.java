package site.heaven96.validate.imp.check.obj;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.assertes.common.exception.H4nUnExpectedException;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;
import site.heaven96.validate.common.enums.Condition;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.iface.check.field.FieldChecker;
import site.heaven96.validate.imp.check.enums.FieldCheckModel;
import site.heaven96.validate.imp.check.field.FiledCheckerFactory;
import site.heaven96.validate.imp.check.field.UnionFiledCheckerImpl;
import site.heaven96.validate.service.impl.FieldCheckServiceImpl;
import site.heaven96.validate.util.FieldUtil;
import site.heaven96.validate.util.SpelUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 联合检查器
 * 下一步会调用FieldChecker去做底层的检查
 * @author lgw3488
 * @date 2021/10/29
 */
@Slf4j
public class UnionCheckerImpl extends AbObjectChecker<H4nUnionCheck> {

    public static final String NO_H4CHECK_ANNOTATION_ERR_MSG = "\n===> 联合校验时，未找到 @H4nCheck 注解";
    public static final String UNKNOWN_CHECK_LOGIC_ERR_MSG = "\n==>构造前提条件错误（未指定Logic）\n[相关校验]:{}";
    private static H4nUnionCheck unionCheck;
    private static Object beCheckObj ;
    //字段和注解的列表（条件类）
    private  List<AnnotationField> anFieldsIf = CollUtil.newArrayList();
    //字段和注解的列表（结论类）
    private List<AnnotationField> anFieldsThen = CollUtil.newArrayList();

    /**
     * 检查
     *
     * @param ckAnnotation 注解
     * @param obj          被检查对象
     * @return boolean
     */
    @Override
    public boolean check(H4nUnionCheck ckAnnotation, Object obj) {
        //检验
        unionCheck = ckAnnotation;
        //对象
        beCheckObj = obj;
        groupAnnotation(beCheckObj);
        ////////////////开始校验
        return doUnionCheck();
    }


    /**
     * 分组注释
     * 将校验注释区分为 [条件] 和 [结论] 两大类
     * @param obj obj
     */
    private void groupAnnotation(Object obj) {
        //找到包含 @H4nCheck 注解的 group 和 @H4nUnionCheck 的group 一致的 FIELDS
        final List<Field> fieldsWithH4nCheck = FieldUtil.getGroupFieldListWithAnnotation(obj, unionCheck.group());
        //如果此bean不包含@H4nCheck注解  直接报错
        AssertUtil.isTrueThrowBeforeExp(CollUtil.isNotEmpty(fieldsWithH4nCheck), NO_H4CHECK_ANNOTATION_ERR_MSG);
        //查询每个field  找出本组的H4nCheck 并根据IF或者THEN分到不同数组
        fieldsWithH4nCheck.stream().forEach(fldItem -> {
            Arrays.stream(fldItem.getDeclaredAnnotationsByType(H4nCheck.class))
                    .forEach(annotationItem -> {
                        //找本组的注解
                        if (unionCheck.group() == annotationItem.group()) {
                            final int order = annotationItem.logic().getOrder();
                            //order > 0 为条件类 order < 0 为结论类
                            if (order != 0) {
                                CollUtil.addAll((order > 0 ? anFieldsIf : anFieldsThen), new AnnotationField(annotationItem, fldItem.getName()));
                            }
                        }
                    });
        });
    }


    /**
     * 进行联合检查
     *
     * @return boolean
     */
    private boolean doUnionCheck(){
        if (checkFieldItem(anFieldsIf)) {
            log.info(StrUtil.format("\n==>[满足]校验前提 \n=>相关校验：{}", unionCheck.toString()));
            return checkFieldItem(anFieldsThen);
        } else {
            log.info(StrUtil.format("\n==>[不满足]校验前提 跳过\n=>相关校验：{}", unionCheck.toString()));
            return true;
        }
    }


    /**
     * 检查字段项
     *
     * @param fields 字段
     * @return boolean
     */
    boolean checkFieldItem(List<AnnotationField> fields){
       AtomicBoolean resultIsTrue = new AtomicBoolean(true);

       FieldChecker fieldChecker = FiledCheckerFactory.getInstance(FieldCheckModel.UNION);

       fields.stream().forEach(
                annotationFieldItem -> {
                    H4nCheck check = annotationFieldItem.getH4nCheck();
                    String fieldName = annotationFieldItem.getFieldName();
                    String spel = check.spel();
                    Logic logic = check.operator();
                    //计算合法值 静态直接返回 动态计算后返回
                    Object legals = this.getLegals(check.origin(),check.legal(),beCheckObj);
                    Object targetFieldPathValue;
                    //取出H4nCheck注解所在对象 例如Demo中的employees
                    Object beanA = ReflectUtil.getFieldValue(beCheckObj, fieldName);
                    //取出真正待验证的值集 例如Demo中的employees.name
                    targetFieldPathValue = SpelUtil.get(spel, beanA);
                    resultIsTrue.set(fieldChecker.check(targetFieldPathValue,logic,check.ignoreCase(),legals));
                    if (resultIsTrue.get() == false){
                        return;
                    }
                }
        );
       return resultIsTrue.get();
    }

    /**
     * 注释字段
     *
     * @author Heaven96
     * @date 2021/10/16
     */
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private class AnnotationField {
        private transient H4nCheck h4nCheck;
        private String fieldName;
    }
}
