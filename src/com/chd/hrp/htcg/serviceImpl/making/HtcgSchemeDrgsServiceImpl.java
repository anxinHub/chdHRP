package com.chd.hrp.htcg.serviceImpl.making;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.chd.hrp.htcg.dao.making.HtcgSchemeDrgsMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeDrgs;
import com.chd.hrp.htcg.service.making.HtcgSchemeDrgsService;
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


@Service("htcgSchemeDrgsService")
public class HtcgSchemeDrgsServiceImpl implements HtcgSchemeDrgsService {

	private static Logger logger = Logger.getLogger(HtcgSchemeDrgsServiceImpl.class);
	
	@Resource(name = "htcgSchemeDrgsMapper")
	private final HtcgSchemeDrgsMapper htcgSchemeDrgsMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addHtcgSchemeDrgs(Map<String,Object> entityMap)throws DataAccessException{
		
		HtcgSchemeDrgs schemeDrgs = queryHtcgSchemeDrgsByCode(entityMap);
		if(schemeDrgs != null){
			
			return "{\"error\":\"当前数据已存在.\",\"state\":\"false\"}";
		}
		try{
			
			htcgSchemeDrgsMapper.addHtcgSchemeDrgs(entityMap);
		 
		    return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			 
	    } catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		} 
	}
	
	@Override
	public String addBatchHtcgSchemeDrgs(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try{
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for (Map<String,Object> entityMap : entityList) {
				HtcgSchemeDrgs schemeDrgs = queryHtcgSchemeDrgsByCode(entityMap);
				if(schemeDrgs != null){
					return "{\"error\":\"\",\"error\":\"当前数据已经存在. 【"+schemeDrgs.getDrgs_name()+"】\"}";
				}else {
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("scheme_code", entityMap.get("scheme_code"));
					map.put("drgs_code", entityMap.get("drgs_code"));
					list.add(map) ;
				}
			}
			
			htcgSchemeDrgsMapper.addBatchHtcgSchemeDrgs(list);
		 
		    return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		} 
		
	}
	
	/**
	 * 
	 */
	@Override
	public String queryHtcgSchemeDrgs(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeDrgs> list = htcgSchemeDrgsMapper.queryHtcgSchemeDrgs(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeDrgs> list = htcgSchemeDrgsMapper.queryHtcgSchemeDrgs(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public HtcgSchemeDrgs queryHtcgSchemeDrgsByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcgSchemeDrgsMapper.queryHtcgSchemeDrgsByCode(entityMap);
	}
	
	public String deleteBatchHtcgSchemeDrgs(List<Map<String,Object>> entityList)throws DataAccessException{
		 
		try{
			
			htcgSchemeDrgsMapper.deleteBatchHtcgSchemeDrgs(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		} 
		 
	}
	/**
	 * 
	 */
	@Override
	public String updateHtcgSchemeDrgs(Map<String,Object> entityMap)throws DataAccessException{
		
		HtcgSchemeDrgs schemeDrgs = queryHtcgSchemeDrgsByCode(entityMap);
		
		if(schemeDrgs != null){
			
			return "{\"error\":\"\",\"error\":\"当前数据已经存在.\"}";
			
		}
		
		try{
			
			htcgSchemeDrgsMapper.updateHtcgSchemeDrgs(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");

		} 
	}
}
