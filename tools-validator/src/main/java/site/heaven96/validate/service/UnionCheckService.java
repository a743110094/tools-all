package site.heaven96.validate.service;

import site.heaven96.validate.common.annotation.H4nUnionCheck;

/**
 * 联合检查服务
 *
 * @author Heaven96
 * @date 2021/10/14
 */
public interface UnionCheckService {
    /**
     * 检查
     *
     * @param huc 参数
     * @return boolean
     */
    boolean check(Object o, H4nUnionCheck huc);
}
