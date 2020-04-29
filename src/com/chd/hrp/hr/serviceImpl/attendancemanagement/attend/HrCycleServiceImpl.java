package com.chd.hrp.hr.serviceImpl.attendancemanagement.attend;

import java.util.ArrayList;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrCycleMapper;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrPeriodMapper;
import com.chd.hrp.hr.service.attendancemanagement.attend.HrCycleService;
import com.github.pagehelper.PageInfo;
/**
 * 考勤周期设置
 * 
 * @author Administrator
 *
 */
@Service("hrCycleService")
public class HrCycleServiceImpl implements HrCycleService{

	private static Logger logger = Logger.getLogger(HrCycleServiceImpl.class);

	@Resource(name = "hrCycleMapper")
	private final HrCycleMapper hrCycleMapper = null;
	
	
	@Resource(name = "hrPeriodMapper")
	private final HrPeriodMapper hrPeriodMapper = null;
	
	//保存
	@Override
	public String addCycle(Map<String, Object> entityMap) {
		try {
			entityMap.put("oper", SessionManager.getUserId());
			entityMap.put("operate_date", new Date());
			entityMap.put("attdent_year", MyConfig.getCurAccYear());
			
			//看考勤周期表本年是否有数据
			List<Map<String,Object>> flag = hrPeriodMapper.queryHrFlag(entityMap);
			if(flag.size()>0){
				for (Map<String, Object> map : flag) {
					if(map.get("HR_FLAG").toString().equals("1")){
						return "{\"msg\":\"考勤期间已经结账，不能再次保存！\",\"state\":\"false\"}";
					}
				}
			
			}
			//看考勤周期表本年是否有数据
			Map<String,Object> yearData = hrCycleMapper.queryHrCycle(entityMap);
			if(yearData == null){
				hrCycleMapper.add(entityMap);
			}else{
				hrCycleMapper.update(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	//生成
	@Override
	public String createCycle(Map<String, Object> entityMap) {
		try{
			String startDate = MyConfig.getCurAccYear();
			entityMap.put("attdent_year", startDate);
			List<Map<String,Object>> list = hrPeriodMapper.queryHrPeriod(entityMap);
		if(list.size() > 0){
				return "{\"error\":\"已经有考勤期间存在，不能再次生成！\",\"state\":\"false\"}";
			}
			
			String begin_time = "";
			String end_time = "";
			String acc_month = "";
			int i;
			int y = 0;
			Integer year_month = Integer.valueOf(0);
			
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
			
			
			Map<String,Object> yearData = hrCycleMapper.queryHrCycle(entityMap);
			if(yearData==null){
				return "{\"error\":\"请先设置周期方式！\",\"state\":\"false\"}";
			}
			entityMap.put("create_user", SessionManager.getUserCode());
			Map<String,Object> startData = hrPeriodMapper.queryStartData(entityMap);
			if(startData.size()!=0 && startData!=null && startData.get("start_year").toString().equals(startDate)){
				i=Integer.parseInt(startData.get("start_month").toString());
			}else{
				i=1;
				y=12;
			}
			for ( ;i < 13; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("attdent_year", startDate);
				map.put("attend_year", startDate);
				map.put("hr_flag", "0");
				if("0".equals(yearData.get("attdent_cycle_style"))){
					if (i < 10) {
						begin_time = startDate + "-" + "0" + i + "-"+ DateUtil.getMinMonthDate(year_month.toString(), new StringBuilder("0").append(i).toString());
						end_time = startDate + "-" + "0" + i + "-"+ DateUtil.getMaxMonthDate(year_month.toString(), new StringBuilder("0").append(i).toString());
						acc_month = "0" + i;
					} else {
						begin_time = startDate + "-" + i + "-" + DateUtil.getMinMonthDate(year_month.toString(), String.valueOf(i));
						end_time = startDate + "-" + i + "-" + DateUtil.getMaxMonthDate(year_month.toString(), String.valueOf(i));
						acc_month = String.valueOf(i);
					}
					
					if (acc_month.equals("02") ) {
						if (Integer.parseInt(startDate) % 4 == 0 && Integer.parseInt(startDate) % 100 != 0 || Integer.parseInt(startDate) % 400 == 0) {
							end_time = end_time.substring(0, end_time.lastIndexOf("-")) + "-29";
						} else {
							end_time = end_time.substring(0, end_time.lastIndexOf("-")) + "-28";
						}
					}
				}else{
					if ("0".equals(yearData.get("attdent_cycle_beg").toString())) {
						if (i < 10) {
							begin_time = startDate + "-" + "0" + i + "-"+ DateUtil.getMinMonthDate(year_month.toString(), new StringBuilder("0").append(i).toString());
							end_time = startDate + "-" + "0" + i + "-"+ DateUtil.getMaxMonthDate(year_month.toString(), new StringBuilder("0").append(i).toString());
							acc_month = "0" + i;
						} else {
							begin_time = startDate + "-" + i + "-" + DateUtil.getMinMonthDate(year_month.toString(), String.valueOf(i));
							end_time = startDate + "-" + i + "-" + DateUtil.getMaxMonthDate(year_month.toString(), String.valueOf(i));
							acc_month = String.valueOf(i);
						}
					} else if (i < 11) {
						if ((i != 1) && (i != 10)) {
							begin_time = startDate + "-" + "0" + (i - 1) + "-" + yearData.get("attdent_cycle_beg");
							end_time = startDate + "-" + "0" + i + "-" + yearData.get("attdent_cycle_end");
							acc_month = "0" + i;
						} else if (i == 10) {
							begin_time = startDate + "-" + "0" + (i - 1) + "-" + yearData.get("attdent_cycle_beg");
							end_time = startDate + "-" + i + "-" + yearData.get("attdent_cycle_end");
							acc_month = String.valueOf(i);
						} else {
							/*if(y==12){begin_time = startDate + "-" + y + "-" + "01";}else{
							
							}*/
							begin_time = startDate + "-" + "0" + i + "-" + "01";
							end_time = startDate + "-" + "0" + i + "-" + yearData.get("attdent_cycle_end");
							acc_month = "0" + i;
						}
					} else if (i != 12) {
						begin_time = startDate + "-" + (i - 1) + "-" + yearData.get("attdent_cycle_beg");
						end_time = startDate + "-" + i + "-" + yearData.get("attdent_cycle_end");
						acc_month = String.valueOf(i);
					} else {
						begin_time = startDate + "-" + (i - 1) + "-" + yearData.get("attdent_cycle_beg");
						end_time = startDate + "-" + i + "-" + "31";
						acc_month = String.valueOf(i);
					}
				}
				
				map.put("begin_date", DateUtil.stringToDate(begin_time, "yyyy-MM-dd"));
				map.put("end_date", DateUtil.stringToDate(end_time, "yyyy-MM-dd"));
				map.put("attend_month", acc_month);
				addList.add(map);
			}
			
			if(addList.size() > 0){
				hrPeriodMapper.addBatchHrPeriod(addList);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String queryHrCycle(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			entityMap.put("attdent_year", MyConfig.getCurAccYear());
			Map<String,Object> hrCycleMap = hrCycleMapper.queryHrCycle(entityMap);
			
			//当未做保存操作前,数据为空。返回此JSON串避免出现异常
			if(hrCycleMap == null){
				return "{\"attdent_cycle_style\":\"2\"}";
			}
			return ChdJson.toJson(hrCycleMap);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 考勤期间查询
	 */
	@Override
	public String queryHrPeriod(Map<String, Object> entityMap)throws DataAccessException {
           entityMap.put("attdent_year", SessionManager.getAcctYear());
		List<Map<String,Object>> list = ChdJson.toListLower((List<Map<String,Object>>) hrPeriodMapper.queryHrPeriod(entityMap));
		return ChdJson.toJson(list);
		
	}
	@Override
	public String deleteCycle(Map<String, Object> entityMap)
			throws DataAccessException {
		try{
		String startDate = MyConfig.getCurAccYear();
				entityMap.put("attdent_year", startDate);
				
				List<Map<String,Object>> list = hrPeriodMapper.queryHrResult(entityMap);
				if(list.size() > 0){
					return "{\"error\":\"已经有考勤期间被使用，不能清除！\",\"state\":\"false\"}";
				}
				
				hrPeriodMapper.deleteCycle(entityMap);
				return "{\"msg\":\"清除成功.\",\"state\":\"true\"}";
		}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
	}
}
