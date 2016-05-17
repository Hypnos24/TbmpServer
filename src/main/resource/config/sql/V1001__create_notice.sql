DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `notice_id_` int(11) NOT NULL,
  `user_id_` varchar(64) DEFAULT NULL,
  `title_` varchar(30) DEFAULT NULL,
  `type_` int(11) DEFAULT '0' COMMENT '1为通知，2为公告，3为消息，4为每日一报',
  `owner_` varchar(30) DEFAULT NULL COMMENT '发布者',
  `create_time_` bigint(20) DEFAULT NULL,
  `content_` text,
  `attachment_` text COMMENT '附件',
  `status_` int(11) DEFAULT NULL,
  PRIMARY KEY (`notice_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;