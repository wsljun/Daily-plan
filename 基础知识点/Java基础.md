1.1 Java基础

    1. 对抽象、继承、多态的理解
	
	  - 封装：是面向对象方法的重要原则，就是把对象的属性和行为（数据）结合为一个独立的整体，并尽可能隐藏对象的内部实现细节，就是把不想告诉或者不该告诉别人的东西隐藏起来，把可以告诉别人的公开，别人只能用我提供的功能实现需求，而不知道是如何实现的。增加安全性

      - 继承：是面向对象最显著的一个特性，继承是从已有的类中派生出新的类称为子类，子类继承父类的数据属性和行为，并能根据自己的需求扩展出新的行为，提高了代码的复用性。

　　  - 多态：指允许不同的对象对同一消息做出相应。即同一消息可以根据发送对象的不同而采用多种不同的行为方式（发送消息就是函数调用）。封装和继承几乎都是为多态而准备的，在执行期间判断引用对象的实际类型，根据其实际的类型调用其相应的方法。
 
      - 抽象：  抽象：表示对问题领域进行分析、设计中得出的抽象的概念，是对一系列看上去不同，但是本质上相同的具体概念的抽象，在java中抽象用 abstract 关键字来修饰，用 abstract 修饰类时，此类就不能被实例化，
	    http://www.cnblogs.com/fuzhentao/p/5804476.html
		
	
	2. 泛型的作用及使用场景
	 
	   - 泛型的本质是参数化泛型...
	   - BaseAdapter 封装，
	   - http://www.cnblogs.com/xunzhi/p/5683709.html
	   

	3. 枚举的特点及使用场景
       
	   - enum 关键字，同class,interface 同一级别 ，可以把enum看成一个抽象类；
	   - 枚举的定义：
	   1. 枚举常量没有任何修饰符
	   2. 枚举常量没有任何修饰符
	   3. 枚举常量必须定义在所有方法或者构造器之前。
       4. 使用场景 http://blog.csdn.net/yehui928186846/article/details/51426415		 


	4. 线程sleep和wait的区别
	
	   - 区别
	   1. 这两个方法来自不同的类分别是Thread和Object
       2. 最主要是sleep方法没有释放锁，而wait方法释放了锁，使得其他线程可以使用同步控制块或者方法。
       3. wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以在任何地方使用（使用范围）
       4.  参考：// http://blog.csdn.net/nicklsq/article/details/7360845 
            //	http://blog.csdn.net/liuzhenwen/article/details/4202967

	5. JAVA反射机制

	6. weak/soft/strong引用的区别

	7. Object的hashCode()与equals()的区别和作用



1.2 集合类   

	1. JAVA常用集合类功能、区别和性能  
	
	   1.1 Collection 是最基本的集合接口；除map外 list,set,Vector(同步)... 均继承自它。
	   - List 接口 是有序的collection,能够精准的控制每个元素的插入位置。使用索引位置来访问list元素。允许有相同的元素存在，
	   1. LinkedList类 实现List接口，允许 null 元素，允许在尾部和首部插入元素。没有同步方法，需自己实现。
	   2. ArrayList类 它允许所有元素，ArryList没有同步方法;大量数据插入时使用ensureCapacity()方法增加ArrayList容量来提高插入效率。
          //http://blog.csdn.net/zhj870975587/article/details/50996811
		  
	2. 并发相关的集合类  
	
	   - 并发List ：Vector和CopyOnWriteArrayList是两个线程安全的List，Vector读写操作都用了同步，相对来说更适用于写多读少的场合，CopyOnWriteArrayList在写的时候会复制一个副本，对副本写，写完用副本替换原值，读的时候不需要同步，适用于写少读多的场合。
       - 并发Set  : CopyOnWriteArraySet基于CopyOnWriteArrayList来实现的，只是在不允许存在重复的对象这个特性上遍历处理了一下。
	   - 并发Map  : ConcurrentHashMap是专用于高并发的Map实现，内部实现进行了锁分离，get操作是无锁的。
	   - 并发的Queue : // http://www.cnblogs.com/zengxianxi/p/3607953.html 
	   - 参考 ： // http://blog.csdn.net/caihaijiang/article/details/7437275
	   
	    
	3. 部分常用集合类的内部实现方式
	
	   - 集合各实现类的底层实现原理  http://blog.csdn.net/qq_25868207/article/details/55259978
	   - Java常用集合的特性以及内部实现。 http://chinesethink.iteye.com/blog/1565780




1.3 多线程相关


	1. Thread、Runnable、Callable、Futrue类关系与区别

	2. JDK中默认提供了哪些线程池，有何区别

	3. 线程同步有几种方式，分别阐述在项目中的用法

	4. 在理解默认线程池的前提下，自己实现线程池

	5. 多线程断点续传



1.4 字符


	1. String的不可变性
	
	   1.4.1 : 一旦一个String对象在内存中创建，它将是不可改变的，所有的String类中方法并不是改变String对象自己，而是重新创建一个新的String对象。

	2. StringBuilder和StringBuffer的区别
	
     - String : 字符串常量
	 - StringBuilder , StringBuffer 都是字符创变量
	 - 三者在执行速度上：StringBuilder > StringBuffer > String
	 - StringBuilder与 StringBuffer对比
	 
　   1. StringBuilder：线程非安全的
     2. StringBuffer：线程安全的
     3. 当我们在字符串缓冲去被多个线程使用是，JVM不能保证StringBuilder的操作是安全的，虽然他的速度最快，但是可以保证StringBuffer是可以正确操作的。当然大多数情况下就是我们是在单线程下进行的操作，所以大多数情况下是建议用StringBuilder而不用StringBuffer的，就是速度的原因。

     - 对于三者使用的总结： 
	 
	 1. 如果要操作少量的数据用 = String
     2. 单线程操作字符串缓冲区 下操作大量数据 = StringBuilder
     3. 多线程操作字符串缓冲区 下操作大量数据 = StringBuffer
	 

	3. 字符集的理解：Unicode、UTF-8、GB2312等 

	4. 正则表达式相关问题




1.5 注解


	1. 注解的使用 

	2. 注解的级别及意义  

	3. [如何自定义注解] (http://www.importnew.com/14479.html)


