
package com.chd.hrp.htc.serviceImpl.task.plan.deptrela;

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
import com.chd.hrp.htc.dao.task.plan.deptrela.HtcTaskDeptRelaMapper;
import com.chd.hrp.htc.entity.task.plan.deptrela.HtcTaskDeptRela;
import com.chd.hrp.htc.service.task.plan.deptrela.HtcTaskDeptRelaService;
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


@Service("htcTaskDeptRelaService")
public class HtcTaskDeptRelaServiceImpl implements HtcTaskDeptRelaService {

	private static Logger logger = Logger.getLogger(HtcTaskDeptRelaServiceImpl.class);
	
	@Resource(name = "htcTaskDeptRelaMapper")
	private final HtcTaskDeptRelaMapper htcTaskDeptRelaMapper = null;
    
	@Override
	public String addHtcTaskDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcTaskDeptRela htcTaskDeptRela = htcTaskDeptRelaMapper.queryHtcTaskDeptRelaByCode(entityMap);
			
			if(null != htcTaskDeptRela){
				
				return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			}
			
			htcTaskDeptRelaMapper.addHtcTaskDeptRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcTaskDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcTaskDeptRela> list = htcTaskDeptRelaMapper.queryHtcTaskDeptRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcTaskDeptRela> list = htcTaskDeptRelaMapper.queryHtcTaskDeptRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcTaskDeptRela queryHtcTaskDeptRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcTaskDeptRelaMapper.queryHtcTaskDeptRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcTaskDeptRela(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcTaskDeptRelaMapper.deleteBatchHtcTaskDeptRela(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String updateHtcTaskDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcTaskDeptRelaMapper.updateHtcTaskDeptRela(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
}
