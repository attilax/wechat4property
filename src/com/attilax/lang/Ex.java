/**
 * 
 */
package com.attilax.lang;

import com.attilax.core;
import com.attilax.io.filex;
import com.attilax.ref.cantFindIDFieldEx;

/**
 * @author ASIMO
 *
 */
public class Ex {

	/**
	@author attilax 老哇的爪子
	@since   p14 9_47_x
	 
	 */
	public static void main(String[] args) {
		try {
			throw new RuntimeException("ttt");
		} catch (Exception e) {
			filex.saveLog(core.toJsonStrO88(e)  , "c:\\ep");
		}
		try {
			throw new cantFindIDFieldEx();
		} catch (Exception e) {
			filex.saveLog(core.toJsonStrO88(e)  , "c:\\ep");
		}

	}

}
