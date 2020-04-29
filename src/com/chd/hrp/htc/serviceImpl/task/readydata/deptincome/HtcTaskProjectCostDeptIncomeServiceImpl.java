package com.chd.hrp.htc.serviceImpl.task.readydata.deptincome;

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
import com.chd.hrp.htc.dao.task.readydata.deptincome.HtcTaskProjectCostDeptIncomeMapper;
import com.chd.hrp.htc.entity.task.readydata.deptincome.HtcTaskProjectCostDeptIncome;
import com.chd.hrp.htc.service.task.readydata.deptincome.HtcTaskProjectCostDeptIncomeService;
import com.chd.hrp.htc.serviceImpl.income.readydata.deptincome.HtcIncomeProjectCostDeptIncomeServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("htcTaskProjectCostDeptIncomeService")
public class HtcTaskProjectCostDeptIncomeServiceImpl implements HtcTaskProjectCostDeptIncomeService{
	
private static Logger logger = Logger.getLogger(HtcIncomeProjectCostDeptIncomeServiceImpl.class);
	
@Resource(name = "htcTaskProjectCostDeptIncomeMapper")
private final HtcTaskProjectCostDeptIncomeMapper htcTaskProjectCostDeptIncomeMapper = null;
	
	@Override
	public String disposeHtcTaskProjectCostDeptIncome(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
			try{
				
				htcTaskProjectCostDeptIncomeMapper.disposeHtcTaskProjectCostDeptIncome(entityMap);
				
				return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
				
			}catch(Exception e){
				
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"采集失败\"}");
		
			}	
		}
		
		@Override
		public String queryHtcTaskProjectCostDeptIncome(
				Map<String, Object> entityMap) throws DataAccessException {
			// TODO Auto-generated method stub
		    SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<HtcTaskProjectCostDeptIncome> list = htcTaskProjectCostDeptIncomeMapper.queryHtcTaskProjectCostDeptIncome(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<HtcTaskProjectCostDeptIncome> list = htcTaskProjectCostDeptIncomeMapper.queryHtcTaskProjectCostDeptIncome(entityMap, rowBounds);
			
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
}
