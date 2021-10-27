package site.heaven96.example.entity.single;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.heaven96.validate.common.annotation.H4nFieldCheck;
import site.heaven96.validate.common.enums.ValueSetOrigin;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Student {

    @H4nFieldCheck(
            valueSetOrigin = ValueSetOrigin.FIXED_VALUE,
            valueSet = {"张三"},
            message = "姓名的值必须在值集 {valueSet} 范围内 id = {id} #{id} ${id}")
    private String name;
    @TableId
    @TableField(value = "stu_id")
    private int id;
}
