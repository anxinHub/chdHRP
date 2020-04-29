package com.chd.hrp.htc.serviceImpl.income.readydata.deptincome;

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
import com.chd.hrp.htc.dao.income.readydata.deptincome.HtcIncomeProjectCostDeptIncomeMapper;
import com.chd.hrp.htc.entity.income.readydata.deptincome.HtcIncomeProjectCostDeptIncome;
import com.chd.hrp.htc.service.income.readydata.deptincome.HtcIncomeProjectCostDeptIncomeService;
import com.github.pagehelper.PageInfo;

@Service("htcIncomeProjectCostDeptIncomeService")
public class HtcIncomeProjectCostDeptIncomeServiceImpl implements HtcIncomeProjectCostDeptIncomeService{
	
	private static Logger logger = Logger.getLogger(HtcIncomeProjectCostDeptIncomeServiceImpl.class);
	
	@Resource(name = "htcIncomeProjectCostDeptIncomeMapper")
	private final HtcIncomeProjectCostDeptIncomeMapper htcIncomeProjectCostDeptIncomeMapper = null;

	@Override
	public String disposeHtcIncomeProjectCostDeptIncome(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			htcIncomeProjectCostDeptIncomeMapper.disposeHtcIncomeProjectCostDeptIncome(entityMap);
			
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");

		}	
	}

	@Override
	public String queryHtcIncomeProjectCostDeptIncome(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIncomeProjectCostDeptIncome> list = htcIncomeProjectCostDeptIncomeMapper.queryHtcIncomeProjectCostDeptIncome(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomeProjectCostDeptIncome> list = htcIncomeProjectCostDeptIncomeMapper.queryHtcIncomeProjectCostDeptIncome(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
