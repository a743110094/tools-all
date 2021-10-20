package site.heaven96.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.heaven96.example.vo.union.DepartmentEmployeeVo1;
import site.heaven96.example.vo.union.DepartmentEmployeeVo2;

import javax.validation.Valid;

/**
 * 联合检查演示控制器
 *
 * @author lgw3488
 * @ignore
 * @date 2021/10/13
 */
@RestController
@RequestMapping("h4n/union")
public class UnionCheckDemoController {
    /**
     * 测试1
     * <p>
     * 若部门为管理部 员工必须大于40岁
     *
     * @param vo1 代码1
     * @return {@code Result<String>}
     * @mock {
     * "name":"sdas"
     * }
     */
    @GetMapping("test1")
    public Object test1(@RequestBody @Valid DepartmentEmployeeVo1 vo1) {
        return vo1;
    }

    /**
     * 测试1
     * <p>
     * 若部门为管理部 员工必须大于40岁
     *
     * @param vo1 代码1
     * @return {@code Result<String>}
     * @mock {
     * "name":"sdas"
     * }
     */
    @GetMapping("test2")
    public Object test2(@RequestBody @Valid DepartmentEmployeeVo2 vo2) {
        return vo2;
    }
}
