package site.heaven96.example.entity.union;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 员工
 *
 * @author Heaven96
 * @date 2021/10/13
 */
@Data
public class Employee {
    /**
     * ID号
     */
    private int id;
    /**
     * 名字
     */
    private String name;
    /**
     * 年龄
     */
    private BigDecimal age;

    /**
     * 角色
     */
    private String role;
}
