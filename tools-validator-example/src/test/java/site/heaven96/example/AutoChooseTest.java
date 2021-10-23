package site.heaven96.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import site.heaven96.validate.common.enums.ValueSetOrigin;
import site.heaven96.validate.util.AutoChooseUtil;

@RunWith(BlockJUnit4ClassRunner.class)
public class AutoChooseTest {

    @Test
    public void test1(){
        ValueSetOrigin auto = ValueSetOrigin.AUTO;

        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"SELECT"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"SELECT * from dual"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"select"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"#{this.tes}"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"#{}"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"SELECT","asd"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"[0,0]"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"0"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"0.000"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"Bob"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"b"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"bbbb"}));
        System.out.println(AutoChooseUtil.valueSetOrigin(auto,new String[]{"bbbbbbbbbbb"}));
    }
}
