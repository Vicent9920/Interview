package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by ��?? on 2017/8/26.
 */

public class Test1 {
    public static void main(String[] args) {
        String html = "<div id=\"readme\" class=\"readme blob instapaper_body\">\n" +
                "    <article class=\"markdown-body entry-content\" itemprop=\"text\"><p>#Android �ڴ�й©�ܽ�</p>\n" +
                "<p>�ڴ�����Ŀ�ľ����������ڿ�������ô��Ч�ı������ǵ�Ӧ�ó����ڴ�й©�����⡣�ڴ�й©��Ҷ���İ���ˣ��򵥴��׵Ľ������Ǹñ��ͷŵĶ���û���ͷţ�һֱ��ĳ����ĳЩʵ��������ȴ���ٱ�ʹ�õ��� GC ���ܻ��ա�����Լ��Ķ��˴�����ص��ĵ����ϣ��������� �ܽ� �������������һ������ѧϰ��Ҳ���Լ�һ����ʾ���Ժ� coding ʱ��ô������Щ��������Ӧ�õ������������</p>\n" +
                "<p>�һ�� java �ڴ�й©�Ļ���֪ʶ��ʼ����ͨ������������˵�� Android �����ڴ�й©�ĸ���ԭ���Լ�������ù���������Ӧ���ڴ�й©����������ܽᡣ</p>\n" +
                "<p>##Java �ڴ�������</p>\n" +
                "<p>Java ��������ʱ���ڴ�������������,�ֱ��Ǿ�̬����,ջʽ����,�Ͷ�ʽ���䣬��Ӧ�ģ����ִ洢����ʹ�õ��ڴ�ռ���Ҫ�ֱ��Ǿ�̬�洢����Ҳ�Ʒ���������ջ���Ͷ�����</p>\n" +
                "<ul>\n" +
                "<li>\n" +
                "<p>��̬�洢����������������Ҫ��ž�̬���ݡ�ȫ�� static ���ݺͳ���������ڴ��ڳ������ʱ���Ѿ�����ã������ڳ������������ڼ䶼���ڡ�</p>\n" +
                "</li>\n" +
                "<li>\n" +
                "<p>ջ�� ����������ִ��ʱ���������ڵľֲ����������а��������������͡���������ã�����ջ�ϴ��������ڷ���ִ�н���ʱ��Щ�ֲ����������е��ڴ潫���Զ����ͷš���Ϊջ�ڴ�������������ڴ�������ָ��У�Ч�ʺܸߣ����Ƿ�����ڴ��������ޡ�</p>\n" +
                "</li>\n" +
                "<li>\n" +
                "<p>���� �� �ֳƶ�̬�ڴ���䣬ͨ������ָ�ڳ�������ʱֱ�� new �������ڴ棬Ҳ���Ƕ����ʵ�����ⲿ���ڴ��ڲ�ʹ��ʱ������ Java ������������������ա�</p>\n" +
                "</li>\n" +
                "</ul>\n" +
                "<p>##ջ��ѵ�����</p>\n" +
                "<p>�ڷ������ڶ���ģ��ֲ�������һЩ�������͵ı����Ͷ�������ñ��������ڷ�����ջ�ڴ��з���ġ�����һ�η������ж���һ������ʱ��Java �ͻ���ջ��Ϊ�ñ��������ڴ�ռ䣬�������ñ�����������󣬸ñ���Ҳ����Ч�ˣ�����������ڴ�ռ�Ҳ�����ͷŵ������ڴ�ռ���Ա�����ʹ�á�</p>\n" +
                "<p>���ڴ�������������� new �����Ķ��󣨰����ö������е����г�Ա�����������顣�ڶ��з�����ڴ棬���� Java �������������Զ������ڶ��в�����һ��������߶���󣬻�������ջ�ж���һ������ı��������������ȡֵ����������߶����ڶ��ڴ��е��׵�ַ���������ı���������������˵�����ñ��������ǿ���ͨ��������ñ��������ʶ��еĶ���������顣</p>\n" +
                "<p>�ٸ�����:</p>\n" +
                "<pre><code>public class Sample {\n" +
                "    int s1 = 0;\n" +
                "    Sample mSample1 = new Sample();\n" +
                "\n" +
                "    public void method() {\n" +
                "        int s2 = 1;\n" +
                "        Sample mSample2 = new Sample();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "Sample mSample3 = new Sample();\n" +
                "</code></pre>\n" +
                "<p>Sample ��ľֲ����� s2 �����ñ��� mSample2 ���Ǵ�����ջ�У��� mSample2 ָ��Ķ����Ǵ����ڶ��ϵġ�\n" +
                "mSample3 ָ��Ķ���ʵ�����ڶ��ϣ����������������г�Ա���� s1 �� mSample1�������Լ�������ջ�С�</p>\n" +
                "<p>���ۣ�</p>\n" +
                "<p>�ֲ������Ļ����������ͺ����ô洢��ջ�У����õĶ���ʵ��洢�ڶ��С����� ��Ϊ�������ڷ����еı��������������淽����������</p>\n" +
                "<p>��Ա����ȫ���洢����У����������������ͣ����ú����õĶ���ʵ�壩���� ��Ϊ���������࣬������վ���Ҫ��new����ʹ�õġ�</p>\n" +
                "<p>�˽��� Java ���ڴ����֮�������������� Java ����ô�����ڴ�ġ�</p>\n" +
                "<p>##Java����ι����ڴ�</p>\n" +
                "<p>Java���ڴ������Ƕ���ķ�����ͷ����⡣�� Java �У�����Ա��Ҫͨ���ؼ��� new Ϊÿ�����������ڴ�ռ� (�������ͳ���)�����еĶ����ڶ� (Heap)�з���ռ䡣���⣬������ͷ����� GC ������ִ�еġ��� Java �У��ڴ�ķ������ɳ�����ɵģ����ڴ���ͷ����� GC ��ɵģ�������֧�����ߵķ���ȷʵ���˳���Ա�Ĺ�������ͬʱ����Ҳ������JVM�Ĺ�������Ҳ�� Java ���������ٶȽ�����ԭ��֮һ����Ϊ��GC Ϊ���ܹ���ȷ�ͷŶ���GC ������ÿһ�����������״̬��������������롢���á������á���ֵ�ȣ�GC ����Ҫ���м�ء�</p>\n" +
                "<p>���Ӷ���״̬��Ϊ�˸���׼ȷ�ء���ʱ���ͷŶ��󣬶��ͷŶ���ĸ���ԭ����Ǹö����ٱ����á�</p>\n" +
                "<p>Ϊ�˸������ GC �Ĺ���ԭ�����ǿ��Խ�������Ϊ����ͼ�Ķ��㣬�����ù�ϵ����Ϊͼ������ߣ�����ߴ�������ָ�����������⣬ÿ���̶߳��������Ϊһ��ͼ����ʼ���㣬���������� main ���̿�ʼִ�У���ô��ͼ������ main ���̶��㿪ʼ��һ�ø��������������ͼ�У�������ɴ�Ķ�������Ч����GC����������Щ�������ĳ������ (��ͨ��ͼ)����������㲻�ɴ�(ע�⣬��ͼΪ����ͼ)����ô������Ϊ���(��Щ)�����ٱ����ã����Ա� GC ���ա�\n" +
                "���£����Ǿ�һ������˵�����������ͼ��ʾ�ڴ�������ڳ����ÿһ��ʱ�̣����Ƕ���һ������ͼ��ʾJVM���ڴ���������������ͼ��������߳������е���6�е�ʾ��ͼ��</p>\n" +
                "<p><a href=\"https://camo.githubusercontent.com/ba01b8ae9af4a5e588251316c826bf3e0e695f35/687474703a2f2f7777772e69626d2e636f6d2f646576656c6f706572776f726b732f636e2f6a6176612f6c2d4a6176614d656d6f72794c65616b2f312e676966\" target=\"_blank\"><img src=\"https://camo.githubusercontent.com/ba01b8ae9af4a5e588251316c826bf3e0e695f35/687474703a2f2f7777772e69626d2e636f6d2f646576656c6f706572776f726b732f636e2f6a6176612f6c2d4a6176614d656d6f72794c65616b2f312e676966\" alt=\"\" data-canonical-src=\"http://www.ibm.com/developerworks/cn/java/l-JavaMemoryLeak/1.gif\" style=\"max-width:100%;\"></a></p>\n" +
                "<p>Javaʹ������ͼ�ķ�ʽ�����ڴ����������������ѭ�������⣬���������������໥���ã�ֻҪ���Ǻ͸����̲��ɴ�ģ���ôGCҲ�ǿ��Ի������ǵġ����ַ�ʽ���ŵ��ǹ����ڴ�ľ��Ⱥܸߣ�����Ч�ʽϵ͡�����һ�ֳ��õ��ڴ��������ʹ�ü�����������COMģ�Ͳ��ü�������ʽ����������������ͼ��ȣ������е�(���Ѵ���ѭ�����õ�����)����ִ��Ч�ʺܸߡ�</p>\n" +
                "<p>##ʲô��Java�е��ڴ�й¶</p>\n" +
                "<p>��Java�У��ڴ�й©���Ǵ���һЩ������Ķ�����Щ���������������ص㣬���ȣ���Щ�����ǿɴ�ģ���������ͼ�У�����ͨ·����������������Σ���Щ���������õģ��������Ժ󲻻���ʹ����Щ�����������������������������Щ����Ϳ����ж�ΪJava�е��ڴ�й©����Щ���󲻻ᱻGC�����գ�Ȼ����ȴռ���ڴ档</p>\n" +
                "<p>��C++�У��ڴ�й©�ķ�Χ����һЩ����Щ���󱻷������ڴ�ռ䣬Ȼ��ȴ���ɴ����C++��û��GC����Щ�ڴ潫��Զ�ղ���������Java�У���Щ���ɴ�Ķ�����GC������գ���˳���Ա����Ҫ�����ⲿ�ֵ��ڴ�й¶��</p>\n" +
                "<p>ͨ�����������ǵ�֪������C++������Ա��Ҫ�Լ�����ߺͶ��㣬������Java����Աֻ��Ҫ����߾Ϳ�����(����Ҫ��������ͷ�)��ͨ�����ַ�ʽ��Java����˱�̵�Ч�ʡ�</p>\n" +
                "<p><a href=\"https://camo.githubusercontent.com/4845ffe2ed44807b01b7bb93647dbff85de6300f/687474703a2f2f7777772e69626d2e636f6d2f646576656c6f706572776f726b732f636e2f6a6176612f6c2d4a6176614d656d6f72794c65616b2f322e676966\" target=\"_blank\"><img src=\"https://camo.githubusercontent.com/4845ffe2ed44807b01b7bb93647dbff85de6300f/687474703a2f2f7777772e69626d2e636f6d2f646576656c6f706572776f726b732f636e2f6a6176612f6c2d4a6176614d656d6f72794c65616b2f322e676966\" alt=\"\" data-canonical-src=\"http://www.ibm.com/developerworks/cn/java/l-JavaMemoryLeak/2.gif\" style=\"max-width:100%;\"></a></p>\n" +
                "<p>��ˣ�ͨ�����Ϸ���������֪����Java��Ҳ���ڴ�й©������Χ��C++ҪСһЩ����ΪJava�������ϱ�֤���κζ����ǿɴ�ģ����еĲ��ɴ������GC����</p>\n" +
                "<p>���ڳ���Ա��˵��GC������͸���ģ����ɼ��ġ���Ȼ������ֻ�м����������Է���GC����������GC�ĺ���System.gc()�����Ǹ���Java���Թ淶���壬 �ú�������֤JVM�������ռ���һ����ִ�С���Ϊ����ͬ��JVMʵ���߿���ʹ�ò�ͬ���㷨����GC��ͨ����GC���̵߳����ȼ���ϵ͡�JVM����GC�Ĳ���Ҳ�кܶ��֣��е����ڴ�ʹ�õ���һ���̶�ʱ��GC�ſ�ʼ������Ҳ�ж�ʱִ�еģ��е���ƽ��ִ��GC���е����ж�ʽִ��GC����ͨ����˵�����ǲ���Ҫ������Щ��������һЩ�ض��ĳ��ϣ�GC��ִ��Ӱ��Ӧ�ó�������ܣ�������ڻ���Web��ʵʱϵͳ����������Ϸ�ȣ��û���ϣ��GCͻȻ�ж�Ӧ�ó���ִ�ж������������գ���ô������Ҫ����GC�Ĳ�������GC�ܹ�ͨ��ƽ���ķ�ʽ�ͷ��ڴ棬���罫�������շֽ�Ϊһϵ�е�С����ִ�У�Sun�ṩ��HotSpot JVM��֧����һ���ԡ�</p>\n" +
                "<p>ͬ������һ�� Java �ڴ�й©�ĵ������ӣ�</p>\n" +
                "<pre><code>Vector v = new Vector(10);\n" +
                "for (int i = 1; i &lt; 100; i++) {\n" +
                "    Object o = new Object();\n" +
                "    v.add(o);\n" +
                "    o = null;   \n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>����������У�����ѭ������Object���󣬲���������Ķ������һ�� Vector �У�������ǽ����ͷ����ñ�����ô Vector ��Ȼ���øö��������������� GC ��˵�ǲ��ɻ��յġ���ˣ����������뵽Vector �󣬻������ Vector ��ɾ������򵥵ķ������ǽ� Vector ��������Ϊ null��</p>\n" +
                "<p><strong>��ϸJava�е��ڴ�й©</strong></p>\n" +
                "<p>1.Java�ڴ���ջ���</p>\n" +
                "<p>�����������Ե��ڴ���䷽ʽ������Ҫ�����������ڴ����ʵ��ַ��Ҳ���Ƿ���һ��ָ�뵽�ڴ����׵�ַ��Java�ж����ǲ���new���߷���ķ��������ģ���Щ����Ĵ��������ڶѣ�Heap���з���ģ����ж���Ļ��ն�����Java�����ͨ���������ջ�����ɵġ�GCΪ���ܹ���ȷ�ͷŶ��󣬻���ÿ�����������״���������ǵ����롢���á������á���ֵ��״�����м�أ�Java��ʹ������ͼ�ķ������й����ڴ棬ʵʱ��ض����Ƿ���Դﵽ��������ɵ����ͽ�����գ�����Ҳ������������ѭ�������⡣��Java�����У��ж�һ���ڴ�ռ��Ƿ���������ռ���׼��������һ���Ǹ��������˿�ֵnull��������û�е��ù�����һ���Ǹ�����������ֵ���������·������ڴ�ռ䡣</p>\n" +
                "<p>2.Java�ڴ�й©�����ԭ��</p>\n" +
                "<p>�ڴ�й©��ָ���ö��󣨲���ʹ�õĶ��󣩳���ռ���ڴ�����ö�����ڴ�ò�����ʱ�ͷţ��Ӷ�����ڴ�ռ���˷ѳ�Ϊ�ڴ�й©���ڴ�й¶��ʱ�������Ҳ��ײ�������������߾Ͳ�֪�������ڴ�й¶������ʱҲ������أ�����ʾ��Out of memory��j</p>\n" +
                "<p>Java�ڴ�й©�ĸ���ԭ����ʲô�أ����������ڵĶ�����ж��������ڶ�������þͺܿ��ܷ����ڴ�й©�����ܶ��������ڶ����Ѿ�������Ҫ��������Ϊ���������ڳ����������ö����²��ܱ����գ������Java���ڴ�й©�ķ���������������Ҫ�����¼����ࣺ</p>\n" +
                "<p>1����̬�����������ڴ�й©��</p>\n" +
                "<p>��HashMap��Vector�ȵ�ʹ�������׳����ڴ�й¶����Щ��̬�������������ں�Ӧ�ó���һ�£����������õ����еĶ���ObjectҲ���ܱ��ͷţ���Ϊ����Ҳ��һֱ��Vector�������š�</p>\n" +
                "<p>����</p>\n" +
                "<pre><code>Static Vector v = new Vector(10);\n" +
                "for (int i = 1; i&lt;100; i++)\n" +
                "{\n" +
                "Object o = new Object();\n" +
                "v.add(o);\n" +
                "o = null;\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>����������У�ѭ������Object ���󣬲���������Ķ������һ��Vector �У���������ͷ����ñ���o=null������ôVector ��Ȼ���øö���������������GC ��˵�ǲ��ɻ��յġ���ˣ����������뵽Vector �󣬻������Vector ��ɾ������򵥵ķ������ǽ�Vector��������Ϊnull��</p>\n" +
                "<p>2������������Ķ������Ա��޸ĺ��ٵ���remove()����ʱ�������á�</p>\n" +
                "<p>���磺</p>\n" +
                "<pre><code>public static void main(String[] args)\n" +
                "{\n" +
                "Set&lt;Person&gt; set = new HashSet&lt;Person&gt;();\n" +
                "Person p1 = new Person(\"��ɮ\",\"pwd1\",25);\n" +
                "Person p2 = new Person(\"�����\",\"pwd2\",26);\n" +
                "Person p3 = new Person(\"��˽�\",\"pwd3\",27);\n" +
                "set.add(p1);\n" +
                "set.add(p2);\n" +
                "set.add(p3);\n" +
                "System.out.println(\"�ܹ���:\"+set.size()+\" ��Ԫ��!\"); //������ܹ���:3 ��Ԫ��!\n" +
                "p3.setAge(2); //�޸�p3������,��ʱp3Ԫ�ض�Ӧ��hashcodeֵ�����ı�\n" +
                "\n" +
                "set.remove(p3); //��ʱremove����������ڴ�й©\n" +
                "\n" +
                "set.add(p3); //������ӣ���Ȼ��ӳɹ�\n" +
                "System.out.println(\"�ܹ���:\"+set.size()+\" ��Ԫ��!\"); //������ܹ���:4 ��Ԫ��!\n" +
                "for (Person person : set)\n" +
                "{\n" +
                "System.out.println(person);\n" +
                "}\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>3��������</p>\n" +
                "<p>��java ����У����Ƕ���Ҫ�ͼ������򽻵���ͨ��һ��Ӧ�õ��л��õ��ܶ�����������ǻ����һ���ؼ�������addXXXListener()�ȷ��������Ӽ����������������ͷŶ����ʱ��ȴû�м�סȥɾ����Щ���������Ӷ��������ڴ�й©�Ļ��ᡣ</p>\n" +
                "<p>4����������</p>\n" +
                "<p>�������ݿ����ӣ�dataSourse.getConnection()������������(socket)��io���ӣ���������ʽ�ĵ�������close���������������ӹرգ������ǲ����Զ���GC ���յġ�����Resultset ��Statement ������Բ�������ʽ���գ���Connection һ��Ҫ��ʽ���գ���ΪConnection ���κ�ʱ���޷��Զ����գ���Connectionһ�����գ�Resultset ��Statement ����ͻ�����ΪNULL���������ʹ�����ӳأ�����Ͳ�һ���ˣ�����Ҫ��ʽ�عر����ӣ���������ʽ�عر�Resultset Statement ���󣨹ر�����һ��������һ��Ҳ��رգ�������ͻ���ɴ�����Statement �����޷��ͷţ��Ӷ������ڴ�й©�����������һ�㶼����try����ȥ�����ӣ���finally�����ͷ����ӡ�</p>\n" +
                "<p>5���ڲ�����ⲿģ�������</p>\n" +
                "<p>�ڲ���������ǱȽ�����������һ�֣�����һ��û�ͷſ��ܵ���һϵ�еĺ�������û���ͷš��������Ա��ҪС���ⲿģ�鲻��������ã��������ԱA ����A ģ�飬������B ģ���һ�������磺\n" +
                "public void registerMsg(Object b);\n" +
                "���ֵ��þ�Ҫ�ǳ�С���ˣ�������һ�����󣬺ܿ���ģ��B�ͱ����˶Ըö�������ã���ʱ�����Ҫע��ģ��B �Ƿ��ṩ��Ӧ�Ĳ���ȥ�����á�</p>\n" +
                "<p>6������ģʽ</p>\n" +
                "<p>����ȷʹ�õ���ģʽ�������ڴ�й©��һ���������⣬���������ڳ�ʼ������JVM���������������д��ڣ��Ծ�̬�����ķ�ʽ�������������������ⲿ�����ã���ô������󽫲��ܱ�JVM�������գ������ڴ�й©��������������ӣ�</p>\n" +
                "<pre><code>class A{\n" +
                "public A(){\n" +
                "B.getInstance().setA(this);\n" +
                "}\n" +
                "....\n" +
                "}\n" +
                "//B����õ���ģʽ\n" +
                "class B{\n" +
                "private A a;\n" +
                "private static B instance=new B();\n" +
                "public B(){}\n" +
                "public static B getInstance(){\n" +
                "return instance;\n" +
                "}\n" +
                "public void setA(A a){\n" +
                "this.a=a;\n" +
                "}\n" +
                "//getter...\n" +
                "} \n" +
                "</code></pre>\n" +
                "<p>��ȻB����singletonģʽ��������һ��A��������ã������A��Ķ��󽫲��ܱ����ա����������A�Ǹ��Ƚϸ��ӵĶ�����߼������ͻᷢ��ʲô���</p>\n" +
                "<h2><a id=\"user-content-android�г������ڴ�й©����\" class=\"anchor\" href=\"#android�г������ڴ�й©����\" aria-hidden=\"true\"><svg aria-hidden=\"true\" class=\"octicon octicon-link\" height=\"16\" version=\"1.1\" viewBox=\"0 0 16 16\" width=\"16\"><path fill-rule=\"evenodd\" d=\"M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z\"></path></svg></a>##Android�г������ڴ�й©����</h2>\n" +
                "<p>###������й©</p>\n" +
                "<p>������������������Ԫ�صķ�������û����Ӧ��ɾ�����ƣ������ڴ汻ռ�á���������������ȫ���Եı��� (�������еľ�̬���ԣ�ȫ���Ե� map �ȼ��о�̬���û� final һֱָ����)����ôû����Ӧ��ɾ�����ƣ��ܿ��ܵ��¼�����ռ�õ��ڴ�ֻ����������������ĵ������Ӿ�������һ���������Ȼʵ������������Ŀ�п϶�����д��ô 2B �Ĵ��룬���Բ�ע�⻹�Ǻ����׳�������������������Ƕ�ϲ��ͨ�� HashMap ��һЩ����֮����£����������Ҫ����һЩ���ۡ�</p>\n" +
                "<p>###������ɵ��ڴ�й©</p>\n" +
                "<p>���ڵ����ľ�̬����ʹ�����������ڸ�Ӧ�õ���������һ�������������ʹ�ò�ǡ���Ļ�������������ڴ�й©����������һ�����͵����ӣ�</p>\n" +
                "<pre><code>public class AppManager {\n" +
                "private static AppManager instance;\n" +
                "private Context context;\n" +
                "private AppManager(Context context) {\n" +
                "this.context = context;\n" +
                "}\n" +
                "public static AppManager getInstance(Context context) {\n" +
                "if (instance == null) {\n" +
                "instance = new AppManager(context);\n" +
                "}\n" +
                "return instance;\n" +
                "}\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>����һ����ͨ�ĵ���ģʽ�����������������ʱ��������Ҫ����һ��Context���������Context���������ڵĳ���������Ҫ��</p>\n" +
                "<p>1�������ʱ������� Application �� Context����Ϊ Application ���������ھ�������Ӧ�õ��������ڣ������⽫û���κ����⡣</p>\n" +
                "<p>2�������ʱ������� Activity �� Context������� Context ����Ӧ�� Activity �˳�ʱ�����ڸ� Context �����ñ��������������У����������ڵ�������Ӧ�ó�����������ڣ����Ե�ǰ Activity �˳�ʱ�����ڴ沢���ᱻ���գ�������й©�ˡ�</p>\n" +
                "<p>��ȷ�ķ�ʽӦ�ø�Ϊ�������ַ�ʽ��</p>\n" +
                "<pre><code>public class AppManager {\n" +
                "private static AppManager instance;\n" +
                "private Context context;\n" +
                "private AppManager(Context context) {\n" +
                "this.context = context.getApplicationContext();// ʹ��Application ��context\n" +
                "}\n" +
                "public static AppManager getInstance(Context context) {\n" +
                "if (instance == null) {\n" +
                "instance = new AppManager(context);\n" +
                "}\n" +
                "return instance;\n" +
                "}\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>��������д���� Context �����ô������ˣ�</p>\n" +
                "<pre><code>����� Application �����һ����̬������getContext() ���� Application �� context��\n" +
                "\n" +
                "...\n" +
                "\n" +
                "context = getApplicationContext();\n" +
                "\n" +
                "...\n" +
                "   /**\n" +
                "     * ��ȡȫ�ֵ�context\n" +
                "     * @return ����ȫ��context����\n" +
                "     */\n" +
                "    public static Context getContext(){\n" +
                "        return context;\n" +
                "    }\n" +
                "\n" +
                "public class AppManager {\n" +
                "private static AppManager instance;\n" +
                "private Context context;\n" +
                "private AppManager() {\n" +
                "this.context = MyApplication.getContext();// ʹ��Application ��context\n" +
                "}\n" +
                "public static AppManager getInstance() {\n" +
                "if (instance == null) {\n" +
                "instance = new AppManager();\n" +
                "}\n" +
                "return instance;\n" +
                "}\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>###�����ڲ���/�Ǿ�̬�ڲ�����첽�߳�</p>\n" +
                "<p>�Ǿ�̬�ڲ��ഴ����̬ʵ����ɵ��ڴ�й©</p>\n" +
                "<p>�е�ʱ�����ǿ��ܻ�������Ƶ����Activity�У�Ϊ�˱����ظ�������ͬ��������Դ�����ܻ��������д����</p>\n" +
                "<pre><code>        public class MainActivity extends AppCompatActivity {\n" +
                "        private static TestResource mResource = null;\n" +
                "        @Override\n" +
                "        protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_main);\n" +
                "        if(mManager == null){\n" +
                "        mManager = new TestResource();\n" +
                "        }\n" +
                "        //...\n" +
                "        }\n" +
                "        class TestResource {\n" +
                "        //...\n" +
                "        }\n" +
                "        }\n" +
                "</code></pre>\n" +
                "<p>��������Activity�ڲ�������һ���Ǿ�̬�ڲ���ĵ�����ÿ������Activityʱ����ʹ�øõ��������ݣ�������Ȼ��������Դ���ظ���������������д��ȴ������ڴ�й©����Ϊ�Ǿ�̬�ڲ���Ĭ�ϻ�����ⲿ������ã����÷Ǿ�̬�ڲ����ִ�����һ����̬��ʵ������ʵ�����������ں�Ӧ�õ�һ��������͵����˸þ�̬ʵ��һֱ����и�Activity�����ã�����Activity���ڴ���Դ�����������ա���ȷ������Ϊ��</p>\n" +
                "<p>�����ڲ�����Ϊ��̬�ڲ���򽫸��ڲ����ȡ������װ��һ�������������Ҫʹ��Context���밴�������Ƽ���ʹ��Application �� Context����Ȼ��Application �� context �������ܵģ�����Ҳ����������ã�������Щ�ط������ʹ�� Activity �� Context������Application��Service��Activity���ߵ�Context��Ӧ�ó������£�</p>\n" +
                "<p><a href=\"https://camo.githubusercontent.com/dee4aecb8a80c4e73337b56ee01cbffa2a8049dd/687474703a2f2f696d672e626c6f672e6373646e2e6e65742f32303135313132333134343232363334393f73706d3d353137362e3130303233392e626c6f67636f6e742e392e437455316334\" target=\"_blank\"><img src=\"https://camo.githubusercontent.com/dee4aecb8a80c4e73337b56ee01cbffa2a8049dd/687474703a2f2f696d672e626c6f672e6373646e2e6e65742f32303135313132333134343232363334393f73706d3d353137362e3130303233392e626c6f67636f6e742e392e437455316334\" alt=\"\" data-canonical-src=\"http://img.blog.csdn.net/20151123144226349?spm=5176.100239.blogcont.9.CtU1c4\" style=\"max-width:100%;\"></a></p>\n" +
                "<p>���У� NO1��ʾ Application �� Service ��������һ�� Activity��������Ҫ����һ���µ� task ������С������� Dialog ���ԣ�ֻ���� Activity �в��ܴ���</p>\n" +
                "<p>###�����ڲ���</p>\n" +
                "<p>android����������̳�ʵ��Activity/Fragment/View����ʱ�����ʹ���������࣬�����첽�̳߳����ˣ���ҪС���ˣ����û���κδ�ʩ����һ���ᵼ��й¶</p>\n" +
                "<pre><code>    public class MainActivity extends Activity {\n" +
                "    ...\n" +
                "    Runnable ref1 = new MyRunable();\n" +
                "    Runnable ref2 = new Runnable() {\n" +
                "        @Override\n" +
                "        public void run() {\n" +
                "\n" +
                "        }\n" +
                "    };\n" +
                "       ...\n" +
                "    }\n" +
                "</code></pre>\n" +
                "<p>ref1��ref2�������ǣ�ref2ʹ���������ڲ��ࡣ��������������ʱ���������õ��ڴ棺</p>\n" +
                "<p><a href=\"https://camo.githubusercontent.com/2b1a52551d828d9640f23ee7c2802476b02ccec3/687474703a2f2f696d67322e746263646e2e636e2f4c312f3436312f312f666230356666366432653638663330396239346464383433353263383161636665306165383339653f73706d3d353137362e3130303233392e626c6f67636f6e742e31302e437455316334\" target=\"_blank\"><img src=\"https://camo.githubusercontent.com/2b1a52551d828d9640f23ee7c2802476b02ccec3/687474703a2f2f696d67322e746263646e2e636e2f4c312f3436312f312f666230356666366432653638663330396239346464383433353263383161636665306165383339653f73706d3d353137362e3130303233392e626c6f67636f6e742e31302e437455316334\" alt=\"\" data-canonical-src=\"http://img2.tbcdn.cn/L1/461/1/fb05ff6d2e68f309b94dd84352c81acfe0ae839e?spm=5176.100239.blogcont.10.CtU1c4\" style=\"max-width:100%;\"></a></p>\n" +
                "<p>���Կ�����ref1ûʲô�ر�ġ�</p>\n" +
                "<p>��ref2����������ʵ�ֶ����������һ�����ã�</p>\n" +
                "<p>this$0�������ָ��MainActivity.this��Ҳ����˵��ǰ��MainActivityʵ���ᱻref2���У��������������ٴ���һ���첽�̣߳����̺߳ʹ�Acitivity�������ڲ�һ�µ�ʱ�򣬾������Activity��й¶��</p>\n" +
                "<p>###Handler ��ɵ��ڴ�й©</p>\n" +
                "<p>Handler ��ʹ����ɵ��ڴ�й©����Ӧ��˵����Ϊ�����ˣ��ܶ�ʱ������Ϊ�˱��� ANR ���������߳̽��к�ʱ�������ڴ�������������߷�װһЩ����ص���api������Handler�������� Handler �������ܵģ����� Handler ��ʹ�ô����дһ���淶���п�������ڴ�й©�����⣬����֪�� Handler��Message �� MessageQueue �����໥������һ��ģ���һ Handler ���͵� Message ��δ��������� Message ���������� Handler ���󽫱��߳� MessageQueue һֱ���С�</p>\n" +
                "<p>���� Handler ���� TLS(Thread Local Storage) ����, �������ں� Activity �ǲ�һ�µġ��������ʵ�ַ�ʽһ����ѱ�֤�� View ���� Activity ���������ڱ���һ�£��ʺ����׵����޷���ȷ�ͷš�</p>\n" +
                "<p>�ٸ����ӣ�</p>\n" +
                "<pre><code>    public class SampleActivity extends Activity {\n" +
                "\n" +
                "    private final Handler mLeakyHandler = new Handler() {\n" +
                "    @Override\n" +
                "    public void handleMessage(Message msg) {\n" +
                "      // ...\n" +
                "    }\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "    super.onCreate(savedInstanceState);\n" +
                "\n" +
                "    // Post a message and delay its execution for 10 minutes.\n" +
                "    mLeakyHandler.postDelayed(new Runnable() {\n" +
                "      @Override\n" +
                "      public void run() { /* ... */ }\n" +
                "    }, 1000 * 60 * 10);\n" +
                "\n" +
                "    // Go back to the previous Activity.\n" +
                "    finish();\n" +
                "    }\n" +
                "    }\n" +
                "</code></pre>\n" +
                "<p>�ڸ� SampleActivity ��������һ���ӳ�10����ִ�е���Ϣ Message��mLeakyHandler ���� push ������Ϣ���� MessageQueue ����� Activity �� finish() ��ʱ���ӳ�ִ������� Message ����������������߳��У������и� Activity �� Handler ���ã����Դ�ʱ finish() ���� Activity �Ͳ��ᱻ�����˴Ӷ�����ڴ�й©���� Handler Ϊ�Ǿ�̬�ڲ��࣬��������ⲿ������ã����������ָ SampleActivity����</p>\n" +
                "<p>�޸��������� Activity �б���ʹ�÷Ǿ�̬�ڲ��࣬�����������ǽ� Handler ����Ϊ��̬�ģ��������ڸ� Activity ���������ھ��޹��ˡ�ͬʱͨ�������õķ�ʽ���� Activity������ֱ�ӽ� Activity ��Ϊ context ����ȥ����������룺</p>\n" +
                "<pre><code>public class SampleActivity extends Activity {\n" +
                "\n" +
                "  /**\n" +
                "   * Instances of static inner classes do not hold an implicit\n" +
                "   * reference to their outer class.\n" +
                "   */\n" +
                "  private static class MyHandler extends Handler {\n" +
                "    private final WeakReference&lt;SampleActivity&gt; mActivity;\n" +
                "\n" +
                "    public MyHandler(SampleActivity activity) {\n" +
                "      mActivity = new WeakReference&lt;SampleActivity&gt;(activity);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void handleMessage(Message msg) {\n" +
                "      SampleActivity activity = mActivity.get();\n" +
                "      if (activity != null) {\n" +
                "        // ...\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  private final MyHandler mHandler = new MyHandler(this);\n" +
                "\n" +
                "  /**\n" +
                "   * Instances of anonymous classes do not hold an implicit\n" +
                "   * reference to their outer class when they are \"static\".\n" +
                "   */\n" +
                "  private static final Runnable sRunnable = new Runnable() {\n" +
                "      @Override\n" +
                "      public void run() { /* ... */ }\n" +
                "  };\n" +
                "\n" +
                "  @Override\n" +
                "  protected void onCreate(Bundle savedInstanceState) {\n" +
                "    super.onCreate(savedInstanceState);\n" +
                "\n" +
                "    // Post a message and delay its execution for 10 minutes.\n" +
                "    mHandler.postDelayed(sRunnable, 1000 * 60 * 10);\n" +
                "\n" +
                "    // Go back to the previous Activity.\n" +
                "    finish();\n" +
                "  }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>���������Ƽ�ʹ�þ�̬�ڲ��� + WeakReference ���ַ�ʽ��ÿ��ʹ��ǰע���пա�</p>\n" +
                "<p>ǰ���ᵽ�� WeakReference����������ͼ򵥵�˵һ�� Java ����ļ����������͡�</p>\n" +
                "<p>Java�����õķ����� Strong reference, SoftReference, WeakReference, PhatomReference ���֡�</p>\n" +
                "<p><a href=\"https://camo.githubusercontent.com/068950c506eddc68677d6a84b5d7ffe1c03c6cf9/68747470733a2f2f67772e616c6963646e2e636f6d2f7470732f5442315536544e4c565858585863685846585858585858585858582d3634342d3534362e6a7067\" target=\"_blank\"><img src=\"https://camo.githubusercontent.com/068950c506eddc68677d6a84b5d7ffe1c03c6cf9/68747470733a2f2f67772e616c6963646e2e636f6d2f7470732f5442315536544e4c565858585863685846585858585858585858582d3634342d3534362e6a7067\" alt=\"\" data-canonical-src=\"https://gw.alicdn.com/tps/TB1U6TNLVXXXXchXFXXXXXXXXXX-644-546.jpg\" style=\"max-width:100%;\"></a></p>\n" +
                "<p>��AndroidӦ�õĿ����У�Ϊ�˷�ֹ�ڴ�������ڴ���һЩռ���ڴ������������ڽϳ��Ķ���ʱ�򣬿��Ծ���Ӧ�������ú������ü�����</p>\n" +
                "<p>��/�����ÿ��Ժ�һ�����ö��У�ReferenceQueue������ʹ�ã���������������õĶ����������������գ�Java������ͻ����������ü��뵽��֮���������ö����С�����������п��Ե�֪�����յ���/�����õĶ����б��Ӷ�Ϊ�����������ʧЧ����/�����á�</p>\n" +
                "<p>�������ǵ�Ӧ�û��õ�������Ĭ��ͼƬ������Ӧ������Ĭ�ϵ�ͷ��Ĭ����Ϸͼ��ȵȣ���ЩͼƬ�ܶ�ط����õ������ÿ�ζ�ȥ��ȡͼƬ�����ڶ�ȡ�ļ���ҪӲ���������ٶȽ������ᵼ�����ܽϵ͡��������ǿ��ǽ�ͼƬ������������Ҫ��ʱ��ֱ�Ӵ��ڴ��ж�ȡ�����ǣ�����ͼƬռ���ڴ�ռ�Ƚϴ󣬻���ܶ�ͼƬ��Ҫ�ܶ���ڴ棬�Ϳ��ܱȽ����׷���OutOfMemory�쳣����ʱ�����ǿ��Կ���ʹ����/�����ü���������������ⷢ�������¾��Ǹ��ٻ������ĳ��Σ�</p>\n" +
                "<p>���ȶ���һ��HashMap�����������ö���</p>\n" +
                "<pre><code>private Map &lt;String, SoftReference&lt;Bitmap&gt;&gt; imageCache = new HashMap &lt;String, SoftReference&lt;Bitmap&gt;&gt; ();\n" +
                "</code></pre>\n" +
                "<p>��������һ������������Bitmap�������õ�HashMap��</p>\n" +
                "<p><a href=\"https://camo.githubusercontent.com/35124988ec96dde280418c48dea872829b96fca8/68747470733a2f2f67772e616c6963646e2e636f6d2f7470732f5442316f575f464c565858585858756158585858585858585858582d3637392d3731372e6a7067\" target=\"_blank\"><img src=\"https://camo.githubusercontent.com/35124988ec96dde280418c48dea872829b96fca8/68747470733a2f2f67772e616c6963646e2e636f6d2f7470732f5442316f575f464c565858585858756158585858585858585858582d3637392d3731372e6a7067\" alt=\"\" data-canonical-src=\"https://gw.alicdn.com/tps/TB1oW_FLVXXXXXuaXXXXXXXXXXX-679-717.jpg\" style=\"max-width:100%;\"></a></p>\n" +
                "<p>ʹ���������Ժ���OutOfMemory�쳣����֮ǰ����Щ�����ͼƬ��Դ���ڴ�ռ���Ա��ͷŵ��ģ��Ӷ������ڴ�ﵽ���ޣ�����Crash������</p>\n" +
                "<p>���ֻ�������OutOfMemory�쳣�ķ����������ʹ�������á��������Ӧ�õ����ܸ����⣬�뾡�����һЩռ���ڴ�Ƚϴ�Ķ��������ʹ�������á�</p>\n" +
                "<p>������Ը��ݶ����Ƿ񾭳�ʹ�����ж�ѡ�������û��������á�����ö�����ܻᾭ��ʹ�õģ��;����������á�����ö��󲻱�ʹ�õĿ����Ը���Щ���Ϳ����������á�</p>\n" +
                "<p>ok�������ص����⡣ǰ����˵�ģ�����һ����̬Handler�ڲ��࣬Ȼ��� Handler ���еĶ���ʹ�������ã������ڻ���ʱҲ���Ի��� Handler ���еĶ��󣬵�����������Ȼ������ Activity й©������ Looper �̵߳���Ϣ�����л��ǿ��ܻ��д��������Ϣ������������ Activity �� Destroy ʱ���� Stop ʱӦ���Ƴ���Ϣ���� MessageQueue �е���Ϣ��</p>\n" +
                "<p>���漸�������������Ƴ� Message��</p>\n" +
                "<pre><code>public final void removeCallbacks(Runnable r);\n" +
                "\n" +
                "public final void removeCallbacks(Runnable r, Object token);\n" +
                "\n" +
                "public final void removeCallbacksAndMessages(Object token);\n" +
                "\n" +
                "public final void removeMessages(int what);\n" +
                "\n" +
                "public final void removeMessages(int what, Object object);\n" +
                "</code></pre>\n" +
                "<p>###��������ʹ�� static ��Ա����</p>\n" +
                "<p>�����Ա����������Ϊ static�������Ƕ�֪�����������ڽ�������app������������һ����</p>\n" +
                "<p>��ᵼ��һϵ�����⣬������app����������ǳ�פ�ڴ�ģ��Ǽ�ʹapp�е���̨���ⲿ���ڴ�Ҳ���ᱻ�ͷš����������ֻ�app�ڴ������ƣ�ռ�ڴ�ϴ�ĺ�̨���̽����Ȼ��գ�yi'wei�����app�������̻�������ǻ����app�ں�̨Ƶ�����������ֻ���װ������뿪����app�Ժ�һҹʱ���ֻ������Ŀ��˵��������������app���ò����û�ж�ػ��߾�Ĭ��</p>\n" +
                "<p>�����޸��ķ����ǣ�</p>\n" +
                "<p>��Ҫ�����ʼʱ��ʼ����̬��Ա�����Կ���lazy��ʼ����\n" +
                "�ܹ������Ҫ˼���Ƿ�����б�Ҫ���������������⡣����ܹ���Ҫ��ô��ƣ���ô�˶�������������������ι���������</p>\n" +
                "<p>###���� override finalize()</p>\n" +
                "<p>1��finalize ������ִ�е�ʱ�䲻ȷ�������������������ͷŽ�ȱ����Դ��ʱ�䲻ȷ����ԭ���ǣ�\n" +
                "���������GC��ʱ�䲻ȷ��\n" +
                "Finalize daemon�̱߳����ȵ���ʱ�䲻ȷ��</p>\n" +
                "<p>2��finalize ����ֻ�ᱻִ��һ�Σ���ʹ���󱻸������Ѿ�ִ�й��� finalize �������ٴα� GC ʱҲ������ִ���ˣ�ԭ���ǣ�</p>\n" +
                "<p>���� finalize ������ object ���� new ��ʱ���������������һ�� finalize reference �������õ���Object�ģ����� finalize ����ִ�е�ʱ�򣬸� object ����Ӧ�� finalize Reference �ᱻ�ͷŵ�����ʹ�����ʱ��Ѹ� object ����(����ǿ��������ס�� object )���ٵڶ��α� GC ��ʱ������û���� finalize reference ��֮��Ӧ������ finalize ����������ִ�С�</p>\n" +
                "<p>3������Finalize������object��Ҫ���پ�������GC���п��ܱ��ͷš�</p>\n" +
                "<p>###��Դδ�ر���ɵ��ڴ�й©</p>\n" +
                "<p>����ʹ����BraodcastReceiver��ContentObserver��File���α� Cursor��Stream��Bitmap����Դ��ʹ�ã�Ӧ����Activity����ʱ��ʱ�رջ���ע����������Щ��Դ�����ᱻ���գ�����ڴ�й©��</p>\n" +
                "<p>###һЩ����������ɵ��ڴ�ѹ��</p>\n" +
                "<p>��Щ���벢������ڴ�й¶���������ǣ����Ƕ�ûʹ�õ��ڴ�û������Ч��ʱ���ͷţ�����û����Ч���������еĶ������Ƶ�����������ڴ档</p>\n" +
                "<p>���磺\n" +
                "Bitmap û���� recycle()���������� Bitmap �����ڲ�ʹ��ʱ,����Ӧ���ȵ��� recycle() �ͷ��ڴ棬Ȼ���������Ϊ null. ��Ϊ���� Bitmap ������ڴ�ռ䣬һ������ java �ģ�һ���� C �ģ���Ϊ Bitmap ����ĵײ���ͨ�� JNI ���õ� )�� ����� recyle() ������� C ���ֵ��ڴ��ͷš�\n" +
                "���� Adapter ʱ��û��ʹ�û���� convertView ,ÿ�ζ��ڴ����µ� converView�������Ƽ�ʹ�� ViewHolder��</p>\n" +
                "<p>##�ܽ�</p>\n" +
                "<p>�� Activity �����������Ӧ�ÿ����� Activity ����������֮�ڣ� ������ܾͿ���ʹ�� getApplicationContext ���� getApplication���Ա��� Activity ���ⲿ���������ڵĶ������ö�й¶��</p>\n" +
                "<p>������Ҫ�ھ�̬�������߾�̬�ڲ�����ʹ�÷Ǿ�̬�ⲿ��Ա����������context )����ʹҪʹ�ã�ҲҪ������ʱ���ⲿ��Ա�����ÿգ�Ҳ�������ڲ�����ʹ���������������ⲿ��ı�����</p>\n" +
                "<p>�����������ڱ�Activity�����ڲ�����󣬲����ڲ�����ʹ�����ⲿ��ĳ�Ա���������������������ڴ�й©��</p>\n" +
                "<pre><code>    ���ڲ����Ϊ��̬�ڲ���\n" +
                "    ��̬�ڲ�����ʹ���������������ⲿ��ĳ�Ա����\n" +
                "</code></pre>\n" +
                "<p>Handler �ĳ��е����ö������ʹ�������ã���Դ�ͷ�ʱҲ������� Handler �������Ϣ�������� Activity onStop ���� onDestroy ��ʱ��ȡ������ Handler ����� Message�� Runnable.</p>\n" +
                "<p>�� Java ��ʵ�ֹ����У�ҲҪ����������ͷţ���õķ������ڲ�ʹ��ĳ����ʱ����ʽ�ؽ��˶���ֵΪ null������ʹ����Bitmap ���ȵ��� recycle()���ٸ�Ϊnull,��ն�ͼƬ����Դ��ֱ�����û��߼�����õ����飨ʹ�� array.clear() ; array = null���ȣ������ѭ˭����˭�ͷŵ�ԭ��</p>\n" +
                "<p>��ȷ�ر���Դ������ʹ����BraodcastReceiver��ContentObserver��File���α� Cursor��Stream��Bitmap����Դ��ʹ�ã�Ӧ����Activity����ʱ��ʱ�رջ���ע����</p>\n" +
                "<p>���ֶԶ����������ڵ����У��ر�ע�ⵥ������̬����ȫ���Լ��ϵȵ��������ڡ�</p>\n" +
                "</article>\n" +
                "  </div>";
        String html2 = "html5556 2262    ";
        html = html.trim();
        html2 = html2.trim();

        Document doc = Jsoup.parse(html);
        String text = doc.getElementsByTag("article").text();

        System.out.println(text);
//        System.out.println(html2);
    }

}
