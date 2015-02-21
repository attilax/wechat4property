///**
// * 
// */
//package com.attilax.ref;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.beanutils.BeanUtils;
//
//import net.sf.json.JSONObject;
//
////import com.attilax.withdraw.Withdraw;
//
///**
// * @author ASIMO
// *
// */
//public class FldMapX {
//
//		/**
//		@author attilax 老哇的爪子
//		@since   p1s e_i_d
//		 
//		 */
//	public static void process(JSONObject jo, Object o, Map fldmapTable) {
//		Set st = fldmapTable.keySet();
//		for (Object stItem : st) {
//			String jsonKeyname = stItem.toString();
//			if(jsonKeyname.trim().equals("alipay"))
//			{
//			try {
//					String objFld = fldmapTable.get(jsonKeyname).toString();
//					BeanUtils.copyProperty(o, objFld,
//							jo.get(jsonKeyname));
//				} catch (IllegalAccessException e) {
//				 
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//				 
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//}
