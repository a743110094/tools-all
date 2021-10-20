package site.heaven96.example.vo.union;


import lombok.Data;
import site.heaven96.example.entity.union.Department;
import site.heaven96.example.entity.union.Employee;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;

import javax.validation.Valid;
import java.util.List;

import static site.heaven96.validate.common.enums.Logic.IF;
import static site.heaven96.validate.common.enums.Logic.THEN;
import static site.heaven96.validate.common.enums.Operator.EQUALS;
import static site.heaven96.validate.common.enums.Operator.GREATER_THAN;

/**
 * 部门员工VO
 *
 * @author lgw3488
 * @date 2021/10/13
 */
@Data
@H4nUnionCheck(message = "管理部的崽，大于40岁才可以(SQL)")

public class DepartmentEmployeeVo2 {
    /**
     * 部门
     */
    @H4nCheck(logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"管理部"})
    private Department department;
    /**
     * 员工
     */
    @H4nCheck(logic = THEN, field = "#this.age", operator = GREATER_THAN, valueSet = {"SELECT 40 FROM DUAL"})
    @Valid
    private List<Employee> employees;
}
