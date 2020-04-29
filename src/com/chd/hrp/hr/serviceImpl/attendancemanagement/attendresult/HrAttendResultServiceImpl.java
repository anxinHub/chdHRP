package com.chd.hrp.hr.serviceImpl.attendancemanagement.attendresult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultManageMapper;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultService;
import com.github.pagehelper.PageInfo;


/**
 * 考勤数据维护
 * @author Administrator
 *
 */
@Service("hrAttendResultService")
public class HrAttendResultServiceImpl  implements HrAttendResultService{
	private static Logger logger = Logger.getLogger(HrAttendResultServiceImpl.class);
	@Resource(name = "hrAttendResultMapper")
	private final HrAttendResultMapper hrAttendResultMapper = null;
	@Resource(name = "hrAttendResultManageMapper")
	private final HrAttendResultManageMapper hrAttendResultManageMapper = null;

	/**
	 * 查询表头
	 */
	@Override
	public Map<String, Object> queryAttendResultHead(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			//校验是否有期间参数
			if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			String yearMonth = entityMap.get("year_month").toString();
			entityMap.put("attend_year", yearMonth.substring(0, 4));
			entityMap.put("attend_month", yearMonth.substring(4, 6));
			//获取考勤周期
			Map<String, Object> periodMap = hrAttendResultMapper.queryHrPeriod(entityMap);
			
			//解析周期生成表头
			boolean is_first = true;
			StringBuilder jsonHead = new StringBuilder();
			jsonHead.append("[{");
			String month = "", year = "";
			if(periodMap != null && periodMap.size() > 0){
				//格式为yyyy-MM-dd
				String begin_date = periodMap.get("BEGIN_DATE").toString();
				String end_date = periodMap.get("END_DATE").toString();
				String byear_month = begin_date.substring(0, 7);
				String eyear_month = end_date.substring(0, 7);
				boolean is_flag = false;  //是否自然月
				//自然月
				if(byear_month.equals(eyear_month)){
					is_flag = true;
				}
				//本月开始日期
				int begin_day = Integer.parseInt(begin_date.substring(8, 10));
				//本月结束日期
				int end_day = 0;
				if(is_flag){
					end_day = Integer.parseInt(end_date.substring(8, 10));

					//拼表头
					is_first = true;
					year = byear_month.substring(0, 4);
					month = byear_month.substring(5, 7);
					while(begin_day <= end_day){
						if(!is_first){
							jsonHead.append("},{");	
						}
						jsonHead.append("\"display\": \"").append(month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						jsonHead.append(", \"name\": \"").append("d"+begin_day).append("\"");
						jsonHead.append(", \"date\": \"").append(year + "-" + month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						
						begin_day++;
						is_first = false;
					}
				}else{
					if(byear_month.endsWith("01") || byear_month.endsWith("03") || byear_month.endsWith("05") || 
							byear_month.endsWith("07") || byear_month.endsWith("08") || byear_month.endsWith("10") || 
							byear_month.endsWith("12")){
						
						end_day = 31;
					}else if(byear_month.endsWith("02")){
						if(Integer.parseInt(byear_month.substring(0, 4)) % 4 == 0){

							end_day = 29;
						}else{

							end_day = 28;
						}
					}else{

						end_day = 30;
					}
					//拼表头
					is_first = true;
					year = byear_month.substring(0, 4);
					month = byear_month.substring(5, 7);
					while(begin_day <= end_day){
						if(!is_first){
							jsonHead.append("},{");	
						}
						jsonHead.append("\"display\": \"").append(month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						jsonHead.append(", \"name\": \"").append("d"+begin_day).append("\"");
						jsonHead.append(", \"date\": \"").append(year + "-" + month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						
						begin_day++;
						is_first = false;
					}
					//本月开始日期
					begin_day = 1;
					//本月结束日期
					end_day = Integer.parseInt(end_date.substring(8, 10));
					//拼表头 
					year = eyear_month.substring(0, 4);
					month = eyear_month.substring(5, 7);
					while(begin_day <= end_day){
						jsonHead.append("},{");	
						jsonHead.append("\"display\": \"").append(month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						jsonHead.append(", \"name\": \"").append("d"+begin_day).append("\"");
						jsonHead.append(", \"date\": \"").append(year + "-" + month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						
						begin_day++;
					}
				}
			}else{
				return retMap;
			}

			jsonHead.append("}]");
			retMap.put("jsonHead", jsonHead.toString());
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("获取表头失败");
		}
		
		return retMap;
	}

	/**
	 * 查询表头Old
	 */
	public Map<String, Object> queryAttendResultHeadOld(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			//校验是否有期间参数
			if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			//获取考勤周期
			Map<String, Object> cycleMap = hrAttendResultMapper.queryAttendCycle(entityMap);
			
			//解析周期生成表头
			String yearMonth = entityMap.get("year_month").toString().replace("-", "").replace(".", "");
			boolean is_first = true;
			StringBuilder jsonHead = new StringBuilder();
			jsonHead.append("[{");
			String month = "", year = "";
			
			if(cycleMap != null && cycleMap.size() > 0){
				//类型
				retMap.put("style", cycleMap.get("ATTDENT_CYCLE_STYLE"));
				//非自然月
				if("1".equals(cycleMap.get("ATTDENT_CYCLE_STYLE").toString())){
					String beforeYearMonth = yearMonth.endsWith("01") ? ((Integer.parseInt(yearMonth.substring(0, 4)) - 1) + "12"): ((Integer.parseInt(yearMonth) - 1) + "");
					//上月开始日期
					int begin_day = Integer.parseInt(cycleMap.get("ATTDENT_CYCLE_BEG").toString());
					//上月结束日期
					int end_day = 0;
					if(beforeYearMonth.endsWith("01") || beforeYearMonth.endsWith("03") || beforeYearMonth.endsWith("05") || 
							beforeYearMonth.endsWith("07") || beforeYearMonth.endsWith("08") || beforeYearMonth.endsWith("10") || 
							beforeYearMonth.endsWith("12")){
						
						end_day = 31;
					}else if(beforeYearMonth.endsWith("02")){
						if(Integer.parseInt(beforeYearMonth.substring(0, 4)) % 4 == 0){

							end_day = 29;
						}else{

							end_day = 28;
						}
					}else{

						end_day = 30;
					}
					//拼表头
					is_first = true;
					year = beforeYearMonth.substring(0, 4);
					month = beforeYearMonth.substring(4, 6);
					while(begin_day <= end_day){
						if(!is_first){
							jsonHead.append("},{");	
						}
						jsonHead.append("\"display\": \"").append(month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						jsonHead.append(", \"name\": \"").append("d"+begin_day).append("\"");
						jsonHead.append(", \"date\": \"").append(year + "-" + month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						
						begin_day++;
						is_first = false;
					}
					//本月开始日期
					begin_day = 1;
					//本月结束日期
					end_day = Integer.parseInt(cycleMap.get("ATTDENT_CYCLE_END").toString());
					//拼表头 
					year = yearMonth.substring(0, 4);
					month = yearMonth.substring(4, 6);
					while(begin_day <= end_day){
						jsonHead.append("},{");	
						jsonHead.append("\"display\": \"").append(month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						jsonHead.append(", \"name\": \"").append("d"+begin_day).append("\"");
						jsonHead.append(", \"date\": \"").append(year + "-" + month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						
						begin_day++;
					}
				}else{//自然月
					//本月开始日期
					int begin_day = 1;
					//本月结束日期
					int end_day = 0;
					if(yearMonth.endsWith("01") || yearMonth.endsWith("03") || yearMonth.endsWith("05") || 
							yearMonth.endsWith("07") || yearMonth.endsWith("08") || yearMonth.endsWith("10") || 
							yearMonth.endsWith("12")){
						
						end_day = 31;
					}else if(yearMonth.endsWith("02")){
						if(Integer.parseInt(yearMonth.substring(0, 4)) % 4 == 0){

							end_day = 29;
						}else{

							end_day = 28;
						}
					}else{

						end_day = 30;
					}
					//拼表头
					is_first = true;
					year = yearMonth.substring(0, 4);
					month = yearMonth.substring(4, 6);
					while(begin_day <= end_day){
						if(!is_first){
							jsonHead.append("},{");	
						}
						jsonHead.append("\"display\": \"").append(month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						jsonHead.append(", \"name\": \"").append("d"+begin_day).append("\"");
						jsonHead.append(", \"date\": \"").append(year + "-" + month + (begin_day < 10 ? "-0" : "-")  + begin_day).append("\"");
						
						begin_day++;
						is_first = false;
					}
				}
			}else{
				return retMap;
			}

			jsonHead.append("}]");
			retMap.put("jsonHead", jsonHead.toString());
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("获取表头失败");
		}
		
		return retMap;
	}
	
	/**
	 * 查询
	 */
	@Override
	public String queryAttendResult(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		
		/*if (sysPage.getTotal() == -1) {
			List<Map<String,Object>> list = ChdJson.toListLower((List<Map<String,Object>>) hrAttendResultMapper.queryAttendResult(entityMap));
			return ChdJson.toJson(list);
		} else {*/
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = hrAttendResultMapper.queryAttendResult(entityMap, rowBounds);
			
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
//		}
	}
	
	/**
	 * 查询明细
	 */
	@Override
	public String queryAttendResultDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		//转日期格式
		entityMap.put("attend_date", DateUtil.stringToDate(entityMap.get("attend_date").toString(), "yyyy-MM-dd"));
		
		List<Map<String,Object>> list = hrAttendResultMapper.queryAttendResultDetail(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * 生成
	 */
	@Override
	public Map<String, Object> addBatchAttendResult(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			
			Map<String, StringBuilder> setMap = new HashMap<String, StringBuilder>();
			Map<String, Object> commonMap = new HashMap<String, Object>();
			Map<Integer, Object> HolidayMap = new HashMap<Integer, Object>();

		//	List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
			
			commonMap.put("group_id", SessionManager.getGroupId());
			commonMap.put("hos_id", SessionManager.getHosId());
			commonMap.put("year_month", entityMap.get("year_month").toString());
			
		
		
			String key = "";
			StringBuilder setSql = new StringBuilder();
			String[] attend_codes = null;
			Map<String, Object> detailMap = null;
			String itemVal = "";
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());

			//校验是否有期间参数
			if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			String yearMonth = entityMap.get("year_month").toString();
			entityMap.put("attend_year", yearMonth.substring(0, 4));
			entityMap.put("attend_month", yearMonth.substring(4, 6));
			//获取考勤周期
			Map<String, Object> periodMap = hrAttendResultMapper.queryHrPeriod(entityMap);
			//获取考勤项目
			List<Map<String, Object>>itemMap = hrAttendResultMapper.queryHrItem(entityMap);
			//获取节假日
			List<Map<String, Object>> holidMap = hrAttendResultMapper.queryHrNotWork(entityMap);
			List<Map<String, Object>> empMap=hrAttendResultMapper.queryEmpInfoByDept(entityMap);
			//解析周期生成表头
			boolean is_first = true;
			StringBuffer sql= new StringBuffer();
			StringBuffer sqlValue= new StringBuffer();
			String month = "", year = "";
		  String attend_code="";
		  String attend_name="";
			for (Map<String, Object> map : itemMap) {
				
				if(map.get("IS_DEFAULT")!=null&&map.get("IS_DEFAULT").toString().equals("1")){
					attend_code=map.get("ATTEND_CODE").toString();
					attend_name=map.get("ATTEND_NAME").toString();
				}
				
			}
			for (Map<String, Object> dateMap : holidMap) {
				if (periodMap != null && periodMap.size() > 0) {
					int key1 = Integer.parseInt(dateMap.get("ATTEND_DATE").toString().substring(8, 10));
					// 日期格式yyy-MM-dd
					String begin_date = periodMap.get("BEGIN_DATE").toString();
					String end_date = periodMap.get("END_DATE").toString();
					String byear_month = begin_date.substring(0, 7);
					String eyear_month = end_date.substring(0, 7);
					if (dateMap.get("ATTEND_DATE").toString().substring(0, 7).equals(byear_month)) {
						HolidayMap.put(key1, dateMap.get("ATTEND_DATE").toString());
					}
				}

			}
			if(attend_code.equals("")&&attend_name.equals("")){

				String str= "{\"error\":\"生成失败,没有默认考勤项目，请添加默认考勤项目!\"}";
	             return JSONObject.parseObject(str);

	}
				if(periodMap != null && periodMap.size() > 0){
				
					//格式为yyyy-MM-dd
					String begin_date = periodMap.get("BEGIN_DATE").toString();
					String end_date = periodMap.get("END_DATE").toString();
					String byear_month = begin_date.substring(0, 7);
					String eyear_month = end_date.substring(0, 7);
					boolean is_flag = false;  //是否自然月
					//自然月
					if(byear_month.equals(eyear_month)){
						is_flag = true;
					}
					//本月开始日期
					int begin_day = Integer.parseInt(begin_date.substring(8, 10));
					//本月结束日期
					int end_day = 0;
					if(is_flag){
						end_day = Integer.parseInt(end_date.substring(8, 10));

						//拼表头
						is_first = true;
						year = byear_month.substring(0, 4);
						month = byear_month.substring(5, 7);
						while(begin_day <= end_day){
							if (!HolidayMap.containsKey(begin_day)) {
							sql.append("d"+begin_day).append(",");
							sqlValue.append("'"+attend_name+"'").append(",");
							begin_day++;
							is_first = false;
							} else{
								begin_day++;
							}

						}	
						
					}else{
						if(byear_month.endsWith("01") || byear_month.endsWith("03") || byear_month.endsWith("05") || 
								byear_month.endsWith("07") || byear_month.endsWith("08") || byear_month.endsWith("10") || 
								byear_month.endsWith("12")){
							
							end_day = 31;
						}else if(byear_month.endsWith("02")){
							if(Integer.parseInt(byear_month.substring(0, 4)) % 4 == 0){

								end_day = 29;
							}else{

								end_day = 28;
							}
						}else{

							end_day = 30;
						}
						//拼表头
						is_first = true;
						year = byear_month.substring(0, 4);
						month = byear_month.substring(5, 7);
						while(begin_day <= end_day){
							if (!HolidayMap.containsKey(begin_day)) {
							sql.append("d"+begin_day).append(",");
							sqlValue.append("'"+attend_name+"'").append(",");
							begin_day++;
							is_first = false;
							}else{
								begin_day++;
							}
						
						}
						//本月开始日期
						begin_day = 1;
						//本月结束日期
						end_day = Integer.parseInt(end_date.substring(8, 10));
						//拼表头 
						year = eyear_month.substring(0, 4);
						month = eyear_month.substring(5, 7);
						while(begin_day <= end_day){
							if (!HolidayMap.containsKey(begin_day)) {
							sql.append("d"+begin_day).append(",");
							sqlValue.append("'"+attend_name+"'").append(",");
							begin_day++;
							//解析主表
							}else{
								begin_day++;
							}
						}
					}
					
					}else{
					return retMap;
				}
			
         for (Map<String, Object> map : empMap) {
        	 entityMap.put("emp_id", map.get("EMP_ID"));
        	 
        	 
        	 int state1=hrAttendResultMapper.queryState(entityMap);
    			
    			if(state1>0){

    				String str= "{\"error\":\"生成失败,考勤数据已经上报!\"}";
    	           //  return JSONObject.parseObject(str);
    	             
                     continue;
    	}
        	 
        
				//格式为yyyy-MM-dd
				String begin_date = periodMap.get("BEGIN_DATE").toString();
				String end_date = periodMap.get("END_DATE").toString();
				String byear_month = begin_date.substring(0, 7);
				String eyear_month = end_date.substring(0, 7);
			
				
				boolean is_flag = false;  //是否自然月
				//自然月
				if(byear_month.equals(eyear_month)){
					is_flag = true;
				}
				//本月开始日期
				int begin_day = Integer.parseInt(begin_date.substring(8, 10));
				//本月结束日期
				int end_day = 0;
				if(is_flag){
					end_day = Integer.parseInt(end_date.substring(8, 10));

					//拼表头
					is_first = true;
					year = byear_month.substring(0, 4);
					month = byear_month.substring(5, 7);
					while(begin_day <= end_day){
						if (!HolidayMap.containsKey(begin_day)) {
						

						//解析主表
						key = map.get("DEPT_ID") + "-" + map.get("EMP_ID");
						if(setMap.containsKey(key)){
							setSql = setMap.get(key);
							
							setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
						}else{
							setSql = new StringBuilder();
							setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
						}
						setMap.put(key, setSql);
						itemVal = "1";
						
						
						detailMap = new HashMap<String, Object>();
						detailMap.put("dept_id_c", map.get("DEPT_ID"));
						detailMap.put("emp_id", map.get("EMP_ID"));
						detailMap.put("attend_date",byear_month+"-"+begin_day);
						detailMap.put("attend_code",attend_code);
						detailMap.put("attend_val", itemVal);
						detailList.add(detailMap);
					
						begin_day++;
						is_first = false;
					}else{
						begin_day++;
					}
					}
				}else{
					if(byear_month.endsWith("01") || byear_month.endsWith("03") || byear_month.endsWith("05") || 
							byear_month.endsWith("07") || byear_month.endsWith("08") || byear_month.endsWith("10") || 
							byear_month.endsWith("12")){
						
						end_day = 31;
					}else if(byear_month.endsWith("02")){
						if(Integer.parseInt(byear_month.substring(0, 4)) % 4 == 0){

							end_day = 29;
						}else{

							end_day = 28;
						}
					}else{

						end_day = 30;
					}
					//拼表头
					is_first = true;
					year = byear_month.substring(0, 4);
					month = byear_month.substring(5, 7);
					while(begin_day <= end_day){
						if (!HolidayMap.containsKey(begin_day)) {
					
						//解析主表
						key = map.get("DEPT_ID") + "-" + map.get("EMP_ID");
						if(setMap.containsKey(key)){
							setSql = setMap.get(key);
							
							setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
						}else{
							setSql = new StringBuilder();
							setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
						}
						setMap.put(key, setSql);
					
							
							itemVal = "1";
							
							/*if(itemMap.containsValue("全勤")){
								
							}*/
								detailMap = new HashMap<String, Object>();
								detailMap.put("dept_id_c", map.get("DEPT_ID"));
								detailMap.put("emp_id", map.get("EMP_ID"));
								detailMap.put("attend_date",byear_month+"-"+begin_day);
								detailMap.put("attend_code", attend_code);
								detailMap.put("attend_val", itemVal);
								detailList.add(detailMap);
							
								begin_day++;
								is_first = false;
					
					}else{
						begin_day++;
					}
					}
					//本月开始日期
					begin_day = 1;
					//本月结束日期
					end_day = Integer.parseInt(end_date.substring(8, 10));
					//拼表头 
					year = eyear_month.substring(0, 4);
					month = eyear_month.substring(5, 7);
					while(begin_day <= end_day){
					
						if (!HolidayMap.containsKey(begin_day)) {
						//解析主表
						key = map.get("DEPT_ID") + "-" + map.get("EMP_ID");
						if(setMap.containsKey(key)){
							setSql = setMap.get(key);
							
							setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
						}else{
							setSql = new StringBuilder();
							setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
						}
						setMap.put(key, setSql);
					
							
							itemVal = "1";
							
							
								detailMap = new HashMap<String, Object>();
								detailMap.put("dept_id_c", map.get("DEPT_ID"));
								detailMap.put("emp_id", map.get("EMP_ID"));
								detailMap.put("attend_date",byear_month+"-"+begin_day);
								detailMap.put("attend_code", attend_code);
								detailMap.put("attend_val", itemVal);
								detailList.add(detailMap);
							
								begin_day++;
								entityMap.put("group_id", SessionManager.getGroupId());
								entityMap.put("hos_id", SessionManager.getHosId());
								
						}else{
							begin_day++;
						}
					}
				}
			
         }
			
			entityMap.put("sql", sql.substring(0, sql.length()-1) );
			entityMap.put("sqlValue", sqlValue.substring(0, sqlValue.length()-1) );
			
			
		

			
		
		/*	
			if(mainList.size() > 0){
				//更新主表
				hrAttendResultMapper.updateBatchMain(commonMap, mainList);
				
			}*/
			for (int i=0;  i<  detailList.size(); i++) {
				updateList.add(detailList.get(i));
			
			if(updateList.size()>0&&updateList.size() == 500|| i == detailList.size() - 1){
				
				
			//删除明细表
			hrAttendResultMapper.deleteBatchDetailResult(entityMap, updateList);
			//删除主表数据
			hrAttendResultMapper.deleteBatchResult(entityMap, updateList);
			updateList=new ArrayList<Map<String,Object>>();
			
			}
			}
			hrAttendResultMapper.addEmpDateByDept(entityMap);
			
			//insertBatchDetailResult(commonMap, detailList);
		
			
		
			retMap.put("msg", "生成成功");
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("生成失败");
		}
		return retMap;
	}
	@Override
	public Map<String, Object> insertBatchDetailResult(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
		Map<String, StringBuilder> setMap = new HashMap<String, StringBuilder>();
		Map<String, Object> commonMap = new HashMap<String, Object>();
		Map<Integer, Object> HolidayMap = new HashMap<Integer, Object>();
		List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		
		commonMap.put("group_id", SessionManager.getGroupId());
		commonMap.put("hos_id", SessionManager.getHosId());
		commonMap.put("year_month", entityMap.get("year_month").toString());
		
		
		
	
		String key = "";
		StringBuilder setSql = new StringBuilder();
		String[] attend_codes = null;
		Map<String, Object> detailMap = null;
		String itemVal = "";
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());

		//校验是否有期间参数
		if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
			return retMap;
		}
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		String yearMonth = entityMap.get("year_month").toString();
		entityMap.put("attend_year", yearMonth.substring(0, 4));
		entityMap.put("attend_month", yearMonth.substring(4, 6));
		//获取考勤周期
		Map<String, Object> periodMap = hrAttendResultMapper.queryHrPeriod(entityMap);
		//获取考勤项目
		List<Map<String, Object>>itemMap = hrAttendResultMapper.queryHrItem(entityMap);
		// 查询节假日
		List<Map<String, Object>> holidMap = hrAttendResultMapper.queryHrNotWork(entityMap);
		List<Map<String, Object>> empMap=hrAttendResultMapper.queryEmpInfoByDept(entityMap);
		//解析周期生成表头
		boolean is_first = true;
		StringBuffer sql= new StringBuffer();
		StringBuffer sqlValue= new StringBuffer();
		String month = "", year = "";
	  String attend_code="";
	  String attend_name="";
		for (Map<String, Object> dateMap : holidMap) {
			if (periodMap != null && periodMap.size() > 0) {
				int key1 = Integer.parseInt(dateMap.get("ATTEND_DATE").toString().substring(8, 10));
				//日期格式yyy-MM-dd
				String begin_date = periodMap.get("BEGIN_DATE").toString();
				String end_date = periodMap.get("END_DATE").toString();
				String byear_month = begin_date.substring(0, 7);
				String eyear_month = end_date.substring(0, 7);
				if (dateMap.get("ATTEND_DATE").toString().substring(0, 7).equals(byear_month)) {
					HolidayMap.put(key1, dateMap.get("ATTEND_DATE").toString());
				}
			}
		}
		for (Map<String, Object> map : itemMap) {
			
			if(map.get("IS_DEFAULT")!=null&&map.get("IS_DEFAULT").toString().equals("1")){
				attend_code=map.get("ATTEND_CODE").toString();
				attend_name=map.get("ATTEND_NAME").toString();
			}
			
		}
		if(attend_code.equals("")&&attend_name.equals("")){

			String str= "{\"error\":\"生成失败,没有默认考勤项目，请添加默认考勤项目!\"}";
             return JSONObject.parseObject(str);

}
			if(periodMap != null && periodMap.size() > 0){
			
				//格式为yyyy-MM-dd
				String begin_date = periodMap.get("BEGIN_DATE").toString();
				String end_date = periodMap.get("END_DATE").toString();
				String byear_month = begin_date.substring(0, 7);
				String eyear_month = end_date.substring(0, 7);
				boolean is_flag = false;  //是否自然月
				//自然月
				if(byear_month.equals(eyear_month)){
					is_flag = true;
				}
				//本月开始日期
				int begin_day = Integer.parseInt(begin_date.substring(8, 10));
				//本月结束日期
				int end_day = 0;
				if(is_flag){
					end_day = Integer.parseInt(end_date.substring(8, 10));

					//拼表头
					is_first = true;
					year = byear_month.substring(0, 4);
					month = byear_month.substring(5, 7);
					while(begin_day <= end_day){
						if (!HolidayMap.containsKey(begin_day)) {
						sql.append("d"+begin_day).append(",");
						sqlValue.append("'"+attend_name+"'").append(",");
						begin_day++;
						is_first = false;
						}else{
							begin_day++;
						}
					}	
					
				}else{
					if(byear_month.endsWith("01") || byear_month.endsWith("03") || byear_month.endsWith("05") || 
							byear_month.endsWith("07") || byear_month.endsWith("08") || byear_month.endsWith("10") || 
							byear_month.endsWith("12")){
						
						end_day = 31;
					}else if(byear_month.endsWith("02")){
						if(Integer.parseInt(byear_month.substring(0, 4)) % 4 == 0){

							end_day = 29;
						}else{

							end_day = 28;
						}
					}else{

						end_day = 30;
					}
					//拼表头
					is_first = true;
					year = byear_month.substring(0, 4);
					month = byear_month.substring(5, 7);
					while(begin_day <= end_day){
						if (!HolidayMap.containsKey(begin_day)) {
						sql.append("d"+begin_day).append(",");
						sqlValue.append("'"+attend_name+"'").append(",");
						begin_day++;
						is_first = false;
						}else{
							begin_day++;
						}
					
					}
					//本月开始日期
					begin_day = 1;
					//本月结束日期
					end_day = Integer.parseInt(end_date.substring(8, 10));
					//拼表头 
					year = eyear_month.substring(0, 4);
					month = eyear_month.substring(5, 7);
					while(begin_day <= end_day){
						if (!HolidayMap.containsKey(begin_day)) {
						sql.append("d"+begin_day).append(",");
						sqlValue.append("'"+attend_name+"'").append(",");
						begin_day++;
						//解析主表
						}else{
							begin_day++;
						}
					}
				}
				
				}else{
				return retMap;
			}
		
     for (Map<String, Object> map : empMap) {

    	 entityMap.put("emp_id", map.get("EMP_ID"));
    	 
    	 
    	 int state1=hrAttendResultMapper.queryState(entityMap);
			
			if(state1>0){

				String str= "{\"error\":\"生成失败,考勤数据已经上报!\"}";
	            // return JSONObject.parseObject(str);
				continue;

	}
    	 
			//格式为yyyy-MM-dd
			String begin_date = periodMap.get("BEGIN_DATE").toString();
			String end_date = periodMap.get("END_DATE").toString();
			String byear_month = begin_date.substring(0, 7);
			String eyear_month = end_date.substring(0, 7);
			
			boolean is_flag = false;  //是否自然月
			//自然月
			if(byear_month.equals(eyear_month)){
				is_flag = true;
			}
			//本月开始日期
			int begin_day = Integer.parseInt(begin_date.substring(8, 10));
			//本月结束日期
			int end_day = 0;
			if(is_flag){
				end_day = Integer.parseInt(end_date.substring(8, 10));

				//拼表头
				is_first = true;
				year = byear_month.substring(0, 4);
				month = byear_month.substring(5, 7);
				while(begin_day <= end_day){
					
					if (!HolidayMap.containsKey(begin_day)) {

					//解析主表
					key = map.get("DEPT_ID") + "-" + map.get("EMP_ID");
					if(setMap.containsKey(key)){
						setSql = setMap.get(key);
						
						setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
					}else{
						setSql = new StringBuilder();
						setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
					}
					setMap.put(key, setSql);
					itemVal = "1";
					
					
					detailMap = new HashMap<String, Object>();
					detailMap.put("dept_id_c", map.get("DEPT_ID"));
					detailMap.put("emp_id", map.get("EMP_ID"));
					detailMap.put("attend_date",byear_month+"-"+begin_day);
					detailMap.put("attend_code",attend_code);
					detailMap.put("attend_val", itemVal);
					detailList.add(detailMap);
				
					begin_day++;
					is_first = false;
				}else{
					begin_day++;
				}
				}
			}else{
				if(byear_month.endsWith("01") || byear_month.endsWith("03") || byear_month.endsWith("05") || 
						byear_month.endsWith("07") || byear_month.endsWith("08") || byear_month.endsWith("10") || 
						byear_month.endsWith("12")){
					
					end_day = 31;
				}else if(byear_month.endsWith("02")){
					if(Integer.parseInt(byear_month.substring(0, 4)) % 4 == 0){

						end_day = 29;
					}else{

						end_day = 28;
					}
				}else{

					end_day = 30;
				}
				//拼表头
				is_first = true;
				year = byear_month.substring(0, 4);
				month = byear_month.substring(5, 7);
				while(begin_day <= end_day){
					if (!HolidayMap.containsKey(begin_day)) {
				
					//解析主表
					key = map.get("DEPT_ID") + "-" + map.get("EMP_ID");
					if(setMap.containsKey(key)){
						setSql = setMap.get(key);
						
						setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
					}else{
						setSql = new StringBuilder();
						setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
					}
					setMap.put(key, setSql);
				
						
						itemVal = "1";
						
						
							detailMap = new HashMap<String, Object>();
							detailMap.put("dept_id_c", map.get("DEPT_ID"));
							detailMap.put("emp_id", map.get("EMP_ID"));
							detailMap.put("attend_date",byear_month+"-"+begin_day);
							detailMap.put("attend_code", attend_code);
							detailMap.put("attend_val", itemVal);
							detailList.add(detailMap);
						
							begin_day++;
							is_first = false;
					}else{
						begin_day++;
					}
				}
				//本月开始日期
				begin_day = 1;
				//本月结束日期
				end_day = Integer.parseInt(end_date.substring(8, 10));
				//拼表头 
				year = eyear_month.substring(0, 4);
				month = eyear_month.substring(5, 7);
				while(begin_day <= end_day){
					if (!HolidayMap.containsKey(begin_day)) {
					
					//解析主表
					key = map.get("DEPT_ID") + "-" + map.get("EMP_ID");
					if(setMap.containsKey(key)){
						setSql = setMap.get(key);
						
						setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
					}else{
						setSql = new StringBuilder();
						setSql.append(", ").append("d"+begin_day).append(" = ").append("'"+attend_name+"'");
					}
					setMap.put(key, setSql);
				
						
						itemVal = "1";
						
						
							detailMap = new HashMap<String, Object>();
							detailMap.put("dept_id_c", map.get("DEPT_ID"));
							detailMap.put("emp_id", map.get("EMP_ID"));
							detailMap.put("attend_date",byear_month+"-"+begin_day);
							detailMap.put("attend_code", attend_code);
							detailMap.put("attend_val", itemVal);
							detailList.add(detailMap);
						
							begin_day++;
					}else{
						begin_day++;
					}
				}
			}
		
     }
		
		entityMap.put("sql", sql.substring(0, sql.length()-1) );
		entityMap.put("sqlValue", sqlValue.substring(0, sqlValue.length()-1) );
		
		
	

		
		
	
	
		if(detailList.size() > 0){
			

			
			List<Map<String, Object>> stringSqlList= new  ArrayList<Map<String, Object>>();
			
			for (int i=0;  i<  detailList.size(); i++) {
				stringSqlList.add(detailList.get(i));
				if( stringSqlList.size()>0 && stringSqlList.size()==500 || i == detailList.size() - 1) {
					
					//添加明细表
					hrAttendResultMapper.insertBatchDetailResult(commonMap, stringSqlList);
					stringSqlList.clear();
				}
                 
			}
			
		
		}
		retMap.put("msg", "生成成功");
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("生成失败");
		}
		return retMap;
	}
	/**
	 * 添加职工
	 */
	@Override
	public Map<String, Object> addAttendResultEmp(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> statetMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			
			//判断职工是否已存在
			int flag = hrAttendResultMapper.existsByAddEmp(entityMap);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "出勤科室已存在该职工");
				return retMap;
			} 
			statetMap.put("group_id", SessionManager.getGroupId());
			statetMap.put("hos_id", SessionManager.getHosId());
			statetMap.put("user_id", SessionManager.getUserId());
			statetMap.put("dept_id_c", entityMap.get("dept_id_c"));
			statetMap.put("year_month", entityMap.get("year_month"));
	    	 int state1=hrAttendResultMapper.queryState(statetMap);
				
				if(state1>0){

					String str= "{\"error\":\"考勤数据已经上报!\"}";
		             return JSONObject.parseObject(str);

		}
			hrAttendResultMapper.addEmp(entityMap);
			
			retMap.put("msg", "生成成功");
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("生成失败");
		}
		return retMap;
	}
	
	/**
	 * 保存
	 */
	@Override
	public Map<String, Object> addAttendResult(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			Map<String, StringBuilder> setMap = new HashMap<String, StringBuilder>();
			Map<String, Object> commonMap = new HashMap<String, Object>();
			List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			commonMap.put("group_id", SessionManager.getGroupId());
			commonMap.put("hos_id", SessionManager.getHosId());
			commonMap.put("year_month", entityMap.get("year_month").toString());
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			String key = "";
			StringBuilder setSql = new StringBuilder();
			String[] attend_codes = null;
			Map<String, Object> detailMap = null;
			String itemVal = "";
			//解析页面参数
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				//解析主表
				key = jsonObj.getString("dept_id_c") + "-" + jsonObj.getString("emp_id");
				if(setMap.containsKey(key)){
					setSql = setMap.get(key);
					if(jsonObj.getString("column_val") == null){
						setSql.append(", ").append(jsonObj.getString("column")).append(" = ").append(jsonObj.getString("column_val"));
					}else{
						setSql.append(", ").append(jsonObj.getString("column")).append(" = '").append(jsonObj.getString("column_val")).append("'");
					}
				}else{
					setSql = new StringBuilder();
					if(jsonObj.getString("column_val") == null){
						setSql.append(jsonObj.getString("column")).append(" = ").append(jsonObj.getString("column_val"));
					}else{
						setSql.append(jsonObj.getString("column")).append(" = '").append(jsonObj.getString("column_val")).append("'");
					}
				}
				setMap.put(key, setSql);
				//解析明细表
				if(jsonObj.getString("attend_code").length() > 0){
					attend_codes = jsonObj.getString("attend_code").split(";");
					itemVal = "1";
					//如果存在多个考勤项则每个考勤项的天数为0.5天
					if(attend_codes.length > 1){
						itemVal = "0.5";
					}
					for(int i = 0; i < attend_codes.length; i++){
						detailMap = new HashMap<String, Object>();
						detailMap.put("dept_id_c", jsonObj.getString("dept_id_c"));
						detailMap.put("emp_id", jsonObj.getString("emp_id"));
						detailMap.put("attend_date", DateUtil.stringToDate(jsonObj.getString("attend_date"), "yyyy-MM-dd"));
						detailMap.put("attend_code", attend_codes[i]);
						detailMap.put("attend_val", itemVal);
						detailList.add(detailMap);
					}
				}
			}
			
			//解析setMap组装主表数据
			Map<String, Object> mainMap = null;
			for(Map.Entry<String, StringBuilder> entry : setMap.entrySet()){
				mainMap = new HashMap<String, Object>();
				mainMap.put("dept_id_c", entry.getKey().split("-")[0]);
				mainMap.put("emp_id", entry.getKey().split("-")[1]);
				mainMap.put("setSql", entry.getValue().toString());
				
				mainList.add(mainMap);
			}
			
			if(mainList.size() > 0){
				//更新主表
				hrAttendResultMapper.updateBatchMain(commonMap, mainList);
			}
			
			if(detailList.size() > 0){
				//删除明细表
				hrAttendResultMapper.deleteBatchDetail(commonMap, detailList);
				//添加明细表
				hrAttendResultMapper.insertBatchDetail(commonMap, detailList);
			}
			
			retMap.put("msg", "保存成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * 保存明细
	 */
	@Override
	public Map<String, Object> saveAttendResultDetail(Map<String, Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
	int state=hrAttendResultMapper.queryState(entityMap);
			
			if(state>0){
			
				String str= "{\"error\":\"考勤数据已经上报!\"}";
	             return JSONObject.parseObject(str);
			}
			//转日期格式
			entityMap.put("attend_date", DateUtil.stringToDate(entityMap.get("attend_date").toString(), "yyyy-MM-dd"));
			//主表set语句
			entityMap.put("setSql", entityMap.get("col_name").toString() + " = '" + entityMap.get("col_val").toString() + "'");
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> detailMap = null;
			//解析页面参数
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				//解析得明细表List
				detailMap = new HashMap<String, Object>();
				detailMap.put("dept_id_c", entityMap.get("dept_id_c"));
				detailMap.put("emp_id", entityMap.get("emp_id"));
				detailMap.put("attend_date", entityMap.get("attend_date"));
				detailMap.put("attend_code", jsonObj.getString("attend_code"));
				detailMap.put("attend_val", jsonObj.getString("attend_val"));
				
				detailList.add(detailMap);
			}
			
			//更新主表
			hrAttendResultMapper.updateMainByCode(entityMap);

			//删除明细表
			hrAttendResultMapper.deleteDetailByCode(entityMap);
			if(detailList.size() > 0){
				//添加明细表
				hrAttendResultMapper.insertBatchDetail(entityMap, detailList);
			}
			
			retMap.put("msg", "保存成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
	/**
	 * 保存快捷设置明细
	 */
	@Override
	public Map<String, Object> saveAttendResultShortCut( Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
		
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			int state=hrAttendResultMapper.queryState(entityMap);
			
			if(state>0){
			
				String str= "{\"error\":\"考勤数据已经上报!\"}";
	             return JSONObject.parseObject(str);
			}
			StringBuffer sb=new StringBuffer();
			List<Map> alllistVo =JSONArray.parseArray(String.valueOf(entityMap.get("allData")),Map.class);
			
	
			if (alllistVo != null && alllistVo.size() > 0) {
				for (Map map : alllistVo) {
					List<String> date=DateUtil.getDaysByBeinEnd(map.get("beg_time").toString(), map.get("end_time").toString());
					int m = date.size();
					for (int i = 0; i < m; i++) {
						if(detailList.size()>0){
						//对比时间多个班次设置
						for (int j = 0; j < detailList.size(); j++) {
							if( DateUtil.stringToDate(date.get(i).toString(), "yyyy-MM-dd").equals(detailList.get(j).get("attend_date"))){
								if(Integer.parseInt(date.get(i).toString().substring(8, 10))<10){
									if(sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))!=-1){
										sb.replace(sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))+6, sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))+6, map.get("attend_name")+";");
										break;
									}	
								}else{

									if(sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))!=-1){
						
						sb.replace(sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))+7, sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))+7, map.get("attend_name")+";");
						break;
									}	 
								
									
								}
								
							}
						else{
								if(Integer.parseInt(date.get(i).toString().substring(8, 10))<10){
									
									
									if(Integer.parseInt(date.get(i).toString().substring(8, 10))==1){
										sb.append("d"+Integer.parseInt(date.get(i).toString().substring(8, 10))+ " = '" + map.get("attend_name")+ "' , ");		
										break;
									}
									if(Integer.parseInt(date.get(i).toString().substring(8, 10))==2){
										sb.append("d"+Integer.parseInt(date.get(i).toString().substring(8, 10))+ " = '" + map.get("attend_name")+ "' , ");		
										
										break;

									}
									if(Integer.parseInt(date.get(i).toString().substring(8, 10))==3){
										sb.append("d"+Integer.parseInt(date.get(i).toString().substring(8, 10))+ " = '" + map.get("attend_name")+ "' , ");		
										break;

									}
									if(sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))==-1){
									sb.append("d"+Integer.parseInt(date.get(i).toString().substring(8, 10))+ " = '" + map.get("attend_name")+ "' , ");
									}
								}else{
									if(sb.indexOf("d"+Integer.parseInt(date.get(i).toString().substring(8, 10)))==-1){
									sb.append("d"+date.get(i).toString().substring(8, 10)+ " = '" + map.get("attend_name")+ "' , ");
									}
								}
								}
							continue;
						}
						}else{
						if(Integer.parseInt(date.get(i).toString().substring(8, 10))<10){
							sb.append("d"+Integer.parseInt(date.get(i).toString().substring(8, 10))+ " = '" + map.get("attend_name")+ "' , ");
						}else{
							sb.append("d"+date.get(i).toString().substring(8, 10)+ " = '" + map.get("attend_name")+ "' , ");
						}
						}
						
						
					//解析得明细表List
					Map<String, Object> detailMap = new HashMap<String, Object>();
					detailMap.put("dept_id_c", entityMap.get("dept_id_c"));
					detailMap.put("emp_id", entityMap.get("emp_id"));
					detailMap.put("attend_date", entityMap.get("attend_date"));
					detailMap.put("attend_date",  DateUtil.stringToDate(date.get(i).toString(), "yyyy-MM-dd"));
					detailMap.put("attend_code", map.get("attend_code"));
					detailMap.put("attend_name", map.get("attend_name"));
					detailMap.put("attend_val", map.get("attend_val"));
					
					detailList.add(detailMap);
					}
			
						
						
					
				
				
				}
				sb.deleteCharAt(sb.length()-2);
				entityMap.put("setSql", sb);
				
			}
			//更新主表
			hrAttendResultMapper.updateMainByCode(entityMap);

		
			if(detailList.size() > 0){
				//删除明细表
				hrAttendResultMapper.deleteDetailByCodeShortCut(entityMap, detailList);
				//添加明细表
				hrAttendResultMapper.insertBatchDetail(entityMap, detailList);
			}
			entityMap.put("user_id", SessionManager.getUserId());
			//用于重复职工的存放
			Map<String, Map<String, Object>> empMap = new HashMap<String, Map<String,Object>>();
			String empKey = "";
			//获取考勤项目对应的列item
			List<Map<String, Object>> itemList = hrAttendResultManageMapper.queryAttendItemCol(entityMap);
			Map<String, Object> itemMap = new HashMap<String, Object>();
			StringBuilder itemSql = new StringBuilder();
			StringBuilder itemSqlVal = new StringBuilder();
			//解析考勤项目得到公共的考勤Map用于计算
			for(Map<String, Object> map : itemList){
				itemMap.put(map.get("ATTEND_ITEM").toString(), 0);

				itemSql.append(map.get("ATTEND_ITEM").toString()).append(", ");
				
				itemSqlVal.append("#{item.").append(map.get("ATTEND_ITEM").toString()).append(",jdbcType=NUMERIC} ").append(map.get("ATTEND_ITEM").toString()).append(", ");
			}
			entityMap.put("state", 0);
			entityMap.put("oper", SessionManager.getUserId());
			entityMap.put("oper_date", new Date());
			
			entityMap.put("itemSql", itemSql.substring(0, itemSql.length() - 2).toString());
			entityMap.put("itemSqlVal", itemSqlVal.substring(0, itemSqlVal.length() - 2).toString());
			
			//获取考勤结果
			List<Map<String, Object>> resultList = hrAttendResultManageMapper.queryResultData(entityMap);
			Map<String, Object> vMap = null;
			for(Map<String, Object> map : resultList){
				empKey = map.get("dept_id_c").toString() + "@" + map.get("emp_id").toString();
				
				if(empMap.containsKey(empKey)){
					vMap = empMap.get(empKey);
				}else{
					entityMap.put("dept_id_c", map.get("dept_id_c").toString());
					entityMap.put("emp_id", map.get("emp_id").toString());
					 int state1=hrAttendResultMapper.queryState(entityMap);
						
						if(state1==0){

							vMap = new HashMap<String, Object>();
							vMap.put("dept_id_c", map.get("dept_id_c").toString());
							vMap.put("emp_id", map.get("emp_id").toString());
							vMap.put("dept_id_b", map.get("dept_id_b").toString());
							vMap.putAll(itemMap);
				}else{
					continue;
				}
					
				}
				
				vMap.put(map.get("attend_item").toString(), map.get("attend_val"));
				
				empMap.put(empKey, vMap);
		
			}
			int count =hrAttendResultManageMapper.queryCount(entityMap);
			//循环empMap得到增加数据的List
			List<Map<String, Object>> manageList = new ArrayList<Map<String,Object>>();
			for(Entry<String, Map<String, Object>> map : empMap.entrySet()){
				manageList.add(map.getValue());
				if(manageList.size()>0||manageList.size() == 300&&manageList.size() - 1==0){
					if(count>0){
						
					
					//删除数据
					hrAttendResultManageMapper.deleteResultManage(entityMap, manageList);
					//插入数据
					hrAttendResultManageMapper.addBatchResultManage(entityMap, manageList);
					//清空List
					manageList = new ArrayList<Map<String,Object>>();
					}
				}
			}
			
			retMap.put("msg", "保存成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
	/**
	 * 导入
	 */
	@Override
	public Map<String, Object> importAttendResult(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			//从para参数中获取考勤周期
			Map<String, Object> paraMap = JsonListMapUtil.getMap(entityMap.get("para").toString());
			if(paraMap.get("year_month") == null || "".equals(paraMap.get("year_month").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
			
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			Map<String, Object> commonMap = new HashMap<String, Object>();
			List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			commonMap.put("group_id", SessionManager.getGroupId());
			commonMap.put("hos_id", SessionManager.getHosId());
			commonMap.put("acc_year", MyConfig.getCurAccYear());
			commonMap.put("year_month", paraMap.get("year_month").toString());
			
			/**
			 * 科室信息
			 */
			List<Map<String, Object>> deptList = hrAttendResultMapper.queryDeptInfo(commonMap);
			Map<String, String> deptMap = new HashMap<String, String>();
			for(Map<String, Object> dept : deptList){
				deptMap.put(dept.get("DEPT_ID").toString(), dept.get("DEPT_ID").toString());
				deptMap.put(dept.get("DEPT_CODE").toString(), dept.get("DEPT_ID").toString());
				deptMap.put(dept.get("DEPT_NAME").toString(), dept.get("DEPT_ID").toString());
			}
			
			/**
			 * 职工信息
			 */
			List<Map<String, Object>> empList = hrAttendResultMapper.queryEmpInfo(commonMap);
			Map<String, String> empMap = new HashMap<String, String>();
			for(Map<String, Object> emp : empList){
				empMap.put(emp.get("EMP_ID").toString(), emp.get("EMP_ID").toString() + "@" + emp.get("DEPT_ID").toString());
				empMap.put(emp.get("EMP_CODE").toString(), emp.get("EMP_ID").toString() + "@" + emp.get("DEPT_ID").toString());
				empMap.put(emp.get("EMP_NAME").toString(), emp.get("EMP_ID").toString() + "@" + emp.get("DEPT_ID").toString());
			}
			
			/**
			 * 考勤类型
			 */
			List<Map<String, Object>> itemList = hrAttendResultMapper.queryAttendItemInfo(commonMap);
			Map<String, String> itemMap = new HashMap<String, String>();
			for(Map<String, Object> item : itemList){
				itemMap.put(item.get("ITEM_CODE").toString(), item.get("ITEM_CODE").toString());
				itemMap.put(item.get("ITEM_NAME").toString(), item.get("ITEM_CODE").toString());
				itemMap.put(item.get("ITEM_SHORT_NAME").toString(), item.get("ITEM_CODE").toString());
			}
			
			/**
			 * 当月考勤周期
			 */
			Map<String, Object> cycleMap = queryAttendResultHead(commonMap);
			if(cycleMap.get("jsonHead") == null || "[{}]".equals(cycleMap.get("jsonHead").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
			List<Map<String, Object>> cycleList = JsonListMapUtil.getListMap(cycleMap.get("jsonHead").toString());
			
			Map<String, Object> mainMap = null;
			Map<String, Object> detailMap = null;
			Map<String, String> repeatMap = new HashMap<String,String>();
			List<String> rowList = null;
			String dept_id_c = "", emp_id = "";
			StringBuilder setSql = null;
			String[] attend_codes = null;
			String itemVal = "1";
			int index = 1; 
		
			List<Map<String, List<String>>> fieldList = ReadFiles.readFiles(entityMap);
			
			if(fieldList == null || fieldList.size() == 0){

				retMap.put("state", "false");
				retMap.put("error", "导入数据为空");
				return retMap;
			}
			
			for (Map<String, List<String>> map : fieldList) {
				//map为空 跳出循环
				if(map == null){
					continue;
				}
				mainMap = new HashMap<String, Object>();
				//判断科室是否存在
				rowList = map.get("dept_code");
				if("".equals(deptMap.get(rowList.get(1))) || deptMap.get(rowList.get(1)) ==null){
					failureMsg.append("<br/>科室信息: "+rowList.get(1)+" 不存在; ");
					failureNum++;
					continue;
				}else{
					dept_id_c = deptMap.get(rowList.get(1));
				}
				mainMap.put("dept_id_c", dept_id_c);
				commonMap.put("dept_id_c", dept_id_c);
				//判断职工是否存在
				rowList = map.get("emp_code");
				if("".equals(empMap.get(rowList.get(1))) || empMap.get(rowList.get(1)) == null){
					failureMsg.append("<br/>职工信息: "+rowList.get(1)+" 不存在; ");
					failureNum++;
					continue;
				}else{
					emp_id = empMap.get(rowList.get(1)).split("@")[0];
				}
				mainMap.put("emp_id", emp_id);
				mainMap.put("dept_id_b", empMap.get(rowList.get(1)).split("@")[1]);
				
				commonMap.put("emp_id", emp_id);
				
				 int state1=hrAttendResultMapper.queryState(commonMap);
					
					if(state1>0){

						failureMsg.append("<br/>职工信息: "+rowList.get(1)+" 考勤数据已上报; ");
						failureNum++;

			}
				//判断是否重复
				String repeatKey = dept_id_c+emp_id;
				if(repeatMap.containsKey(repeatKey)){
					failureMsg.append("<br/>职工信息: "+rowList.get(1)+" 重复导入; ");
					failureNum++;
					continue;
				}else{
					repeatMap.put(repeatKey, repeatKey);
				}
				
				//循环解析考勤周期
				setSql = new StringBuilder();
				for(Map<String, Object> vMap : cycleList){
					rowList = map.get(vMap.get("name").toString());
					if(rowList.get(1) != null && !"".equals(rowList.get(1))){
						//主表修改语句的set
						setSql.append(vMap.get("name")).append(" = \'").append(rowList.get(1)).append("\', ");
						//明细表
						attend_codes = rowList.get(1).split(";");

						itemVal = "1";
						if(attend_codes.length > 1){
							itemVal = "0.5";
						}
						for(int i = 0; i < attend_codes.length; i++){
							detailMap = new HashMap<String, Object>();
							detailMap.put("dept_id_c", dept_id_c);
							detailMap.put("emp_id", emp_id);
							detailMap.put("attend_date", DateUtil.stringToDate(vMap.get("date").toString(), "yyyy-MM-dd"));
							if("".equals(itemMap.get(attend_codes[i])) || itemMap.get(attend_codes[i]) == null){
								failureMsg.append("<br/>考勤项: "+attend_codes[i]+" 不存在; ");
								failureNum++;
								continue;
							}
							detailMap.put("attend_code", itemMap.get(attend_codes[i]));
							detailMap.put("attend_val", itemVal);
							detailList.add(detailMap);
						}
					}
				}
				if(setSql.length() > 2){
					mainMap.put("setSql", setSql.substring(0, setSql.length() - 2).toString());//由于,号后面有一个空格所以这里为-2
					
					mainList.add(mainMap);
				}else{
					failureMsg.append("<br/>第"+index+"考勤数据不能为空 ");
					failureNum++;
				}
				
				index++;
			}
			
			//存在错误信息
			if (failureNum>0){
				retMap.put("state", "false");
				retMap.put("error", failureMsg.toString());
				return retMap;
			}

			//保存数据
			List<Map<String, Object>> tList = new ArrayList<Map<String,Object>>();
			if(mainList.size() > 0){
				//更新主表
				if(mainList.size() <= 300){
					//添加不存在的职工
					hrAttendResultMapper.addEmpByImp(commonMap, mainList);
					//更新职工考勤数据
					successNum = hrAttendResultMapper.updateBatchMain(commonMap, mainList);
				}else{
					for(Map<String, Object> tMap : mainList){
						tList.add(tMap);
						if(tList.size() == 300){
							//添加不存在的职工
							hrAttendResultMapper.addEmpByImp(commonMap, tList);
							//更新职工考勤数据
							successNum += hrAttendResultMapper.updateBatchMain(commonMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//添加不存在的职工
						hrAttendResultMapper.addEmpByImp(commonMap, tList);
						//更新职工考勤数据
						successNum += hrAttendResultMapper.updateBatchMain(commonMap, tList);
						tList = new ArrayList<Map<String,Object>>();
					}
				}
			}
			
			if(detailList.size() > 0){
				if(mainList.size() <= 300){
					//删除明细表
					hrAttendResultMapper.deleteBatchDetail(commonMap, detailList);
					//添加明细表
					hrAttendResultMapper.insertBatchDetail(commonMap, detailList);
				}else{
					for(Map<String, Object> tMap : detailList){
						tList.add(tMap);
						if(tList.size() == 300){
							//删除明细表
							hrAttendResultMapper.deleteBatchDetail(commonMap, tList);
							//添加明细表
							hrAttendResultMapper.insertBatchDetail(commonMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//删除明细表
						hrAttendResultMapper.deleteBatchDetail(commonMap, tList);
						//添加明细表
						hrAttendResultMapper.insertBatchDetail(commonMap, tList);
					}
				}
			}
			
			retMap.put("msg", "导入成功");
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
	
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryAttendResultPrint(Map<String, Object> entityMap) throws DataAccessException {
	    
		return hrAttendResultMapper.queryAttendResultPrint(entityMap);
	}
	
	/**
	 * 删除
	 */
	@Override
	public Map<String, Object> deleteAttendResult(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择删除的数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			
			//状态判断
			entityMap.put("check_state", "1");
			int flag = hrAttendResultMapper.existsByState(entityMap, list);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在考勤数据已提交的职工，请重新选择！");
				return retMap;
			}
			//删除数据
			hrAttendResultMapper.deleteAttendResult(entityMap, list);
			
			retMap.put("msg", "删除成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("删除失败");
		}
		
		return retMap;
	}
	
	/**
	 * 导入排班
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> importPb(Map<String, Object> entityMap) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			//校验是否有期间参数
			if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
				
				retMap.put("state", "false");
				retMap.put("error", "请选择考勤周期");
				return retMap;
			}
			entityMap.put("year", entityMap.get("year_month").toString().substring(0, 4));
			entityMap.put("month", entityMap.get("year_month").toString().substring(4, 6));
			
			Map<String, Map<String, String>> setMap = new HashMap<String, Map<String,String>>();
			List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			
			//获取排班数据
			List<Map<String, Object>> pbList = hrAttendResultMapper.queryPbData(entityMap);
			
			//解析表头获取日期与主表列字段对应关系
			Map<String, Object> cycleMap = queryAttendResultHead(entityMap);
			if(cycleMap.get("jsonHead") == null || "[{}]".equals(cycleMap.get("jsonHead").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
			List<Map<String, Object>> cycleList = JsonListMapUtil.getListMap(cycleMap.get("jsonHead").toString());
			Map<String, String> dateMap = new HashMap<String, String>();
			for(Map<String, Object> vMap : cycleList){
				dateMap.put(vMap.get("date").toString(), vMap.get("name").toString());
			}
			
			//第一次循环解析出主表和每天考勤项目对应的考勤天数
			String setKey = "", colKey = "", valKey = "", attend_date = "";
			Map<String, String> colMap = null;
			Map<String, String> valMap = new HashMap<String, String>(); 
			for(Map<String, Object> vMap : pbList){
				
				setKey = vMap.get("dept_id_c").toString() + "@" + vMap.get("emp_id").toString() + "@" + vMap.get("dept_id_b");
				
				if(setMap.containsKey(setKey)){
					colMap = setMap.get(setKey);
				}else{
					colMap = new HashMap<String, String>();
				}
				attend_date = DateUtil.dateToString((Date)vMap.get("attend_date"), "yyyy-MM-dd");
				colKey = dateMap.get(attend_date);
				if(colMap.containsKey(colKey)){
					colMap.put(colKey, colMap.get(colKey) + ";" + vMap.get("short_name").toString());
				}else{
					colMap.put(colKey, vMap.get("short_name").toString());
				}
				
				setMap.put(setKey, colMap);
				
				//同一人同一天存在多个考勤项则考勤项对应的值为0.5天
				valKey = setKey + attend_date;
				if(valMap.containsKey(valKey)){
					valMap.put(valKey, "0.5");
				}else{
					valMap.put(valKey, "1");
				}
			}
			//解析得主表List
			Map<String, Object> mainMap = null;
			StringBuilder setSql = new StringBuilder();
			String[] setKeys = null;
			for(Map.Entry<String, Map<String, String>> entry : setMap.entrySet()){
				setKeys = entry.getKey().split("@");
				mainMap = new HashMap<String, Object>();
				mainMap.put("dept_id_c", setKeys[0]);
				mainMap.put("emp_id", setKeys[1]);
				mainMap.put("dept_id_b", setKeys[2]);
				setSql = new StringBuilder();
				for(Map.Entry<String, String> map : entry.getValue().entrySet()){
					setSql.append(map.getKey()).append(" = '").append(map.getValue()).append("'").append(", ");
				}
				mainMap.put("setSql", setSql.substring(0, setSql.length() - 2).toString());
				
				mainList.add(mainMap);
			}
			
			//循环第二次得明细数据
			Map<String, Object> detailMap = null;
			for(Map<String, Object> vMap : pbList){
				//处理明细表
				detailMap = new HashMap<String, Object>();
				detailMap.put("dept_id_c", vMap.get("dept_id_c").toString());
				detailMap.put("emp_id", vMap.get("emp_id").toString());
				detailMap.put("attend_date", (Date)vMap.get("attend_date"));
				detailMap.put("attend_code", vMap.get("attend_code").toString());

				valKey = vMap.get("dept_id_c").toString() + "@" + vMap.get("emp_id").toString() + "@" + vMap.get("dept_id_b") + DateUtil.dateToString((Date)vMap.get("attend_date"), "yyyy-MM-dd");
				detailMap.put("attend_val", valMap.get(valKey));  
				
				detailList.add(detailMap);
			}

			//保存数据
			List<Map<String, Object>> tList = new ArrayList<Map<String,Object>>();
			if(mainList.size() > 0){
				//更新主表
				if(mainList.size() <= 300){
					//添加不存在的职工
					hrAttendResultMapper.addEmpByImp(entityMap, mainList);
					//更新职工考勤数据
					hrAttendResultMapper.updateBatchMain(entityMap, mainList);
				}else{
					for(Map<String, Object> tMap : mainList){
						tList.add(tMap);
						if(tList.size() == 300){
							//添加不存在的职工
							hrAttendResultMapper.addEmpByImp(entityMap, tList);
							//更新职工考勤数据
							hrAttendResultMapper.updateBatchMain(entityMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//添加不存在的职工
						hrAttendResultMapper.addEmpByImp(entityMap, tList);
						//更新职工考勤数据
						hrAttendResultMapper.updateBatchMain(entityMap, tList);
						tList = new ArrayList<Map<String,Object>>();
					}
				}
			}
			
			if(detailList.size() > 0){
				if(mainList.size() <= 300){
					//删除明细表
					hrAttendResultMapper.deleteBatchDetail(entityMap, detailList);
					//添加明细表
					hrAttendResultMapper.insertBatchDetail(entityMap, detailList);
				}else{
					for(Map<String, Object> tMap : detailList){
						tList.add(tMap);
						if(tList.size() == 300){
							//删除明细表
							hrAttendResultMapper.deleteBatchDetail(entityMap, tList);
							//添加明细表
							hrAttendResultMapper.insertBatchDetail(entityMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//删除明细表
						hrAttendResultMapper.deleteBatchDetail(entityMap, tList);
						//添加明细表
						hrAttendResultMapper.insertBatchDetail(entityMap, tList);
					}
				}
			}
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * 导入加班
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> importJb(Map<String, Object> entityMap) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			//校验是否有期间参数
			if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
				
				retMap.put("state", "false");
				retMap.put("error", "请选择考勤周期");
				return retMap;
			}
			entityMap.put("year", entityMap.get("year_month").toString().substring(0, 4));
			entityMap.put("month", entityMap.get("year_month").toString().substring(4, 6));
			
			Map<String, Map<String, String>> setMap = new HashMap<String, Map<String,String>>();
			List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			
			//获取加班数据
			List<Map<String, Object>> jbList = hrAttendResultMapper.queryJbData(entityMap);
			
			//解析表头获取日期与主表列字段对应关系
			Map<String, Object> cycleMap = queryAttendResultHead(entityMap);
			if(cycleMap.get("jsonHead") == null || "[{}]".equals(cycleMap.get("jsonHead").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
			List<Map<String, Object>> cycleList = JsonListMapUtil.getListMap(cycleMap.get("jsonHead").toString());
			Map<String, String> dateMap = new HashMap<String, String>();
			for(Map<String, Object> vMap : cycleList){
				dateMap.put(vMap.get("date").toString(), vMap.get("name").toString());
			}
			
			//第一次循环解析出主表和每天考勤项目对应的考勤天数
			String setKey = "", colKey = "";
			Map<String, String> colMap = null;
			for(Map<String, Object> vMap : jbList){
				
				setKey = vMap.get("dept_id_c").toString() + "@" + vMap.get("emp_id").toString() + "@" + vMap.get("dept_id_b");
				
				if(setMap.containsKey(setKey)){
					colMap = setMap.get(setKey);
				}else{
					colMap = new HashMap<String, String>();
				}
			
				colKey = dateMap.get(vMap.get("attend_date").toString());
				if(colMap.containsKey(colKey)){
					colMap.put(colKey, colMap.get(colKey) + ";" + vMap.get("short_name").toString());
				}else{
					colMap.put(colKey, vMap.get("short_name").toString());
				}
				
				setMap.put(setKey, colMap);
				
			}
			//解析得主表List
			Map<String, Object> mainMap = null;
			StringBuilder setSql = new StringBuilder();
			String[] setKeys = null;
			for(Map.Entry<String, Map<String, String>> entry : setMap.entrySet()){
				setKeys = entry.getKey().split("@");
				mainMap = new HashMap<String, Object>();
				mainMap.put("dept_id_c", setKeys[0]);
				mainMap.put("emp_id", setKeys[1]);
				mainMap.put("dept_id_b", setKeys[2]);
				for(Map.Entry<String, String> map : entry.getValue().entrySet()){
					setSql = new StringBuilder();
					setSql.append(map.getKey()).append(" = '").append(map.getValue()).append("'").append(", ");
				}
				mainMap.put("setSql", setSql.substring(0, setSql.length() - 2).toString());
				
				mainList.add(mainMap);
			}
			
			//循环第二次得明细数据
			Map<String, Object> detailMap = null;
			for(Map<String, Object> vMap : jbList){
				//处理明细表
				detailMap = new HashMap<String, Object>();
				detailMap.put("dept_id_c", vMap.get("dept_id_c").toString());
				detailMap.put("emp_id", vMap.get("emp_id").toString());
				detailMap.put("attend_date", DateUtil.stringToDate(vMap.get("attend_date").toString(), "yyyy-MM-dd"));
				detailMap.put("attend_code", vMap.get("attend_code").toString());
				detailMap.put("attend_val", vMap.get("attend_val").toString());  
				
				detailList.add(detailMap);
			}

			//保存数据
			List<Map<String, Object>> tList = new ArrayList<Map<String,Object>>();
			if(mainList.size() > 0){
				//更新主表
				if(mainList.size() <= 300){
					//添加不存在的职工
					hrAttendResultMapper.addEmpByImp(entityMap, mainList);
					//更新职工考勤数据
					hrAttendResultMapper.updateBatchMain(entityMap, mainList);
				}else{
					for(Map<String, Object> tMap : mainList){
						tList.add(tMap);
						if(tList.size() == 300){
							//添加不存在的职工
							hrAttendResultMapper.addEmpByImp(entityMap, tList);
							//更新职工考勤数据
							hrAttendResultMapper.updateBatchMain(entityMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//添加不存在的职工
						hrAttendResultMapper.addEmpByImp(entityMap, tList);
						//更新职工考勤数据
						hrAttendResultMapper.updateBatchMain(entityMap, tList);
						tList = new ArrayList<Map<String,Object>>();
					}
				}
			}
			
			if(detailList.size() > 0){
				if(mainList.size() <= 300){
					//删除明细表
					hrAttendResultMapper.deleteBatchDetail(entityMap, detailList);
					//添加明细表
					hrAttendResultMapper.insertBatchDetail(entityMap, detailList);
				}else{
					for(Map<String, Object> tMap : detailList){
						tList.add(tMap);
						if(tList.size() == 300){
							//删除明细表
							hrAttendResultMapper.deleteBatchDetail(entityMap, tList);
							//添加明细表
							hrAttendResultMapper.insertBatchDetail(entityMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//删除明细表
						hrAttendResultMapper.deleteBatchDetail(entityMap, tList);
						//添加明细表
						hrAttendResultMapper.insertBatchDetail(entityMap, tList);
					}
				}
			}
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * 导入休假
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> importXj(Map<String, Object> entityMap) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			//校验是否有期间参数
			if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
				
				retMap.put("state", "false");
				retMap.put("error", "请选择考勤周期");
				return retMap;
			}
			entityMap.put("year", entityMap.get("year_month").toString().substring(0, 4));
			entityMap.put("month", entityMap.get("year_month").toString().substring(4, 6));
			
			Map<String, Map<String, String>> setMap = new HashMap<String, Map<String,String>>();
			List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			//获取休假数据
			List<Map<String, Object>> xjList = hrAttendResultMapper.queryXjData(entityMap);
			
			List<Map<String, Object>> xjNewList=new ArrayList<Map<String,Object>>();
			//重新组合休假数据
			for (int i = 0; i < xjList.size(); i++) {
				
				
				List<String> date=DateUtil.getDaysByBeinEnd(xjList.get(i).get("attend_xjbegdate").toString(), xjList.get(i).get("attend_xjenddate").toString());				
				int m = date.size();
				for (int j = 0; j < m; j++) {
					Map<String, Object> xjMap= new HashMap<String, Object>();
					xjMap.put("attend_date", date.get(j).toString());	
					xjMap.put("emp_id", xjList.get(i).get("emp_id").toString());
					xjMap.put("dept_id_c", xjList.get(i).get("dept_id"));
					xjMap.put("dept_id_b", xjList.get(i).get("dept_id"));
					xjMap.put("attend_code", xjList.get(i).get("attend_code").toString());
					xjMap.put("short_name", xjList.get(i).get("short_name").toString());
					
					xjNewList.add(xjMap);
				}
				
			}
			
			
			//解析表头获取日期与主表列字段对应关系
			Map<String, Object> cycleMap = queryAttendResultHead(entityMap);
			if(cycleMap.get("jsonHead") == null || "[{}]".equals(cycleMap.get("jsonHead").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
			
			List<Map<String, Object>> cycleList = JsonListMapUtil.getListMap(cycleMap.get("jsonHead").toString());
			Map<String, String> dateMap = new HashMap<String, String>();
			for(Map<String, Object> vMap : cycleList){
				dateMap.put(vMap.get("date").toString(), vMap.get("name").toString());
			}
			//第一次循环解析出主表和每天考勤项目对应的考勤天数
			String setKey = "", colKey = "", valKey = "", attend_date = "";
			Map<String, String> colMap = null;
			Map<String, String> valMap = new HashMap<String, String>(); 
			for(Map<String, Object> vMap : xjNewList){
				
				setKey = vMap.get("dept_id_c").toString() + "@" + vMap.get("emp_id").toString()+ "@" + vMap.get("dept_id_b") ;
				
				if(setMap.containsKey(setKey)){
					colMap = setMap.get(setKey);
				}else{
					colMap = new HashMap<String, String>();
				}
				attend_date = vMap.get("attend_date").toString();
				colKey = dateMap.get(attend_date);
				if(colMap.containsKey(colKey)){
					colMap.put(colKey, colMap.get(colKey) + ";" + vMap.get("short_name").toString());
				}else{
					colMap.put(colKey, vMap.get("short_name").toString());
				}
				
				setMap.put(setKey, colMap);
				
				//同一人同一天存在多个考勤项则考勤项对应的值为0.5天
				valKey = setKey + attend_date;
				if(valMap.containsKey(valKey)){
					valMap.put(valKey, "0.5");
				}else{
					valMap.put(valKey, "1");
				}
			}
			//解析得主表List
			Map<String, Object> mainMap = null;
			StringBuilder setSql = new StringBuilder();
			String[] setKeys = null;
			for(Map.Entry<String, Map<String, String>> entry : setMap.entrySet()){
				setKeys = entry.getKey().split("@");
				mainMap = new HashMap<String, Object>();
				mainMap.put("dept_id_c", setKeys[0]);
				mainMap.put("emp_id", setKeys[1]);
				mainMap.put("dept_id_b", setKeys[2]);
				setSql = new StringBuilder();
				for(Map.Entry<String, String> map : entry.getValue().entrySet()){
					setSql.append(map.getKey()).append(" = '").append(map.getValue()).append("'").append(", ");
				}
				mainMap.put("setSql", setSql.substring(0, setSql.length() - 2).toString());
				
				mainList.add(mainMap);
			}
			
			//循环第二次得明细数据
			Map<String, Object> detailMap = null;
			for(Map<String, Object> vMap : xjNewList){
				//处理明细表
				detailMap = new HashMap<String, Object>();
				detailMap.put("dept_id_c", vMap.get("dept_id_c").toString());
				detailMap.put("emp_id", vMap.get("emp_id").toString());
				detailMap.put("attend_date", DateUtil.stringToDate(vMap.get("attend_date").toString(), "yyyy-MM-dd"));
				detailMap.put("attend_code", vMap.get("attend_code").toString());

				valKey = vMap.get("dept_id_c").toString() + "@" + vMap.get("emp_id").toString() + "@" + vMap.get("dept_id_b") +vMap.get("attend_date").toString();
				detailMap.put("attend_val", valMap.get(valKey));  
				
				detailList.add(detailMap);
			}

			//保存数据
			List<Map<String, Object>> tList = new ArrayList<Map<String,Object>>();
			if(mainList.size() > 0){
				//更新主表
				if(mainList.size() <= 300){
					//添加不存在的职工
					hrAttendResultMapper.addEmpByImp(entityMap, mainList);
					//更新职工考勤数据
					hrAttendResultMapper.updateBatchMain(entityMap, mainList);
				}else{
					for(Map<String, Object> tMap : mainList){
						tList.add(tMap);
						if(tList.size() == 300){
							//添加不存在的职工
							hrAttendResultMapper.addEmpByImp(entityMap, tList);
							//更新职工考勤数据
							hrAttendResultMapper.updateBatchMain(entityMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//添加不存在的职工
						hrAttendResultMapper.addEmpByImp(entityMap, tList);
						//更新职工考勤数据
						hrAttendResultMapper.updateBatchMain(entityMap, tList);
						tList = new ArrayList<Map<String,Object>>();
					}
				}
			}
			
			if(detailList.size() > 0){
				if(mainList.size() <= 300){
					//删除明细表
					hrAttendResultMapper.deleteBatchDetail(entityMap, detailList);
					//添加明细表
					hrAttendResultMapper.insertBatchDetail(entityMap, detailList);
				}else{
					for(Map<String, Object> tMap : detailList){
						tList.add(tMap);
						if(tList.size() == 300){
							//删除明细表
							hrAttendResultMapper.deleteBatchDetail(entityMap, tList);
							//添加明细表
							hrAttendResultMapper.insertBatchDetail(entityMap, tList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//删除明细表
						hrAttendResultMapper.deleteBatchDetail(entityMap, tList);
						//添加明细表
						hrAttendResultMapper.insertBatchDetail(entityMap, tList);
					}
				}
			}
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
    /**
     * 批量修改
     */
	@Override
	public Map<String, Object> saveAttendResultShortPL(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
		
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			@SuppressWarnings("rawtypes")
			List<Map> alllistVo =JSONArray.parseArray(String.valueOf(entityMap.get("allData")),Map.class);
			@SuppressWarnings("rawtypes")
			List<Map> emplistVo =JSONArray.parseArray(String.valueOf(entityMap.get("emp_count")),Map.class);
			Map<String, Object> keyMap=new HashMap<String, Object>();
	
			if (alllistVo != null && alllistVo.size() > 0) {
				
				for (Map map : alllistVo) {
					StringBuffer sb=new StringBuffer();
					String beg_time=null;
				
					List<String> date=DateUtil.getDaysByBeinEnd(map.get("beg_time").toString(), map.get("end_time").toString());
					if(entityMap.get("beg_time")==null){
						entityMap.put("beg_time", map.get("beg_time").toString());
					}else {
						if(   !DateUtil.compareDate(DateUtil.stringToDate(entityMap.get("beg_time").toString(), "yyyy-MM-dd"), DateUtil.stringToDate(map.get("beg_time").toString(), "yyyy-MM-dd"))){
							entityMap.put("beg_time", map.get("beg_time").toString());
						}else{
							entityMap.put("beg_time", entityMap.get("beg_time").toString());

						}
						
					}
				

					entityMap.put("end_time", map.get("end_time").toString());
					int m = date.size();
					for (int i = 0; i < m; i++) {
						
					
						if(Integer.parseInt(date.get(i).toString().substring(8, 10))<10){
							sb.append("d"+Integer.parseInt(date.get(i).toString().substring(8, 10))+ " = '" + map.get("attend_name")+ "' , ");
						}else{
							sb.append("d"+date.get(i).toString().substring(8, 10)+ " = '" + map.get("attend_name")+ "' , ");
						}
						
						
						
						
							
			
					
			
						Map<String, Object> entity=new HashMap<String, Object>();
						
						entity.put("emp_id",map.get("emp_id"));
						entity.put("group_id", SessionManager.getGroupId());
						entity.put("hos_id", SessionManager.getHosId());
						entity.put("year_month",entityMap.get("year_month"));
						//entity.put("dept_id_c", e);
						
				int state1=hrAttendResultMapper.queryState(entity);
				
				if(state1>0){
				
					String str= "{\"error\":\"考勤数据已经上报!\"}";
		             return JSONObject.parseObject(str);
				}
					//解析得明细表List
					Map<String, Object> detailMap = new HashMap<String, Object>();
					
					String emp=map.get("emp_id").toString()+date.get(i)+map.get("attend_code")+map.get("attend_val");
					if(!keyMap.containsKey(emp)){
						
					
						int dept_id_c=hrAttendResultMapper.queryStateDeptId(entity);
					detailMap.put("dept_id_c", dept_id_c);
				
					detailMap.put("emp_id",map.get("emp_id"));
					
					entityMap.put("emp_id", map.get("emp_id"));
					entityMap.put("dept_id_c", dept_id_c);
					//detailMap.put("attend_date",date.get(i));
					detailMap.put("attend_date", date.get(i));
					detailMap.put("attend_date",  DateUtil.stringToDate(date.get(i).toString(), "yyyy-MM-dd"));
					detailMap.put("attend_code", map.get("attend_code"));
					detailMap.put("attend_name", map.get("attend_name"));
					detailMap.put("attend_val", map.get("attend_val"));
					
					detailList.add(detailMap);
					}
					keyMap.put(map.get("emp_id").toString()+date.get(i)+map.get("attend_code")+map.get("attend_val"), map.get("emp_id"));
						
			}
					
			
						
				
					sb.deleteCharAt(sb.length()-2);
					entityMap.put("setSql", sb);
				
					//更新主表
					hrAttendResultMapper.updateMainByCodePL(entityMap);
					if(detailList.size() > 0){
						
						/*	//删除明细表
							hrAttendResultMapper.deleteDetailByCodeShortCut(entityMap, detailList);
							//添加明细表
							hrAttendResultMapper.insertBatchDetail(entityMap, detailList);*/
							
							List<Map<String, Object>> stringSqlList= new  ArrayList<Map<String, Object>>();
							List<String> deleteSqlList= new  ArrayList<String>();
							StringBuffer deleteSql=new StringBuffer();
							deleteSql.append(" DELETE FROM hr_attend_result_d  WHERE group_id = '"+entityMap.get("group_id")+"' and hos_id = '"+entityMap.get("hos_id")+"' and year_month= '"+entityMap.get("year_month")+"' and emp_id in ("+entityMap.get("emp_id")+") and  dept_id_c in ("+entityMap.get("dept_id_c")+")");
							
						
							deleteSql.append(" and attend_date >= to_date( '"+entityMap.get("beg_time")+"','yyyy-mm-dd') and attend_date <= to_date( '"+entityMap.get("end_time")+"','yyyy-mm-dd')");
						
							deleteSqlList.add(deleteSql.toString());
							//删除明细表
							hrAttendResultMapper.deleteDetailByCodeShortCut(entityMap,detailList);
							
							for (int i1=0;  i1<  detailList.size(); i1++) {
								stringSqlList.add(detailList.get(i1));
								
								//detailList.get(i).put("attend_date", DateUtil.stringToDate(detailList.get(i).get("attend_date").toString(), "yyyy-MM-dd"));
								if( stringSqlList.size()>0 && stringSqlList.size()==500 || i1 == detailList.size() - 1) {
									
							
									
									//添加明细表
									hrAttendResultMapper.insertBatchDetail(entityMap, stringSqlList);
									stringSqlList.clear();


								}
								}
				                 
							}
						
						
			
				
				
				entityMap.put("user_id", SessionManager.getUserId());
				//用于重复职工的存放
				Map<String, Map<String, Object>> empMap = new HashMap<String, Map<String,Object>>();
				String empKey = "";
				//获取考勤项目对应的列item
				List<Map<String, Object>> itemList = hrAttendResultManageMapper.queryAttendItemCol(entityMap);
				Map<String, Object> itemMap = new HashMap<String, Object>();
				StringBuilder itemSql = new StringBuilder();
				StringBuilder itemSqlVal = new StringBuilder();
				//解析考勤项目得到公共的考勤Map用于计算
				for(Map<String, Object> map1 : itemList){
					itemMap.put(map1.get("ATTEND_ITEM").toString(), 0);

					itemSql.append(map1.get("ATTEND_ITEM").toString()).append(", ");
					
					itemSqlVal.append("#{item.").append(map1.get("ATTEND_ITEM").toString()).append(",jdbcType=NUMERIC} ").append(map1.get("ATTEND_ITEM").toString()).append(", ");
				}
				entityMap.put("state", 0);
				entityMap.put("oper", SessionManager.getUserId());
				entityMap.put("oper_date", new Date());
				
				entityMap.put("itemSql", itemSql.substring(0, itemSql.length() - 2).toString());
				entityMap.put("itemSqlVal", itemSqlVal.substring(0, itemSqlVal.length() - 2).toString());
				
				//获取考勤结果
				List<Map<String, Object>> resultList = hrAttendResultManageMapper.queryResultData(entityMap);
				Map<String, Object> vMap = null;
				for(Map<String, Object> map2 : resultList){
					empKey = map2.get("dept_id_c").toString() + "@" + map2.get("emp_id").toString();
					
					if(empMap.containsKey(empKey)){
						vMap = empMap.get(empKey);
					}else{
						entityMap.put("dept_id_c", map2.get("dept_id_c").toString());
						entityMap.put("emp_id", map2.get("emp_id").toString());
						 int state1=hrAttendResultMapper.queryState(entityMap);
							
							if(state1==0){

								vMap = new HashMap<String, Object>();
								vMap.put("dept_id_c", map2.get("dept_id_c").toString());
								vMap.put("emp_id", map2.get("emp_id").toString());
								vMap.put("dept_id_b", map2.get("dept_id_b").toString());
								vMap.putAll(itemMap);
					}else{
						continue;
					}
						
					}
					
					vMap.put(map2.get("attend_item").toString(), map2.get("attend_val"));
					
					empMap.put(empKey, vMap);
			
				}
				int count =hrAttendResultManageMapper.queryCount(entityMap);
				//循环empMap得到增加数据的List
				List<Map<String, Object>> manageList = new ArrayList<Map<String,Object>>();
				for(Entry<String, Map<String, Object>> map3 : empMap.entrySet()){
					manageList.add(map3.getValue());
					if(manageList.size()>0||manageList.size() == 300&&manageList.size() - 1==0){
						if(count>0){
						//删除数据
						hrAttendResultManageMapper.deleteResultManage(entityMap, manageList);
						//插入数据
						hrAttendResultManageMapper.addBatchResultManage(entityMap, manageList);
						//清空List
						manageList = new ArrayList<Map<String,Object>>();
						}
					}
				}
					
				
				
				}
			}
		
			
			
			retMap.put("msg", "保存成功");
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	@Override
	public String queryEmpSelectResult(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrAttendResultMapper.queryEmpSelectResult(entityMap);
		return JSONArray.toJSONString(list);
	}
//

	@Override
	public String queryAttendEmp(Map<String, Object> entityMap)
			throws DataAccessException {	
				StringBuffer emp= new StringBuffer();
		
		
		List<Map> emplistVo =JSONArray.parseArray(String.valueOf(entityMap.get("emp_id")),Map.class);
		for (Map map2 : emplistVo) {
			emp.append(map2.get("emp_id")+",");
		}
		emp.deleteCharAt(emp.length()-1);
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("emp_id", emp);
			List<Map<String,Object>> list = hrAttendResultMapper.queryAttendEmp(entityMap);
			
			return ChdJson.toJsonLower(list);}
	
}
