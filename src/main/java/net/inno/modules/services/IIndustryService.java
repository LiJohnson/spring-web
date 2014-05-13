package net.inno.modules.services;

import java.util.List;

import net.inno.modules.pojo.Industry;

/**
 * 制造联行业
 * @author LYM
 */
public interface IIndustryService {
	
	/**
	 * 无ID：获取制造联行业（从父到子）
	 * 有ID：获取制造联行业（从子到父）
	 * @return
	 */
	List<Industry> loadIndustry(Long industryId);
	
	/**
	 * 添加或者修改制造联行业
	 * 1、主键 <= 0 为添加
	 * 2、parentId <= 0 为一级分类
	 * @param industry
	 * @return
	 */
	int handleIndustry(Industry industry);
	
	/**
	 * 删除制造联行业
	 * @param industryId
	 * @return
	 */
	int deleteIndustry(long industryId);
	
}
