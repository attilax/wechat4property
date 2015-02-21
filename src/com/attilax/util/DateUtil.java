package com.attilax.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import net.sf.json.JSONObject;

import com.attilax.core;
import com.attilax.collection.NoHandlerCanProcessReqExcpt;
import com.attilax.collection.handleChain;
import com.attilax.io.filex;
import com.attilax.text.strUtil;
import com.attilax.time.Handler;
import com.attilax.time.HandlerBase;
import com.attilax.time.timeUtil;

import  m.datepkg.dateUtil_o16;
import static m.datepkg.dateUtil_o16.*;

/**
 * should use timeUtil
 * com.attilax.util.DateUtil
 * @author  attilax 老哇的爪子
 *@since  o08 j_o_38$
 */
@Deprecated 
public final class DateUtil extends dateUtil_o16  {

	private DateUtil() {
		//todox declare multi var..
		int i=3,j=4;
	}
	
	public static final String fullDateSdf = "yyyy-MM-dd HH:mm:ss";
	private static final String usFmt = "MM/dd/yyyy HH:mm:ss";
	private static final String dateSdf = "yyyy-MM-dd";
	/**
	 * muliti 
	 * @param source
	 * @param includeTime
	 * @return
	 * @throws ParseException 
	 */
	public static Date str2dateX(final String source, final boolean includeTime) throws ParseException {

		Handler bingo_handler =  (Handler) core.new_cglib(Handler.class);
		List li=new ArrayList();
		Date d;
		try {
			d = (Date) handleChain.handleReq(li, new HandlerBase(
					"ConcreteHandler cn prcsr") {

				@Override
				public Object handleReq(Object arg) throws ParseException   {

					return str2date_excpt(source, includeTime);
				}

			 
			},

			new HandlerBase("ConcreteHandler us prcsr") {
				@Override
				public Object handleReq(Object arg) throws ParseException {

					return str2date_excpt(source, includeTime, usFmt);
				}
			});
		} catch (NoHandlerCanProcessReqExcpt e) {
		 throw new ParseException(source, 0);
		}
		Handler bg=(Handler) li.get(0);
		System.out.println("---bingo handerl:"+bg.getName());
		return d;
	}

public static Date str2date(String source,boolean includeTime,String fmt){
		
		Date date = null;
		SimpleDateFormat sdf = null;
		if(includeTime){
			sdf = new SimpleDateFormat(fmt);
		}else{
			sdf = new SimpleDateFormat(dateSdf);
		}
		try{
			date =  sdf.parse(source);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
/**
 * 
 * @param source
 * @param includeTime
 * @return
 * @throws ParseException 
 */
public static Date str2date_excpt(String source,boolean includeTime) throws ParseException{
	
	Date date = null;
	SimpleDateFormat sdf = null;
	if(includeTime){
		sdf = new SimpleDateFormat(fullDateSdf);
	}else{
		sdf = new SimpleDateFormat(dateSdf);
	}
	if(curUsedTimeZone.get()!=null)
		sdf.setTimeZone(curUsedTimeZone.get());
	 
	date =  sdf.parse(source);
	 
	return date;
}
/**
 * 
 * @param source
 * @param includeTime
 * @return
 * @throws ParseException
 */
public static Date str2date_excpt(String source,boolean includeTime,String datafmt) throws ParseException{
	
	Date date = null;
	SimpleDateFormat sdf = null;
	if(includeTime){
		sdf = new SimpleDateFormat(datafmt);
	}else{
		sdf = new SimpleDateFormat(datafmt);
	}
	 
		date =  sdf.parse(source);
	 
	return date;
}

	public static Date str2date(String source,boolean includeTime){
		
		Date date = null;
		SimpleDateFormat sdf = null;
		if(includeTime){
			sdf = new SimpleDateFormat(fullDateSdf);
		}else{
			sdf = new SimpleDateFormat(dateSdf);
		}
		try{
			date =  sdf.parse(source);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	public static String date2str(Date d,String fmt_str){
		SimpleDateFormat sdf = null;
		//String fullDateSdf = "yyyy-MM-dd HH:";
			sdf = new SimpleDateFormat(fmt_str);
		 
		return sdf.format(d);
	}
	
	
	public static String date2str(Date d,boolean includeTime){
		SimpleDateFormat sdf = null;
		if(includeTime){
			sdf = new SimpleDateFormat(fullDateSdf);
		}else{
			sdf = new SimpleDateFormat(dateSdf);
		}
		return sdf.format(d);
	}
	/**
	 * not include time
	@author attilax 老哇的爪子
		@since  2014-4-30 下午12:48:42$
	
	 * @param d
	 * @return
	 */
	public static String date2str(Date d){
		return date2str(d,false);
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o9n k_f_v   
	
	 * @param d
	 * @return
	 */
	public static String date2str_wzTime(Date d){
		return date2str(d,true);
	}
	
	public static String date2str_wzTime(Timestamp ts){
		
		try {
			Date d=ts;
			return date2str(d,true);
		} catch (Exception e) {
			core.err(e);
			return "";
		}
		
	}
	/**
	 *  
	@author attilax 老哇的爪子
		@since  2014-5-12 下午04:27:23$
	
	 * @return  2014-05-12    fmt
	 */
	public static String getTodayStr(){
		return date2str(new Date());
	}
	
	public static Date getYesterday(){
	  return getYesterday(null);
	}
	
	public static Date getYesterday(Date startAt){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.DAY_OF_MONTH, -1);
	  return cal.getTime();
	}
	
	public static String getYesterdayStr(){
		return date2str(getYesterday());
	}
	
	public static String getYesterdayStr(Date startAt){
		return date2str(getYesterday(startAt));
	}
	
	
	public static boolean isSameDay(Date d1,Date d2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);
		return cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取两个时间相差的分钟数
	 * @param start	开始时间
	 * @param end	结束时间
	 * @return		分钟数
	 */
	public static int getMinuteInterval(Date start,Date end){
		try {
			long result=end.getTime()-start.getTime();
			result = result / (1000 * 60);//分钟数
			return (int) result;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	public static int getMinuteInterval(String start,Date end){
		return getMinuteInterval(str2date(start,true),end);
	}
	
	public static int getMinuteInterval(String start,String end){
		return getMinuteInterval(str2date(start,true),str2date(end,true));
	}
	
	public static int getSecondInterval(Date start,Date end){
		try{
			return (int) (end.getTime()-start.getTime()) / 1000;
		}catch(Exception e){
			return 0;
		}
	}
	
	public static int getSecondInterval(String start,Date end){
		return getSecondInterval(str2date(start,true),end);
	}
	
	public static int getSecondInterval(String start,String end){
		return getSecondInterval(str2date(start,true),str2date(end,true));
	}
	
	public static long getMillisInterval(Date start,Date end){
		try{
			long result = end.getTime()-start.getTime();
			return (int) result;
		}catch(Exception e){
			return 0;
		}
	}
	
	public static long getMillisInterval(String start,Date end){
		return getMillisInterval(str2date(start,true),end);
	}
	
	public static long getMillisInterval(String start,String end){
		return getMillisInterval(str2date(start,true),str2date(end,true));
	}
	
	/**
	 * 获取两个时间相差的天数
	 * @param start	开始时间
	 * @param end	结束时间
	 * @return		天数
	 */
	public static int getDayInterval(Date start,Date end){
		int minute = getMinuteInterval(start,end);
		if(minute>0){
			return minute/1440;
		}
		return 0;
	}
	
	public static String getFirstDayOfPreXWeek(int x){
		return getFirstDayOfPreXWeek(null,x);
	}
	
	public static String getFirstDayOfPreXWeek(Date startAt,int x){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.WEEK_OF_MONTH, (Math.abs(x)-1)*-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return date2str(cal.getTime());
	}
	
	public static String getLastDayOfPreXWeek(int x){
		return getLastDayOfPreXWeek(null,x);
	}
	
	public static String getLastDayOfPreXWeek(Date startAt,int x){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.WEEK_OF_MONTH, (Math.abs(x)-1)*-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return date2str(cal.getTime());
	}
	
	public static String getFirstDayOfLastWeek() {
		return getFirstDayOfPreXWeek(1);
	}
	
	public static String getFirstDayOfLastWeek(Date startAt) {
		return getFirstDayOfPreXWeek(startAt,1);
	}
	
	public static String getLastDayOfLastWeek() {
		return getLastDayOfPreXWeek(1);
	}
	
	public static String getLastDayOfLastWeek(Date startAt) {
		return getLastDayOfPreXWeek(startAt,1);
	}
	
	public static String getFirstDayOfWeek() {
		return getFirstDayOfPreXWeek(0);
	}
	
	public static String getFirstDayOfWeek(Date startAt) {
		return getFirstDayOfPreXWeek(startAt,0);
	}
	
	public static String getLastDayOfWeek() {
		return getLastDayOfPreXWeek(0);
	}
	
	public static String getLastDayOfWeek(Date startAt) {
		return getLastDayOfPreXWeek(startAt,0);
	}
	
	public static String getFirstDayOfMonth() {
		return getFirstDayOfPreXMonth(0);
	}
	
	public static String getFirstDayOfMonth(Date startAt) {
		return getFirstDayOfPreXMonth(startAt,0);
	}
	
	public static String getLastDayOfMonth() {
		return getLastDayOfPreXMonth(0);
	}
	
	public static String getLastDayOfMonth(Date startAt) {
		return getLastDayOfPreXMonth(startAt,0);
	}
	
	public static String getFirstDayOfLastMonth(){
		return getFirstDayOfPreXMonth(1);
	}
	
	public static String getFirstDayOfLastMonth(Date startAt){
		return getFirstDayOfPreXMonth(startAt,1);
	}
	
	
	public static String getLastDayOfLastMonth() {
		return getLastDayOfPreXMonth(1);
	}
	
	public static String getLastDayOfLastMonth(Date startAt) {
		return getLastDayOfPreXMonth(startAt,1);
	}
	
	public static String getFirstDayOfPreXMonth(int x){
		return getFirstDayOfPreXMonth(null,x);
	}
	
	public static String getFirstDayOfPreXMonth(Date startAt,int x){
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		cal.add(Calendar.MONTH, Math.abs(x)*-1);
		cal.set(Calendar.DATE, 1);// 设为当前月的1号
		return date2str(cal.getTime());
	}
	
	public static String getLastDayOfPreXMonth(int x) {
		return getLastDayOfPreXMonth(null,x);
	}
	
	public static String getLastDayOfPreXMonth(Date startAt,int x) {
		Calendar cal = Calendar.getInstance();
		if(startAt!=null) cal.setTime(startAt);
		if(x>1) cal.add(Calendar.MONTH, Math.abs(x-1)*-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		if(x==0) cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return date2str(cal.getTime());
	}
	
	
	public static int getWeekOfMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	 
	public static String getPreXMonth(int x){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, x*-1);
		return date2str(cal.getTime());
	}
	
	public static String getPreDay(int x,boolean includeTime){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, x*-1);
		return date2str(cal.getTime(),includeTime);
	}
	
	public static void main(String[] args)      {
		
		
		
//		System.out.println(timestamp());
//		System.out.println(System.currentTimeMillis());
//		Date date = DateUtil.str2date("2013-01-11", false);
//		System.out.println("Yesterday:"+DateUtil.getYesterdayStr(date));
//		System.out.println("LastWeek Range:"+DateUtil.getFirstDayOfLastWeek(date) +" - "+ DateUtil.getLastDayOfLastWeek(date));
//		System.out.println("ThisWeek Range:"+DateUtil.getFirstDayOfWeek(date) +" - "+ DateUtil.getLastDayOfWeek(date));
//		System.out.println("LastMonth Range:"+DateUtil.getFirstDayOfLastMonth(date)+" - "+DateUtil.getLastDayOfLastMonth(date));
//		System.out.println("ThisMonth Range:"+DateUtil.getFirstDayOfMonth(date)+" - "+DateUtil.getLastDayOfMonth(date));
//		
//		System.out.println( getTodayStr());
//		 System.out.println("============");
		// System.out.println(DateUtil.str2dateX("2013-01-11 01:02:03", true));
//		 try {
//			System.out.println(DateUtil.str2dateX("20a/13/2013 01:02:03", true));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
		 
//		 JSONObject jo=JSONObject.fromObject(filex.read("c:\\t.txt" ));		
//		 
//		 System.out.println(jsonObj2Str(jo));
		
		Date dt;
		try {
			DateUtil.curUsedTimeZone.set(CstTimeZone());
			dt = str2dateX("2014-1-1 12:12:12", true);   //1388549532000
			
			//1388549532000
			System.out.println(dt.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

		/**
		@author attilax 老哇的爪子
		@since   oap f_g_p
		 
		 */
	public static TimeZone CstTimeZone() {
		 
		return TimeZone.getTimeZone("GMT+8");
	}
	
	private static TimeZone AtTimeZone() {
		 
		return TimeZone.getTimeZone("GMT-7");
	}

	/**
	 * get java timestamp
	 * o40
	 * @return
	 */
	public static long timestamp() {
		String startTime="2014-01-01 00:00:01";
		Date startDate=str2date(startTime, true);
	long span=	getMillisInterval(startDate,new Date());
		
		
		return span;
	}
	
	/**
	 * get java timestamp
	 * o40
	 * @return
	 */
	public static String timestamp_strFmt() {
		 
		
		
		return String.valueOf(timestamp());
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-4-30 下午12:50:19$
	
	 * @return
	 */
	public static Date today_notime() {
		// 下午12:50:19   2014-4-30 
		String dt_s=DateUtil.today();
		Date dt=DateUtil.str2date(dt_s, false);
		return dt;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 下午04:39:49$
	
	 * @return
	 */
	public static String today_Start() {
		// attilax 老哇的爪子  下午04:39:49   2014-5-12 
		return  DateUtil.getTodayStr()+" 00:00:00";
	}

	/**
	@author attilax 老哇的爪子
		@since  o7f mk9$
	
	 * @param value
	 * @param b
	 * @return
	 * @throws ParseException 
	 */
	public static String toStandFmtYYYYmmDD_hhmmss(Object value, boolean b) throws ParseException {
		// attilax 老哇的爪子  mk9   o7f 
		Date dt=str2dateX(value.toString(), b);
		return date2str(dt,true);
		 
		
	}
	
	public static String toStandFmtYYYYmmDD_hhmmss_byDate(Object value ) throws ParseException {
		// attilax 老哇的爪子  mk9   o7f 
	 Date dt=(Date) value;
		return date2str(dt,true);
		 
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o7f mq52$
	
	 * @param value   YYYYmmDD_hhmmss or mm/dd/year hhmmss  or Date
	 * @param b
	 * @return
	 * @throws ParseException 
	 */
	public static Timestamp toTimeStamp(Object value, boolean b) throws ParseException {
		// attilax 老哇的爪子  mq52   o7f 
		if (value instanceof Date) {
			Date dt = (Date) value;
			return new Timestamp(dt.getTime());
		}
		// as flow str fmt
		Date dt = str2dateX(value.toString(), b);
		return new Timestamp(dt.getTime());
		
	}
	/**
	 * 
	@author attilax 老哇的爪子
	\t@since  Aug 19, 2014 6:38:58 AM$
	
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp toTimeStamp()   {
		Date value=new Date();
		// attilax 老哇的爪子  mq52   o7f 
		if (value instanceof Date) {
			Date dt = (Date) value;
			return new Timestamp(dt.getTime());
		}
		// as flow str fmt
		Date dt = null;
		try {
			dt = str2dateX(value.toString(), true);
		} catch (ParseException e) {
			//  attilax 老哇的爪子 6:42:20 AM   Aug 19, 2014   
			e.printStackTrace();
		}
		return new Timestamp(dt.getTime());
		
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
	\t@since  Aug 19, 2014 6:38:58 AM$
	
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp toTimeStamp_NS() throws ParseException {
		Date value=new Date();
		// attilax 老哇的爪子  mq52   o7f 
		if (value instanceof Date) {
			Date dt = (Date) value;
			return new Timestamp(dt.getTime());
		}
		// as flow str fmt
		Date dt = str2dateX(value.toString(), true);
		return new Timestamp(dt.getTime());
		
	}

	/**
	 * curr time
	@author attilax 老哇的爪子
		@since  o7g V0e$
	
	 * @return
	 */
	public static Timestamp timestampO7() {
		// attilax 老哇的爪子  V0e   o7g 
		return  new Timestamp(System.currentTimeMillis()); 
	 
		
	}
/**
 * 
@author attilax 老哇的爪子
	@since  o08 2_y_42   

 * @return
 */
	public static Timestamp timestampO9(String strFmt) {
		// attilax 老哇的爪子  V0e   o7g 
		try {
			Date d=str2date(strFmt, true);
			 
			return   timeUtil.date2timestamp(d);
		} catch (Exception e) {
			core.err(strUtil.concat("--str:",strFmt),e);
			return timestampO7();
		}
		
	 
		
	}
public static 	ThreadLocal<TimeZone> curUsedTimeZone=new ThreadLocal<TimeZone>();
	/**
	@author attilax 老哇的爪子
		@since  o7k j42i$
	
	 * @param date
	 * @return
	 */
	public static String toStandFmtYYYYmmDD_hhmmss_byDate_CST(Date d) {
		// attilax 老哇的爪子  j42i   o7k 
		SimpleDateFormat sdf = null;
	 
			sdf = new SimpleDateFormat(fullDateSdf);
	 sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(d);
	//	DateUtil.toStandFmtYYYYmmDD_hhmmss_byDate(date)
		
	}

	public static Date getDateFrmNet()  {
		URL url;
		   URLConnection uc = null;
		try {
			url = new URL("http://www.bjtime.cn");
			uc = url.openConnection();
			 
			uc.connect();
		} catch ( Exception e1) {
			//  attilax 老哇的爪子 l4737   o7k   
			 throw new RuntimeException(e1);
		}//取得资源对象  
     
		 //发出连接  
        long ld=uc.getDate(); //取得网站日期时间（时间戳）  
        Date date=new Date(ld); //转换为标准时间对象  
		return date;
	}

	public static String getDatetime(Date d) {
		// attilax 老哇的爪子  j42i   o7k 
		SimpleDateFormat sdf = null;
	 
			sdf = new SimpleDateFormat(fullDateSdf);
	 
		return sdf.format(d);
	//	DateUtil.toStandFmtYYYYmmDD_hhmmss_byDate(date)
		
	}
	
	public static String getDatetime( ) {
		return getDatetime(new Date());
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o01 3_57_r   
	
	 * @param x
	 * @return
	 */
	public static String jsonObj2Str(Object x) {
		if(x==null) return "";
		// attilax 老哇的爪子  3_57_r   o01 
		try {
			JSONObject jo=(JSONObject) x;
			long timeStamp=jo.getLong("time");
			 Date d=long2Date(timeStamp);
			return date2str_wzTime(d);
		} catch (Exception e) {
			core.warn(e);
			return "";
		}
		
		
	}
	
	
	public static String timeStamp2Str(JSONObject x) {
		if(x==null) return "";
		// attilax 老哇的爪子  3_57_r   o01 
		try {
			JSONObject jo=(JSONObject) x;
			long timeStamp=jo.getLong("time");
			 Date d=long2Date(timeStamp);
			return date2str_wzTime(d);
		} catch (Exception e) {
			core.warn(e);
			return "";
		}
		
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o01 4_0_h   
	
	 * @param timeStamp
	 * @return
	 */
	private static Date long2Date(long timeStamp) {
		// attilax 老哇的爪子  4_0_h   o01 
		 
		    return new Date(timeStamp); 
		 
		
	}
}
