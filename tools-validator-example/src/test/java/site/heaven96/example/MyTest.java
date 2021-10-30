package site.heaven96.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Arrays;
import java.util.stream.Collectors;


@RunWith(BlockJUnit4ClassRunner.class)

public class MyTest {
    @Test
    public void test1() {
        String[] legal = new String[]{"1", "2", "3", "sss"};
        String[] legal2 = new String[]{"1", "2", "3", "sss"};
        String[] legal3 = new String[]{"1", "2", "3", "sssw"};
        System.out.println(Arrays.stream(legal).collect(Collectors.toList()));
    }

    public boolean sss(Object obj){
        return obj instanceof Number;
    }
}
