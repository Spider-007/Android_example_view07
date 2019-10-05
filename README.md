# Android_example_view07
android的网络编程部分（HttpUrlConnection 以及 WebView加载网页 以及 Okhttp ）
## Android webView load Url
 <br/>.1.使用webView 控件加载布局；接着 webView 就像是一个解析器，把我们输入的网站自动进行 解析，并且 显示成一个网页
 <br/>.2.把webView 显示出来，设置 setJavaScriptEnabled() 启用加载器加载网页，接着设置 webClient（）为new WebViewClient() 这样的好处就是 加载的时候 把webView显示到 控件的webView上面
 <br/>.3.使用loadUrl 加载网站！
 
## Android send Request
  <br/>1.发送请求的时候，主要是 对httpUrlConnection 的使用做一个简单的练习(因为在android6.0之后httpClient已经被费用，原因是Android团队不支持这种请求协议，因为内置大量的api)
  <br/>2.在子线程加载 https://www.baidu.com 并且和他握手，使用 openConnection 获取实例
  <br/>3.接着使用 inputStream 读取加载的网页的流
  <br/>4.接着对获取到的流，在主线程更新 ui！
  <br/>5.关流，关闭 connection 实例
  
<br/> Q：post 和 Get 最本质的区别在上什么地方呢？
<br/> A:post 是给网络 发起请求 ，传递数据!接着等待服务器返回！ Get是服务器返回好的，你直接拿来读！解析等等操作！
<br/> Q:如何设置post 和 get 模式呢？
<br/> A:使用 httpUrlConnection setRequestMethod("") 设置指定模式 
<br/> Q:post如何使用
<br/> A:首先设置请求为post；其次在获取流之前，先把提交的数据使用outPutStream写入进去->把提交的数据使用键值对并且使用 & 分开；

## 使用 Okhttp 请求网络，okHttp 是一套开发人员首选的httpUrlConnection的网络框架，可以大大提高开发效率！基本使用步骤：
<br/> 1. 添加 本地依赖库，到官方文档上 找到依赖库 地址 implementation("com.squareup.okhttp3:okhttp:4.2.1") 
<br/> 2. 拿到依赖库首先获取 Okhttp 的 实例 ---> 添加Request->请求，给请求参数的url 赋值！接着build()代表构建完毕！
<br/> 3. 接着注意使用Get 请求和使用post请求在用法上 有明显的差别；
<br/> 4. post 是把数据发送给服务器，接着返回响应数据，也就是说我们需要通过 RequestBody()放置提交的参数！
<br/> 5. 接着调用 OkhttpClient的newCall() 方法创建一个Call对象，并且调用它的execute()方法发送请求并且获取 服务器返回的数据即可！
<br/> Q:使用哪个接口呢？
<br/> A:先使用 http://www.baidu.com 进行测试练习，现在任何 get post类型的接口我们都可以应付了！
<br/> Q:在开发的时候发现自己想不起来步骤了！
<br/> A:还是没有理解！刚刚开发完，补充一下笔记！->补充完毕 changed 了第 二 步骤 和第三步骤
<br/> Q：开发的过程中脑袋里的步骤一定要明确，知道自己下一步该干什么？偶尔卡壳了，停下，再去想一想 这样对么?
<br/> A:这样可以呀！能成长起来 为什么不呢？

## 数据解析部分
<br/> 1.到Apache  http://httpd.apache.org/download.cgi 上下载或者搜索 Apache服务器下载  -> [https://blog.csdn.net/qq_41914317/article/details/102180526](https://blog.csdn.net/qq_41914317/article/details/102180526)
<br/> 2.配置好apache服务器之后需要 添加 xml 格式数据 和 json格式数据，我们在 apache\htdocs 目录下，新建 get_data.xml 以及 get_data.json 文件，并且在 自己搭建的 webView 中去验证
<br/> 3.我们写一个 专门解析的工具类，提供 pull 解析 和 sax 解析两种方式-->接着传递data数据
<br/> 4.pull 解析 的步骤大概是 ：<br/> 首先获取到 Pull 解析器的实例<br/>->接着设置输入流,传递的参数就是需要解析的数据;<br/>->接着使用 getEventType()流并且获取到事件类型
<br/>->根据Document的 节点判断是否结束;<br/>->再去判断开始标签，和结束标签，开始的时候赋值，结束的时候打印；<br/>最后 next 循环遍历该节点直到等于 END_DOCUMENT 则代表结束
<br/> 5:SAX 解析 相比更加常用，在语义方面有点优势！ </br>step:<br/>->1.首先创建一个自定义Handler继承DefaultHandler 重写4个方法；<br/>2.接着这四个方法 分别是 startDocument() ，endDocument() , startElement() , endElement() , characters() 
<br/>->即startDocument()代表会在开始解析xml 的时候使用<br/>endDocument()代表完成整个Xml解析的时候使用<br/>startElement()会在开始解析某个节点的时候调用！<br/>endElement()会在完成解析某个节点的时候使用!<br/>characters()会在 获取节点中内容的时候调用,可以被调用多次，一些换行符也有可能被当作内容显示，针对这个在代码中做好控制！
<br/>具体的步骤也就是：<br/>1.获取到SAX的实例；<br/>2.创建sax解析对象，并且获取到XmlRead对象，接着把我们编写的Handle放到xmlRead里面，在使用xmlReader的parse来进行解析！
<br/> JSONArray 和 JSONObject 需要注意的是 {} [] 的区别,[] 是一个数组，遇见 {} 就是，数组需要使用JSONArray来获取对象;<br/>->遍历获取 JSONObject对象，获取对应的string;
<br/> GSON 解析 也是 google 推出的，具体步骤如下：<br/>1.添加 本地依赖库的闭包;<br/>2.如果是对象的话，创建实体类，接着去放到gson的fromJson里面，把json转化为实体对象存储起来!
<br/> 如果遇见数组类型的话，使用 new TypeToken().getType() 将期望解析的数据放到fromJson中
