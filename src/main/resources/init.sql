CREATE TABLE `user_base` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userMobile` varchar(11) NOT NULL COMMENT '用户手机号，登录凭证',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `userPwd` varchar(128) DEFAULT NULL COMMENT '用户密码',
  `appToken` varchar(128) DEFAULT NULL COMMENT 'token',
  `userStatus` char(1) DEFAULT '0' COMMENT '用户状态，0正常，1禁用，2登录锁定',
  `modifyDate` datetime DEFAULT NULL COMMENT '更新时间',
  `lockVersion` bigint(20) DEFAULT '1' COMMENT '乐观锁版本',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `index_uq_mobile` (`userMobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础表';

CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `uri` varchar(128) DEFAULT NULL COMMENT '资源标识',
  `name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `enable` char(1) DEFAULT '0' COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `identify` varchar(16) NOT NULL COMMENT '字符串标识',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注说明',
  `enable` char(1) DEFAULT '0' COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `identify` varchar(16) NOT NULL COMMENT '角色字符串标识',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注说明',
  `enable` char(1) DEFAULT '0' COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `perms_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `permId` bigint(20) DEFAULT NULL COMMENT '权限id',
  `resId` bigint(20) DEFAULT NULL COMMENT '资源id',
  `enable` char(1) DEFAULT '0' COMMENT '是否启用',
  PRIMARY KEY (`id`),
  KEY `fk_perm_res_perm` (`permId`),
  KEY `fk_perm_res_res` (`resId`),
  CONSTRAINT `fk_perm_res_perm` FOREIGN KEY (`permId`) REFERENCES `perms` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_perm_res_res` FOREIGN KEY (`resId`) REFERENCES `resources` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `roles_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色id',
  `resId` bigint(20) DEFAULT NULL COMMENT '资源id',
  `enable` char(1) DEFAULT '0' COMMENT '是否启用',
  PRIMARY KEY (`id`),
  KEY `fk_role_res_role` (`roleId`),
  KEY `fk_role_res_res` (`resId`),
  CONSTRAINT `fk_role_res_res` FOREIGN KEY (`resId`) REFERENCES `resources` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_role_res_role` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `permId` bigint(20) DEFAULT NULL COMMENT '权限id',
  `enable` char(1) DEFAULT '0' COMMENT '是否启用',
  PRIMARY KEY (`id`),
  KEY `fk_user_perms_user` (`userId`),
  KEY `fk_user_perms_perm` (`permId`),
  CONSTRAINT `fk_user_perms_perm` FOREIGN KEY (`permId`) REFERENCES `perms` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_user_perms_user` FOREIGN KEY (`userId`) REFERENCES `user_base` (`userId`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色id',
  `enable` char(1) DEFAULT '0' COMMENT '是否启用',
  PRIMARY KEY (`id`),
  KEY `fk_user_roles_user` (`userId`),
  KEY `fk_user_roles_role` (`roleId`),
  CONSTRAINT `fk_user_roles_role` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_user_roles_user` FOREIGN KEY (`userId`) REFERENCES `user_base` (`userId`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
