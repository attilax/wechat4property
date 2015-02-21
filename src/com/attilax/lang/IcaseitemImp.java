/**
 * @author attilax 老哇的爪子
	@since  o8s j_i_42$
 */
package com.attilax.lang;
import com.attilax.core;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  o8s j_i_42$
 */
public abstract class IcaseitemImp implements Icaseitem {

	private boolean conditOk;

	/**
	@author attilax 老哇的爪子
		@since  o8s j_k_9$
	
	 * @param b
	 */
	public IcaseitemImp(boolean b) {
		//  attilax 老哇的爪子 j_k_9   o8s   
		this.conditOk=b;
	}
	
	public boolean isConditOk(){
		return this.conditOk;
	}

	/* (non-Javadoc)
	 * @see com.attilax.lang.Icaseitem#exec()
	 * @author  attilax 老哇的爪子
	 *@since  o8s j_i_55$
	 */
 
	//  attilax 老哇的爪子 j_i_42   o8s   
}

//  attilax 老哇的爪子