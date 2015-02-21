/**
 * @author attilax 老哇的爪子
	@since  2014年5月11日 下午5:10:51$
	
	com.attilax.qrcode.qrcodex
 */
package com.attilax.qrcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.attilax.util.tryX;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @author  attilax 老哇的爪子
 *@since  2014年5月11日 下午5:10:51$
 */
public class qrcodex {

	/**
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午5:10:51$
	
	 */
	public qrcodex() {
		// TODO Auto-generated constructor stub
	}

	//  attilax 老哇的爪子 下午5:10:51   2014年5月11日   

	/**
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午5:10:51$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午5:10:51   2014年5月11日 
	//	gene("aa","100","100","c:\\qrcode.png");
		
		
		  final String content = "aa餐巾纸a";
		  final   String path = "C:/餐巾纸.jpg";
		     
		     final int width = 400;
		     final	int height = 401;
		     
				gene(content, path, width, height);
		  
System.out.println("ok");
	}

	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午5:35:54$
	
	 * @param content
	 * @param path
	 * @param width
	 * @param height
	 */
	public static void gene(final String content, final String path,
			final int width, final int height) {
		new tryX<Object>() {

			@Override
			public Object item(Object t) throws Exception {
				// attilax 老哇的爪子  下午5:23:26   2014年5月11日 
				   MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
				     
				     Map hints = new HashMap();
				     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				 
					BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height,hints);
				     File file1 = new File(path );
				     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
				return null;
			}
		}.$("");
	}
	
	
	@Deprecated
	private static void gene(String  code, String strHeight, String strWidth,
			String file) {
		//   OutputStream out 
			OutputStream output = null;
			try {
				output = new FileOutputStream(new File(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}   //  OutputStream out = new FileOutputStream(f);

			try {
				if (strHeight != null && !"".equals(strHeight) && strWidth != null
						&& !"".equals(strWidth)) {
					BarCodeUtil.makeBarCoder(code, Integer.parseInt(strHeight),
							Integer.parseInt(strWidth), output);
				} else {
					BarCodeUtil.makeBarCoder(code, output);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				output.flush();output.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}

//  attilax 老哇的爪子