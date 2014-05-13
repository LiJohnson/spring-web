package net.inno.modules.services;

import java.util.List;

import net.inno.modules.pojo.Alliance;
import net.inno.myBatisPlugin.page.model.PageRowBounds;

/**
 * 制造联
 * @author LYM
 */
public interface IAllianceService {
	
	/**
	 * 添加或者修改联盟企业
	 * 1、allianceId <= 0 添加
	 * 2、allianceId > 0 修改
	 * @param alliance
	 * @return
	 */
	int handleAlliance(Alliance alliance);
	
	/**
	 * 删除联盟企业
	 * @param allianceId
	 * @return
	 */
	int deleteAlliance(long allianceId);
	
	/**
	 * 获取一个联盟企业
	 * @param allianceId
	 * @return
	 */
	Alliance getById(long allianceId);
	
	/**
	 * 获取一个联盟企业
	 * @param login_id 用户ID
	 * @return
	 */
	Alliance getByLogin_id(String login_id);
	
	/**
	 * 获取符合条件的联盟企业
	 * 1、name 			模糊匹配
	 * 2、industryId 	行业的筛选
	 * 3、cost 			真假值判断
	 * 4、crafts 		真假值判断
	 * 5、quality 		真假值判断
	 * 6、delivery 		真假值判断
	 * 7、yield 			真假值判断
	 * @param alliance
	 * @param page
	 * @return
	 */
	List<Alliance> getList(Alliance alliance, PageRowBounds page);
	
}
