
## CORS问题小结 ##
CORS问题的起源是源于对用户的保护措施，即浏览器不允许一个域名下ajax请求另一个源的rest请求


基于安全的原因,浏览器是存在同源策略机制的,同源策略阻止从一个源加载的文档或脚本获取或设置另一个源加载额文档的属性。

有点绕,说的简单点就是浏览器限制脚本只能和同协议、同域名、同端口的脚本进行交互。

JSONP就是为了解决这一问题的,JSONP是英文JSON with Padding的缩写,是一个非官方的协议。

他允许服务端生成script tags返回值客户端,通过JavaScript callback的形式来实现站点访问。

JSONP是一种script tag的注入,将server返回的response添加到页面是实现特定功能。

简而言之,JSONP本身不是复杂的东西,就是通过scirpt标签对javascript文档的动态解析绕过了浏览器的同源策略。


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

2 使用jsonp。不建议使用。因为需要server端配合。CORS Spring4.0开始已经提供了很好的解决方案
见Module:server-jsonp-support

3 纯服务器端。使用@CrossOrigin注解或在WebMvcConfigurerAdapter配置或在配置文件中配置
见Module:server-cors-registry

4 后台代理。
Module client-third、server-jsonp-springv3_0-support、server-jsonp-support就是通过这种方式来访问server-cors-registry上的服务
后台代理也可以使用Nginx转发的方式，相关域名信息不变，但将请求该域名的请求转发到另一个源的Rest接口服务器

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







