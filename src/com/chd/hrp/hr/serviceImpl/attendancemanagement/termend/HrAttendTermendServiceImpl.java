package com.chd.hrp.hr.serviceImpl.attendancemanagement.termend;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrPeriodMapper;
import com.chd.hrp.hr.dao.attendancemanagement.termend.HrAttendTermendMapper;
import com.chd.hrp.hr.service.attendancemanagement.termend.HrAttendTermendService;
import com.github.pagehelper.PageInfo;


/**
 * 结账
 */
@Service("hrAttendTermendService")
public class HrAttendTermendServiceImpl  implements HrAttendTermendService{
	private static Logger logger = Logger.getLogger(HrAttendTermendServiceImpl.class);
	@Resource(name = "hrAttendTermendMapper")
	private final HrAttendTermendMapper hrAttendTermendMapper = null;
	
	@Resource(name = "hrPeriodMapper")
	private final HrPeriodMapper hrPeriodMapper = null;

	
	/**
	 * 获取当前期间
	 */
	@Override
	public Map<String, Object> queryAttendTermendYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String month = "",	year ="";
			String year_before = "", month_before = "";
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			
			retMap = hrAttendTermendMapper.queryYearMonth(entityMap);
			
			if(retMap.get("year_month") == null || "".equals(retMap.get("year_month").toString())){
				year= String.valueOf((int) Integer.parseInt(MyConfig.getCurAccYear())+1);
				month="01";
				year_before=MyConfig.getCurAccYear();
				month_before="12";
				retMap.put("year", year);
				retMap.put("month", month);
				retMap.put("year_before", year_before);
				retMap.put("month_before", month_before);
				return retMap;
				
			}
			
			year=retMap.get("year_month").toString().substring(0, 4);
			month=retMap.get("year_month").toString().substring(4, 6);
			
			
			if("01".equals(month)){
				year_before = (Integer.parseInt(year) - 1) + "";
				month_before = "12";
			}else{
				year_before = year;
				month_before = "00" + (Integer.parseInt(month) - 1);
				month_before = month_before.substring(month_before.length() - 2, month_before.length());
			}

			retMap.put("year", year);
			retMap.put("month", month);
			retMap.put("year_before", year_before);
			retMap.put("month_before", month_before);

			retMap.put("state", "true");
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
	
	/**
	 * 查询清除余额
	 */
	@Override
	public String queryAttendXjedDel(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("year", MyConfig.getCurAccYear());
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String,Object>> list = hrAttendTermendMapper.queryAttendXjedDel(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 保存清除余额
	 */
	@Override
	public Map<String, Object> addAttendXjedDel(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("del_date", new Date());
			
			hrAttendTermendMapper.addAttendXjedDel(entityMap);
			hrAttendTermendMapper.updateDelAmtByAttendCode(entityMap);
			
			retMap.put("msg", "操作成功");
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
	
	/**
	 * 结账
	 */
	@Override
	public Map<String, Object> confirmAttendTermend(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> yearMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			entityMap.put("hr_flag", 1);
			entityMap.put("hr_user", SessionManager.getUserId());
			entityMap.put("hr_date", new Date());
			
			yearMap.put("group_id", SessionManager.getGroupId());
			yearMap.put("hos_id", SessionManager.getHosId());
			yearMap.put("user_id", SessionManager.getUserId());
			
			List<Map<String,Object>> list = hrPeriodMapper.queryHrPeriodByYear(entityMap);
			if(list.size()==0){
				retMap.put("state", "false");
				retMap.put("error", "没有获取到期间信息");
				return retMap;
			}
			
			//修改结账状态
			hrAttendTermendMapper.updateHrPeriodFlag(entityMap);
			
			//新期间
			String year_before = entityMap.get("year").toString();
			String month_before = entityMap.get("month").toString();
			String year = "", month = "";
			
			if("12".equals(month_before)){
				year = (Integer.parseInt(year_before) + 1) + "";
				month = "01";
			}else{
				year = year_before;
				month = "00" + (Integer.parseInt(month_before) + 1);
				month = month.substring(month.length() - 2, month.length());
			}

			retMap.put("year", year);
			retMap.put("month", month);
			retMap.put("year_before", year_before);
			retMap.put("month_before", month_before);
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * 反结账
	 */
	@Override
	public Map<String, Object> unConfirmAttendTermend(Map<String, Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			entityMap.put("hr_flag", 0);
			entityMap.put("hr_user", null);
			entityMap.put("hr_date", null);
			
			List<Map<String,Object>> list = hrPeriodMapper.queryHrPeriodByYear(entityMap);
			if(list.size()==0){
				retMap.put("state", "false");
				retMap.put("error", "没有获取到期间信息");
				return retMap;
			}
			//修改结账状态
			hrAttendTermendMapper.updateHrPeriodFlag(entityMap);
			
			//新期间
			String year = entityMap.get("year").toString();
			String month = entityMap.get("month").toString();
			String year_before = "", month_before = "";
			
			if("01".equals(month)){
				year_before = (Integer.parseInt(year) - 1) + "";
				month = "12";
			}else{
				year_before = year;
				month_before = "00" + (Integer.parseInt(month) - 1);
				month_before = month_before.substring(month_before.length() - 2, month_before.length());
			}

			retMap.put("year", year);
			retMap.put("month", month);
			retMap.put("year_before", year_before);
			retMap.put("month_before", month_before);
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
}
