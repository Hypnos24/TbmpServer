DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `project_id_` int(11) NOT NULL,
  `project_name_` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `process_` int(11) DEFAULT NULL COMMENT '进度',
  `device_` int(11) DEFAULT NULL COMMENT '设备状态',
  `warning_` int(11) DEFAULT NULL COMMENT '预警',
  `risk_` int(11) DEFAULT NULL COMMENT '风险',
  `all_circle_` int(11) DEFAULT NULL,
  `today_circle_` int(11) DEFAULT NULL,
  `warning_info_` text CHARACTER SET utf8,
  PRIMARY KEY (`project_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;