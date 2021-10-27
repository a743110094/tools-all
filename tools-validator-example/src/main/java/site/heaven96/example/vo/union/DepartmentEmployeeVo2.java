package site.heaven96.example.vo.union;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.heaven96.example.entity.union.Department;
import site.heaven96.example.entity.union.Employee;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;

import java.util.List;

import static site.heaven96.validate.common.enums.Condition.IF;
import static site.heaven96.validate.common.enums.Condition.THEN;
import static site.heaven96.validate.common.enums.Logic.*;

/**
 * 部门员工VO
 *
 * @author Heaven96
 * @date 2021/10/13
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@H4nUnionCheck(message = "管理部的崽，大于40岁才可以(SQL)")
@H4nUnionCheck(group = 2, message = "A部的崽，必须拥有开发者的角色(SQL)")
public class DepartmentEmployeeVo2 {
    /**
     * 部门
     */
    @H4nCheck(logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"管理部"})
    @H4nCheck(group = 2, field = "#this.name", operator = EQUALS, valueSet = {"管理部"})
    private Department department;
    /**
     * 员工
     */
    @H4nCheck(logic = THEN, field = "#this.age", operator = GREATER_THAN, valueSet = {"SELECT 40 FROM DUAL"})
    @H4nCheck(group = 2,logic = THEN, field = "#this.role", operator = IN, valueSet = {"SELECT '开发者' FROM DUAL WHERE '开发者' = #{thisrole} "})
    private List<Employee> employees;
}
