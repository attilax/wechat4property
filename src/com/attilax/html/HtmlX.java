/**
 * 
 */
package com.attilax.html;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author ASIMO
 *
 */
public class HtmlX {

	/**
	@author attilax 老哇的爪子
	@since   p25 g_43_6
	 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * com.attilax.html.Htmlencode
		@author attilax 老哇的爪子
		@since   p25 g_44_37
	 */
	public static String Htmlencode(Object s2) {
		//org.json.simple.ItemList
		// attilax 老哇的爪子  2_f_42   o03 
		
		String  s=	StringEscapeUtils.escapeHtml4(s2.toString());
	    s = s.toString().replaceAll("\r\n", "<br>");
		return s;
		
	}

}
