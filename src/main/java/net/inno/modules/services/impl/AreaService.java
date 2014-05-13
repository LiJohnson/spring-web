package net.inno.modules.services.impl;

import java.util.List;

import net.inno.modules.dao.AreaMapper;
import net.inno.modules.pojo.Area;
import net.inno.modules.services.IAreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 细节评论业务处理实现
 * @author LYM 
 * @version 1.0
 */
@Service("areaService")
public class AreaService extends BaseService implements IAreaService { 

	/**
	 * 地区sys_area
	 */
	@Autowired
	private AreaMapper areaMapper;

	public long addArea(Area area){
		if(area == null){
			return 0;
		}
		this.areaMapper.insert(area);
		return area.getAreaId();
	}

	public int updateArea(Area area) {
		if(area == null){
			return 0;
		}
		return this.areaMapper.update(area);
	}

	public int deleteArea(long areaId) {
		try{
			return this.areaMapper.delete(areaId);
		}catch (Exception e) {
			return 0;
		}
	}
	
	public Area getAreaById(int areaId){
		if(areaId < 0){
			return null;
		}
		return this.areaMapper.get(areaId);
	}
	
	public List<Area> getAreaByParentId(int parentId){
		if(parentId < 0){
			return null;
		}
		return this.areaMapper.getAreaByParentId(parentId);
	}
	
	public Area[] getArea(int areaId){
		Area[] array = {null, null, null};
		Area prov = null, city = null, town = null;
		
		town = this.getAreaById(areaId);
		if(town != null){
			city = this.getAreaById(town.getParentId());
		}		
		if(city != null){
			prov = this.getAreaById(city.getParentId());
		}
		if( prov == null && city != null){
			prov = city;
			city = town;
			town = null;
		}
		if( city == null ){
			prov = town;
			city = null;
			town = null;
		}
		array[0] = prov;
		array[1] = city;
		array[2] = town;

		return array;
	}
	
	public String getAreaName(int areaId){
		Area[] array = this.getArea(areaId);
		StringBuffer name = new StringBuffer();
		for(Area area : array){
			if(area != null){
				name.append(area.getName()).append(" ");
			}
		}
		return name.toString().trim();
	}
	
	public String getAreaName(long areaId){
		return this.getAreaName((int)areaId);
	}
	
}