package cn.com.luckytry.interview.ui.parsehtml;

/**
 * github 网站内容
 * Created by 魏兴 on 2017/11/26.
 */

public class GithubData extends WebData{
    private static WebData instance;
    private GithubData(String url) {
        super(url);
    }

    @Override
    public String getText() {
        return doc.getElementsByTag("article").text();

    }

    @Override
    public String getgetElements() {
        return doc.getElementsByTag("article").html();
    }

    public static WebData getInstance(String url){
        synchronized (GithubData.class){
            if(instance == null){
                instance = new GithubData(url);
            }
        }

        return instance;
    }
}
