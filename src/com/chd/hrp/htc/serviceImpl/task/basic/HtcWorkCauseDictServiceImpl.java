
package com.chd.hrp.htc.serviceImpl.task.basic;

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
import com.chd.hrp.htc.dao.task.basic.HtcWorkCauseDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWorkCauseDict;
import com.chd.hrp.htc.service.task.basic.HtcWorkCauseDictService;
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


@Service("htcWorkCauseDictService")
public class HtcWorkCauseDictServiceImpl implements HtcWorkCauseDictService {

	private static Logger logger = Logger.getLogger(HtcWorkCauseDictServiceImpl.class);
	
	@Resource(name = "htcWorkCauseDictMapper")
	private final HtcWorkCauseDictMapper htcWorkCauseDictMapper = null;
	
	@Override
	public String addHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException{
	  
		try{
			HtcWorkCauseDict htcWorkCauseDict = htcWorkCauseDictMapper.queryHtcWorkCauseDictByCode(entityMap);
		
			if(null != htcWorkCauseDict){
				return "{\"error\":\"职务编码重复.\",\"state\":\"false\"}";
			}
			
			htcWorkCauseDictMapper.addHtcWorkCauseDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}	
	}
	
	/**
	 * 
	 */
	@Override
	public String queryHtcWorkCauseDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkCauseDict> list = htcWorkCauseDictMapper.queryHtcWorkCauseDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkCauseDict> list = htcWorkCauseDictMapper.queryHtcWorkCauseDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 
	 */
	@Override
	public HtcWorkCauseDict queryHtcWorkCauseDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcWorkCauseDictMapper.queryHtcWorkCauseDictByCode(entityMap);
	}
	
	@Override
    public String deleteHtcWorkCauseDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			htcWorkCauseDictMapper.deleteHtcWorkCauseDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
    }
	/**
	 * 
	 */
	@Override
    public String deleteBatchHtcWorkCauseDict(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try{
			
			htcWorkCauseDictMapper.deleteBatchHtcWorkCauseDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
    }
	
	/**
	 * 
	 */
	@Override
	public String updateHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			htcWorkCauseDictMapper.updateHtcWorkCauseDict(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
}
