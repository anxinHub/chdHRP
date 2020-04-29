package com.chd.base.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;


/** 时间工具类 */
public class DateUtil {

	private static Logger logger = Logger.getLogger(DateUtil.class);

	/** 时间格式 **/
	private static SimpleDateFormat dateTimePattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 根据默认格式，将sTime转换成date类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date stringToDate(String sTime) {
		Date d = null;
		try {
			d = dateTimePattern.parse(sTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 根据默认格式，将sDate(格式:yyyy-MM-dd)转换成java.util.Date类型
	 */
	public static Date stringToDate1(String sDate) {
		Date d = null;
		try {
			d = datePattern.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 根据指定格式sFormat，将sTime转换成date类型
	 */
	public static Date stringToDate(String sTime, String sFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		Date d = null;
		try {
			d = formatter.parse(sTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 根据指定格式sFormat，将sTime转换成date类型
	 */
	public static Date getCurrenDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static Date getCurrenDate() {
		Date d = null;
		try {
			d = dateTimePattern.parse(dateTimePattern.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 根据默认格式，将aDate转换成String类型 yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToString(Date aDate) {
		String returnValue = "";
		if (aDate == null) {
			System.out.println("aDate is null!");
		} else {
			returnValue = dateTimePattern.format(aDate);
		}
		return (returnValue);
	}
	
	/**
	 * 根据默认格式，将aDate转换成String类型 yyyy-MM-dd
	 */
	public static String dateToString1(Date aDate) {
		String returnValue = "";
		if (aDate == null) {
			System.out.println("aDate is null!");
		} else {
			returnValue = datePattern.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 根据指定格式sFormat，将aDate转换成String类型
	 */
	public static String dateToString(Date aDate, String sFormat) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null) {
			System.out.println("aDate is null!");
		} else {
			df = new SimpleDateFormat(sFormat);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	public static String dateFormat(Object date, String sFormat) {
		String returnValue = "";
		try {
			if (date != null) {

				SimpleDateFormat df = new SimpleDateFormat(sFormat);
				if (date instanceof Date) {
					returnValue = df.format(date);
				} else {
					returnValue = df.format(df.parse(date.toString()));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public static String dateFormat1(Object date, String sFormat) throws ParseException {
		String returnValue = "";
		if (date != null) {

			SimpleDateFormat df = new SimpleDateFormat(sFormat);
			if (date instanceof Date) {
				returnValue = df.format(date);
			} else {
				returnValue = df.format(df.parse(date.toString()));
			}
		}
		return returnValue;
	}

	/**
	 * 时间计算，返回String类型的时间 sTime计算的时间 iTimeMold计算的类型（如：时、分、秒、月）
	 * iTime加减量（如减30分：-30,指定计算的类型iTimeMold为分钟Calendar.MINUTE） sFormat返回的时间的格式
	 */
	public static String calculate(String sTime, int iTimeMold, int iTime, String sFormat) {
		Date dTime = null;
		try {
			Date date = new SimpleDateFormat(sFormat).parse(sTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// cal.add(Calendar.MINUTE, + iTime);
			cal.add(iTimeMold, +iTime);
			dTime = cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateToString(dTime, sFormat);
	}

	/**
	 * 获取间隔各个月份集(传入startTime,endTime的格式为(yyyyMMdd),返回集合的格式为yyyyMM)
	 * 
	 * @param startTime
	 * @param endTime
	 * @param entID
	 * @return
	 */
	public static List<String> getMonthList(String startTime, String endTime) {

		String startYear = startTime.substring(0, 4);
		String startMonth = startTime.substring(4, 6);

		String endYear = endTime.substring(0, 4);
		String endMonth = endTime.substring(4, 6);
		int iStartYear = Integer.parseInt(startYear);
		int iStartMonth = Integer.parseInt(startMonth);
		int iEndYear = Integer.parseInt(endYear);
		int iEndMonth = Integer.parseInt(endMonth);

		List<String> monthList = new ArrayList<String>();
		int iNowYear = iStartYear;
		for (int i = iStartYear; i <= iEndYear; i++) {

			if (iNowYear == iStartYear && iNowYear == iEndYear) {
				addMonthToList(iStartMonth, iEndMonth, Integer.toString(iNowYear), monthList);
			} else if (iNowYear == iStartYear) {
				addMonthToList(iStartMonth, 12, Integer.toString(iNowYear), monthList);
			} else if (iNowYear == iEndYear) {
				addMonthToList(1, iEndMonth, Integer.toString(iNowYear), monthList);
			} else {
				addMonthToList(1, 12, Integer.toString(iNowYear), monthList);
			}

			iNowYear = iNowYear + 1;
		}

		return monthList;
	}

	public static void addMonthToList(int iStartMonth, int iEndMonth, String nowYear, List<String> monthList) {
		int iNowMonth = iStartMonth;
		String nowTime = "";
		for (int i = iStartMonth; i <= iEndMonth; i++) {
			nowTime = nowYear + getTwoByteMonth(iNowMonth);
			monthList.add(nowTime);
			iNowMonth = iNowMonth + 1;
		}
	}

	public static String getTwoByteMonth(int month) {
		String returnMonth = Integer.toString(month);
		if (returnMonth.length() < 2) {
			returnMonth = "0" + returnMonth;
		}
		return returnMonth;
	}

	/**
	 * 获取当前年月的一下月
	 * @param year_month 年月 如：197001
	 * @return 年月 如：197002
	 */
	public static String getNextYear_Month(String year_month) {
		String year = year_month.substring(0, 4);
		String month = year_month.substring(4, 6);

		int year_i = Integer.parseInt(year);

		int month_i = Integer.parseInt(month);

		if (Integer.parseInt(month) == 12) {

			month = "01";

			year = String.valueOf(year_i + 1);

		} else {
			if (month_i < 9) {

				month = "0" + String.valueOf(month_i + 1);

			} else {

				month = String.valueOf(month_i + 1);

			}

		}

		return year + month;
	}

	// 获取当前年月的一上月
	public static String getTopYear_Month(String year_month) {
		String year = year_month.substring(0, 4);
		String month = year_month.substring(4, 6);

		int year_i = Integer.parseInt(year);
		int month_i = Integer.parseInt(month);
		
		//如果当前月等于1月,退回到去年12月
		if (Integer.parseInt(month) == 1) {
			month = "12";
			year = String.valueOf(year_i - 1);
		} else {
			if (month_i <= 10) {
				month = "0" + String.valueOf(month_i - 1);
			} else {
				month = String.valueOf(month_i - 1);
			}
		}
		

		/*if (Integer.parseInt(month) == 12) {

			month = "01";

			year = String.valueOf(year_i - 1);

		} else {
			if (month_i <= 10) {

				month = "0" + String.valueOf(month_i - 1);

			} else {

				month = String.valueOf(month_i - 1);

			}

		}*/

		return year + month;
	}

	// 获取当前年月的一下月
	public static String getLastYear_Month(String year_month) {
		String year = year_month.substring(0, 4);
		String month = year_month.substring(4, 6);

		int year_i = Integer.parseInt(year);

		int month_i = Integer.parseInt(month);

		if (Integer.parseInt(month) == 1) {

			month = "12";

			year = String.valueOf(year_i - 1);

		} else {
			if (month_i < 11) {

				month = "0" + String.valueOf(month_i - 1);

			} else {

				month = String.valueOf(month_i - 1);

			}

		}

		return year + month;
	}

	/**
	 * 获取当前年月天的一下月天 例如GetSysDate("yyyy/MM/dd", "2003/01/29", 0, 1, 1)
	 * 
	 * @param format
	 *            :得到的日期格式
	 * @param StrDate
	 *            :计算前的年月日
	 * @param year
	 *            :相加年数
	 * @param month
	 *            :相加月数
	 * @param day
	 *            :下月的日期相加的天数
	 */
	public static String GetSysDate(String format, String StrDate, int year, int month, int day) {

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat sFmt = new SimpleDateFormat(format);

		cal.setTime(sFmt.parse((StrDate), new ParsePosition(0)));

		if (day != 0) {
			cal.add(cal.DATE, day);
		}
		if (month != 0) {
			cal.add(cal.MONTH, month);
		}
		if (year != 0) {
			cal.add(cal.YEAR, year);

		}
		return sFmt.format(cal.getTime());
	}

	/**
	 * 获取输入某月份中的第一天
	 * 
	 * @param year
	 *            :输入的年
	 * @param month
	 *            :输入的月
	 */
	public static String getMinMonthDate(String year, String month) throws ParseException {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		// 设置月份
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("dd");

		String lastDayOfMonth = sdf.format(cal.getTime());

		return lastDayOfMonth;
	}

	/**
	 * 获取月份最后日期
	 * 
	 * @param year
	 *            :输入的年
	 * @param month
	 *            :输入的月
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(String year, String month) throws ParseException {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		// 设置月份
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		// 处理取月份最后一天的小问题有闰年的时候4,6,9,11 最后一天为01
		cal.set(Calendar.DATE, 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("dd");

		String lastDayOfMonth = sdf.format(cal.getTime());

		return lastDayOfMonth;
	}

	/*
	 * public static DateTime ISOToDate(String sTime){ 没测试 所以先注销掉 DateTime dt5 =
	 * new DateTime(sTime); dt5.toDate(); return dt5; }
	 */

	public static String ISOToString(String sTime, String fomat) {

		DateTime dt5 = new DateTime(sTime);

		return dt5.toString(fomat);
	}

	/**
	 * js日期字符串转成java指定格式的日期字符串
	 * 
	 * @param jsDate
	 * @param fomat
	 * @return
	 * @throws ParseException
	 */
	public static String jsDateToString(String jsDate, String fomat) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(fomat, Locale.getDefault());
		if (jsDate.indexOf("GMT") > 0) {
			jsDate = jsDate.replace("GMT", "").replaceAll("\\(.*\\)", "");
			SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z", Locale.ENGLISH);
			Date dateTrans = format.parse(jsDate);
			jsDate = df.format(dateTrans);
		} else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			Date dateTrans = format.parse(jsDate);
			jsDate = df.format(dateTrans);
		}
		return jsDate;
	}

	/**
	 * 判断今天是否-目标日期<=多少天
	 * 
	 * @param time目标时间
	 * @param type距离多少天
	 * @return
	 */
	public static boolean isDiffFromToday(String time, int type) {
		Date today = new Date();
		Date dt = stringToDate(time+" 23:59:59", "yyyy-MM-dd HH:mm:ss");

		long diff = dt.getTime() - today.getTime();
		
		if(type==0){
			if(diff<0){
				return true;
			}else{
				return false;
			}
		}
		
		if (diff < 0){
			diff = 0;
		}
		long days = diff / (1000 * 60 * 60 * 24);

		if (days >= 0 && days <= type) {
			return true;
		}

		return false;

	}
	
	public static String getPreData(String year_month) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		Date date = sdf.parse(year_month);

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.MONTH, -1); // 得到前一个月

		return sdf.format(cal.getTime());
	}

	// 两个时间比较大小，d1大于d2返回true否则相反
	public static boolean compareDate(Date d1, Date d2) {
		if (d1.getTime() > d2.getTime()) {
			return true;
		} else if (d1.getTime() < d2.getTime()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取时间差 精确到 毫秒
	 * 
	 * @param prm_begin
	 *            开始时间
	 * @param prm_end
	 *            结束时间
	 * @param format
	 *            精确 匹配 格式 day:精确到天 hour:精确到小时 min:精确到分钟 s:精确到秒 ms:精确到毫秒</br>
	 *            默认 到天
	 * @return
	 */
	public static long getTimeDifference(String prm_begin, String prm_end, String format) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		long between = 0;

		try {
			Date begin = dfs.parse(prm_begin);
			Date end = dfs.parse(prm_end);
			between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		long day = between / (24 * 60 * 60 * 1000);
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);

		System.out.println(s);
		if (format.equals("day")) {
			return day;
		} else if (format.equals("hour")) {
			return hour;
		} else if (format.equals("s")) {
			return s;
		} else if (format.equals("ms")) {
			return ms;
		} else {
			return day;
		}

	}

	/**
	 * 获取系统日期 格式：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getSysDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		return df.format(new Date());
	}
	/**
	 * 获取系统时间 格式：yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return
	 */
	public static String getSysTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 设置日期格式
		return df.format(new Date());
	}
	
	
	//获取当前星期几
	public static int getCurWeek(Date date) {
		
		//String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(w<=0)w=7;
		return w;
//		if (w < 0)
//			w = 0;
//		return weekDays[w];
	}
	
	
	//根据字符串日期+数量=最后的时间
	public static long  getAddDate(String dateStr,long day) {
		Date date=DateUtil.stringToDate(dateStr);
		long time = date.getTime();
		day = day * 24 * 60 * 60 * 1000;
		time += day;
		return new Date(time).getTime();
	}

	public static String getAfterDate(String dateStr,long day){
		long s=getAddDate(dateStr,day);
		String d=dateToString(new Date(s),"yyyy-MM-dd");
		return d;
	}
	
	/**
	 * 校验日期是否符合yyyy-MM-dd格式
	 * @param args
	 * @return
	 */
	 public static boolean CheckDate (String args){

	        String str = args;

	        String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher m = pattern.matcher(str);
	        boolean dateFlag = m.matches();
	        if (!dateFlag) {
	            System.out.println("格式错误");
	            return dateFlag ;
	        }
			return dateFlag;
	      
	    }
	
	 /**
	 * 校验日期是否符合yyyyMM格式
	 * @param args
	 * @return
	 */
	 public static boolean CheckDateYearMonth (String args){

        String str = args;

        String regex = "[0-9]{4}[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        boolean dateFlag = m.matches();
        if (!dateFlag) {
            System.out.println("格式错误");
            return dateFlag ;
        }
		return dateFlag;
      
    }
	 /**
		 * 校验日期是否符合yyyyMMdd格式
		 * @param args
		 * @return
		 */
		 public static boolean CheckDateYMD (String args){

		        String str = args;

		        String regex = "[0-9]{4}[0-9]{2}[0-9]{2}";
		        Pattern pattern = Pattern.compile(regex);
		        Matcher m = pattern.matcher(str);
		        boolean dateFlag = m.matches();
		        if (!dateFlag) {
		            System.out.println("格式错误");
		            return dateFlag ;
		        }
				return dateFlag;
		      
		    }
		
	 
	/**
	 * 根据日期范围返回的日期集合 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getDaysByBeinEnd(String beginDate,String endDate){
		
		
        List<String> days = new ArrayList<String>();

        try {
            Date start = datePattern.parse(beginDate);
            Date end = datePattern.parse(endDate);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
            	//System.out.println(datePattern.format(tempStart.getTime()));
                days.add(datePattern.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
        	logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return days;
	}
	
	/**
	 * 将日期的星期与日期范围里面的星期比较，最终返回日期范围里面的日期
	 * 人力资源排班复制上周功能使用
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static String getDaysByWeek(String date,String beginDate,String endDate){
		
		String newDays=null;

        try {
        	
        	int week=getCurWeek(DateUtil.stringToDate(date,"yyyy-MM-dd"));
            Date start = datePattern.parse(beginDate);
            Date end = datePattern.parse(endDate);
            
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            
            while (tempStart.before(tempEnd)) {
            	if(week==getCurWeek(tempStart.getTime())){
            		newDays=datePattern.format(tempStart.getTime());
            		break;
            	}
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
                
            }

        } catch (ParseException e) {
        	logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return newDays;
	}
	
	
	/**
	 * 将日期与日期范围里面的天比较，最终返回日期范围里面的日期
	 * 人力资源排班复制上月功能使用
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static String getDaysByMonth(String date,String beginDate,String endDate){
		
		String newDays=null;

        try {
        	
        	Date sel = datePattern.parse(date);
            Date start = datePattern.parse(beginDate);
            Date end = datePattern.parse(endDate);
            
            Calendar tempSel = Calendar.getInstance();
            tempSel.setTime(sel);
            int selDay=tempSel.get(Calendar.DAY_OF_MONTH);
            
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            
            while (tempStart.before(tempEnd)) {
            	
            	if(selDay==tempStart.get(Calendar.DAY_OF_MONTH)){
            		newDays=datePattern.format(tempStart.getTime());
            		break;
            	}
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
                
            }

        } catch (ParseException e) {
        	logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return newDays;
	}
	
	/**
	 * 取当天的年月日的   日 （如：01）
	 * @return
	 */
	public static String getToDayD(){
		Date date = new Date();
		String ymd = datePattern.format(date);
		return ymd.split("-")[2];
	}
	
	public static String getPerMonth(String year_month,int mcount) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Date date = sdf.parse(year_month);

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.MONTH, mcount); // 得到前一个月

		return sdf.format(cal.getTime());
	}
	
	public static void main(String[] args){
		try{
		System.out.println(getPerMonth("2020-02",-7));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
