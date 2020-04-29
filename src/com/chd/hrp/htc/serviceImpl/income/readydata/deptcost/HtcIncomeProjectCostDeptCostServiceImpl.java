package com.chd.hrp.htc.serviceImpl.income.readydata.deptcost;

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
import com.chd.hrp.htc.dao.income.readydata.deptcost.HtcIncomeProjectCostDeptCostMapper;
import com.chd.hrp.htc.entity.income.readydata.deptcost.HtcIncomeProjectCostDeptCost;
import com.chd.hrp.htc.service.income.readydata.deptcost.HtcIncomeProjectCostDeptCostService;
import com.github.pagehelper.PageInfo;

@Service("htcIncomeProjectCostDeptCostService")
public class HtcIncomeProjectCostDeptCostServiceImpl implements HtcIncomeProjectCostDeptCostService{
	
	private static Logger logger = Logger.getLogger(HtcIncomeProjectCostDeptCostServiceImpl.class);
	
	@Resource(name = "htcIncomeProjectCostDeptCostMapper")
	private final HtcIncomeProjectCostDeptCostMapper htcIncomeProjectCostDeptCostMapper = null;

	@Override
	public String disposeHtcIncomeProjectCostDeptCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			htcIncomeProjectCostDeptCostMapper.disposeHtcIncomeProjectCostDeptCost(entityMap);
			
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");

		}	
	}

	@Override
	public String queryHtcIncomeProjectCostDeptCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIncomeProjectCostDeptCost> list = htcIncomeProjectCostDeptCostMapper.queryHtcIncomeProjectCostDeptCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomeProjectCostDeptCost> list = htcIncomeProjectCostDeptCostMapper.queryHtcIncomeProjectCostDeptCost(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
