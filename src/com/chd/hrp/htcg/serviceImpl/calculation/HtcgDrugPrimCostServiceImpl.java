package com.chd.hrp.htcg.serviceImpl.calculation;

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
import com.chd.hrp.htcg.dao.calculation.HtcgDrugPrimCostMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDrugPrimCost;
import com.chd.hrp.htcg.service.calculation.HtcgDrugPrimCostService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcgDrugPrimCostService")
public class HtcgDrugPrimCostServiceImpl implements HtcgDrugPrimCostService {

	private static Logger logger = Logger.getLogger(HtcgDrugPrimCostServiceImpl.class);
	
	@Resource(name = "htcgDrugPrimCostMapper")
	private final HtcgDrugPrimCostMapper htcgDrugPrimCostMapper = null;

	@Override
	public String initHtcgDrugPrimCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
		
			    htcgDrugPrimCostMapper.initHtcgDrugPrimCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}
	
	@Override
	public String queryHtcgDrugPrimCost(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgDrugPrimCost> list = htcgDrugPrimCostMapper.queryHtcgDrugPrimCost(entityMap) ;
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgDrugPrimCost> list = htcgDrugPrimCostMapper.queryHtcgDrugPrimCost(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	public String updateBatchMarkupPercentDrugPrimCost(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			htcgDrugPrimCostMapper.updateBatchMarkupPercentDrugPrimCost(entityList);
			 return"{\"msg\":\"填充成功.\",\"state\":\"true\"}";
	   	     
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"填充失败\"}");
		}
		
	}
	
	@Override
    public String calHtcgDrugPrimCost(Map<String, Object> entityMap) throws DataAccessException {
		 try {
			 
			 try {
					
			    htcgDrugPrimCostMapper.calHtcgDrugPrimCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"生成失败\"}");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"计算失败\"}");
		}
    }
	
	public String deleteBatchHtcgDrugPrimCost(List<Map<String,Object>> entityList)throws DataAccessException{
		try{
			htcgDrugPrimCostMapper.deleteBatchHtcgDrugPrimCost(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
  			logger.error(e.getMessage(), e);
  			throw new SysException("{\"error\":\"删除失败\"}");
  		}
	}

}
