package com.chd.hrp.htc.serviceImpl.relative.readydata.deptincome;

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
import com.chd.hrp.htc.dao.relative.readydata.deptincome.HtcRelativeProjectCostDeptIncomeMapper;
import com.chd.hrp.htc.entity.relative.readydata.deptincome.HtcRelativeProjectCostDeptIncome;
import com.chd.hrp.htc.service.relative.readydata.deptincome.HtcRelativeProjectCostDeptIncomeService;
import com.github.pagehelper.PageInfo;

@Service("htcRelativeProjectCostDeptIncomeService")
public class HtcRelativeProjectCostDeptIncomeServiceImpl implements HtcRelativeProjectCostDeptIncomeService{
	
	private static Logger logger = Logger.getLogger(HtcRelativeProjectCostDeptIncomeServiceImpl.class);
	
	@Resource(name = "htcRelativeProjectCostDeptIncomeMapper")
	private final HtcRelativeProjectCostDeptIncomeMapper htcRelativeProjectCostDeptIncomeMapper = null;

	@Override
	public String disposeHtcRelativeProjectCostDeptIncome(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			htcRelativeProjectCostDeptIncomeMapper.disposeHtcRelativeProjectCostDeptIncome(entityMap);
			
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");

		}	
	}

	@Override
	public String queryHtcRelativeProjectCostDeptIncome(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativeProjectCostDeptIncome> list = htcRelativeProjectCostDeptIncomeMapper.queryHtcRelativeProjectCostDeptIncome(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativeProjectCostDeptIncome> list = htcRelativeProjectCostDeptIncomeMapper.queryHtcRelativeProjectCostDeptIncome(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
