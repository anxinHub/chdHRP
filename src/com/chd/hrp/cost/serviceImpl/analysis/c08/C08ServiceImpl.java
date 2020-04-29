package com.chd.hrp.cost.serviceImpl.analysis.c08;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.analysis.c08.C08Mapper;
import com.chd.hrp.cost.service.analysis.c08.C08Service;
import com.chd.hrp.cost.serviceImpl.analysis.c01.C01ServiceImpl;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 指标分析服务实现类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("c08Service")
public class C08ServiceImpl implements C08Service{

	private static Logger logger = Logger.getLogger(C01ServiceImpl.class);

	@Resource(name = "c08Mapper")
	private final C08Mapper analysisMapper = null;

	
	@Override
	public String queryAnalysisC0800(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0800(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0800(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	
	@Override
	public String queryAnalysisC0801(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0801(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0801(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	@Override
	public List<Map<String, Object>> queryC0801Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0801Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0802(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0802(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0802(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public List<Map<String, Object>> queryC0802Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0802Print(entityMap);
		
		return list;

	}

}
