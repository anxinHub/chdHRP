package com.chd.hrp.ass.serviceImpl.tongJiReports;

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
import com.chd.hrp.ass.dao.tongJiReports.AssDepreBreakageMapper;
import com.chd.hrp.ass.service.tongJiReports.AssDepreBreakageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050701 资产报损查询
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assDepreBreakageService")
public class AssDepreBreakageServiceImpl implements AssDepreBreakageService {
	
	private static Logger logger = Logger.getLogger(AssDepreBreakageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDepreBreakageMapper")
	private final AssDepreBreakageMapper assDepreBreakageMapper = null;
	
	/**
	 * 资产报损查询
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assDepreBreakageMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assDepreBreakageMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssDepreBreakagePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDepreBreakageMapper.queryAssDepreBreakagePrint(entityMap);
	
		return list;
	}

}
