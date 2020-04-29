
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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptWorkRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptWorkRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptWorkRelaService;
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

@Service("htcDeptWorkRelaService")
public class HtcDeptWorkRelaServiceImpl implements HtcDeptWorkRelaService {

	private static Logger logger = Logger.getLogger(HtcDeptWorkRelaServiceImpl.class);
	
	@Resource(name = "htcDeptWorkRelaMapper")
	private final HtcDeptWorkRelaMapper htcDeptWorkRelaMapper = null;

	@Override
	public String addHtcDeptWorkRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptWorkRela htcDeptWorkRela = htcDeptWorkRelaMapper.queryHtcDeptWorkRelaByCode(entityMap);
			
			if(null !=htcDeptWorkRela){
				return "{\"error\":\"对应关系已经存在.\",\"state\":\"false\"}";
			}
			
			htcDeptWorkRelaMapper.addHtcDeptWorkRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptWorkRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptWorkRela> list = htcDeptWorkRelaMapper.queryHtcDeptWorkRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptWorkRela> list = htcDeptWorkRelaMapper.queryHtcDeptWorkRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcDeptWorkRela queryHtcDeptWorkRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptWorkRelaMapper.queryHtcDeptWorkRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptWorkRela(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
			htcDeptWorkRelaMapper.deleteBatchHtcDeptWorkRela(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String updateHtcDeptWorkRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		    try {
		    	
				htcDeptWorkRelaMapper.updateHtcDeptWorkRela(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");	
			}
	}


    
}
