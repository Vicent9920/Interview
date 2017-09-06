# JAVA设计模式初探之装饰者模式
###### （选自炸斯特——http://blog.csdn.net/jason0539/article/details/22713711）
这个模式花费了挺长时间，开始有点难理解，其实就是
定义：动态给一个对象添加一些额外的职责,就象在墙上刷油漆.使用Decorator模式相比用生成子类方式达到功能的扩充显得更为灵活。
设计初衷:通常可以使用继承来实现功能的拓展,如果这些需要拓展的功能的种类很繁多,那么势必生成很多子类,增加系统的复杂性,同时,使用继承实现功能拓展,我们必须可预见这些拓展功能,这些功能是编译时就确定了,是静态的。

### 要点：
装饰者与被装饰者拥有共同的超类，继承的目的是继承类型，而不是行为

   实际上Java 的I/O API就是使用Decorator实现的。
   
   ```
//定义被装饰者  
public interface Human {  
    public void wearClothes();  
  
    public void walkToWhere();  
}  
  
//定义装饰者  
public abstract class Decorator implements Human {  
    private Human human;  
  
    public Decorator(Human human) {  
        this.human = human;  
    }  
  
    public void wearClothes() {  
        human.wearClothes();  
    }  
  
    public void walkToWhere() {  
        human.walkToWhere();  
    }  
}  
  
//下面定义三种装饰，这是第一个，第二个第三个功能依次细化，即装饰者的功能越来越多  
public class Decorator_zero extends Decorator {  
  
    public Decorator_zero(Human human) {  
        super(human);  
    }  
  
    public void goHome() {  
        System.out.println("进房子。。");  
    }  
  
    public void findMap() {  
        System.out.println("书房找找Map。。");  
    }  
  
    @Override  
    public void wearClothes() {  
        // TODO Auto-generated method stub  
        super.wearClothes();  
        goHome();  
    }  
  
    @Override  
    public void walkToWhere() {  
        // TODO Auto-generated method stub  
        super.walkToWhere();  
        findMap();  
    }  
}  
  
public class Decorator_first extends Decorator {  
  
    public Decorator_first(Human human) {  
        super(human);  
    }  
  
    public void goClothespress() {  
        System.out.println("去衣柜找找看。。");  
    }  
  
    public void findPlaceOnMap() {  
        System.out.println("在Map上找找。。");  
    }  
  
    @Override  
    public void wearClothes() {  
        // TODO Auto-generated method stub  
        super.wearClothes();  
        goClothespress();  
    }  
  
    @Override  
    public void walkToWhere() {  
        // TODO Auto-generated method stub  
        super.walkToWhere();  
        findPlaceOnMap();  
    }  
}  
  
public class Decorator_two extends Decorator {  
  
    public Decorator_two(Human human) {  
        super(human);  
    }  
  
    public void findClothes() {  
        System.out.println("找到一件D&G。。");  
    }  
  
    public void findTheTarget() {  
        System.out.println("在Map上找到神秘花园和城堡。。");  
    }  
  
    @Override  
    public void wearClothes() {  
        // TODO Auto-generated method stub  
        super.wearClothes();  
        findClothes();  
    }  
  
    @Override  
    public void walkToWhere() {  
        // TODO Auto-generated method stub  
        super.walkToWhere();  
        findTheTarget();  
    }  
}  
  
//定义被装饰者，被装饰者初始状态有些自己的装饰  
public class Person implements Human {  
  
    @Override  
    public void wearClothes() {  
        // TODO Auto-generated method stub  
        System.out.println("穿什么呢。。");  
    }  
  
    @Override  
    public void walkToWhere() {  
        // TODO Auto-generated method stub  
        System.out.println("去哪里呢。。");  
    }  
}  
//测试类，看一下你就会发现，跟java的I/O操作有多么相似  
public class Test {  
    public static void main(String[] args) {  
        Human person = new Person();  
        Decorator decorator = new Decorator_two(new Decorator_first(  
                new Decorator_zero(person)));  
        decorator.wearClothes();  
        decorator.walkToWhere();  
    }  
}  
```
**运行结果：**


![日志](http://img.blog.csdn.net/20140401085445906?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvamFzb24wNTM5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

其实就是进房子找衣服，然后找地图这样一个过程，通过装饰者的三层装饰，把细节变得丰富。
### 关键点：
1、Decorator抽象类中，持有Human接口，方法全部委托给该接口调用，目的是交给该接口的实现类即子类进行调用。
2、Decorator抽象类的子类（具体装饰者），里面都有一个构造方法调用super(human),这一句就体现了抽象类依赖于子类实现即抽象依赖于实现的原则。因为构造里面参数都是Human接口，只要是该Human的实现类都可以传递进去，即表现出Decorator dt = new Decorator_second(new Decorator_first(new Decorator_zero(human)));这种结构的样子。所以当调用dt.wearClothes();dt.walkToWhere()的时候，又因为每个具体装饰者类中，都先调用super.wearClothes和super.walkToWhere()方法，而该super已经由构造传递并指向了具体的某一个装饰者类（这个可以根据需要调换顺序），那么调用的即为装饰类的方法，然后才调用自身的装饰方法，即表现出一种装饰、链式的类似于过滤的行为。
3、具体被装饰者类，可以定义初始的状态或者初始的自己的装饰，后面的装饰行为都在此基础上一步一步进行点缀、装饰。
4、装饰者模式的设计原则为：对扩展开放、对修改关闭，这句话体现在我如果想扩展被装饰者类的行为，无须修改装饰者抽象类，只需继承装饰者抽象类，实现额外的一些装饰或者叫行为即可对被装饰者进行包装。所以：扩展体现在继承、修改体现在子类中，而不是具体的抽象类，这充分体现了依赖倒置原则，这是自己理解的装饰者模式。

说的不清楚，有些只可意会不可言传的感觉，多看几遍代码，然后自己敲出来运行一下，基本上就领悟了。

下面这个例子也有助于理解 装饰的流程和作用
现在需要一个汉堡，主体是鸡腿堡，可以选择添加生菜、酱、辣椒等等许多其他的配料，这种情况下就可以使用装饰者模式。

 1. Decorator抽象类中，持有Human接口，方法全部委托给该接口调用，目的是交给该接口的实现类即子类进行调用。
 2. Decorator抽象类的子类（具体装饰者），里面都有一个构造方法调用super(human),这一句就体现了抽象类依赖于子类实现即抽象依赖于实现的原则。因为构造里面参数都是Human接口，只要是该Human的实现类都可以传递进去，即表现出Decorator dt = new Decorator_second(new Decorator_first(new Decorator_zero(human)));这种结构的样子。所以当调用dt.wearClothes();dt.walkToWhere()的时候，又因为每个具体装饰者类中，都先调用super.wearClothes和super.walkToWhere()方法，而该super已经由构造传递并指向了具体的某一个装饰者类（这个可以根据需要调换顺序），那么调用的即为装饰类的方法，然后才调用自身的装饰方法，即表现出一种装饰、链式的类似于过滤的行为。
 3. 具体被装饰者类，可以定义初始的状态或者初始的自己的装饰，后面的装饰行为都在此基础上一步一步进行点缀、装饰。
 4. 装饰者模式的设计原则为：对扩展开放、对修改关闭，这句话体现在我如果想扩展被装饰者类的行为，无须修改装饰者抽象类，只需继承装饰者抽象类，实现额外的一些装饰或者叫行为即可对被装饰者进行包装。所以：扩展体现在继承、修改体现在子类中，而不是具体的抽象类，这充分体现了依赖倒置原则，这是自己理解的装饰者模式。
 
 说的不清楚，有些只可意会不可言传的感觉，多看几遍代码，然后自己敲出来运行一下，基本上就领悟了。

下面这个例子也有助于理解 装饰的流程和作用
现在需要一个汉堡，主体是鸡腿堡，可以选择添加生菜、酱、辣椒等等许多其他的配料，这种情况下就可以使用装饰者模式。

汉堡基类（被装饰者，相当于上面的Human）
```
package decorator;    
    
public abstract class Humburger {    
        
    protected  String name ;    
        
    public String getName(){    
        return name;    
    }    
        
    public abstract double getPrice();    
    
}    
```

鸡腿堡类（被装饰者的初始状态，有些自己的简单装饰，相当于上面的Person）

```
package decorator;    
    
public class ChickenBurger extends Humburger {    
        
    public ChickenBurger(){    
        name = "鸡腿堡";    
    }    
    
    @Override    
    public double getPrice() {    
        return 10;    
    }    
    
}    
```

配料的基类（装饰者，用来对汉堡进行多层装饰，每层装饰增加一些配料，相当于上面Decorator）

```
package decorator;    
    
public abstract class Condiment extends Humburger {    
        
    public abstract String getName();    
    
}    
```

生菜（装饰者的第一层，相当于上面的decorator_zero）

```
package decorator;    
    
public class Lettuce extends Condiment {    
        
    Humburger humburger;    
        
    public Lettuce(Humburger humburger){    
        this.humburger = humburger;    
    }    
    
    @Override    
    public String getName() {    
        return humburger.getName()+" 加生菜";    
    }    
    
    @Override    
    public double getPrice() {    
        return humburger.getPrice()+1.5;    
    }    
    
}    

```

辣椒（装饰者的第二层，相当于上面的decorator_first）

```
package decorator;    
    
public class Chilli extends Condiment {    
        
    Humburger humburger;    
        
    public Chilli(Humburger humburger){    
        this.humburger = humburger;    
            
    }    
    
    @Override    
    public String getName() {    
        return humburger.getName()+" 加辣椒";    
    }    
    
    @Override    
    public double getPrice() {    
        return humburger.getPrice();  //辣椒是免费的哦    
    }    
    
}    
```

测试类

```
package decorator;    
    
public class Test {    
    
    /**  
     * @param args  
     */    
    public static void main(String[] args) {    
        Humburger humburger = new ChickenBurger();    
        System.out.println(humburger.getName()+"  价钱："+humburger.getPrice());    
        Lettuce lettuce = new Lettuce(humburger);    
        System.out.println(lettuce.getName()+"  价钱："+lettuce.getPrice());    
        Chilli chilli = new Chilli(humburger);    
        System.out.println(chilli.getName()+"  价钱："+chilli.getPrice());    
        Chilli chilli2 = new Chilli(lettuce);    
        System.out.println(chilli2.getName()+"  价钱："+chilli2.getPrice());    
    }    
    
}    
```

输出

```
鸡腿堡  价钱：10.0    
鸡腿堡 加生菜  价钱：11.5    
鸡腿堡 加辣椒  价钱：10.0    
鸡腿堡 加生菜 加辣椒  价钱：11.5   
```
