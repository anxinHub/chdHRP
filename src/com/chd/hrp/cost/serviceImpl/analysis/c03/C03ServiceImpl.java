package com.chd.hrp.cost.serviceImpl.analysis.c03;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.analysis.c03.C03Mapper;
import com.chd.hrp.cost.entity.CostIncomeDetail;
import com.chd.hrp.cost.service.analysis.c03.C03Service;
import com.chd.hrp.cost.serviceImpl.analysis.c01.C01ServiceImpl;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 分类分析服务类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("c03Service")
public class C03ServiceImpl implements C03Service{

	private static Logger logger = Logger.getLogger(C01ServiceImpl.class);

	@Resource(name = "c03Mapper")
	private final C03Mapper analysisMapper = null;

	@Override
	public String queryAnalysisC0301(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0301(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0301(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public String addCostAnalysisC0301(Map<String,Object> entityMap)throws DataAccessException{
		
		
		
		
		analysisMapper.deleteCostAnalysisC0301(entityMap);

		
		
		try {
			
			analysisMapper.addCostAnalysisC0301(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addCostAnalysisC0301\"}";

		}

	}
	
	@Override
	public String queryAnalysisC0302(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0302(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0302(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0303(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0303(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0303(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0304(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0304(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0304(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0305(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0305(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0305(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0306(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0306(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0306(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0307(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0307(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0307(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0308(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0308(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0308(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public List<Map<String, Object>> queryC0301Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0301Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0302Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0302Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0303Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0303Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0304Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0304Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0305Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0305Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0306Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0306Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0307Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0307Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0308Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0308Print(entityMap);
		
		return list;

	}

}
