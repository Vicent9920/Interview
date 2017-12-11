package cn.com.luckytry.interview.ui.parsehtml;

/**
 * WebData 工厂类
 * Created by 魏兴 on 2017/11/26.
 */

public class WebFactory {
    public static WebData getWebData(String url){

        if(url.startsWith("http://www.jianshu.com")){
            return JianShuData.getInstance(url);
        }else if(url.startsWith("https://github.com/")){
            return GithubData.getInstance(url);
        }else if(url.startsWith("http://p.codekk.com/blogs")){
            return CodeKK.getInstance(url);
        }else {
            return null;
        }
    }
}
