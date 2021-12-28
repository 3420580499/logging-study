###日志原理解析
1.初始化LogManager(日志管理器)
2.LogManager加载logging.properties配置（可以读取配置文件）
3.添加Logger（日志记录）到LogManager
4.从单例LogManager获取Logger
5.设置级别Level，并指定日志记录LogRecord 4. Filter提供了日志级别之外更细粒度的控制
6.Handler（日志处理器）是用来处理日志输出位置
7.Formatter是用来格式化LogRecord的

注意：日志处理器（handler）用来输出，它输出有普通的格式也有xml文件的格式

注意：Logger和handler都要设置级别（更改默认级别后才能起作用）

![image-20211227212727986](C:\Users\hu\AppData\Roaming\Typora\typora-user-images\image-20211227212727986.png)

![img](file:///C:\Users\hu\AppData\Local\Temp\ksohtml13304\wps1.jpg)
