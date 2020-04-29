
package com.chd.hrp.htc.serviceImpl.task.readydata;

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
import com.chd.hrp.htc.dao.task.readydata.HtcPeopleTimeMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcPeopleTime;
import com.chd.hrp.htc.service.task.readydata.HtcPeopleTimeService;
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


@Service("htcPeopleTimeService")
public class HtcPeopleTimeServiceImpl implements HtcPeopleTimeService {

	private static Logger logger = Logger.getLogger(HtcPeopleTimeServiceImpl.class);
	
	@Resource(name = "htcPeopleTimeMapper")
	private final HtcPeopleTimeMapper htcPeopleTimeMapper = null;
    
	@Override
	public String addHtcPeopleTime(Map<String,Object> entityMap)throws DataAccessException{
	    
		try{
			
			HtcPeopleTime htcPeopleTime = htcPeopleTimeMapper.queryHtcPeopleTimeByCode(entityMap);
			
			if(null != htcPeopleTime){
				
				return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			}
			htcPeopleTimeMapper.addHtcPeopleTime(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	@Override
	public String queryHtcPeopleTime(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcPeopleTime> list = htcPeopleTimeMapper.queryHtcPeopleTime(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcPeopleTime> list = htcPeopleTimeMapper.queryHtcPeopleTime(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcPeopleTime queryHtcPeopleTimeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcPeopleTimeMapper.queryHtcPeopleTimeByCode(entityMap);
	}
	
	@Override
	public String deleteBatchHtcPeopleTime(List<Map<String,Object>> list)throws DataAccessException{
		try{
			htcPeopleTimeMapper.deleteBatchHtcPeopleTime(list);
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
	public String updateHtcPeopleTime(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			htcPeopleTimeMapper.updateHtcPeopleTime(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
}
