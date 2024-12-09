# 注意事项

* 每个版本的对接方式可能有较大差异，对比各个版本的readme.md文件确认。

# 实现方案

# 对接流程

## 自行实现逻辑

### 前端获取秘钥的方式：

实现Controller,BizService层代码。对接EncryptConfigRecordService服务，前端按顺序调用：

* fetchServerRsaPublicKey
* fetchServerAesKey
* queryServerAesKeyByAesKeyId

### web层加解密拦截器：Filter

* 实现Filter拦截器，对接WebEncryptFilterService.process方法
* 前后端约定如何传递aesKeyId
* Controller增加WebEncryptConfig注解
* Controller中所有使用了RequestMapping

### 配置

* 生成数据库表：执行encrypt_config_record.sql
* 配置log4j.xml，按包路径配置日志

# Release Note

## 1.1
初始化版本