// 生成《SSM 学习 · 错题本》Word 文档
// 用法：改下面的 ENTRIES 数组，然后 node gen-ssm-cuotiben.js
const {
  Document, Packer, Paragraph, TextRun, HeadingLevel, Table, TableRow, TableCell,
  WidthType, AlignmentType, BorderStyle, ShadingType, Footer, Header, PageNumber, PageBreak,
} = require("docx");
const fs = require("fs");

const OUT = "C:/Users/ch2fx/Desktop/MyJava/ssm-错题本.docx";

const SONG = { ascii: "Times New Roman", eastAsia: "宋体", hAnsi: "Times New Roman" };
const YAHEI = { ascii: "Arial", eastAsia: "微软雅黑", hAnsi: "Arial" };
const MONO = { ascii: "Consolas", eastAsia: "宋体", hAnsi: "Consolas" };

// ============ 错题数据 ============
const CATS = ["Java 基础", "Spring 核心", "Maven 与工程", "SpringMVC", "MyBatis", "MyBatis-Plus", "其他"];

const ENTRIES = [
  {
    no: "001",
    date: "2026-11-11",
    cat: "Spring 核心",
    title: "beans.xml 到底做了什么：容器替你 new + 注入",
    kind: "答疑",
    blocks: [
      { t: "p", label: "问题", text: "beans.xml 里 <bean id=\"bookDao\" class=\"...\"/> 和 <bean id=\"bookService\">...<property name=\"bookDao\" ref=\"bookDao\"/></bean> 这两段到底做了什么？" },
      { t: "p", label: "是什么", text: "beans.xml 是 Spring 容器的「装配清单」。容器启动时照着这份清单造对象、连依赖。可以把它想成一个 Map：key 是 id，value 是对象。" },
      { t: "p", label: "逐行", text: "① <bean id=\"bookDao\" class=\"com.itheima.dao.impl.BookDaoImpl\"/>：容器调用 new BookDaoImpl()，把实例存进容器，key = bookDao。等价于 map.put(\"bookDao\", new BookDaoImpl())。" },
      { t: "p", label: "逐行", text: "② <bean id=\"bookService\" class=\"com.itheima.service.impl.BookServiceImpl\">：容器调用 new BookServiceImpl()，存成 bookService。注意：此时它的 bookDao 字段还是 null。" },
      { t: "p", label: "逐行", text: "③ <property name=\"bookDao\" ref=\"bookDao\"/>：容器调用 bookService.setBookDao(容器里 id 为 bookDao 的对象)。name 对应 setter 名（set 去掉、首字母小写），ref 表示「引用另一个 Bean 的 id」，不是字符串。" },
      { t: "p", label: "机制", text: "执行顺序：解析 XML → new BookDaoImpl（bean bookDao）→ new BookServiceImpl（bean bookService）→ 调 setBookDao(bookDao) → 容器就绪。所以 getBean(\"bookService\") 拿到的对象，内部 bookDao 已经有值了。" },
      { t: "p", label: "关键点", text: "你的 Java 代码里没有 new BookDaoImpl()，但 BookServiceImpl 能调到它——这就是 IoC（创建权交给容器）+ DI（依赖由容器注入）。" },
      { t: "p", label: "陷阱", text: "ref 和 value 别混：ref=\"bookDao\" 传对象；value=\"bookDao\" 传字符串。name 写错（比如和 setter 名对不上）会报「找不到 setter」。id 不能重复；class 必须写全限定类名。" },
      { t: "p", label: "一句话记", text: "bean 让容器造对象，property ref 让容器把另一个对象塞进来；你只管声明，容器负责 new 和接线。" },
    ],
  },
  {
    no: "002",
    date: "2026-11-11",
    cat: "Maven 与工程",
    title: "src/main/resources 是干什么的：放「运行时才读」的文件",
    kind: "答疑",
    blocks: [
      { t: "p", label: "问题", text: "resources 目录是干什么的？为什么 beans.xml 要放这里？" },
      { t: "p", label: "是什么", text: "Maven 的标准目录：src/main/java 放 .java 源码，src/main/resources 放「不是 Java 代码、但程序运行时需要读」的文件。" },
      { t: "p", label: "机制", text: "编译/打包时，Maven 把 resources 里的文件原样复制到 target/classes；.java 编译成 .class 也放到 target/classes。target/classes 就是 classpath 的根。所以 new ClassPathXmlApplicationContext(\"beans.xml\") 里的 \"beans.xml\" 是相对 classpath 根的名字，不带 src/main/resources 前缀。" },
      { t: "p", label: "对照", text: "java → 编译成 class → target/classes；resources → 原样复制 → target/classes。两边最后在同一个目录汇合，这就是为什么代码能按名字找到配置文件。" },
      { t: "p", label: "陷阱", text: "把 beans.xml 放到 src/main/java 下，Maven 默认不会复制（除非在 pom 里配 resources），运行时通常会报 class path resource [beans.xml] cannot be opened。IDEA 里 resources 应该显示为紫色文件夹（Resources Root），可以在 Project Structure → Modules → Sources 确认。" },
      { t: "p", label: "一句话记", text: "java 放源码，resources 放配置；它们最后都进 target/classes，而 target/classes 就是 classpath 根。" },
    ],
  },
  {
    no: "003",
    date: "2026-11-11",
    cat: "Spring 核心",
    title: "App 是启动类吗：它是「程序入口」，不是 Spring 专属",
    kind: "答疑",
    blocks: [
      { t: "p", label: "问题", text: "App 这个类是启动类吗？" },
      { t: "p", label: "是什么", text: "它是程序的入口类。判断标准只有一个：有没有 public static void main(String[] args)。JVM 从 main 开始执行，跟 Spring 没关系。" },
      { t: "p", label: "区别", text: "原生 Spring（本项目）：App 里手动 new ClassPathXmlApplicationContext(\"beans.xml\") 来启动容器。Spring Boot：启动类叫 XxxApplication，靠 @SpringBootApplication + SpringApplication.run(...) 自动创建容器，你不用手写 new。" },
      { t: "p", label: "机制", text: "容器不是在类加载时自动启动的；是 main 执行到 new ClassPathXmlApplicationContext 那一刻，才开始解析 XML、创建 Bean、注入依赖。" },
      { t: "p", label: "陷阱", text: "没有 main 的类不能直接运行（IDEA 左侧不会出现绿色三角）；Spring Boot 的启动类也不用你写 new，但它的 main 里仍然调用了 SpringApplication.run。" },
      { t: "p", label: "一句话记", text: "App 是入口，ApplicationContext 是容器；入口里启动容器，容器里装 Bean。" },
    ],
  },
  {
    no: "004",
    date: "2026-11-11",
    cat: "Spring 核心",
    title: "App 里的代码逐行解释：启动容器 → 取 Bean → 调用",
    kind: "答疑",
    blocks: [
      { t: "p", label: "问题", text: "App 里这几行代码分别是什么意思？" },
      { t: "code", text: "ApplicationContext ctx = new ClassPathXmlApplicationContext(\"beans.xml\");\nBookService bookService = (BookService) ctx.getBean(\"bookService\");\nbookService.save();" },
      { t: "p", label: "逐行", text: "① new ClassPathXmlApplicationContext(\"beans.xml\")：创建容器并读取 classpath 根下的 beans.xml。这一步会解析 XML、创建所有 Bean、完成依赖注入，返回容器对象 ctx。ApplicationContext 是接口，ClassPathXmlApplicationContext 是它的一种实现（从 classpath 读 XML）。" },
      { t: "p", label: "逐行", text: "② ctx.getBean(\"bookService\")：按 id 从容器取 Bean，返回 Object，所以要 (BookService) 强转。\"bookService\" 就是 XML 里的 id；Spring 默认单例，拿到的是容器里那个唯一实例。" },
      { t: "p", label: "逐行", text: "③ bookService.save()：调用接口方法。实际执行 BookServiceImpl.save()，里面再调 bookDao.save()，而 bookDao 是容器注入的 BookDaoImpl。" },
      { t: "p", label: "关键点", text: "getBean 有三种常用写法：getBean(\"id\") 返回 Object 要强转；getBean(BookService.class) 按类型取不用强转，但同类型有多个 Bean 时会报 NoUniqueBeanDefinitionException；getBean(\"id\", BookService.class) 按 id + 类型取，最安全。" },
      { t: "p", label: "陷阱", text: "id 写错 → NoSuchBeanDefinitionException；强转类型写错 → ClassCastException；非 Web 程序 main 结束后 JVM 退出，如果想触发 destroy 方法，可以显式 ctx.close()。" },
      { t: "p", label: "一句话记", text: "new ClassPathXmlApplicationContext = 启动容器；getBean = 从容器拿对象；调方法 = 用对象。三行就是「启动 → 取 → 用」。" },
    ],
  },
  {
    no: "005",
    date: "2026-11-11",
    cat: "Java 基础",
    title: "Object 是什么：所有类的祖宗，getBean 为什么要强转",
    kind: "答疑",
    blocks: [
      { t: "p", label: "问题", text: "Object 是什么意思？为什么 ctx.getBean(\"bookService\") 前面要加 (BookService)？" },
      { t: "p", label: "是什么", text: "Object 是 Java 里所有类的「祖宗」——java.lang.Object。你写的每个类，即使没写 extends，也默认继承了它。所以任何对象都可以装进一个 Object 变量里。" },
      { t: "p", label: "机制", text: "getBean(String id) 的方法签名返回的是 Object，因为 Spring 在编译期不知道 XML 里配的是什么类型——类型是运行时读 XML 才知道的。它只能先返回一个「万能类型」Object。" },
      { t: "p", label: "逐行", text: "Object obj = ctx.getBean(\"bookService\"); 这行能过，但 obj 只能调 Object 自带的 toString / equals / hashCode，调不了 save()——编译器不知道它其实是 BookService。" },
      { t: "p", label: "逐行", text: "BookService bookService = (BookService) ctx.getBean(\"bookService\"); 强转是在告诉编译器：这个 Object 实际上是 BookService，按 BookService 用。运行时 JVM 会检查；如果不是，抛 ClassCastException。" },
      { t: "p", label: "关键点", text: "强转不改变对象本身，只改变「编译器允许你调哪些方法」。就像快递箱上写着「易碎品」，箱子里的东西没变，只是你知道了该怎么拿。" },
      { t: "p", label: "陷阱", text: "id 取出来的是 BookDaoImpl，却强转成 BookService → ClassCastException。用 getBean(BookService.class) 或 getBean(\"bookService\", BookService.class) 可以让 Spring 帮你做类型检查，避免手写强转。" },
      { t: "p", label: "一句话记", text: "Object 是所有类的祖宗，能装任何对象，但只能调祖宗自己的方法；要用子类的方法，就得强转回它真正的类型。getBean(String) 返回 Object，所以必须强转。" },
    ],
  },
  {
    no: "006",
    date: "2026-11-11",
    cat: "Spring 核心",
    title: "删掉 bookDao 之后：Spring 在启动时就报 NoSuchBeanDefinitionException",
    kind: "破坏性实验",
    blocks: [
      { t: "p", label: "问题", text: "把 beans.xml 里的 <bean id=\"bookDao\" .../> 删掉（或注释）后，为什么 App 一启动就报错？" },
      { t: "p", label: "现象", text: "BeanCreationException: Error creating bean with name 'bookService' ... Cannot resolve reference to bean 'bookDao' while setting bean property 'bookDao'; nested exception is NoSuchBeanDefinitionException: No bean named 'bookDao' available" },
      { t: "p", label: "根因", text: "<property name=\"bookDao\" ref=\"bookDao\"/> 让容器去 Map 里找 key = bookDao。找不到，容器在 refresh() 阶段就 fail-fast。容器启动时会把单例 Bean 都创建好，所以错误发生在启动时，不是调用 save() 时。" },
      { t: "p", label: "区别", text: "如果删的是 <bean 开头（XML 不合法），报的是 SAXParseException / cvc-complex-type.2.3，这是 XML 解析层；如果删的是整行（XML 合法但依赖缺失），才是 Spring 装配层的 NoSuchBeanDefinitionException。两层要分清。" },
      { t: "p", label: "解决", text: "把 <bean id=\"bookDao\" class=\"com.itheima.dao.impl.BookDaoImpl\"/> 加回来，重新运行。" },
      { t: "p", label: "教训", text: "读异常先看最底下的 Caused by；启动即失败（fail-fast）比运行时 NPE 好查。XML 报错的行号可能滞后，要从上往下找结构问题。" },
      { t: "p", label: "一句话记", text: "XML 不合法 → 解析器炸；XML 合法但依赖缺失 → 容器装配时炸。两种错，两层原因。" },
    ],
  },
  {
    no: "007",
    date: "2026-11-11",
    cat: "Spring 核心",
    title: "静态工厂方法忘了写 static：No matching factory method",
    kind: "踩坑",
    blocks: [
      { t: "p", label: "问题", text: "beans.xml 配了 factory-method=\"getBookDao\"，运行却报 No matching factory method found on class [...BookDaoFactory]: factory method 'getBookDao()'. Check that a method with the specified name exists and that it is static." },
      { t: "p", label: "现象", text: "BeanCreationException: Error creating bean with name 'bookDao' ... No matching factory method found ... Check that a method with the specified name exists and that it is static." },
      { t: "p", label: "根因", text: "XML 里的 class + factory-method 表示「调用这个类的静态方法」。Spring 不会 new 一个工厂对象，而是直接 BookDaoFactory.getBookDao()。方法少了 static，就是实例方法，类上根本没有这个静态方法 → 找不到。" },
      { t: "p", label: "解决", text: "在方法签名上加 static：public static BookDao getBookDao() { ... }。" },
      { t: "p", label: "相关", text: "如果确实想用实例方法，就要用「实例工厂」写法：先声明工厂 bean <bean id=\"bookDaoFactory\" class=\"...BookDaoFactory\"/>，再 <bean id=\"bookDao\" factory-bean=\"bookDaoFactory\" factory-method=\"getBookDao\"/>。这是不同模式，不是错误。" },
      { t: "p", label: "教训", text: "报错最后一句 Check that a method with the specified name exists and that it is static 就是答案。先读最后一句，再看堆栈。" },
      { t: "p", label: "一句话记", text: "class + factory-method = 静态工厂，方法必须 static；实例方法要用 factory-bean。" },
    ],
  },
  {
    no: "008",
    date: "2026-11-11",
    cat: "Spring 核心",
    title: "加了 static 之后两个对象相等？不是 static 的功劳，是 scope",
    kind: "答疑",
    blocks: [
      { t: "p", label: "问题", text: "为什么静态工厂方法加了 static 之后，两次 getBean(\"bookDao\") 拿到的是同一个对象？" },
      { t: "p", label: "是什么", text: "两个维度别混：factory-method / static 决定「怎么造对象」，scope 决定「造几个对象」。两次相等是因为 scope 默认是 singleton，和 static 没关系。" },
      { t: "p", label: "机制", text: "单例：第一次 getBean(\"bookDao\") 时创建对象并放进容器的 Map；第二次直接返回 Map 里缓存的那个，工厂方法只被调用一次。多例：每次 getBean 都重新调用工厂，返回新对象。" },
      { t: "p", label: "对照", text: "之前用构造方法创建时，两次 getBean 也是 true——那时根本没有 static。现在给 bookDao 加 scope=\"prototype\"，即使工厂方法是 static，两次也会变成 false，而且工厂会打印多次。这证明数量由 scope 决定。" },
      { t: "p", label: "陷阱", text: "不要把「静态」理解成「单例」。static 是 Java 语言层面「方法属于类、不需要对象」；scope 是 Spring 层面「容器里存几个实例」，两者互不相干。" },
      { t: "p", label: "一句话记", text: "static 管怎么造，scope 管造几个；相等是单例的功劳，不是 static 的功劳。" },
    ],
  },
  {
    no: "009",
    date: "2026-11-13",
    cat: "Spring 核心",
    title: "AOP 切入点表达式通配符速查",
    kind: "速查",
    blocks: [
      { t: "p", label: "问题", text: "execution 表达式怎么写？各个通配符是什么意思？" },
      { t: "p", label: "是什么", text: "execution(返回值类型 包名.类名.方法名(参数类型))，四段缺一不可。" },
      { t: "code", text: "execution(* com.itheima.service.BookService.*(..))   // BookService 所有方法\nexecution(* com.itheima.service..*.*(..))            // service 及子包所有方法\nexecution(* find*(..))                               // 所有以 find 开头的方法\nexecution(* com.itheima.service.*.save(..))          // service 包下所有类的 save 方法" },
      { t: "ul", items: [
        "* —— 任意（返回值 / 类名 / 方法名）",
        ".. —— 包路径：当前包及子包；参数：任意个参数",
        "() —— 无参",
        "(..) —— 任意参数",
        "(*) —— 一个任意类型参数",
        "(Integer, *) —— 两个参数：第一个 Integer，第二个任意",
      ] },
      { t: "p", label: "陷阱", text: "四段缺一不可：返回值、包.类、方法名、参数。类名和方法名之间是一个点，别漏；括号必须是英文 ( )；BookService* 不是合法的类名通配，要写 BookService.*(..)。" },
      { t: "p", label: "一句话记", text: "返回值 包.类.方法(参数)；* 表示任意，.. 表示任意个/子包。" },
    ],
  },
];

ENTRIES.sort((a, b) => a.no.localeCompare(b.no));

// ============ 排版工具 ============
const BOX = { style: BorderStyle.SINGLE, size: 4, color: "D0D0D0" };

function codeBlock(text) {
  const lines = text.split("\n");
  return lines.map((line, i) =>
    new Paragraph({
      shading: { type: ShadingType.CLEAR, fill: "F4F4F4" },
      spacing: { before: i === 0 ? 80 : 0, after: i === lines.length - 1 ? 160 : 0, line: 260 },
      indent: { left: 480, right: 240 },
      children: [new TextRun({ text: line || " ", font: MONO, size: 18, color: "333333" })],
    })
  );
}

const LABEL_COLOR = {
  问题: "7F7F7F",
  是什么: "1F4E79",
  逐行: "5B3E96",
  关键点: "C00000",
  陷阱: "C00000",
  机制: "5B3E96",
  对照: "2E7D32",
  区别: "2E7D32",
  一句话记: "B8860B",
  相关: "7B1FA2",
  为什么: "C00000",
};

function labelParagraph(label, text) {
  return new Paragraph({
    spacing: { before: 100, after: 100, line: 300 },
    indent: { left: 200 },
    children: [
      new TextRun({ text: `【${label}】`, bold: true, color: LABEL_COLOR[label] || "333333", font: SONG, size: 21 }),
      new TextRun({ text: text, font: SONG, size: 21 }),
    ],
  });
}

function entryBlocks(e) {
  const out = [];
  out.push(
    new Paragraph({
      spacing: { before: 360, after: 60 },
      children: [
        new TextRun({ text: ` 第 ${e.no} 条 `, bold: true, color: "FFFFFF", font: YAHEI, size: 20, shading: { type: ShadingType.CLEAR, fill: "1F3864" } }),
        new TextRun({ text: `  ${e.cat}`, color: "1F3864", font: YAHEI, size: 18 }),
        new TextRun({ text: `   ${e.kind}`, color: "909090", font: YAHEI, size: 18 }),
      ],
    })
  );
  out.push(
    new Paragraph({
      spacing: { after: 80 },
      children: [new TextRun({ text: e.title, bold: true, font: YAHEI, size: 26, color: "202020" })],
    })
  );
  out.push(
    new Paragraph({
      spacing: { after: 60 },
      children: [new TextRun({ text: e.date, font: SONG, size: 18, color: "909090" })],
    })
  );
  for (const b of e.blocks) {
    if (b.t === "p") out.push(labelParagraph(b.label, b.text));
    else if (b.t === "ul") {
      b.items.forEach((it) =>
        out.push(
          new Paragraph({
            bullet: { level: 0 },
            spacing: { after: 60, line: 300 },
            indent: { left: 480, hanging: 220 },
            children: [new TextRun({ text: it, font: SONG, size: 21 })],
          })
        )
      );
    } else if (b.t === "code") out.push(...codeBlock(b.text));
  }
  return out;
}

// ============ 正文组装 ============
const children = [];

children.push(
  new Paragraph({
    spacing: { before: 1200, after: 120 },
    alignment: AlignmentType.CENTER,
    children: [new TextRun({ text: "SSM 学习 · 错题本", bold: true, font: YAHEI, size: 52, color: "1F3864" })],
  })
);
children.push(
  new Paragraph({
    spacing: { after: 600 },
    alignment: AlignmentType.CENTER,
    children: [new TextRun({ text: "问过的每一个「为什么」，都要能自己讲回来", font: SONG, size: 22, color: "808080" })],
  })
);
children.push(
  new Paragraph({
    alignment: AlignmentType.CENTER,
    spacing: { after: 60 },
    children: [new TextRun({ text: `建立于 2026-11-11　·　当前收录 ${ENTRIES.length} 条`, font: SONG, size: 20, color: "909090" })],
  })
);
children.push(new Paragraph({ children: [new PageBreak()] }));

children.push(new Paragraph({ text: "怎么用这本错题本", heading: HeadingLevel.HEADING_1 }));
children.push(
  new Paragraph({
    spacing: { before: 100, after: 80 },
    children: [new TextRun({ text: "为什么要有它", bold: true, font: YAHEI, size: 24, color: "1F3864" })],
  })
);
children.push(
  new Paragraph({
    spacing: { after: 120, line: 320 },
    children: [new TextRun({ text: "抽象的知识点容易忘。问过的问题、踩过的错，写下来才能变成自己的；只听过一遍，过两天就只剩「好像讲过」。", font: SONG, size: 21 })],
  })
);
children.push(
  new Paragraph({
    spacing: { before: 100, after: 80 },
    children: [new TextRun({ text: "什么时候写", bold: true, font: YAHEI, size: 24, color: "1F3864" })],
  })
);
children.push(
  new Paragraph({
    spacing: { after: 120, line: 320 },
    children: [new TextRun({ text: "每次问完我问题、或者踩完一个坑，收工前 5 分钟把 Q&A 整理进来。这本只收 SSM 计划里的内容。", font: SONG, size: 21 })],
  })
);
children.push(
  new Paragraph({
    spacing: { before: 100, after: 80 },
    children: [new TextRun({ text: "怎么复习", bold: true, font: YAHEI, size: 24, color: "1F3864" })],
  })
);
[
  "每天睡前翻 3 条，只看「一句话记」，能自己把前因后果讲出来就算过。",
  "周末整本翻一遍，讲不出来的条目打星号，下周优先看。",
  "动手写代码前，先扫一眼同类问题——错题本的价值在于「不让同一个问题问第二次」。",
].forEach((t) =>
  children.push(
    new Paragraph({
      bullet: { level: 0 },
      spacing: { after: 60, line: 320 },
      indent: { left: 480, hanging: 220 },
      children: [new TextRun({ text: t, font: SONG, size: 21 })],
    })
  )
);

children.push(new Paragraph({ text: "每条答疑的格式", heading: HeadingLevel.HEADING_2 }));
const fmtRows = [
  ["问题", "当时问的是什么", "把原问题抄下来"],
  ["是什么", "一句话定义 / 它解决什么问题", "先给结论"],
  ["机制", "内部怎么跑 / 执行顺序", "这一行写不出机制，等于没记"],
  ["陷阱", "最容易错的地方 / 报错对照", "贴报错原文"],
  ["一句话记", "能复用到别处的总结", "复习时只看这一行"],
];
children.push(
  new Table({
    width: { size: 8600, type: WidthType.DXA },
    columnWidths: [1100, 3600, 3900],
    rows: [
      new TableRow({
        tableHeader: true,
        children: ["行", "写什么", "要求"].map((h) =>
          new TableCell({
            shading: { type: ShadingType.CLEAR, fill: "1F3864" },
            margins: { top: 60, bottom: 60, left: 100, right: 100 },
            children: [new Paragraph({ children: [new TextRun({ text: h, bold: true, color: "FFFFFF", font: YAHEI, size: 20 })] })],
          })
        ),
      }),
      ...fmtRows.map((row, ri) =>
        new TableRow({
          children: row.map((cell, ci) =>
            new TableCell({
              shading: { type: ShadingType.CLEAR, fill: ri % 2 === 0 ? "FFFFFF" : "F5F7FA" },
              margins: { top: 60, bottom: 60, left: 100, right: 100 },
              children: [
                new Paragraph({
                  children: [
                    new TextRun({
                      text: cell,
                      font: SONG,
                      size: 20,
                      bold: ci === 0,
                      color: ci === 0 ? LABEL_COLOR[cell] || "333333" : "333333",
                    }),
                  ],
                }),
              ],
            })
          ),
        })
      ),
    ],
  })
);

children.push(new Paragraph({ text: "分类", heading: HeadingLevel.HEADING_2 }));
children.push(
  new Paragraph({
    spacing: { after: 100 },
    children: [new TextRun({ text: CATS.join("　|　"), font: SONG, size: 21, color: "404040" })],
  })
);

children.push(new Paragraph({ children: [new PageBreak()] }));

// 索引
children.push(new Paragraph({ text: "索引", heading: HeadingLevel.HEADING_1 }));
children.push(
  new Paragraph({
    spacing: { after: 160 },
    children: [new TextRun({ text: "按编号速查，复习时可以用这张表打勾。", font: SONG, size: 20, color: "808080" })],
  })
);
children.push(
  new Table({
    width: { size: 8600, type: WidthType.DXA },
    columnWidths: [900, 1300, 1900, 4500],
    rows: [
      new TableRow({
        tableHeader: true,
        children: ["编号", "日期", "分类", "标题"].map((h) =>
          new TableCell({
            shading: { type: ShadingType.CLEAR, fill: "1F3864" },
            margins: { top: 60, bottom: 60, left: 100, right: 100 },
            children: [new Paragraph({ children: [new TextRun({ text: h, bold: true, color: "FFFFFF", font: YAHEI, size: 20 })] })],
          })
        ),
      }),
      ...ENTRIES.map((e, ri) =>
        new TableRow({
          children: [e.no, e.date, e.cat, e.title].map((cell) =>
            new TableCell({
              shading: { type: ShadingType.CLEAR, fill: ri % 2 === 0 ? "FFFFFF" : "F5F7FA" },
              margins: { top: 60, bottom: 60, left: 100, right: 100 },
              children: [new Paragraph({ children: [new TextRun({ text: cell, font: SONG, size: 20 })] })],
            })
          ),
        })
      ),
    ],
  })
);

children.push(new Paragraph({ children: [new PageBreak()] }));

// 正文
children.push(new Paragraph({ text: "答疑正文", heading: HeadingLevel.HEADING_1 }));
for (const cat of CATS) {
  const list = ENTRIES.filter((e) => e.cat === cat);
  if (!list.length) continue;
  children.push(new Paragraph({ text: cat, heading: HeadingLevel.HEADING_2 }));
  for (const e of list) children.push(...entryBlocks(e));
}

// 空白模板
children.push(new Paragraph({ children: [new PageBreak()] }));
children.push(new Paragraph({ text: "空白模板（复制这一段来写新答疑）", heading: HeadingLevel.HEADING_1 }));
children.push(
  new Paragraph({
    spacing: { after: 160 },
    children: [new TextRun({ text: "在 Word 里直接复制下面这条，改掉内容即可。写完把这页删掉也不影响前面。", font: SONG, size: 20, color: "808080" })],
  })
);
children.push(
  ...entryBlocks({
    no: "XXX",
    date: "2026-MM-DD",
    cat: "分类",
    kind: "答疑",
    title: "一句话标题：这里写你问的问题",
    blocks: [
      { t: "p", label: "问题", text: "当时问的是什么，把原问题抄下来。" },
      { t: "p", label: "是什么", text: "一句话定义 / 它解决什么问题。" },
      { t: "p", label: "机制", text: "内部怎么跑，执行顺序是什么。" },
      { t: "p", label: "陷阱", text: "最容易错的地方，报错原文是什么。" },
      { t: "p", label: "一句话记", text: "能复用到别处的总结。" },
    ],
  })
);

// ============ 文档 ============
const doc = new Document({
  styles: {
    default: {
      document: {
        run: { font: SONG, size: 21, color: "262626" },
        paragraph: { spacing: { line: 300 } },
      },
    },
    paragraphStyles: [
      {
        id: "Heading1", name: "Heading 1", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { font: YAHEI, size: 34, bold: true, color: "1F3864" },
        paragraph: { spacing: { before: 240, after: 200 }, outlineLevel: 0 },
      },
      {
        id: "Heading2", name: "Heading 2", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { font: YAHEI, size: 26, bold: true, color: "2E5496" },
        paragraph: { spacing: { before: 320, after: 140 }, outlineLevel: 1 },
      },
    ],
  },
  sections: [
    {
      properties: {
        page: {
          size: { width: 11906, height: 16838 },
          margin: { top: 1300, right: 1440, bottom: 1300, left: 1440 },
        },
      },
      headers: {
        default: new Header({
          children: [
            new Paragraph({
              alignment: AlignmentType.RIGHT,
              border: { bottom: { style: BorderStyle.SINGLE, size: 4, color: "D9D9D9", space: 4 } },
              children: [new TextRun({ text: "SSM 学习 · 错题本", font: YAHEI, size: 16, color: "A0A0A0" })],
            }),
          ],
        }),
      },
      footers: {
        default: new Footer({
          children: [
            new Paragraph({
              alignment: AlignmentType.CENTER,
              children: [
                new TextRun({ text: "第 ", font: SONG, size: 16, color: "A0A0A0" }),
                new TextRun({ children: [PageNumber.CURRENT], font: SONG, size: 16, color: "A0A0A0" }),
                new TextRun({ text: " 页", font: SONG, size: 16, color: "A0A0A0" }),
              ],
            }),
          ],
        }),
      },
      children,
    },
  ],
});

Packer.toBuffer(doc).then((buffer) => {
  fs.writeFileSync(OUT, buffer);
  console.log("OK: " + OUT + "  (" + buffer.length + " bytes)");
});
