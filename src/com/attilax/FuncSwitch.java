/**
 * 
 */
package com.attilax;

import java.io.File;

/**
 * @author ASIMO
 *
 */
public class FuncSwitch {

		/**
		@author attilax 老哇的爪子
		@since   oaq 9_49_4
		 
		 */
	public static String getState(String string) {
	 
		if( new File("c:\\"+string).exists())
			return "true";
			else
		return "false";
	}

}
