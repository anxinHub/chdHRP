package com.chd.hrp.acc.serviceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.AccYearMapper;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.entity.AccYear;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.entity.AccYearMonthMy97;
import com.chd.hrp.acc.service.AccYearMonthService;
import com.chd.hrp.sys.dao.InitProcMapper;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.service.base.SysConfigService;

@Service("accYearMonthService")
public class AccYearMonthServiceImpl implements AccYearMonthService {
	private static Logger logger = Logger.getLogger(AccYearMonthServiceImpl.class);
	
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;
	
	@Resource(name = "accYearMapper")
	private final AccYearMapper accYearMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "initProcMapper")
	private final InitProcMapper initProcMapper = null;
	
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;
	
	public String addAccYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		String begin_time = "";
		
		String end_time = "";
		
		Integer year_month = Integer.valueOf(0);
		
		AccYear accYear = this.accYearMapper.queryAccYearByCode(entityMap);
		
		if (accYear != null) {
			return "{\"error\":\"该会计期间已存在,添加失败！ \",\"state\":\"true\"}";
		}
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			String startDate = entityMap.get("acc_year").toString();
			
			entityMap.put("begin_date", startDate + "-01-01");
			
			entityMap.put("end_date", startDate + "-12-31");
			
			accYearMapper.addAccYear(entityMap);
			
			for (int i = 1; i < 13; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("group_id", entityMap.get("group_id"));
				
				map.put("hos_id", entityMap.get("hos_id"));
				
				map.put("copy_code", entityMap.get("copy_code"));
				
				map.put("acc_year", startDate);
				
				if ("0".equals(entityMap.get("acc_begin").toString())) {
					if (i < 10) {
						begin_time = startDate + "-" + "0" + i + "-"
								+ DateUtil.getMinMonthDate(year_month.toString(), new StringBuilder("0").append(i).toString());
						
						end_time = startDate + "-" + "0" + i + "-"
								+ DateUtil.getMaxMonthDate(year_month.toString(), new StringBuilder("0").append(i).toString());
					} else {
						begin_time = startDate + "-" + i + "-" + DateUtil.getMinMonthDate(year_month.toString(), String.valueOf(i));
						
						end_time = startDate + "-" + i + "-" + DateUtil.getMaxMonthDate(year_month.toString(), String.valueOf(i));
					}
					
				} else if (i < 11) {
					if ((i != 1) && (i != 10)) {
						begin_time = startDate + "-" + "0" + (i - 1) + "-" + entityMap.get("acc_begin");
						
						end_time = startDate + "-" + "0" + i + "-" + entityMap.get("acc_end");
					} else if (i == 10) {
						begin_time = startDate + "-" + "0" + (i - 1) + "-" + entityMap.get("acc_begin");
						
						end_time = startDate + "-" + i + "-" + entityMap.get("acc_end");
					} else {
						begin_time = startDate + "-" + "0" + i + "-" + "01";
						
						end_time = startDate + "-" + "0" + i + "-" + entityMap.get("acc_end");
					}
					
				} else if (i != 12) {
					begin_time = startDate + "-" + (i - 1) + "-" + entityMap.get("acc_begin");
					
					end_time = startDate + "-" + i + "-" + entityMap.get("acc_end");
				} else {
					begin_time = startDate + "-" + (i - 1) + "-" + entityMap.get("acc_begin");
					
					end_time = startDate + "-" + i + "-" + "31";
				}
				
				if (i > 9) {
					map.put("acc_month", i);
				} else {
					map.put("acc_month", "0" + i);
				}
				
				if (map.get("acc_month").toString().equals("02") && "0".equals(entityMap.get("acc_begin").toString())) {
					if (Integer.parseInt(startDate) % 4 == 0 && Integer.parseInt(startDate) % 100 != 0 || Integer.parseInt(startDate) % 400 == 0) {
						end_time = end_time.substring(0, end_time.lastIndexOf("-")) + "-29";
					} else {
						end_time = end_time.substring(0, end_time.lastIndexOf("-")) + "-28";
					}
				}
				
				map.put("begin_date", DateUtil.stringToDate(begin_time, "yyyy-MM-dd"));
				
				map.put("end_date", DateUtil.stringToDate(end_time, "yyyy-MM-dd"));
				
				accYearMonthMapper.addAccYearMonth(map);
				// 批量处理暂时不优化，后期优化
				// list.add(map);
			}
			// this.accYearMonthMapper.addBatchAccYearMonth(list);
			
			//重新加载会计期间
			sysConfigService.queryYearMonthInit(entityMap);
			
			this.initProcMapper.saveInitYearProc(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(), e);
		}
		
	}
	
	public String addBatchAccYearMonth(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			this.accYearMonthMapper.addBatchAccYearMonth(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccYearMonth\"}";
	}
	
	public String queryAccYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		List list = this.accYearMonthMapper.queryAccYearMonth(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	public AccYearMonth queryAccYearMonthByCode(Map<String, Object> entityMap) throws DataAccessException {
		return this.accYearMonthMapper.queryAccYearMonthByCode(entityMap);
	}
	
	public String deleteBatchAccYearMonth(Map<String, Object> entityList) throws DataAccessException {
		try {
			
			this.accYearMonthMapper.deleteAccYearMonth(entityList);
			
			this.accYearMapper.deleteAccYear(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccYearMonth\"}";
	}
	
	public String deleteAccYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			this.accYearMonthMapper.deleteAccYearMonth(entityMap);
			
			//重新加载会计期间
			sysConfigService.queryYearMonthInit(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccYearMonth\"}";
	}
	
	public String updateAccYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		List list = new ArrayList();
		List accYearMonthList = this.accYearMonthMapper.queryAccYearMonth(entityMap);

		Date begin_time = null;
		Date end_time = null;
		DecimalFormat df = new DecimalFormat("00");
		
		try {
			for (int i = 0; i < accYearMonthList.size(); i++) {
				AccYearMonth accYearMonth = (AccYearMonth) accYearMonthList.get(i);
				String month = accYearMonth.getBegin_date().replace("-", "").substring(4, 6);
				String begin = accYearMonth.getBegin_date().replace("-", "").substring(0, 6);
				String end = DateUtil.getNextYear_Month(begin);

				if ("01".equals(month)) {
					begin_time = DateUtil.stringToDate(begin + "01", "yyyyMMdd");
				} else {
					begin_time = DateUtil.stringToDate(begin + entityMap.get("begin_time"), "yyyyMMdd");
				}

				if ("12".equals(month)) {
					end_time = DateUtil.stringToDate(
							accYearMonth.getBegin_date().replace("-", "").substring(0, 6) + "31", "yyyyMMdd");
				} else {
					end_time = DateUtil.stringToDate(end + entityMap.get("end_time"), "yyyyMMdd");
				}

				Map map = new HashMap();
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("group_id", entityMap.get("group_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("acc_year", entityMap.get("acc_year"));
				map.put("acc_month", df.format(i + 1));
				map.put("begin_date", begin_time);
				map.put("end_date", end_time);
				list.add(map);
			}

			this.accYearMonthMapper.deleteAccYearMonth(entityMap);
			this.accYearMonthMapper.addBatchAccYearMonth(list);
			// 重新加载会计期间
			sysConfigService.queryYearMonthInit(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	public String updateBatchAccYearMonth(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			this.accYearMonthMapper.updateBatchAccYearMonth(entityList);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccYearMonth\"}";
	}
	
	public String importAccYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
	}
	
	public String queryAccYearMonthByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		
		List<AccYear> yearList = this.accYearMapper.queryAccYear(entityMap);
		
		if (yearList.size() > 0) {
			int row = 0;
			for (AccYear accYear : yearList) {
				if (row == 0)
					jsonResult.append("{");
				else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + accYear.getAcc_year() + ",");
				jsonResult.append("group_id:'" + accYear.getGroup_id() + "',");
				jsonResult.append("hos_id:'" + accYear.getHos_id() + "',");
				jsonResult.append("copy_code:'" + accYear.getCopy_code() + "',");
				jsonResult.append("name:" + accYear.getAcc_year());
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}
	
	public String queryYearMonthBySelect(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(this.accYearMonthMapper.queryYearMonthBySelect(entityMap));
	}
	
	/*
	 * 配合My97DatePicker插件使用(non-Javadoc)
	 * 参数：flag(acc_flag、mat_flag...)、集团、医院、账套、年度(可为空)
	 * 返回对象AccYearMonthMy97，最小日期，最大日期，当前日期(格式：2016年11月)
	 */
	
	public AccYearMonthMy97 queryYearMonthByMy97(Map<String, Object> entityMap) throws DataAccessException {
		
		List<String> li = new ArrayList<String>();
		li = this.accYearMonthMapper.queryYearMonthByMy97(entityMap);
		
		AccYearMonthMy97 accYearMonthMy97 = new AccYearMonthMy97();
		if ((li != null) && (li.size() == 3)) {
			accYearMonthMy97.setMinDate((li.get(0) == null) || ((li.get(0)).equals("")) ? "0" : li.get(0));
			accYearMonthMy97.setMaxDate((li.get(1) == null) || ((li.get(1)).equals("")) ? "0" : li.get(1));
			accYearMonthMy97.setCurDate((li.get(2) == null) || ((li.get(2)).equals("")) ? "请维护会计年度！" : li.get(2));
		}
		
		return accYearMonthMy97;
	}
	
	public String queryMonthBySelect(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(this.accYearMonthMapper.queryMonthBySelect(entityMap));
	}
	
	public List<AccYearMonth> queryAccTellPeriod(Map<String, Object> entityMap) {
		return this.accYearMonthMapper.queryAccYearMonth(entityMap);
	}
	
	public String queryAccYearMonthNow(Map<String, Object> entityMap) {
		return this.accYearMonthMapper.queryAccYearMonthNow(entityMap);
	}
	
	/**
	 * 根据flag标识,取所有系统模块的会计期间 flag：acc_flag、mat_flag... return：2016.11
	 */
	
	@Override
	public String queryAccYearMonthAllNow(String flag) throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("flag", flag);
		
		String yearMonth = accYearMonthMapper.queryAccYearMonthAllNow(map);
		if (yearMonth == null || yearMonth.equals("")) {
			throw new SysException("没有会计年度，请到系统平台添加会计年度！");
		}
		return yearMonth;
	}
	
	// 根据年度查找月份
	@Override
	public String queryYearMonthByAccYearSelect(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(accYearMonthMapper.queryYearMonthByAccYearSelect(entityMap));
	}
}