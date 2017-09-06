一、引子
在大学的数据结构这门课上，树是最重要的章节之一。还记得树是怎么定义的吗？树(Tree)是n(n≥0)个结点的有限集T，T为空时称为空树，否则它满足如下两个条件：
(1)    有且仅有一个特定的称为根(Root)的结点；
(2)   其余的结点可分为m(m≥0)个互不相交的子集Tl，T2，…，Tm，其中每个子集本身又是一棵树，并称其为根的子树(SubTree)。
上面给出的递归定义刻画了树的固有特性：一棵非空树是由若干棵子树构成的，而子树又可由若干棵更小的子树构成。而这里的子树可以是叶子也可以是分支。
今天要学习的组合模式就是和树型结构以及递归有关系。
 
二、定义与结构
组合(Composite)模式的其它翻译名称也很多，比如合成模式、树模式等等。在《设计模式》一书中给出的定义是：将对象以树形结构组织起来，以达成“部分－整体”的层次结构，使得客户端对单个对象和组合对象的使用具有一致性。
从定义中可以得到使用组合模式的环境为：在设计中想表示对象的“部分－整体”层次结构；希望用户忽略组合对象与单个对象的不同，统一地使用组合结构中的所有对象。
看下组合模式的组成。
1)         抽象构件角色Component：它为组合中的对象声明接口，也可以为共有接口实现缺省行为。
2)       树叶构件角色Leaf：在组合中表示叶节点对象——没有子节点，实现抽象构件角色声明的接口。
3)       树枝构件角色Composite：在组合中表示分支节点对象——有子节点，实现抽象构件角色声明的接口；存储子部件。
下图为组合模式的类图表示。

 
如图所示：一个Composite实例可以像一个简单的Leaf实例一样，可以把它传递给任何使用Component的方法或者对象，并且它表现的就像是一个Leaf一样。
可以看出来，使用组合模式使得这个设计结构非常灵活，在下面的例子中会得到进一步的印证。
      
三、安全性与透明性
组合模式中必须提供对子对象的管理方法，不然无法完成对子对象的添加删除等等操作，也就失去了灵活性和扩展性。但是管理方法是在Component中就声明还是在Composite中声明呢？
一种方式是在Component里面声明所有的用来管理子类对象的方法，以达到Component接口的最大化（如下图所示）。目的就是为了使客户看来在接口层次上树叶和分支没有区别——透明性。但树叶是不存在子类的，因此Component声明的一些方法对于树叶来说是不适用的。这样也就带来了一些安全性问题。

 
另一种方式就是只在Composite里面声明所有的用来管理子类对象的方法（如下图所示）。这样就避免了上一种方式的安全性问题，但是由于叶子和分支有不同的接口，所以又失去了透明性。
    


> 《设计模式》一书认为：在这一模式中，相对于安全性，我们比较强调透明性。对于第一种方式中叶子节点内不需要的方法可以使用空处理或者异常报告的方式来解决。
 
四、举例
这里以JUnit中的组合模式的应用为例（JUnit入门）。
JUnit是一个单元测试框架，按照此框架下的规范来编写测试代码，就可以使单元测试自动化。为了达到“自动化”的目的，JUnit中定义了两个概念：TestCase和TestSuite。TestCase是对一个类或者jsp等等编写的测试类；而TestSuite是一个不同TestCase的集合，当然这个集合里面也可以包含TestSuite元素，这样运行一个TestSuite会将其包含的TestCase全部运行。
然而在真实运行测试程序的时候，是不需要关心这个类是TestCase还是TestSuite，我们只关心测试运行结果如何。这就是为什么JUnit使用组合模式的原因。
JUnit为了采用组合模式将TestCase和TestSuite统一起来，创建了一个Test接口来扮演抽象构件角色，这样原来的TestCase扮演组合模式中树叶构件角色，而TestSuite扮演组合模式中的树枝构件角色。下面将这三个类的有关代码分析如下：
 

```
//Test接口——抽象构件角色
public interface Test {
       /**
        * Counts the number of test cases that will be run by this test.
        */
       public abstract int countTestCases();
       /**
        * Runs a test and collects its result in a TestResult instance.
        */
       public abstract void run(TestResult result);
}
 
//TestSuite类的部分有关源码——Composite角色，它实现了接口Test
public class TestSuite implements Test {
//用了较老的Vector来保存添加的test
       private Vector fTests= new Vector(10);
       private String fName;
       …… 
/**
        * Adds a test to the suite.
        */
       public void addTest(Test test) {          
//注意这里的参数是Test类型的。这就意味着TestCase和TestSuite以及以后实现Test接口的任何类都可以被添加进来
              fTests.addElement(test);
       }
       ……
       /**
        * Counts the number of test cases that will be run by this test.
        */
       public int countTestCases() {
              int count= 0;
              for (Enumeration e= tests(); e.hasMoreElements(); ) {
                     Test test= (Test)e.nextElement();
                     count= count + test.countTestCases();
              }
              return count;
       }
       /**
        * Runs the tests and collects their result in a TestResult.
        */
       public void run(TestResult result) {
              for (Enumeration e= tests(); e.hasMoreElements(); ) {
                    if (result.shouldStop() )
                           break;
                     Test test= (Test)e.nextElement();
                           //关键在这个方法上面
                     runTest(test, result);
              }
       }
            //这个方法里面就是递归的调用了，至于你的Test到底是什么类型的只有在运行的时候得知
            public void runTest(Test test, TestResult result) {
                   test.run(result);
            }
……
}
 
//TestCase的部分有关源码——Leaf角色，你编写的测试类就是继承自它
public abstract class TestCase extends Assert implements Test {
       ……
       /**
        * Counts the number of test cases executed by run(TestResult result).
        */
       public int countTestCases() {
              return 1;
       }
/**
        * Runs the test case and collects the results in TestResult.
        */
       public void run(TestResult result) {
              result.run(this);
       }
……
}
```
       可以看出这是一个偏重安全性的组合模式。因此在使用TestCase和TestSuite时，不能使用Test来代替。
 
五、优缺点
从上面的举例中可以看到，组合模式有以下优点：
1)         使客户端调用简单，客户端可以一致的使用组合结构或其中单个对象，用户就不必关心自己处理的是单个对象还是整个组合结构，这就简化了客户端代码。
2)       更容易在组合体内加入对象部件. 客户端不必因为加入了新的对象部件而更改代码。这一点符合开闭原则的要求，对系统的二次开发和功能扩展很有利！
当然组合模式也少不了缺点：组合模式不容易限制组合中的构件。
 
六、总结
组合模式是一个应用非常广泛的设计模式，在前面已经介绍过的解释器模式、享元模式中都是用到了组合模式。它本身比较简单但是很有内涵，掌握了它对你的开发设计有很大的帮助。
这里写下了我学习组合模式的总结，希望能给你带来帮助，也希望您能给与指正。
