package com.chd.hrp.htc.serviceImpl.income.plan.set;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.income.plan.set.HtcIncomePlanSetMapper;
import com.chd.hrp.htc.entity.income.plan.set.HtcIncomePlanSet;
import com.chd.hrp.htc.service.income.plan.set.HtcIncomePlanSetService;
import com.github.pagehelper.PageInfo;

@Service("htcIncomePlanSetService")
public class HtcIncomePlanSetServiceImpl implements HtcIncomePlanSetService{
	
private static Logger logger = Logger.getLogger(HtcIncomePlanSetServiceImpl.class);
	
	@Resource(name = "htcIncomePlanSetMapper")
	private final HtcIncomePlanSetMapper htcIncomePlanSetMapper = null;

	@Override
	public String addHtcIncomePlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			HtcIncomePlanSet htcIncomePlanSet = htcIncomePlanSetMapper.queryHtcIncomePlanSetByCode(entityMap);
			if(null != htcIncomePlanSet){
				return "{\"error\":\"方案编码重复.\",\"state\":\"false\"}";
			}
			entityMap.put("is_check", 0);//默认未审核
			entityMap.put("is_current", 0);//默认否应用方案
			htcIncomePlanSetMapper.addHtcIncomePlanSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");

		}	
	}

	@Override
	public String queryHtcIncomePlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIncomePlanSet> list = htcIncomePlanSetMapper.queryHtcIncomePlanSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomePlanSet> list = htcIncomePlanSetMapper.queryHtcIncomePlanSet(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcIncomePlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcIncomePlanSetMapper.deleteBatchHtcIncomePlanSet(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}

	@Override
	public String updateHtcIncomePlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
			try{
	
				htcIncomePlanSetMapper.updateHtcIncomePlanSet(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}catch(Exception e){
				
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
	
			}	
	}

	@Override
	public HtcIncomePlanSet queryHtcIncomePlanSetByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcIncomePlanSetMapper.queryHtcIncomePlanSetByCode(entityMap);
	}

	@Override
	public String queryHtcIncomePlanSetAudit(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIncomePlanSet> list = htcIncomePlanSetMapper.queryHtcIncomePlanSetAudit(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomePlanSet> list = htcIncomePlanSetMapper.queryHtcIncomePlanSetAudit(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String auditHtcIncomePlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcIncomePlanSetMapper.auditHtcIncomePlanSet(list);
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"审核失败\"}");

		}	
	}

	@Override
	public String cancelAuditIncomeHtcPlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcIncomePlanSetMapper.cancelAuditIncomeHtcPlanSet(list);
			
			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"消审失败\"}");

		}	
	}

	@Override
	public String queryHtcIncomePlanHistory(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIncomePlanSet> list = htcIncomePlanSetMapper.queryHtcIncomePlanHistory(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomePlanSet> list = htcIncomePlanSetMapper.queryHtcIncomePlanHistory(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	

}
