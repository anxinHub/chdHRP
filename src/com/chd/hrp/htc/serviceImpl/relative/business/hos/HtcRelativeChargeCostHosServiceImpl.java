package com.chd.hrp.htc.serviceImpl.relative.business.hos;

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
import com.chd.hrp.htc.dao.relative.business.hos.HtcRelativeChargeCostHosMapper;
import com.chd.hrp.htc.entity.relative.business.hos.HtcRelativeChargeCostHos;
import com.chd.hrp.htc.service.relative.business.hos.HtcRelativeChargeCostHosService;
import com.github.pagehelper.PageInfo;
 
@Service("htcRelativeChargeCostHosService")
public class HtcRelativeChargeCostHosServiceImpl implements HtcRelativeChargeCostHosService{
	

 private static Logger logger = Logger.getLogger(HtcRelativeChargeCostHosServiceImpl.class);
	
	@Resource(name = "htcRelativeChargeCostHosMapper")
	private final HtcRelativeChargeCostHosMapper htcRelativeChargeCostHosMapper = null;

	@Override
	public String addHtcRelativeChargeCostHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			 String err_txt = "";
			htcRelativeChargeCostHosMapper.addHtcRelativeChargeCostHos(entityMap);
			    
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

	@Override
	public String queryHtcRelativeChargeCostHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<HtcRelativeChargeCostHos> list = htcRelativeChargeCostHosMapper.queryHtcRelativeChargeCostHos(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<HtcRelativeChargeCostHos> list = htcRelativeChargeCostHosMapper.queryHtcRelativeChargeCostHos(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
	}

}
