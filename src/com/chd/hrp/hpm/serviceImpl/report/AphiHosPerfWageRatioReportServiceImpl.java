package com.chd.hrp.hpm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiHosPerfWageRatioReportMapper;
import com.chd.hrp.hpm.service.report.AphiHosPerfWageRatioReportService;

@Service("aphiHosPerfWageRatioReportService")
public class AphiHosPerfWageRatioReportServiceImpl implements AphiHosPerfWageRatioReportService {
	
	private static final Logger logger = Logger.getLogger(AphiHosPerfWageRatioReportServiceImpl.class);
	
	
	@Resource(name = "aphiHosPerfWageRatioReportMapper")
	private final AphiHosPerfWageRatioReportMapper aphiHosPerfWageRatioReportMapper = null;

	//报表查询
	@Override
	public String queryHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException {
		
		String acct_yearm = String.valueOf(entityMap.get("acct_yearm"));
		
		entityMap.put("acct_year", acct_yearm.substring(0, 4));
		
		entityMap.put("acct_month", acct_yearm.substring(4, 6));
		
		
		List<Map<String,Object>> list = aphiHosPerfWageRatioReportMapper.queryHpmHosPerfWageRatioReport(entityMap);

		return ChdJson.toJsonLower(list);
		
	}

	//审核
		@Override
		public String shenhe(Map<String, Object> entityMap) throws DataAccessException {
			
			try {
				aphiHosPerfWageRatioReportMapper.shenhe(entityMap);
				
				if("1".equals(entityMap.get("is_audit"))){
					
					return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
				}else{
					return "{\"msg\":\"反审成功.\",\"state\":\"true\"}";
				}
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"审核失败.\"}");
			}
			
		}

		
	@Override
	public String initHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			String acct_yearm = String.valueOf(entityMap.get("acct_yearm"));
			
			entityMap.put("acct_year", acct_yearm.substring(0, 4));
			
			entityMap.put("acct_month", acct_yearm.substring(4, 6));
			
			List<Map<String,Object>> mapList = aphiHosPerfWageRatioReportMapper.queryHpmHosPerfWageRatioReportByCollect(entityMap);
			int  flag=aphiHosPerfWageRatioReportMapper.queryisaudit(entityMap);
			
			if(flag > 0){
					
				return "{\"warn\":\"当前年月生成数据已经审核，不能重复生成\",\"state\":\"false\"}";	
			}
			
			aphiHosPerfWageRatioReportMapper.deleteHpmHosPerfWageRatioReport(entityMap);
			
			for(Map<String,Object> map : mapList){
			
				
				map.putAll(entityMap);
				
				aphiHosPerfWageRatioReportMapper.addHpmHosPerfWageRatioReport(map);
				
			}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 \"}";
		}
	}

	@Override
	public String updateHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String paramVo = String.valueOf(entityMap.get("ParamVo"));
			
			String[] arr = paramVo.split("#");
			
			entityMap.put("acct_year", arr[0]);
			
			entityMap.put("acct_month", arr[1]);
			
			entityMap.put("report_code", arr[2]);

			entityMap.put("perf_wages", arr[3]);
			
			entityMap.put("perf_pers", arr[4]);
			
			entityMap.put("perf_bonus", arr[5]);
			
			entityMap.put("perf_income", arr[6]);
			
			entityMap.put("perf_income_ratio", arr[7]);

			aphiHosPerfWageRatioReportMapper.updateHpmHosPerfWageRatioReport(entityMap);
			
			return "{\"msg1\":\"修改成功.\",\"state\":\"true1\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 \"}";
		}
	}

	@Override
	public String deleteHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			String acct_yearm = String.valueOf(entityMap.get("acct_yearm"));
			
			entityMap.put("acct_year", acct_yearm.substring(0, 4));
			
			entityMap.put("acct_month", acct_yearm.substring(4, 6));
			
			aphiHosPerfWageRatioReportMapper.deleteHpmHosPerfWageRatioReport(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 \"}";
		}
	}

	//报表打印查询
	@Override
	public List<Map<String,Object>> queryHpmHosPerfWageRatioReportPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		String acct_yearm = String.valueOf(entityMap.get("acct_yearm"));
		
		entityMap.put("acct_year", acct_yearm.substring(0, 4));
		
		entityMap.put("acct_month", acct_yearm.substring(4, 6));
		
		List<Map<String,Object>> list = aphiHosPerfWageRatioReportMapper.queryHpmHosPerfWageRatioReportPrint(entityMap);
		
		return list;
	}
}
