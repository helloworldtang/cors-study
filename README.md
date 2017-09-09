CORS（跨域资源共享，Cross-Origin Resource Sharing）

## CORS问题小结 ##
CORS问题的起源是源于对用户的保护措施，为了防止某域名下的接口被其他域名下的网页非法调用，
即浏览器不允许一个域名下ajax请求另一个源的rest请求


基于安全的原因,浏览器是存在同源策略机制的,同源策略阻止从一个源加载的文档或脚本获取或设置另一个源加载额文档的属性。

有点绕,说的简单点就是浏览器限制脚本只能和同协议、同域名、同端口的脚本进行交互。


解决跨域问题常用的解决方案有两个：    
JSONP：利用script标签可跨域的特点，在跨域脚本中可以直接回调当前脚本的函数。    
CORS：服务器设置HTTP响应头中Access-Control-Allow-Origin值，解除跨域限制。    
但是这两个跨域方案都存在一个致命的缺陷，严重依赖后端的协助。   

JSONP（Javascript Object Notation With Padding）即JSON with Padding的缩写,是一个非官方的协议。        
技术细节：JSONP是一种script tag的注入,将server返回的response添加到页面，通过JavaScript callback的形式来实现站点访问。       
具体来说，就是动态添加一个<script>标签，**而script标签的src属性是没有跨域的限制的**。        
这样说来，这种跨域方式其实**与ajax XmlHttpRequest协议无关了**,而缺点也很明显,它只支持GET请求而不支持POST等其它类型的HTTP请求；        
它只支持跨域HTTP请求这种情况，不能解决不同域的两个页面之间如何进行JavaScript调用的问题         



**注意：仅仅是浏览器的限制**

### 什么是CORS ###
一个URI:Scheme://host.domain:port/
只要满足下面任何一个条件，都是不同源：

1 Scheme不同，譬如http,https

2 host不同，譬如mail.163.com和www.163.com

3 domain不同

4 port不同，譬如tmall.com和tmall.com:8080


### 解决CORS问题的方案： ###
1 纯客户端，使用iframe来请求rest接口

2 使用jsonp。不建议使用。因为需要server端配合，并且只支持get方法。CORS Spring4.0开始已经提供了很好的解决方案
见Module:server-jsonp-support

3 纯服务器端。使用@CrossOrigin注解或在WebMvcConfigurerAdapter配置或在配置文件中配置
见Module:server-cors-registry

4 后台代理
Module client-third、server-jsonp-springv3_0-support、server-jsonp-support就是通过这种方式来访问server-cors-registry上的服务
后台代理也可以使用Nginx转发的方式，js请求的是同源的域名，**但js请求的服务器并没有相关的处理相关的业务逻辑**，而是按照相关约定去请求另外一台服务器上的接口,因为后台服务器请求时，没有浏览器的相关限制，任何可用的url都可访问

就是“明修栈道，暗渡陈仓”的套路了

5 CORS         
CORS是支持所有类型的HTTP请求,并且也只是服务端进行设置即可,但是缺点就是老的浏览器不支持CORS(如:IE7,7,8,等)

思路：     
CORS的响应头      

Access-Control-Allow-Origin : 必须的,允许的域名,如果设置*,则表示接受任何域名      
Access-Control-Allow-Credentials : 非必须的,表示是否允许发送Cookie,注意,当设置为true的时候,客户端的ajax请求,也需要将withCredentials属性设置为true       
Access-Control-Expose-Headers : 非必须的,表示客户端能拿到的header,默认情况下XMLHttpRequest的getResponseHeader方法只能拿到几个基本的header,如果有自定义的header要获取的话,则需要设置此值      
Access-Control-Request-Method : 必须的,表示CORS上会使用到那些HTTP方法       
Access-Control-Request-Headers : 必须的,表示CORS上会有那些额外的的有信息         

CORS将请求分为两种类型      

两种类型分别为简单请求和非简单请求,同时满足以下两大条件的请求被定义为是简单请求:                     
请求方法是以下三种之一:       
1、HEAD       
2、GET         
3、POST     

HTTP头信息不超出以下几种字段:      
Accept     
Accept-Language       
Content-Language      
Last-Event-ID       
Content-Type：只限于三个值application/x-www-form-urlencoded、multipart/form-data、text/plain      

对于非简单请求,浏览器会自动发一个预检请求,这个请求是OPTIONS方法的,主要是询问服务器当前请求是否在允许范围内     


**Tips:**
最关键的就是server端返回的header需要设置 Access-Control-Allow-Origin：*

```php
header("Access-Control-Allow-Origin: *"); # 跨域处理
```

如果需要指定某域名才允许跨域访问，只需把Access-Control-Allow-Origin:*改为Access-Control-Allow-Origin:允许的域名


### Model简介： ###
server-cors-registry：最新的解决方案。在WebMvcConfigurerAdapter中配置。需要先启动，其它Module会依赖这个

server-jsonp-support：@CrossOrigin和JSONP来解决跨域问题

server-jsonp-springv3_0-support：Spring3.0中CORS的解决方案：AbstractJsonpResponseBodyAdvice

client-third：这个模块的会访问上面这个Module的服务


上面四个服务启动后，在浏览器中打开http://localhost 就可以看到测试上面所叙场景的Button了







