# 关 1 · Spring 是什么（P2–P5）

> 日期：2026-11-11
> 目标：说清 IoC / DI / Bean 的关系，理解 Spring 解决的是「对象由谁创建、依赖由谁传入」的问题。

---

## 1. 一句话说清 IoC / DI / Bean

- **IoC（控制反转）**：对象的创建权，从「自己 new」反转到「容器」。
- **DI（依赖注入）**：容器把对象需要的依赖注入进来，是 IoC 的实现手段。
- **Bean**：被 Spring 容器创建和管理的对象。

**一句话：IoC 是思想，DI 是实现手段，Bean 是容器管的东西。**

---

## 2. 写法 A vs 写法 B

### 写法 A：类内部 new（没有反转）

```java
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;

    public OrderServiceImpl() {
        this.orderMapper = new OrderMapperImpl(); // 自己造
    }
}
```

问题：

- `OrderServiceImpl` 和 `OrderMapperImpl` 焊死
- 10 个类依赖它，就有 10 处 `new`，换实现要改 10 个类
- 无法单独测试（绕不开真实实现 / 数据库）

### 写法 B：外部 new，构造器传入（DI）

```java
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) { // 别人造好传进来
        this.orderMapper = orderMapper;
    }
}
```

```java
// 装配发生在入口一处
OrderMapper orderMapper = new CachedOrderMapper();
OrderServiceImpl service = new OrderServiceImpl(orderMapper);
```

**为什么 B 更好：**

- 换实现只改装配处 1 行
- 类只认接口，不认具体实现
- 测试可以传 `FakeOrderMapper`，不用启动数据库

> 关键：DI 的关键不是「有没有构造器 / setter」，而是 **对象由谁来 new、由谁来传**。
> 在类内部 new = 没反转；在类外部 new、容器负责传 = 反转了。

---

## 3. Spring 的 5 个模块

| 模块 | 作用 | 对应依赖 |
|---|---|---|
| Core Container | IoC 容器、Bean 管理 | `spring-core` / `spring-beans` / `spring-context` |
| AOP | 面向切面 | `spring-aop` / `aspectjweaver` |
| Data Access | JDBC、事务、MyBatis 整合 | `spring-jdbc` / `spring-tx` |
| Web | SpringMVC | `spring-webmvc` |
| Test | 测试整合 | `spring-test` + JUnit |

---

## 4. 面试锚点

- 什么是 IoC？DI 和 IoC 是什么关系？
- Spring 的核心模块有哪些？
- 为什么 Spring 官方推荐构造器注入？

---

## 5. 我的疑问 / 待补

- （留空，之后补）
