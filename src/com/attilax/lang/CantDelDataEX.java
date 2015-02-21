/**
 * @author attilax 老哇的爪子
	@since  o93 j_z_41$
 */
package com.attilax.lang;
import com.attilax.core;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.directwebremoting.annotations.DataTransferObject;
/**
 * @author  attilax 老哇的爪子
 *@since  o93 j_z_41$
 */
@DataTransferObject
public class CantDelDataEX extends RuntimeException {

	/**
	@author attilax 老哇的爪子
		@since  o93 j_z_54$
	
	 * @param string
	 */
	public CantDelDataEX(String string) {
		//  attilax 老哇的爪子 j_z_54   o93   
		super(string);
	}
	//  attilax 老哇的爪子 j_z_41   o93   
}

//  attilax 老哇的爪子