package com.focustar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.focustar.entity.weixin.message.request.TextMessage;

public class FileTextUitl {

	private static String path = Constant.path + "\\imei\\";
	private static String filenameTemp;

	/**
	 * 创建文件
	 * 
	 * @throws IOException
	 */
	public static boolean creatTxtFile() throws IOException {
		boolean flag = false;
		// 根据日期确实文件名称
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		filenameTemp = path + sdf.format(currentDate) + ".txt";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return flag;
	}

	/**
	 * 写文件
	 * 
	 * @param newStr
	 *            新内容
	 * @throws IOException
	 */
	public static boolean writeTxtFile(TextMessage textMessage) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = "\r\n";
		String temp = "";

		// 根据日期确实文件名称
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		filenameTemp = path + sdf.format(currentDate) + ".txt";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该文件原有的内容
			for (int j = 0; (temp = br.readLine()) != null; j++) {
				buf = buf.append(temp);
				// System.getProperty("line.separator")
				// 行与行之间的分隔符 相当于“\n”
				buf = buf.append(System.getProperty("line.separator"));
			}
			if (textMessage != null) {
				filein = textMessage.getToUserName() + "\r\n";
				buf.append(filein);
				filein = textMessage.getFromUserName() + "\r\n";
				buf.append(filein);
				filein = textMessage.getCreateTime() + "\r\n";
				buf.append(filein);
				filein = textMessage.getMsgType() + "\r\n";
				buf.append(filein);
				filein = textMessage.getFuncFlag() + "\r\n";
				buf.append(filein);
				filein = textMessage.getMsgId() + "\r\n";
				buf.append(filein);
				filein = textMessage.getImei() + "\r\n";
				buf.append(filein);
				filein = textMessage.getBuyDate() + "\r\n";
				buf.append(filein);
				filein = textMessage.getReturnDate() + "\r\n";
				buf.append(filein);
			}

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}
}
