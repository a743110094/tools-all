package site.heaven96.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@RunWith(BlockJUnit4ClassRunner.class)

public class MyTest {
    @Test
    public void test1(){
        Integer a = 1;
        int b = 1;
        Double c = 1.00;
        double d = 1.0;
        BigDecimal e = new BigDecimal("2.22");
        long f = 1L;
        Long g = 1L;
        byte h = 2;
        Byte i = 1;
        Short j = 2 ;


        System.out.println(sss(a));
        System.out.println(sss(b));
        System.out.println(sss(c));
        System.out.println(sss(d));
        System.out.println(sss(e));
        System.out.println(sss(f));
        System.out.println(sss(g));
        System.out.println(sss(h));
        System.out.println(sss(i));
        System.out.println(sss(j));

        java.sql.Date sad  = Date.valueOf(LocalDate.now());

        System.out.println(sad instanceof java.util.Date);
    }

    public boolean sss(Object obj){
        return obj instanceof Number;
    }
}
