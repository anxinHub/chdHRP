package com.chd.hrp.htc.serviceImpl.relative.plan.set;

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
import com.chd.hrp.htc.dao.relative.plan.set.HtcRelativePlanSetMapper;
import com.chd.hrp.htc.entity.relative.plan.set.HtcRelativePlanSet;
import com.chd.hrp.htc.service.relative.plan.set.HtcRelativePlanSetService;
import com.github.pagehelper.PageInfo;

@Service("htcRelativePlanSetService")
public class HtcRelativePlanSetServiceImpl implements HtcRelativePlanSetService{
	
private static Logger logger = Logger.getLogger(HtcRelativePlanSetServiceImpl.class);
	
	@Resource(name = "htcRelativePlanSetMapper")
	private final HtcRelativePlanSetMapper htcRelativePlanSetMapper = null;

	@Override
	public String addHtcRelativePlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			HtcRelativePlanSet htcRelativePlanSet = htcRelativePlanSetMapper.queryHtcRelativePlanSetByCode(entityMap);
			if(null != htcRelativePlanSet){
				return "{\"error\":\"方案编码重复.\",\"state\":\"false\"}";
			}
			entityMap.put("is_check", 0);//默认未审核
			entityMap.put("is_current", 0);//默认否应用方案
			htcRelativePlanSetMapper.addHtcRelativePlanSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");

		}	
	}

	@Override
	public String queryHtcRelativePlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativePlanSet> list = htcRelativePlanSetMapper.queryHtcRelativePlanSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativePlanSet> list = htcRelativePlanSetMapper.queryHtcRelativePlanSet(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcRelativePlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcRelativePlanSetMapper.deleteBatchHtcRelativePlanSet(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}

	@Override
	public String updateHtcRelativePlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
			try{
	
				htcRelativePlanSetMapper.updateHtcRelativePlanSet(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}catch(Exception e){
				
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
	
			}	
	}

	@Override
	public HtcRelativePlanSet queryHtcRelativePlanSetByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcRelativePlanSetMapper.queryHtcRelativePlanSetByCode(entityMap);
	}

	@Override
	public String queryHtcRelativePlanSetAudit(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativePlanSet> list = htcRelativePlanSetMapper.queryHtcRelativePlanSetAudit(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativePlanSet> list = htcRelativePlanSetMapper.queryHtcRelativePlanSetAudit(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String auditHtcRelativePlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcRelativePlanSetMapper.auditHtcRelativePlanSet(list);
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"审核失败\"}");

		}	
	}

	@Override
	public String cancelAuditHtcRelativePlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcRelativePlanSetMapper.cancelAuditHtcRelativePlanSet(list);
			
			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"消审失败\"}");

		}	
	}

	@Override
	public String queryHtcRelativePlanHistory(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativePlanSet> list = htcRelativePlanSetMapper.queryHtcRelativePlanHistory(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativePlanSet> list = htcRelativePlanSetMapper.queryHtcRelativePlanHistory(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	

}
