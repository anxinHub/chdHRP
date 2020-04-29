/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.query;

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
import com.chd.hrp.mat.dao.storage.query.MatDeptOutReportMapper;
import com.chd.hrp.mat.service.storage.query.MatDeptOutReportService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 常州三院 个性化报表
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matDeptOutReportService")
public class MatDeptOutReportServiceImpl implements MatDeptOutReportService {

	private static Logger logger = Logger.getLogger(MatDeptOutReportServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "matDeptOutReportMapper")
	private final MatDeptOutReportMapper matDeptOutReportMapper = null;
	
	@Override
	public String queryMatDeptOutReport(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		
		
      SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matDeptOutReportMapper.queryMatDeptOutReport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matDeptOutReportMapper.queryMatDeptOutReport(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String queryMatStoreOutFimType(Map<String, Object> entityMap) {

		
	      SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matDeptOutReportMapper.queryMatStoreOutFimType(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = matDeptOutReportMapper.queryMatStoreOutFimType(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
			
	}

	@Override
	public String queryMatFinance(Map<String, Object> entityMap) throws DataAccessException {
		
		
	      SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matDeptOutReportMapper.queryMatFinance(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = matDeptOutReportMapper.queryMatFinance(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	
}

	@Override
	public List<Map<String, Object>> queryMatFinancePrint(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = matDeptOutReportMapper.queryMatFinance(mapVo);
		
		return list;
		
	}

	@Override
	public List<Map<String, Object>> queryMatDeptOutReportPrint(Map<String, Object> mapVo)
			throws DataAccessException {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		List<Map<String, Object>> list = matDeptOutReportMapper.queryMatDeptOutReport(mapVo);
			
			return list;
	}

	@Override
	public List<Map<String, Object>> queryMatStoreOutFimTypePrint(Map<String, Object> mapVo) {	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());		
		List<Map<String, Object>> list = matDeptOutReportMapper.queryMatStoreOutFimType(mapVo);
		return list;
		}
}