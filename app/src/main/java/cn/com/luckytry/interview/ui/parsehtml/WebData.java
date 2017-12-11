package cn.com.luckytry.interview.ui.parsehtml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 网址解析管理
 * Created by 魏兴 on 2017/11/26.
 */

public abstract class WebData {

    protected String url;
    protected Document doc;

    public WebData(String url) {
        this.url = url;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文章文本内容
     * @return
     */
    public abstract String getText();

    public abstract String getgetElements();
}
