package site.heaven96.example;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import site.heaven96.example.entity.union.Employee;
import site.heaven96.example.vo.union.DepartmentEmployeeVo1;
import site.heaven96.validate.util.SpelUtil;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(BlockJUnit4ClassRunner.class)
public class SpelTest {
    @Test
    public void test() {
        DepartmentEmployeeVo1 departmentEmployeeVo1 = new DepartmentEmployeeVo1();
        Date date = new Date();
        Employee employee = new Employee(3, "casfasdc", new BigDecimal("1"), "role", date);
        Employee employee1 = new Employee(1, "lrx", new BigDecimal("1"), "role", date);
        Employee employee2 = new Employee(1, "yyq", new BigDecimal("1"), "role", date);
        System.out.println(employee.hashCode());
        System.out.println(employee2.hashCode());
        System.out.println(employee1.hashCode());
        departmentEmployeeVo1.setEmployees(
                ListUtil.toList(
                        employee, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2
                        , employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1
                        , employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2
                        , employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2
                        , employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2, employee1, employee2, employee, employee1, employee2
                        , employee1, employee2, employee, employee1, employee2
                )
        );

        System.out.println(SpelUtil.get("#this.employees[all].name", departmentEmployeeVo1));
        System.out.println(SpelUtil.get("#this.name", employee1));
    }

    @Test
    public void test2() {
        String s = "sfa.sagf.sdfsa[all].name";
        String sub = StrUtil.subAfter(s, '.', true);
        System.out.println(sub);
    }
}
