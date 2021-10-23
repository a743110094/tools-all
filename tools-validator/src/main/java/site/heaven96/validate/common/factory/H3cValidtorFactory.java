package site.heaven96.validate.common.factory;

import cn.hutool.core.util.ReflectUtil;
import site.heaven96.validate.service.H3cWmsValidateService;
import site.heaven96.validate.service.impl.FieldRequireValidServiceImpl;
import site.heaven96.validate.service.impl.H3cWmsValidateServiceImpl;

/**
 * H3C验证器厂
 *
 * @author Heaven96
 * @date 2021/10/10
 */
public class H3cValidtorFactory {

    public static final String H_3_C_WMS_VALIDATE_SERVICE_IMPL = "H3cWmsValidateServiceImpl";
    public static final String H_3_C_WMS_VALIDATE_SERVICE = "H3cWmsValidateService";
    public static final String ABSTRACT_FIELD_REQUIRE_VALID_SERVICE_IMPL = "FieldRequireValidServiceImpl";

    /**
     * H3C验证器厂
     * 无参构造器，私有化
     */
    private H3cValidtorFactory() {

    }

    /**
     * 获取实例
     *
     * @param classname 类名
     * @return {@code H3cWmsValidateService}
     */
    public static H3cWmsValidateService getInstance(String classname) {
        H3cWmsValidateService service = null;
        try {
            if (H_3_C_WMS_VALIDATE_SERVICE_IMPL.equals(classname) || H_3_C_WMS_VALIDATE_SERVICE.equals(classname)) {
                service = ReflectUtil.newInstance(H3cWmsValidateServiceImpl.class);
            } else if (ABSTRACT_FIELD_REQUIRE_VALID_SERVICE_IMPL.equals(classname)) {
                service = ReflectUtil.newInstance(FieldRequireValidServiceImpl.class);
            } else if ("FieldCheckServiceImpl".equals(classname)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }
}