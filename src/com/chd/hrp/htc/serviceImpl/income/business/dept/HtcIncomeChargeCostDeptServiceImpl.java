package com.chd.hrp.htc.serviceImpl.income.business.dept;

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
import com.chd.hrp.htc.dao.income.business.dept.HtcIncomeChargeCostDeptMapper;
import com.chd.hrp.htc.entity.income.business.dept.HtcIncomeChargeCostDept;
import com.chd.hrp.htc.service.income.business.dept.HtcIncomeChargeCostDeptService;
import com.github.pagehelper.PageInfo;

@Service("htcIncomeChargeCostDeptService")
public class HtcIncomeChargeCostDeptServiceImpl implements HtcIncomeChargeCostDeptService{
	

 private static Logger logger = Logger.getLogger(HtcIncomeChargeCostDeptServiceImpl.class);
	
	@Resource(name = "htcIncomeChargeCostDeptMapper")
	private final HtcIncomeChargeCostDeptMapper htcIncomeChargeCostDeptMapper = null;

	@Override
	public String queryHtcIncomeChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<HtcIncomeChargeCostDept> list = htcIncomeChargeCostDeptMapper.queryHtcIncomeChargeCostDept(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<HtcIncomeChargeCostDept> list = htcIncomeChargeCostDeptMapper.queryHtcIncomeChargeCostDept(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
	}

	@Override
	public String collectHtcIncomeChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
			try {
				
				   entityMap.put("proj_dept_id", "".equals(entityMap.get("proj_dept_id"))?0:entityMap.get("proj_dept_id"));
				    
				    String err_txt = "";
				    
				    htcIncomeChargeCostDeptMapper.collectHtcIncomeChargeCostDept(entityMap);
					
				    err_txt = entityMap.get("err_txt").toString();
				    
				    if("核算成功".equals(err_txt)){
				    	
				    	return "{\"msg\":\""+err_txt+".\",\"state\":\"true\"}";
				    	
				    }else {
				    	return "{\"msg\":\""+err_txt+".\",\"state\":\"false\"}";
				    }
					
					
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage(), e);
	
					throw new SysException("{\"error\":\"核算失败\"}");
				}
	}

}
