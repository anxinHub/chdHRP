package com.chd.hrp.mat.serviceImpl.warning;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.warning.MatSafeStoreWarningMapper;
import com.chd.hrp.mat.service.warning.MatSafeStoreWarningService;
import com.github.pagehelper.PageInfo;

@Service("matSafeStoreWarningService")
public class MatSafeStoreWarningServiceImpl implements MatSafeStoreWarningService {

	private static Logger logger = Logger.getLogger(MatSafeStoreWarningServiceImpl.class);

	@Resource(name = "matSafeStoreWarningMapper")
	private final MatSafeStoreWarningMapper matSafeStoreWarningMapper = null;

	// 查询安全库存预警
	@Override
	public String queryMatSafeStoreWarning(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = matSafeStoreWarningMapper.queryMatSafeStoreWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list =
				 matSafeStoreWarningMapper.queryMatSafeStoreWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}
	//超高限预警 查询
	@Override
	public String queryMatHighStoreWarning(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = matSafeStoreWarningMapper.queryMatHighStoreWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list = matSafeStoreWarningMapper.queryMatHighStoreWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}
	
	//短缺货预警 查询
	@Override
	public String queryMatShortStoreWarning(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = matSafeStoreWarningMapper.queryMatShortStoreWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list = matSafeStoreWarningMapper.queryMatShortStoreWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}

}
