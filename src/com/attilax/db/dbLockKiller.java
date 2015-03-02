/**
 * @author attilax 老哇的爪子
	@since  o8r 4_h_53$
 */
package com.attilax.db;
import com.attilax.Closure;
import com.attilax.core;
import com.attilax.collection.CollectionUtils;
import com.attilax.util.god;
import com.focustar.ServiceLoctor4vod;
import com.focustar.util.HbX4vod;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.hibernate.Session;
/**
 * @author  attilax 老哇的爪子
 *@since  o8r 4_h_53$
 */
public class dbLockKiller {

	private static int num=0;
	public	DBX dbx;
	/**
	@author attilax 老哇的爪子
		@since  o8r 4_h_53   
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  4_h_53   o8r 
		
		 dbLockKiller k=ServiceLoctor4vod.getDbLockKiller();
		 k.start();
//		 List li= k.getLockTables();
//		 k.kill(li);
//		System.out.println(core.toJsonStrO88(li));
	//	serv
//		 startAsCycle();
	

	}
	
	/**
	@author attilax 老哇的爪子
		@since  2014-9-2 上午01:24:04   
	
	 */
	@SuppressWarnings("unchecked")
	public void start() {
		// attilax 老哇的爪子  上午01:24:04   2014-9-2 
		 List li=  getLockTables();
		 kill(li);
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-9-2 上午01:21:06   
	
	 * @param li
	 */
	private void kill(List li) {
		// attilax 老哇的爪子  上午01:21:06   2014-9-2 
		CollectionUtils.each_safe(li, new Closure<Map, Object>(){

			@Override
			public Object execute(Map arg0) throws Exception {
				// attilax 老哇的爪子  上午01:21:52   2014-9-2 
				String sql = "kill "+ arg0.get("spid").toString();
				core.log(sql);
				dbx.execSql(sql);
				return null;
				
			}});
		
	}

	public List<Map> getLockTables()
	{
		String s="     select            request_session_id spid,           OBJECT_NAME(resource_associated_entity_id) tableName        from            sys.dm_tran_locks       where            resource_type='OBJECT'";
		
		return dbx.execSql(s);
		
	}
	//  attilax 老哇的爪子 4_h_53   o8r   
//	public static int  num=0;
	/**
	@author attilax 老哇的爪子
		@since  2014-9-2 上午01:27:51   
	
	 */

//	public   void startAsCycle() {
//		
//		
//	 	if(num>2)return;
//	 		num++;
//		// attilax 老哇的爪子  上午01:27:51   2014-9-2 
//	
//	}

	/**
	@author attilax 老哇的爪子
		@since  2014-9-2 上午01:36:03   
	
	 * @return
	 */
	public static dbLockKiller $() {
		// attilax 老哇的爪子  上午01:36:03   2014-9-2 
		return new dbLockKiller();
		
	}
}

//  attilax 老哇的爪子