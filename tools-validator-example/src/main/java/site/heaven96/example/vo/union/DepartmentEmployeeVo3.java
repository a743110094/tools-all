package site.heaven96.example.vo.union;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.heaven96.example.entity.union.Department;
import site.heaven96.example.entity.union.Employee;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nReflectCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;
import site.heaven96.validate.common.enums.LegalOrigin;

import javax.validation.Valid;
import java.util.List;

import static site.heaven96.validate.common.enums.Condition.IF;
import static site.heaven96.validate.common.enums.Condition.THEN;
import static site.heaven96.validate.common.enums.Logic.EQUALS;
import static site.heaven96.validate.common.enums.Logic.GREATER_THAN;

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
@H4nReflectCheck(message = "反射测试,S部的人 年龄不能大于40",method = "site.heaven96.example.rule.ClassA.check")
public class DepartmentEmployeeVo3 {
    /**
     * 部门
     */
    @H4nCheck(logic = IF, spel = "#this.name", operator = EQUALS, legal = {"管理部"})
    private Department department;
    /**
     * 员工
     */
    @H4nCheck(logic = THEN, spel = "#this.age", operator = GREATER_THAN, legal = {"SELECT 40 FROM DUAL"})
    @Valid
    private List<Employee> employees;
}
