package site.heaven96.example.entity.single;


import lombok.Data;
import site.heaven96.validate.common.annotation.H4nFieldCheck;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.common.enums.ValueSetOrigin;


@Data
public class Teacher {
    @H4nFieldCheck(
            fieldNote = "姓名",
            valueSetOrigin = ValueSetOrigin.SQL_RESULTS,
            operator = Operator.IN,
            sql = "SELECT 'lisi' FROM DUAL union all SELECT 'zhangsan' FROM DUAL",
            message = "#id $id #.id $.id {id}{idValue}#{id}${id} {getId}#{getId}${getId}  {idValue}#{idValue}${idValue} "
    )
    private String name;


    private int id;

}
