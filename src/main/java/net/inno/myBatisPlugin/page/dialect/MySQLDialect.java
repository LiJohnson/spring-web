package net.inno.myBatisPlugin.page.dialect;

import net.inno.commons.util.DBUtil;

/**
 * Created by IntelliJ IDEA.
 * User: hqq
 * Date: 12-4-16
 * Time: 下午9:35
 * To change this template use File | Settings | File Templates.
 */
public class MySQLDialect implements IDialect{
    public String getLimitString(String sql, int offset, int limit) {
        return DBUtil.getMySqlLimitString(sql,offset,limit);
    }
}
