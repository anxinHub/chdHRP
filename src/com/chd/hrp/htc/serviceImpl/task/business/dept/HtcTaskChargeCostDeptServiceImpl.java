package com.chd.hrp.htc.serviceImpl.task.business.dept;

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
import com.chd.hrp.htc.dao.task.business.dept.HtcTaskChargeCostDeptMapper;
import com.chd.hrp.htc.entity.task.business.dept.HtcTaskChargeCostDept;
import com.chd.hrp.htc.service.task.business.dept.HtcTaskChargeCostDeptService;
import com.github.pagehelper.PageInfo;

@Service("htcTaskChargeCostDeptService")
public class HtcTaskChargeCostDeptServiceImpl implements HtcTaskChargeCostDeptService{

private static Logger logger = Logger.getLogger(HtcTaskChargeCostDeptServiceImpl.class);
	
	@Resource(name = "htcTaskChargeCostDeptMapper")
	private final HtcTaskChargeCostDeptMapper htcTaskChargeCostDeptMapper = null;

	@Override
	public String queryHtcTaskChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<HtcTaskChargeCostDept> list = htcTaskChargeCostDeptMapper.queryHtcTaskChargeCostDept(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<HtcTaskChargeCostDept> list = htcTaskChargeCostDeptMapper.queryHtcTaskChargeCostDept(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
	}

	@Override
	public String collectHtcTaskChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			 String err_txt = "";
			 
			entityMap.put("proj_dept_id", "".equals(entityMap.get("proj_dept_id"))?0:entityMap.get("proj_dept_id"));
			
			htcTaskChargeCostDeptMapper.collectHtcTaskChargeCostDept(entityMap);
			
			err_txt = entityMap.get("err_txt").toString();
			    
		    if("核算成功".equals(err_txt)){
		    	
		    	return "{\"msg\":\""+err_txt+".\",\"state\":\"true\"}";
		    	
		    }else {
		    	return "{\"msg\":\""+err_txt+".\",\"state\":\"false\"}";
		    }
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"失败\"}");
		}
	}
}
