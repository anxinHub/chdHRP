package com.chd.hrp.htcg.serviceImpl.making;
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
import com.chd.hrp.htcg.dao.making.HtcgSchemeRecipeGroupRuleMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeRecipeGroupRule;
import com.chd.hrp.htcg.service.making.HtcgSchemeRecipeGroupRuleService;
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


@Service("htcgSchemeRecipeGroupRuleService")
public class HtcgSchemeRecipeGroupRuleServiceImpl implements HtcgSchemeRecipeGroupRuleService {

	private static Logger logger = Logger.getLogger(HtcgSchemeRecipeGroupRuleServiceImpl.class);
	
	@Resource(name = "htcgSchemeRecipeGroupRuleMapper")
	private final HtcgSchemeRecipeGroupRuleMapper htcgSchemeRecipeGroupRuleMapper = null;

	@Override
	public String initHtcgSchemeRecipeGroupRule(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgSchemeRecipeGroupRuleMapper.initHtcgSchemeRecipeGroupRule(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgSchemeRecipeGroupRule(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeRecipeGroupRule> list = htcgSchemeRecipeGroupRuleMapper.queryHtcgSchemeRecipeGroupRule(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeRecipeGroupRule> list = htcgSchemeRecipeGroupRuleMapper.queryHtcgSchemeRecipeGroupRule(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgSchemeRecipeGroupRule(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
			htcgSchemeRecipeGroupRuleMapper.deleteBatchHtcgSchemeRecipeGroupRule(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateBatchHtcgSchemeRecipeGroupRule(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
			htcgSchemeRecipeGroupRuleMapper.updateBatchHtcgSchemeRecipeGroupRule(entityList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
    
	
}
