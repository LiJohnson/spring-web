package net.inno.modules.services;

import java.util.List;

import net.inno.modules.pojo.Area;

public interface IAreaService {

	/**
	 * 增加
	 * @param area 地区
	 * @return 地区ID
	 */
    long addArea(Area area);

    /**
     * 更新
     * @param Area 待更新的地区
     * @return 影响数据库记录的条数
     */
    int updateArea(Area area);

    /**
     * 删除
     * @param areaId 待删除的地区
     * @return 影响数据库记录的条数
     */
    int deleteArea(long areaId);
    
    /**
     * 通过主键获取一个实例
     * @param areaId 地区ID
     * @return 地区
     */
    Area getAreaById(int areaId);
    
    /**
	 * 通过父节点来找下一级的全部节点
	 * @param parentId 父节点
	 * @return 下一级的全部节点
	 */
	List<Area> getAreaByParentId(int parentId);
	
	/**
	 * 获取地区
	 * 三个长度 分别存放一级、二级、三级（null）
	 * @param areaId 最后一级的地区ID
	 * @return 该级及往上所有地区
	 */
	Area[] getArea(int areaId);
	
	/**
	 * 获取地区名称 分隔符为空格
	 * 类如：广东省 佛山市 禅城区
	 * @param areaId 最后一级的地区ID
	 * @return 该级及往上所有地区
	 */
	String getAreaName(int areaId);
	
	/**
	 * 获取地区名称 分隔符为空格
	 * 类如：广东省 佛山市 禅城区
	 * @param areaId 最后一级的地区ID
	 * @return 该级及往上所有地区
	 */
	String getAreaName(long areaId);

}