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
import com.chd.hrp.htc.dao.task.projectcost.HtcResCauseSetMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcResCauseSet;
import com.chd.hrp.htc.service.task.projectcost.HtcResCauseSetService;
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

@Service("htcResCauseSetService")
public class HtcResCauseSetServiceImpl implements HtcResCauseSetService {

	private static Logger logger = Logger.getLogger(HtcResCauseSetServiceImpl.class);

	@Resource(name = "htcResCauseSetMapper")
	private final HtcResCauseSetMapper htcResCauseSetMapper = null;

	@Override
    public String initHtcResCauseSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			htcResCauseSetMapper.initHtcResCauseSet(entityMap);
			
			return "{\"msg\":\"初始化成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"初始化失败\"}");	
		}
    }
	
	@Override
	public String queryHtcResCauseSet(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcResCauseSet> list = htcResCauseSetMapper.queryHtcResCauseSet(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcResCauseSet> list = htcResCauseSetMapper.queryHtcResCauseSet(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	

	@Override
	public String updateBatchHtcResCauseSet(List<Map<String, Object>> entityList)
			throws DataAccessException {
		// TODO Auto-generated method stub

		try{
			htcResCauseSetMapper.updateBatchHtcResCauseSet(entityList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
	
	
	
	
	/**
	 * 
	 */
	@Override
	public String deleteBatchHtcResCauseSet(List<Map<String,Object>> entityList) throws DataAccessException {
		
		try{
			
			htcResCauseSetMapper.deleteBatchHtcResCauseSet(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcResCauseSet\"}";
		}
	}

}
