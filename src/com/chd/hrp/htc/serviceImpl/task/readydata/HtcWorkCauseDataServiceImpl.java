
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
import com.chd.hrp.htc.dao.task.readydata.HtcWorkCauseDataMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcWorkCauseData;
import com.chd.hrp.htc.service.task.readydata.HtcWorkCauseDataService;
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


@Service("htcWorkCauseDataService")
public class HtcWorkCauseDataServiceImpl implements HtcWorkCauseDataService {

	private static Logger logger = Logger.getLogger(HtcWorkCauseDataServiceImpl.class);
	
	@Resource(name = "htcWorkCauseDataMapper")
	private final HtcWorkCauseDataMapper htcWorkCauseDataMapper = null;

	@Override
	public String queryHtcWorkCauseDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkCauseData> list = htcWorkCauseDataMapper.queryHtcWorkCauseDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkCauseData> list = htcWorkCauseDataMapper.queryHtcWorkCauseDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcWorkCauseData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkCauseData> list = htcWorkCauseDataMapper.queryHtcWorkCauseData(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkCauseData> list = htcWorkCauseDataMapper.queryHtcWorkCauseData(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String collectHtcWorkCauseData(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcWorkCauseDataMapper.deleteBatchHtcWorkCauseData(list);
			htcWorkCauseDataMapper.insertBatchHtcWorkCauseData(list);
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
	        logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"采集失败\"}");
		}
	}
    
}
