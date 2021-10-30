package site.heaven96.example.entity.single;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.heaven96.validate.common.annotation.H4nFieldCheck;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Teacher {
    @H4nFieldCheck(
            fieldNote = "姓名",
            valueSetOrigin = LegalOrigin.SQL,
            operator = Logic.IN,
            sql = "SELECT 'lisi' FROM DUAL union all SELECT 'zhangsan' FROM DUAL",
            message = "#id $id #.id $.id {id}{idValue}#{id}${id} {getId}#{getId}${getId}  {idValue}#{idValue}${idValue} "
    )
    private String name;


    private int id;

}
