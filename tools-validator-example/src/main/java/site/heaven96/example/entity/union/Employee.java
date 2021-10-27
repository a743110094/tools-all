package site.heaven96.example.entity.union;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工
 *
 * @author Heaven96
 * @date 2021/10/13
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee implements Serializable {
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

    /**
     * 出生日期
     */
    private Date dateOfBirth;

}
