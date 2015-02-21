/**
 * 
 */
package com.attilax.wechat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.attilax.core;
 
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.util.Mapx;

import net.sf.json.JSONObject;
 

/**
 * @author ASIMO
 *
 */
public class Xml2JsonUtil {
	
	public static void main(String[] args) {
		String t=filex.read(pathx.classPath(WechatX.class)+"/prepay.xml");
		Map mp = xml2map(t);
		Map mp_ordered=Mapx.order(mp);
		String string=Mapx.toUrlstr(mp);
		System.out.println(core.toJsonStrO88(mp) );
		System.out.println(string);
	}
	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param xml
	 *            xml格式的字符串
	 * @return 成功返回json 格式的字符串;失败反回null
	 */
	@SuppressWarnings("unchecked")
	public static  String xml2JSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString(2);
		} catch (Exception e) {
			//log.error("传入XML后转换JSON出现错误===== Xml2JsonUtil-->xml2JSON============>>",e);
			return null;
		}
	}
	
	
	public static  Map xml2map(String xml) {
		try {
			JSONObject obj = new JSONObject();
			 
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			Map mp = iterateElement(root);
			 
			return mp;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		 
	}

	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param file
	 *            java.io.File实例是一个有效的xml文件
	 * @return 成功反回json 格式的字符串;失败反回null
	 */
	@SuppressWarnings("unchecked")
	public static String xml2JSON(File file) {
		JSONObject obj = new JSONObject();
		try {
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(file);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString();
		} catch (Exception e) {
			//log.error("传入文件后转换JSON出现错误====Xml2JsonUtil-->xml2JSON=============>>",e);
			return null;
		}
	}

	/**
	 * 一个迭代方法
	 * 
	 * @param element
	 *            : org.jdom.Element
	 * @return java.util.Map 实例
	 */
	@SuppressWarnings("unchecked")
	private static Map  iterateElement(org.jdom.Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
//		Map 
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {  //@openid
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
				if (obj.containsKey(et.getName())) {  //openid 
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
			//	obj.put(et.getName(), list);
				obj.put(et.getName(), et.getTextTrim());
			}
		}
		return obj;
	}
}
	//private static final Log log = LogFactory.getLog(Xml2JsonUtil.class);	