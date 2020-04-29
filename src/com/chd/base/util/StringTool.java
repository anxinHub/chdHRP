/**
 * 2014-6-13 StringTool.java author:pengjin
 */
package com.chd.base.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;
import org.nutz.mvc.Mvcs;

import com.chd.base.startup.LoadSystemInfo;

/**
 * 字符串处理工具类 <br>
 * 常规转换、拼音码转换、五笔码转换、提示语拼接（通过格式）
 * 
 * @author Liuyingduo
 */
public class StringTool {

	// public static final String UTF8="UTF-8";
	// public static final String ISO88591="iso-8859-1";
	// public static final String GBK="GBK";
	// public static final String GB2312="gb2312";

	private static Logger logger = Logger.getLogger(StringTool.class);

	/**
	 * 默认把字符串由 iso-8859-1 转 UTF-8
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String getString(String str) {
		String s = "";

		if (str == null || str.equalsIgnoreCase("")) {

			return "";
		}

		byte[] temp;

		try {
			temp = str.getBytes("iso-8859-1");
			s = new String(temp, "UTF-8");

		}
		catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return s;
	}

	/**
	 * 按需转字符串
	 * 
	 * @param str
	 *            字符串
	 * @param oldcharset
	 *            原字符类型
	 * @param newcharset
	 *            新字符类型
	 * @return
	 */
	public static String getCharsetString(String str, String oldcharset, String newcharset) {
		String s = "";

		if (str == null || str.equalsIgnoreCase("")) {

			return "";
		}

		byte[] temp;

		try {
			temp = str.getBytes(oldcharset);
			s = new String(temp, newcharset);

		}
		catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return s;
	}

	// 处理JSON特殊字符
	public static String string2Json(String s) {
		if (s == null || s.equals("")) {
			return s;
		}
		if(s.contains("ORA-20000")){
			//处理异常，只显示oracle提示信息
			return s.substring(s.indexOf("ORA-20000"),s.indexOf("ORA-06512"));
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {

			char c = s.charAt(i);
			switch (c) {
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
			}

		}
		return sb.toString();
	}

	// 将json格式解析成list
	public static List<String> parseJsonToArray(String data) {
		List<String> list = new ArrayList<String>();
		if (null == data || data.length() == 0) {
			return null;
		}
		if (data.length() < 4) {
			return null;
		}
		String temp = data.substring(2, data.length() - 2);
		temp = temp.replaceAll("\\'", "");
		String splitResult[] = temp.split(",");
		for (int i = 0; i < splitResult.length; i++) {
			list.add(splitResult[i].substring(splitResult[i].indexOf(":") + 1));
		}
		return list;
	}

	/**
	 * 根据HIS数据库编码转换编码
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String getStringByHis(String str) {

		if (str == null || str.equalsIgnoreCase("")) {
			return str;
		}

		try {
			// oracle库
			if (LoadSystemInfo.getHrpMap().get("hisDatatype").equals("oracle") && !LoadSystemInfo.getHrpMap().get("hisUnicode").equals("")) {
				return new String(str.getBytes(LoadSystemInfo.getHrpMap().get("hisUnicode")), "GBK");
			} else {
				return str;
			}

		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 匹配单个字符全拼的声母和韵母,声母可能不存在.注意y和w,虽然不在声母范围,但是居首也是有可能的
	 */
	private static final Pattern psp = Pattern.compile("^([bpmfdtnlgkhjqxrzcsyw]{0,2})([aeiouv][a-z]*)$");

	private static HanyuPinyinOutputFormat hpof;

	private static Properties wb86;

	private static Properties shuangPin;

	static {
		// 拼音格式
		hpof = new HanyuPinyinOutputFormat();
		hpof.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出全小写
		hpof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不显示音调
		hpof.setVCharType(HanyuPinyinVCharType.WITH_V);// ü替换为v

		// 五笔数据
		wb86 = new Properties();
		try {
			wb86.load(new BufferedInputStream(StringTool.class.getResourceAsStream("wb86.properties")));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// 双拼
		shuangPin = new Properties();
		try {
			shuangPin.load(new BufferedInputStream(StringTool.class.getResourceAsStream("sp.properties")));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字符转化为拼音.无法转换返回null,转换成功返回所有的发音
	 * 
	 * @param c
	 * @return
	 */
	public static String[] charToPinyin(char c) {
		
		
		if (c < 0x4E00 || c > 0x9FA5) {// GBK字库在unicode中的起始和结束位置
			if((c > 0x29 && c < 0x40) || (c > 0x40 && c < 0x5B) || (c > 0x60 && c < 0x7B)){
				String cc = Character.toString(c); 
				String[] c1 = new String[10];
				c1[0] = cc.toUpperCase();
				return c1;
			}
			
			if (c != 0x3007) {// 圆圈0比较特殊,需要处理一下
				return null;
			}
			
		}
		
		
		try {
			return PinyinHelper.toHanyuPinyinStringArray(c, hpof);
		}
		catch (BadHanyuPinyinOutputFormatCombination e) {
			return null;
		}
	}

	/**
	 * 获取单个字符的双拼,顺序和全拼一致
	 * 
	 * @param c
	 * @return
	 */
	public static String[] charToShuangPin(char c) {
		String[] array = charToPinyin(c);
		if (array == null) {
			return array;
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String s = array[i];
			Matcher m = psp.matcher(s);
			if (m.matches()) {
				String sm = m.group(1);
				String smdz = shuangPin.getProperty(sm);
				String ym = m.group(2);
				String ymdz = shuangPin.getProperty(ym);

				String r = "";
				if (smdz != null) {
					r = smdz;
				}
				if (ymdz != null) {
					r += ymdz;
				}

				result[i] = r;
			} else {
				logger.info("分解" + c + "拼音的拼音时发生错误!无法拆分出声母和韵母.");
			}
		}
		return result;
	}

	/**
	 * 返回字符串的拼音的首字母,每个多音字只取第一个发音.
	 * 
	 * @param s
	 * @return
	 */
	public static String toPinyinShouZiMu(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			String[] r = charToPinyin(c);
			/*if (r == null) {
				sb.append(c);
			} else {
				String py = r[0];
				if (py.length() > 0) {
					sb.append(py.charAt(0));
				}
			}*/
			if(r!=null){
				String py = r[0];
				if (py.length() > 0) {
					sb.append(py.charAt(0));
				}
			}
		}
		//转化成大写
		return   sb.toString().toUpperCase();
	}

	/**
	 * 返回字符串的拼音,全拼,每个字的首字母大写,每个多音字只取第一个发音.
	 * 
	 * @param s
	 * @return
	 */
	public static String toQuanPin(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			String[] r = charToPinyin(c);
			if (r == null) {
				sb.append(c);
			} else {
				String py = r[0];
				if (py.length() > 0) {
					char f = Character.toUpperCase(py.charAt(0));
					// if ((f == 'A' || f == 'O' || f == 'E') && sb.length() >
					// 0) {
					// aoe 开头的音节连接在其它音节后面的时候，如果音节的界限发生混淆，用隔音符号（'）隔开，例如
					// pí'ǎo（皮袄）xī'ān（西安）。
					// py = "'" + f + py.substring(1);
					// } else {
					py = f + py.substring(1);
					// }
				}
				sb.append(py);
			}
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 返回字符串的双拼.多音字只取第一个发音.每个字的首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toShuangPin(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			String[] r = charToShuangPin(c);
			if (r == null) {
				sb.append(c);
			} else {
				String py = r[0];
				if (py.length() > 0) {
					char f = Character.toUpperCase(py.charAt(0));
					py = f + py.substring(1);
				}
				sb.append(py);
			}
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 字符转化为五笔(86),无法转化返回null
	 * 
	 * @param c
	 * @return
	 */
	public static String[] charToWubi(char c) {
		if (c < 0x4E00 || c > 0x9FA5) {// GBK字库在unicode中的起始和结束位置
			return null;
		}
		String result = wb86.getProperty(Integer.toHexString(c).toUpperCase());
		if (result == null) {
			return null;
		}
		if (result.contains(",")) {
			return result.split(",");
		} else {
			return new String[] { result };
		}
	}

	/**
	 * 汉字转五笔码
	 * 
	 * @param s
	 * @return
	 */
	public static String toWuBi(String s) {
		char[] array = s.toCharArray();
		String reverse = "";
		for (char c : array) {
			String[] arr = charToWubi(c);
			if (null != arr) {
				reverse +=  charToWubi(c)[0].toCharArray()[0];// 取第一个五笔简码的第一个字符
			} else {
				StringBuilder sb = new StringBuilder();
				String[] r = charToPinyin(c);
				/*if (r == null) {
					sb.append(c);
				} else {
					String py = r[0];
					if (py.length() > 0) {
						sb.append(py.charAt(0));
					}
				}*/
				if(r != null){
					String py = r[0];
					if (py.length() > 0) {
						sb.append(py.charAt(0));
					}
				}
				reverse += sb.toString();// 取拼音码
			}

		}
		return reverse.toUpperCase();
	};

	/**
	 * 获取返回信息的附加信息 <br>
	 * 错误信息 操作成功 附属信息提示
	 * 
	 * @param resultJson
	 * <br>
	 *            返回的json字符串
	 * @param tag
	 * <br>
	 *            json 标记 <br>
	 *            {msg: ,error:}
	 * @param msg
	 * <br>
	 *            页面提示信息
	 * @param state
	 * <br>
	 *            true|false
	 * @return
	 */
	public static String getMessage(String resultJson, String tag, String msg, String state) {
		StringBuffer r_json = new StringBuffer();
		r_json.append("{\"");
		r_json.append(tag);
		r_json.append("\":\"");
		r_json.append(msg);
		r_json.append("\",\"");
		r_json.append(state);
		r_json.append("\"}");
		return resultJson += r_json.toString();
	}

	public static void main(String[] args) {
		//System.out.println(toWuBi("111aaa——啊。./"));
		//System.out.println(toPinyinShouZiMu("111aaa——啊。./"));

		String ss = "五笔";
		String ss1 = "沈";
		char[] array = ss.toCharArray();
		String reverse = "";
		String reverse1 = "";
		for (char c : array) {

			String[] arr = StringTool.charToWubi(c);
			if (null != arr) {
				reverse += charToWubi(c)[0];// 取第一个五笔简码

				reverse1 += charToWubi(c)[0].toCharArray()[0];// 取第一个五笔简码

			}

		}
		System.out.println(StringTool.toPinyinShouZiMu(ss1));
		System.out.println(reverse);
		System.out.println(reverse1);
	}

	/**
	 * 转换数据，求交集和差集
	 * 
	 * @param list1
	 *            原有数据集合
	 * @param list2
	 *            现在数据集合
	 * @return 返回map <br>
	 *         get('add')新增的数据<br>
	 *         get('del')删除的数据
	 */
	public static Map<String, Object> getchangeData(List<String> list1, List<String> list2) {

		List<String> add = new ArrayList<String>();// 更新数据集合
		List<String> del = new ArrayList<String>();// 删除数据集合
		List<String> result = new ArrayList<String>();// 中间转换集合
		// 需要删除的数据集合
		del.clear();
		del.addAll(list1);
		del.removeAll(list2);// 差集
		// 需要中间转换集合
		result.clear();
		result.addAll(list2);
		result.retainAll(list1); // 交集
		// 需要添加的数据集合
		add.clear();
		add.addAll(list2);
		add.removeAll(result);
		// 返回结果集
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("add", add);
		map.put("del", del);
		map.put("upd", result);
		return map;
	}

	/**
	 * 判断object是否为空
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNotBlank(Object object) {
		if (null == object) {
			return false;
		}
		if (object.toString().equals("")) {
			return false;
		}
		if (object.toString().equals("null")) {
			return false;
		}
		return true;
	}
	
	//excel列号转数字，AA=>27
	public static int excelColStrToNum(String colStr) {
        int num = 0;
        int result = 0;
        colStr=colStr.toUpperCase();
        int length=colStr.length();
        
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }
	/**
	 * 配置H2 JDBC URL 
	 * @param path
	 * @return
	 */
	public static String getJdbcUrlToH2(String path) {
		return "jdbc:h2:" + Mvcs.getNutConfig().getAppRoot() + "/" + path;
	}
	
	/**
	 * "是","否"转换成 "0","1", 默认是"0"<br>
	 * "0":"否"<br>
	 * "1":"是"
	 * @param key "是","否","1","0"
	 * @return String 是-->1
	 *                否-->0
	 */
	public static String convertIsNoDefaultNo(String key){
		Map<String, String> map = new HashMap<String, String>();
		map.put("否", "0");
		map.put("是", "1");
		map.put("0", "0");
		map.put("1", "1");
		String value = map.get(key);
		return value == null ? "0" : value;
	}
	
	/**
	 * "是","否"转换成 "0","1", 默认是"1"<br>
	 * "0":"否"<br>
	 * "1":"是"
	 * @param key "是","否","1","0"
	 * @return String 是-->1
	 *                否-->0
	 */
	public static String convertIsNoDefaultIs(String key){
		Map<String, String> map = new HashMap<String, String>();
		map.put("否", "0");
		map.put("是", "1");
		map.put("0", "0");
		map.put("1", "1");
		String value = map.get(key);
		return value == null ? "1" : value;
	}
	
	/**
	 * 消息提示描述
	 * 
	 * @param msg 
	 *        例：以下XXX不存在：或 以下XXX格式不正确：
	 * @param list
	 *        检查出来的编码、名称等要提示的值的集合
	 * @return String 最后再拼接一个html换行元素
	 */
	public static String msgDescribe(StringBuilder msg, List<String> list){
		if(list.isEmpty()){
			return "";
		}
		
		for(String val : list){
			msg.append("<br/>").append(val);
		}
		return msg.append("<br/>").toString();
	}
}
