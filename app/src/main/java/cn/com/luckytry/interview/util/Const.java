package cn.com.luckytry.interview.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * 常量管理类
 * Created by 魏兴 on 2017/8/27.
 */

public class Const {

    public static final String SPTAG = "interview";
    public static final String THEME_MOUDLE = "theme_moudle";




    /**
     * 整理加载html文件
     * @param html
     * @return
     */
    public static String getData(Context context,String html){
        String bgcolor = "";
        String textcolor = "";
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //切换主题
        if(mode == Configuration.UI_MODE_NIGHT_YES) {//夜间模式
            bgcolor = "background: #444D55;\n";
            textcolor = " color: #ffffff;\n";
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {//日间模式
            bgcolor = "background: #f0f0f0;\n";
            textcolor = " color: #333333;\n";
        }
        String source = "<!DOCTYPE html>\n"
                +"<html lang='en'>" +
                "<head><meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "<meta http-equiv='X-UA-Compatible' >" +
                "<style type=\"text/css\">\n" +
                "      body{\n" +
                           bgcolor +
                            textcolor +
                "          font-family: \"RobotoDraft\", \"Roboto\", \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" +
                "          padding-bottom: 3px;"+
                "          }\n" +
                "       pre, code { \n" +
                "           padding: 5px 10px; \n" +
                "           line-height:102px\n"+
                "           border: 1px solid #eee;   \n" +
                "           white-space:-moz-pre-wrap; /* Mozilla, since 1999 */    \n" +
                "           white-space:-pre-wrap; /* Opera 4-6 */    \n" +
                "           white-space:-o-pre-wrap; /* Opera 7 */    \n" +
                "           word-wrap:break-word; /* Internet Explorer 5.5+ */  \n" +
                "           white-space: pre-wrap; /* Firefox */ "+
                "           word-break:break-all; \n" +
                "       } \n" +
                "       article {\n" +
                "           width: 100%;\n" +
                "           position: fixed;\n" +
                "           top: 0;\n" +
                "           left: 0;\n"+
                "           right: 0;\n"+
                "           bottom: 0;\n" +
                "           overflow: auto;\n" +
                "           }"+
                "img{\n" +
                "\t \t\t\tmax-width: 100%;\n" +
                "\t \t\t\tvertical-align: middle;\n" +
                "\t \t\t\tborder: 0;\n" +
                "\t \t\t}"+
                "</style>" +
                "</head>" +
                "<body>" +
                "<article class='markdown-body entry-content' itemprop='text'>"+
                html+"" +
                "</article></body></html>";
        return source;
    }
}
