# aurora
Spring Cloud 微服务脚手架

## 结构
```
├─aurora
│  │
│  ├─aurora-common----------------------------------------公共模块
│  │
│  ├─aurora-eureka----------------------------------------服务注册中心
│  │
│  ├─aurora-mybatisplus-generator-------------------------代码生成 mybatisplus 
│  │
│  ├─aurora-passport--------------------------------------后台伪单点登录模块
│  │
│  ├─aurora-zuul------------------------------------------微服务网关
│  │
│  ├─aurora-infrastructure
│  │  │
│  │  ├─aurora-hystrix-dashboard--------------------------监控面板 hystrix-dashboard 
│  │  │
│  │  └─aurora-monitor------------------------------------系统监控 spring boot admin
│  │
│  ├─aurora-portal
│  │  │
│  │  ├─aurora-portal-admin-------------------------------各系统 统一管理平台
│  │  │
│  │  ├─aurora-portal-service-----------------------------用户、角色、权限微服务
│  │  │
│  │  ├─aurora-portal-service-api-------------------------用户、角色、权限微服务API
│  │  │
│  │  └─aurora-portal-service-client----------------------用户、角色、权限微服务客户端 供其他引入
│  │

```

## 技术
* Spring Boot 2.0.6.RELEASE
* Spring Cloud Finchley.SR2
* Spring Cloud Eureka
* Spring Cloud Zuul
* Spring Cloud Hystrix
* Spring Cloud Feign
* Spring Boot Admin
* Spring Data Redis
* Apache Shiro 1.4.1
* Mybatis Plus 3.0.6
* lombok 1.18.8
* druid 1.1.20

## 运行

#### 1、hosts 文件
添加  127.0.0.1       aurora-eureka
#### 2、SQL文件
执行sql下的portal.sql 文件
#### 3、修改 redis mysql 链接
aurora-portal-admin、aurora-portal-service
#### 4、启动
aurora-eureka、aurora-portal-service、aurora-portal-admin 。。。