/**
 * 
 */
package com.attilax.lang;

/**
 * @author ASIMO
 *
 */
public class Pdf2htmlEXUtil {
	 
    /**
     * 调用pdf2htmlEX将pdf文件转换为html文件
     * @param command 调用exe的字符串
     * @param pdfName 需要转换的pdf文件名称
     * @param htmlName 生成的html文件名称
     * @return
     */
    public static boolean pdf2html(String command,String pdfName,String htmlName){
        Runtime rt = Runtime.getRuntime();
        try {
            Process p = rt.exec(command);
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");             
              // kick off stderr 
            errorGobbler.start(); 
            StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT"); 
              // kick off stdout 
            outGobbler.start();
            int w = p.waitFor();
            System.out.println(w);
            int v = p.exitValue();
            System.out.println(v);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
     //C:\pdf2htmlEX-0.12-win32-static-with-poppler-data\pdf2htmlEX.exe C:\00\关于优化研发一部组织架构的通知（2015001）.pdf 关于优化研发一部组织架构的通知.html
    public static void main(String[] args) {
    	
        pdf2html("C:\\pdf2htmlEX-0.12-win32-static-with-poppler-data\\pdf2htmlEX.exe C:\\00\\关于优化研发一部组织架构的通知（2015001）.pdf 关于优化研发一部组织架构的通知.html","v.pdf","v2.html");
   System.out.println("..fi");
    }
}