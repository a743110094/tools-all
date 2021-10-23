package site.heaven96.validate.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.constant.ValidtorConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author lgw3488
 * @date 2021/10/23
 */
public class DateUtil {
    /**
     * 判断是否是日期
     *
     * @param obj OBJ
     * @return boolean
     */
    public static boolean isDate(Object obj){
        if (ObjectUtil.isNull(obj)){
            return false;
        }
        return (obj instanceof java.util.Date || obj instanceof LocalDate || obj instanceof LocalDateTime || obj instanceof LocalTime) == true;
    }

    /**
     * 字符串是否可以强制转换为日期
     *
     * @param str 应力
     * @return boolean
     */
    public static boolean strCanBeCastToDate(String str){
        return ObjectUtil.isNotNull(strCastToDate(str));
    }

    /**
     * 字符串转换为日期
     *
     * @param str 应力
     * @return {@code Date}
     */
    public static Date strCastToDate(String str){
        if (StrUtil.isBlank(str)){
            return null;
        }
        //试错转换
        try {
            //用于格式化或解析具有偏移量的日期（如果可用），例如“2011-12-03”或“2011-12-03+01:00”。
            return cn.hutool.core.date.DateUtil.parse(str, DateTimeFormatter.ISO_DATE);
        }catch (Exception e){
            //ignore
        }
        try {
            //用于格式化或解析没有偏移量的日期，例如“20111203”。
            return cn.hutool.core.date.DateUtil.parse(str, DateTimeFormatter.BASIC_ISO_DATE);
        }catch (Exception e){
            //ignore
        }
        try {
            return cn.hutool.core.date.DateUtil.parse(str, ValidtorConstants.DATE_HOUR_MINUTE_FORMAT);
        }catch (Exception e){
            //ignore
        }
        try {
            return cn.hutool.core.date.DateUtil.parse(str, ValidtorConstants.DATE_HOUR_MINUTE_SECOND_FORMAT);
        }catch (Exception e){
            //ignore
        }
        try {
            return cn.hutool.core.date.DateUtil.parse(str, ValidtorConstants.DATE_HOUR_MINUTE_SECOND_MILLI_SECOND_FORMAT);
        }catch (Exception e){
            //ignore
        }
        return null;
    }
}
