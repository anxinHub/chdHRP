package com.chd.hrp.cost.serviceImpl.analysis.c04;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.analysis.c04.C04Mapper;
import com.chd.hrp.cost.service.analysis.c04.C04Service;
import com.chd.hrp.cost.serviceImpl.analysis.c01.C01ServiceImpl;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 习性分析服务类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("c04Service")
public class C04ServiceImpl implements C04Service{

	private static Logger logger = Logger.getLogger(C01ServiceImpl.class);

	@Resource(name = "c04Mapper")
	private final C04Mapper analysisMapper = null;

	
	
	@Override
	public String addAnalysisC0401(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			analysisMapper.deleteAnalysisC0401(entityMap);
			
			analysisMapper.addAnalysisC0401(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0401\"}";
	}
	
	@Override
	public String queryAnalysisC0401(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0401(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0401(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	@Override
	public String queryAnalysisC0402(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0402(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0402(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	

	@Override
	public String addAnalysisC0403(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
          try {
			
			analysisMapper.deleteAnalysisC0403(entityMap);
			
			analysisMapper.addAnalysisC0403(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0403\"}";
	}
	
	@Override
	public String queryAnalysisC0403(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0403(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0403(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0404(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0404(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0404(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0401Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0401Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0403Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0403Print(entityMap);
		
		return list;

	}
}
