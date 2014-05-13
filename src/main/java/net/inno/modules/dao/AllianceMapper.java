package net.inno.modules.dao;

import java.util.List;

import net.inno.modules.dao.BaseMapper;
import net.inno.modules.pojo.Alliance;
import net.inno.myBatisPlugin.page.model.PageRowBounds;
 
import org.springframework.stereotype.Repository;

@Repository
public interface AllianceMapper extends BaseMapper<Alliance> {

	/**
	 * 获取符合条件的联盟企业
	 * 1、name 			模糊匹配
	 * 2、industryId 	行业的筛选
	 * 3、cost 			真假值判断
	 * 4、crafts 		真假值判断
	 * 5、quality 		真假值判断
	 * 6、delivery 		真假值判断
	 * 7、yield 			真假值判断
	 * 7、areaId			地区判断
	 * 7、industryId 	行业判断
	 * @param alliance
	 * @param page
	 * @return
	 */
	List<Alliance> getList(Alliance alliance, PageRowBounds page);
	
	/**
	 * 获取一个联盟企业
	 * @param login_id 用户ID
	 * @return
	 */
	Alliance getByLogin_id(String login_id);
	
}
