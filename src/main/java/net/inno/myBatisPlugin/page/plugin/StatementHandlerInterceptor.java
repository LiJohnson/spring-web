package net.inno.myBatisPlugin.page.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import net.inno.myBatisPlugin.page.dialect.IDialect;
import net.inno.myBatisPlugin.page.model.PageRowBounds;
import net.inno.myBatisPlugin.page.util.ReflectUtil;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

/**
 * Created by IntelliJ IDEA.
 * User: hqq
 * Date: 12-4-16
 * Time: 下午9:47
 * To change this template use File | Settings | File Templates.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class StatementHandlerInterceptor implements Interceptor {
    private static String DIALECT = "net.inno.myBatisPlugin.page.dialect.MySQLDialect";
   // private static final Log LOG = LogFactory.getLog(StatementHandlerInterceptor.class);

    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
        PreparedStatementHandler handler = (PreparedStatementHandler) ReflectUtil.getFieldValue(statement, "delegate");
        try {
            final Object rowBounds = ReflectUtil.getFieldValue(handler, "rowBounds");
            if ( rowBounds != null && rowBounds instanceof PageRowBounds ) {
            	BoundSql boundSql = statement.getBoundSql();            	
            	final PageRowBounds page = (PageRowBounds) rowBounds;
            	
            	if( page.isCountPage() ){
	            	MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getFieldValue(handler, "mappedStatement");
	                Connection connection = (Connection) invocation.getArgs()[0];
	                page.setTotalRecord( this.getTotalRecord( mappedStatement, connection ,  boundSql.getParameterObject() ) );
	                if( page.getStart() >= page.getTotalRecord() ){
	                	page.setCurrentPage(page.getTotalPage());
	                }
            	}
            	
                String sql = boundSql.getSql();
                IDialect dialect = (IDialect) Class.forName(DIALECT).newInstance();
                sql = dialect.getLimitString(sql, page.getStart(), page.getPageSize());
                ReflectUtil.setFieldValue(boundSql, "sql", sql);
            }
        } catch (Exception e) {

        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        String dialect = (String) properties.get("dialect");

        if (dialect != null) {
            if ("mysql".equals(dialect)) {
                DIALECT = "net.inno.myBatisPlugin.page.dialect.MySQLDialect";
            } else if (properties.get("dialect") != null) {
                StatementHandlerInterceptor.DIALECT = (String) properties.get("dialect");
            }
        }
    }
    
    /************************************************************************************************/
    private String getCountSql( String sql ){    	
    	//测试SQL语句： String sql = "SELECT \n fromuserloginId  FrOM prd_product \nWHERE productId IN (SELECT productId FROM prd_details WHERE detailsId IN (SELECT detailsId FROM prd_detail_comments WHERE userId = #{userId})) ORDER BY beginTime DESC ";
    	sql = sql.replaceFirst("\\b((?i)FROM)\\b", "--");
		sql = sql.replaceFirst("^[\\s\\S]+\\-\\-", "SELECT COUNT(*) FROM ");
		//截掉LIMIT语句
		sql = sql.replaceFirst("\\b((?i)LIMIT)\\b(.|\\n)+", "");
		
		//sql = sql.replaceFirst("[\\t\\n\\s\\r]+((?i)ORDER)[\\t\\r\\n\\s].+$", "");
		return sql;
	}
	
    /**
     * 计算总记录数
     * @param mappedStatement
     * @param connection
     * @param args
     * @return
     */
	private int getTotalRecord( MappedStatement mappedStatement , Connection connection , Object args) {
		BoundSql boundSql = mappedStatement.getBoundSql(args);
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql( mappedStatement.getConfiguration(), countSql, parameterMappings, args);
		ParameterHandler parameterHandler = new DefaultParameterHandler( mappedStatement, args, countBoundSql);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)rs.close();
				if (pstmt != null)pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
