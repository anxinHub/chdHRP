package com.chd.hrp.htc.serviceImpl.task.readydata.deptcost;

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
import com.chd.hrp.htc.dao.task.readydata.deptcost.HtcTaskProjectCostDeptCostMapper;
import com.chd.hrp.htc.entity.task.readydata.deptcost.HtcTaskProjectCostDeptCost;
import com.chd.hrp.htc.service.task.readydata.deptcost.HtcTaskProjectCostDeptCostService;
import com.github.pagehelper.PageInfo;

@Service("htcTaskProjectCostDeptCostService")
public class HtcTaskProjectCostDeptCostServiceImpl implements HtcTaskProjectCostDeptCostService{
	
private static Logger logger = Logger.getLogger(HtcTaskProjectCostDeptCostServiceImpl.class);
	
	@Resource(name = "htcTaskProjectCostDeptCostMapper")
	private final HtcTaskProjectCostDeptCostMapper htcTaskProjectCostDeptCostMapper = null;

	@Override
	public String disposeHtcTaskProjectCostDeptCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			htcTaskProjectCostDeptCostMapper.disposeHtcTaskProjectCostDeptCost(entityMap);
			
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");

		}	
	}

	@Override
	public String queryHtcTaskProjectCostDeptCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcTaskProjectCostDeptCost> list = htcTaskProjectCostDeptCostMapper.queryHtcTaskProjectCostDeptCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcTaskProjectCostDeptCost> list = htcTaskProjectCostDeptCostMapper.queryHtcTaskProjectCostDeptCost(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
