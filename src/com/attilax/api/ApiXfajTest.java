/**
 * @author attilax 老哇的爪子
	@since  o9n i_0_7$
 */
package com.attilax.api;

import com.attilax.core;
import com.attilax.cfg.ApiInier;
import com.attilax.cfg.IocX;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.net.requestImp;

import static com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import www.Hbx4shp;

/**
 * @author attilax 老哇的爪子
 * @since o9n i_0_7$
 */
public class ApiXfajTest   {

	public ApiXfajTest() {
		super();
	}

	public static String defOkRet = "{\"errcode\":0,\"errmsg\":\"ok\" }";

	/**
	 * @author attilax 老哇的爪子
	 * @since o9n i_0_7
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// pp4();
//		new ApiInier().ini();
//		OrmX ax= IocX.getBean(OrmX.class);
//		requestImp ri=new requestImp();
//		String datas = "searchCar("+ filex.read(pathx.classPath(ApiXsqljsonFmt.class)+"/sqljson.json") +")";
////		datas = "upcar("+ filex.read(pathx.classPath(ApiXsqljsonFmt.class)+"/sqljson_update.json") +")";
////		datas = "del_car("+ filex.read(pathx.classPath(ApiXsqljsonFmt.class)+"/sqljson_del.json") +")";
//		datas = "regNormalUser ("+ filex.read(pathx.classPath(ApiXsqljsonFmt.class)+"/json_up_faj.json") +")";
//		datas = ""+ filex.read(pathx.classPath(ApiXsqljsonFmt.class)+"/json_query_faj.json") +"";
//		//datas=filex.read( ApiXsqljsonFmt.class,"addOrder_faj.json");
//		System.out.println(datas);
//		ri.setParam("data", datas);
		System.out.println(ax.handleReq(ri) );
	}

	private static void pp4() {
		String data = " method ( {'p1':'xxx','p2',33} )  ";
		// new ApiInier().ini();
		// // attilax 老哇的爪子 i_0_7 o9n
		// ApiX2 c=new ApiX2();
		// c.hbx=Hbx4shp.getStance();
		int brkLeftIndex = data.indexOf("(");

		String subMeth = data.substring(0, brkLeftIndex).trim();

		int brkLastRit = data.lastIndexOf(")");
		Object parameter = data.substring(brkLeftIndex + 1, brkLastRit);
		System.out.println("@@" + subMeth + "-----" + parameter + "@@end");
	}

 
	// attilax 老哇的爪子 i_0_7 o9n

		/**
		@author attilax 老哇的爪子
		 * @param parameter 
		@since   p20 k_52_49
		 
		 */
	 
}

// attilax 老哇的爪子