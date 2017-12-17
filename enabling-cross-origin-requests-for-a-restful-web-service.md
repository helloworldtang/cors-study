【helloworldtang翻译中】

# Enabling Cross Origin Requests for a RESTful Web Service

> 原文：[Enabling Cross Origin Requests for a RESTful Web Service](https://spring.io/guides/gs/rest-service-cors/)
>
> 译者：[helloworldtang](https://github.com/helloworldtang)
>
> 校对：


This guide walks you through the process of creating a "hello world" [RESTful web service](https://spring.io/understanding/REST) with Spring that includes headers for [Cross-Origin Resource Sharing (CORS)](https://spring.io/understanding/CORS) in the response. You will find more information about Spring CORS support in this [blog post](https://spring.io/blog/2015/06/08/cors-support-in-spring-framework).
本入门教程将向您介绍使用Spring创建一个“hello world”的[RESTful风格的web 服务](https://spring.io/understanding/REST) ，这个服务的响应头信息包含了 [Cross-Origin Resource Sharing (CORS)](https://spring.io/understanding/CORS)。您还可以在博客[blog post](https://spring.io/blog/2015/06/08/cors-support-in-spring-framework)中找到更多关于Spring CORS的信息。
## What you’ll build
##你将创建一个什么样的服务

You’ll build a service that will accept HTTP GET requests at:
您将构建一个接受HTTP GET请求的服务:
```
http://localhost:8080/greeting
```

and respond with a [JSON](https://spring.io/understanding/JSON) representation of a greeting:
然后这个GET请求的返回值是一个表示问候的 [JSON](https://spring.io/understanding/JSON):
```json
{"id":1,"content":"Hello, World!"}
```

You can customize the greeting with an optional `name` parameter in the query string:
您可以在查询字符串中使用一个可选参数`name`来自定义问候信息
```
http://localhost:8080/greeting?name=User
```

The `name` parameter value overrides the default value of "World" and is reflected in the response:
这个`name` 参数对应的值会覆盖"World"的默认值，并且这个GET请求的返回值也会随之发生变化：
```json
{"id":1,"content":"Hello, User!"}
```

This service differs slightly from the one described in [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/) in that it will use Spring Framework CORS support to add the relevant CORS response headers.
该服务与[构建一个RESTful风格的Web服务 ](https://spring.io/guides/gs/rest-service/) 中所描述的略有不同，因为它将使用Spring Framework CORS的相关特性来添加需要的CORS响应头。
## What you’ll need
## 你将需要什么

- About 15 minutes
- 大约需要15分钟
- A favorite text editor or IDE 
- 最喜欢的文本编辑器或IDE
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 或者更新的版本
- [Gradle 2.3+](http://www.gradle.org/downloads) or [Maven 3.0+](https://maven.apache.org/download.cgi)
- You can also import the code straight into your IDE:
- 您也可以直接将代码导入IDE:
  - [Spring Tool Suite (STS)](https://spring.io/guides/gs/sts)
  - [IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea/)

## How to complete this guide
## 如何完成这个入门教程

Like most Spring [Getting Started guides](https://spring.io/guides), you can start from scratch and complete each step, or you can bypass basic setup steps that are already familiar to you. Either way, you end up with working code.
就像大多数[Spring入门教程](https://spring.io/guides)一样，您可以从头开始，完成每一步，或者您可以绕过您已经熟悉的基本设置步骤。无论采用哪种方式，最终都将得到是可以是可以正常运行的代码。
To **start from scratch**, move on to .
**从头开始**，继续[使用Gradle构建](https://spring.io/guides/gs/rest-service-cors/#scratch)
To **skip the basics**, do the following:
**跳过基本内容**，请执行以下操作
- [Download](https://github.com/spring-guides/gs-rest-service-cors/archive/master.zip) and unzip the source repository for this guide, or clone it using [Git](https://spring.io/understanding/Git): `git clone https://github.com/spring-guides/gs-rest-service-cors.git` 
- [下载](https://github.com/spring-guides/gs-rest-service-cors/archive/master.zip) 并解压缩该入门教程的源代码库，或者使用 [Git](https://spring.io/understanding/Git)克隆: `git clone https://github.com/spring-guides/gs-rest-service-cors.git` 
- cd into `gs-rest-service-cors/initial`
- 使用cd命令进入目录`gs-rest-service-cors/initial`
- Jump ahead to [Create a resource representation class](https://spring.io/guides/gs/rest-service-cors/#initial).
- 向前跳转[创建一个资源表示类](https://spring.io/guides/gs/rest-service-cors/#initial)
  **When you’re finished**, you can check your results against the code in `gs-rest-service-cors/complete`.
  **当您完成后**，您可以在`gs-rest-service-cors/complete`目录中检查您的结果。
## Build with Gradle
## 使用Gradle构建

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Gradle](http://gradle.org/) and [Maven](https://maven.apache.org/) is included here. If you’re not familiar with either, refer to [Building Java Projects with Gradle](https://spring.io/guides/gs/gradle) or [Building Java Projects with Maven](https://spring.io/guides/gs/maven).
首先，您需要创建一个构建脚本。您可以使用任何你擅长的构建工具来编译Spring应用程序，但是您使用 [Gradle](http://gradle.org/) and [Maven](https://maven.apache.org/) 构建的代码在这里都是一样的。如果您不熟悉这两个构建工具，请参考[使用Gradle构建Java项目](https://spring.io/guides/gs/gradle) ，或者 [使用Maven构建 Java项目](https://spring.io/guides/gs/maven).。
### Create the directory structure
### 创建目录结构
In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:
在您选择的项目目录中，创建以下子目录结构;例如，在*nix系统使用可以使用命令 `mkdir -p src/main/java/hello`:
```
└── src
    └── main
        └── java
            └── hello
```

### Create a Gradle build file 
### 创建一个Gradle构建文件

Below is the [initial Gradle build file](https://github.com/spring-guides/gs-rest-service-cors/blob/master/initial/build.gradle).
下面是 [初始 Gradle 构建文件](https://github.com/spring-guides/gs-rest-service-cors/blob/master/initial/build.gradle)
`build.gradle`

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.9.RELEASE")
    }
}

apply plugin: 'java'
apply plugin: 'org.springframework.boot'

jar {
    baseName = 'gs-rest-service-cors'
    version =  '0.1.0'
}

repositories {
    mavenCentral()
}

sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
}
```

The [Spring Boot gradle plugin](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-tools/spring-boot-gradle-plugin) provides many convenient features:
[Spring Boot gradle plugin](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-tools/spring-boot-gradle-plugin) 插件提供了许多方便的特性:
- It collects all the jars on the classpath and builds a single, runnable "über-jar", which makes it more convenient to execute and transport your service. 
- 收集类路径上的所有jar，并构建一个单一的、可运行的"über-jar"，这使得执行和传输您的服务更加方便。
- It searches for the `public static void main()` method to flag as a runnable class.
  -它查找 `public static void main()`方法并标记为一个可运行的类。
- It provides a built-in dependency resolver that sets the version number to match [Spring Boot dependencies](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-dependencies/pom.xml). You can override any version you wish, but it will default to Boot’s chosen set of versions.
- 它提供了一个内置的依赖解析器，它可以设置版本号来匹配 [Spring Boot依赖](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-dependencies/pom.xml)。您可以覆盖任何您希望的版本，但是它将默认引导所选择的版本集。
## Build with Maven
## 使用Maven构建

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Maven](https://maven.apache.org/) is included here. If you’re not familiar with Maven, refer to [Building Java Projects with Maven](https://spring.io/guides/gs/maven).
首先，您需要创建一个构建脚本。您可以使用任何你擅长的构建工具来编译Spring应用程序，但是您使用 [Maven](https://maven.apache.org/) 构建的代码在这里都是一样的。如果您不熟悉Maven，请参阅 [Building Java Projects with Maven](https://spring.io/guides/gs/maven)。
###  Create the directory structure
### 创建目录结构

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:
在您选择的项目目录中，创建以下子目录结构;例如，在*nix系统使用可以使用命令`mkdir -p src/main/java/hello`：

```
└── src
    └── main
        └── java
            └── hello
```

`pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.springframework</groupId>
    <artifactId>gs-rest-service-cors</artifactId>
    <version>0.1.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <properties>
        <java.version>1.8</java.version>
    </properties>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

The [Spring Boot Maven plugin](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-tools/spring-boot-maven-plugin) provides many convenient features:
[Spring Boot Maven plugin]提供许多方便的特性：
- It collects all the jars on the classpath and builds a single, runnable "über-jar", which makes it more convenient to execute and transport your service.
- 收集类路径上的所有jar，并构建一个单一的、可运行的"über-jar"，这使得执行和传输您的服务更加方便。
- It searches for the `public static void main()` method to flag as a runnable class.
  -它查找 `public static void main()`方法并标记为一个可运行的类。
- It provides a built-in dependency resolver that sets the version number to match [Spring Boot dependencies](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-dependencies/pom.xml). You can override any version you wish, but it will default to Boot’s chosen set of versions.
- 它提供了一个内置的依赖解析器，它可以设置版本号来匹配[Spring Boot 依赖](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-dependencies/pom.xml)。您可以覆盖任何您希望的版本，但是它将默认引导所选择的版本集。
## Build with your IDE
## 使用IDE构建

- Read how to import this guide straight into [Spring Tool Suite](https://spring.io/guides/gs/sts/).
- 阅读如何将该入门教程直接导入[Spring Tool Suite](https://spring.io/guides/gs/sts/)。
- Read how to work with this guide in [IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea).
- 阅读如何与这个入门教程一起使用 [IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea)。
## Create a resource representation class 
## 创建一个资源表示类

Now that you’ve set up the project and build system, you can create your web service.
现在您已经建立了项目和构建系统，您可以创建您的web服务。
Begin the process by thinking about service interactions.
通过考虑服务交互来开始这个过程。
The service will handle `GET` requests for `/greeting`, optionally with a `name` parameter in the query string. The `GET` request should return a `200 OK` response with JSON in the body that represents a greeting. It should look something like this:
该服务将处理 `GET`请求`/greeting`，并可选地使用查询字符串中的 `name`参数。 `GET`请求应该返回一个`200 OK`响应，该响应是表示问候的正文中的JSON。应该是这样的:
```json
{
    "id": 1,
    "content": "Hello, World!"
}
```

The `id` field is a unique identifier for the greeting, and `content` is the textual representation of the greeting.
`id`字段是问候的唯一标识符，`content`是问候的文本表示。
To model the greeting representation, you create a resource representation class. Provide a plain old java object with fields, constructors, and accessors for the `id` and `content` data:
要创建greeting模型，您需要创建一个资源表示类。为`id`和`content`数据提供一个普通的java POJO对象，其中包含字段、构造函数和访问器
`src/main/java/hello/Greeting.java`

```java
package hello;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting() {
        this.id = -1;
        this.content = "";
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
```


> As you see in steps below, Spring uses the Jackson JSON library to automatically marshal instances of type Greeting into JSON.
> 正如您在下面的步骤中看到的，Spring使用[Jackson JSON](http://wiki.fasterxml.com/JacksonHome)库将类型`Greeting`的实例自动编组为JSON。

Next you create the resource controller that will serve these greetings.
接下来，您将创建资源控制器来提供这些greetings
## Create a resource controller  
## 创建一个资源控制器

In Spring’s approach to building RESTful web services, HTTP requests are handled by a controller. These components are easily identified by the [`@Controller`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/stereotype/Controller.html) annotation, and the `GreetingController` below handles `GET` requests for `/greeting` by returning a new instance of the `Greeting` class:
在使用Spring构建RESTful风格 web服务的方法中，HTTP请求由控制器处理。这些组件很容易被 [`@Controller`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/stereotype/Controller.html)注解标识，下面的`GreetingController`通过处理`/greeting`接口的 `GET` 请求来返回一个新的`Greeting`类实例:
`src/main/java/hello/GreetingController.java`

```java
package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
```

This controller is concise and simple, but there’s plenty going on under the hood. Let’s break it down step by step.
这个控制器简洁、简单，但是在底层有很多东西。让我们一步一步地把它分解。
The `@RequestMapping` annotation ensures that HTTP requests to `/greeting` are mapped to the `greeting()` method.
`@RequestMapping`注解确保将到`/greeting`的HTTP请求映射到 `greeting()` 方法。
>The above example uses the @GetMapping annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).
>上面的例子使用了`@GetMapping`注解，它充当了`@RequestMapping(method = RequestMethod.GET)`的快捷方式。


`@RequestParam` binds the value of the query string parameter `name` into the `name` parameter of the `greeting()` method. This query string parameter is not `required`; if it is absent in the request, the `defaultValue` of "World" is used.
`@RequestParam` 将参数名为`name`的查询字符串绑定`greeting()`方法的 `name`参数。这个查询字符串参数不是 `required`;如果请求中没有，则使用默认值"World" 。
The implementation of the method body creates and returns a new `Greeting` object with `id`and `content` attributes based on the next value from the `counter`, and formats the given `name` by using the greeting `template`.
方法提供了创建并返回一个带有`id`和 `content`属性的新的 `Greeting`对象的功能，该对象基于`counter`的下一个值，并通过使用问候`template`来格式化给定的`name`属性 。
A key difference between a traditional MVC controller and the RESTful web service controller above is the way that the HTTP response body is created. Rather than relying on a [view technology](https://spring.io/understanding/view-templates) to perform server-side rendering of the greeting data to HTML, this RESTful web service controller simply populates and returns a `Greeting` object. The object data will be written directly to the HTTP response as JSON.
传统的MVC控制器和上面的RESTful风格的web服务控制器之间的一个关键区别是HTTP响应主体的创建方式。这个rest式的web服务控制器并不依赖于 [view technology](https://spring.io/understanding/view-templates)来执行向HTML的问候数据的服务器端呈现，而是简单地填充并返回一个Greeting对象。对象数据将以JSON形式直接写入HTTP响应。
To accomplish this, the [`@ResponseBody`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html) annotation on the `greeting()` method tells Spring MVC that it does not need to render the greeting object through a server-side view layer, but that instead the greeting object returned *is* the response body, and should be written out directly.
要做到这一点， [`@ResponseBody`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html) 注解在`greeting()`方法告诉Spring MVC，它不需要通过服务器端视图层来呈现问候对象，而是返回的Greeting对象*就是* 响应体，应该直接输出到客户端。
The `Greeting` object must be converted to JSON. Thanks to Spring’s HTTP message converter support, you don’t need to do this conversion manually. Because [Jackson](http://wiki.fasterxml.com/JacksonHome) is on the classpath, Spring’s [`MappingJackson2HttpMessageConverter`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/converter/json/MappingJackson2HttpMessageConverter.html) is automatically chosen to convert the `Greeting` instance to JSON.
`Greeting`对象必须转换为JSON。由于Spring提供了HTTP消息转换器，您不需要手工进行这种转换。因为 [Jackson](http://wiki.fasterxml.com/JacksonHome)在类路径中,Spring会自动使用[`MappingJackson2HttpMessageConverter`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/converter/json/MappingJackson2HttpMessageConverter.html)将`Greeting` 实例转换为JSON。
## Enabling CORS
## 启用CORS 

### Controller method CORS configuration  
### 在控制器方法上配置CORS 

So that the RESTful web service will include CORS access control headers in its response, you just have to add a `@CrossOrigin` annotation to the handler method:
因此，基于RESTful风格的web服务将在其响应中包含CORS的访问控制头信息，您只需在处理请求的方法上添加一个`@CrossOrigin`注解:
`src/main/java/hello/GreetingController.java`

```java
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
```

This `@CrossOrigin` annotation enables cross-origin requests only for this specific method. By default, its allows all origins, all headers, the HTTP methods specified in the `@RequestMapping`annotation and a maxAge of 30 minutes is used. You can customize this behavior by specifying the value of one of the annotation attributes: `origins`, `methods`, `allowedHeaders`, `exposedHeaders`, `allowCredentials` or `maxAge`. In this example, we only allow `http://localhost:9000` to send cross-origin requests.
这个`@CrossOrigin`注解只支持这个特定方法的跨域请求。默认情况下，它允许使用`@RequestMapping`注释中指定的所有的域，所有的头信息、HTTP方法以及30分钟的maxAge。您可以通过指定一个注释属性的值来定制这个行为:`origins`、`methods`、`allowedHeaders`、`exposedHeaders`、`allowCredentials`或 `maxAge`。在本例中，我们只允许 `http://localhost:9000`发送跨域请求。

>it is also possible to add this annotation at controller class level as well, in order to enable CORS on all handler methods of this class.
>还可以在控制器类级别上添加这个注释，以便在这个类的所有处理程序方法上启用CORS。

### Global CORS configuration  
### 全局CORS配置

As an alternative to fine-grained annotation-based configuration, you can also define some global CORS configuration as well. This is similar to using a `Filter` based solution, but can be declared within Spring MVC and combined with fine-grained `@CrossOrigin` configuration. By default all origins and `GET`, `HEAD` and `POST` methods are allowed.
作为细粒度的基于注释的配置的另一种选择，您还可以定义一些全局的CORS配置。这类似于使用基于`Filter`的解决方案，但可以在Spring MVC中声明，并结合细粒度的`@CrossOrigin`配置。默认情况下，所有域和`GET`, `HEAD`和 `POST`方法都是允许的。
`src/main/java/hello/GreetingController.java`

```java
    @GetMapping("/greeting-javaconfig")
    public Greeting greetingWithJavaconfig(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
```

`src/main/java/hello/Application.java`

```java
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
            }
        };
    }
```

You can easily change any properties (like the `allowedOrigins` one in the example), as well as only apply this CORS configuration to a specific path pattern. Global and controller level CORS configurations can also be combined.
您可以很容易地更改任何属性(例如示例中的`allowedOrigins`)，并且只将这个CORS配置应用于特定的路径模式。全局和控制器级别的CORS配置也可以组合在一起。
## Make the application executable
## 使应用程序可以执行

Although it is possible to package this service as a traditional [WAR](https://spring.io/understanding/WAR) file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring’s support for embedding the [Tomcat](https://spring.io/understanding/Tomcat) servlet container as the HTTP runtime, instead of deploying to an external instance.
尽管可以将此服务打包为用于部署到外部应用服务器的传统[WAR](https://spring.io/understanding/WAR) 包，但下面演示的更简单的方法创建了一个独立的应用程序。您可以将所有内容打包到一个单独的、可执行的JAR文件中，由一个好的旧Java `main()`驱动的方法。在此过程中，您使用Spring的支持来将 [Tomcat](https://spring.io/understanding/Tomcat)  servlet容器嵌入到HTTP运行时中，而不是将其部署到外部实例中。
`src/main/java/hello/Application.java`

```java
package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

`@SpringBootApplication` is a convenience annotation that adds all of the following:
`@SpringBootApplication`是一个方便的注解，它添加了以下所有内容:
- `@Configuration` tags the class as a source of bean definitions for the application context.
- `@Configuration`将类标记为应用程序上下文的bean定义的来源。
- `@EnableAutoConfiguration` tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
- `@EnableAutoConfiguration` 告诉Spring Boot 开始添加基于类路径设置、其他bean和各种属性设置的bean。
- Normally you would add `@EnableWebMvc` for a Spring MVC app, but Spring Boot adds it automatically when it sees **spring-webmvc** on the classpath. This flags the application as a web application and activates key behaviors such as setting up a `DispatcherServlet`.
- 通常情况下，你会为一个Spring MVC应用添加 `@EnableWebMvc`注解，但是当Spring Boot在类路径上看到 **spring-webmvc** 时，它会自动添加它。这将应用程序标记为web应用程序，并激活诸如设置 `DispatcherServlet`之类的关键行为。
- `@ComponentScan` tells Spring to look for other components, configurations, and services in the `hello` package, allowing it to find the controllers.
- `@ComponentScan`告诉Spring在`hello` 包中寻找其他组件、配置和服务，以便它能够找到所有的控制器。
  The `main()` method uses Spring Boot’s `SpringApplication.run()` method to launch an application. Did you notice that there wasn’t a single line of XML? No **web.xml** file either. This web application is 100% pure Java and you didn’t have to deal with configuring any plumbing or infrastructure.
  `main()`方法使用Spring Boot的 `SpringApplication.run()`来启动应用程序。您注意到没有一行XML吗?也没有**web.xml**文件。这个web应用程序是100%纯Java的，您不需要处理任何管道或基础设施的配置。
### Build an executable JAR  
### 构建一个可执行JAR

You can run the application from the command line with Gradle or Maven. Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources, and run that. This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.
您可以在命令行中使用Gradle或Maven来运行该应用程序。或者您可以构建一个单一的可执行JAR文件，该文件包含所有必需的依赖项、类和资源，并运行该文件。这使得在整个开发生命周期中，跨不同的环境，以及在不同的环境中，将服务作为一个应用程序进行部署、版本和部署是很容易的。
If you are using Gradle, you can run the application using `./gradlew bootRun`. Or you can build the JAR file using `./gradlew build`. Then you can run the JAR file:
如果您在使用Gradle，您可以使用这个应用程序`./gradlew bootRun`。或者您可以使用`./gradlew build`来构建JAR文件。然后运行这个JAR文件:
```bash
java -jar build/libs/gs-rest-service-cors-0.1.0.jar
```

If you are using Maven, you can run the application using `./mvnw spring-boot:run`. Or you can build the JAR file with `./mvnw clean package`. Then you can run the JAR file:
如果使用Maven，则可以使用`./mvnw spring-boot:run`运行这个应用程序。或者您可以用`./mvnw clean package`构建JAR文件 。然后您可以运行JAR文件:
```bash
java -jar target/gs-rest-service-cors-0.1.0.jar
```
>The procedure above will create a runnable JAR. You can also opt to build a classic WAR file instead.
>上面的过程将创建一个可运行的JAR。您也可以选择[构建一个典型的WAR包](https://spring.io/guides/gs/convert-jar-to-war/)。


Logging output is displayed. The service should be up and running within a few seconds.
显示日志输出。该服务应该在几秒钟内启动并运行。
## Test the service   
## 测试上面的service

Now that the service is up, visit http://localhost:8080/greeting, where you see:
现在服务已经启动，访问`http://localhost:8080/greeting`接口,返回值如下所示:
```json
{"id":1,"content":"Hello, World!"}
```

Provide a `name` query string parameter with <http://localhost:8080/greeting?name=User>. Notice how the value of the `content` attribute changes from "Hello, World!" to "Hello User!":
`http://localhost:8080/greeting?name=User`提供一个`name`查询字符串参数。注意， `content`属性的值从“Hello，World！”变为 "Hello User!":
```json
{"id":2,"content":"Hello, User!"}
```

This change demonstrates that the `@RequestParam` arrangement in `GreetingController` is working as expected. The `name` parameter has been given a default value of "World", but can always be explicitly overridden through the query string.
这一变化表明，`GreetingController`中的`@RequestParam` 安排是按预期工作的。`name`参数被赋予了默认值“World”，但是可以通过查询字符串显式地覆盖它。
Notice also how the `id` attribute has changed from `1` to `2`. This proves that you are working against the same `GreetingController` instance across multiple requests, and that its `counter` field is being incremented on each call as expected.
还要注意`id`属性是如何从`1`变为`2`的。这就证明了您在多个请求中对同一个`GreetingController` 实例进行了操作，并且它的`counter`字段在每次调用时都是按照预期的那样递增的。
Now to test that the CORS headers are in place and allowing a Javascript client from another origin to access the service, you’ll need to create a Javascript client to consume the service.
现在要测试CORS头文件的位置，并允许来自另一个域的Javascript客户端访问该服务，您需要创建一个Javascript客户端来使用该服务
First, create a simple Javascript file named `hello.js` with the following content:
首先，创建一个简单的Javascript文件 `hello.js` ，包含以下内容:
`public/hello.js`

```js
$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/greeting"
    }).then(function(data, status, jqxhr) {
       $('.greeting-id').append(data.id);
       $('.greeting-content').append(data.content);
       console.log(jqxhr);
    });
});
```

This script uses jQuery to consume the REST service at <http://localhost:8080/greeting>. It is loaded by `index.html` as shown here:
这个脚本使用jQuery使用REST服务`http://localhost:8080/greeting`。它是由包含以下内容的`index.html`文件 加载:
`public/index.html`

```html
<!DOCTYPE html>
<html>
    <head>
        <title>Hello CORS</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="hello.js"></script>
    </head>

    <body>
        <div>
            <p class="greeting-id">The ID is </p>
            <p class="greeting-content">The content is </p>
        </div>
    </body>
</html>
```
>This is essentially the REST client created in Consuming a RESTful Web Service with jQuery, modified slightly to consume the service running on localhost, port 8080. See that guide for more details on how this client was developed.
>这本质上是[使用jQuery调用RESTful风格的Web服务](https://spring.io/guides/gs/consuming-rest-jquery/)中创建的REST客户端，稍微修改一下，以便调用在本机8080端口上运行的服务。有关如何开发该客户端的详细信息，请参阅该入门教程。


Because the REST service is already running on localhost, port 8080, you’ll need to be sure to start the client from another server and/or port. This will not only avoid a collision between the two applications, but will also ensure that the client code is served from a different origin than the service. To start the client running on localhost, port 9000:
因为REST服务已经在本地主机8080端口上运行，所以您需要确保从另一个服务器和/或端口启动客户机。这不仅避免了这两个应用程序之间的冲突，而且还将确保客户端代码的服务与服务不同。在本地主机上启动使用9000端口的客户端:
```bash
mvn spring-boot:run -Dserver.port=9000
```

Once the client starts, open [http://localhost:9000](http://localhost:9000/) in your browser, where you should see:
客户机启动后，在浏览器中打开[http://localhost:9000](http://localhost:9000/)，您将看到:
![Model data retrieved from the REST service is rendered into the DOM if the proper CORS headers are in the response.](https://spring.io/guides/gs/rest-service-cors/images/hello.png)
如果正确的CORS头部在响应中，则从REST服务中检索到的模型数据被呈现到DOM中。
If the service response includes the CORS headers, then the ID and content will be rendered into the page. But if the CORS headers are missing (or insufficiently defined for the client), then the browser will fail the request and the values will not be rendered into the DOM:
如果服务响应包含了CORS头信息，那么ID和content将被呈现到页面中。但是如果缺少了CORS的头信息(或者对客户端不够定义)，那么浏览器将会导致请求失败，而这些值将不会被呈现到DOM中:
![如果响应中缺少CORS标头，则浏览器将会失败。没有数据将被呈现到DOM中。](https://spring.io/guides/gs/rest-service-cors/images/hello_fail.png)
。
## Summary
## 小结

Congratulations! You’ve just developed a RESTful web service including Cross-Origin Resource Sharing with Spring.
恭喜!您刚刚开发了一个基于Spring并且支持跨域资源共享的RESTful风格web服务。
## See Also
## 另请参阅

The following guides may also be helpful:
以下的入门教程可能也有帮助:
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [构建一个RESTful风格的Web服务](https://spring.io/guides/gs/rest-service/)
- [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)
- [构建一个超文本驱动的RESTful风格的Web服务](https://spring.io/guides/gs/rest-hateoas/)
- [Creating API Documentation with Restdocs](https://spring.io/guides/gs/testing-restdocs/)
 [使用Restdocs创建一个API文档](https://spring.io/guides/gs/testing-restdocs/)
- [Accessing GemFire Data with REST](https://spring.io/guides/gs/accessing-gemfire-data-rest/)
- [基于REST方式访问GemFire Data](https://spring.io/guides/gs/accessing-gemfire-data-rest/)
- [基于REST方式访问 MongoDB Data](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
- [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
- [基于MySQL的数据访问](https://spring.io/guides/gs/accessing-data-mysql/)
- [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
-  [基于REST方式的JPA 数据访问](https://spring.io/guides/gs/accessing-data-rest/)
- [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
- [基于REST方式的 Neo4j 数据访问](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
- [Consuming a RESTful Web Service](https://spring.io/guides/gs/consuming-rest/)
- [调用RESTful风格的Web服务](https://spring.io/guides/gs/consuming-rest/)
- [Consuming a RESTful Web Service with AngularJS](https://spring.io/guides/gs/consuming-rest-angularjs/)
- [基于AngularJS访问一个 RESTful风格Web服务](https://spring.io/guides/gs/consuming-rest-angularjs/)
- [Consuming a RESTful Web Service with jQuery](https://spring.io/guides/gs/consuming-rest-jquery/)
- [基于 jQuery调用一个RESTful风格的Web服务](https://spring.io/guides/gs/consuming-rest-jquery/)
- [Consuming a RESTful Web Service with rest.js](https://spring.io/guides/gs/consuming-rest-restjs/)
- [基于rest.js调用一个RESTful风格的Web服务](https://spring.io/guides/gs/consuming-rest-restjs/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [保护Web应用程序](https://spring.io/guides/gs/securing-web/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
- [使用Spring构建REST服务](https://spring.io/guides/tutorials/bookmarks/)
- [React.js and Spring Data REST](https://spring.io/guides/tutorials/react-and-spring-data-rest/)
- [React.js和Spring Data REST](https://spring.io/guides/tutorials/react-and-spring-data-rest/)
- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [使用Spring Boo构建应用程序](https://spring.io/guides/gs/spring-boot/)

Want to write a new guide or contribute to an existing one? Check out our [contribution guidelines](https://github.com/spring-guides/getting-started-guides/wiki).
想要写一本新的入门教程或对现有的入门教程做出贡献?看看我们的 [共创入门教程](https://github.com/spring-guides/getting-started-guides/wiki)。
> 本文由spring4all.com翻译小分队创作，采用[知识共享-署名-非商业性使用-相同方式共享 4.0 国际 许可](http://creativecommons.org/licenses/by-nc-sa/4.0/) 协议进行许可。