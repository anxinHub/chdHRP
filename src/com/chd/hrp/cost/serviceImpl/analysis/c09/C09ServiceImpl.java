package com.chd.hrp.cost.serviceImpl.analysis.c09;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.analysis.c09.C09Mapper;
import com.chd.hrp.cost.service.analysis.c09.C09Service;
import com.chd.hrp.cost.serviceImpl.analysis.c01.C01ServiceImpl;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 盘亏服务实现类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("c09Service")
public class C09ServiceImpl implements C09Service{

	private static Logger logger = Logger.getLogger(C01ServiceImpl.class);

	@Resource(name = "c09Mapper")
	private final C09Mapper analysisMapper = null;

	@Override
	public String queryAnalysisC0901(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0901(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0901(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	@Override
	public List<Map<String, Object>> queryC0901Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0901Print(entityMap);
		
		return list;

	}
	@Override
	public String addCostAnalysisC0901(Map<String,Object> entityMap)throws DataAccessException{
		
		
		
		
		analysisMapper.deleteCostAnalysisC0901(entityMap);

		
		
		try {
			
			analysisMapper.addCostAnalysisC0901(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0301\"}";

		}

	}
	
	@Override
	public String queryAnalysisC0902(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0902(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0902(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0902Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0902Print(entityMap);
		
		return list;

	}
	
	@Override
	public String queryAnalysisC0903(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0903(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0903(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0903Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0903Print(entityMap);
		
		return list;

	}
	
	@Override
	public String queryAnalysisC0904(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0904(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0904(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0904Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0904Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0905(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0905(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0905(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0905Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0905Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0906(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0906(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0906(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0906Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0906Print(entityMap);
		
		return list;

	}
	@Override
	public String addCostAnalysisC0902(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0902(entityMap);

		try {
			analysisMapper.addCostAnalysisC0902(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0902\"}";
		}
	}

	@Override
	public String addCostAnalysisC0903(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0903(entityMap);

		try {
			analysisMapper.addCostAnalysisC0903(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0903\"}";
		}
	}

	@Override
	public String addCostAnalysisC0904(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0904(entityMap);

		try {
			analysisMapper.addCostAnalysisC0904(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0904\"}";
		}
	}

	@Override
	public String addCostAnalysisC0905(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0905(entityMap);

		try {
			analysisMapper.addCostAnalysisC0905(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0905\"}";
		}
	}

	@Override
	public String addCostAnalysisC0906(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0906(entityMap);

		try {
			analysisMapper.addCostAnalysisC0906(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0906\"}";
		}
	}

}
