package site.heaven96.example.entity.single;

import lombok.Data;
import site.heaven96.validate.common.annotation.H4nTbCheck;
import site.heaven96.validate.common.enums.TbCheck;

/**
 * custumer
 * 做服装的人
 *
 * @author heaven96
 * @date 2021/10/31 19:11:23
 */
@H4nTbCheck(check = TbCheck.PRIMARY_KEY_CHECK,fieldName = "id",message = "表校验测试,用户的id已存在，请更换")
@Data
public class Customer {
    private int id ;
}
