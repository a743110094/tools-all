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
import site.heaven96.validate.iface.check.obj.ObjectChecker;
import site.heaven96.validate.imp.check.enums.ObjectCheckModel;
import site.heaven96.validate.imp.check.obj.ObjectCheckerFactory;
import site.heaven96.validate.imp.check.obj.UnionCheckerImpl;
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
        ObjectChecker checker = ObjectCheckerFactory.getInstance(ObjectCheckModel.UNION);
        return checker.check(ck,o);
    }

}
