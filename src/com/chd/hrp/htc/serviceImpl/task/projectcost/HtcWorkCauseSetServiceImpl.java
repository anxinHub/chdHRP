
package com.chd.hrp.htc.serviceImpl.task.projectcost;

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
import com.chd.hrp.htc.dao.task.projectcost.HtcWorkCauseSetMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcChargeCoop;
import com.chd.hrp.htc.entity.task.projectcost.HtcWorkCauseSet;
import com.chd.hrp.htc.service.task.projectcost.HtcWorkCauseSetService;
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


@Service("htcWorkCauseSetService")
public class HtcWorkCauseSetServiceImpl implements HtcWorkCauseSetService {

	private static Logger logger = Logger.getLogger(HtcWorkCauseSetServiceImpl.class);
	
	@Resource(name = "htcWorkCauseSetMapper")
	private final HtcWorkCauseSetMapper htcWorkCauseSetMapper = null;

	@Override
	public String initHtcWorkCauseSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcWorkCauseSetMapper.initHtcWorkCauseSet(entityMap);
			return "{\"msg\":\"初始化成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"初始化成功\"}");	
		}
	}

	@Override
	public String queryHtcWorkCauseSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){ 	
			
			List<HtcWorkCauseSet> list = htcWorkCauseSetMapper.queryHtcWorkCauseSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkCauseSet> list = htcWorkCauseSetMapper.queryHtcWorkCauseSet(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcWorkCauseSet(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
         try {
			
			htcWorkCauseSetMapper.deleteBatchHtcWorkCauseSet(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除成功\"}");	
		} 
	}

	@Override
	public String updateBatchHtcWorkCauseSet(List<Map<String, Object>> listVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
				htcWorkCauseSetMapper.updateBatchHtcWorkCauseSet(listVo);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改成功\"}");	
			} 
	}
    
}
