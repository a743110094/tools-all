package site.heaven96.example.rule;

import cn.hutool.core.util.NumberUtil;
import com.google.gson.Gson;
import site.heaven96.example.entity.union.Department;
import site.heaven96.example.entity.union.Employee;
import site.heaven96.example.vo.union.DepartmentEmployeeVo3;

import java.math.BigDecimal;
import java.util.List;

public class ClassA {
    public boolean check(DepartmentEmployeeVo3 v1){
        if (v1.getDepartment().getName().equals("S部")){
           return v1.getEmployees().stream().allMatch(employee -> NumberUtil.isLess(employee.getAge() , new BigDecimal("40") ));
        }
        return true;
    }
}
