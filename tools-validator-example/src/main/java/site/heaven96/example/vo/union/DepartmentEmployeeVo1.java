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
import static site.heaven96.validate.common.enums.Operator.*;

/**
 * 部门员工VO
 *
 * @author Heaven96
 * @date 2021/10/13
 */
@Data
@H4nUnionCheck(group = 1,message = "管理部的崽，大于40岁才可以")
@H4nUnionCheck(group = 2,message = "研发部的崽，小于30岁才可以")
@H4nUnionCheck(group = 3 , message = "行政部的崽，年龄必须在20-30岁之间,闭区间")
@H4nUnionCheck(group = 4 , message = "A部的崽，年龄为18")
@H4nUnionCheck(group = 5 , message = "B部的崽，小于20岁才可以")
@H4nUnionCheck(group = 6 , message = "C部的崽，年龄为30 / 40才可以")
@H4nUnionCheck(group = 7 , message = "D部的崽，年龄 <= 60才可以")
@H4nUnionCheck(group = 8 , message = "E部的崽，年龄 >= 30才可以")
public class DepartmentEmployeeVo1 {
    /**
     * 部门
     */
    @H4nCheck(group = 1,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"管理部"})
    @H4nCheck(group = 2,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"研发部"})
    @H4nCheck(group = 3,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"行政部"})
    @H4nCheck(group = 4,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"A部"})
    @H4nCheck(group = 5,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"B部"})
    @H4nCheck(group = 6,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"C部"})
    @H4nCheck(group = 7,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"D部"})
    @H4nCheck(group = 8,logic = IF, field = "#this.name", operator = EQUALS, valueSet = {"E部"})
    @Valid
    private Department department;
    /**
     * 员工
     */
    @H4nCheck(group = 1,logic = THEN, field = "#this.age", operator = GREATER_THAN, valueSet = {"40"})
    @H4nCheck(group = 2,logic = THEN, field = "#this.age", operator = LESS_THAN, valueSet = {"30"})
    @H4nCheck(group = 3,logic = THEN, field = "#this.age", operator = BETWEEN_AND, valueSet = {"[20,30]"})
    @H4nCheck(group = 4,logic = THEN, field = "#this.age", operator = EQUALS, valueSet = {"18"})
    @H4nCheck(group = 5,logic = THEN, field = "#this.age", operator = LESS_THAN, valueSet = {"20"})
    @H4nCheck(group = 6,logic = THEN, field = "#this.age", operator = IN, valueSet = {"30","40"})
    @H4nCheck(group = 7,logic = THEN, field = "#this.age", operator = LESS_THAN_OR_EQUAL_TO, valueSet = {"60"})
    @H4nCheck(group = 8,logic = THEN, field = "#this.age", operator = GREATER_THAN_OR_EQUALS, valueSet = {"30"})
    @Valid
    private List<Employee> employees;
}
