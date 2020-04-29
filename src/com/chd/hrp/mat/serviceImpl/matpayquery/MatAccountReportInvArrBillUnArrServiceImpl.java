package com.chd.hrp.mat.serviceImpl.matpayquery;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.matpayquery.MatAccountReportInvArrBillUnArrMapper;
import com.chd.hrp.mat.service.matpayquery.MatAccountReportInvArrBillUnArrService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 货到票未到明细表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matAccountReportInvArrBillUnArrService")
public class MatAccountReportInvArrBillUnArrServiceImpl implements MatAccountReportInvArrBillUnArrService {
	
	private static Logger logger = Logger.getLogger(MatAccountReportInvArrBillUnArrServiceImpl.class);
	
	@Resource(name = "matAccountReportInvArrBillUnArrMapper")
	private final MatAccountReportInvArrBillUnArrMapper matAccountReportInvArrBillUnArrMapper = null;
	@Override
	public String queryMatAccountReportInvArrBillUnArr(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matAccountReportInvArrBillUnArrMapper.queryMatAccountReportInvArrBillUnArr(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matAccountReportInvArrBillUnArrMapper.queryMatAccountReportInvArrBillUnArr(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public List<Map<String,Object>> queryMatAccountReportInvArrBillUnArrPrint(Map<String, Object> entityMap) throws DataAccessException {
				
				entityMap.put("group_id", SessionManager.getGroupId());
					
				entityMap.put("hos_id", SessionManager.getHosId());
					
				entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Map<String,Object>> list = matAccountReportInvArrBillUnArrMapper.queryMatAccountReportInvArrBillUnArr(entityMap);
		
		return list;
	}
	@Override
	public String queryMatAccountReportInvArrBillUnArrSup(Map<String, Object> entityMap) throws DataAccessException {
SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matAccountReportInvArrBillUnArrMapper.queryMatAccountReportInvArrBillUnArrSup(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matAccountReportInvArrBillUnArrMapper.queryMatAccountReportInvArrBillUnArrSup(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		    
	}

}
