/**
 * 
 */
package com.attilax.lang;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.attilax.Closure;
import com.attilax.core;
import com.attilax.biz.seo.getConnEx;
import com.attilax.coll.ListX;
import com.attilax.io.filex;
import com.attilax.sms.pullEx;

/**
 * @author ASIMO
 * 
 */
public class AdrX {

	public static void main(String[] args) throws getConnEx, SQLException {
		// exe();
		new AdrX().exe();
		System.out.println("fini");

	}
	
	
	
	
	
	
	
	
	
	
	
	

	private static void exe() {
		final AdrX ax = new AdrX();
		// System.out.println(ax.pull(""));

		ax.db = "c:\\mms.db";
		ax.startIdx=944;
		ax.setFilter(new Closure<Map, Boolean>() {

			@Override
			public Boolean execute(Map arg0) throws Exception {
				if (arg0.get("body").toString().contains("您的校验码"))
					return false;
				else
					return true;
			}
		});

		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("timer evet");
				try {
					ax.pull("");
					System.out.println("pull..." + filex.getUUidName());
					List<Map<String, Object>> li = ax.get_msg();
					System.out.println(li.size());
				//	if (li.size() < 10)
						System.out.println(core.toJsonStrO88(li));

				} catch (getConnEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (pullEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, 0, 8000);
	}

	Closure<Map, Boolean> filter;

	/**
	 * @author attilax 老哇的爪子
	 * @since p14 g_8_46
	 */
	private void setFilter(Closure<Map, Boolean> closure) {
		// TODO Auto-generated method stub
		filter = closure;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since p14 g_7_47
	 */
	private void setFilter(String string, String string2) {
		// TODO Auto-generated method stub

	}

	private String db;
	public Connection conn;
	public int startIdx;

	private List<Map<String, Object>> get_msg() throws getConnEx, SQLException {
		// ini
		if (conn == null)
			conn = getConnection();
		List<Map<String, Object>> li_r = new ArrayList<Map<String, Object>>();
		QueryRunner queryRunner = new QueryRunner(true);
		// queryRunner.query()
		// Map<String, Object> map = queryRunner.query(conn,
		// "select * from content ", new MapHandler());
		List<Map<String, Object>> list = queryRunner
				.query(conn,
						" select  * from sms where type=1 and _id>"+String.valueOf(startIdx)+" order by date desc limit 0,10 ",
						new MapListHandler());

		for (Map<String, Object> map : list) {

			try {
				if (this.filter.execute(map)) {

				} else
					li_r.add(map);
			} catch (Exception e) {
				core.log(e);

			}

		}
		return li_r;

	}

	private Connection getConnection() throws getConnEx {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new getConnEx("getconnex" + core.getTrace(e));
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + this.db);
		} catch (SQLException e) {
			throw new getConnEx("getconnex" + core.getTrace(e));
		}
		return conn;
	}

	int nFlag = 0;

	/**
	 * @author attilax 老哇的爪子
	 * @throws pullEx
	 * @since p14 f_37_a
	 */
	public String pull(String string) throws pullEx {
		new File("c:/mms.db").delete();

		String s = CmdX
				.exec("  cmd /c  	Adb pull /data/data/com.android.providers.telephony/databases/mmssms.db c:/mms.db ");
		if (!new File("c:/mms.db").exists())
			throw new pullEx();
		return s;
	}

}
