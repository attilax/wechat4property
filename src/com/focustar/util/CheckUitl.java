package com.focustar.util;

import java.util.regex.Pattern;

public class CheckUitl {

	public static void main(String[] args) {
		CheckUitl test = new CheckUitl();
	}

	/**
	 * 日期合法性判断程序
	 * 
	 * @param date
	 *            日期字符串，格式:20140101
	 * @return TRUE 正确 FLASE 错误
	 */
	public static boolean checkDate(String date) {
		if (date.length() != 8)
			return false;

		Pattern pattern = Pattern.compile("[0-9]*");
		boolean isNumber = pattern.matcher(date).matches();
		if (!isNumber)
			return false;

		String month = date.substring(4, 6);
		if (Integer.valueOf(month) > 12 || Integer.valueOf(month) < 1)
			return false;

		String day = date.substring(6, 8);
		if (Integer.valueOf(day) > 31 || Integer.valueOf(day) < 1)
			return false;
		return true;
	}

}
