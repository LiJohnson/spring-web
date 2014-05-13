--
-- 表的结构 `sv_alliance`
--

CREATE TABLE IF NOT EXISTS `sv_alliance` (
  `allianceId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` char(32) DEFAULT NULL COMMENT '用户ID，如果是root填写的话则为null',
  `industryId` bigint(20) NOT NULL COMMENT '所属行业',
  `areaId` bigint(20) NOT NULL COMMENT '省市区ID',
  `stree` varchar(50) DEFAULT NULL COMMENT '街道门牌号',
  `name` varchar(50) NOT NULL COMMENT '企业名称',
  `website` varchar(50) DEFAULT NULL COMMENT '官方网站',
  `scale` varchar(50) DEFAULT NULL COMMENT '企业规模',
  `contact` text COMMENT '联系信息，JSON',
  `mainProduct` varchar(50) DEFAULT NULL COMMENT '主营产品',
  `cost` int(11) NOT NULL DEFAULT '0' COMMENT '成本优势,N-0,Y-1',
  `crafts` int(11) NOT NULL DEFAULT '0' COMMENT '工艺优势,N-0,Y-1',
  `quality` int(11) NOT NULL DEFAULT '0' COMMENT '质量控制,N-0,Y-1',
  `delivery` int(11) NOT NULL DEFAULT '0' COMMENT '交期,N-0,Y-1',
  `yield` int(11) NOT NULL DEFAULT '0' COMMENT '产量,N-0,Y-1',
  `description` varchar(255) DEFAULT NULL COMMENT '对企业竞争力/优势的描述',
  PRIMARY KEY (`allianceId`),
  UNIQUE KEY `allianceLogin` (`login_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='制造联信息收集' AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- 表的结构 `sv_industry`
--

CREATE TABLE IF NOT EXISTS `sv_industry` (
  `industryId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parentId` bigint(20) NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL COMMENT '行业名称',
  `pos` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`industryId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='制造联行业' AUTO_INCREMENT=16 ;

-- --------------------------------------------------------

--
-- 表的结构 `sv_question`
--

CREATE TABLE IF NOT EXISTS `sv_question` (
  `questionId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parentId` bigint(11) NOT NULL DEFAULT '0' COMMENT 'parentId',
  `title` tinytext NOT NULL COMMENT '问题',
  `type` varchar(20) DEFAULT NULL COMMENT '问题类型',
  `score` int(11) NOT NULL COMMENT '分数',
  `answer` text COMMENT '答案',
  `other` text COMMENT '其它信息',
  `pos` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`questionId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='问题表' AUTO_INCREMENT=271 ;

-- --------------------------------------------------------

--
-- 表的结构 `sv_survey`
--

CREATE TABLE IF NOT EXISTS `sv_survey` (
  `surveyId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` varchar(32) NOT NULL COMMENT '用户id（智慧岛的）',
  `time` datetime NOT NULL COMMENT '测试时间',
  `score` int(11) NOT NULL COMMENT '分数',
  `result` text NOT NULL COMMENT '结果',
  PRIMARY KEY (`surveyId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='测试记录' AUTO_INCREMENT=13 ;
