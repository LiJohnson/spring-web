package net.inno.myBatisPlugin.page.dialect;

/**
 * 数据库方言
 *
 * @version 1.0
 * @created 2011-6-16 下午04:06:55
 */
public interface IDialect {
    /**
     * 生成具有limit后的简单分页SQL
     *
     * @param sql
     * @param offset    开始
     * @param limit     结束
     * @return
     */
    public String getLimitString(String sql, int offset, int limit);
}

