# mysql数据库自动备份

### 使用方式
1. https://github.com/sixsixsix516/sun-backup/releases 下载jar文件 或自己拉下代码编译出jar
2. 将jar文件放在服务器中
3. 运行
nohup java -jar 文件名  参数列表 &

参数列表
 
|  参数   | 默认 | 是否必填| 备注 | 
| ---| ----  | ---- |----  |
| host  | localhost |  非必填| mysql服务的ip
| port  | 3306 |  非必填| mysql服务的端口
| username  | root |  非必填| mysql服务的用户名
| password  | root |  非必填| mysql服务的密码  : 如果密码包含 $&%等特殊字符, 需要整体用 "" 包起来  例如 --password="@ewwew"
| db  |  |  必填| 要备份的数据库名
| title  |  |  非必填| 备份的文件发送邮件时的标题
| sendToEmail  |  |  非必填| 备份的文件发送到哪个邮件地址


完整示例命令  nohup java -jar sun-backup.jar --port=12631 --password=123456 --db=superman --title=新版超人备份 --sendToEmail=sixsixsix@163.com   &



#### 更新日志
1.1
- 增加代码注释, 优化代码写法, 调整包结构(更清晰), 可读性更好
