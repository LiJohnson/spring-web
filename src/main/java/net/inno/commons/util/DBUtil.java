package net.inno.commons.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-29
 * Time: 上午10:29
 * 数据库和sql工具类
 */
public class DBUtil {
    static HashMap<?, ?> Ids = null;

    /**
     * 关闭数据库操作对象
     *
     * @param conn  Connection
     * @param pstmt PreparedStatement
     * @param rs    ResultSet
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {

        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {

        }
        try {
            if (conn != null) {
                if (conn.isClosed() == false) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 取得一个sql查询的分页sql字符串
     *
     * @param sql    String 要分页的sql字符串
     * @param begin  int 要取得数据的开始记录
     * @param number int 要去的记录的数量
     * @return String
     */
    public static String getLimitString(String sql, int begin, int number) {
        if (begin == -1) {
            begin = 0;
        }
        if (number == -1) {
            number = 2000000000;
        }
        return getMySqlLimitString(sql, begin, number);
    }

    public static String getMySqlLimitString(String sql, int begin, int number) {
        StringBuffer pagingSelect = new StringBuffer(100);
        pagingSelect.append(sql);
        if (begin != -1 && number != -1) {
            pagingSelect.append(" limit " + begin + ", " + number);
        }
        return pagingSelect.toString();
    }
}
