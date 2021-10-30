package site.heaven96.validate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.assertes.common.exception.H4nUnExpectedException;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;
import site.heaven96.validate.common.enums.Condition;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.service.UnionCheckService;
import site.heaven96.validate.util.AutoChooseUtil;
import site.heaven96.validate.util.FieldUtil;
import site.heaven96.validate.util.SpelUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@Slf4j
public class UnionCheckServiceImpl implements UnionCheckService {

    public static final String NO_H4CHECK_ANNOTATION_ERR_MSG = "\n===> 联合校验时，未找到 @H4nCheck 注解";
    public static final String UNKNOWN_CHECK_LOGIC_ERR_MSG = "\n==>构造前提条件错误（未指定Logic）\n[相关校验]:{}";
    /**
     * 挖掘级别
     */
    private int level;
    /**
     * 继续挖掘
     */
    private boolean keepDigging = true;

    /**
     * 检查
     *
     * @param ck CK
     * @return boolean
     */
    @Override
    public boolean check(Object o, H4nUnionCheck ck) {
        return doCheck(o, ck);
    }
    //---------------------------------------

    /**
     * 执行检查
     *
     * @param ck CK
     * @return boolean
     */
    private boolean doCheck(Object o, H4nUnionCheck ck) {
        /** 值集来源 */
        final LegalOrigin legalOrigin = AutoChooseUtil.valueSetOrigin(ck.valueSetOrigin(), ck.valueSet());
        return groupCheck(o, ck);
        //找结论（logic）
        //做判断
    }

    /**
     * 单组检查
     *
     * @param o  O
     * @param ck CK
     * @return boolean
     */
    private boolean groupCheck(final Object o, final H4nUnionCheck ck) {
        //校验组号
        final int group = ck.group();
        //找到包含 @H4nCheck 注解的 group 和 @H4nUnionCheck 的group 一致的 FIELDS
        final List<Field> fieldsWithH4nCheck = FieldUtil.getGroupFieldListWithAnnotation(o, group);
        //如果此bean不包含@H4nCheck注解  直接报错
        AssertUtil.isTrueThrowBeforeExp(CollUtil.isNotEmpty(fieldsWithH4nCheck), NO_H4CHECK_ANNOTATION_ERR_MSG);
        //字段和注解的列表
        //List<AnnotationField>  = CollectionUtil.newArrayList();
        //字段和注解的列表（条件类）
        List<AnnotationField> anFieldsIf = CollUtil.newArrayList();
        //字段和注解的列表（结论类）
        List<AnnotationField> anFieldsThen = CollUtil.newArrayList();

        //查询每个field，找出本组的H4nCheck 并根据IF或者THEN分到不同数组
        fieldsWithH4nCheck.stream().forEach(fldItem -> {
            Arrays.stream(fldItem.getDeclaredAnnotationsByType(H4nCheck.class))
                    //找本组的注解
                    .filter(annotationItem -> annotationItem.group() == group)
                    .forEach(annoItem -> {
                        final int order = annoItem.logic().getOrder();
                        //order > 0 为条件类 order < 0 为结论类
                        if (order != 0) {
                            CollUtil.addAll((order > 0 ? anFieldsIf : anFieldsThen), new AnnotationField(annoItem, fldItem));
                        }
                    });
        });
        ////////////////开始校验
        //1.判断   Condition.IF/OR_IF/AND_IF条件链  是否成立
        AtomicBoolean conditionIsTrue = new AtomicBoolean(true);
        AtomicBoolean resultIsTrue = new AtomicBoolean(true);
        //冗余代码
        anFieldsIf.stream().forEach(
                annotationFieldItem -> {
                    H4nCheck check = annotationFieldItem.getH4nCheckAnnotation();
                    Field annotationField = annotationFieldItem.getField();
                    String targetFieldPath = check.field();
                    Logic logic = check.operator();
                    String[] valueSet = check.legal();
                    Condition condition = check.logic();
                    Object targetFieldPathValue;
                    Object beanA = ReflectUtil.getFieldValue(o, annotationField.getName());
                    targetFieldPathValue = SpelUtil.get(targetFieldPath, beanA);
                    boolean b = FieldCheckServiceImpl.filedCheck(
                            targetFieldPathValue, logic, check.ignoreCase(), valueSet
                    );

                    if (condition.equals(Condition.IF) || condition.equals(Condition.AND_IF)) {
                        conditionIsTrue.set(conditionIsTrue.get() && b);
                    } else if (condition.equals(Condition.OR_IF)) {
                        conditionIsTrue.set(conditionIsTrue.get() || b);
                    } else {
                        throw new H4nUnExpectedException(StrUtil.format(UNKNOWN_CHECK_LOGIC_ERR_MSG, check));
                    }
                    if (!conditionIsTrue.get()) {
                        return;
                    }
                }
        );

        if (conditionIsTrue.get()) {
            //验证逻辑 TODO
            log.info(StrUtil.format("\n==>[满足]校验前提 \n=>相关校验：{}", ck.toString()));
            {
                //冗余代码
                anFieldsThen.stream().forEach(
                        annotationFieldItem -> {
                            H4nCheck check = annotationFieldItem.getH4nCheckAnnotation();
                            Field annotationField = annotationFieldItem.getField();
                            String targetFieldPath = check.field();
                            Logic logic = check.operator();
                            String[] valueSet = check.legal();
                            Condition condition = check.logic();
                            Object targetFieldPathValue;
                            Object beanA = ReflectUtil.getFieldValue(o, annotationField.getName());
                            targetFieldPathValue = SpelUtil.get(targetFieldPath, beanA);
                            if (!(targetFieldPathValue instanceof Collection)) {
                                targetFieldPathValue = Arrays.asList(targetFieldPathValue);
                            }
                            if (targetFieldPathValue instanceof Collection) {
                                Collection collections = (Collection) targetFieldPathValue;

                                collections.stream().forEach(targetFieldPathValueItem -> {
                                    boolean b = FieldCheckServiceImpl.filedCheck(
                                            targetFieldPathValueItem, logic, check.ignoreCase(), valueSet
                                    );
                                    if (condition.equals(Condition.THEN) || condition.equals(Condition.AND_THEN)) {
                                        resultIsTrue.set(resultIsTrue.get() && b);
                                    } else if (condition.equals(Condition.OR_THEN)) {
                                        resultIsTrue.set(resultIsTrue.get() || b);
                                    } else {
                                        throw new H4nUnExpectedException("构造前提条件错误（未指定Logic）" + check);
                                    }
                                    if (!resultIsTrue.get()) {
                                        return;
                                    }
                                });
                            }
                        }
                );

            }
            return resultIsTrue.get();
        } else {
            log.info(StrUtil.format("\n==>[不满足]校验前提 跳过\n=>相关校验：{}", ck.toString()));
            return true;
        }
    }


    /**
     * 获取SQL值集合 当值集来源是SQL执行结果时
     *
     * @return {@code List<String>}
     */
    private List<String> getSqlValueSets(Object o, H4nUnionCheck ck) {
        final String sql = ck.sql();
        return null;
        //TODO 2021年10月14日 23:41:50
    }


    /**
     * 注记字段
     *
     * @author Heaven96
     * @date 2021/10/16
     */
    private class AnnotationField {
        private H4nCheck h4nCheckAnnotation;
        private Field field;

        public AnnotationField(H4nCheck h4nCheckAnnotation, Field field) {
            this.h4nCheckAnnotation = h4nCheckAnnotation;
            this.field = field;
        }

        public H4nCheck getH4nCheckAnnotation() {
            return h4nCheckAnnotation;
        }

        public void setH4nCheckAnnotation(H4nCheck h4nCheckAnnotation) {
            this.h4nCheckAnnotation = h4nCheckAnnotation;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }
}
