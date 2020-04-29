package com.chd.hrp.hr.serviceImpl.attendancemanagement.accrest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.attendancemanagement.accrest.HrAccRestStatisticsYearMapper;
import com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestStatisticsYearService;
import com.github.pagehelper.PageInfo;

@Service("hrAccRestStatisticsYearService")
public class HrAccRestStatisticsYearServiceImpl  implements HrAccRestStatisticsYearService{
	private static Logger logger = Logger.getLogger(HrAccRestStatisticsYearServiceImpl.class);
	@Resource(name = "hrAccRestStatisticsYearMapper")
	private final HrAccRestStatisticsYearMapper hrAccRestStatisticsYearMapper = null;

	/**
	 * 查询
	 */
	@Override
	public String queryAccRestStatisticsYear(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		String begin_date = "";
		String end_date = "";
		String obegin_date = "";
		String oend_date = "";
		entityMap.put("year", SessionManager.getAcctYear());
	/*	if(entityMap.get("acc_year_month") != null && entityMap.get("acc_year_month") != ""){
		//上个月份
		String last_year;
		String last_month;
		int year = Integer.parseInt(entityMap.get("acc_year_month").toString().split("-")[0]);
		int month = Integer.parseInt(entityMap.get("acc_year_month").toString().split("-")[1]);
		if(month==1){
			last_month=String.valueOf(12);
			last_year = String.valueOf(year-1);
		}else if(month<=10){
			last_month="0"+String.valueOf(month-1);
			last_year = String.valueOf(year);
		}else{
			last_month=String.valueOf(month-1);
			last_year = String.valueOf(year);
		}
		entityMap.put("year_month", last_year+"-"+last_month);
		
		//查询考勤周期设置
		Map<String,Object> cycMap = hrAccRestStatisticsYearMapper.queryHrCycDate(entityMap);
		if("1".equals(cycMap.get("ATTDENT_CYCLE_STYLE"))){
			begin_date = entityMap.get("acc_year_month").toString()+"-"+cycMap.get("ATTDENT_CYCLE_BEG");
			end_date = entityMap.get("acc_year_month").toString()+"-"+cycMap.get("ATTDENT_CYCLE_END");
		}else{
			begin_date = entityMap.get("acc_year_month").toString()+"-01";
			//计算一个月有多少天
			
			int day = getMaxDayByYearMonth(year,month);
			end_date = entityMap.get("acc_year_month").toString()+"-"+String.valueOf(day);
		}
		
		obegin_date = begin_date+" 00:00:00";
		oend_date = end_date+" 23:59:59";
		
		entityMap.put("begin_date", DateUtil.stringToDate(begin_date.toString(), "yyyy-MM-dd"));
		entityMap.put("end_date", DateUtil.stringToDate(end_date.toString(), "yyyy-MM-dd"));
		entityMap.put("obegin_date", DateUtil.stringToDate(obegin_date.toString(), "yyyy-MM-dd"));
		entityMap.put("oend_date", DateUtil.stringToDate(oend_date.toString(), "yyyy-MM-dd"));
		}*/
	/*	//期初天数的取值方式
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) hrAccRestStatisticsMapper.queryHrAttdentAccstatIsUse(entityMap);
		if(dataList.size() > 0){
			entityMap.put("is_use", "1");
		}else{
			entityMap.put("is_use", "0");
		}*/
		
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = ChdJson.toListLower((List<Map<String, Object>>) hrAccRestStatisticsYearMapper.query(entityMap));
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = ChdJson.toListLower((List<Map<String, Object>>) hrAccRestStatisticsYearMapper.query(entityMap, rowBounds));
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}

	public int getMaxDayByYearMonth(int year, int month) {
		  Calendar calendar = Calendar.getInstance();
		  calendar.set(Calendar.YEAR, year - 1);
		  calendar.set(Calendar.MONTH, month);
		  return calendar.getActualMaximum(Calendar.DATE);
	}

	@Override
	public String overtimeYearQuery(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAccRestStatisticsYearMapper.overtimeYearQuery(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAccRestStatisticsYearMapper.overtimeYearQuery(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}



	@Override
	public String applyingLeavesYearQuery(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAccRestStatisticsYearMapper.applyingLeavesQueryYear(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAccRestStatisticsYearMapper.applyingLeavesQueryYear(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	/**
	 * 更新
	 */
	@Override
	public String updateAccRestStatisticsYear(Map<String, Object> entityMap) throws DataAccessException {
		try{
			/*
			List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();//保存List
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();//修改List
			
			entityMap.put("attend_accyear", entityMap.get("acc_year_month").toString().split("-")[0]);
			entityMap.put("attend_accmonth", entityMap.get("acc_year_month").toString().split("-")[1]);
			
			//查看当月存在的员工数据
			List<Map<String,Object>> existsList = ChdJson.toListLower(hrAccRestStatisticsYearMapper.queryAccYearMonthExists(entityMap));
			Map<String,Object> existsMap = new HashMap<String,Object>();
			if(existsList.size() > 0){
				for(Map<String,Object> eMap : existsList){
					String key = eMap.get("emp_id").toString()+eMap.get("attend_accyear").toString()+eMap.get("attend_accmonth").toString();
					if(existsMap.get(key)==null){
						existsMap.put(key, eMap);
					}
				}
			}
			
			Map<String,Object> sMap ;
			String jsonKey;
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = json.iterator();
			while(iterator.hasNext()){
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				sMap = new HashMap<String,Object>();
				sMap.put("group_id", SessionManager.getGroupId());
				sMap.put("hos_id", SessionManager.getHosId());
				sMap.put("emp_id", jsonObj.get("emp_id"));
				sMap.put("attend_accyear", entityMap.get("attend_accyear"));
				sMap.put("attend_accmonth", entityMap.get("attend_accmonth"));
				sMap.put("attend_accmonthbeg", jsonObj.get("attend_accdays"));
				sMap.put("attend_accmonthend", jsonObj.get("end_days"));
				jsonKey = jsonObj.get("emp_id")+entityMap.get("attend_accyear").toString()+entityMap.get("attend_accmonth").toString();
				//存在放入updateList
				if(existsMap.get(jsonKey)!=null){
					updateList.add(sMap);
				}else{
					existsMap.put(jsonKey, sMap);
					saveList.add(sMap);
				}
			}
			
			if(saveList.size()>0){
				hrAccRestStatisticsYearMapper.addBatch(saveList);
			}
			if(updateList.size()>0){
				hrAccRestStatisticsYearMapper.updateBatch(updateList);
			}*/
			
			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryAccRestStatisticsYearPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		/*entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		String begin_date = "";
		String end_date = "";
		String obegin_date = "";
		String oend_date = "";
		
		//上个月份
		String last_year;
		String last_month;
		int year = Integer.parseInt(entityMap.get("acc_year_month").toString().split("-")[0]);
		int month = Integer.parseInt(entityMap.get("acc_year_month").toString().split("-")[1]);
		if(month==1){
			last_month=String.valueOf(12);
			last_year = String.valueOf(year-1);
		}else if(month<10){
			last_month="0"+String.valueOf(month-1);
			last_year = String.valueOf(year);
		}else{
			last_month=String.valueOf(month-1);
			last_year = String.valueOf(year);
		}
		entityMap.put("year_month", last_year+"-"+last_month);
		
		//查询考勤周期设置
		Map<String,Object> cycMap = hrAccRestStatisticsYearMapper.queryHrCycDate(entityMap);
		if("1".equals(cycMap.get("ATTDENT_CYCLE_STYLE"))){
			begin_date = entityMap.get("acc_year_month").toString()+"-"+cycMap.get("ATTDENT_CYCLE_BEG");
			end_date = entityMap.get("acc_year_month").toString()+"-"+cycMap.get("ATTDENT_CYCLE_END");
		}else{
			begin_date = entityMap.get("acc_year_month").toString()+"-01";
			//计算一个月有多少天
			
			int day = getMaxDayByYearMonth(year,month);
			end_date = entityMap.get("acc_year_month").toString()+"-"+String.valueOf(day);
		}
		
		obegin_date = begin_date+" 00:00:00";
		oend_date = end_date+" 23:59:59";
		
		entityMap.put("begin_date", DateUtil.stringToDate(begin_date.toString(), "yyyy-MM-dd"));
		entityMap.put("end_date", DateUtil.stringToDate(end_date.toString(), "yyyy-MM-dd"));
		entityMap.put("obegin_date", DateUtil.stringToDate(obegin_date.toString(), "yyyy-MM-dd HH:mm:ss"));
		entityMap.put("oend_date", DateUtil.stringToDate(oend_date.toString(), "yyyy-MM-dd HH:mm:ss"));
		
		//期初天数的取值方式
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) hrAccRestStatisticsYearMapper.queryHrAttdentAccstatIsUse(entityMap);
		if(dataList.size() > 0){
			entityMap.put("is_use", "1");
		}else{
			entityMap.put("is_use", "0");
		}
		*/
		entityMap.put("year", SessionManager.getAcctYear());
		return (List<Map<String, Object>>) hrAccRestStatisticsYearMapper.queryByPrint(entityMap);
	}
	
}