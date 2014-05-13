package net.inno.modules.pojo;

import net.inno.modules.pojo.BasePojo;
 
/**
 * 地区表
 * @date 2013-06-09 10:30:57
 */
public class Area extends BasePojo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int areaId;

	/**
	 * 父级Id
	 */
	private int parentId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * key
	 */
	private String key;
 
	public Area(){}


	/**
	 * 主键
	 */
	public int getAreaId(){
		return this.areaId;
	}

	/**
	 * 主键
	 */
	public void setAreaId(int areaId){
		 this.areaId = areaId; 
	}


	/**
	 * 父级Id
	 */
	public int getParentId(){
		return this.parentId;
	}

	/**
	 * 父级Id
	 */
	public void setParentId(int parentId){
		 this.parentId = parentId; 
	}


	/**
	 * 名称
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 名称
	 */
	public void setName(String name){
		 this.name = name; 
	}


	/**
	 * key
	 */
	public String getKey(){
		return this.key;
	}

	/**
	 * key
	 */
	public void setKey(String key){
		 this.key = key; 
	}

 
}
