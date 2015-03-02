/**
 * 
 */
package com.attilax.db;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

//import www.attilax.lang.getConnEx;

import com.attilax.io.pathx;
import com.attilax.util.PropX;

/**
 * @author ASIMO
 * 
 */
public class Hb4JdbcX {
	private static Configuration configuration = new AnnotationConfiguration();
	public static String CONFIG_FILE_LOCATION = pathx.classPath()
			+ "/hibernate.cfg.xml";
	static File propertyFile = new File(CONFIG_FILE_LOCATION);

	/**
	 * @author attilax 老哇的爪子
	 * @since p2b 10_6_i
	 */
	public static void main(String[] args) {
		//java.lang.Double
//		String js="{\"n\":5.5,\"s\":\"sv1\"}";
//		System.out.println(js);
//		JSONObject jo=JSONObject.fromObject(js);
//		System.out.println(jo.get("n").getClass());
//		DataSource ds = new DataSource() {
//
//			@Override
//			public PrintWriter getLogWriter() throws SQLException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public void setLogWriter(PrintWriter out) throws SQLException {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void setLoginTimeout(int seconds) throws SQLException {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public int getLoginTimeout() throws SQLException {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//
//			@Override
//			public <T> T unwrap(Class<T> iface) throws SQLException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public boolean isWrapperFor(Class<?> iface) throws SQLException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public Connection getConnection() throws SQLException {
//
//				return null;
//			}
//
//			@Override
//			
//		};
//		org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate jt = new NamedParameterJdbcTemplate(
//				ds);
//		 String sql="insert into user(name,age,sex) values(:name,:age,:sex)";  
//	        Map<String,Object> map=new HashMap<String,Object>();  
//	        map.put("name","attilax99");  
//	        map.put("age","");  
//	        map.put("sex", "");  
//	        int temp=jt.update(sql, map);  
//	        System.out.println(temp);

	}
	
	
	public Connection getConnection()
			throws SQLException {
		configuration.configure(propertyFile);
		String url = configuration.getProperty("connection.url");
		String driver = configuration
				.getProperty("connection.driver_class");

		String uname = configuration.getProperty("connection.username");
		String pwd = configuration.getProperty("connection.password");
		try {

			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		Connection conn;

		conn = DriverManager.getConnection(url, uname, pwd);

		return conn;
	}

}
