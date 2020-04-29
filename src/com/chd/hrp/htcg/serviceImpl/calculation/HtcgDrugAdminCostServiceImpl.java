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
import com.chd.hrp.htcg.dao.calculation.HtcgDrugAdminCostMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDrugAdminCost;
import com.chd.hrp.htcg.service.calculation.HtcgDrugAdminCostService;
import com.github.pagehelper.PageInfo;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcgDrugAdminCostService")
public class HtcgDrugAdminCostServiceImpl implements HtcgDrugAdminCostService {

	private static Logger logger = Logger.getLogger(HtcgDrugAdminCostServiceImpl.class);

	@Resource(name = "htcgDrugAdminCostMapper")
	private final HtcgDrugAdminCostMapper htcgDrugAdminCostMapper = null;


	@Override
	public String initHtcgDrugAdminCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			   htcgDrugAdminCostMapper.initHtcgDrugAdminCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgDrugAdminCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgDrugAdminCost> list = htcgDrugAdminCostMapper.queryHtcgDrugAdminCost(entityMap) ;
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgDrugAdminCost> list = htcgDrugAdminCostMapper.queryHtcgDrugAdminCost(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String collectHtcgDeptDrugAdminCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			   htcgDrugAdminCostMapper.collectHtcgDeptDrugAdminCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgDeptDrugAdminCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
			Map<String, Object> map = htcgDrugAdminCostMapper.queryHtcgDeptDrugAdminCost(entityMap) ;
			return "{\"ms\":\"1.\",\"admin_cost_money\":\""+map.get("admin_cost_money")+"\"}";
	}

	@Override
	public String workloadHtcgDrugAdminCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			   htcgDrugAdminCostMapper.workloadHtcgDrugAdminCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"分摊失败\"}");
		}
	}

	@Override
	public String revenueHtcgDrugAdminCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			   htcgDrugAdminCostMapper.revenueHtcgDrugAdminCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"分摊失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgDrugAdminCost(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgDrugAdminCostMapper.deleteBatchHtcgDrugAdminCost(list);
			 return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	
	
}
