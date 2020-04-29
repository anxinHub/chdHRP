package com.chd.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单价、金额格式化工具类
 * */
public class NumberUtil {
	
	//第一个正则只要字符串是以数字开头的isNumeric方法就返回true
	//private static Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*"); 
	private static Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$"); 
	
	/**
	 * 四舍五入2位小数
	 * @param num
	 * @return double
	 */
	public static double numberToRound(double num){
		DecimalFormat df = new DecimalFormat("0.00");
		Double result = Double.valueOf(df.format(Math.round(num*100)/100.0));
		return result == null ? 0.00 : result;
	}
	
	/**
	 * 按指定小数位数四舍五入
	 * @param num
	 * @param index
	 * @return double
	 */
	public static double numberToRound(double num, int index){
		
		String format = "0";
		for(int i = 0; i < index; i++){
			if(format.indexOf(".") > 0){
				format += "0";
			}else{
				format += ".0";
			}
		}
		DecimalFormat df = new DecimalFormat(format);
		
        double temp = Math.pow(10, index);
        Double result = Double.valueOf(df.format(Math.round(num*temp)/temp));
		return result == null ? 0.00 : result;
	}
	
	/**
	 * 返回指定小数位数
	 * @param num
	 * @return String
	 */
	public static String numberToString(double num){

		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(Math.round(num*100)/100.0);
	}
	
	/**
	 * 返回指定小数位数
	 * @param num
	 * @param index
	 * @return String
	 */
	public static String numberToString(double num, int index){
		
		String format = "0";
		for(int i = 0; i < index; i++){
			if(format.indexOf(".") > 0){
				format += "0";
			}else{
				format += ".0";
			}
		}
		DecimalFormat df = new DecimalFormat(format);
		
        double temp = Math.pow(10, index);
		return df.format(Math.round(num*temp)/temp);
	}
	
	/**
	 * 正则表达式判断字符串是否为数字
	 * @param 字符串
	 * @return
	 */
	public static boolean isNumeric(String str){ 

		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false; 
		} 
		return true; 
	}
	
	/**
	 * 两个Double数相加
	 * @param 加数1
	 * @param 加数2
	 * @return Double
	 */
	public static Double add(Double v1, Double v2){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 两个Double数相加
	 * @param 加数1
	 * @param 加数2
	 * @param 保留小数位数
	 * @return Double
	 */
	public static Double add(Double v1, Double v2, int scale){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		
		return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 两个Double数相减
	 * @param 被减数
	 * @param 减数
	 * @param 保留小数位数
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2, int scale){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 两个Double数相减
	 * @param 被减数
	 * @param 减数
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 两个Double数相乘
	 * @param 乘数1
	 * @param 乘数2
	 * @param 保留小数位数
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2, int scale){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		
		return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 两个Double数相乘
	 * @param 乘数1
	 * @param 乘数2
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 两个Double数相除
	 * @param 被除数
	 * @param 除数
	 * @param 保留小数位数
	 * @return Double
	 */
	public static Double div(Double v1, Double v2, int scale){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		
		return b1.divide(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
