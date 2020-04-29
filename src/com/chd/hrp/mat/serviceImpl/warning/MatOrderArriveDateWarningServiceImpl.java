package com.chd.hrp.mat.serviceImpl.warning;

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
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.warning.MatOrderArriveDateWarningMapper;
import com.chd.hrp.mat.service.warning.MatOrderArriveDateWarningService;
import com.github.pagehelper.PageInfo;

@Service("matOrderArriveDateWarningService")
public class MatOrderArriveDateWarningServiceImpl implements MatOrderArriveDateWarningService {

	private static Logger logger = Logger.getLogger(MatOrderArriveDateWarningServiceImpl.class);

	@Resource(name = "matOrderArriveDateWarningMapper")
	private final MatOrderArriveDateWarningMapper matOrderArriveDateWarningMapper = null;

	// 查询材料效期预警
	@Override
	public String queryMatOrderArriveDateWarning(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matOrderArriveDateWarningMapper.queryMatOrderArriveDateWarning(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matOrderArriveDateWarningMapper.queryMatOrderArriveDateWarning(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
		
	}

	@Override
	public String queryMatOrderInvInfo(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matOrderArriveDateWarningMapper.queryMatOrderInvInfo(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matOrderArriveDateWarningMapper.queryMatOrderInvInfo(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

}
