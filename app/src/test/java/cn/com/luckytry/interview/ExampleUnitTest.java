package cn.com.luckytry.interview;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.interview.bean.InterViewMoudle;

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
    public void Test() throws IOException {
        Document doc = Jsoup.connect("http://blog.csdn.net/Vicent_9920/article/details/78797827").get();
        //markdown_views
        Element elementOl = doc.select(".markdown_views").first().select("ol").first();//查找第一个a元素
        ArrayList<Element> elementLis = elementOl.children();
        List<InterViewMoudle> moudles = new ArrayList<>();
        for (int i = 0; i < elementLis.size(); i++) {
            Element item = elementLis.get(i);
            String[] name = item.html().split("<br>");
            ArrayList<Element> itemElementList = item.select("ul").first().children();
            for (int j = 0; j < itemElementList.size(); j++) {
                Element moudle = itemElementList.get(j);
                InterViewMoudle interViewMoudle = new InterViewMoudle();
                interViewMoudle.setFileName(moudle.text());
                String adress = moudle.child(0).attr("href");
                interViewMoudle.setAdress(adress);
                interViewMoudle.setGroup(name[0]);
                moudles.add(interViewMoudle);
            }
        }
        System.out.println(new Gson().toJson(moudles));

    }

}