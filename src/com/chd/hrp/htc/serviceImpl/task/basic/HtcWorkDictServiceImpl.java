
package com.chd.hrp.htc.serviceImpl.task.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.task.basic.HtcWorkDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWorkDict;
import com.chd.hrp.htc.service.task.basic.HtcWorkDictService;
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


@Service("htcWorkDictService")
public class HtcWorkDictServiceImpl implements HtcWorkDictService {

	private static Logger logger = Logger.getLogger(HtcWorkDictServiceImpl.class);
	
	@Resource(name = "htcWorkDictMapper")
	private final HtcWorkDictMapper htcWorkDictMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addHtcWorkDict(Map<String,Object> entityMap)throws DataAccessException{
	   
		try{
			HtcWorkDict htcWorkDict = htcWorkDictMapper.queryHtcWorkDictByCode(entityMap);
		
			if(null != htcWorkDict){
				return "{\"error\":\"作业编码已存在.\",\"state\":\"false\"}";
			}
			
			htcWorkDictMapper.addHtcWorkDict(entityMap);
			
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
	public String queryHtcWorkDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkDict> list = htcWorkDictMapper.queryHtcWorkDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkDict> list = htcWorkDictMapper.queryHtcWorkDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 
	 */
	@Override
	public HtcWorkDict queryHtcWorkDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcWorkDictMapper.queryHtcWorkDictByCode(entityMap);
	}
	
	
	@Override
    public String deleteHtcWorkDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			htcWorkDictMapper.deleteHtcWorkDict(entityMap);
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
	public String deleteBatchHtcWorkDict(List<Map<String, Object>> entityList)throws DataAccessException{
		try{
			
			htcWorkDictMapper.deleteBatchHtcWorkDict(entityList);
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
	public String updateHtcWorkDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			htcWorkDictMapper.updateHtcWorkDict(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

}
