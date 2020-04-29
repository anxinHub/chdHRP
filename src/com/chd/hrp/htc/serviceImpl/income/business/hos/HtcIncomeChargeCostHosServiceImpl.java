package com.chd.hrp.htc.serviceImpl.income.business.hos;

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
import com.chd.hrp.htc.dao.income.business.hos.HtcIncomeChargeCostHosMapper;
import com.chd.hrp.htc.entity.income.business.hos.HtcIncomeChargeCostHos;
import com.chd.hrp.htc.service.income.business.hos.HtcIncomeChargeCostHosService;
import com.github.pagehelper.PageInfo;
 
@Service("htcIncomeChargeCostHosService")
public class HtcIncomeChargeCostHosServiceImpl implements HtcIncomeChargeCostHosService{
	

 private static Logger logger = Logger.getLogger(HtcIncomeChargeCostHosServiceImpl.class);
	
	@Resource(name = "htcIncomeChargeCostHosMapper")
	private final HtcIncomeChargeCostHosMapper htcIncomeChargeCostHosMapper = null;

	@Override
	public String addHtcIncomeChargeCostHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			   String err_txt = "";
			   htcIncomeChargeCostHosMapper.addHtcIncomeChargeCostHos(entityMap);
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
	public String queryHtcIncomeChargeCostHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<HtcIncomeChargeCostHos> list = htcIncomeChargeCostHosMapper.queryHtcIncomeChargeCostHos(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<HtcIncomeChargeCostHos> list = htcIncomeChargeCostHosMapper.queryHtcIncomeChargeCostHos(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
	}

}
