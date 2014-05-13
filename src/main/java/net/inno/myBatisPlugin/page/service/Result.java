package net.inno.myBatisPlugin.page.service;

/**
 * Created by IntelliJ IDEA.
 * User: hqq
 * Date: 12-4-16
 * Time: 下午9:24
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务层返回结果
 *
 * @author liyixing liyixing1@yahoo.com.cn
 * @version 1.0
 * @since 2011-11-9 下午01:56:19
 */
public class Result<T> {
    public static final String ERROR_MESSAGE_KEY = "ERROR_MESSAGE_KEY";
	public static final String SUCCESS_MESSAGE_KEY = "SUCCESS_MESSAGE_KEY";
	public static final String SUCCESS_MESSAGE_CODE = "action.do.success";
	/**
	 * 是否成功
	 */
	private boolean isSuccess = false;
	/**
	 * 是否有错误消息
	 */
	private boolean hasError = false;
	/**
	 * 字段错误
	 */
	private Map<String, String> fieldErrors = new HashMap<String, String>();
	/**
	 * 应用错误
	 */
	private String serviceError;
	/**
	 * 返回的数据
	 */
	private T data;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean hasError() {
		if (hasError) {
			return hasError;
		}

		if (fieldErrors.size() > 0) {
			hasError = true;

			return hasError;
		}

		if (StringUtils.isNotEmpty(serviceError)) {
			hasError = true;

			return hasError;
		}

		return hasError;
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public String getServiceError() {
		return serviceError;
	}

	public void setServiceError(String serviceError) {
		this.serviceError = serviceError;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 自动组装bindingresult
	 */
	public void rejectValues(BindingResult bindingResult) {
		for (String filed : fieldErrors.keySet()) {
			String code = fieldErrors.get(filed);

			bindingResult.rejectValue(filed, code, code);
		}
	}

	/**
	 * 将服务错误消息放到modelMap
	 */
	public void rejectMessage(ModelMap modelMap) {
		if (StringUtils.isNotEmpty(serviceError)) {
			modelMap.put(ERROR_MESSAGE_KEY, serviceError);
		}
	}
}
