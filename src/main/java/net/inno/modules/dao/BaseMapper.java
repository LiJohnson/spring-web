package net.inno.modules.dao;


import java.util.List;
import java.util.Map;

import net.inno.modules.bean.ListCondition;
import net.inno.myBatisPlugin.page.model.PageRowBounds;

/**
 * lcs
 * 实现Dao一般的公共方法， 如：增、查、删、改等操作 本接口采用泛型<T>实现
 * 2012-08-04 16:59:22
 */

public interface BaseMapper<T> {

	/**
	 * 保存实例
	 * 
	 * @param t
	 */
	public int insert(T t);


	/**
	 * 更新实例
	 * 
	 * @param t
	 */
	public int update(T t);

	/**
	 * 删除实例
	 * @param id
	 */
	public int delete(long id);

	/**
	 * 根据主键加载实例
	 * 
	 * @param id
	 * @return t 加载的实例
	 */
	public T get(long id);
	
	/**
	 * 根据主键加载实例
	 * @param id
	 * @return T 加载的实例
	 */
	public T getById(long id);

	/**
	 * 查找实例
	 * @param t
	 * @return list
	 */
	public List<T> find(T t);
	
	/**
	 * 查找实例
	 * @param t
	 * @param bounds
	 * @return
	 */
	public List<T> find(T t, PageRowBounds page);
	
	/**
	 * 查找实例
	 * @param condition
	 * @return
	 */
	public List<T> search( ListCondition condition);
	
	/**
	 * 查找实例,可以分页
	 * @param condition
	 * @param bounds
	 * @return
	 */
	public List<T> search( ListCondition condition , PageRowBounds page );
	
	/**
	 * 根据条件加载相符的实例的记录总数
	 * 
	 * @param condition
	 * @return int
	 */	
	public int count(ListCondition condition);
	
	/**
	 * 根据条件加载相符的实例的记录总数
	 * @param filters
	 * @return
	 */
	public long count(Map<String, Object> filters);
	
	/**
	 * 分页查找
	 * @param filters 分页查找所需要的参数集合
	 * @return 
	 */
	public List<T> findPage(Map<String, Object> filters);


	/**
	 * 加载所有实例
	 */
	public List<T> findAll();
}
