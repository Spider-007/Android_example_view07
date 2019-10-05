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