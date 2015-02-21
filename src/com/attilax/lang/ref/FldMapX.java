/**
 * 
 */
package com.attilax.lang.ref;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.attilax.io.filex;

import net.sf.json.JSONObject;

//import com.attilax.withdraw.Withdraw;

/**
 * @author ASIMO
 *
 */
public class FldMapX {

	/**
	 * @author attilax 老哇的爪子
	 * @since p1s e_i_d
	 */
	public static void process(JSONObject jo, Object o, Map fldmapTable) {
		Set st = fldmapTable.keySet();
		for (Object stItem : st) {
			String jsonKeyname = stItem.toString();
			// if(jsonKeyname.trim().equals("alipay"))

			 
				String objFld = fldmapTable.get(jsonKeyname).toString();
				copyProperty(o, objFld, jo.get(jsonKeyname));
				
			 

		}
	}

		/**
		@author attilax 老哇的爪子
		@since   p26 j_k_59
		 
		 */
	private static void copyProperty(Object o, String objFld, Object object) {
		try {
			BeanUtils.copyProperty(o, objFld, object);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Field[] fds=o.getClass().getDeclaredFields();
		for (Field field : fds) {
			if(field.getName().replaceAll("_", "").toLowerCase().equals(RefX.getUniName(objFld)))
			{
				try {
					BeanUtils.copyProperty(o, field.getName(), object);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
		
	}

	public static Map frmTxt(String f) {
		Map m = new HashMap();
		List<String> li = filex.toList_filtEmptyNTrim(f);
		for (String line : li) {
			String[] a = line.split(",");

			m.put(a[0], a[1]);

		}

		return m;
	}

}
