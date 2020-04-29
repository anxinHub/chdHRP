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
import com.chd.hrp.htcg.dao.making.HtcgSchemeMrRuleMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeMrRule;
import com.chd.hrp.htcg.entity.making.HtcgSchemePathConf;
import com.chd.hrp.htcg.service.making.HtcgSchemeMrRuleService;
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

@Service("htcgSchemeMrRuleService")
public class HtcgSchemeMrRuleServiceImpl implements HtcgSchemeMrRuleService {

	private static Logger logger = Logger.getLogger(HtcgSchemeMrRuleServiceImpl.class);

	@Resource(name = "htcgSchemeMrRuleMapper")
	private final HtcgSchemeMrRuleMapper htcgSchemeMrRuleMapper = null;

	@Override
	public String addHtcgSchemeMrRule(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgSchemeMrRuleMapper.addHtcgSchemeMrRule(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}


	@Override
	public String initHtcgSchemeMrRule(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	  try {
			
			htcgSchemeMrRuleMapper.initHtcgSchemeMrRule(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}


	@Override
	public String queryHtcgSchemeMrRule(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeMrRule> list = htcgSchemeMrRuleMapper.queryHtcgSchemeMrRule(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeMrRule> list = htcgSchemeMrRuleMapper.queryHtcgSchemeMrRule(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcgSchemeMrRule queryHtcgSchemeMrRuleByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgSchemeMrRuleMapper.queryHtcgSchemeMrRuleByCode(entityMap);
	}

	@Override
	public String deleteHtcgSchemeMrRule(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
				htcgSchemeMrRuleMapper.deleteHtcgSchemeMrRule(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String deleteBatchHtcgSchemeMrRule(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
				htcgSchemeMrRuleMapper.deleteBatchHtcgSchemeMrRule(list);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String updateHtcgSchemeMrRule(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			htcgSchemeMrRuleMapper.updateHtcgSchemeMrRule(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}


	@Override
	public String updateBatchHtcgSchemeMrRule(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			 
				htcgSchemeMrRuleMapper.updateBatchHtcgSchemeMrRule(list);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

	
}
