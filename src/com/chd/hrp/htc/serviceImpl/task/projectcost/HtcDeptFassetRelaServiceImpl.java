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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptFassetRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptFassetRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptFassetRelaService;
import com.github.pagehelper.PageInfo;

@Service("htcDeptFassetRelaService")
public class HtcDeptFassetRelaServiceImpl implements HtcDeptFassetRelaService{
	
	private static Logger logger = Logger.getLogger(HtcDeptFassetRelaServiceImpl.class);

	@Resource(name = "htcDeptFassetRelaMapper")
	private final HtcDeptFassetRelaMapper htcDeptFassetRelaMapper = null;

	
	@Override
	public String addHtcDeptFassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptFassetRela htcDeptFassetRela = htcDeptFassetRelaMapper.queryHtcDeptFassetRelaByCode(entityMap);
			
			if(null !=htcDeptFassetRela){
				return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			}
			htcDeptFassetRelaMapper.addHtcDeptFassetRela(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptFassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptFassetRela> list = htcDeptFassetRelaMapper.queryHtcDeptFassetRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptFassetRela> list = htcDeptFassetRelaMapper.queryHtcDeptFassetRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcDeptFassetRela queryHtcDeptFassetRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptFassetRelaMapper.queryHtcDeptFassetRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptFassetRela(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 htcDeptFassetRelaMapper.deleteBatchHtcDeptFassetRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");	
			}
	}

	@Override
	public String updateHtcDeptFassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
        	htcDeptFassetRelaMapper.updateHtcDeptFassetRela(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}

}
