/**
 * 
 */
package com.attilax.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.inject.Inject;

/**
 * @author ASIMO
 *
 */
public class DbX {

		/**
		@author attilax 老哇的爪子
		@since   p31 j_o_53
		 
		 */
	public static List getColsList(Connection con, String table) {
		List li=new ArrayList();
		// TODO Auto-generated method stub
		String tableName=table;
		  DatabaseMetaData dbmd;
		try {
			dbmd = con.getMetaData();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			throw new RuntimeException(e2);
		}  
		ResultSet rs;
		try {
			rs = dbmd.getColumns(con.getCatalog(),   "%",   tableName,   null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}   
		 try {
			while(rs.next()) {
				   String columnName = rs.getString("COLUMN_NAME");
				   li.add(columnName);
//			   String columnType = rs.getString("TYPE_NAME");
//			   int datasize = rs.getInt("COLUMN_SIZE");
//			   int digits = rs.getInt("DECIMAL_DIGITS");
//			   int nullable = rs.getInt("NULLABLE"); 
//			   System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+
//			     nullable);
				 // }
			}
		} catch (SQLException e) {
			 
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return li;
	}
@Inject
	Connection conn;
@Inject
Session session;
			/**
			@author attilax 老哇的爪子
			@since   p31 l_4_r
			 
			 */
		public static boolean checkConn(Connection con) {
			try {
				Statement stmt=	con.createStatement();
				stmt.executeQuery("select 1 as c ");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//throw new RuntimeException(e);
				return false;
			}
			
			
		}
		
		public   int getCount(String sql) {
	String s="	select count(*) from ( "+sql+") as t ";
	Query query = this.session.createSQLQuery(s.toString());
	Number uniqueResult = (Number) query.uniqueResult();
	return uniqueResult.intValue();
		
		}

}
