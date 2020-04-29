package com.chd.hrp.htc.serviceImpl.relative.business.dept;

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
import com.chd.hrp.htc.dao.relative.business.dept.HtcRelativeChargeCostDeptMapper;
import com.chd.hrp.htc.entity.relative.business.dept.HtcRelativeChargeCostDept;
import com.chd.hrp.htc.service.relative.business.dept.HtcRelativeChargeCostDeptService;
import com.github.pagehelper.PageInfo;

@Service("htcRelativeChargeCostDeptService")
public class HtcRelativeChargeCostDeptServiceImpl implements HtcRelativeChargeCostDeptService{
	

 private static Logger logger = Logger.getLogger(HtcRelativeChargeCostDeptServiceImpl.class);
	
	@Resource(name = "htcRelativeChargeCostDeptMapper")
	private final HtcRelativeChargeCostDeptMapper htcRelativeChargeCostDeptMapper = null;

	@Override
	public String queryHtcRelativeChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<HtcRelativeChargeCostDept> list = htcRelativeChargeCostDeptMapper.queryHtcRelativeChargeCostDept(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<HtcRelativeChargeCostDept> list = htcRelativeChargeCostDeptMapper.queryHtcRelativeChargeCostDept(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
	}

	@Override
	public String collectHtcRelativeChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
			try {
				
				   entityMap.put("proj_dept_id", "".equals(entityMap.get("proj_dept_id"))?0:entityMap.get("proj_dept_id"));
				    
				    String err_txt = "";
				    
				    htcRelativeChargeCostDeptMapper.collectHtcRelativeChargeCostDept(entityMap);
					
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
