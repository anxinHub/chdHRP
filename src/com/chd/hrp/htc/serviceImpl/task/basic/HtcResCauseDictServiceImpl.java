
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
import com.chd.hrp.htc.dao.task.basic.HtcResCauseDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcResCauseDict;
import com.chd.hrp.htc.service.task.basic.HtcResCauseDictService;
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


@Service("htcResCauseDictService")
public class HtcResCauseDictServiceImpl implements HtcResCauseDictService {

	private static Logger logger = Logger.getLogger(HtcResCauseDictServiceImpl.class);
	
	@Resource(name = "htcResCauseDictMapper")
	private final HtcResCauseDictMapper htcResCauseDictMapper = null;
    
	@Override
	public String addHtcResCauseDict(Map<String,Object> entityMap)throws DataAccessException{
	
		try{
			
			HtcResCauseDict htcResCauseDict = htcResCauseDictMapper.queryHtcResCauseDictByCode(entityMap);
		
			if(null != htcResCauseDict){
				return "{\"error\":\"资源动因已存在.\",\"state\":\"false\"}";
			}
			
			htcResCauseDictMapper.addHtcResCauseDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	@Override
	public String queryHtcResCauseDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			List<HtcResCauseDict> list = htcResCauseDictMapper.queryHtcResCauseDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcResCauseDict> list = htcResCauseDictMapper.queryHtcResCauseDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 
	 */
	@Override
	public HtcResCauseDict queryHtcResCauseDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcResCauseDictMapper.queryHtcResCauseDictByCode(entityMap);
	}
	
	
	/**
	 * 
	 */
	@Override
	public String deleteHtcResCauseDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try{
			
			htcResCauseDictMapper.deleteHtcResCauseDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
		
	}
	
	@Override
	public String deleteBatchHtcResCauseDict(List<Map<String, Object>> entityList)throws DataAccessException{
		
		try{
			
			htcResCauseDictMapper.deleteBatchHtcResCauseDict(entityList);
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
	public String updateHtcResCauseDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			htcResCauseDictMapper.updateHtcResCauseDict(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

}
