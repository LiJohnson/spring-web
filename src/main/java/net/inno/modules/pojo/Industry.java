package net.inno.modules.pojo;

import java.util.List;

import net.inno.modules.pojo.BasePojo;
 
/**
 * 制造联行业
 * @date 2014-05-07 10:44:48
 */
public class Industry extends BasePojo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private long industryId;

	/**
	 * 父ID
	 */
	private long parentId;

	/**
	 * 行业名称
	 */
	private String name;

	/**
	 * 排序
	 */
	private int pos;
	
	/**
	 * 子行业
	 */
	private List<Industry> children;
 
	public Industry(){}
	
	public Industry(long industryId){
		this();
		this.industryId = industryId;
	}


	/**
	 * 主键
	 */
	public long getIndustryId(){
		return this.industryId;
	}

	/**
	 * 主键
	 */
	public void setIndustryId(long industryId){
		 this.industryId = industryId; 
	}


	/**
	 * 父ID
	 */
	public long getParentId(){
		return this.parentId;
	}

	/**
	 * 父ID
	 */
	public void setParentId(long parentId){
		 this.parentId = parentId; 
	}


	/**
	 * 行业名称
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 行业名称
	 */
	public void setName(String name){
		 this.name = name; 
	}


	/**
	 * 排序
	 */
	public int getPos(){
		return this.pos;
	}

	/**
	 * 排序
	 */
	public void setPos(int pos){
		 this.pos = pos; 
	}

	/**
	 * 子行业
	 */
	public List<Industry> getChildren() {
		return children;
	}

	/**
	 * 子行业
	 */
	public void setChildren(List<Industry> children) {
		this.children = children;
	}

	/**
	 * 制造联行业顶级ID
	 */
	public static long INDUSTRY_TOP = 0L;

 
}
