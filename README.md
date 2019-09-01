SpringSecurity 学习项目

By：imooc

基于原始教程进行了一定修改，例如全套尝试使用 SB2.x 来构建

## 模块描述
- security：主模块
- security-core：核心业务逻辑
- security-browser：浏览器安全特定代码
- security-app:app相关特定代码
- security-demo：样例程序

## Web模块
使用 SpringMVC 开发 RESTful API 服务/接口

常用的最简单最基础的一些应用。

- 使用 MockMvc 进行接口的测试。
- 使用 Swagger 进行文档的生成。
- 使用 @jsonView 多视图方式来选择性的返回实体中的属性。
- ~~时间的转换~~（时间戳默认可以被识别和转换）。
- 请求参数的校验（@Valid），自定义校验器。
- 统一异常处理。
- 拦截器与过滤器。
- 使用 WireMock 伪造 Restful 数据。

> 关于日期格式的定制，如果使用配置文件方式的话，不能存在带 @EnableWebMvc 注解的配置类，否则其作用会被屏蔽。
> 
> 复杂情况下，就使用 HttpMessageConverter 进行自定义吧
>
> see: https://www.jianshu.com/p/f7e36b0cae41

## SpringSecurity相关
使用到的功能：

- 自定义登陆方式、页面、记住我等必要的配置
- 自定义密码处理
- 自定义认证成功、失败后的逻辑
- 集成图形验证码和短信验证码

默认的退出地址为：`/logout`

## 流程梳理
Browser 项目为例，其中 BrowserSecurityController 负责处理未认证情况下请求如何流转，这里就判断客户端类型，如果是浏览器就跳到登陆页面，如果是其他客户端，直接返回 401；

主要配置文件为 BrowserSecurityConfig，涉及核心中的两个主要配置类 ValidateCodeSecurityConfig 和 SmsCodeAuthenticationSecurityConfig；

通过这些配置文件，会设置基本的配置，例如登陆方式为表单，失败、成功的自定义处理，校验认证的自定义处理；

ValidateCodeSecurityConfig 和 SmsCodeAuthenticationSecurityConfig 配置类最重要的是加入了两个过滤器 ValidateCodeFilter 和 SmsCodeAuthenticationFilter，分别位于前端，处理普通的图形校验码和短信校验码；

接下来分析 ValidateCodeFilter 到逻辑，它会判断请求属于那一类到校验（配置文件中有配置），如果是需要进行图形验证码校验的请求，那么就拦截开始处理；处理流程是通过 validateCodeProcessorHolder 寻找到对应到处理器，
然后进行校验提交的验证码是否正确。

那么接下来的 SmsCodeAuthenticationFilter 也是类似，不过不同于 ValidateCodeFilter，验证码的验证并不是来登陆的，只是一个校验，默认图形验证码走的还是用户名密码的登陆逻辑；
传统的表单登陆 + 图形验证码我们直接使用的系统自带的一套（UsernamePasswordAuthenticationFilter），在 SmsCodeAuthenticationFilter 中我们需要自定义一套，当然是参考的 UsernamePasswordAuthenticationFilter 实现；

最后，ValidateCodeController 中负责处理验证码的生成请求，当然也是通过 validateCodeProcessorHolder 来寻找对应的验证码类型，然后调用生成方法即可，生成方法在白名单中，拦截器会自动放行。

---
UsernamePasswordAuthenticationFilter 流程简单说明：

当认证请求进入这个过滤器后，它会从请求取出用户名和密码信息，封装到一个 UsernamePasswordAuthenticationToken（未认证状态） 中，接下来就到了 AuthenticationManager，它会从一堆 AuthenticationProvider 中选出一个；
依据就是这些 AuthenticationProvider 中，有一个 supports 方法，它会验证是否支持当前的 AuthenticationToken，如果支持，就进行认证了；

认证过程会通过 UserDetailsService 来获取用户信息（UserDetails），然后比较能通过认证，如果顺利，就会把 UsernamePasswordAuthenticationToken 做一个"已认证"的标记，保存到 session。

PS：不管是图形验证码还是短信验证码，他们到验证肯定是要放到凭证认证之前的，也就是他们的过滤器要在 UsernamePasswordAuthenticationFilter 和 SmsCodeAuthenticationFilter 之前，只不过短信验证码比较特殊，
一般我们认为你通过了就算是登陆了。

## OAuth协议
协议主要包含角色：

- 服务提供商（Provider）
    - 认证服务器（Authorization Server）
    - 资源服务器（Resource Server）
- 资源所有者（Resource Owner）
- 客户端（Client）

基本流程：

资源所有者，也就是用户，去访问客户端的服务，然后客户端需要获取其他应用（服务提供商）中的用户数据，所以客户端会向用户请求授权，用户同意后，服务提供商会发放一个有效期的令牌给客户端（用户同意操作在服务提供商完成）；
之后客户端可以拿着这个令牌去资源服务器获取资源，资源服务器会验证这个令牌的合法性，通过即可返回需要的资源。

其中，最重要的就是用户同意这个步骤了，同意后如何获取令牌，一般我们采用的也就是授权码模式和密码模式，其中授权码模式最为广泛。
流程：

需要授权时，客户端会将用户导向服务提供商的认证服务器，用户需要在认证服务器上完成同意授权，然后认证服务器会返回一个授权码，一般是将这个授权码返回到客户端的后台，然后客户端的后台根据这个授权码再去认证服务器换取令牌；
这样令牌就拿到了，整个过程需要两步，认证服务器也能通过导向的连接携带的参数来确定是那个客户端需要授权；
这样不直接返回令牌而是授权码大大加强来应用间的安全性。

## 关于测试
在测试模块，使用到了 SpringMock 的相关支持，方便用来解析 RESTful 返回的 JSON 串；
其中用到了 JsonPath 的一些语法，感兴趣的可以去看一下。