package net.inno.commons.web;

import java.util.HashMap;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: sdhery
 * Date: 13-9-13
 * Time: 上午10:28
 * 读取Spring 配置的资源文件里的参数类
 */

public class PropertyPlaceholderConfigExt extends PropertyPlaceholderConfigurer {
    private static HashMap<String, Object> ctxPropertiesMap;

    /**
     *
     * @param beanFactoryToProcess
     * @param props
     * @throws BeansException
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            //System.out.println(keyStr + " : "+value);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /**
     * 根据参数名获得值
     * @param name
     * @return
     */
    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }
}
