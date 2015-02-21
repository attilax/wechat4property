/**
 * 
 */
package com.attilax;

/**
 * @author ASIMO
 *
 */
public class retryT2 {

	/**
	@author attilax 老哇的爪子
	@since   oac d_h_56
	 
	 */
	public static void main(String[] args) {
	String s=	(String) core.retry3(new Closure () {

			@Override
			public Object execute(Object arg0) throws Exception {
				 throw new RuntimeException("ttt");
				//return null;
			}
		}, new errEventProcess(){

			@Override
			public Object execute(Object arg0) throws Exception {
			 
				return "...";
			}
			
		},"c:\\0terr");
	System.out.println("==="+s);

	}

}
