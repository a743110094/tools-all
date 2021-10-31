package site.heaven96.validate.imp.check.obj;

import site.heaven96.validate.iface.check.obj.ObjectChecker;
import site.heaven96.validate.imp.check.enums.ObjectCheckModel;
import site.heaven96.validate.imp.check.field.FiledCheckerFactory;

import static site.heaven96.validate.imp.check.enums.ObjectCheckModel.*;

/**
 * 对象检查工厂
 *
 * @author heaven96
 * @date 2021/10/31 17:54:35
 */
public class ObjectCheckerFactory {
    /**
     * 检查工厂
     *
     * @author heaven96
     * @date 2021/10/31 17:54:30
     */
    private static class CheckerFactory {
        /*singleton*/
        private static final ObjectChecker unionChecker = new UnionCheckerImpl();
        private static final ObjectChecker reflectChecker = new ReflectCheckerImpl();
        private static final ObjectChecker tbChecker = new TbCheckerImpl();
    }

    /**
     * 对象检查工厂
     */
    private ObjectCheckerFactory(){}


    /**
     * 获得实例
     *
     * @param model 模型
     * @return {@link ObjectChecker}
     */
    public static final ObjectChecker getInstance(ObjectCheckModel model) {
        if (model == UNION){
            return CheckerFactory.unionChecker;
        }
        if (model == REFLECT){
            return CheckerFactory.reflectChecker;
        }
        if (model == TB_CHECK){
            return CheckerFactory.tbChecker;
        }
        return null;
    }
}
