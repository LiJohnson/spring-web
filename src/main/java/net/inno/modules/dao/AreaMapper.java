package net.inno.modules.dao;

import java.util.List;

import net.inno.modules.dao.BaseMapper;
import net.inno.modules.pojo.Area;
 
import org.springframework.stereotype.Repository;

@Repository
public interface AreaMapper extends BaseMapper<Area> {

	/**
	 * 通过父节点来找下一级的全部节点
	 * @param parentId 父节点
	 * @return 下一级的全部节点
	 */
	public List<Area> getAreaByParentId(int parentId);
	
	
}
