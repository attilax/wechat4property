package com.attilax.time;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.attilax.core;
import com.attilax.util.DateUtil;

public class timeUtil  {

	public static void main(String[] args) throws ParseException {
		
	String timestamp_strFmt = timeUtil.getTimestamp_strFmt();
	timestamp_strFmt=timestamp_strFmt.substring(0, 10);
	System.out.println(timestamp_strFmt);
	System.out.println(timestamp_strFmt.length());	;
		System.out.println("bbcc");
	
		// TODO Auto-generated method stub
//		String s = "00:01:09";
//		int sec = str2secs(s);
//		System.out.println(sec);
//		sec = 79; // 00:01:19
//		System.out.println(secs2str(sec));
	//	System.out.println(getNowTime_NID_secFmt(TimeZone.getTimeZone("GMT+8")));
		System.out.println( isOvertime("2015-01-05"));
		System.out.println(	timeUtil. isOvertime("2022-01-05"));
		
	//	 sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}

		/**
		@author attilax 老哇的爪子
		@since   obl d_40_46
		 
		 */
	public static boolean isOvertime(String date_s) {
	    Date d= DateUtil.str2date(date_s, false);
	    if(d.getTime()<new Date().getTime())return true;
		return false;
	}

	/**
	@author attilax 老哇的爪子
		@since  o00 i_56_e   
	
	 * @param timeZone
	 * @return
	 */


	public static String secs2str(int sec) throws ParseException {
		String date = "2014-01-01";
		Date start = DateUtil.str2date_excpt(date + " 00:00:00", true);
		long endTimestamp = sec * 1000 + start.getTime();
		Date end = new Date(endTimestamp);
		String s = DateUtil.date2str(end, true);
		String[] sa = s.split(" ");
		return sa[1];
	}
	
	public static String secs2str_SF(int sec)  {
		try{
		String date = "2014-01-01";
		Date start = null;
		try {
			start = DateUtil.str2date_excpt(date + " 00:00:00", true);
		} catch (ParseException e) {
			//  attilax ���۵�צ�� 8:34:30 AM   Aug 24, 2014   
			core.log(e);
		}
		long endTimestamp = sec * 1000 + start.getTime();
		Date end = new Date(endTimestamp);
		String s = DateUtil.date2str(end, true);
		String[] sa = s.split(" ");
		return sa[1];
		}catch(Exception e)
		{	core.log(e);
			return "00:00:00";
		}
	}

	public static int str2secs(Object s) throws ParseException {
		String date = "2014-01-01";
		String full = date + " " + s;
		Date dt = DateUtil.str2date_excpt(full, true);
		Date dt2 = DateUtil.str2date_excpt(date + " 00:00:00", true);
		int i = DateUtil.getSecondInterval(dt2, dt);

		return i;
	}

	/**
	@author attilax 老哇的爪子
		@since  o8r h_52_m   
	
	 * @return
	 */
	public static Timestamp getTimestamp() {
		// attilax 老哇的爪子  h_52_m   o8r 
		return core.toTimeStamp(new Date());
		
	}
	
	public static Timestamp date2timestamp(Date dt) {
		// attilax 老哇的爪子  h_52_m   o8r 
		return core.toTimeStamp(dt);
		
	}
	private static final String onlyTimeTemplet = "HH:mm:ss";
	/**
	@author attilax 老哇的爪子
		@since  o00 i_e_50   
	
	 * @return
	 */
	public static long getTime_NID_secFmt(Date d) {
		// attilax 老哇的爪子  i_e_50   o00 
	String  time=geTime_NotIncluDate_secFmt(d);
		 
		try {
			return  str2secs(time);
		} catch (ParseException e) {
			//  attilax 老哇的爪子 i_m_i   o00   
			e.printStackTrace();
			throw new RuntimeException("ParseException:"+time,e);
		}
	 
		
	}
	
	private static long getNowTime_NID_secFmt(TimeZone timeZone) {
		// attilax 老哇的爪子  i_56_e   o00 
		return  getTime_NID_secFmt(new Date(),timeZone);
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o00 i_59_a   
	
	 * @param date
	 * @param timeZone
	 * @return
	 */
	private static long getTime_NID_secFmt(Date date, TimeZone timeZone) {
		// attilax 老哇的爪子  i_59_a   o00 
		String  time=geTime_NotIncluDate_secFmt(date,timeZone);
		 
		try {
			return  str2secs(time);
		} catch (ParseException e) {
			//  attilax 老哇的爪子 i_m_i   o00   
			e.printStackTrace();
			throw new RuntimeException("ParseException:"+time,e);
		}
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o00 i_59_44   
	
	 * @param date
	 * @param timeZone
	 * @return
	 */
	private static String geTime_NotIncluDate_secFmt(Date date, TimeZone timeZone) {
		// attilax 老哇的爪子  i_59_44   o00 
		SimpleDateFormat sdf = null;
		 
		sdf = new SimpleDateFormat(onlyTimeTemplet);
	 sdf.setTimeZone(timeZone);
		return sdf.format(date);
		
	}

	public static long getNowTime_NID_secFmt() {
		// attilax 老哇的爪子  i_e_50   o00 
	 
		 
		return  getTime_NID_secFmt(new Date());
	 
		
	}
	
	
	public static String getNowTime_NotIncluDate() {
		// attilax 老哇的爪子  i_e_50   o00 
		 
		 
		return geTime_NotIncluDate_secFmt(new Date());
	 
		
	}
	
	public static String geTime_NotIncluDate_secFmt(Date d) {
		// attilax 老哇的爪子  i_e_50   o00 
		SimpleDateFormat sdf = null;
	 
			sdf = new SimpleDateFormat(onlyTimeTemplet);
		 
		return sdf.format(d);
	 
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o00 j_4_l   
	
	 * @return
	 */
	public static long getNow_timeonly_secFmt_CST() {
		// attilax 老哇的爪子  j_4_l   o00 
		
		return getNowTime_NID_secFmt(TimeZone.getTimeZone("GMT+8"));
		
	}

	public static String Now_CST() {
		// attilax 老哇的爪子  j_4_l   o00 
		SimpleDateFormat sdf = null;
	//	if(includeTime){
			sdf = new SimpleDateFormat( DateUtil. fullDateSdf);
			sdf.setTimeZone(DateUtil.CstTimeZone());
//		}else{
//			sdf = new SimpleDateFormat(dateSdf);
//		}
		return sdf.format(new Date());
	//	return DateUtil.date2str_wzTime(new Date());
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o00 j_9_42   
	
	 * @param string
	 * @return
	 */
	public static long str2secs_RE(String string) {
		// attilax 老哇的爪子  j_9_42   o00 
		try {
			return timeUtil.str2secs(string);
		} catch (ParseException e) {
			//  attilax 老哇的爪子 j_0_5   o00   
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

		/**
		@author attilax 老哇的爪子
		 * @throws ParseException 
		@since   ob9 h_k_8
		 
		 */
	public static Timestamp getTimestamp(String time_str) throws ParseException {
		Timestamp ts1;
		Timestamp ts2;
		 
	 
			ts1 = DateUtil.toTimeStamp(time_str, true);
		return ts1;
	}

			/**
			@author attilax 老哇的爪子
			@since   p25 f_45_z
			 
			 */
		public static String getTimestamp_strFmt() {
			// TODO Auto-generated method stub
			return String.valueOf(System.currentTimeMillis()) ;
		}



}
