package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MyClass {
    public static void main(String[] args) {
        String html = "<!DOCTYPE html>\n" +
                "<html class=\"turbolinks-progress-bar\"><head>\n" +
                "  <title>Android ��������ʦ����ָ�� - Wiki</title>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"https://diycode.b0.upaiyun.com/assets/favicon-6200a4759942579bd403b10460911e7576be30b6ac3d88b6454a2a2544be70b5.ico\">\n" +
                "  <meta name=\"keywords\" content=\"/\">\n" +
                "  <meta name=\"description\" content=\"/\">\n" +
                "  <meta name=\"apple-mobile-web-app-capable\" content=\"no\">\n" +
                "  <meta content=\"True\" name=\"HandheldFriendly\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">\n" +
                "  <link rel=\"alternate\" type=\"application/rss+xml\" title=\"����������\" href=\"https://www.diycode.cc/topics/feed\">\n" +
                "  <meta property=\"wb:webmaster\" content=\"d0ef27f708a14400\">\n" +
                "  <link rel=\"stylesheet\" media=\"screen\" href=\"https://diycode.b0.upaiyun.com/assets/front-b64c4625672b5e1c4398f796ad39f408dd206728481fe2dfc6496aec80daa85f.css\" data-turbolinks-track=\"true\">\n" +
                "  <meta name=\"action-cable-url\" content=\"/cable\">\n" +
                "  <script async=\"\" src=\"https://www.google-analytics.com/analytics.js\"></script><script src=\"https://diycode.b0.upaiyun.com/assets/app-eab2e84baf7da2ad5cd02e0772763bee74ad836eab14d1f5bbc2dc56c8aa4fff.js\" data-turbolinks-track=\"true\"></script><style>html.turbolinks-progress-bar::before {\n" +
                "  content: '';\n" +
                "  position: fixed;\n" +
                "  top: 0;\n" +
                "  left: 0;\n" +
                "  z-index: 2000;\n" +
                "  background-color: #0076ff;\n" +
                "  height: 3px;\n" +
                "  opacity: 0.99;\n" +
                "  width: 0%;\n" +
                "  transition: width 300ms ease-out, opacity 150ms ease-in;\n" +
                "  transform: translate3d(0,0,0);\n" +
                "}</style>\n" +
                "  <meta name=\"csrf-param\" content=\"authenticity_token\">\n" +
                "<meta name=\"csrf-token\" content=\"xCUjJOYe/XcDUMGb/sc/L/WkOXLB0G94XIaymQ3evel5UwodhV486e1zuAfXL9SJPyklqBQJwAl6lAE86OPNKA==\">\n" +
                "  <script src=\"//ruby-china-files.b0.upaiyun.com/assets/d3.min.js\" data-turbolinks-track=\"reload\" type=\"text/javascript\"></script>\n" +
                "  <script src=\"//ruby-china-files.b0.upaiyun.com/assets/cal-heatmap.min.js\" data-turbolinks-track=\"reload\" type=\"text/javascript\"></script>\n" +
                "  <link rel=\"dns-prefetch\" href=\"//assets.youhost.com\">\n" +
                "\n" +
                "</head>\n" +
                "<body data-controller-name=\"pages\">\n" +
                "  <div class=\"header\">\n" +
                "    <nav class=\"navbar navbar-inverse navbar-static-top navbar-default\">\n" +
                "      <div class=\"container\">\n" +
                "        <div class=\"navbar-header\" id=\"navbar-header\" data-turbolinks-permanent=\"\">\n" +
                "          <a href=\"/\" class=\"navbar-brand\"><img src=\"https://diycode.b0.upaiyun.com/photo/2016/a616a86eafa8b6c7a43fc7bbc8f041f1.png\" alt=\"A616a86eafa8b6c7a43fc7bbc8f041f1\"></a>\n" +
                "        </div>\n" +
                "\n" +
                "        <span id=\"main-nav-menu\" data-turbolinks-temporary=\"\">\n" +
                "          <ul class=\"nav navbar-nav\"><li class=\"\"><a href=\"/topics\">����</a></li><li class=\"\"><a href=\"/projects\">��Ŀ</a></li><li class=\"\"><a href=\"/news\">News</a></li><li class=\"\"><a href=\"/trends\">Github Ranking</a></li><li class=\"active\"><a href=\"/wiki\">Wiki</a></li><li class=\"\"><a href=\"/sites\">��վ</a></li></ul>\n" +
                "        </span>\n" +
                "\n" +
                "        <ul class=\"nav user-bar navbar-nav navbar-right\">\n" +
                "  <li><a href=\"/account/sign_up\">ע��</a></li>\n" +
                "  <li><a href=\"/account/sign_in\">��¼</a></li>\n" +
                "</ul>\n" +
                "\n" +
                "\n" +
                "        <ul class=\"nav navbar-nav navbar-right\">\n" +
                "          <li class=\"nav-search hidden-xs\">\n" +
                "            <form class=\"navbar-form form-search active\" action=\"/search\" method=\"GET\">\n" +
                "              <div class=\"form-group\">\n" +
                "                <input class=\"form-control\" name=\"q\" type=\"text\" value=\"\" placeholder=\"������վ����\">\n" +
                "              </div>\n" +
                "              <i class=\"fa btn-search fa-search\"></i>\n" +
                "            </form>\n" +
                "          </li>\n" +
                "        </ul>\n" +
                "      </div>\n" +
                "    </nav>\n" +
                "  </div>\n" +
                "\n" +
                "  <div id=\"main\" class=\"main-container container\">\n" +
                "    \n" +
                "    \n" +
                "    <div id=\"page-show\" class=\"panel panel-default\">\n" +
                "    <div class=\"panel-heading\">\n" +
                "      <a href=\"/wiki/androidinterview/comments\">\n" +
                "        10 ������\n" +
                "      </a>,\n" +
                "      �汾 13�� �������� <abbr class=\"timeago\" title=\"2017-04-24T18:03:11+08:00\">4 ��ǰ</abbr>\n" +
                "      <span class=\"pull-right opts\">\n" +
                "        \n" +
                "      </span>\n" +
                "    </div>\n" +
                "\n" +
                "  <div class=\"panel-body markdown\">\n" +
                "    <article>\n" +
                "      <h2 id=\"��Android ��������ʦ����ָ�ϡ�\">��Android ��������ʦ����ָ�ϡ�</h2>\n" +
                "<p><a href=\"http://www.diycode.cc/wiki/androidinterview\" target=\"_blank\">��Android ��������ʦ����ָ�� LearningNotes ��</a>��������֪��Android��������ʦ<a href=\"https://www.zhihu.com/people/tao-cheng-1\" target=\"_blank\">�ճ�</a>����<a href=\"https://www.zhihu.com/people/liang-anant\" target=\"_blank\">����ȫ</a>���ײ��֡���ҿ���ȥ֪����ע����λ���ĵ����ꡣ���ָ�ϰ����˴󲿷�Android�����Ļ���������֪ʶ���������԰���׼�����Ե�ͬѧ��Ҳ���԰�������ѧϰ�͹�����ͬѧ�����Լ���֪ʶ�㡣���ĵ�ͬѧҲ���԰����ǲ������ơ�</p>\n" +
                "\n" +
                "<p>���Ƽ�һ��֪��ר����ÿ�ո��¹����� Android ����������£�<a href=\"https://zhuanlan.zhihu.com/diy-android\" target=\"_blank\">Android �������¾�ѡ</a></p>\n" +
                "<hr />\n" +
                "<h2 id=\"��һ����\">��һ����</h2>\n" +
                "<ul class=\"part\">\n" +
                "\t\n" +
                "<li class=\"packge\">\n" +
                "\t\n" +
                "<p><a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part1/Android\" target=\"_blank\">Android(��׿)</a></p>\n" +
                "\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86.md\" target=\"_blank\">Android����֪ʶ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md\" target=\"_blank\">Android�ڴ�й©�ܽ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Handler%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E5%88%86%E6%9E%90%E5%8F%8A%E8%A7%A3%E5%86%B3.md\" target=\"_blank\">Handler�ڴ�й©���������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96.md\" target=\"_blank\">Android�����Ż�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Listview%E8%AF%A6%E8%A7%A3.md\" target=\"_blank\">ListView���</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Recyclerview%E5%92%8CListview%E7%9A%84%E5%BC%82%E5%90%8C.md\" target=\"_blank\">RecyclerView��ListView����ͬ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Asynctask%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md\" target=\"_blank\">AsyncTaskԴ�����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/%E6%8F%92%E4%BB%B6%E5%8C%96%E6%8A%80%E6%9C%AF%E5%AD%A6%E4%B9%A0.md\" target=\"_blank\">���������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/%E8%87%AA%E5%AE%9A%E4%B9%89%E6%8E%A7%E4%BB%B6.md\" target=\"_blank\">�Զ���ؼ�</a></li>\n" +
                "<li><a href=\"http://www.jianshu.com/p/e99b5e8bd67b\" target=\"_blank\">�¼��ַ�����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/ANR%E9%97%AE%E9%A2%98.md\" target=\"_blank\">ANR����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Art%E5%92%8CDalvik%E5%8C%BA%E5%88%AB.md\" target=\"_blank\">Art��Dalvik������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%85%B3%E4%BA%8Eoom%E7%9A%84%E8%A7%A3%E5%86%B3%E6%96%B9%E6%A1%88.md\" target=\"_blank\">Android����OOM�Ľ������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Fragment.md\" target=\"_blank\">Fragment</a></li>\n" +
                "<li><a href=\"https://github.com/xxv/android-lifecycle\" target=\"_blank\">Activity&amp;Fragment</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/SurfaceView.md\" target=\"_blank\">SurfaceView</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%87%A0%E7%A7%8D%E8%BF%9B%E7%A8%8B.md\" target=\"_blank\">Android���ֽ���</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/APP%E5%90%AF%E5%8A%A8%E8%BF%87%E7%A8%8B.md\" target=\"_blank\">APP��������</a></li>\n" +
                "<li>Activity���������Լ�����չʾ����</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%9B%BE%E7%89%87%E4%B8%AD%E7%9A%84%E4%B8%89%E7%BA%A7%E7%BC%93%E5%AD%98.md\" target=\"_blank\">ͼƬ��������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/%E7%83%AD%E4%BF%AE%E5%A4%8D%E6%8A%80%E6%9C%AF.md\" target=\"_blank\">���޸���ԭ��</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/AIDL.md\" target=\"_blank\">AIDL</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Binder%E6%9C%BA%E5%88%B6.md\" target=\"_blank\">Binder����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Zygote%E5%92%8CSystem%E8%BF%9B%E7%A8%8B%E7%9A%84%E5%90%AF%E5%8A%A8%E8%BF%87%E7%A8%8B.md\" target=\"_blank\">Zygote��System���̵���������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/MVC%2CMVP%2CMVVM%E7%9A%84%E5%8C%BA%E5%88%AB.md\" target=\"_blank\">Android�е�MVC��MVP��MVVM</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/MVP.md\" target=\"_blank\">MVP</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%BC%80%E6%9C%BA%E8%BF%87%E7%A8%8B.md\" target=\"_blank\">Android��������</a></li>\n" +
                "<li><a href=\"http://www.jianshu.com/p/c1a3a881a144\" target=\"_blank\">RetrofitԴ�����</a></li>\n" +
                "<li><a href=\"http://frodoking.github.io/2015/10/10/android-glide/\" target=\"_blank\">GlideԴ�����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/EventBus%E7%94%A8%E6%B3%95%E8%AF%A6%E8%A7%A3.md\" target=\"_blank\">EventBus�÷����</a></li>\n" +
                "<li><a href=\"http://p.codekk.com/blogs/detail/54cfab086c4761e5001b2538\" target=\"_blank\">EventBusԴ�����</a></li>\n" +
                "<li><a href=\"http://www.open-open.com/lib/view/open1438065400878.html\" target=\"_blank\">Android ORM ���֮ greenDAO ʹ���ĵ�</a></li>\n" +
                "<li><a href=\"http://gank.io/post/560e15be2dca930e00da1083\" target=\"_blank\">RxJava</a></li>\n" +
                "<li>���һ��ͼƬ�첽���ػ��淽��</li>\n" +
                "<li>Android UI����</li>\n" +
                "<li><a href=\"http://wuxiaolong.me/categories/Gradle/\" target=\"_blank\">Gradle</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.md\" target=\"_blank\">��©��ȱ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Git%E6%93%8D%E4%BD%9C.md\" target=\"_blank\">Git����</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"packge\">\n" +
                "<p ><a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part1/DesignPattern\" target=\"_blank\">DesignPattern(���ģʽ)</a></p>\n" +
                "\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E5%B8%B8%E8%A7%81%E7%9A%84%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99.md\" target=\"_blank\">�����������ԭ��</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">����ģʽ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/Builder%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">Builderģʽ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E5%8E%9F%E5%9E%8B%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">ԭ��ģʽ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E7%AE%80%E5%8D%95%E5%B7%A5%E5%8E%82.md\" target=\"_blank\">�򵥹���</a></li>\n" +
                "<li>��������ģʽ</li>\n" +
                "<li>���󹤳�ģʽ</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">����ģʽ</a></li>\n" +
                "<li>״̬ģʽ</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E8%B4%A3%E4%BB%BB%E9%93%BE%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">������ģʽ</a></li>\n" +
                "<li>������ģʽ</li>\n" +
                "<li>����ģʽ</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E8%A7%82%E5%AF%9F%E8%80%85%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">�۲���ģʽ</a></li>\n" +
                "<li>����¼ģʽ</li>\n" +
                "<li>������ģʽ</li>\n" +
                "<li>ģ�巽��ģʽ</li>\n" +
                "<li>������ģʽ</li>\n" +
                "<li>�н���ģʽ</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">����ģʽ</a></li>\n" +
                "<li>���ģʽ</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E9%80%82%E9%85%8D%E5%99%A8%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">������ģʽ</a></li>\n" +
                "<li>װ��ģʽ</li>\n" +
                "<li>��Ԫģʽ</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/DesignPattern/%E5%A4%96%E8%A7%82%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">���ģʽ</a></li>\n" +
                "<li>�Ž�ģʽ</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<hr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<hr>\n" +
                "<h2 id=\"�ڶ�����\">�ڶ�����</h2>\n" +
                "<ul class=\"part\">\n" +
                "\t\n" +
                "<li class=\"packge\">\n" +
                "<p><a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part2/JavaSE\" target=\"_blank\">JavaSE(Java����)</a></p>\n" +
                "\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/Java%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86.md\" target=\"_blank\">Java����֪ʶ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/Java%E4%B8%AD%E7%9A%84%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F.md\" target=\"_blank\">Java�е��ڴ�й©</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/String%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md\" target=\"_blank\">StringԴ�����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/Java%E9%9B%86%E5%90%88%E6%A1%86%E6%9E%B6.md\" target=\"_blank\">Java���Ͽ��</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/ArrayList%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90.md\" target=\"_blank\">ArrayListԴ������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/LinkedList%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90.md\" target=\"_blank\">LinkedListԴ������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/Vector%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90.md\" target=\"_blank\">VectorԴ������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/HashMap%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90.md\" target=\"_blank\">HashMapԴ������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/HashTable%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90.md\" target=\"_blank\">HashTableԴ������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/LinkedHashMap%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90.md\" target=\"_blank\">LinkedHashMapԴ������</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"packge\">\n" +
                "<p><a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part2/JVM\" target=\"_blank\">JVM(Java�����)</a></p>\n" +
                "\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JVM/JVM.md\" target=\"_blank\">JVM����֪ʶ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JVM/JVM%E7%B1%BB%E5%8A%A0%E8%BD%BD%E6%9C%BA%E5%88%B6.md\" target=\"_blank\">JVM����ػ���</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JVM/Java%E5%86%85%E5%AD%98%E5%8C%BA%E5%9F%9F%E4%B8%8E%E5%86%85%E5%AD%98%E6%BA%A2%E5%87%BA.md\" target=\"_blank\">Java�ڴ��������ڴ����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JVM/%E5%9E%83%E5%9C%BE%E5%9B%9E%E6%94%B6%E7%AE%97%E6%B3%95.md\" target=\"_blank\">���������㷨</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"packge\">\n" +
                "<p><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/Java%E5%B9%B6%E5%8F%91.md\" target=\"_blank\">JavaConcurrent(Java����)</a></p>\n" +
                "\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/Java%E5%B9%B6%E5%8F%91%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86.md\" target=\"_blank\">Java��������֪ʶ</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E7%94%9F%E4%BA%A7%E8%80%85%E5%92%8C%E6%B6%88%E8%B4%B9%E8%80%85%E9%97%AE%E9%A2%98.md\" target=\"_blank\">�����ߺ�����������</a></li>\n" +
                "<li>\n" +
                "<a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/Thread%E5%92%8CRunnable%E5%AE%9E%E7%8E%B0%E5%A4%9A%E7%BA%BF%E7%A8%8B%E7%9A%84%E5%8C%BA%E5%88%AB.md\" target=\"_blank\">Thread��Runnableʵ�ֶ��̵߳�����</a><br>\n" +
                "</li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E7%BA%BF%E7%A8%8B%E4%B8%AD%E6%96%AD.md\" target=\"_blank\">�߳��ж�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E5%AE%88%E6%8A%A4%E7%BA%BF%E7%A8%8B%E4%B8%8E%E9%98%BB%E5%A1%9E%E7%BA%BF%E7%A8%8B.md\" target=\"_blank\">�ػ��߳��������߳�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/Synchronized.md\" target=\"_blank\">synchronized</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E7%8E%AF%E5%A2%83%E4%B8%AD%E5%AE%89%E5%85%A8%E4%BD%BF%E7%94%A8%E9%9B%86%E5%90%88API.md\" target=\"_blank\">���̻߳����а�ȫʹ�ü���API</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E5%AE%9E%E7%8E%B0%E5%86%85%E5%AD%98%E5%8F%AF%E8%A7%81%E7%9A%84%E4%B8%A4%E7%A7%8D%E6%96%B9%E6%B3%95%E6%AF%94%E8%BE%83%EF%BC%9A%E5%8A%A0%E9%94%81%E5%92%8Cvolatile%E5%8F%98%E9%87%8F.md\" target=\"_blank\">ʵ���ڴ�ɼ������ַ����Ƚϣ�������volatile����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E6%AD%BB%E9%94%81.md\" target=\"_blank\">����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E5%8F%AF%E9%87%8D%E5%85%A5%E5%86%85%E7%BD%AE%E9%94%81.md\" target=\"_blank\">������������</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/%E4%BD%BF%E7%94%A8wait:notify:notifyall%E5%AE%9E%E7%8E%B0%E7%BA%BF%E7%A8%8B%E9%97%B4%E9%80%9A%E4%BF%A1.md\" target=\"_blank\">ʹ��wait/notify/notifyAllʵ���̼߳�ͨ��</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaConcurrent/NIO.md\" target=\"_blank\">NIO</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>\n" +
                "\n" +
                "<hr>\n" +
                "<h2 id=\"��������\">��������</h2>\n" +
                "<ul class=\"part\">\n" +
                "\t<li class=\"packge\">\n" +
                "\t\t<p><a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part3/DataStructure\" target=\"_blank\">DataStructure(���ݽṹ)</a></p>\n" +
                "\t\t<ul>\n" +
                "\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/DataStructure/%E6%95%B0%E7%BB%84.md\" target=\"_blank\">����</a></li>\n" +
                "\t\t\t<li>����</li>\n" +
                "\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/DataStructure/%E6%A0%88%E5%92%8C%E9%98%9F%E5%88%97.md\" target=\"_blank\">ջ�Ͷ���</a></li>\n" +
                "\t\t\t<li>�ַ���</li>\n" +
                "\t\t\t<li>��</li>\n" +
                "\t\t\t<li>ͼ</li>\n" +
                "\t\t</ul>\n" +
                "\t</li>\n" +
                "\t<li class=\"packge\">\n" +
                "\t\t<p><a href=\"\" target=\"_blank\">Algorithm(�㷨)</a></p>\t\n" +
                "\t\t<ul>\n" +
                "\t\t\t<li>\n" +
                "\t\t\t\t<a href=\"https://github.com/anAngryAnt/LearningNotes/tree/master/Part3/Algorithm/Sort\" target=\"_blank\">����</a>\t\t\t\t\n" +
                "\t\t\t\t<ul>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/anAngryAnt/LearningNotes/tree/master/Part3/Algorithm/Sort/%E9%80%89%E6%8B%A9%E6%8E%92%E5%BA%8F.md\" target=\"_blank\">ѡ������</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/anAngryAnt/LearningNotes/tree/master/Part3/Algorithm/Sort/%E5%86%92%E6%B3%A1%E6%8E%92%E5%BA%8F.md\" target=\"_blank\">ð������</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/anAngryAnt/LearningNotes/tree/master/Part3/Algorithm/Sort/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F.md\" target=\"_blank\">��������</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/anAngryAnt/LearningNotes/tree/master/Part3/Algorithm/Sort/%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F.md\" target=\"_blank\">�鲢����</a></li>\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\t</li>\n" +
                "\t\t\t<li>����\t\n" +
                "\t\t\t\t<ul>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/Lookup/%E9%A1%BA%E5%BA%8F%E6%9F%A5%E6%89%BE.md\" target=\"_blank\">˳�����</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/Lookup/%E6%8A%98%E5%8D%8A%E6%9F%A5%E6%89%BE.md\" target=\"_blank\">�۰����</a></li>\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\t</li>\n" +
                "\t\t\t<li>����ָOffer��\t\n" +
                "\t\t\t\t<ul>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E5%89%91%E6%8C%87Offer/1.%E4%B8%83%E7%A7%8D%E6%96%B9%E5%BC%8F%E5%AE%9E%E7%8E%B0singleton%E6%A8%A1%E5%BC%8F.md\" target=\"_blank\">������2:ʵ��Singletonģʽ</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E5%89%91%E6%8C%87Offer/%E9%9D%A2%E8%AF%95%E9%A2%986%EF%BC%9A%E9%87%8D%E5%BB%BA%E4%BA%8C%E5%8F%89%E6%A0%91.md\" target=\"_blank\">������6���ؽ�������</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E5%89%91%E6%8C%87Offer/%E9%9D%A2%E8%AF%95%E9%A2%9811%EF%BC%9A%E6%95%B0%E5%80%BC%E7%9A%84%E6%95%B4%E6%95%B0%E6%AC%A1%E6%96%B9.md\" target=\"_blank\">������11����ֵ�������η�</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E5%89%91%E6%8C%87Offer/%E9%9D%A2%E8%AF%95%E9%A2%9844%EF%BC%9A%E6%89%91%E5%85%8B%E7%89%8C%E7%9A%84%E9%A1%BA%E5%AD%90.md\" target=\"_blank\">������44���˿��Ƶ�˳��</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E5%89%91%E6%8C%87Offer/%E9%9D%A2%E8%AF%95%E9%A2%9845%EF%BC%9A%E5%9C%86%E5%9C%88%E4%B8%AD%E6%9C%80%E5%90%8E%E5%89%A9%E4%B8%8B%E7%9A%84%E6%95%B0%E5%AD%97.md\" target=\"_blank\">������45��ԲȦ�����ʣ�µ�����</a></li>\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\t</li>\n" +
                "\t\t\t<li>������Ա���Խ�䡷</li>\n" +
                "\t\t\t<li>��LeetCode��\t\n" +
                "\t\t\t\t<ul>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/LeetCode/two-sum.md\" target=\"_blank\">two-sum</a></li>\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\t</li>\n" +
                "\t\t\t<li>������Ա��������ָ��(�����)��\t\t\n" +
                "\t\t\t\t<ul>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E7%A8%8B%E5%BA%8F%E5%91%98%E4%BB%A3%E7%A0%81%E9%9D%A2%E8%AF%95%E6%8C%87%E5%8D%97(%E5%B7%A6%E7%A8%8B%E4%BA%91)/1.%E8%AE%BE%E8%AE%A1%E4%B8%80%E4%B8%AA%E6%9C%89getMin%E5%8A%9F%E8%83%BD%E7%9A%84%E6%A0%88.md\" target=\"_blank\">1.���һ����getMin���ܵ�ջ</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E7%A8%8B%E5%BA%8F%E5%91%98%E4%BB%A3%E7%A0%81%E9%9D%A2%E8%AF%95%E6%8C%87%E5%8D%97(%E5%B7%A6%E7%A8%8B%E4%BA%91)/2.%E7%94%B1%E4%B8%A4%E4%B8%AA%E6%A0%88%E7%BB%84%E6%88%90%E7%9A%84%E9%98%9F%E5%88%97.md\" target=\"_blank\">2.������ջ��ɵĶ���</a></li>\n" +
                "\t\t\t\t\t<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part3/Algorithm/%E7%A8%8B%E5%BA%8F%E5%91%98%E4%BB%A3%E7%A0%81%E9%9D%A2%E8%AF%95%E6%8C%87%E5%8D%97(%E5%B7%A6%E7%A8%8B%E4%BA%91)/3.%E5%A6%82%E4%BD%95%E4%BB%85%E7%94%A8%E9%80%92%E5%BD%92%E5%87%BD%E6%95%B0%E5%92%8C%E6%A0%88%E6%93%8D%E4%BD%9C%E9%80%86%E5%BA%8F%E4%B8%80%E4%B8%AA%E6%A0%88.md\" target=\"_blank\">3.��ν��õݹ麯����ջ��������һ��ջ</a></li>\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\t</li>\n" +
                "\t\t</ul>\n" +
                "\t</li>\n" +
                "</ul>\n" +
                "\n" +
                "<hr>\n" +
                "<h2 id=\"���Ĳ���\">���Ĳ���</h2>\n" +
                "<ul class=\"part\">\n" +
                "\t\n" +
                "<li class=\"packge\">\n" +
                "\t<p>\n" +
                "\t\t<a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part4/Network\" target=\"_blank\">Network(����)</a>\n" +
                "\t</p>\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part4/Network/TCP%E4%B8%8EUDP.md\" target=\"_blank\">TCP/UDP</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part4/Network/Http%E5%8D%8F%E8%AE%AE.md\" target=\"_blank\">HTTP</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part4/Network/Socket.md\" target=\"_blank\">Socket</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>\n" +
                "\t<p>\n" +
                "\t\t<a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part4/OperatingSystem/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F.md\" target=\"_blank\">OperatingSystem(����ϵͳ)</a>\n" +
                "\t</p>\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part4/OperatingSystem/Linux%E7%B3%BB%E7%BB%9F%E7%9A%84IPC.md\" target=\"_blank\">Linuxϵͳ��IPC</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>\n" +
                "\n" +
                "<hr>\n" +
                "\n" +
                "<h2 id=\"���岿��\">���岿��</h2>\n" +
                "\n" +
                "<ul class=\"part\">\n" +
                "<li class=\"packge\">\n" +
                "<p><a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part5/ReadingNotes\" target=\"_blank\">ReadingNotes(����ʼ�)</a></p>\n" +
                "\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAPP%E7%A0%94%E5%8F%91%E5%BD%95%E3%80%8B%E7%AC%AC1%E7%AB%A0%E8%AF%BB%E4%B9%A6%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��APP�з�¼����1�¶���ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAPP%E7%A0%94%E5%8F%91%E5%BD%95%E3%80%8B%E7%AC%AC2%E7%AB%A0%E8%AF%BB%E4%B9%A6%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��APP�з�¼����2�¶���ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B%E7%AC%AC%E4%B8%80%E7%AB%A0%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Android��������̽������һ�±ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B%E7%AC%AC%E4%BA%8C%E7%AB%A0%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Android��������̽�����ڶ��±ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B%E7%AC%AC%E4%B8%89%E7%AB%A0%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Android��������̽���������±ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B%E7%AC%AC%E5%9B%9B%E7%AB%A0%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Android��������̽���������±ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B%E7%AC%AC%E5%85%AB%E7%AB%A0%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Android��������̽�����ڰ��±ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B%E7%AC%AC%E5%8D%81%E4%BA%94%E7%AB%A0%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Android��������̽������ʮ���±ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8A%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3java%E8%99%9A%E6%8B%9F%E6%9C%BA%E3%80%8B%E7%AC%AC12%E7%AB%A0.md\" target=\"_blank\">���������Java���������12��</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AJava%E7%BC%96%E7%A8%8B%E6%80%9D%E6%83%B3%E3%80%8B%E7%AC%AC%E4%B8%80%E7%AB%A0%E8%AF%BB%E4%B9%A6%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Java���˼�롷��һ�¶���ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/ReadingNotes/%E3%80%8AJava%E7%BC%96%E7%A8%8B%E6%80%9D%E6%83%B3%E3%80%8B%E7%AC%AC%E4%BA%8C%E7%AB%A0%E8%AF%BB%E4%B9%A6%E7%AC%94%E8%AE%B0.md\" target=\"_blank\">��Java���˼�롷�ڶ��¶���ʼ�</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part5/Project\" target=\"_blank\">Project(��Ŀ)</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part5/Project/%E9%A1%B9%E7%9B%AE.md\" target=\"_blank\">��Ŀ�ѵ�</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>\n" +
                "<h2 id=\"��������\">��������</h2>\n" +
                "<ul class=\"part\">\n" +
                "<li class=\"packge\">\n" +
                "\t<p>\n" +
                "\t\t<a href=\"https://github.com/GeniusVJR/LearningNotes/tree/master/Part6/InterviewExperience\" target=\"_blank\">InterviewExperience(���Ծ���)</a>\n" +
                "\t</p>\n" +
                "<ul>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part6/InterviewExperience/Alibaba.md\" target=\"_blank\">Alibaba</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part6/InterviewExperience/%E7%BE%8E%E5%9B%A2.md\" target=\"_blank\">����</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part6/InterviewExperience/%E8%B1%8C%E8%B1%86%E8%8D%9A.md\" target=\"_blank\">�㶹��</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part6/InterviewExperience/%E8%9C%BB%E8%9C%93FM.md\" target=\"_blank\">����FM</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part6/InterviewExperience/%E6%96%B0%E6%B5%AA%E5%BE%AE%E5%8D%9A.md\" target=\"_blank\">����΢��</a></li>\n" +
                "<li><a href=\"https://github.com/GeniusVJR/LearningNotes/blob/master/Part6/InterviewExperience/%E7%BD%91%E6%98%93%E6%9D%AD%E7%A0%94.md\" target=\"_blank\">���׺���</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"packge\"><a href=\"https://zhuanlan.zhihu.com/p/20672941\" target=\"_blank\">Resume(����)</a></li>\n" +
                "</ul>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<hr>\n" +
                "<h2 id=\"���߲���\">���߲���</h2>\n" +
                "<ul class=\"part\">\n" +
                "<li class=\"packge\"><a href=\"https://github.com/JackyAndroid/AndroidInterview-Q-A/blob/master/README-CN.md\" target=\"_blank\">����һ�߻�������˾�ڲ��������</a></li>\n" +
                "</ul>\n" +
                "\n" +
                "<p><strong>��ע��</strong>���߲���������Android����ʦ<a href=\"http://weibo.com/u/5885816355\" target=\"_blank\">ī��èjacky</a>����</p>\n" +
                "\n" +
                "<p>��������������õ����ϻ�ӭ��΢�Ÿ����ң������ŵĳ���Ա��΢�źţ�diycodes��Ŀǰ��ע���� 1100 �ˣ�<br>\n" +
                "<img src=\"http://diycode.b0.upaiyun.com/photo/2016/f031fc25263f7294711038efa72ae579.jpg\" title=\"\" alt=\"\"></p>\n" +
                "    </article>\n" +
                "\n" +
                "    <div class=\"social-share-button\" data-title=\"Android ��������ʦ����ָ�� via: @diycode_cc \" data-img=\"\" data-url=\"\" data-desc=\"\" data-via=\"\">\n" +
                "<a rel=\"nofollow \" data-site=\"twitter\" class=\"ssb-icon ssb-twitter\" onclick=\"return SocialShareButton.share(this);\" title=\"����Twitter\" href=\"#\"></a>\n" +
                "<a rel=\"nofollow \" data-site=\"facebook\" class=\"ssb-icon ssb-facebook\" onclick=\"return SocialShareButton.share(this);\" title=\"����Facebook\" href=\"#\"></a>\n" +
                "<a rel=\"nofollow \" data-site=\"google_plus\" class=\"ssb-icon ssb-google_plus\" onclick=\"return SocialShareButton.share(this);\" title=\"����Google+\" href=\"#\"></a>\n" +
                "<a rel=\"nofollow \" data-site=\"weibo\" class=\"ssb-icon ssb-weibo\" onclick=\"return SocialShareButton.share(this);\" title=\"��������΢��\" href=\"#\"></a>\n" +
                "<a rel=\"nofollow \" data-site=\"douban\" class=\"ssb-icon ssb-douban\" onclick=\"return SocialShareButton.share(this);\" title=\"��������\" href=\"#\"></a>\n" +
                "</div>\n" +
                "  </div>\n" +
                "\n" +
                "  <div class=\"editors panel-footer \">\n" +
                "    <h3>��ҳ������:</h3>\n" +
                "    <div class=\"avatars\">\n" +
                "        <a class=\"hacknews_clear\" href=\"/jixiaohua\"><img class=\"media-object avatar-16\" style=\"max-width: 252px;\" src=\"https://diycode.b0.upaiyun.com/user/avatar/2.jpg\" alt=\"2\"></a>\n" +
                "    </div>\n" +
                "    <div class=\"buttons\">\n" +
                "      <a class=\"btn btn-success\" href=\"/wiki/androidinterview/comments#new_comment\">��������</a>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "  </div>\n" +
                "\n" +
                "  <footer class=\"footer\" id=\"footer\" data-turbolinks-permanent=\"\">\n" +
                "    <div class=\"container\">\n" +
                "      <div class=\"media\">\n" +
                "  <div class=\"media-left\" style=\"margin-right:20px;\">\n" +
                "    <img class=\"media-object\" src=\"//diycode.b0.upaiyun.com/photo/2016/ef110d40d2fb44c7f5464bbf121ed9d8.png\" style=\"width:56px;\">\n" +
                "  </div>\n" +
                "  \n" +
                "  <div class=\"media-body\">\n" +
                "    <div class=\"links\">\n" +
                " <a href=\"/wiki/about\">����</a> / \n" +
                "<a href=\"/users\">��Ծ��Ա</a> / \n" +
                "<a href=\"/api\">API</a> / \n" +
                "<a href=\"/news\">Hacker News</a> /  \n" +
                "<a href=\"/tags\">tags</a> /  \n" +
                "<a href=\"/wiki/followus\">��ע����</a>  /  \n" +
                "<a href=\"https://xiaozhuanlan.com\">Сר��</a> \n" +
                "    </div>\n" +
                "    <div class=\"copyright\">\n" +
                "    DiyCode���ڶ࿪���߹�ͬά���������ڹ�����������ʦ�߶˽�������������<br>\n" +
                "    ͼƬ�洢�� <a href=\"https://www.upyun.com/\" target=\"_blank\" style=\"display:inline-block;\" rel=\"twipsy\" title=\"\" data-original-title=\"ͼƬ�洢������������\">�����ƴ洢</a> �ṩ��Inspired by RubyChina��<a href=\"http://www.miitbeian.gov.cn/publish/query/indexFirst.action\" target=\"_blank\"> ��ICP��14008250��</a><br>\n" +
                "    </div>\n" +
                "    <div class=\"links\" style=\"margin-top:8px\" data-no-turbolink=\"\">\n" +
                "       <a href=\"?locale=zh-CN\" rel=\"nofollow\">��������</a> / <a href=\"?locale=zh-TW\" rel=\"nofollow\">���w����</a> / <a href=\"?locale=en\" rel=\"nofollow\">English</a>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\"media-right\" style=\"width:200px; text-align:right;\">\n" +
                "    <a href=\"http://www.ucloud.cn\" target=\"_blank\" rel=\"twipsy\" title=\"\" style=\"display:inline-block;margin-right:5px; margin-top: 5px;\" data-original-title=\"��վ�������� UCloud ����\"><img src=\"//ruby-china-files.b0.upaiyun.com/photo/2015/60202bb15bf6dc06fc8dd7e8baea061c.png\" style=\"height:24px;\"></a>\n" +
                "  </div>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "  </footer>\n" +
                "\n" +
                "  <script type=\"text/javascript\" data-turbolinks-eval=\"false\">\n" +
                "    App.root_url = \"https://www.diycode.cc/\";\n" +
                "    App.asset_url = \"https://diycode.b0.upaiyun.com\";\n" +
                "    App.locale = \"zh-CN\";\n" +
                "  </script>\n" +
                "  <script>\n" +
                "    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" +
                "    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" +
                "    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" +
                "    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');\n" +
                "\n" +
                "    ga('create', 'UA-92073059-1', 'auto');\n" +
                "    ga('send', 'pageview');\n" +
                "  </script>\n" +
                "  \n" +
                "  <div style=\"display:none\">\n" +
                "    <script src=\"https://s11.cnzz.com/z_stat.php?id=1258132733&amp;web_id=1258132733\" language=\"JavaScript\"></script><script src=\"https://c.cnzz.com/core.php?web_id=1258132733&amp;t=z\" charset=\"utf-8\" type=\"text/javascript\"></script><a href=\"http://www.cnzz.com/stat/website.php?web_id=1258132733\" target=\"_blank\" title=\"վ��ͳ��\">վ��ͳ��</a>\n" +
                "  </div>\n" +
                "  <div class=\"zoom-overlay\"></div>\n" +
                "\n" +
                "\n" +
                "</body></html>";

        Document doc = Jsoup.parse(html);
        Elements parts = doc.getElementsByClass("part");
        for (int i = 0; i < parts.size(); i++) {
            Element part = parts.get(i);
            ArrayList<Element> tag = part.select(".packge");
            for (Element element:tag) {
                Element tagP = element.getElementsByTag("p").first();
                if(tagP==null){
                    System.out.println("tag1:"+"��"+i+"����");
                    System.out.println("bean:"+element.text());
                    String href = element.getElementsByTag("a").first().attr("href");
                    System.out.println("href:"+href);
                    break;
                }
                System.out.println("tag1:"+tagP.text());
                Elements beans = element.getElementsByTag("ul").first().getElementsByTag("li");
                for (Element bean:beans) {
                    if(bean.getElementsByTag("ul").first()==null){
                        System.out.println("bean:"+bean.text());
                        if(bean.getElementsByTag("a").size() != 0){
                            String href = bean.getElementsByTag("a").first().attr("href");
                            System.out.println("href:"+href);
                        }
                    }else{
                        String beantag = bean.text();
                        if(beantag.contains(" ")){
                            String[] title = beantag.split(" ");
                            beantag = title[0];
                        }
                        System.out.println("tag2:"+beantag);
                        ArrayList<Element> beanlist = bean.getElementsByTag("ul").first().getElementsByTag("li");
                        if(beanlist.size()!=0){
                            for (Element beanData:beanlist) {
                                System.out.println("bean:"+beantag+"  "+beanData.text());
                                if(beanData.getElementsByTag("a").size() != 0){
                                    String href = beanData.getElementsByTag("a").first().attr("href");
                                    System.out.println("href:"+href);
                                }
                            }
                        }else{

                        }
                    }
                }
            }
        }




    }
}
