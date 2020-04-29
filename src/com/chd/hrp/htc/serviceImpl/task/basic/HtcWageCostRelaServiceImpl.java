
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
import com.chd.hrp.htc.dao.task.basic.HtcWageCostRelaMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWageCostRela;
import com.chd.hrp.htc.service.task.basic.HtcWageCostRelaService;
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


@Service("htcWageCostRelaService")
public class HtcWageCostRelaServiceImpl implements HtcWageCostRelaService {

	private static Logger logger = Logger.getLogger(HtcWageCostRelaServiceImpl.class);
	
	@Resource(name = "htcWageCostRelaMapper")
	private final HtcWageCostRelaMapper htcWageCostRelaMapper = null;
    
	@Override
	public String addHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException{
	   
		try {
			
			HtcWageCostRela htcWageCostRela = htcWageCostRelaMapper.queryHtcWageCostRelaByCode(entityMap);
			
			if(null != htcWageCostRela){
				return "{\"error\":\"工资与成本项目已存在关系.\",\"state\":\"false\"}";
			}
			htcWageCostRelaMapper.addHtcWageCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	@Override
	public String addBatchHtcWageCostRela(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}
	
	@Override
	public String queryHtcWageCostRela(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWageCostRela> list = htcWageCostRelaMapper.queryHtcWageCostRela(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWageCostRela> list = htcWageCostRelaMapper.queryHtcWageCostRela(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcWageCostRela queryHtcWageCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcWageCostRelaMapper.queryHtcWageCostRelaByCode(entityMap);
	}
	
	@Override
    public String deleteHtcWageCostRela(Map<String, Object> entityMap) throws DataAccessException {
	
		try {
			
			htcWageCostRelaMapper.deleteHtcWageCostRela(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
    }

	@Override
	public String deleteBatchHtcWageCostRela(List<Map<String, Object>> entityList)throws DataAccessException{
		
		try {
			htcWageCostRelaMapper.deleteBatchHtcWageCostRela(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	@Override
	public String updateHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			htcWageCostRelaMapper.updateHtcWageCostRela(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	
}
