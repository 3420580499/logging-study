package com.strive;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

/**
 * @author 小白
 * @create 2021/12/27
 */
public class JULTest {

    @Test
    public void test01(){
        /*String [] s = new String[]{"x"};*/
        //Logger对象的创建方式，不能直接new对象
        //取得对象的方法参数，需要引入当前类的全路径字符串
        Logger logger = Logger.getLogger("com.strive.JULTest");
        /*
        * 日志输出的两种方式
        * 第一种方式：直接调用日志级别相关的方法，方法中传递日志输出的信息，假设现在我们要输出info级别的日志信息
        * */
        logger.info("第一种方式");

        /*
        * 第二种方式：调用通用的log方法，然后在里面通过level类型来定义日志的级别参数，以及搭配日志输出信息的参数
        * */
        logger.log(Level.INFO,"第二种方式");
        String name = "小明";
        int age = 12;
        logger.log(Level.INFO,"姓名:{0},年龄:{1}",new Object[]{name,age});
    }

    @Test
    public void test02(){
        /*
            日志的级别（通过源码查看，非常简单）
              SEVERE : 错误 --- 最高级的日志级别
              WARNING : 警告
              INFO : （默认级别）消息
              CONFIG : 配置
              FINE : 详细信息（少）
              FINER : 详细信息（中）
              FINEST : 详细信息 （多） --- 最低级的日志级别
            两个特殊的级别
               OFF 可用来关闭日志记录
               ALL 启用所有消息的日志记录
            对于日志的级别，我们重点关注的是new对象的时候的第二个参数
            是一个数值
            OFF Integer.MAX_VALUE 整型最大值
            SEVERE 1000
            WARNING 900
            ...
            ...
            FINEST 300
            ALL Integer.MIN_VALUE 整型最小值
            这个数值的意义在于，如果我们设置的日志的级别是INFO -- 800
            那么最终展现的日志信息，必须是数值大于800的所有的日志信息
            最终展现的就是
            SEVERE
            WARNING
            INFO
         */
        Logger logger = Logger.getLogger("com.strive.JULTest");

        /*
            通过打印结果，我们看到了仅仅只是输出了info级别以及比info级别高的日志信息
            比info级别低的日志信息没有输出出来
            证明了info级别的日志信息，它是系统默认的日志级别
            在默认日志级别info的基础上，打印比它级别高的信息
         */
        /*
            如果仅仅只是通过以下形式来设置日志级别
            那么不能够起到效果
            将来需要搭配处理器handler共同设置才会生效
         */
        //下面这行代码没有用（设置级别得搭配handler处理器使用才会生效）
        logger.setLevel(Level.CONFIG);

        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");
    }


    @Test
    public void test03(){

        /*
            自定义日志的级别
         */
        //日志记录器
        Logger logger = Logger.getLogger("com.strive.JULTest");

        //将默认的日志打印方式关闭掉
        //参数设置为false，我们打印日志的方式就不会按照父logger默认的方式去进行操作
        logger.setUseParentHandlers(false);

        //处理器Handler
        //在此我们使用的是控制台日志处理器，取得处理器对象
        ConsoleHandler handler = new ConsoleHandler();
        //创建日志格式化组件对象
        SimpleFormatter formatter = new SimpleFormatter();

        //在处理器中设置输出格式
        handler.setFormatter(formatter);
        //在记录器中添加处理器
        logger.addHandler(handler);

        //设置日志的打印级别
        //此处必须将日志记录器和处理器的级别进行统一的设置，才会达到日志显示相应级别的效果
        //logger.setLevel(Level.CONFIG);
        //handler.setLevel(Level.CONFIG);

        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);

        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");
    }

    @Test
    public void test04() throws IOException {

        /*
            将日志输出到具体的磁盘文件中
            这样做相当于是做了日志的持久化操作
         */
        Logger logger = Logger.getLogger("com.strive.JULTest");
        logger.setUseParentHandlers(false);

        //文件日志处理器
        FileHandler handler = new FileHandler("D:\\日志学习\\01-JUL的使用\\myLogTest.log");
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        logger.addHandler(handler);

        //也可以同时在控制台和文件中进行打印
        ConsoleHandler handler2 = new ConsoleHandler();
        handler2.setFormatter(formatter);
        logger.addHandler(handler2); //可以在记录器中同时添加多个处理器

        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
        handler2.setLevel(Level.CONFIG);

        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");

        /*
            总结：
                用户使用Logger来进行日志的记录，Logger可以持有多个处理器Handler
                （日志的记录使用的是Logger，日志的输出使用的是Handler）
                添加了哪些handler对象，就相当于需要根据所添加的handler
               将日志输出到指定的位置上，例如控制台、文件..
         */
    }


    @Test
    public void test05(){
        /*
            Logger之间的父子关系
                JUL中Logger之间是存在"父子"关系的
                值得注意的是，这种父子关系不是我们普遍认为的类之间的继承关系
                关系是通过树状结构存储的

                JUL在初始化时会创建一个顶层RootLogger作为所有Logger的父Logger
                查看源码：
                    owner.rootLogger = owner.new RootLogger();
                    RootLogger是LogManager的内部类
                    java.util.logging.LogManager$RootLogger
                    默认的名称为 空串

                以上的RootLogger对象作为树状结构的根节点存在的
                将来自定义的父子关系通过路径来进行关联
                父子关系，同时也是节点之间的挂载关系
                owner.addLogger(owner.rootLogger);
                LoggerContext cx = getUserContext(); //LoggerContext一种用来保存节点的Map关系

                private LogNode               node; //节点
         */
        /*
            从下面创建的两个logger对象看来
            我们可以认为logger1是logger2的父亲
         */
        //父亲是RootLogger，名称默认是一个空的字符串
        //RootLogger可以被称之为所有logger对象的顶层logger
        Logger logger1 = Logger.getLogger("com.strive");

        Logger logger2 = Logger.getLogger("com.strive.JULTest");

        //System.out.println(logger2.getParent()==logger1); //true

        System.out.println("logger1的父Logger引用为:"
                +logger1.getParent()+"; 名称为"+logger1.getName()+"; 父亲的名称为"+logger1.getParent().getName());


        System.out.println("logger2的父Logger引用为:"
                +logger2.getParent()+"; 名称为"+logger2.getName()+"; 父亲的名称为"+logger2.getParent().getName());


        /*
            父亲所做的设置，也能够同时作用于儿子
            对logger1做日志打印相关的设置，然后我们使用logger2进行日志的打印
         */
        //父亲做设置
        logger1.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        logger1.addHandler(handler);
        handler.setLevel(Level.ALL);
        logger1.setLevel(Level.ALL);

        //儿子做打印
        logger2.severe("severe信息");
        logger2.warning("warning信息");
        logger2.info("info信息");
        logger2.config("config信息");
        logger2.fine("fine信息");
        logger2.finer("finer信息");
        logger2.finest("finest信息");
    }


    @Test
    public void test06() throws Exception {
        /*
            以上所有的配置相关的操作，都是以java硬编码的形式进行的
            我们可以使用更加清晰，更加专业的一种做法，就是使用配置文件
            如果我们没有自己添加配置文件，则会使用系统默认的配置文件
            这个配置文件：
                owner.readPrimordialConfiguration();
                readConfiguration();
                java.home --> 找到jre文件夹 --> lib --> logging.properties

            做文件日志打印，新日志会覆盖掉原来的日志
            但是我们现在的需求不是覆盖，而是追加
         */

        /*读取我们配置好的配置文件*/
        InputStream input = new FileInputStream("D:\\日志学习\\01-JUL的使用\\logging.properties");

        //取得日志管理器对象
        LogManager logManager = LogManager.getLogManager();

        //读取自定义的配置文件
        logManager.readConfiguration(input);

        Logger logger = Logger.getLogger("com.strive.JULTest");

        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");
        /*
            JUL日志框架使用方式总结（原理解析）
                1.初始化LogManager
                    LogManager加载logging.properties配置文件
                    添加Logger到LogManager
                2.从单例的LogManager获取Logger
                3.设置日志级别Level，在打印的过程中使用到了日志记录的LogRecord类
                4.Filter作为过滤器提供了日志级别之外更细粒度的控制
                5.Handler日志处理器，决定日志的输出位置，例如控制台、文件...
                6.Formatter是用来格式化输出的
         */
    }

}
