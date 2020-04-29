package com.chd.hrp.cost.serviceImpl.analysis.c01;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.analysis.c01.C01Mapper;
import com.chd.hrp.cost.service.analysis.c01.C01Service;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 结余分析服务类<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("c01Service")
public class C01ServiceImpl implements C01Service {

	private static Logger logger = Logger.getLogger(C01ServiceImpl.class);

	@Resource(name = "c01Mapper")
	private final C01Mapper analysisMapper = null;

	@Override
	public String queryAnalysisC0101(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0101(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0101(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	@Override
	public List<Map<String, Object>> queryC0101Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0101Print(entityMap);
		
		return list;

	}

	@Override
	public String addCostAnalysisC0101(Map<String,Object> entityMap)throws DataAccessException{
	
		analysisMapper.deleteCostAnalysisC0101(entityMap);
		try {
			
			analysisMapper.addCostAnalysisC0101(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0301\"}";
		}

	}
	
	
	@Override
	public String queryAnalysisC0102(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0102(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0102(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0102Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0102Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0103(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0103(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0103(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0103Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0103Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0104(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0104(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0104(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0104Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0104Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0105(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0105(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0105(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0105Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0105Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0106(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0106(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0106(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0106Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0106Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0107(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0107(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0107(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0107Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0107Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0108(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0108(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0108(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0108Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0108Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0109(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0109(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0109(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0109Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0109Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0110(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0110(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0110(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0110Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0110Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0111(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0111(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0111(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0111Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0111Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0112(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0112(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0112(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0112Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0112Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0113(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0113(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0113(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0113Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0113Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0114(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0114(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0114(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0114Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0114Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0115(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0115(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0115(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0115Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0115Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0116(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0116(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0116(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0116Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0116Print(entityMap);
		
		return list;

	}
	@Override
	public String queryAnalysisC0117(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0117(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0117(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0117Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0117Print(entityMap);
		
		return list;

	}
    
	@Override
	public String addCostAnalysisC0102(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0102(entityMap);
		try {
			
			analysisMapper.addCostAnalysisC0102(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0102\"}";
		}
	}


	@Override
	public String addCostAnalysisC0103(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0103(entityMap);
		try {
			
			analysisMapper.addCostAnalysisC0103(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0103\"}";
		}
	}


	@Override
	public String addCostAnalysisC0104(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0104(entityMap);
		try {
			
			analysisMapper.addCostAnalysisC0104(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0104\"}";
		}
	}


	@Override
	public String addCostAnalysisC0105(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0105(entityMap);
		try {
			
			analysisMapper.addCostAnalysisC0105(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0105\"}";
		}
	}


	@Override
	public String addCostAnalysisC0106(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0106(entityMap);
		try {
			
			analysisMapper.addCostAnalysisC0106(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0106\"}";
		}
	}


	@Override
	public String addCostAnalysisC0107(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		analysisMapper.deleteCostAnalysisC0107(entityMap);
		try {
			
			analysisMapper.addCostAnalysisC0107(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0107\"}";
		}
	}

}
