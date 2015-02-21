/**
 * 
 */
package com.attilax.lang;

import java.io.IOException;

/**
 * @author ASIMO
 *
 */
public class WebinfoT {

	/**
	@author attilax 老哇的爪子
	 * @throws IOException 
	@since   p19 b_47_54
	 
	 */
	public static void main(String[] args) throws IOException {
		  WebInfoX x=new WebInfoX();
			x.fileName="c:/0.csv" ;// "c:\\r2.csv";
			x.pages=50;
			x.pageStart=1;
			x.kw="湖北建设";
			
				x.exec( );

	}

}
