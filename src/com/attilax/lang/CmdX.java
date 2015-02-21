package com.attilax.lang;

    import java.io.BufferedReader;  
    import java.io.File;  
    import java.io.FileWriter;  
import java.io.InputStreamReader;  

import com.attilax.core;
import com.attilax.sms.NoDeviceEx;
import com.attilax.sms.pullEx;
    /** 
     * Java 获得  CMD 输出信息 
     * @author MrChu 
     * 2013-2-19 
     */  
    public class CmdX {  
       
     public static void main(String[] args) throws NoDeviceEx, pullEx {  
      // CMD 执行命令  
      String cmd = "cmd /c adb devices" ;  
        
    String r = exec(cmd);
    String  no=getDeviceNo(r);
	System.out.println( r); ;  
	System.out.println(no); ;  
	AdrX ax=new AdrX();
	ax.pull("");
	
     }
     	/**
		@author attilax 老哇的爪子
     	 * @throws NoDeviceEx 
		@since   p14 e_52_6
		 
		 */
	private static String getDeviceNo(String txt) throws NoDeviceEx {
		  String[] a=txt.split("\n");
		    for (String line : a) {
				if(line.endsWith("device"))
				return (getDeviceNoSingle(line));
			}
		throw new NoDeviceEx("");
	}
		/**
			@author attilax 老哇的爪子
			@since   p14 e_52_y
			 
			 */
		private static String getDeviceNoSingle(String line) {
			 String[] a=line.split("\t");
			return a[0];
		}
	public static String exec(String cmd) {
 		try {  
 		   // 执行 CMD 命令  
 		   Process process = Runtime.getRuntime().exec(cmd);  
 		//  process.geto
 		   // 从输入流中读取文本  
 		   BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
 		     String r="";
 		   // 构造一个写出流并指定输出文件保存路径  
 		//   FileWriter fw = new FileWriter(new File("C:/Users/Administrator/Desktop/CmdInfo.txt"));  
 		     
 		   String line = null;  
 		     
 		   // 循环读取  
 		   while ((line = reader.readLine()) != null) {  
 		    // 循环写入  
 		  r=r+(line + "\n");  
 		   }  
 		     
 		 
 		   // 关闭输出流  
 		   process.getOutputStream().close();  
 		     
 		   System.out.println("cmd ext finish!");  
 		   return r;
 		  } catch (Exception e) {  
 			 return core.toJsonStrO88(e);
 		  }
		
 	}  
	@Deprecated
	public static void exec0(String cmd) {
		try {  
		   // 执行 CMD 命令  
		   Process process = Runtime.getRuntime().exec(cmd);  
		     
		   // 从输入流中读取文本  
		   BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
		     
		   // 构造一个写出流并指定输出文件保存路径  
		   FileWriter fw = new FileWriter(new File("C:/Users/Administrator/Desktop/CmdInfo.txt"));  
		     
		   String line = null;  
		     
		   // 循环读取  
		   while ((line = reader.readLine()) != null) {  
		    // 循环写入  
		    fw.write(line + "\n");  
		   }  
		     
		   // 刷新输出流  
		   fw.flush();  
		     
		   // 关闭输出流  
		   fw.close();  
		     
		   // 关闭输出流  
		   process.getOutputStream().close();  
		     
		   System.out.println("程序执行完毕!");  
		  } catch (Exception e) {  
		   e.printStackTrace();  
		  }
	}  
       
    }  