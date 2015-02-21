package com.attilax.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.attilax.Closure;
import com.attilax.ClosureNoExcpt;
import com.attilax.core;
import com.attilax.collection.Func;
import com.attilax.collection.listUtil;
import com.attilax.text.strUtil;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Funcx;
import com.attilax.util.fileC0;
import com.attilax.util.god;
import com.attilax.util.travDir;
//import com.focustar.playRec.GvPlayRecord;
//import com.focustar.push.FileNotExist;

import m.FileService;

public class filex {

	/**
	@author attilax 老哇的爪子
		@since  o8j 0_r_52$
	
	 * @param savefileName
	 */
//	public filex(String savefileName) {
//		//  attilax 老哇的爪子 0_r_52   o8j   
//	}


	public static void main(String[] args) {
		System.out.println(pathx.webAppPath() );
		
//		String file = pathx.webAppPath() + "\\" + mtrl.getFilePath();
//		if (!new File(file).exists())
//			throw new FileNotExist("mtrl:" + mtrl.getFilePath());
//		mtrl.setSize(String.valueOf(filex.getSize(file)));
		
		//filex.save_SF("aa", "c:\\attilaxO0kk\\aaa\\t.txt");
		
		//o0k();
	//	rename_batch();
		
	System.out.println(fileNameEncode("a*|b"));	
		System.out.println("--");

//		System.out.println(getExtName("c:\\haha.htm"));
//		List li = new ArrayList();
//		li.add("xx");
//		filex.saveList2file(li, "c:/ccx2/ka.log", "gbk");
//		
//		
//		String fName =" G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";  
//		  
////      方法一：  
//  
//        File tempFile =new File( fName.trim());  
//  
//        String fileName = tempFile.getName();  
//          
//        System.out.println("fileName = " + fileName);  
  
	}


	private static void o0k() {
		String files = "classpath:applicationContext-*.xml";
 List locations=filex.getFiles_Spring_lst(files);
//core.print_wzFmt(locations);
	}

	
	/**
	@author attilax 老哇的爪子
		@since  2014-6-19 下午03:24:42$
	  
	 */
	private static void rename_batch() {
		// attilax 老哇的爪子 下午03:24:42 2014-6-19
		String s = "E:\\workspace\\GialenWeiXin\\WebRoot\\mobile\\gridimg";
		List l = travDir.getAllFileList(s, "png");
		listUtil.map_generic(l, new Func_4SingleObj<String, String>() {

			@Override
			public String invoke(String o) {
				// attilax 老哇的爪子 下午04:12:23 2014-6-19

				String ori = "方格抽奖";
				String newF = o.replaceAll(ori, "");
				rename(o, newF);
				return newF;
			}
		});

	}
	 public static void rename(String f, String target) {
		// attilax 老哇的爪子  下午04:15:25   2014-6-19 
		move(f, target);
	}


	// static 
	 public static final byte[] readImageData(String path){
		File file= new File(path);
		try{
			long length = file.length();
			byte tempData [] = new byte[(int)length];
			FileInputStream  fin = new FileInputStream(file);
			BufferedInputStream  bufIn=new BufferedInputStream(fin);
			bufIn.read(tempData);
			fin.close();
			return tempData;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("read image is  erro,please check path");
			return null;
		}
	}
	 
	 
	 public static final byte[] read4img(String path) throws readEX  {
			File file= new File(path);
			 
				long length = file.length();
				byte tempData [] = new byte[(int)length];
				FileInputStream fin;
				try {
					fin = new FileInputStream(file);
				} catch (FileNotFoundException e1) {
					//  attilax 老哇的爪子 7:42:05 AM   Sep 2, 2014   
					e1.printStackTrace();
					throw new readEX("FileNotFoundException");
				}
				BufferedInputStream  bufIn=new BufferedInputStream(fin);
				try {
					bufIn.read(tempData);
				} catch (IOException e) {
					//  attilax 老哇的爪子 7:41:52 AM   Sep 2, 2014   
					e.printStackTrace();
					throw new readEX("IOException");
				}
				try {
					fin.close();
				} catch (IOException e) {
					//  attilax 老哇的爪子 7:41:56 AM   Sep 2, 2014   
					throw new readEX("IOException ");
				}
				return tempData;
			 
		}


/**
 *   xx.jpg >>> jpg
@author attilax 老哇的爪子
\t@since  Aug 2, 2014 9:27:29 AM$

 * @param fileName
 * @return
 */
	public static String getExtName(String fileName) {
		
		fileName=getFileName(fileName);  //o82  add can get empty extname.... no extname file can process..now .
		String name = "";
		String extention = "";
		if (fileName.length() > 0 && fileName != null) { // --截取文件名
			int i = fileName.lastIndexOf(".");
			if (i > -1 && i < fileName.length()) {
				name = fileName.substring(0, i); // --文件名
				extention = fileName.substring(i + 1); // --扩展名
			}
		}
		return extention;

	}
	
	public static String getFileName_noExtName(String fileName) {
		String name = "";
		String extention = "";
		int lastSpashIndex=fileName.lastIndexOf("\\");
		if (fileName.length() > 0 && fileName != null) { // --截取文件名
			int i = fileName.lastIndexOf(".");
			if (i > -1 && i < fileName.length()) {
				name = fileName.substring(lastSpashIndex+1, i); // --文件名
				extention = fileName.substring(i + 1); // --扩展名
			}
		}
		return name;

	}
	
	public static String getFileName(String fileName) {
		String name = "";
		String extention = "";
		int lastSpashIndex=fileName.lastIndexOf("\\");
		if(lastSpashIndex==-1)
			lastSpashIndex=fileName.lastIndexOf("/");
		if (fileName.length() > 0 && fileName != null) { // --截取文件名
		 
		 
				name = fileName.substring(lastSpashIndex+1); // --文件名
			 
		}
		return name;

	}

	public static void write(String fileName, String str) {
		FileService.writeFile(fileName, str);

	}

	public static void write_utf8(String fileName, String s) {
		fileC0.writeFile(fileName, s, "utf-8");

	}

	
	public static void write(String fileName, String s, String encode) {
		fileC0.writeFile(fileName, s, encode);

	}

	public static String read(String path, String encode) {

		// if(path.equals(""))
		// {
		// System.out.println("--warnging :  path is empty cad");
		// return "";
		// }
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(path), encode);
		} catch (Exception e1) {

			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
		BufferedReader reader;
		ArrayList li = new ArrayList<String>();
		try {
			reader = new BufferedReader(isr);

			// BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
			String line = reader.readLine();
			while (line != null) {
				// writer.write(line);
				sb.append(line + "\r\n");
				line = reader.readLine();

			}

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//o4e  cancel occu  handel
		try {
			isr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	/**
	 * o3i add mkdir code
	 * @param list
	 * @param path
	 * @param encode
	 */
	public static void saveList2file(List list, String path, String encode) {
		
		 createAllPath(path);
		     
		     
		String s = "";
		StringBuilder strApp = new StringBuilder();
		List<String> li2 = list;

		String sx = "";
		int len = list.size();
		String enter = System.getProperty("line.separator");
		for (int i = 0; i < len; i++) {
			if (i % 2000 == 0)
				System.out.println("--saveList2file:" + i);
			sx = li2.get(i);
			if (s.equals("")) {
				s = sx;
				strApp.append(sx);
			} else
				strApp.append(enter).append(sx);
			// s=s+enter+sx;
		}

		s = strApp.toString();
		fileC0.writeFile(path, s, encode);
	}

	/**
	 * o3i    
	 * @param fileFullPath 
	 */
	public static void createAllPath(String fileFullPath) {
		File file = new File(fileFullPath);
		 if (!file.getParentFile().exists()) {
		     System.out.println("目标文件所在路径不存在，准备创建。。。");
		     if (!file.getParentFile().mkdirs()) {
		      System.out.println("创建目录文件所在的目录失败！");
		   
		     }
		 }
	}

	/**
	 * default utf8 encode
	 * @param s
	 * @return
	 */
	public static List<String> read2list(String s) {
		 
		return read2list(s,"utf-8");
	}
	
	public static List<String> read2list(String files, String encode) {
		List<String> lst=new ArrayList<String>();
		 List<String> fs=listUtil.toList(files);
		
		for(String f:fs)
		{
			fileC0 fc = new fileC0();
			List<String> lir = new ArrayList<String>();
			List<String> li = fc.fileRead2list(f, encode);
			lst.addAll(li);
		}
	
		return lst;
	}

	public static void save(String txt, String target2) {
		fileC0 fc = new fileC0();
		fc.save(txt, target2);
		
	}
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o0m i_48_2   
	
	 * @param txt
	 * @param target2
	 */
	public static void save_SF(String txt, String target2) {
		try {
			createAllPath(target2);
		} catch (Exception e) {
			core.log("createAllPath err:"+target2);
		}
		
		try {
			fileC0 fc = new fileC0();
			fc.save(txt, target2);
			
		} catch (Exception e) {
			core.log("savefile err:"+target2 +"  "+e.getMessage());
		}
		
	}

	public static void saveList2file(List<String> li, String path) {
		saveList2file(li, path, "utf-8");
		
	}

	public static List read2list_filtEmpty(String hasPhonie_file) {
		List li=read2list(hasPhonie_file);
		li=listUtil.filterO4(li, new Func(){

			@Override
			public Object invoke(Object... o) {
				String s=(String) o[0];
				if(s.trim().length()==0)
					return true;
				return false;
			}});
		return li;
	}

	public static String read(String string) {
		 try {
			 return read(string,"utf-8");
		} catch (Exception e) {
			return "";
		}
		
	}
	
	public static String read_SF(String file,String defVal) {
		 try {
			 return read(file,"utf-8");
		} catch (Exception e) {
			return defVal;
		}
		
	}

//	public static List read2list_filtEmptyNstartSpace(String dicFilePath) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static List read2list_filtEmptyNstartSpace(String dicFilePath) {
		List li=read2list(dicFilePath);
		li=listUtil.filterO4(li, new Func(){

			@Override
			public Object invoke(Object... o) {
				String s=(String) o[0];
				if(s.trim().length()==0)
					return true;
				return false;
			}});
		li=listUtil.map_generic(li, new Funcx<Object, String>(){

			@Override
			public String invoke(Object... o) {
			 
				return o[0].toString().trim();
			}});
		return li;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-8 上午09:37:40$
	
	 * @param f
	 * @param string
	 */
	public static void move(String f, String target) {
		// attilax 老哇的爪子  上午09:37:40   2014-5-8 
//		File f=new  File(target);
//		if(f.exists())
			createAllPath(target);
			
			File oldFile = new File(f);
			//filex.move(f,target);
//			
//			// 将文件移到新文件里
//			
 		File fnew = new File(target);
 		oldFile.renameTo(fnew);
		
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午5:37:56$
	
	 * @return
	 */
	public static String getUUidName() {
		// attilax 老哇的爪子  下午5:37:56   2014年5月11日 
		return god.getUUid();
	}


	/**
	@author attilax 老哇的爪子
	\t@since  Jul 20, 2014 10:44:41 AM$
	
	 * @param jsonStr
	 * @param file
	 */
	public static void save_safe(String jsonStr, String file) {
		// attilax 老哇的爪子  10:44:41 AM   Jul 20, 2014 
		save_SF(jsonStr, file);
//		
//		try {
//			save(jsonStr,file);
//		} catch (Exception e) {
//			core.log(e);
//			// TODO: handle exception
//		}
//		
	}


	/**
	@author attilax 老哇的爪子
		@since  o7k V5k$
	
	 * @param obj
	 * @param file
	 */
	public static void save_safe(Object obj, String file) {
		// attilax 老哇的爪子  V5k   o7k 
		String js=core.toJsonStr(obj);
		
		try {
		createAllPath(file);	
			save(js,file);
		} catch (Exception e) {
			core.log(e);
			// TODO: handle exception
		}
	}


	/** safe
	@author attilax 老哇的爪子
		@since  o7l hjm$
	
	 * @param o
	 * @param target2
	 */
	public static void save(Object o, String file) {
		// attilax 老哇的爪子  hjm   o7l 
		try {
			createAllPath(file);	
				String js=core.toJsonStrO7(o);
				save(js,file);
			} catch (Exception e) {
				core.log(e);
			 
			}
		
	}
	/**
	 * 
	@author attilax 老哇的爪子
	\t@since  Aug 23, 2014 4:01:15 PM$
	
	 * @param ba
	 * @param file
	 */
	public static void save(byte[] ba, String file) {
		// attilax 老哇的爪子  hjm   o7l 
		try {
			createAllPath(file);	
			InputStream is = new ByteArrayInputStream(ba);
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
		 	OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
			} catch (Exception e) {
				core.log(e);
			 
			}
		
	}
	
	


	/**
	@author attilax 老哇的爪子
	\t@since  Aug 2, 2014 3:49:47 AM$
	
	 * @param string
	 * @throws IOException 
	 */
	public static void append(String fileName, String fileContent,String encode) throws IOException {
		// attilax 老哇的爪子  3:49:47 AM   Aug 2, 2014 
	 
		   
		         
		        File f = new File(fileName);      
		        if (!f.exists())   
		        {       
		            f.createNewFile();      
		        }      
		        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true),encode);      
		        BufferedWriter writer=new BufferedWriter(write);          
		        writer.write(fileContent);      
		       
		        writer.close();     
		     
		 
		
		
	}
	private File file;
	private BufferedWriter writer;
	
	public filex ( String fileName ) throws IOException 
	{
ini(fileName,"utf-8");
	}
	
	public filex ( String fileName,  String encode) throws IOException 
	{
		   ini(fileName, encode);   
	}


	private void ini(String fileName, String encode) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		File f = new File(fileName);      
	        if (!f.exists())   
	        {       
	            f.createNewFile();      
	        }   
		this.file=f;
		  OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true),encode);      
	        this.writer=new BufferedWriter(write);
	}
	/**
	 * high perf
	@author attilax 老哇的爪子
	\t@since  Aug 2, 2014 6:21:30 AM$
	
	 * @param fileName
	 * @param fileContent
	 * @param encode
	 * @throws IOException
	 */
	public   void append_HP(  String fileContent ) throws IOException   {
		// attilax 老哇的爪子  3:49:47 AM   Aug 2, 2014 
	 
		   
		         
		        
		             
		        writer.write(fileContent);      
		      
		     
		 
		
		
	}
	
	public   void append_HP_Safe(  String fileContent )    {
		// attilax 老哇的爪子  3:49:47 AM   Aug 2, 2014 
	 
		   
		         
		        
		             
		        try {
					writer.write(fileContent);
					writer.flush();
				} catch (IOException e) {
					 
					e.printStackTrace();
				} 
	}
	
	public   void appendLine_flush_safe(  String line )    {
		// attilax 老哇的爪子  3:49:47 AM   Aug 2, 2014 
	 
		   
		         
		        
		             
		        try {
					writer.write(line+"\r\n");
					writer.flush();
				} catch (IOException e) {
					 
					e.printStackTrace();
				} 
	}
	public void close() throws IOException
	{
		  writer.close();     
	}
	/**
	 * 
	@author attilax 老哇的爪子
	\t@since  Aug 2, 2014 3:52:45 AM$
	
	 * @param fileName
	 * @param fileContent
	 * @param encode
	 * @throws IOException 
	 */
	public static void append(String fileName, String fileContent ) throws IOException {
		// attilax 老哇的爪子  3:49:47 AM   Aug 2, 2014 
	 
		append(fileName,fileContent,"utf-8");
		
		
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o8o 0_39_j   
	
	 * @param fileName
	 * @param fileContent
	 * @throws IOException
	 */
	public static void appendLine(String fileName, String fileContent ) throws IOException {
		// attilax 老哇的爪子  3:49:47 AM   Aug 2, 2014 
	 
		append(fileName,fileContent+"\r\n","utf-8");
		
		
	}

	/**
	@author attilax 老哇的爪子
	\t@since  Aug 2, 2014 3:55:24 AM$
	
	 * @param fileName
	 * @param string
	 */
//	public static void append(String fileName, String string) {
//		// attilax 老哇的爪子  3:55:24 AM   Aug 2, 2014 
//		
//		{ 
//		 } 
//		
//		
//	}
	
	
	/**
	 * 
	@author attilax 老哇的爪子
	\t@since  Aug 3, 2014 4:07:26 AM$
	
	 * @param path
	 * @param encode
	 * @return
	 * @throws IOException 
	 */
	public static void read_HP(String path, String encode,Closure closure) throws IOException {
		 
		if(path.equals(""))
		{
			System.out.println("--warnging :  path is empty cad");
		//	return new ArrayList<String>();
		}
		InputStreamReader isr   = new InputStreamReader(new FileInputStream(path),encode);
		 
		 BufferedReader reader = new BufferedReader(isr);
		 
		   // BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));  
		    String line = reader.readLine();  
		    while(line!=null){  
		     //   writer.write(line);  
		    	 try {
					closure.execute(line);
				} catch (Exception e) {
					//  attilax 老哇的爪子 4:24:05 AM   Aug 3, 2014   
					e.printStackTrace();
				}
		        line = reader.readLine();  
		       
		      
		      
		    } 
		    reader.close();
		     
	 
	//	return li;
	}


	/**
	@author attilax 老哇的爪子
		@since  o9l 2_b_42   
	
	 * @param files
	 * @return
	 */
	public static String[] getFiles_Spring(String files) {
		// attilax 老哇的爪子  2_b_42   o9l 
		files=files.replaceAll("classpath:", "");
		
	List li=	travDir.getAllFileListByRExpress(pathx.classPath(), files);
		return   (String[]) li.toArray(new String[li.size()]) ;
		
	}
	
	public static List  getFiles_Spring_lst(String files) {
		// attilax 老哇的爪子  2_b_42   o9l 
		files=files.replaceAll("classpath:", "");
		
	List li=	travDir.getAllFileListByRExpress(pathx.classPath(), files);
		return   li;
		
	}

/**
 * %2F
%5C
%3A
*
%3F
%3C
%3E
%22
%7C
 * @param filenameOri
 * @return
 */
	public static String fileNameEncode(String filenameOri) {
		// /\:* <>\"|
		if(filenameOri.equals("."))
			return "%2E";
		if(filenameOri.equals(".."))
			return "%2E%2E";
		Map<String, String> mp = (Map<String, String>) new ClosureNoExcpt() {

			@Override
			public Object execute(final Object arg0) {
				final Map<String, String> mp = new HashMap<String, String>() {
					{
						this.put("*", "%2A");

					}
				};
				final String[] as = strUtil.SplitByNone("/\\:?<>\"|");

				for (final String s : as) {
					try {
						mp.put(s, URLEncoder.encode(s, "utf-8"));

					} catch (final UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return mp;
			}
		}.execute(null);
		String[] as = strUtil.SplitByNone(filenameOri);
		String fname2 = "";
		for (String s : as) {
			fname2 += mp.get(s) == null ? s : mp.get(s);
		}
		return fname2;
	}


	/**
	@author attilax 老哇的爪子
	@since   oaa c_54_43
	 
	 */
public static long getSize(String file) {
	 
	return new File(file).length();
}


		/**
		@author attilax 老哇的爪子
		@since   oad g_s_a
		 
		 */
	public static void file2url(String fileurl) {
		  File file = new File(fileurl);  
      //    Player player = Manager.createPlayer(file.toURI().toURL());  
		
	}


			/**
			@author attilax 老哇的爪子
			@since   oaj c_47_q
			 
			 */
		public static void saveLog(Exception e, String dir) {
		filex.save_SF(core.getTrace(e), dir+"\\"+filex.getUUidName()+".txt");
			
		}


				/**
				@author attilax 老哇的爪子
				@since   oaj e_1_49
				 
				 */
			public static void saveLog(String string, String dir) {
				filex.save_SF(string, dir+"\\"+filex.getUUidName()+".txt");
				
			}


					/**
					@author attilax 老哇的爪子
					@since   oap a_f_46
					 
					 */
				public static void del(String string) {
					try {
						 File f=new File(string);
						 f.delete();
					} catch (Exception e) {
						 filex.saveLog(e, "c:\\e");
					}
					
					
				}


						/**
						@author attilax 老哇的爪子
						@since   oap b_6_9
						 
						 */
					public static void saveObj(Object map, String dir) {
						try {
							String f=dir+"\\"+filex.getUUidName()+".obj";
							  FileOutputStream fos = new FileOutputStream(f);

		                        ObjectOutputStream oos = new ObjectOutputStream(fos);

		                      //  System.out.println(" 1> " + cat.getName());

		                     //   cat.setName("My Cat");                       

		                        oos.writeObject(map);
						} catch (Exception e) {
							 saveLog(e, "c:\\e");
						}
						
						//filex.save_SF(string, dir+"\\"+filex.getUUidName()+".txt");
					}

					public static Object readObj(  String f) {
						try {
							  FileInputStream fis = new FileInputStream(f);

			                     ObjectInputStream ois = new ObjectInputStream(fis);

			                    return ois.readObject();
						} catch (Exception e) {
							 saveLog(e, "c:\\e");
							 throw new RuntimeException(e.getMessage(),e);
						}
						 
						
						//filex.save_SF(string, dir+"\\"+filex.getUUidName()+".txt");
					}


						/**
						@author attilax 老哇的爪子
						@since   ob3 j_0_42
						 
						 */
					public static void saveLog(Exception e, String dir,
							String enc) {
						try {
						String filepath = dir+"\\"+filex.getUUidName()+"_"+enc+".txt";
					
							createAllPath(dir);
							fileC0.writeFile(filepath, core.getTrace(e), enc );
						} catch (Exception e1) {
							core.log("createAllPath err:"+dir);
					 
						
						} 
					//	filex.save_SF(core.getTrace(e), filepath);
						
						
					}
					
				   
 
		

}
