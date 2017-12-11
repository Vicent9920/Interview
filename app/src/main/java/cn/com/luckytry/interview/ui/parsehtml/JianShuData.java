package cn.com.luckytry.interview.ui.parsehtml;

/**
 * 简书网址内容
 * Created by 魏兴 on 2017/11/26.
 */

public class JianShuData extends WebData{
    private static WebData instance;
    private JianShuData(String url) {
        super(url);
    }

    @Override
    public String getText() {
        return doc.select("div.show-content").first().text();
    }

    @Override
    public String getgetElements() {
        return doc.select("div.show-content").first().html();
    }
    public static WebData getInstance(String url){
        synchronized (JianShuData.class){
            if(instance == null){
                instance = new JianShuData(url);
            }
        }

        return instance;
    }
}
