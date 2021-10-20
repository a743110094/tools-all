package site.heaven96.validate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.common.enums.ValueSetOrigin;
import site.heaven96.validate.common.exception.H4nUnExpectedException;
import site.heaven96.validate.service.UnionCheckService;
import site.heaven96.validate.util.AssertUtil;
import site.heaven96.validate.util.FieldUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@Slf4j
public class UnionCheckServiceImpl implements UnionCheckService {

    /**
     * 选择
     */
    public static final String SELECT = "SELECT";
    /**
     * 拍击招牌
     */
    public static final String UCSI_POUND_SIGN = "#";
    public static final String UCSI_H4CHECK_ANNOTATION_NOT_FOUND_ERR_MSG = "\n===> 联合校验时，未找到 @H4nCheck 注解";
    public static final String UCSI_CHECK_LOGIC_UNDEFINDED = "\n==>构造前提条件错误（未指定Logic）\n[相关校验]:{}";
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
        final ValueSetOrigin valueSetOrigin = autoChooseValueSetOrigin(ck.valueSetOrigin(), ck.valueSet());



        return printCheckLogin(o, ck);
        //找结论（logic）

        //做判断
    }

    private boolean printCheckLogin(final Object o, final H4nUnionCheck ck) {
        //校验组号
        final int group = ck.group();
        //获取Bean Obj的类
        final Class<?> oClass = o.getClass();
        //找到包含 @H4nCheck 注解的 group 和 @H4nUnionCheck 的group 一致的 FIELDS
        final List<Field> fieldsWithH4nCheck = FieldUtil.getGroupFieldListWithAnnotation(o,group);
        //如果此bean不包含@H4nCheck注解  直接报错
        AssertUtil.isTrueThrowH4nBeforeValidateCheckException(CollUtil.isNotEmpty(fieldsWithH4nCheck), UCSI_H4CHECK_ANNOTATION_NOT_FOUND_ERR_MSG);
        //字段和注解的列表
        List<AnnotationField> annotationFields = CollectionUtil.newArrayList();
        List<AnnotationField> annotationFieldsCondition = CollUtil.newArrayList();
        List<AnnotationField> annotationFieldsJudge = CollUtil.newArrayList();

        fieldsWithH4nCheck.stream()
                .forEach(field -> {
                    Optional<H4nCheck> any = Arrays.stream(field.getDeclaredAnnotationsByType(H4nCheck.class))
                            .filter(annotationItem -> annotationItem.group() == group).findAny();
                    CollectionUtil.addAll(annotationFields, new AnnotationField(any.get(), field));
                });



        ////////////////开始校验


        annotationFields.stream().forEach(annotationFieldsItem -> {
                    Logic declareLogic = annotationFieldsItem.getH4nCheckAnnontation().logic();
                    switch (declareLogic) {
                        case IF: {
                        }
                        case AND_IF: {
                        }
                        case OR_IF: {
                            CollUtil.addAll(annotationFieldsCondition, annotationFieldsItem);
                            break;
                        }
                        case THEN: {
                            CollUtil.addAll(annotationFieldsJudge, annotationFieldsItem);
                            break;
                        }
                        default: {
                        }
                    }
                }
        );
        //1.判断前提条件是否成立（即看是否满足开始验证的逻辑)
        AtomicBoolean conditionIsTrue = new AtomicBoolean(true);
        AtomicBoolean resultIsTrue = new AtomicBoolean(true);
        //冗余代码
        annotationFieldsCondition.stream().forEach(
                annotationFieldItem -> {
                    H4nCheck check = annotationFieldItem.getH4nCheckAnnontation();
                    Field annotationField = annotationFieldItem.getField();
                    String targetFieldPath = check.field();
                    Operator operator = check.operator();
                    String[] valueSet = check.valueSet();
                    Logic logic = check.logic();

                    Object annotationFieldValue = ReflectUtil.getFieldValue(o, annotationField.getName());
                    Object targetFieldPathValue = null;
                    {
                        //创建ExpressionParser解析表达式
                        ExpressionParser parser = new SpelExpressionParser();
                        //SpEL表达式语法设置在parseExpression()入参内
                        Expression exp = parser.parseExpression(targetFieldPath);
                        //执行SpEL表达式，执行的默认Spring容器是Spring本身的容器：ApplicationContext
                        //Object value = exp.getValue();


                        /**也可以使用非Spring的ApplicationContext容器，则用下面的方法*/
                        //创建一个虚拟的容器EvaluationContext
                        StandardEvaluationContext ctx = new StandardEvaluationContext();
                        //向容器内添加bean
                        Object beanA = ReflectUtil.getFieldValue(o, annotationField.getName());
                        // ctx.setVariable("bean_id", beanA);
                        //setRootObject并非必须；一个EvaluationContext只能有一个RootObject，引用它的属性时，可以不加前缀
                        ctx.setRootObject(beanA);
                        //getValue有参数ctx，从新的容器中根据SpEL表达式获取所需的值
                        targetFieldPathValue = exp.getValue(ctx,String.class);
                        System.out.println(targetFieldPathValue);
                    }
                    boolean b = FieldCheckServiceImpl.fun10(
                            targetFieldPathValue, operator, valueSet
                    );

                    if (logic.equals(Logic.IF) || logic.equals(Logic.AND_IF)) {
                        conditionIsTrue.set(conditionIsTrue.get() && b);
                    } else if (logic.equals(Logic.OR_IF)) {
                        conditionIsTrue.set(conditionIsTrue.get() || b);
                    } else {
                        throw new H4nUnExpectedException(StrUtil.format(UCSI_CHECK_LOGIC_UNDEFINDED, check));
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
                annotationFieldsJudge.stream().forEach(
                        annotationFieldItem -> {
                            H4nCheck check = annotationFieldItem.getH4nCheckAnnontation();
                            Field annotationField = annotationFieldItem.getField();
                            String targetFieldPath = check.field();
                            Operator operator = check.operator();
                            String[] valueSet = check.valueSet();
                            Logic logic = check.logic();

                            Object annotationFieldValue = ReflectUtil.getFieldValue(o, annotationField.getName());
                            Object targetFieldPathValue = null;
                            {

                                Object beanA = ReflectUtil.getFieldValue(o, annotationField.getName());
                                if (beanA instanceof Collection) {
                                    //#this.![#this.age]
                                    targetFieldPath = StrUtil.format("#this.![{}]", targetFieldPath);
                                    int size = CollUtil.size(beanA);
                                }

                                //创建ExpressionParser解析表达式
                                ExpressionParser parser = new SpelExpressionParser();
                                //SpEL表达式语法设置在parseExpression()入参内
                                Expression exp = parser.parseExpression(targetFieldPath);
                                //执行SpEL表达式，执行的默认Spring容器是Spring本身的容器：ApplicationContext
                                //Object value = exp.getValue();


                                /**也可以使用非Spring的ApplicationContext容器，则用下面的方法*/
                                //创建一个虚拟的容器EvaluationContext
                                StandardEvaluationContext ctx = new StandardEvaluationContext();
                                //向容器内添加bean

                                // ctx.setVariable("bean_id", beanA);
                                //setRootObject并非必须；一个EvaluationContext只能有一个RootObject，引用它的属性时，可以不加前缀
                                ctx.setRootObject(beanA);
                                //getValue有参数ctx，从新的容器中根据SpEL表达式获取所需的值


                                targetFieldPathValue = exp.getValue(ctx,String.class);
                                System.out.println(targetFieldPathValue);
                            }
                            if (!(targetFieldPathValue instanceof Collection)) {
                                targetFieldPathValue = Arrays.asList(targetFieldPathValue);
                            }

                            if (targetFieldPathValue instanceof Collection) {
                                Collection collections = (Collection) targetFieldPathValue;

                                collections.stream().forEach(targetFieldPathValueItem -> {
                                    boolean b = FieldCheckServiceImpl.fun10(
                                            targetFieldPathValueItem, operator, valueSet
                                    );
                                    if (logic.equals(Logic.THEN) || logic.equals(Logic.AND_THEN)) {
                                        resultIsTrue.set(resultIsTrue.get() && b);
                                    } else if (logic.equals(Logic.OR_THEN)) {
                                        resultIsTrue.set(resultIsTrue.get() || b);
                                    } else {
                                        throw new H4nUnExpectedException("构造前提条件错误（未指定Logic）" + check.toString());
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
     * 自动根据值集的风格 判断值集来源
     *
     * @param valueSet       值集
     * @param valueSetOrigin 值集原点
     * @return {@code ValueSetOrigin}
     */
    private ValueSetOrigin autoChooseValueSetOrigin(ValueSetOrigin valueSetOrigin, String[] valueSet) {
        //默认自动判断 但指定的优先级更高
        if (valueSetOrigin.equals(ValueSetOrigin.AUTO)) {
            //Auto choose valueSet Origin
            int length = ArrayUtil.length(valueSet);
            if (length == 0 || length > 1) {
                //值集为空或者大于一个值 默认为固定值判断模式
                return ValueSetOrigin.FIXED_VALUE;
            } else {
                if (StrUtil.startWithIgnoreEquals(valueSet[0].trim(), UCSI_POUND_SIGN) && !UCSI_POUND_SIGN.equalsIgnoreCase(valueSet[0].trim())) {
                    //#开头 默认为取其他属性值
                    return ValueSetOrigin.DYNAMIC_SPECIFIED_VALUE;
                }
                if (StrUtil.startWithIgnoreCase(valueSet[0].trim(), SELECT) && !SELECT.equalsIgnoreCase(valueSet[0].trim())) {
                    //SELECT 默认取SQL
                    return ValueSetOrigin.SQL_RESULTS;
                }
            }
            return ValueSetOrigin.FIXED_VALUE;
        }
        return valueSetOrigin;
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
     * @author lgw3488
     * @date 2021/10/16
     */
    private class AnnotationField {
        private H4nCheck h4nCheckAnnontation;
        private Field field;

        public AnnotationField(H4nCheck h4nCheckAnnontation, Field field) {
            this.h4nCheckAnnontation = h4nCheckAnnontation;
            this.field = field;
        }

        public H4nCheck getH4nCheckAnnontation() {
            return h4nCheckAnnontation;
        }

        public void setH4nCheckAnnontation(H4nCheck h4nCheckAnnontation) {
            this.h4nCheckAnnontation = h4nCheckAnnontation;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }
}
