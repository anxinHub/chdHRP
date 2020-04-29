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
import com.chd.hrp.ass.dao.assquery.AssDistributionMapper;
import com.chd.hrp.ass.service.assquery.AssDistributionService;
import com.github.pagehelper.PageInfo;

@Service("assDistributionService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssDistributionServiceImpl implements AssDistributionService {

	private static Logger logger = Logger.getLogger(AssDistributionServiceImpl.class);

	@Resource(name = "assDistributionMapper")
	private final AssDistributionMapper assDistributionMapper = null;

	@Override
	public String queryAssDistribution(Map<String, Object> entityMap) throws DataAccessException {
		logger.debug("AssDistributionServiceImpl.queryAssDistribution");
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<Map<String, Object>> list = null;

		if (sysPage.getTotal() == -1) {
			if("sum".equals(entityMap.get("searchType").toString())){//汇总查询
				list = assDistributionMapper.queryAssDistribution(entityMap);
			}else if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assDistributionMapper.queryAssDistributionDetail(entityMap);
			}else if("check".equals(entityMap.get("searchType").toString())){//盘点查询
				list = assDistributionMapper.queryAssDistributionCheck(entityMap);
			}
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			if("sum".equals(entityMap.get("searchType").toString())){//汇总查询
				list = assDistributionMapper.queryAssDistribution(entityMap, rowBounds);
			}else if("detail".equals(entityMap.get("searchType").toString())){//明细查询
				list = assDistributionMapper.queryAssDistributionDetail(entityMap, rowBounds);
			}else if("check".equals(entityMap.get("searchType").toString())){//盘点查询
				list = assDistributionMapper.queryAssDistributionCheck(entityMap, rowBounds);
			}

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public List<Map<String, Object>> queryAssDistributionPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = null;
		
		

		if("sum".equals(entityMap.get("searchType").toString())){//汇总查询
			list = assDistributionMapper.queryAssDistribution(entityMap);
		}else if("detail".equals(entityMap.get("searchType").toString())){//明细查询
			list = assDistributionMapper.queryAssDistributionDetail(entityMap);
		}else if("check".equals(entityMap.get("searchType").toString())){//盘点查询
			list = assDistributionMapper.queryAssDistributionCheck(entityMap);
		}
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAssbuttonPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDistributionMapper.queryAssbuttonPrint(entityMap);
	
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAssbuttonsPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		//SysPage sysPage = new SysPage();
		
		//sysPage.setPage(Integer.parseInt(entityMap.get("page").toString()));
		//sysPage.setTotal(Integer.parseInt(entityMap.get("total").toString()));
		//sysPage.setPagesize(Integer.parseInt(entityMap.get("pageSize")
			//.toString()));
		
		//RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = assDistributionMapper.queryAssbuttonsPrint(entityMap);
		
		//PageInfo page = new PageInfo(list);
	
		return list;
	}

}
