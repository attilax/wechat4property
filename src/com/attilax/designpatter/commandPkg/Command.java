/**
 * 
 */
package com.attilax.designpatter.commandPkg;

import java.util.HashMap;
import java.util.Map;

import com.attilax.Closure;
import com.attilax.ClosureNoExcpt;
import com.attilax.Closure2;

/**
 * @author ASIMO
 *
 */
public class Command {
	public static Map<String,Closure2> mp=new HashMap<String, Closure2>();
	
	
String key;
		/**
	 * @param string
	 */
	public Command(String string) {
		key=string;
	}

		/**
		@author attilax 老哇的爪子
		 * @return 
		@since   ob2 m_s_54
		 
		 */
	public Object execute() {
		 
		Closure2 cls=(Closure2) mp.get(key);
		return cls.execute("");
	}

			/**
			@author attilax 老哇的爪子
			@since   ob2 m_37_0
			 
			 */
		public static void reg(String key, Closure2 closure_RE) {
			mp.put(key, closure_RE);
			
		}
	
	//public 

}
