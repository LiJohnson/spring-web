package net.inno.modules.dao;

import java.util.List;

import net.inno.modules.dao.BaseMapper;
import net.inno.modules.pojo.Industry;
 
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryMapper extends BaseMapper<Industry> {

	/**
	 * 通过父ID获取子行业
	 * @param industryId
	 * @return
	 */
	List<Industry> getByParentId(long industryId);
	
	/**
	 * 删除子行业
	 * @param industryId
	 * @return
	 */
	int deleteByParentId(long industryId);
	
	/**
	 * 是否存在该行业分类
	 * @param name
	 * @return
	 */
	boolean isExist(String name);
	
	/**
	 * 更新行业的名字和排序
	 * @param industry
	 * @return
	 */
	int edit(Industry industry);
	
}
