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
import com.chd.hrp.htcg.dao.making.HtcgSchemeIcd10RuleMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeDrgs;
import com.chd.hrp.htcg.entity.making.HtcgSchemeGeneralRule;
import com.chd.hrp.htcg.entity.making.HtcgSchemeIcd10Rule;
import com.chd.hrp.htcg.entity.making.HtcgSchemeIcd9Rule;
import com.chd.hrp.htcg.service.making.HtcgSchemeIcd10RuleService;
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
@Service("htcgSchemeIcd10RuleService")
public class HtcgSchemeIcd10RuleServiceImpl implements HtcgSchemeIcd10RuleService {

	private static Logger logger = Logger.getLogger(HtcgSchemeIcd10RuleServiceImpl.class);
	
	@Resource(name = "htcgSchemeIcd10RuleMapper")
	private final HtcgSchemeIcd10RuleMapper htcgSchemeIcd10RuleMapper = null;
    
	@Override
    public String queryHtcgSchemeDrgsRule(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeDrgs> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeDrgsRule(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeDrgs> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeDrgsRule(entityMap, rowBounds) ;
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
    }
	

	@Override
	public String addHtcgSchemeIcd10Rule(Map<String,Object> entityMap)throws DataAccessException{
		try{
			
			HtcgSchemeIcd10Rule htcgSchemeIcd10Rule = htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd10RuleByCode(entityMap);
			
			if(null != htcgSchemeIcd10Rule){
				
				return "{\"error\":\"该诊断已存在入组规则!.\",\"state\":\"false\"}";
			}
			htcgSchemeIcd10RuleMapper.addHtcgSchemeIcd10Rule(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	    } catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	public String deleteBatchHtcgSchemeIcd10Rule(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try{ 
				
		        int state = htcgSchemeIcd10RuleMapper.deleteBatchHtcgSchemeIcd10Rule(entityList);
	        	 
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}"; 
				
		    } catch (Exception e) {
	
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
	
			}
			
		}

	@Override
	public HtcgSchemeIcd10Rule queryHtcgSchemeIcd10RuleByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd10RuleByCode(entityMap);
	}
	@Override
	public String queryHtcgSchemeIcd10Rule(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeIcd10Rule> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd10Rule(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeIcd10Rule> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd10Rule(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	
	@Override
    public String addHtcgSchemeIcd9Rule(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
		 	
			HtcgSchemeIcd9Rule htcgSchemeIcd9Rule = htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd9RuleByCode(entityMap);
			 
			if(null != htcgSchemeIcd9Rule){
						
			 return "{\"error\":\"该手术已存在入组规则!.\",\"state\":\"false\"}";
			}
	
			htcgSchemeIcd10RuleMapper.addHtcgSchemeIcd9Rule(entityMap);
		 
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}		
    }
	
	@Override
    public String deleteBatchHtcgSchemeIcd9Rule(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try{
		 
			htcgSchemeIcd10RuleMapper.deleteBatchHtcgSchemeIcd9Rule(entityList);
	 
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}
    }
	
	@Override
	public HtcgSchemeIcd9Rule queryHtcgSchemeIcd9RuleByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd9RuleByCode(entityMap);
	}
	
	@Override
    public String queryHtcgSchemeIcd9Rule(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeIcd9Rule> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd9Rule(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeIcd9Rule> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeIcd9Rule(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
    }

	@Override
    public String queryHtcgSchemeGeneralRule(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeGeneralRule> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeGeneralRule(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeGeneralRule> list = htcgSchemeIcd10RuleMapper.queryHtcgSchemeGeneralRule(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
    }
}
