package com.chd.hrp.acc.serviceImpl.tell;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.tell.AccInventoryReportMapper;
import com.chd.hrp.acc.entity.AccInventoryReport;
import com.chd.hrp.acc.service.tell.AccInventoryReportService;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 库存报表<BR>
*/
@Service("accInventoryReportService")
public class AccInventoryReportServiceImpl implements AccInventoryReportService{
	
	@Resource(name = "accInventoryReportMapper")
	private final AccInventoryReportMapper accInventoryReportMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	
	/**
	 * @Description 
	 * 库存日报<BR> 
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccInventoryReportByDay(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccInventoryReport> list = accInventoryReportMapper.queryAccInventoryReportByDay(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccInventoryReport> list = accInventoryReportMapper.queryAccInventoryReportByDay(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 库存日打印<BR> 
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> queryAccInventoryReportByDayPrint(Map<String, Object> entityMap) throws DataAccessException {
	
			if(entityMap.get("group_id") == null){
				
				entityMap.put("group_id", SessionManager.getGroupId());
			}
			if(entityMap.get("hos_id") == null){
				
				entityMap.put("hos_id", SessionManager.getHosId());
			}
			if(entityMap.get("copy_code") == null){
				
				entityMap.put("copy_code", SessionManager.getCopyCode());
			}
		
			entityMap.put("mod_code", "0101");
			
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			
			entityMap.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
			
			entityMap.put("acc_year", entityMap.get("end_time").toString().substring(0, 4));
			
			List<Map<String, Object>> list = accInventoryReportMapper.queryAccInventoryReportByDayPrint(entityMap);
			
			entityMap.put("mod_code", "01");
			
			return list;
		
	}
	
	/**
	 * @Description 
	 * 库存月报<BR> 
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccInventoryReportByMonth(Map<String, Object> entityMap){
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccInventoryReport> list = accInventoryReportMapper.queryAccInventoryReportByMonth(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccInventoryReport> list = accInventoryReportMapper.queryAccInventoryReportByMonth(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 库存打印<BR> 
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> queryAccInventoryReportByMonthPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		if(entityMap.get("group_id") == null){
			
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if(entityMap.get("hos_id") == null){
			
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if(entityMap.get("copy_code") == null){
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
	
		entityMap.put("mod_code", "0101");
		
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		
		entityMap.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
		
		//entityMap.put("acc_year", entityMap.get("end_yearMonth").toString().substring(0, 4));
		
		List<Map<String, Object>> list = accInventoryReportMapper.queryAccInventoryReportByMonthPrint(entityMap);
		
		entityMap.put("mod_code", "01");
		
		return list;
	
	}
  }
