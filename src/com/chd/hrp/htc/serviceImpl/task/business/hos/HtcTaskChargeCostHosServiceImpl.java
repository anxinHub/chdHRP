package com.chd.hrp.htc.serviceImpl.task.business.hos;

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
import com.chd.hrp.htc.dao.task.business.hos.HtcTaskChargeCostHosMapper;
import com.chd.hrp.htc.entity.task.business.hos.HtcTaskChargeCostHos;
import com.chd.hrp.htc.service.task.business.hos.HtcTaskChargeCostHosService;
import com.github.pagehelper.PageInfo;

@Service("htcTaskChargeCostHosService")
public class HtcTaskChargeCostHosServiceImpl implements HtcTaskChargeCostHosService{
	
private static Logger logger = Logger.getLogger(HtcTaskChargeCostHosServiceImpl.class);
	
	@Resource(name = "htcTaskChargeCostHosMapper")
	private final HtcTaskChargeCostHosMapper htcTaskChargeCostHosMapper = null;

	@Override
	public String queryHtcTaskChargeCostHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcTaskChargeCostHos> list = htcTaskChargeCostHosMapper.queryHtcTaskChargeCostHos(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcTaskChargeCostHos> list = htcTaskChargeCostHosMapper.queryHtcTaskChargeCostHos(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public String collectHtcTaskChargeCostHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String err_txt = "";
			 
			entityMap.put("proj_dept_id", "".equals(entityMap.get("proj_dept_id"))?0:entityMap.get("proj_dept_id"));
			
			htcTaskChargeCostHosMapper.collectHtcTaskChargeCostHos(entityMap);
			
			err_txt = entityMap.get("err_txt").toString();
			    
		    if("核算成功".equals(err_txt)){
		    	
		    	return "{\"msg\":\""+err_txt+".\",\"state\":\"true\"}";
		    	
		    }else {
		    	return "{\"msg\":\""+err_txt+".\",\"state\":\"false\"}";
		    }
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

}
