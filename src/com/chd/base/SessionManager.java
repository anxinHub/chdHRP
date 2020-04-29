/**
 * 2015-1-8 SessionManager.java author:pengjin
 */
package com.chd.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chd.base.exception.SysException;
import com.chd.hrp.sys.service.base.SysBaseService;

public class SessionManager {

	private static Logger logger = Logger.getLogger(SessionManager.class);

	public static HttpServletRequest getRequest() {

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();

		return request;
	}
	
	public static HttpSession getSession() {

		return getRequest().getSession();
	}

	/*
	 * 获取session
	 */
	public static String getUserId() {

		return getSession().getAttribute("user_id") == null ? null
				: getSession().getAttribute("user_id").toString();

	}

	public static String getCopyCode() {
		// 超级管理员、集团管理员、医院管理没有账套
		if (getSession().getAttribute("type_code").equals(2)
				|| getSession().getAttribute("type_code").equals(3)
				|| getSession().getAttribute("type_code").equals(4)) {
			return "";
		}

		if (getSession().getAttribute("copy_code") == null) {
			throw new SysException("copy_code为空！");
		}

		return getSession().getAttribute("copy_code").toString();
	}

	public static String getCopyName() {

		// 超级管理员、集团管理员、医院管理没有账套
		if (getSession().getAttribute("type_code").equals(2)
				|| getSession().getAttribute("type_code").equals(3)
				|| getSession().getAttribute("type_code").equals(4)) {
			return "";
		}

		if (getSession().getAttribute("copy_name") == null) {

			throw new SysException("copy_name为空！");

		}

		return getSession().getAttribute("copy_name").toString();

	}
	
	public static String getCopyNature() {

		// 超级管理员、集团管理员、医院管理没有账套
		if (getSession().getAttribute("type_code").equals(2)
				|| getSession().getAttribute("type_code").equals(3)
				|| getSession().getAttribute("type_code").equals(4)) {
			return "";
		}

		if (getSession().getAttribute("copy_nature") == null) {

			throw new SysException("copy_nature为空！");

		}

		return getSession().getAttribute("copy_nature").toString();

	}

	public static String getGroupId() {
		
		if(getSession().getAttribute("user_code")==null){
			throw new SysException("会话超时！");
		}
		
		if (!getSession().getAttribute("user_code").equals("admin")
				&& getSession().getAttribute("group_id") == null) {

			throw new SysException("group_id为空！");

		}

		return getSession().getAttribute("group_id") == null ? ""
				: getSession().getAttribute("group_id").toString();
	}

	public static String getGroupCode() {

		return getSession().getAttribute("group_code") == null ? ""
				: getSession().getAttribute("group_code").toString();

	}
	//部门科室支出性质
	public static String getOutCode() {
		return getSession().getAttribute("out_code") == null ? ""
				: getSession().getAttribute("out_code").toString();
	}

	public static String getGroupName() {

		return getSession().getAttribute("group_name") == null ? ""
				: getSession().getAttribute("group_name").toString();

	}

	// 集团简称
	public static String getGroupSimple() {

		return getSession().getAttribute("group_simple") == null ? ""
				: getSession().getAttribute("group_simple").toString();
	}

	public static String getHosId() {

		if (getSession().getAttribute("hos_id") == null) {

			throw new SysException("hos_id为空！");

		}

		return getSession().getAttribute("hos_id") == null ? "" : getSession()
				.getAttribute("hos_id").toString();
	}

	public static String getHosNo() {

		if (getSession().getAttribute("hos_no") == null) {

			throw new SysException("hos_no为空！");

		}

		return getSession().getAttribute("hos_no") == null ? "" : getSession()
				.getAttribute("hos_no").toString();
	}

	public static String getHosCode() {

		return getSession().getAttribute("hos_code") == null ? ""
				: getSession().getAttribute("hos_code").toString();
	}

	public static String getHosName() {
		// 超级管理员、集团用户、集团管理员没有医院名称
		if (getSession().getAttribute("type_code").equals(1)
				|| getSession().getAttribute("type_code").equals(2)
				|| getSession().getAttribute("type_code").equals(3)) {

			return "";

		}

		if (getSession().getAttribute("hos_name") == null) {

			throw new SysException("hos_name为空！");

		}

		return getSession().getAttribute("hos_name").toString();
	}

	// 医院简称
	public static String getHosSimple() {
		// 超级管理员、集团用户、集团管理员没有医院名称
		if (getSession().getAttribute("type_code").equals(1)
				|| getSession().getAttribute("type_code").equals(2)
				|| getSession().getAttribute("type_code").equals(3)) {

			return "";

		}

		if (getSession().getAttribute("hos_simple") == null) {

			throw new SysException("hos_simple为空！");

		}

		return getSession().getAttribute("hos_simple").toString();
	}

	public static String getUserCode() {

		return getSession().getAttribute("user_code") == null ? null
				: getSession().getAttribute("user_code").toString();

	}

	public static String getUserName() {

		return getSession().getAttribute("user_name") == null ? ""
				: getSession().getAttribute("user_name").toString();

	}

	public static String getTypeCode() {
		// 0医院用户，1集团用户，2超级管理员，3集团管理员，4医院管理员
		return getSession().getAttribute("type_code") == null ? null
				: getSession().getAttribute("type_code").toString();

	}

	public static String getModCode() {

		return getSession().getAttribute("mod_code") == null ? null
				: getSession().getAttribute("mod_code").toString();

	}

	public static String getModName() {

		return getSession().getAttribute("mod_name") == null ? null
				: getSession().getAttribute("mod_name").toString();

	}

	public static String getEmpCode() {

		return getSession().getAttribute("emp_code") == null ? ""
				: getSession().getAttribute("emp_code").toString();

	}

	public static String getEmpName() {

		return getSession().getAttribute("emp_name") == null ? ""
				: getSession().getAttribute("emp_name").toString();

	}

	public static String getAcctYear() {

		if (!getSession().getAttribute("user_code").equals("admin")
				&& getSession().getAttribute("acct_year") == null) {

			throw new SysException("acct_year为空！");

		}

		return getSession().getAttribute("acct_year") == null ? ""
				: getSession().getAttribute("acct_year").toString();
	}

	public static String getHospital() {

		if (getSession().getAttribute("hospital") == null) {

			throw new SysException("版权限制！");

		}

		return getSession().getAttribute("hospital") == null ? ""
				: getSession().getAttribute("hospital").toString();
	}

	public static String getSupNo() {

		return getSession().getAttribute("sup_no") == null ? "" : getSession()
				.getAttribute("sup_no").toString();
	}

	public static String getSupId() {

		return getSession().getAttribute("sup_id") == null ? "" : getSession()
				.getAttribute("sup_id").toString();
	}

	public static boolean existsUserPerm(String permId) {

		List<String> li = new ArrayList<String>();

		li = (List<String>) getSession().getAttribute("user_perm");

		if (li != null && li.size() > 0) {

			for (String s : li) {

				if (permId.equals(s)) {

					return true;

				}

			}

		}
		return false;

	}

	/**
	 * 获取当前会计期间
	 * 
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	public static String getSysYearMonth(String flag)
			throws DataAccessException {

		if (getSession().getAttribute("acc_year_month_map") == null) {

			return null;

		}
		// 获取当前系统会计期间集合
		Map<String, Object> accmap = (Map<String, Object>) getSession()
				.getAttribute("acc_year_month_map");
		Map<String, Object> modStart = getModStartMonth();
		String yearmonth = "000000";
		// 启用年月
		/*
		 * 00 系统平台 01 财务管理系统 0101 出纳账管理 0102 工资管理 02 预算管理系统 03 科室成本管理系统 04
		 * 物流管理系统 05 固定资产管理系统 06 人力资源管理系统 07 绩效考核管理系统 08 药品管理系统 99 医院协同
		 */
		String yearMonth = "0";
		if ("acc_flag".equals(flag)) {
			yearMonth = (null == modStart.get("01")) ? "0" : modStart.get(
					"01").toString();
		}
		if ("cash_flag".equals(flag)) {
			yearMonth = (null == modStart.get("0101")) ? "0" : modStart
					.get("0101").toString();
		}
		if ("wage_flag".equals(flag)) {
			yearMonth = (null == modStart.get("0102")) ? "0" : modStart
					.get("0102").toString();
		}
		if ("ass_flag".equals(flag)) {
			yearMonth = (null == modStart.get("05")) ? "0" : modStart.get(
					"05").toString();
		}
		if ("mat_flag".equals(flag)) {
			yearMonth = (null == modStart.get("04")) ? "0" : modStart.get(
					"04").toString();
		}
		if ("cost_flag".equals(flag)) {
			yearMonth = (null == modStart.get("03")) ? "0" : modStart.get(
					"03").toString();
		}
		
		
		for (Map.Entry<String, Object> entry : accmap.entrySet()) {

			Map<String, Object> value = (Map<String, Object>) entry.getValue();
			Integer obj = (Integer) (value.get(flag.toLowerCase()) == null ? 0 : value.get(flag.toLowerCase()));
			if (obj == 0) {
				if (null != yearMonth && !"".equals(yearMonth)
						&& null != entry.getKey()) {
					if (Integer.valueOf(entry.getKey()) >= Integer
							.valueOf(yearMonth)) {
						yearmonth = entry.getKey();
						break;
					}
				}
			}
		}
		return yearmonth;

	}

	/**
	 * 获取上一个会计期间
	 * 
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	public static String getLastSysYearMonth(String flag)
			throws DataAccessException {

		String yearmonth = getSysYearMonth(flag);
		if(yearmonth!=null) {
			String year = yearmonth.substring(0, 4);
			String month = yearmonth.substring(4, 6);

			if (month.equals("00") || month.equals("01")) {
				return "000000";
			} else {
				int month_i = Integer.parseInt(month);

				if (month_i <= 10) {
					month = "0" + String.valueOf(month_i - 1);
				} else {
					month = String.valueOf(month_i - 1);
				}

				return year + month;
			}
		}else {
			return "000000";
		}
		

	}

	/**
	 * 判断传递的年月是否已经结账
	 * 
	 * @param yearmonth
	 *            年月 yyyyMM
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	public static boolean getAccYearMonthIsCheckOut(String yearmonth,
			String flag) throws DataAccessException {
		if (getSession().getAttribute("acc_year_month_map") == null) {
            
			return true;

		}
		// 获取当前系统会计期间集合
		Map<String, Object> accmap = (Map<String, Object>) getSession().getAttribute("acc_year_month_map");
		// 获取查询年月会计期间集合
		Map<String, Object> yearmonthMap = (Map<String, Object>) accmap.get(yearmonth);
		if (yearmonthMap==null){
			//所选年月不再系统上线会计期间之内，存在这种情况的，不允许做业务操作
			return true;
		}else{
			Integer obj = (Integer) (yearmonthMap.get(flag.toLowerCase()) == null ? 0
					: yearmonthMap.get(flag.toLowerCase()));
			return obj == 1 ? true : false;
		}
	}

	/**
	 * 获取系统启用
	 * 
	 * @throws DataAccessException
	 */
	public static Map<String, Object> getModStartMonth()
			throws DataAccessException {

		if (getSession().getAttribute("acc_mod_start_map") == null) {

			return null;

		}
		// 获取当前系统会计期间集合
		Map<String, Object> accmap = (Map<String, Object>) getSession()
				.getAttribute("acc_mod_start_map");

		return accmap;

	}

	/**
	 * 获取会计期间集合
	 * 
	 * @return
	 */
	public static Map<String, Object> queryAccYearMonth() {
		if (getSession().getAttribute("acc_year_month_map") == null) {
			return null;

		}
		// 获取当前系统会计期间集合
		Map<String, Object> accmap = (Map<String, Object>) getSession().getAttribute("acc_year_month_map");

		return accmap;
	}

	/**
	 * 获取财务系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getAccParaMap() {
		if (getSession().getAttribute("acc_para_map") == null) {

			return null;

		}
		// 获取当前系统财务系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("acc_para_map");
		return para;
	}
	
	/**
	 * 获取预算系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getBudgParaMap() {
		if (getSession().getAttribute("budg_para_map") == null) {

			return null;

		}
		// 获取当前系统预算系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("budg_para_map");
		return para;
	}
	
	
	/**
	 * 获取绩效系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getPrmParaMap() {
		if (getSession().getAttribute("prm_para_map") == null) {

			return null;

		}
		// 获取当前系统绩效系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("prm_para_map");
		return para;
	}
	
	

	/**
	 * 获取物流系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getMatParaMap() {
		if (getSession().getAttribute("mat_para_map") == null) {

			return null;

		}
		// 获取当前系统物流系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("mat_para_map");
		return para;
	}

	/**
	 * 获取药品系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getMedParaMap() {
		if (getSession().getAttribute("med_para_map") == null) {

			return null;

		}
		// 获取当前系统药品系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("med_para_map");
		return para;
	}

	/**
	 * 获取资产系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getAssParaMap() {
		if (getSession().getAttribute("ass_para_map") == null) {

			return null;

		}
		// 获取当前系统资产系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("ass_para_map");
		return para;
	}

	/**
	 * 获取成本系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getCostParaMap() {
		if (getSession().getAttribute("cost_para_map") == null) {

			return null;

		}
		// 获取当前系统成本系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("cost_para_map");
		return para;
	}
	
	
	/**
	 * 获取奖金系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getHpmParaMap() {
		if (getSession().getAttribute("hpm_para_map") == null) {

			return null;

		}
		// 获取当前系统奖金系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("hpm_para_map");
		return para;
	}
	
	/**
	 * 获取人力系统参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getHrParaMap() {
		if (getSession().getAttribute("hr_para_map") == null) {

			return new HashMap<String,Object>();

		}
		// 获取当前系统人力系统参数
		Map<String, Object> para = (Map<String, Object>) getSession()
				.getAttribute("hr_para_map");
		return para;
	}
	

	/**
	 * 系统规则集合
	 * 
	 * @return
	 */
	public static Map<String, Map<String, Object>> queryHosRulesMap() {
		if (getSession().getAttribute("hos_rules") == null) {

			return null;

		}
		// 系统规则集合
		Map<String, Map<String, Object>> para = (Map<String, Map<String, Object>>) getSession()
				.getAttribute("hos_rules");
		return para;
	}

	/**
	 * 系统参数集合
	 * 
	 * @return
	 */
	public static Map<String, Map<String, Object>> sysParaMaps() {
		if (getSession().getAttribute("sys_para_maps") == null) {

			return null;

		}
		// 系统规则集合
		Map<String, Map<String, Object>> para = (Map<String, Map<String, Object>>) getSession()
				.getAttribute("sys_para_maps");
		return para;
	}
	/**
	 * 集团属性
	 * 
	 * @return
	 */
	public static Map<String, Map<String, Object>> queryGroupParaMap() {
		if (getSession().getAttribute("group_para") == null) {

			return null;

		}
		// 系统规则集合
		Map<String, Map<String, Object>> para = (Map<String, Map<String, Object>>) getSession()
				.getAttribute("group_para");
		return para;
	}
	
	/**
	 * 集团属性-供应商生成厂商分类
	 * 
	 * @return
	 */
	public static Map<String, Map<String, Object>> queryGroupSFMap() {
		if (getSession().getAttribute("group_para_sf") == null) {

			return null; 
		}
		// 系统规则集合
		Map<String, Map<String, Object>> para = (Map<String, Map<String, Object>>) getSession()
				.getAttribute("group_para_sf");
		return para;
	}

	
	public static synchronized String getIpAddr() {
		   String ip = getRequest().getHeader("x-forwarded-for"); 
	       //System.out.println("x-forwarded-for ip: " + ip);
	       if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
	           // 多次反向代理后会有多个ip值，第一个ip才是真实ip
	           if( ip.indexOf(",")!=-1 ){
	               ip = ip.split(",")[0];
	           }
	       }  
	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = getRequest().getHeader("Proxy-Client-IP");  
	           //System.out.println("Proxy-Client-IP ip: " + ip);
	       }  
	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = getRequest().getHeader("WL-Proxy-Client-IP");  
	           //System.out.println("WL-Proxy-Client-IP ip: " + ip);
	       }  
	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = getRequest().getHeader("HTTP_CLIENT_IP");  
	           //System.out.println("HTTP_CLIENT_IP ip: " + ip);
	       }  
	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = getRequest().getHeader("HTTP_X_FORWARDED_FOR");  
	           //System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
	       }  
	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = getRequest().getHeader("X-Real-IP");  
	           //System.out.println("X-Real-IP ip: " + ip);
	       }  
	       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = getRequest().getRemoteAddr();  
	           //System.out.println("getRemoteAddr ip: " + ip);
	       } 
	       //System.out.println("获取客户端ip: " + ip);
	       return ip;  
	   }
}
