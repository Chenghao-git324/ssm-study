# tools —— SpringMVC 跑起来的工具

IDEA Ultimate 订阅过期、学生授权又不好申请，所以**不用 IDEA 的 Tomcat 集成**，
改成「独立 Tomcat + 双击脚本」。代码照样在 IDEA 里写，只是启动服务走脚本。

## 怎么用

| 操作 | 做法 |
|---|---|
| 启动 | 双击 `run.bat` |
| 停止 | 关掉 `run.bat` 的黑窗口（或双击 `stop.bat`） |
| 访问 | http://localhost:8081/springmvc-demo/hello |

每天改代码的循环：

1. 在 IDEA 里改 `springmvc-demo` 的代码
2. 关掉上一次的黑窗口
3. 双击 `run.bat`（它会自动重新编译打包，再启动 Tomcat）
4. 刷新浏览器

## `run.bat` 做了两件事

```
[1/2] mvn clean package war:exploded
       → 生成 target\springmvc-demo-1.0-SNAPSHOT\（解压后的 war）
[2/2] catalina.bat run
       → 启动 Tomcat 9，端口 8081
```

## 为什么不用拷 war 包

Tomcat 的 `conf/Catalina/localhost/springmvc-demo.xml` 把访问路径
`/springmvc-demo` 直接指向了项目的 `target\springmvc-demo-1.0-SNAPSHOT` 目录，
所以重新打包后 Tomcat 读到的就是最新代码，不需要往 `webapps` 里拷东西。

那个 xml 里还写了 `reloadable="true"`：`WEB-INF/classes` 里的 class 变了，
Tomcat 会自己重新加载这个应用。

## 常见报错

| 现象 | 原因 |
|---|---|
| `Address already in use: bind` | 上一个黑窗口没关，端口 8081 还被占着 |
| 访问 `/springmvc-demo/` 返回 404 | 正常。`DispatcherServlet` 映射了 `/`，静态资源（index.html）还没配，后面关卡会讲 |
| 编译报错 | 先看 `run.bat` 窗口里 mvn 的报错，Tomcat 不会启动 |
