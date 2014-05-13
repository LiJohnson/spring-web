package net.inno.util.CodeGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lcs
 * 2012-08-04 15:34:33
 * 代码生成工具
 */
public class MyBatis {

	/**
	 *类型映射
	 *以右边的代替左边 
	 */
	private static String[] typeMap = { 
			"java.sql.Timestamp", "java.util.Date",
			"java.lang.Integer", "java.lang.int", 
			"java.lang.Long","java.lang.long" };

	private static final int FIELD = 0 ;
	private static final int JAVA_TYPE = 1 ;
	private static final int SQL_TYPE = 2 ;
	private static final int COLUMN = 3 ;
	
	private static final String C_DRIVER ="com.mysql.jdbc.Driver"; 
	private static final String C_URL =  "jdbc:mysql://192.168.1.5:3306/survey";
	private static final String C_USER="root";
	private static final String C_PASS="123456";
	private static String[] TABLES = null;
	
	/**
	 *路径 
	 */
	private static String filePath = "\\src\\main\\java\\net\\inno\\util\\CodeGenerator\\";
	
	/**
	 * 包名
	 */
	private static String pageketName = "net.inno.modules";
	
	/**
	 * 用于操作数据库
	 */
	private static Statement statement;

	/**
	 * 获取数据库的表名
	 * @return List<String>
	 */
	private static List<String> getTableName() {
		List<String> tables = new ArrayList<String>();
		if( TABLES != null && TABLES.length > 0 ){
			return Arrays.asList(TABLES);
		}
		
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("show tables");
			while (rs.next()) {
				tables.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	
	/**
	 * 根据列名返回属性名
	 * @param columnName
	 * @return
	 */
	private static String getFieldName( String columnName ){
		return columnName;
	}
	/**
	 * 返回一个表的列名和对实体的属性名，以及对应的数据类型
	 * @param table
	 * @return
	 */
	private static List<String[]> getColumNames(String table) {
		List<String[]> colNames = new ArrayList<String[]>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("select * from " + table);
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1, count = rsmd.getColumnCount(); i <= count; i++) {
				String[] col = new String[5];
				col[COLUMN] = rsmd.getColumnName(i);
				col[FIELD] = getFieldName(col[COLUMN]);
				col[JAVA_TYPE] = rsmd.getColumnClassName(i);
				col[SQL_TYPE] = rsmd.getColumnTypeName(i);
				//col[COMMENT] = rsmd.getColumnLabel(i);
				colNames.add(col);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return colNames;
	}

	/**
	 * 根据表名返回实体的类名
	 * @param tableName
	 * @return
	 */
	private static String getClassName(String tableName) {
		String className = tableName.substring(tableName.indexOf("_")+1);
		int index = -1;
		while ((index = className.indexOf("_")) > 0) {
			String c = className.charAt(index + 1) + "";
			className = className.replaceFirst("_" + c, c.toUpperCase());
		}
		return className.substring(0, 1).toUpperCase() + className.substring(1);
	}

	/**
	 * 根据typeMap 进行类型转换
	 * @param type
	 * @return
	 */
	private static String typeCorver(String type) {
		for (int i = 0, len = typeMap.length; i < len; i += 2) {
			if (type.equals(typeMap[i]) && i < len - 1)
				return typeMap[i + 1];
		}
		return type;
	}
	
	/**
	 * 获取主键下标
	 * @param fieldData
	 * @param tableName
	 * @return
	 */
	private static int getPrimaryKey( List<String[]> fieldData , String tableName){
		//String className = getClassName(tableName);
		return 0;
	}

	/**
	 * 生成注释
	 * @param comment
	 * @param isClass
	 * @return
	 */
	private static String getCommentCode(String comment , boolean isClass ){
		if( comment == null || "".equals(comment.trim()) )return "" ;
		String t = "\t";
		String s = "";
		if( isClass ){
			t = "";
			s = " * @date " + (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"\n";
		}
		return  t + "/**\n" +
				t + " * " + comment+"\n"+
				s +
				t + " */";
	}
	/**
	 * 创建实体类文件
	 * @param filePath
	 * @param className
	 * @param fieldData
	 * @throws IOException
	 */
	private static void createPojoFile(String filePath, String className,
			List<String[]> fieldData , Map<String, String> comment ) throws IOException {
		File f = new File(filePath + className + ".java");
		if (!f.exists()) {
			f.createNewFile();
		}
		FileOutputStream stream = new FileOutputStream(f);
		StringBuffer _import = new StringBuffer();
		StringBuffer _constructor = new StringBuffer();
		StringBuffer _field = new StringBuffer();
		StringBuffer _getterAndsetter = new StringBuffer();
		
		_constructor.append("public "+className+"(){}\n");
		
		for (String[] field : fieldData) {
			field[JAVA_TYPE] = typeCorver(field[JAVA_TYPE]);
			if (!field[JAVA_TYPE].startsWith("java.lang.")) {
				String r = "import " + field[JAVA_TYPE];
				if (_import.indexOf(r) < 0) {
					_import.append(r + ";\n");
				}
			}
			field[JAVA_TYPE] = field[JAVA_TYPE].substring(field[JAVA_TYPE].lastIndexOf(".") + 1);
			_field.append("\n"+getCommentCode(comment.get(field[COLUMN]),false)+"\n");
			_field.append("\tprivate " + field[JAVA_TYPE] + " " + field[FIELD] + ";\n");

			String t = field[FIELD].substring(0, 1).toUpperCase()
					+ field[FIELD].substring(1);
			_getterAndsetter.append("\n"+getCommentCode(comment.get(field[COLUMN]),false)+"\n");
			_getterAndsetter.append("\tpublic " + field[JAVA_TYPE] + " get" + t
					+ "(){\n\t\treturn this." + field[FIELD] + ";\n\t}\n");
			_getterAndsetter.append("\n"+getCommentCode(comment.get(field[COLUMN]),false)+"\n");
			_getterAndsetter.append("\tpublic void set" + t + "(" + field[JAVA_TYPE]
					+ " " + field[FIELD] + "){\n\t\t this." + field[FIELD] + " = "
					+ field[FIELD] + "; \n\t}\n\n");
		}

		String code = pojoCode.replaceAll("#class#", className);
		code = code.replace("#constructor#", _constructor.toString());
		code = code.replace("#field#", _field.toString());
		code = code.replace("#getterAndsetter#", _getterAndsetter.toString());
		code = code.replace("#import#", _import.toString());
		code = code.replace("#comment#", getCommentCode(comment.get("table"),true));
		stream.write(code.getBytes());
		stream.flush();
		stream.close();
	}

	/**
	 * 创建dao文件
	 * @param filePath
	 * @param className
	 * @throws Exception
	 */
	private static void createDaoFile( String filePath , String className ) throws Exception{
		File f = new File(filePath + className + "Mapper.java");
		if (!f.exists()) {
			f.createNewFile();
		}
		FileOutputStream stream = new FileOutputStream(f);
		StringBuffer _import = new StringBuffer();
		StringBuffer _function = new StringBuffer();
		
		_import.append("import "+pageketName+".pojo."+className+";\n");
		
		String code = daoCode.replaceAll("#class#", className);
		code = code.replace("#function#", _function.toString());
		code = code.replace("#import#", _import.toString());
		stream.write(code.getBytes());
		stream.flush();
		stream.close();
	}
	

	/**
	 * 创建mapper文件
	 * @param filePath
	 * @param className
	 * @param tableName
	 * @param fieldData
	 * @throws IOException
	 */
	private static void createMapperFile( String filePath , String className , String tableName, List<String[]> fieldData ) throws IOException{
		File f = new File(filePath + className + "Mapper.xml");
		if (!f.exists()) {
			f.createNewFile();
		}
		int primaryKeyIndex = getPrimaryKey(fieldData, tableName);
		String[] primaryKey = fieldData.get(primaryKeyIndex);
		
		StringBuffer buf1 = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();
		String dot = "\n\t";
		for( String[] field :fieldData  ){
			if( field[FIELD] == primaryKey[FIELD] )continue;
			
			buf1.append(dot+"`" +field[COLUMN] + "`");
			buf2.append(dot+"#{"+field[FIELD]+"}");
			dot = ",\n\t";
		}
		
		FileOutputStream stream = new FileOutputStream(f);
		StringBuffer _sql = new StringBuffer();		
		_sql.append("<insert id=\"insert\" parameterType=\""+className+"\"   keyProperty=\""+primaryKey[FIELD]+"\" useGeneratedKeys=\"true\">\n");
		_sql.append("INSERT INTO " + tableName + "\n\t("+buf1.toString()+"\n\t)\nVALUES\n\t("+buf2.toString()+"\n\t)\n");
		_sql.append("</insert>\n\n");
		
		_sql.append("<delete id=\"delete\" parameterType=\"long\">\n");
		_sql.append("DELETE FROM " + tableName + " WHERE "+primaryKey[COLUMN]+" = #{"+primaryKey[FIELD]+"}\n");
		_sql.append("</delete>\n\n");
		
		_sql.append("<update id=\"update\" parameterType=\""+className+"\">\n");
		_sql.append("UPDATE " + tableName + " SET \n"); 
		
		dot = "\t";
		for( String[] field :fieldData  ){
			if(field[FIELD] == primaryKey[FIELD])
				continue;
			_sql.append(dot + "`"+field[COLUMN] + "`" + " = #{" + field[FIELD]+"}");
			dot=",\n\t";
		}
		_sql.append(" \nWHERE \n\t" +  primaryKey[COLUMN]+" = #{"+primaryKey[FIELD]+"}\n");    	
		_sql.append("</update>\n\n");
		
		_sql.append("<select id=\"get\" resultType=\""+className + "\" parameterType=\"long\">\n");
		_sql.append("\tSELECT * FROM "+tableName+" WHERE " +primaryKey[COLUMN]+" = #{"+primaryKey[FIELD]+"}\n");
		_sql.append("</select>\n\n");
		
		_sql.append("<select id=\"find\" resultType=\""+className + "\" parameterType=\""+className+"\">\n");
		_sql.append("\tSELECT * FROM "+tableName+" where 1=1 \n");
		for( String[] field :fieldData  ){
			if("int".equals(field[JAVA_TYPE]) || "long".equals(field[JAVA_TYPE])){
				_sql.append("\t<if test=\""+field[FIELD]+" != -1\">\n");
				_sql.append("\t\t AND `"+field[COLUMN]+"` = #{" + field[FIELD] +"}\n");
			}else if("String".equals(field[JAVA_TYPE]) ){
				_sql.append("\t<if test=\""+field[FIELD]+" != null\">\n");
				_sql.append("\t\t AND `"+field[COLUMN]+"` like #{" + field[FIELD] +"}\n");
			}else{
				_sql.append("\t<if test=\""+field[FIELD]+" != null\">\n");
				_sql.append("\t\t AND `"+field[COLUMN]+"` = #{" + field[FIELD] +"}\n");
			}
	        
	        _sql.append("\t</if>\n");
		}
		_sql.append("</select>\n\n");
		
			
		_sql.append("<select id=\"search\" resultType=\""+className+"\" parameterType=\"ListCondition\">\n");
		_sql.append("\tSELECT <if test=\"distinct\">distinct</if> * FROM " + tableName+"\n");
		_sql.append("\t<if test=\"_parameter != null\">\n");
		_sql.append("\t\t<include refid=\"Where_ListCondition\"/>\n");
		_sql.append("\t</if>\n");
		_sql.append("\t<if test=\"orderSql != null\">order by \\${orderSql}</if>\n");
		_sql.append("</select>\n\n");
		
		
		_sql.append("<select id=\"count\" resultType=\"java.lang.Integer\" parameterType=\"ListCondition\">\n");
		_sql.append("\tSELECT count(*) FROM "+tableName+"\n");
		_sql.append("\t<if test=\"_parameter != null\">\n");
        _sql.append("\t\t<include refid=\"Where_ListCondition\"/>\n");
        _sql.append("\t</if>\n");
		_sql.append("</select>\n\n");
		
		String code = mapperCode.replaceFirst("#sql#", _sql.toString());
		code = code.replaceFirst("#className#", className);
		stream.write(code.getBytes());
		stream.flush();
		stream.close();
	}
	
	/**
	 * 创建文件
	 * @param tableName
	 * @throws Exception
	 */
	private static void CreateFile(String tableName) throws Exception {
		List<String[]> fieldData = getColumNames(tableName);
		Map<String, String> comment = getComment(tableName);
		String className = getClassName(tableName);

		String basePath = new File(".").getCanonicalPath() + filePath;

		String pojoPath = basePath + "pojo\\";
		String daoPath = basePath + "dao\\";
		String mapperPath = basePath + "mapper\\";
		File f = new File(pojoPath);
		if (!f.exists())
			f.mkdirs();
		f = new File(daoPath);
		if (!f.exists())
			f.mkdirs();
		f = new File(mapperPath);
		if(!f.exists()){
			f.mkdirs();
		}
		if(fieldData.size() > 0){
		createPojoFile(pojoPath, className, fieldData,comment);
		createMapperFile(mapperPath, className, tableName , fieldData);
		createDaoFile(daoPath , className);
		}
	}

	/**
	 * 通过spring创建一个statement
	 * @return
	 */
	private static Statement createStatement(){
		ApplicationContext factory = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SqlSessionFactory ssf = (SqlSessionFactory) factory
				.getBean("sqlSessionFactory");
		SqlSession ss = ssf.openSession();
		Connection connection = ss.getConnection();
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过JDBC创建一个statement
	 * @return
	 */
	private static Statement createStatement2(){
		try {
			Class.forName(C_DRIVER);
			return DriverManager.getConnection(C_URL,C_USER,C_PASS).createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 主入口方法
	 * @param args
	 * @throws Exception
	 */
	public static void main1(String[] args) throws Exception {
		statement = createStatement2();
		
		List<String> tables = getTableName();
		for( String tableName : tables ){			
			CreateFile(tableName);
		}
		System.out.println("-----------------------------------" + tables.size() + " files created !");
	}

	/**
	 * 入口函数，可以通过JUnit调用
	 * @throws Exception 
	 */
	@Test 
	public void codeGen() throws Exception {
		statement = createStatement();
		
		List<String> tables = getTableName();
		for( String tableName : tables ){
			if(tableName.startsWith("t_"))continue;
			CreateFile(tableName);
		}
		System.out.println("-----------------------------------" + tables.size() + " files created !");
	}
	
	/**
	 * 获取一个表的注释
	 * @param tableName
	 * @return
	 */
	private static Map<String, String> getComment(String tableName){
		ResultSet rt = null ;
		Map<String , String> comment = new HashedMap();
		try {
			rt = statement.executeQuery("show create table " + tableName);
		} catch (SQLException e) {}
		
		try {
			while (rt != null && rt.next()) {
				String[] lines = rt.getString(2).split("\n");
				//System.out.println(rt.getString(2) );
				for( String line : lines ){
					line = line.trim();
					if( !line.startsWith("`") )continue;
					int index = line.indexOf("`",2);
					int index2 = line.indexOf("COMMENT '");
					//System.err.println(line.substring(1, index));
					//System.err.println(line.substring(index2 + "COMMENT '".length(), line.length()-2));
					if( index2 > 0){
						comment.put(line.substring(1, index), line.substring(index2 + "COMMENT '".length(), line.length()-2));
					}
				}
				int index = lines[lines.length-1].indexOf("COMMENT='");
				if(index > 0){
					comment.put("table", lines[lines.length-1].substring(index + "COMMENT='".length() , lines[lines.length-1].length()-1 ));
				}
			}
		} catch (SQLException e) {}
		return comment;
	}
		
	/**
	 * 实体类代码模版
	 */
	private static String pojoCode = "package " + pageketName
			+ ".pojo;\n\n" 
			+"import net.inno.modules.pojo.BasePojo;\n"
			+ "#import# \n"
			+ "#comment#\n"
			+ "public class #class# extends BasePojo {\n"
			+ "\tprivate static final long serialVersionUID = 1L;\n"
			+ "#field# \n" 
			+ "\t#constructor#\n" 
			+ "#getterAndsetter# \n" 
			+ "}\n";
	
	/**
	 * dao接口代码模版
	 */
	private static String daoCode = "package " + pageketName +".dao;\n\n"
			+"import net.inno.modules.dao.BaseMapper;\n"
			+ "#import# \n"
			+"import org.springframework.stereotype.Repository;\n\n"
			+"@Repository\n"
			+ "public interface #class#Mapper extends BaseMapper<#class#> {\n"
			+ "#function#\n" 
			+ "}\n";
	
	/**
	 * mapper文件模版
	 */
	private static String mapperCode=""
			+"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
			+"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n"
			+"<mapper namespace=\"net.inno.modules.dao.#className#Mapper\">\n"
			+"#sql#"
			+"<sql id=\"Where_ListCondition\">\n"
	        +"\t<where>\n"
	        +"\t\t<foreach collection=\"listSql\" item=\"value\">\n"
	        +"\t\t\t${value}\n"
	        +"\t\t</foreach>\n"
	        +"\t</where>\n"
	        +"</sql>\n"
			+"</mapper>\n"
		;
}