package cn.com.luckytry.interview;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Test()  {
       int a = 152;
        double b = a/50f;
       int c = (int) Math.ceil(a/50f);
        System.out.print(c);
    }
}