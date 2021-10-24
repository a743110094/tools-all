package site.heaven96.validate.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.assertes.util.AssertUtil;

import java.util.*;

/**
 * H4N SQL参数工具
 *
 * @author lgw3488
 * @date 2021/10/22
 */
public class H4nSqlParamsUtil {

    /**
     * 初始化索引
     */
    private static final int INIT_INDEX = -1;

    private static final String REGEX = "#{1}\\{(\\w*\\.*\\w*){1,3}}";


    public static String parseSql(String sql){
        return ReUtil.replaceAll(sql, REGEX, "?");
        //List<String> all = ReUtil.findAll(REGEX, sql, 0);
    }
    /**
     * 构造某个【动态】sql的参数  sql的参数用?占位
     *
     * @param sqlTemplate SQL模板
     * @param paramsIn    输入参数
     * @return {@code List<Object[]>}
     */
    public static List<Object[]> params(String sqlTemplate , Object... paramsIn){
        final int requireParams = StrUtil.count(sqlTemplate,'?');
        final int provideParams = paramsIn.length;
        AssertUtil.isTrueThrowBeforeExp(requireParams==provideParams,"给定参数和要求参数数目不匹配");
        List<Object[]> list = CollUtil.newArrayList();
        if (provideParams ==0){
            list.add(paramsIn);
            return list;
        }
        final Optional<Object> any = Arrays.stream(paramsIn).filter(item -> item instanceof Collection).findAny();
        //判断有无集合 有集合会遍历生成多个SQL
        if (any.isPresent()){
            //有集合
            int collIndex = INIT_INDEX;
            Collection<?> coll = null;
            for (int i = 0; i < provideParams; i++) {
                Object o = paramsIn[i];
                if (o instanceof Collection){
                    AssertUtil.isTrueThrowBeforeExp(collIndex == INIT_INDEX,"暂定仅允许一个Collection");
                    coll = (Collection<?>) o;
                    collIndex = i;
                }
            }
            //只能有一个Collection时先去重 因为其他值一样
            Iterator<?> iterator = CollUtil.distinct(coll).iterator();
            while (iterator.hasNext()){
                ArrayList<Object> paramListCopy = CollUtil.newArrayList(paramsIn);
                paramListCopy.set(collIndex, iterator.next());
                ArrayList<Object> objects = CollUtil.newArrayList(paramListCopy);
                list.add(objects.toArray());
            }
        }else{
            list.add(paramsIn);
        }
        return list;
    }
}
