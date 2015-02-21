/**
 * 
 */
package com.attilax.lang;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.attilax.fileTrans.ConnEx;
import com.attilax.io.filex;
import com.attilax.lbs.NoRztEx;
import com.attilax.net.websitex;

/**
 * @author ASIMO
 *
 */
public class WebInfoX {

	public int pages;
	public int pageStart;
	/**
	@author attilax 老哇的爪子
	 * @throws NoRztEx 
	 * @throws ConnEx 
	 * @throws IOException 
	@since   p17 d_b_0
	 
	 */
	public static void main(String[] args) throws NoRztEx, ConnEx, IOException {
		//5bu6562R
	//	String path=
	//	String rzt=args[1];
	WebInfoX x=new WebInfoX();
	x.fileName=args[0];// "c:\\r2.csv";
	x.pages=Integer.parseInt(args[1]);
	x.pageStart=Integer.parseInt(args[2]);;
	
	x.exec( );
	System.out.println("--fi");

	}
	filex fx;
	public String fileName ;
	public String kw;
	public String exec( ) throws IOException     {
 
		
		fx=new filex(fileName);
		int pages=getpage();
		for(int i=1;i<=pages;i++)
		{
			if(i<pageStart)
				continue;
			try {
				singlePage(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		fx.close();
		return null;


		//  return tab.html();

	}


						/**
		@author attilax 老哇的爪子
						 * @param page 
						 * @throws ConnEx 
						 * @throws NoRztEx 
						 * @throws ParseLsitEx 
		@since   p17 g_37_c
		 
		 */
	private void singlePage(int page) throws ConnEx, NoRztEx, ParseLsitEx {
//		if(page>0)
//			break;
	
		// System.out.println ( new websitex(). WebpageContent(url, "gbk", 3));
		String html = null;
		try {
			String api = (String) getCurPageUrl(page);
			//http://www.czvv.com/k5bu6562Rp0c0cc0s0m0e0f0d0.html
		 	websitex wc = new websitex();
		 	wc.refer="http://www.czvv.com/";//http://www.czvv.com/
			html = wc.WebpageContent(api, "utf-8", 15);
		}   catch (Exception e) {
			e.printStackTrace();
			throw new ConnEx(e.getMessage());
		}
		//================trace
		if (new File("C:\\traceOk").exists())
			filex.save_safe(html, "c:\\rztTrace.html");
		else
			filex.del("c:\\rztTrace.html");
		// filex.read("c:\\rzt.html", "gbk");
		// filex.write(path + ".htm", html);
		 //	html=filex.read("c:\\00.htm");
		List li=getList(html);
		
	
		for(Object obj:li)
		{
			try {
				processItem(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}


					/**
		@author attilax 老哇的爪子
		@since   p17 e_3_r
		 
		 */
	private Object getCurPageUrl(int i) {
		String s="http://www.czvv.com/k"+ Base64. encode(kw,"utf-8") +"p@pagec0cc0s0m0e0f0d0.html".replaceAll("@page", String.valueOf(i-1));
		return s;
	}


				/**
		@author attilax 老哇的爪子
				 * @param obj 
		@since   p17 d_58_42
		 
		 */
	private void processItem(Object obj) {
		Element item=(Element) obj;
	  String name=	item.getElementsByClass("resultName").get(0).text();
	  Element e=  item.getElementsByClass("l_r").get(0);
	 String tel=e.child(0).text();
	 String lyesyiren=e.child(1).text();
	 String addr=e.child(2).text();
	 
		String line = name+","+tel+","+lyesyiren+","+addr;
		fx.appendLine_flush_safe(line);
		System.out.println( line);
		
	}


			/**
		@author attilax 老哇的爪子
			 * @param html 
			 * @throws NoRztEx 
			 * @throws ParseLsitEx 
		@since   p17 d_57_m
		 
		 */
	private List getList(String html) throws NoRztEx, ParseLsitEx {
		try {
			Document doc = null;
		doc = Jsoup.parse(html);
		Elements tabs = doc.getElementsByTag("ol");
	 
		return tabs;
	} catch (Exception e) {
		e.printStackTrace();
	//	System.out.println("norzt:" + addr);
		throw new ParseLsitEx("noRzt");
	}
	 
 
	}


		/**
		@author attilax 老哇的爪子
		@since   p17 d_55_h
		 
		 */
	private int getpage() {
		 
		return pages;
	}

}
