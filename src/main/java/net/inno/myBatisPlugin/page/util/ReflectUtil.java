package net.inno.myBatisPlugin.page.util;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
/**
 * 反射工具类
 *
 * @author liyixing
 * @version 1.0
 * @created 2011-6-16 下午03:29:09
 */
public class ReflectUtil {

	/**
	 *
	 * 描述 :属性的get或者set操作
	 *
	 * @param obj
	 * @param fieldName
	 * @param fieldVal
	 * @param type
	 * @return
	 * @author liyixing 2011-6-16
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static Object operate(Object obj, String fieldName,
			Object fieldVal, String type) throws Exception {
		// 获得对象类型
		Class<? extends Object> classType = obj.getClass();
		String fieldFirstChar = fieldName.toUpperCase().substring(0, 1);
		String fieldOther = fieldName.substring(1);
		String methodName = type + fieldFirstChar + fieldOther;
		Object ret;

		if ("set".equals(type)) {
			ret = classType.getMethod(methodName, fieldVal.getClass()).invoke(
					obj, fieldVal);
		} else {
			ret = classType.getMethod(methodName).invoke(obj);
		}

		return ret;
	}

	/**
	 * 描述 :调用get，来获取值
	 *
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 * @author liyixing 2011-6-16
	 */
	public static Object getVal(Object obj, String fieldName) throws Exception {
		return operate(obj, fieldName, null, "get");
	}

	/**
	 *
	 * 描述 : 调用set来设置值
	 *
	 * @param obj
	 * @param fieldName
	 * @param fieldVal
	 * @throws Exception
	 * @author liyixing 2011-6-16
	 */
	public static void setVal(Object obj, String fieldName, Object fieldVal)
			throws Exception {
		operate(obj, fieldName, fieldVal, "set");
	}

	/**
	 * 描述 : 获取指定的方法
	 *
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @author liyixing 2011-6-16
	 */
	private static Method getDeclaredMethod(Object object, String methodName,
			Class<?>[] parameterTypes) throws NoSuchMethodException {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
			}
		}

		throw new NoSuchMethodException("不存在的方法：" + methodName);
	}

	/**
	 *
	 * 描述:根据方法名获取第一个方法
	 *
	 * @param object
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 * @author liyixing 2011-12-7 下午05:59:03
	 */
	public static Method getDeclaredMethods(Object object, String methodName)
			throws NoSuchMethodException {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			Method[] methods = superClass.getDeclaredMethods();

			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					method.setAccessible(true);

					return method;
				}
			}
		}

		throw new NoSuchMethodException("不存在的方法：" + methodName);
	}

	/**
	 *
	 * 描述:根据方法名，方法参数数量获取第一个方法
	 *
	 * @param object
	 * @param methodName
	 * @param argLength
	 * @return
	 * @throws NoSuchMethodException
	 * @author liyixing 2011-12-7 下午06:00:05
	 */
	public static Method getDeclaredMethods(Object object, String methodName,
			int argLength) throws NoSuchMethodException {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			Method[] methods = superClass.getDeclaredMethods();

			for (Method method : methods) {
				if (method.getName().equals(methodName)
						&& method.getParameterTypes().length == argLength) {
					method.setAccessible(true);

					return method;
				}
			}
		}

		throw new NoSuchMethodException("不存在的方法：" + methodName);
	}

	private static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 描述 :查找字段
	 *
	 * @param object
	 * @param filedName
	 * @return
	 * @author liyixing 2011-6-16
	 */
	private static Field getDeclaredField(Object object, String filedName)
			throws NoSuchFieldException {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
			}
		}

		throw new NoSuchFieldException("不存在的字段");
	}

	/**
	 *
	 * 描述 :调用方法
	 *
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 * @throws InvocationTargetException
	 * @author liyixing 2011-6-16
	 * @throws NoSuchMethodException
	 */
	public static Object invokeMethod(Object object, String methodName,
			Class<?>[] parameterTypes, Object[] parameters)
			throws InvocationTargetException, NoSuchMethodException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);

		if (method == null) {
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {

		}

		return null;
	}

	/**
	 *
	 * 描述 :设置字段值
	 *
	 * @param object
	 * @param fieldName
	 * @param value
	 * @author liyixing 2011-6-16
	 * @throws NoSuchFieldException
	 */
	public static void setFieldValue(Object object, String fieldName,
			Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * 描述 :获取字段值
	 *
	 * @param object
	 * @param fieldName
	 * @return
	 * @author liyixing 2011-6-16
	 * @throws NoSuchFieldException
	 */
	public static Object getFieldValue(Object object, String fieldName)
			throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return result;
	}
}
