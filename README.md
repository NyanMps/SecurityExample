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