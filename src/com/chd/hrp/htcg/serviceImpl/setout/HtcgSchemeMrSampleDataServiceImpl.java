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
import com.chd.hrp.htcg.dao.setout.HtcgSchemeMrSampleDataMapper;
import com.chd.hrp.htcg.entity.setout.HtcgSchemeMrSampleData;
import com.chd.hrp.htcg.service.setout.HtcgSchemeMrSampleDataService;
import com.github.pagehelper.PageInfo;

@Service("htcgSchemeMrSampleDataService")
public class HtcgSchemeMrSampleDataServiceImpl implements HtcgSchemeMrSampleDataService {

	private static Logger logger = Logger.getLogger(HtcgSchemeMrSampleDataServiceImpl.class);

	@Resource(name = "htcgSchemeMrSampleDataMapper")
	private final HtcgSchemeMrSampleDataMapper htcgSchemeMrSampleDataMapper = null;

	@Override
	public String initHtcgSchemeMrSampleData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgSchemeMrSampleDataMapper.initHtcgSchemeMrSampleData(entityMap);
			return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
		}
	}

	@Override
	public String queryHtcgSchemeMrSampleData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgSchemeMrSampleData> list = htcgSchemeMrSampleDataMapper.queryHtcgSchemeMrSampleData(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgSchemeMrSampleData> list = htcgSchemeMrSampleDataMapper.queryHtcgSchemeMrSampleData(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgSchemeMrSampleData(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
	  try {
			
			htcgSchemeMrSampleDataMapper.deleteBatchHtcgSchemeMrSampleData(list);
		
			return "{\"msg\":\"删除失败.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

}
