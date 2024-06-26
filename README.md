# OAsystem
## 项目简介
项目分为contorller层，service层，mapper层三层，controller层用于与客户端交互，service层用于实现具体业务，mapper层用于与数据库交互.此外，还自定义了一系列的DTO,VO,BO,以及用于token生成和校验的工具类JWTUtil和用于加盐加密的
EncryptUtil
## 技术栈
### springboot
  整合了许多成熟框架，可以快速构建项目，简化代码，简化配置等
### mybatis
  一款半自动的ORM持久层框架，具有较高的SQL灵活性，用于与数据库交互
### redis
  基于内存的key-value型数据库，相较于基于硬盘的数据库有更快的读写速度
### rabbitMQ
  消息队列，在本项目中主要用于异步更改数据库数据，提升用户体验
### WebSocket
  基于tcp的协议，本项目中用于打造在线聊天室
### JWT-token
  用于在无状态的http协议中保持登录状态，提高安全性
### 加盐加密
  用于保障用户隐私以及提高数据安全性
## 项目亮点
  1. 运用了rabbitMQ，异步更改sql数据库，不用在业务中等待数据库更改完成，加快了业务处理速度，提升了用户体验
  2. 关于申请的业务，用redis储存了需要处理的申请id，可以通过token中的信息获取用户的职位，快速获取用户需要处理的申请；在对申请进行操作时，先对redis中的数据进行更改，然后通过rabbitMQ异步更改sql数据库中的数据，加快业务处理速度
## 学习心得
  1. 运用了许多未曾用过的技术(rabbitMQ, WebSocket等),对这些技术栈有了更进一步的了解，收获了经验
  2. 丰富了一点项目经历的同时，也意识到自己经验不足，对于许多技术的运用不熟练，对项目的整体架构不太熟悉，对一些业务的具体实现方式不太清除.希望能在未来不断丰富自己的项目经历，收获更多经验
