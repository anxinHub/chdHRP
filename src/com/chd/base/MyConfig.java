package com.chd.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chd.base.startup.LoadMenuFile;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.MyAccYearMonth;
import com.chd.hrp.sys.entity.SysMenu;


public class MyConfig{

	private static Logger logger = Logger.getLogger(MyConfig.class);
	
	
	public static HttpSession getSession() {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();

		return request.getSession();
	}
	
	public static ServletContext getServletContext() {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();

		return request.getServletContext();
	}
	
	/**
	 * 取当前系统记账的会计年度
	 * @param 默认从session里面取集团医院账套
	 * @return 年yyyy
	 */
	public static String getCurAccYear(){
		
		Map<String,MyAccYearMonth> map=(Map<String, MyAccYearMonth>) getServletContext().getAttribute("acc_year_month");
		if(map==null) return null; 
		String key=SessionManager.getGroupId()+SessionManager.getHosId()+SessionManager.getCopyCode();
		MyAccYearMonth myAccYear=map.get(key);
		String accYear=null;
		if(SessionManager.getModCode().equals("01")){
			//财务系统
			accYear=myAccYear.getCurYearMonthAcc().substring(0, 4);
		}else if(SessionManager.getModCode().equals("03")){
			//科室成本系统
			accYear=myAccYear.getCurYearMonthCost().substring(0, 4);
		}else if(SessionManager.getModCode().equals("04")){
			//物流系统
			accYear=myAccYear.getCurYearMonthMat().substring(0, 4);
		}else if(SessionManager.getModCode().equals("05")){
			//资产系统
			accYear=myAccYear.getCurYearMonthAss().substring(0, 4);
		}else if(SessionManager.getModCode().equals("08")){
			//药品系统
			accYear=myAccYear.getCurYearMonthMed().substring(0, 4);
		}else if(SessionManager.getModCode().equals("14")){
			//无形资产系统
			accYear=myAccYear.getCurYearMonthIss().substring(0, 4);
		}else{
			//其他系统，没有期间概念取系统年度
			if(getSession().getAttribute("acct_year") != null){
				accYear=getSession().getAttribute("acct_year").toString();
			}else{
				accYear=DateUtil.getSysTime().substring(0, 4);
			}
			
		}
		return accYear; 
	}
	
	/**
	 * 取当前系统记账的会计期间
	 * @param 默认从session里面取集团医院账套
	 * @return 年月 yyyyMM
	 */
	public static String getCurAccYearMonth(){
		
		Map<String,MyAccYearMonth> map=(Map<String, MyAccYearMonth>) getServletContext().getAttribute("acc_year_month");
		if(map==null) return null; 
		String key=SessionManager.getGroupId()+SessionManager.getHosId()+SessionManager.getCopyCode();
		MyAccYearMonth myAccYear=map.get(key);
		String accYear=null;
		if(SessionManager.getModCode().equals("01")){
			//财务系统
			accYear=myAccYear.getCurYearMonthAcc();
		}else if(SessionManager.getModCode().equals("03")){
			//科室成本系统
			accYear=myAccYear.getCurYearMonthCost();
		}else if(SessionManager.getModCode().equals("04")){
			//物流系统
			accYear=myAccYear.getCurYearMonthMat();
		}else if(SessionManager.getModCode().equals("05")){
			//资产系统
			accYear=myAccYear.getCurYearMonthAss();
		}else if(SessionManager.getModCode().equals("14")){
			//无形资产系统
			accYear=myAccYear.getCurYearMonthIss();
		}else if(SessionManager.getModCode().equals("08")){
			//药品系统
			accYear=myAccYear.getCurYearMonthMed();
		}else{
			//其他系统，没有期间概念取系统年度
			String curDate=DateUtil.getSysTime();
			if(getSession().getAttribute("acct_year") != null){
				accYear=getSession().getAttribute("acct_year").toString()+curDate.substring(5,7);
			}else{
				accYear=curDate.substring(0, 4)+curDate.substring(5,7);
			}
		}
		return accYear; 
	}
	
	/**
	 * 取当前系统记账的会计期间
	 * @param 默认从session里面取集团医院账套
	 * @return MyAccYearMonth对象
	 */
	public static MyAccYearMonth getMyAccYearMonth(){
		
		Map<String,MyAccYearMonth> map=(Map<String, MyAccYearMonth>) getServletContext().getAttribute("acc_year_month");
		if(map==null) return null; 
		String key=SessionManager.getGroupId()+SessionManager.getHosId()+SessionManager.getCopyCode();
		return map.get(key);
	}

	/**
	 * 取当前系统记账的最小日期
	 * @param 默认从session里面取集团医院账套
	 * @return 年yyyy-MM-dd
	 */
	public static String getMinDate(){
		
		Map<String,MyAccYearMonth> map=(Map<String, MyAccYearMonth>) getServletContext().getAttribute("acc_year_month");
		if(map==null) return null; 
		String key=SessionManager.getGroupId()+SessionManager.getHosId()+SessionManager.getCopyCode();
		MyAccYearMonth myAccYear=map.get(key);
		if(myAccYear!=null){
			return myAccYear.getMinDate();
		}
		return null;
	}
	
	/**
	 * 取当前系统记账的最大日期
	 * @param 默认从session里面取集团医院账套
	 * @return 年yyyy-MM-dd
	 */
	public static String getMaxDate(){
		
		Map<String,MyAccYearMonth> map=(Map<String, MyAccYearMonth>) getServletContext().getAttribute("acc_year_month");
		if(map==null) return null; 
		String key=SessionManager.getGroupId()+SessionManager.getHosId()+SessionManager.getCopyCode();
		MyAccYearMonth myAccYear=map.get(key);
		if(myAccYear!=null){
			return myAccYear.getMaxDate();
		}
		return null;
	}
	
	
	/**
	 * 取当前系统记账的所有会计期间对象
	 * @param 集团ID，医院ID，账套编码
	 * @return 实体类MyAccYearMonth
	 */
	public static MyAccYearMonth getAccYearMonth(String groupId,String hosId,String copyCode){
		
		Map<String,MyAccYearMonth> map=(Map<String, MyAccYearMonth>) getServletContext().getAttribute("acc_year_month");
		if(map==null) return null; 
		String key=groupId+hosId+copyCode;
		return map.get(key); 
	}
	
	/**
	 * 取当前系统记账的会计期间
	 * @param 默认从session里面取集团医院账套
	 * @return 实体类MyAccYearMonth
	 */
	public static MyAccYearMonth getAccYearMonth(){
		
		Map<String,MyAccYearMonth> map=(Map<String, MyAccYearMonth>) getServletContext().getAttribute("acc_year_month");
		if(map==null) return null; 
		String key=SessionManager.getGroupId()+SessionManager.getHosId()+SessionManager.getCopyCode();
		return map.get(key); 
	}
	
	
	/**
	 * 取系统参数
	 * @param 集团ID，医院ID，账套编码，参数编码
	 * @return 参数值
	 */
	public static String getSysPara(String groupId,String hosId,String copyCode,String paraCode){
		
		Map<String,String> map=(Map<String, String>) getServletContext().getAttribute("sys_para");
		if(map==null) return null; 
		String key=groupId+hosId+copyCode+paraCode;
		if(map.get(key)!=null){
			return map.get(key)==null?null:map.get(key).toString();
		}
		return null; 
	}
	
	
	/**
	 * 取系统参数
	 * @param 参数编码，默认从session里面取集团医院账套
	 * @return 参数值
	 */
	public static String getSysPara(String paraCode){
		
		Map<String,Object> map=(Map<String, Object>) getServletContext().getAttribute("sys_para");
		String key=SessionManager.getGroupId()+SessionManager.getHosId()+SessionManager.getCopyCode()+paraCode;
		if(map==null) return null; 
		if(map.get(key)!=null){
			return map.get(key)==null?null:map.get(key).toString();
		}
		return null; 
	}
	
	
	/**
	 * 医院用户、集团用户登录、系统平台功能授权，检查菜单功能权限
	 * @param liUserPerm，权限id
	 * @param modCode
	 * @param id，菜单id
	 * @return
	 */
	public static List<SysMenu> checkMenuPerm(List<SysMenu> rootList,List<String> liUserPerm,int id,List<SysMenu> childList) {
		
		for(SysMenu menu:rootList){
			if(menu.getPid()==id){
				if(menu.getPerm_id()!=null && liUserPerm.contains(menu.getPerm_id())){
					//System.out.println("id："+menu.getId()+"，pid："+menu.getPid()+"，code："+menu.getCode()+"，name："+menu.getMenu_name()+"，url："+menu.getMenu_url()+"，permid："+menu.getPerm_id());
					childList.add(menu);
					break;
				}else{
					//permid：null代表非末级，需要递归
					checkMenuPerm(rootList,liUserPerm,menu.getId(),childList);
				}
			}
		}
		return childList;
	}
}
