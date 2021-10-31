package site.heaven96.validate.imp.check.field;

import site.heaven96.validate.iface.check.field.FieldChecker;
import site.heaven96.validate.imp.check.enums.FieldCheckModel;
import site.heaven96.validate.imp.check.field.UnionFiledCheckerImpl;

import static site.heaven96.validate.imp.check.enums.FieldCheckModel.UNION;

/**
 * 提交检查程序实例
 *
 * @author heaven96
 * @date 2021/10/31 12:08:34
 */
public class FiledCheckerFactory {

    private static class CheckerFactory {
    private static final FieldChecker unionFiledChecker = new UnionFiledCheckerImpl();
    }

    private FiledCheckerFactory(){}


    public static final FieldChecker getInstance(FieldCheckModel model) {
        if (model == UNION){
            return CheckerFactory.unionFiledChecker;
        }
        return null;
    }  
}