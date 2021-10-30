package site.heaven96.example.vo.union;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.heaven96.example.entity.union.Department;
import site.heaven96.example.entity.union.Employee;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;

import javax.validation.Valid;
import java.io.Serializable;
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
@H4nUnionCheck(group = 1, message = "管理部的崽，大于40岁才可以")
@H4nUnionCheck(group = 2, message = "研发部的崽，小于30岁才可以")
@H4nUnionCheck(group = 3, message = "行政部的崽，年龄必须在20-30岁之间,闭区间")
@H4nUnionCheck(group = 4, message = "A部的崽，年龄为18")
@H4nUnionCheck(group = 5, message = "B部的崽，小于20岁才可以")
@H4nUnionCheck(group = 6, message = "C部的崽，年龄为30 / 40才可以")
@H4nUnionCheck(group = 7, message = "D部的崽，年龄 <= 60才可以")
@H4nUnionCheck(group = 8, message = "E部的崽，年龄 >= 30才可以")
@H4nUnionCheck(group = 9, message = "F部的崽，名字不能为空 null ")
@H4nUnionCheck(group = 10, message = "G部的崽，名字不能为空 hasText")
@H4nUnionCheck(group = 11, message = "H部的崽，生日必须晚于2020年1月1日")
public class DepartmentEmployeeVo1 implements Serializable {
    /**
     * 部门
     */
    @H4nCheck(group = 1, logic = IF, field = "#this.name", operator = EQUALS, legal = "管理部")
    @H4nCheck(group = 2, logic = IF, field = "#this.name", operator = EQUALS, legal = "研发部")
    @H4nCheck(group = 3, logic = IF, field = "#this.name", operator = EQUALS, legal = "行政部")
    @H4nCheck(group = 4, logic = IF, field = "#this.name", operator = EQUALS, legal = "A部")
    @H4nCheck(group = 5, logic = IF, field = "#this.name", operator = EQUALS, legal = "B部")
    @H4nCheck(group = 6, logic = IF, field = "#this.name", operator = EQUALS, legal = "C部")
    @H4nCheck(group = 7, logic = IF, field = "#this.name", operator = EQUALS, legal = "D部")
    @H4nCheck(group = 8, logic = IF, field = "#this.name", operator = EQUALS, legal = "E部")
    @H4nCheck(group = 9, logic = IF, field = "#this.name", operator = EQUALS, legal = "F部")
    @H4nCheck(group = 10, logic = IF, field = "#this.name", operator = EQUALS, legal = "G部")
    @H4nCheck(group = 11, logic = IF, field = "#this.name", operator = EQUALS, legal = "H部")
    @Valid
    private Department department;
    /**
     * 员工
     */
    @H4nCheck(group = 1, logic = THEN, field = "#this[all].age", operator = GREATER_THAN, legal = {"40"})
    @H4nCheck(group = 2, logic = THEN, field = "#this[all].age", operator = LESS_THAN, legal = {"30"})
    @H4nCheck(group = 3, logic = THEN, field = "#this[all].age", operator = BETWEEN_AND, legal = {"[20,30]"})
    @H4nCheck(group = 4, logic = THEN, field = "#this[all].age", operator = EQUALS, legal = {"18"})
    @H4nCheck(group = 5, logic = THEN, field = "#this[all].age", operator = LESS_THAN, legal = {"20"})
    @H4nCheck(group = 6, logic = THEN, field = "#this[all].age", operator = IN, legal = {"30", "40"})
    @H4nCheck(group = 7, logic = THEN, field = "#this[all].age", operator = LESS_THAN_OR_EQUAL_TO, legal = {"60"})
    @H4nCheck(group = 8, logic = THEN, field = "#this[all].age", operator = GREATER_THAN_OR_EQUALS, legal = {"30"})
    @H4nCheck(group = 9, logic = THEN, field = "#this[all].name", operator = NOT_NULL)
    @H4nCheck(group = 10, logic = THEN, field = "#this[all].name", operator = HAS_TEXT)
    @H4nCheck(group = 11, logic = THEN, field = "#this[all].dateOfBirth", operator = GREATER_THAN, legal = {"2020-01-01"})
    @Valid
    private List<Employee> employees;

}
