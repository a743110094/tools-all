package site.heaven96.example;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import site.heaven96.validate.util.H4nSqlParamsUtil;

import java.util.Arrays;

@RunWith(BlockJUnit4ClassRunner.class)

public class SqlBuilderTest {
    @Test
    public void test() {
        System.out.println(new Gson().toJson(H4nSqlParamsUtil.params(
                "select * from user where name = ? and age = ? and c = ?",
                Arrays.asList("张三", "lisi", "是打发史蒂夫", "张三"),
                10,
                "A"
                )));


        H4nSqlParamsUtil.parseSql("SELECT '开发者' FROM DUAL WHERE '开发者' = #{this.role} ");

    }

}
