package com.chd.hrp.htcg.serviceImpl.setout;

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
import com.chd.hrp.htcg.dao.setout.HtcgSchemeMrGroupDataMapper;
import com.chd.hrp.htcg.entity.setout.HtcgSchemeMrGroupData;
import com.chd.hrp.htcg.service.setout.HtcgSchemeMrGroupDataService;
import com.github.pagehelper.PageInfo;

@Service("htcgSchemeMrGroupDataService")
public class HtcgSchemeMrGroupDataServiceImpl implements HtcgSchemeMrGroupDataService {

	private static Logger logger = Logger.getLogger(HtcgSchemeMrGroupDataServiceImpl.class);

	@Resource(name = "htcgSchemeMrGroupDataMapper")
	private final HtcgSchemeMrGroupDataMapper htcgSchemeMrGroupDataMapper = null;

	@Override
	public String initHtcgSchemeMrGroupData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			
			 htcgSchemeMrGroupDataMapper.initHtcgSchemeMrGroupData(entityMap);
		
			return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgSchemeMrGroupData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgSchemeMrGroupData> list = htcgSchemeMrGroupDataMapper.queryHtcgSchemeMrGroupData(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgSchemeMrGroupData> list = htcgSchemeMrGroupDataMapper.queryHtcgSchemeMrGroupData(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgSchemeMrGroupData(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
        try {
        	htcgSchemeMrGroupDataMapper.deleteBatchHtcgSchemeMrGroupData(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}



}
