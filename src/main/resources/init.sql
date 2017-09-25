CREATE TABLE `user_base` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userMobile` varchar(11) NOT NULL COMMENT '用户手机号，登录凭证',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `userPwd` varchar(128) DEFAULT NULL COMMENT '用户密码',
  `appToken` varchar(128) DEFAULT NULL COMMENT 'token',
  `userStatus` char(1) DEFAULT '0' COMMENT '用户状态，0正常，1禁用，2登录锁定',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `index_uq_mobile` (`userMobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础表'