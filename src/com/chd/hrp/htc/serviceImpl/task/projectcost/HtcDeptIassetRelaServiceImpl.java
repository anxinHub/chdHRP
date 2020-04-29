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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptIassetRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptIassetRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptIassetRelaService;
import com.github.pagehelper.PageInfo;


@Service("htcDeptIassetRelaService")
public class HtcDeptIassetRelaServiceImpl implements HtcDeptIassetRelaService{

	private static Logger logger = Logger.getLogger(HtcDeptIassetRelaServiceImpl.class);
	
	@Resource(name = "htcDeptIassetRelaMapper")
	private final HtcDeptIassetRelaMapper htcDeptIassetRelaMapper = null;

	
	@Override
	public String addHtcDeptIassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptIassetRela htcDeptIassetRela = htcDeptIassetRelaMapper.queryHtcDeptIassetRelaByCode(entityMap);
			
			if(null != htcDeptIassetRela){
				return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			}
			htcDeptIassetRelaMapper.addHtcDeptIassetRela(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptIassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptIassetRela> list = htcDeptIassetRelaMapper.queryHtcDeptIassetRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptIassetRela> list = htcDeptIassetRelaMapper.queryHtcDeptIassetRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcDeptIassetRela queryHtcDeptIassetRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptIassetRelaMapper.queryHtcDeptIassetRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptIassetRela(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 htcDeptIassetRelaMapper.deleteBatchHtcDeptIassetRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");	
			}
	}

	@Override
	public String updateHtcDeptIassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
        	htcDeptIassetRelaMapper.updateHtcDeptIassetRela(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
}
