--
-- 表的结构 `sv_question`
--

CREATE TABLE IF NOT EXISTS `sv_question` (
  `questionId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parentId` bigint(11) NOT NULL COMMENT 'parentId',
  `title` tinytext NOT NULL COMMENT '问题',
  `type` varchar(20) DEFAULT NULL COMMENT '问题类型',
  `score` int(11) NOT NULL COMMENT '分数',
  `answer` text COMMENT '答案',
  `other` text COMMENT '其它信息',
  `pos` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`questionId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='问题表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sv_test`
--

CREATE TABLE IF NOT EXISTS `sv_test` (
  `testId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` varchar(32) NOT NULL COMMENT '用户id（智慧岛的）',
  `time` datetime NOT NULL COMMENT '测试时间',
  `score` int(11) NOT NULL COMMENT '分数',
  `result` text NOT NULL COMMENT '结果',
  PRIMARY KEY (`testId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='测试记录' AUTO_INCREMENT=1 ;