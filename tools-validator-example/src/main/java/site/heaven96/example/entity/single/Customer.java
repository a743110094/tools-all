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
@H4nTbCheck(check = TbCheck.EXIST_CHECK,fieldName = "depart_id",message = "表校验测试,departId不存在")
@H4nTbCheck(check = TbCheck.EXISTS_AND_UNIQUE,fieldName = "name",message = "表校验测试,用户的名字[ ${validatedValue} ]不满足存在且唯一的要求")
@Data
public class Customer {
    private int id ;
    private int depart_id;
    private String name ;
}
