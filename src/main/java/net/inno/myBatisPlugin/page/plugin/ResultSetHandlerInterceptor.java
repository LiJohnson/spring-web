package net.inno.myBatisPlugin.page.plugin;

import java.sql.Statement;
import java.util.Properties;

import net.inno.myBatisPlugin.page.util.ReflectUtil;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

/**
 * 结果集插件，防止分页情况下，mybatis再次做假分页
 *
 * @version 1.0
 * @since 2011-12-6 下午08:28:12
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class ResultSetHandlerInterceptor implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
        Object resultSet = invocation.getTarget();

        RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(resultSet,"rowBounds");

        // 非mybatis默认分页信息，就设置为mybatis默认分页，这样mybatis就不会做list的假分页了
        if (rowBounds != RowBounds.DEFAULT) {
            ReflectUtil.setFieldValue(resultSet, "rowBounds", RowBounds.DEFAULT);
        }

        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
