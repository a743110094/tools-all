package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.util.AssertUtil;
import site.heaven96.validate.util.H4nCompareUtil;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期等于处理程序
 *
 * @author Heaven96
 * @date 2021/10/20
 */
public class DateEqualsFixedValueHandler extends AbstractEqualsFixedValueHandler {

    private String dateFormatter = "yyyy-MM-dd HH:mm:ss.SSS";

    @Override
    public boolean subHandle(Object obj, Operator operator, Object standardVal) {
        boolean objIsDate = (obj instanceof java.util.Date) || (obj instanceof java.sql.Date) ||
                (obj instanceof LocalDate) || (obj instanceof Timestamp);
        if (!objIsDate) {
            AssertUtil.isTrueThrowBeforeExp(nextEqualsHandler()!=null,AE_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextEqualsHandler().subHandle(obj, operator, standardVal);
        }
        Date date;
        java.util.Date date1;
        LocalDate localDate;
        Timestamp timestamp;
        final String standardDateStr = StrUtil.str(standardVal, StandardCharsets.UTF_8);
        try {
            date = (Date) obj;
            return H4nCompareUtil.equals(date,DateUtil.parse(standardDateStr,dateFormatter));
        }catch (ClassCastException e){
            //ignore
        }
        try {
            date1 = (java.util.Date) obj;
            return H4nCompareUtil.equals(date1,DateUtil.parse(standardDateStr,dateFormatter));
        }catch (ClassCastException e){
            //ignore
        }
        try {
            localDate = (LocalDate) obj;
            return H4nCompareUtil.equals(localDate,LocalDate.parse(standardDateStr, DateTimeFormatter.ofPattern(dateFormatter)));
        }catch (ClassCastException e){
            //ignore
        }
        try {
            timestamp = (Timestamp) obj;
            return H4nCompareUtil.equals(timestamp,Long.valueOf(standardDateStr));
        }catch (ClassCastException e){
            //ignore
        }

        AssertUtil.isTrueThrowBeforeExp(nextEqualsHandler()!=null,AE_HANDLER_NOT_MATCHES_ERR_MSG);
        return nextEqualsHandler().subHandle(obj, operator, standardVal);
    }

}
