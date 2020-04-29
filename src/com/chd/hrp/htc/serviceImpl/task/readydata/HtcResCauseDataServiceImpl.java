
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
import com.chd.hrp.htc.dao.task.readydata.HtcResCauseDataMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcResCauseData;
import com.chd.hrp.htc.service.task.readydata.HtcResCauseDataService;
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


@Service("htcResCauseDataService")
public class HtcResCauseDataServiceImpl implements HtcResCauseDataService {

	private static Logger logger = Logger.getLogger(HtcResCauseDataServiceImpl.class);
	
	@Resource(name = "htcResCauseDataMapper")
	private final HtcResCauseDataMapper htcResCauseDataMapper = null;
	
	@Override
	public String queryHtcResCauseDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcResCauseData> list = htcResCauseDataMapper.queryHtcResCauseDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcResCauseData> list = htcResCauseDataMapper.queryHtcResCauseDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public String queryHtcResCauseData(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcResCauseData> list = htcResCauseDataMapper.queryHtcResCauseData(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcResCauseData> list = htcResCauseDataMapper.queryHtcResCauseData(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public String collectHtcResCauseData(List<Map<String,Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcResCauseDataMapper.deleteBatchHtcResCauseData(list);
			htcResCauseDataMapper.insertBatchHtcResCauseData(list);
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
	        logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"采集失败\"}");
		}
	}
}
