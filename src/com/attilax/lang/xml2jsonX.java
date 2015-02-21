/**
 * @author attilax 老哇的爪子
	@since  o7l mpf$
 */
package com.attilax.lang;
import com.attilax.core;
import com.attilax.io.filex;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.w3c.dom.Document;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
/**
 * @author  attilax 老哇的爪子
 *@since  o7l mpf$
 */
public class xml2jsonX {

	/**
	@author attilax 老哇的爪子
		@since  o7l mpf$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  mpf   o7l 
		String string = "c:\\im.cdm.xml";
		//string="D:\\workspace\\vodx\\WebRoot\\WEB-INF\\springMVC.xml";
		String s=filex.read(string);
		String s2=getJSONFromXml(s).toString();
		filex.save(s2, "c:\\im.json");
		System.out.println("---");

	}
    /** 
     * 将xmlDocument转换为JSON对象 
     * @param xmlDocument XML Document 
     * @return JSON对象 
     */  
    public static JSON getJSONFromXml(Document xmlDocument) {  
        String xmlString = xmlDocument.toString();  
        return getJSONFromXml(xmlString);  
    }  
    /** 
     * 将xml字符串转换为JSON对象 
     * @param xmlFile xml字符串 
     * @return JSON对象 
     */  
    public static JSON getJSONFromXml(String xmlString) {  
        XMLSerializer xmlSerializer = new XMLSerializer();  
        JSON json = xmlSerializer.read(xmlString);  
        return json;  
    }  
	//  attilax 老哇的爪子 mpf   o7l   
}

//  attilax 老哇的爪子