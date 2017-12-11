package cn.com.luckytry.interview.ui.parsehtml;

/**
 * codeKK文章内容
 * Created by 魏兴 on 2017/11/26.
 */

public class CodeKK extends WebData{
    private static WebData instance;
    private CodeKK(String url) {
        super(url);
    }

    @Override
    public String getText() {
        return doc.select("div.hero-unit").first().text();
    }

    @Override
    public String getgetElements() {
        return doc.select("div.hero-unit").first().html();
    }

    public static WebData getInstance(String url){
        synchronized (CodeKK.class){
            if(instance == null){
                instance = new CodeKK(url);
            }
        }

        return instance;
    }

}
