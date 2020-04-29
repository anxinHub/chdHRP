package com.chd.hrp.htc.serviceImpl.relative.readydata.deptcost;

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
import com.chd.hrp.htc.dao.relative.readydata.deptcost.HtcRelativeProjectCostDeptCostMapper;
import com.chd.hrp.htc.entity.relative.readydata.deptcost.HtcRelativeProjectCostDeptCost;
import com.chd.hrp.htc.service.relative.readydata.depcost.HtcRelativeProjectCostDeptCostService;
import com.github.pagehelper.PageInfo;

@Service("htcRelativeProjectCostDeptCostService")
public class HtcRelativeProjectCostDeptCostServiceImpl implements HtcRelativeProjectCostDeptCostService{
	
	private static Logger logger = Logger.getLogger(HtcRelativeProjectCostDeptCostServiceImpl.class);
	
	@Resource(name = "htcRelativeProjectCostDeptCostMapper")
	private final HtcRelativeProjectCostDeptCostMapper htcRelativeProjectCostDeptCostMapper = null;

	@Override
	public String disposeHtcRelativeProjectCostDeptCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			htcRelativeProjectCostDeptCostMapper.disposeHtcRelativeProjectCostDeptCost(entityMap);
			
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");

		}	
	}

	@Override
	public String queryHtcRelativeProjectCostDeptCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativeProjectCostDeptCost> list = htcRelativeProjectCostDeptCostMapper.queryHtcRelativeProjectCostDeptCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativeProjectCostDeptCost> list = htcRelativeProjectCostDeptCostMapper.queryHtcRelativeProjectCostDeptCost(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
