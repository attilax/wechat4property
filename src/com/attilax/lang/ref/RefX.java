/**
 * 
 */
package com.attilax.lang.ref;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.kunpeng.www.domain.CarVo;

import net.sf.json.JSONObject;
 

/**
 * @author ASIMO
 * 
 */
public class RefX {

	/**del space and down_string
	 * @author attilax 老哇的爪子
	 * @since p1q d_37_54
	 */
	public static void copyProperties(JSONObject jo, Object g) {
		Field[] fds = g.getClass().getDeclaredFields();
		for (Field field : fds) {
			if(field.getName().toLowerCase().trim().startsWith("price"))
				System.out.println("--fld:"+field.getName());
			Set st = jo.keySet();
			for (Object stItem : st) {
				String jsonKeyname = stItem.toString();
			
				if (field.getName().toLowerCase().trim()
						.equals(jsonKeyname.toLowerCase().replaceAll("_", "").trim())) {
					if(jsonKeyname.toLowerCase().trim().startsWith("price"))
						System.out.println("");
					try {
						BeanUtils.copyProperty(g, field.getName(),
								jo.get(jsonKeyname));
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
	}

	public static void copyProperties(JSONObject jo, Object car, Map FldmapTable) {
		copyProperties(jo,car);
		FldMapX.process(jo,car,FldmapTable);
		
	}

		/**
		@author attilax 老哇的爪子
		@since   p26 j_n_g
		 
		 */
	public static Object getUniName(String objFld) {
		// TODO Auto-generated method stub
		return objFld.replaceAll("_", "").toLowerCase();
	}

}
