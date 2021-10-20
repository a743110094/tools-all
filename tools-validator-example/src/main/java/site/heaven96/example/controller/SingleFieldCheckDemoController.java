package site.heaven96.example.controller;


import org.springframework.web.bind.annotation.*;
import site.heaven96.example.entity.single.Student;
import site.heaven96.example.entity.single.Teacher;

import javax.validation.Valid;

/**
 * 单字段校验 演示控制器
 *
 * @author lgw3488
 * @ignore
 * @date 2021/10/13
 */
@RestController
@RequestMapping("h4n/single")
public class SingleFieldCheckDemoController {

    /**
     * 字段必须为固定值集 自定义错误消息
     *
     * @param student 学生
     * @return {@code Result<String>}
     * @mock {
     * "name":"sdas"
     * }
     */
    @GetMapping("test1")
    public String test1(@RequestBody @Valid Student student) {
        return student.toString();
    }


    /**
     * 字段必须为固定的sql  默认错误消息
     *
     * @param teacher 教师
     * @return {@code Result<String>}
     * @mock {
     * "name":"zhangsan8",
     * "id" : 1
     * }
     */
    @PostMapping("test2")
    public String test2(@RequestBody @Valid Teacher teacher) {
        return teacher.toString();
    }

}
