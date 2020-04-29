package com.chd.hrp.ass.serviceImpl.assquery;

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
import com.chd.hrp.ass.dao.assquery.AssDistributionDeptMapper;
import com.chd.hrp.ass.service.assquery.AssDistributionDeptService;
import com.github.pagehelper.PageInfo;

@Service("assDistributionDeptService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssDistributionDeptServiceImpl implements AssDistributionDeptService { 

	private static Logger logger = Logger.getLogger(AssDistributionDeptServiceImpl.class);
	                  
	@Resource(name = "assDistributionDeptMapper")
	private final AssDistributionDeptMapper assDistributionDeptMapper = null;

	@Override
	public String queryAssDistributionDept(Map<String, Object> entityMap) throws DataAccessException {
		logger.debug("AssDistributionDeptServiceImpl.queryAssDistributionDept");
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assDistributionDeptMapper.queryAssDistributionDept(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = assDistributionDeptMapper.queryAssDistributionDept(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public List<Map<String, Object>> queryAssDistributionDeptPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = assDistributionDeptMapper.queryAssDistributionDept(entityMap);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAssDisPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDistributionDeptMapper.queryAssDisPrint(entityMap);
	
		return list;
	}

}
