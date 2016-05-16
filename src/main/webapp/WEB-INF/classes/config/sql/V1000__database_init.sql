/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : localhost
 Source Database       : boi

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 02/25/2016 17:34:18 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_dictionary_param`
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary_param`;
CREATE TABLE `t_dictionary_param` (
  `index_` varchar(50) NOT NULL,
  `value_` varchar(100) DEFAULT NULL,
  `module_` int(11) NOT NULL,
  `create_time_` datetime DEFAULT NULL,
  `modify_time_` datetime DEFAULT NULL,
  `sort_` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_`,`module_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_dictionary_param`
-- ----------------------------
BEGIN;
INSERT INTO `t_dictionary_param` VALUES ('yidong', '中国移动', '1', '2015-04-13 10:36:52', '2016-02-25 17:17:37', '1'), ('liantong', '中国联通', '1', '2016-02-25 17:12:12', '2016-02-25 17:17:36', '2'), ('dianxin', '中国电信', '1', '2016-02-25 17:12:32', '2016-02-25 17:17:35', '3');
COMMIT;

-- ----------------------------
--  Table structure for `t_fix_flag`
-- ----------------------------
DROP TABLE IF EXISTS `t_fix_flag`;
CREATE TABLE `t_fix_flag` (
  `fix_id_` varchar(20) NOT NULL,
  `fix_time_` datetime DEFAULT NULL,
  `fix_desc_` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fix_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_fix_flag`
-- ----------------------------
BEGIN;
INSERT INTO `t_fix_flag` VALUES ('20160223', '2016-02-25 16:35:55', '补丁说明');
COMMIT;

-- ----------------------------
--  Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menu_id_` varchar(64) NOT NULL,
  `parent_id_` varchar(64) DEFAULT NULL,
  `level_` int(11) DEFAULT NULL,
  `text_` varchar(50) DEFAULT NULL,
  `href_url_` varchar(100) DEFAULT NULL,
  `target_` varchar(20) DEFAULT NULL,
  `sort_` varchar(10) DEFAULT NULL,
  `icon_` varchar(20) DEFAULT NULL,
  `title_` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`menu_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_menu`
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` VALUES ('100000', null, '0', '权限管理', null, null, '100000', 'leftico01.png', null), ('100001', '100000', '1', '用户管理', 'web/admin/user/index', 'rightFrame', '100001', null, null), ('100002', '100000', '1', '角色管理', 'web/admin/role/index', 'rightFrame', '100002', null, null), ('200000', null, '0', '套餐管理', null, null, '200000', 'leftico01.png', null), ('200001', '200000', '1', '套餐列表', 'web/admin/area/advanced_index', 'rightFrame', '200001', null, null), ('300000', null, '0', '系统管理', null, null, '300000', 'leftico01.png', null), ('300001', '300000', '1', '系统参数设置', 'web/admin/system/param/index', 'rightFrame', '300001', null, null), ('300002', '300000', '1', '数据字典管理', 'web/admin/system/dictionary/index', 'rightFrame', '300002', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `t_operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_log`;
CREATE TABLE `t_operate_log` (
  `log_id_` varchar(64) NOT NULL,
  `operate_time_` datetime DEFAULT NULL,
  `user_id_` varchar(64) DEFAULT NULL,
  `operate_content_` text,
  `log_ip_` varchar(20) DEFAULT NULL,
  `module_` int(11) DEFAULT NULL,
  PRIMARY KEY (`log_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_operate_log`
-- ----------------------------
BEGIN;
INSERT INTO `t_operate_log` VALUES ('402894845317c62d015317c67cf70001', '2016-02-25 17:33:17', '402881e846e4b3910146e4b8ce6c0004', '登录系统', '0:0:0:0:0:0:0:1', '1000');
COMMIT;

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id_` varchar(64) NOT NULL,
  `name_` varchar(50) DEFAULT NULL,
  `description_` varchar(100) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL,
  `modify_time_` datetime DEFAULT NULL,
  `user_id_` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`role_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES ('100000', '超级管理员', '可管理后台整个系统', '2014-12-10 17:36:33', '2014-12-10 17:36:36', null), ('402882374a4ba68c014a4bb1cc9d0001', '默认角色', '每个新用户都默认拥有的角色', '2014-12-15 10:05:51', '2015-03-27 15:05:56', '402881e846e4b3910146e4b8ce6c0004');
COMMIT;

-- ----------------------------
--  Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `role_id_` varchar(64) NOT NULL,
  `menu_id_` varchar(64) NOT NULL,
  PRIMARY KEY (`role_id_`,`menu_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `t_role_menu` VALUES ('100000', '100000'), ('100000', '100001'), ('100000', '100002'), ('100000', '200000'), ('100000', '200001'), ('100000', '300000'), ('100000', '300001'), ('100000', '300002'), ('402882374a4ba68c014a4bb1cc9d0001', '100000'), ('402882374a4ba68c014a4bb1cc9d0001', '100001'), ('402882374a4ba68c014a4bb1cc9d0001', '200000'), ('402882374a4ba68c014a4bb1cc9d0001', '200001'), ('402882374a4ba68c014a4bb1cc9d0001', '300000'), ('402882374a4ba68c014a4bb1cc9d0001', '300001'), ('402882374a4ba68c014a4bb1cc9d0001', '300002');
COMMIT;

-- ----------------------------
--  Table structure for `t_server_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_server_user`;
CREATE TABLE `t_server_user` (
  `user_id_` varchar(64) NOT NULL,
  `username_` varchar(50) DEFAULT NULL,
  `assistant_password_` varchar(20) DEFAULT NULL,
  `encrypted_password_` varchar(50) DEFAULT NULL,
  `name_` varchar(50) DEFAULT NULL,
  `phone_` varchar(20) DEFAULT NULL,
  `parent_id_` varchar(64) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL,
  `modify_time_` datetime DEFAULT NULL,
  `last_login_time_` datetime DEFAULT NULL,
  `status_` int(11) DEFAULT NULL,
  `last_login_ip_` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_server_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_server_user` VALUES ('402881e846e4b3910146e4b8ce6c0004', 'admin', 'MlZHVTI=', 'fee256b4bad0868ceb6a1d9122a91077', '管理员', '18559785980', null, '2014-12-10 11:11:11', '2015-05-08 10:11:57', '2016-02-25 17:33:17', '1', '0:0:0:0:0:0:0:1'), ('402894845317c367015317c487ea0003', 'xx', 'UkVMS08=', 'e51c4bfc873b53e8e141f4ebbdebd624', '薛兴', null, '402881e846e4b3910146e4b8ce6c0004', '2016-02-25 17:31:09', '2016-02-25 17:31:09', null, '1', null);
COMMIT;

-- ----------------------------
--  Table structure for `t_sub_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_sub_menu`;
CREATE TABLE `t_sub_menu` (
  `menu_id_` varchar(64) NOT NULL,
  `parent_id_` varchar(64) DEFAULT NULL,
  `level_` int(11) DEFAULT NULL,
  `href_url_` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`menu_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sub_menu`
-- ----------------------------
BEGIN;
INSERT INTO `t_sub_menu` VALUES ('00000001', null, '2', 'web/admin/welcome'), ('00000002', null, '2', 'web/admin/main'), ('00000003', null, '2', 'web/admin/edit_pass'), ('00000004', null, '2', 'web/admin/edit_info'), ('00000005', null, '2', 'web/admin/top'), ('10000101', '100001', '2', 'web/admin/user/list'), ('10000102', '100001', '2', 'web/admin/user/edit_input'), ('10000103', '100001', '2', 'web/admin/user/add_input'), ('10000104', '100001', '2', 'web/admin/user/edit'), ('10000105', '100001', '2', 'web/admin/user/add'), ('10000106', '100001', '2', 'web/admin/user/reset_pass'), ('10000201', '100002', '2', 'web/admin/role/list'), ('10000202', '100002', '2', 'web/admin/role/edit_input'), ('10000203', '100002', '2', 'web/admin/role/edit'), ('10000204', '100002', '2', 'web/admin/role/add_input'), ('10000205', '100002', '2', 'web/admin/role/add'), ('20000101', '200001', '2', 'web/admin/area/advanced_list'), ('20000102', '200001', '2', 'web/admin/area/edit_advanced'), ('20000103', '200001', '2', 'web/admin/area/add_advanced'), ('20000104', '200001', '2', 'web/admin/area/edit_advanced_input'), ('20000105', '200001', '2', 'web/admin/area/add_advanced_input'), ('20000106', '200001', '2', 'web/admin/area/clear_mac'), ('30000101', '300001', '2', 'web/admin/system/param/list'), ('30000102', '300001', '2', 'web/admin/system/param/edit'), ('30000201', '300002', '2', 'web/admin/system/dictionary/list'), ('30000202', '300002', '2', 'web/admin/system/dictionary/edit'), ('30000203', '300002', '2', 'web/admin/system/dictionary/add');
COMMIT;

-- ----------------------------
--  Table structure for `t_system_param`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_param`;
CREATE TABLE `t_system_param` (
  `param_id_` varchar(64) NOT NULL DEFAULT '',
  `name_` varchar(50) DEFAULT NULL,
  `value_` varchar(100) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL,
  `modify_time_` datetime DEFAULT NULL,
  PRIMARY KEY (`param_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_system_param`
-- ----------------------------
BEGIN;
INSERT INTO `t_system_param` VALUES ('initPassword', '新用户初始化密码', '123456', '2014-12-18 09:34:22', '2014-12-18 09:42:22'), ('timeZone', '时区配置', '8', '2015-04-15 17:29:53', '2015-04-15 17:29:55');
COMMIT;

-- ----------------------------
--  Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id_` varchar(64) NOT NULL,
  `role_id_` varchar(64) NOT NULL,
  PRIMARY KEY (`role_id_`,`user_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES ('402881e846e4b3910146e4b8ce6c0004', '100000'), ('402894845317c367015317c487ea0003', '402882374a4ba68c014a4bb1cc9d0001');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
