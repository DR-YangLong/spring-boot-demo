# kotlin实现的spring boot示例
选型：     
* druid数据源，mysql数据库
* mybatis rom框架，mybatis-plus为通用mapper
* log4j，logback日志框架
* fastjson，jackson框架
* spring boot框架
* autoloadcache缓存框架

## 初始化
1. mysql的test数据库执行init.sql，创建表。安装和运行redis。
2. 已配置mysql的账号密码均为root，如果要自定义账号密码，修改并运行util包下*DruidPassword.kt*获取到密文密码和公钥后，将其分别填入*application-dev.yml*中 **password** 处和 **connectionProperties的config.decrypt.key=** 后。
druid监控配置application.yml中：
```yml
druid:
  stat:
   userName: 
   password: 
   resetEnable: 
   allow: 
   deny: 
```
下配置，如果需要其他配置，可到DruidStatConfig中自行扩展支持属性。

3. 修改*application.yml*中redis链接地址和密码为你的redis地址和密码。

4. 运行*Application.kt*中main方法。

## 测试
1. druid监控：访问<http://127.0.0.1:8080/druid>，输入配置的用户名密码登录查看。
2. web测试：查看UserController对应mapping和参数，访问测试。
3. redis测试：查看RedisController对应mapping和参数，访问测试。
