INSERT INTO `sys_user` (`id`, `createTime`, `createUser`, `state`, `updateTime`, `updateUser`, `nickname`, `password`, `truename`, `username`) VALUES ('1', '2018-08-01 18:18:58.000000', 'admin', '1', NULL, NULL, '管理员', '123456', '管理员', 'admin');

INSERT INTO `sys_role` (`id`, `createTime`, `createUser`, `state`, `updateTime`, `updateUser`, `displayOrder`, `roleCode`, `roleName`) VALUES ('1', '2018-08-01 18:15:54.000000', 'admin', '1', NULL, NULL, '0', 'ROLE_ADMIN', '管理员');
INSERT INTO `sys_role` (`id`, `createTime`, `createUser`, `state`, `updateTime`, `updateUser`, `displayOrder`, `roleCode`, `roleName`) VALUES ('2', '2018-08-01 18:15:54.000000', 'admin', '1', NULL, NULL, '0', 'ROLE_USER', '用户');

INSERT INTO `sys_user_role` (`id`, `createTime`, `createUser`, `state`, `updateTime`, `updateUser`, `roleCode`, `username`) VALUES ('1', '2018-08-01 18:19:44.000000', 'admin', '1', NULL, NULL, 'ROLE_USER', 'admin');
INSERT INTO `sys_user_role` (`id`, `createTime`, `createUser`, `state`, `updateTime`, `updateUser`, `roleCode`, `username`) VALUES ('2', '2018-08-01 18:20:11.000000', 'admin', '1', NULL, NULL, 'ROLE_ADMIN', 'admin');
