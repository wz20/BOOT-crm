# 								**BOOT客户管理系统（详解）**

## Github：https://github.com/wz20

## Gitee：https://gitee.com/Wang-ZeLXM

本文中的代码不可以直接粘贴，因为有一些没有写在注释中的文字分析！请前往github，或CSDN资源

## 演示图：

![image-20210512195956401](D:\OneDrive\桌面\JavaEE资料\演示.png)

# 本系统SSM整合思路：

![image-20210512183618433](D:\OneDrive\桌面\JavaEE资料\ssm整合思路.png)

## springMVC系统流程图：

![image-20210512201444109](D:\OneDrive\桌面\JavaEE资料\springmvc工作流程.png)

# 一. 环境搭建

## 1.pom.xml添加依赖

```xml
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
        <!-- spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <!--springmvc所用依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <!--Servlet - JSP-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <!--  mybatis所用依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.6</version>
        </dependency>
        <!--   数据库所用依赖-->
        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.24</version>
        </dependency>
        <!--数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.1</version>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <!--日志 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.14</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.25</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.25</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.logback-extensions</groupId>
            <artifactId>logback-ext-spring</artifactId>
            <version>0.1.4</version>
        </dependency>
		<!-- 工具包 -->
         <dependency>
             <groupId>org.apache.commons</groupId>
             <artifactId>commons-lang3</artifactId>
             <version>3.11</version>
         </dependency>
        <!--jackson-->
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.2</version>
        </dependency>
        <!-- 文件上传下载 -->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.2</version>
        </dependency>
        <!--事务-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>
```

## 2. 创建目录结构

![image-20210511143522973](D:\OneDrive\桌面\JavaEE资料\boot1.png)



## 3. 配置文件的编写

### spring配置文件---applicationContext的编写

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
<!--扫描包除了带有controller注解的-->
    <context:component-scan base-package="com.wangze" use-default-filters="true">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
<!-- 整合mybatis-->
    <context:property-placeholder location="classpath:db.properties"/>
    <!--连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <!--最大连接数10，最小空闲数5-->
        <property name="maxActive" value="10" />
        <property name="minIdle" value="5" />
    </bean>
    <!--配置sqlSessionFactoryBean-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="typeAliasesPackage" value="com.wangze.core.entity"/>有了这个别名，在mapper中就可以不用写全类名
        <property name="mapperLocations">
            <list>
                <value>classpath:com.wangze.mapper/*Mapper.xml</value>扫描mapper下所有以Mapper结尾的文件
            </list>
        </property>
    </bean>
    <!--创建DAO对象 MapperScannerConfigure-->
    <bean id="scanner"
          class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
        <property name="basePackage" value="com.wangze.core.dao">  指定mapper对应的dao接口包
        </property>
    </bean>
<!--  整合mybatis结束-->

<!--注解开发 事务  事务的开发就是把AOP开发中的额外方法换成事务-->
    <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <tx:annotation-driven transaction-manager="dataSourceTransactionManager"/>
    <!--只需要在原始对象上加注解：@Tronsaction    增删改操作 @Transactional 查询操作  @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)-->

</beans>
```

### mvc配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">
<!--配置包扫描-->
    <context:component-scan base-package="com.wangze" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
    <!--加载属性文件-->
    <context:property-placeholder location="classpath:resource.properties"/>
    <!--处理器映射器 适配器-->
    <mvc:annotation-driven/>
    <!--配置静态资源访问映射，此配置中的文件将不会呗前端控制器拦截 -->
    <mvc:resources location="/js/" mapping="/js/**" />
    <mvc:resources location="/css/" mapping="/css/**" />
    <mvc:resources location="/fonts/" mapping="/fonts/**" />
    <mvc:resources location="/images/" mapping="/images/**" />

    <!-- 配置视图解释器ViewResolver -->
    <bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp" />
    </bean>


</beans>
```

### db.properties（数据库连接池）

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/boot_crm
jdbc.username=root
jdbc.password=123456
jdbc.initialSize=5
```

### resource.properties（用于查询数据字典中的数据）

```properties
customer.from.type=002
customer.industry.type=001
customer.level.type=006
```

### log4j.properties（log4j配置文件）

```properties
# resources文件夹根目录下
log4j.rootLogger=debug,console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
```

### logback.xml（logback配置文件）

当取消spring文件用spring3.0+提供的@Bean所取代时，log4j不能用了，我们使用logback日志

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 控制台输出 -->
    <appender name="STDOUT"
              class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--格式化输出：%d表示⽇期，%thread表示线程名，
           %-5level：级别从左显示5个字符宽度%msg：⽇志消息，%n是换⾏符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS}
                [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="DEBUG">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
<!-- spring-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
<!--监听器-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
<!--springmvc-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>  //中央控制器
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>         //启动时机
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>             //默认所有请求都被springmvc处理，需要配置静态资源不被拦截；
    </servlet-mapping>
<!--    过滤器 防止请求和相应的数据乱码 -->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
<!--默认首页-->
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
</web-app>
```

## 4.导入页面资源

```
请访问我的github获取：https://github.com/wz20 
```

## 5.导入数据库

```sql
boot_crm
```



# 二、实现登录功能（User相关）

```markdown
用户发出登录请求后，验证登录账号和密码，如果正确，跳转视图层；如果错误，给出提示；
```



## 1.编写user实体类

在entity包下，由于我使用了lombok，所以@Data  自动生成set，get方法

```java
package com.wangze.core.entity;import lombok.Data;import org.springframework.stereotype.Component;import java.io.Serial;import java.io.Serializable;/** * @author: 王泽20 * 一个对象序列化的接口，一个类只有实现了Serializable接口，它的对象才能被序列化。 *    从说明中我们可以看到，如果我们没有自己声明一个serialVersionUID变量,接口会默认生成一个serialVersionUID *    但是强烈建议用户自定义一个serialVersionUID,因为默认的serialVersionUID对于class的细节非常敏感，反序列化时可能会导致InvalidClassException这个异常。 *    在前面我们已经新建了一个实体类User实现Serializable接口，并且定义了serialVersionUID变量。 */@Component@Datapublic class User implements Serializable {    @Serial    private static final long serialVersionUID = 1L;    private Integer user_Id;     //用户id    private String user_code;   //用户账号    private String user_name;   //用户名称    private String user_password;   //用户密码    private Integer user_state;     //用户状态}
```

## 2.编写UserDao接口

```java
package com.wangze.core.dao;import com.wangze.core.entity.User;import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;/** * @author: 王泽20 * 通过账号密码查询用户 */@Repository   //这个注解相当于<bean>标签，是@Component的衍生注解，用于dao层public interface UserDao {    public User findUser(@Param("usercode") String usercode, @Param("password") String password);    //当你使用了使用@Param注解来声明参数时，如果使用 #{} 或 ${} 的方式都可以。    //在旧版本中如果想在mapper.xml 中引用变量使用${} 必须用@param为变量起别名；新版只要是只有一个参数都可以不用起别名    //# 和 $ 到底有什么区别？？？   #采用占位符的方式   $采用参数拼接的方式    参数拼接有SQL注入的风险}
```

### 对应mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE mapper        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"        "http://mybatis.org/dtd/mybatis-3-mapper.dtd"><mapper namespace="com.wangze.core.dao.UserDao"><!--查询用户-->    <select id="findUser" parameterType="String" resultType="com.wangze.core.entity.User">        select * from sys_user        where user_code = #{usercode}        and user_password = #{password}        and user_state = '1'    </select></mapper>
```



## 3.编写UserService接口

```java
package com.wangze.core.service;import com.wangze.core.entity.User;public interface UserService {    public User findUser(String usercode,String password);}
```

## 4.编写UserServiceImpl

```java
package com.wangze.core.service;import com.wangze.core.dao.UserDao;import com.wangze.core.entity.User;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;/** * @author: 王泽20 */@Service  //这个注解相当于<bean>标签，是@Component的衍生注解，用于service层@Transactionalpublic class UserServiceImpl implements UserService {    @Autowired				//注入成员变量userDao    private UserDao userDao;    @Override  //在数据库中找这个user    public User findUser(String usercode, String password) {        User user =this.userDao.findUser(usercode,password);        return user;    }}
```

## 5.编写UserController类

```java
package com.wangze.core.controller;import com.wangze.core.entity.User;import com.wangze.core.service.UserService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import javax.servlet.http.HttpSession;/** * @author: 王泽20 */@Controller  //这个注解相当于<bean>标签，是@Component的衍生注解，用于Controller层public class UserController {    @Autowired          //依赖注入    private UserService userService;    @RequestMapping(value = "/login",method = RequestMethod.POST)   //必须是post方法     public String login(String usercode, String password, Model model, HttpSession session){        User user = userService.findUser(usercode,password);        if(user != null){                session.setAttribute("USER_SESSION",user);                return "customer";        }        model.addAttribute("msg","账号或密码错误，请重新输入");        return "login";    }}
```

## 6.测试

## 登录验证（拦截器）

### 1.创建LoginInterceptor类

```java
package com.wangze.core.interceptor;import com.wangze.core.entity.User;import org.springframework.stereotype.Component;import org.springframework.web.servlet.HandlerInterceptor;import org.springframework.web.servlet.ModelAndView;import javax.servlet.ServletException;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;import javax.servlet.http.HttpSession;import java.io.IOException;/** * @author: 王泽20    springmvc中的拦截器，只有返回值为true，后面的方法才会继续执行 */@Componentpublic class LoginInterceptor implements HandlerInterceptor {    @Override    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws ServletException, IOException {        //获取请求的url        String url =request.getRequestURI();        //拦截不是登录的请求        if(url.contains("/login")){            return true;        }        //获取session        HttpSession session =request.getSession();        User user= (User) session.getAttribute("USER_SESSION");        //判断session中是否有用户数据，如果有，则返回true，继续向下执行        if (user != null){            return true;        }        //不符合条件的给出提示信息，并转发到登录界面        request.setAttribute("msg","您还没有登录，请先登录");        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);  //抛出异常        return false;    }    @Override    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {    }    @Override    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {    }}
```



### 2.在springmvc配置文件中配置拦截器

```xml
<!--   拦截器-->    <mvc:interceptors>    <mvc:interceptor>        <mvc:mapping path="/**"/>        <ref bean="loginInterceptor"/>    </mvc:interceptor>    </mvc:interceptors>
```

### 3.验证

```java
 @RequestMapping("/tocon")    public String tocon(){        return "customer";    }浏览器输入...../tocon   
```

## 退出登录

在userController中

```java
 /* 退出登录 */    @RequestMapping("logout")    public String logout(HttpSession session){        session.invalidate();  //清除session        return "redirect:login";    }    //向用户登录界面跳转    @RequestMapping(value = "login",method = RequestMethod.GET)    public String toLogin(){        return "login";    }
```

# 三、客户管理模块

```asciiarmor
对客户表，进行增删改查  （重点：分页查询  具体两个工具类此处不做介绍，在github中可以获取源码）
```



## 一、查询操作

### 1.实体类

- Customer

  ```java
  package com.wangze.core.entity;import lombok.Data;import org.springframework.stereotype.Component;import java.io.Serial;import java.io.Serializable;import java.util.Date;/** * @author: 王泽20 */@Data@Componentpublic class Customer implements Serializable {    @Serial    private static final long serialVersionUID = 1L;    private Integer cust_id;                    //客户编号    private String cust_name;                   //客户名称    private Integer cust_user_id;               //负责人id    private Integer cust_create_id;              //创建人id    private String cust_source;                 //客户信息来源    private String cust_industry;               //客户所属行业    private String cust_level;                  //客户级别    private String cust_linkman;                //联系人    private String cust_phone;                  //固定电话    private String cust_mobile;                 //移动电话    private String cust_zipcode;                //邮政编码    private String cust_address;                //联系地址    private Date cust_createtime;               //创建时间    private Integer start;                      //起始行    private Integer rows;                       //所取行数}
  ```

- 数据字典 BaseDict

  ```java
  package com.wangze.core.entity;import lombok.Data;import org.springframework.stereotype.Component;import java.io.Serial;import java.io.Serializable;/** * @author: 王泽20 */@Data@Componentpublic class BaseDict implements Serializable {    @Serial    private static final long serialVersionUID = 1L;    private String dict_id;                 //数据字典id    private String dict_type_code;          //数据字典类别代码    private String dict_type_name;          //数据字典类别名称    private String dict_item_name;          //数据字典项目名称    private String dict_item_code;          //数据字典项目代码    private Integer dict_sort;          //排序字段    private String dict_enable;         //是否可用    private String dict_memo;           //备注}
  ```

  

### 2.Dao

- CustomerDao

  ```java
  package com.wangze.core.dao;import com.wangze.core.entity.Customer;import org.springframework.stereotype.Repository;import java.util.List;@Repositorypublic interface CustomerDao {    //客户列表    public List<Customer> selectCustomerList(Customer customer);    //客户数    public Integer selectCustomerListCount(Customer customer);}
  ```

- BaseDictDao

  ```java
  package com.wangze.core.dao;import com.wangze.core.entity.BaseDict;import java.util.List;public interface BaseDictDao {    public List<BaseDict> selectBaseDictByTypeCode(String typecode);}
  ```

  

### 3.Mapper

- CustomerMapper

  ```xml
  <?xml version="1.0" encoding="UTF-8"?><!DOCTYPE mapper        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"        "http://mybatis.org/dtd/mybatis-3-mapper.dtd"><mapper namespace="com.wangze.core.dao.CustomerDao">	<sql id="selectCustomerListWhere">		<where>			<if test="cust_name != null">				cust_name like "%"#{cust_name}"%"			</if>			<if test="cust_source != null">				and cust_source = #{cust_source}			</if>			<if test="cust_industry != null">				and cust_industry = #{cust_industry}			</if>			<if test="cust_level != null">				and cust_level = #{cust_level}			</if>		</where>	</sql><!--	查询客户列表-->	<select id="selectCustomerList" parameterType="com.wangze.core.entity.Customer" resultType="com.wangze.core.entity.Customer">		SELECT		cust_id,		cust_name,		cust_user_id,		cust_create_id,		b.dict_item_name,cust_source,		c.dict_item_name,cust_industry,		d.dict_item_name,cust_level,		cust_linkman,		cust_phone,		cust_mobile,		cust_createtime		FROM		customer a		LEFT JOIN (		SELECT		dict_id,		dict_item_name		FROM		base_dict		WHERE		dict_type_code = '002'		) b ON a.cust_source = b.dict_id		LEFT JOIN (		SELECT		dict_id,		dict_item_name		FROM		base_dict		WHERE		dict_type_code = '001'		) c ON a.cust_industry = c.dict_id		LEFT JOIN (		SELECT		dict_id,		dict_item_name		FROM		base_dict		WHERE		dict_type_code = '006'		) d ON a.cust_level = d.dict_id		<include refid="selectCustomerListWhere"/>		<!-- 执行分页查询 -->		<if test="start !=null and rows != null">			limit #{start},#{rows}		</if>	</select>	<!-- 查询客户总数 -->	<select id="selectCustomerListCount" parameterType="com.wangze.core.entity.Customer" resultType="Integer">		select count(*) from customer		<include refid="selectCustomerListWhere"/>	</select></mapper>
  ```

- BaseDictMapper.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?><!DOCTYPE mapper        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"        "http://mybatis.org/dtd/mybatis-3-mapper.dtd"><mapper namespace="com.wangze.core.dao.BaseDictDao">	<select id="selectBaseDictByTypeCode" parameterType="String" resultType="com.wangze.core.entity.BaseDict">		select * from base_dict where dict_type_code=#{typecode}	</select></mapper>
  ```

### 4.service

- CustomerService

  ```java
  package com.wangze.core.service;import com.wangze.common.utils.Page;import com.wangze.core.entity.Customer;import org.springframework.stereotype.Service;@Servicepublic interface CustomerService {    // 查询客户列表    public Page<Customer> findCustomerList(Integer page,Integer rows,String custName,String custSource,String custIndustry,String custLevel);}
  ```

- BaseDictService

  ```java
  package com.wangze.core.service;import com.wangze.core.entity.BaseDict;import org.springframework.stereotype.Service;import java.util.List;@Servicepublic interface BaseDictService {    //根据类别代码查询数据字典    public List<BaseDict> findBaseDictByTypeCode(String typecode);}
  ```

  

### 5.serviceimpl

- Custormer

  ```java
  package com.wangze.core.service;import com.wangze.common.utils.Page;import com.wangze.core.dao.CustomerDao;import com.wangze.core.entity.Customer;import org.apache.commons.lang3.StringUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.util.List;/** * @author: 王泽20 * 客户管理 */@Service("customerService")@Transactionalpublic class CustomerServiceImpl implements CustomerService{    @Autowired    private CustomerDao customerDao;    @Override    public Page<Customer> findCustomerList(Integer page, Integer rows, String custName, String custSource, String custIndustry, String custLevel) {      //创建客户对象        Customer customer =new Customer();        // 判断客户名称        if(StringUtils.isNotBlank(custName)){            customer.setCust_name(custName);        }        // 判断客户信息来源        if(StringUtils.isNotBlank(custSource)){            customer.setCust_source(custSource);        }        // 判断客户所属行业        if(StringUtils.isNotBlank(custIndustry)){            customer.setCust_industry(custIndustry);        }        // 判断客户级别        if(StringUtils.isNotBlank(custLevel)){            customer.setCust_level(custLevel);        }        // 当前页        customer.setStart((page-1) * rows) ;        // 每页数        customer.setRows(rows);        // 查询客户列表        List<Customer> customers =                customerDao.selectCustomerList(customer);        // 查询客户列表总记录数        Integer count = customerDao.selectCustomerListCount(customer);        // 创建Page返回对象        Page<Customer> result = new Page<>();        result.setPage(page);        result.setRows(customers);        result.setSize(rows);        result.setTotal(count);        return result;    }}
  ```

  

- BaseDict

  ```java
  package com.wangze.core.service;import com.wangze.core.dao.BaseDictDao;import com.wangze.core.entity.BaseDict;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.List;/** * @author: 王泽20 */@Service("baseDictService")public class BaseDictServiceImpl implements BaseDictService{    @Autowired    private BaseDictDao baseDictDao;    @Override    public List<BaseDict> findBaseDictByTypeCode(String typecpde) {        return baseDictDao.selectBaseDictByTypeCode(typecpde);    }}
  ```

  

### 6.Controller

```java
package com.wangze.core.controller;import com.wangze.common.utils.Page;import com.wangze.core.entity.BaseDict;import com.wangze.core.entity.Customer;import com.wangze.core.service.BaseDictService;import com.wangze.core.service.CustomerService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.context.ApplicationContext;import org.springframework.context.annotation.PropertySource;import org.springframework.context.support.ClassPathXmlApplicationContext;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestParam;import java.util.List;/** * @author: 王泽20 */@Controllerpublic class CustomerController {    @Autowired    private CustomerService customerService;    @Autowired    private BaseDictService baseDictService;    @Value("${customer.from.type}")    private String FROM_TYPE;    // 客户所属行业    @Value("${customer.industry.type}")    private String INDUSTRY_TYPE;    // 客户级别    @Value("${customer.level.type}")    private String LEVEL_TYPE;    /**     *  客户列表     */    @RequestMapping(value = "/customer/list")    public String list(@RequestParam(defaultValue="1")Integer page,                       @RequestParam(defaultValue="10")Integer rows,                       String custName, String custSource, String custIndustry,                       String custLevel, Model model) {        // 条件查询所有客户        Page<Customer> customers = customerService.findCustomerList(page, rows, custName,                        custSource, custIndustry, custLevel);        model.addAttribute("page", customers);        // 客户来源        List<BaseDict> fromType = baseDictService.findBaseDictByTypeCode(FROM_TYPE);        // 客户所属行业        List<BaseDict> industryType = baseDictService.findBaseDictByTypeCode(INDUSTRY_TYPE);        // 客户级别        List<BaseDict> levelType = baseDictService.findBaseDictByTypeCode(LEVEL_TYPE);        // 添加参数        model.addAttribute("fromType", fromType);        model.addAttribute("industryType", industryType);        model.addAttribute("levelType", levelType);        model.addAttribute("custName", custName);        model.addAttribute("custSource", custSource);        model.addAttribute("custIndustry", custIndustry);        model.addAttribute("custLevel", custLevel);        return "customer";    }}
```

## 二、添加客户（增）

分析：增添用户实则在客户表中增加数据，前面已经创建了客户的实体类，所以只用编写controller层和service以及Dao中的方法。

此过程是添加，所以需要注意事务的提交。前面有配置...可以查看spring配置文件

### 1.Dao层

```java
 // 创建客户    public int createCustomer(Customer customer);
```

### 2.mapper

```xml
<!-- 添加客户 -->	<insert id="createCustomer" parameterType="com.wangze.core.entity.Customer">		insert into customer(			cust_name,			cust_user_id,			cust_create_id,			cust_source,			cust_industry,			cust_level,			cust_linkman,			cust_phone,			cust_mobile,			cust_zipcode,			cust_address,			cust_createtime		)		values(#{cust_name},			   #{cust_user_id},			   #{cust_create_id},			   #{cust_source},			   #{cust_industry},			   #{cust_level},			   #{cust_linkman},			   #{cust_phone},			   #{cust_mobile},			   #{cust_zipcode},			   #{cust_address},			   #{cust_createtime}			  )	</insert>
```

### 3.Service

```java
//新建客户     int createCustomer(Customer customer);
```

### 4.ServiceImpl

```java
/**     * 创建客户     */    @Override    public int createCustomer(Customer customer) {        return customerDao.createCustomer(customer);    }
```

### 5.Controller

```java
/**     * 创建客户     */    @RequestMapping("/customer/create")    @ResponseBody    public String customerCreate(Customer customer, HttpSession session) {        // 获取Session中的当前用户信息        User user = (User) session.getAttribute("USER_SESSION");        // 将当前用户id存储在客户对象中        customer.setCust_create_id(user.getUser_Id());        // 创建Date对象        Date date = new Date();        // 得到一个Timestamp格式的时间，存入mysql中的时间格式“yyyy/MM/dd HH:mm:ss”        Timestamp timeStamp = new Timestamp(date.getTime());        customer.setCust_createtime(timeStamp);        // 执行Service层中的创建方法，返回的是受影响的行数        int rows = customerService.createCustomer(customer);        if(rows > 0){            return "OK";        }else{            return "FAIL";        }    }
```



## 三、修改、删除 客户

由于增删改大同小异，所以这两个合起来写了。

### 1.Dao层

```java
// 通过id查询客户    public Customer getCustomerById(Integer id);    // 更新客户信息    public int updateCustomer(Customer customer);    // 删除客户    int deleteCustomer (Integer id);
```

### 2.mapper

```xml
<!-- 根据id获取客户信息 -->	<select id="getCustomerById" parameterType="Integer"			resultType="com.wangze.core.entity.Customer">		select * from customer where cust_id = #{id}	</select>	<!-- 更新客户 -->	<update id="updateCustomer" parameterType="com.wangze.core.entity.Customer">		update customer		<set>			<if test="cust_name!=null">				cust_name=#{cust_name},			</if>			<if test="cust_user_id!=null">				cust_user_id=#{cust_user_id},			</if>			<if test="cust_create_id!=null">				cust_create_id=#{cust_create_id},			</if>			<if test="cust_source!=null">				cust_source=#{cust_source},			</if>			<if test="cust_industry!=null">				cust_industry=#{cust_industry},			</if>			<if test="cust_level!=null">				cust_level=#{cust_level},			</if>			<if test="cust_linkman!=null">				cust_linkman=#{cust_linkman},			</if>			<if test="cust_phone!=null">				cust_phone=#{cust_phone},			</if>			<if test="cust_mobile!=null">				cust_mobile=#{cust_mobile},			</if>			<if test="cust_zipcode!=null">				cust_zipcode=#{cust_zipcode},			</if>			<if test="cust_address!=null">				cust_address=#{cust_address},			</if>			<if test="cust_createtime!=null">				cust_createtime=#{cust_createtime},			</if>		</set>		where cust_id=#{cust_id}	</update>	<!-- 删除客户 -->	<delete id="deleteCustomer" parameterType="Integer">		delete from customer where cust_id=#{id}	</delete>
```

### 3.service

```java
 // 通过id查询客户    public Customer getCustomerById(Integer id);    // 更新客户    public int updateCustomer(Customer customer);    // 删除客户    public int deleteCustomer(Integer id);
```

### 4.serviceImpl

```java
 /**     * 创建客户     */    @Override    public int createCustomer(Customer customer) {        return customerDao.createCustomer(customer);    }    /**     * 通过id查询客户     */    @Override    public Customer getCustomerById(Integer id) {        Customer customer = customerDao.getCustomerById(id);        return customer;    }    /**     * 更新客户     */    @Override    public int updateCustomer(Customer customer) {        return customerDao.updateCustomer(customer);    }    /**     * 删除客户     */    @Override    public int deleteCustomer(Integer id) {        return customerDao.deleteCustomer(id);    }
```

### 5.controller

```java
   /**
     * 通过id获取客户信息
     */
    @RequestMapping("/customer/getCustomerById")
    @ResponseBody
    public Customer getCustomerById(Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return customer;
    }
    /**
     * 更新客户
     */
    @RequestMapping("/customer/update")
    @ResponseBody
    public String customerUpdate(Customer customer) {
        int rows = customerService.updateCustomer(customer);
        if(rows > 0){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    /**
     * 删除客户
     */
    @RequestMapping("/customer/delete")
    @ResponseBody
    public String customerDelete(Integer id) {
        int rows = customerService.deleteCustomer(id);
        if(rows > 0){
            return "OK";
        }else{
            return "FAIL";
        }
    }
```