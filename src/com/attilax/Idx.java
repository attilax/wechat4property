/**
 * 
 */
package com.attilax;

/**
 * @author ASIMO
 *
 */
public class Idx {

		/**
		@author attilax 老哇的爪子
		@since   oa6 i_d_m
		 
		 */
	public static void each(String ids, Closure  closure) {
		 String[] a=ids.split(",");
		 for (String id2 : a) {
			 try {
				 int id=Integer.parseInt(id2);
					try {
						closure.execute(id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	}

}
