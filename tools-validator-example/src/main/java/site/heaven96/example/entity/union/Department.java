package site.heaven96.example.entity.union;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 部门
 *
 * @author Heaven96
 * @date 2021/10/13
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Department implements Serializable {
    /**
     * ID号
     */
    private int id;
    /**
     * 名字
     */
    private String name;

}
