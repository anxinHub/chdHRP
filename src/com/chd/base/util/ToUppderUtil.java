package com.chd.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToUppderUtil {
	// 日期转化为大小写  
	    public static String dataToUpper(String dateStr) {  
	        String res="";  
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        Date date = null;  
	        try {  
	            date = df.parse(dateStr);  
	        } catch (Exception e) {  
	            // 日期型字符串格式错误  
	            System.out.println("日期型字符串格式错误");  
	        }  
	        if(date!=null){  
	            Calendar ca = Calendar.getInstance();  
	            ca.setTime(date);  
	            int year = ca.get(Calendar.YEAR);  
	            int month = ca.get(Calendar.MONTH) + 1;  
	            int day = ca.get(Calendar.DAY_OF_MONTH);  
	            res=numToUpper(year) + "年" + monthToUppder(month) + "月"+dayToUppder(day) + "日";  
	        }  
	        return res;  
	    }  
	  
	    // 将数字转化为大写  
	    public static String numToUpper(int num) {  
	        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};  
	        String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };  
	        char[] str = String.valueOf(num).toCharArray();  
	        String rstr = "";  
	        for (int i = 0; i < str.length; i++) {  
	            rstr = rstr + u[Integer.parseInt(str[i] + "")];  
	        }  
	        return rstr;  
	    }  
	  
	    // 月转化为大写  
	    public static String monthToUppder(int month) {  
	        if (month < 10) {  
	            return numToUpper(month);  
	        } else if (month == 10) {  
	            return "十";  
	        } else {  
	            return "十" + numToUpper(month - 10);  
	        }  
	    }  
	  
	    // 日转化为大写  
	    public static String dayToUppder(int day) {  
	        if (day < 20) {  
	            return monthToUppder(day);  
	        } else {  
	            char[] str = String.valueOf(day).toCharArray();  
	            if (str[1] == '0') {  
	                return numToUpper(Integer.parseInt(str[0] + "")) + "十";  
	            } else {  
	                return numToUpper(Integer.parseInt(str[0] + "")) + "十"  
	                        + numToUpper(Integer.parseInt(str[1] + ""));  
	            }  
	        }  
	    }  

}
