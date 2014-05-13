package net.inno.modules.services.impl;

import org.apache.commons.lang.StringUtils;

public class BaseService {
	
	protected static final int ADD_ERROR = -1;
	protected static final int DELETE_ERROR = -2;
	protected static final int UPDATE_ERROR = -3;
	
	
	/**
	 * 校验一个实例的Id是否有效
	 * @param id
	 * @return
	 */
	protected boolean checkPojoId( long id ){
		return id > 0;
	}
	
	protected boolean isString( String str ) {
		return !StringUtils.isBlank(str);
	}
}
