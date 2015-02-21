/**
 * 
 */
package com.attilax.lang;

import com.attilax.Closure;
import com.attilax.core;
import com.attilax.io.filex;

/**
 * @author ASIMO
 *
 */
public class ResRlsX {

	/**
	@author attilax 老哇的爪子
	@since   obm g_a_44
	 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ResRlsX.realease(7,new Closure () {

				@Override
				public Object execute(Object arg0) throws Exception {
					// TODO Auto-generated method stub
					return null;
				}
			});
	}

		/**
		@author attilax 老哇的爪子
		@since   obm g_b_50
		 
		 */
	private static void realease(final int timeoutSec, final Closure closure) {
	    core.execMeth_Ays(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(timeoutSec *1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					closure.execute("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "resRealeasor"+filex.getUUidName());
		
	}

}
