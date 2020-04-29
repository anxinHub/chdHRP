
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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptTitleRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptTitleRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptTitleRelaService;
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


@Service("htcDeptTitleRelaService")
public class HtcDeptTitleRelaServiceImpl implements HtcDeptTitleRelaService {

	private static Logger logger = Logger.getLogger(HtcDeptTitleRelaServiceImpl.class);
	
	@Resource(name = "htcDeptTitleRelaMapper")
	private final HtcDeptTitleRelaMapper htcDeptTitleRelaMapper = null;

	@Override
	public String addHtcDeptTitleRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptTitleRela htcDeptTitleRela = htcDeptTitleRelaMapper.queryHtcDeptTitleRelaByCode(entityMap);
			if(null != htcDeptTitleRela){
				return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			}
			htcDeptTitleRelaMapper.addHtcDeptTitleRela(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptTitleRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptTitleRela> list = htcDeptTitleRelaMapper.queryHtcDeptTitleRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptTitleRela> list = htcDeptTitleRelaMapper.queryHtcDeptTitleRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcDeptTitleRela queryHtcDeptTitleRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptTitleRelaMapper.queryHtcDeptTitleRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptTitleRela(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				htcDeptTitleRelaMapper.deleteBatchHtcDeptTitleRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");	
			}
	}

	@Override
	public String updateHtcDeptTitleRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	  try {
			
			htcDeptTitleRelaMapper.updateHtcDeptTitleRela(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
	
}
