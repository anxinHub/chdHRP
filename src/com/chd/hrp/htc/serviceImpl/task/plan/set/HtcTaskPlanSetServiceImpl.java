
package com.chd.hrp.htc.serviceImpl.task.plan.set;
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
import com.chd.hrp.htc.dao.task.plan.set.HtcTaskPlanSetMapper;
import com.chd.hrp.htc.entity.task.plan.set.HtcTaskPlanSet;
import com.chd.hrp.htc.service.task.plan.set.HtcTaskPlanSetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcTaskPlanSetService")
public class HtcTaskPlanSetServiceImpl implements HtcTaskPlanSetService {

	private static Logger logger = Logger.getLogger(HtcTaskPlanSetServiceImpl.class);
	
	@Resource(name = "htcTaskPlanSetMapper")
	private final HtcTaskPlanSetMapper htcTaskPlanSetMapper = null;

	
	@Override
	public String addHtcTaskPlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			HtcTaskPlanSet htcTaskPlanSet = htcTaskPlanSetMapper.queryHtcTaskPlanSetByCode(entityMap);
			if(null != htcTaskPlanSet){
				return "{\"error\":\"方案编码重复.\",\"state\":\"false\"}";
			}
			entityMap.put("is_check", 0);//默认未审核
			entityMap.put("is_current", 0);//默认否应用方案
			htcTaskPlanSetMapper.addHtcTaskPlanSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");

		}	
	}

	@Override
	public String queryHtcTaskPlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcTaskPlanSet> list = htcTaskPlanSetMapper.queryHtcTaskPlanSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcTaskPlanSet> list = htcTaskPlanSetMapper.queryHtcTaskPlanSet(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcTaskPlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcTaskPlanSetMapper.deleteBatchHtcTaskPlanSet(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}

	@Override
	public String updateHtcTaskPlanSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
			try{
	
				htcTaskPlanSetMapper.updateHtcTaskPlanSet(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}catch(Exception e){
				
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
	
			}	
	}

	@Override
	public HtcTaskPlanSet queryHtcTaskPlanSetByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcTaskPlanSetMapper.queryHtcTaskPlanSetByCode(entityMap);
	}

	@Override
	public String queryHtcTaskPlanSetAudit(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcTaskPlanSet> list = htcTaskPlanSetMapper.queryHtcTaskPlanSetAudit(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcTaskPlanSet> list = htcTaskPlanSetMapper.queryHtcTaskPlanSetAudit(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String auditHtcTaskPlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcTaskPlanSetMapper.auditHtcTaskPlanSet(list);
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"审核失败\"}");

		}	
	}

	@Override
	public String cancelAuditHtcTaskPlanSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{

			htcTaskPlanSetMapper.cancelAuditHtcTaskPlanSet(list);
			
			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"消审失败\"}");

		}	
	}

	@Override
	public String queryHtcTaskPlanHistory(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcTaskPlanSet> list = htcTaskPlanSetMapper.queryHtcTaskPlanHistory(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcTaskPlanSet> list = htcTaskPlanSetMapper.queryHtcTaskPlanHistory(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
}
