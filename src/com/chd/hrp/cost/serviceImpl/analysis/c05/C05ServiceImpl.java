package com.chd.hrp.cost.serviceImpl.analysis.c05;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.analysis.c05.C05Mapper;
import com.chd.hrp.cost.service.analysis.c05.C05Service;
import com.chd.hrp.cost.serviceImpl.analysis.c01.C01ServiceImpl;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 对比分析服务类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("c05Service")
public class C05ServiceImpl implements C05Service{

	private static Logger logger = Logger.getLogger(C01ServiceImpl.class);

	@Resource(name = "c05Mapper")
	private final C05Mapper analysisMapper = null;

	@Override
	public String addAnalysisC0501(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			analysisMapper.deleteAnalysisC0501(entityMap);
			
			analysisMapper.addAnalysisC0501(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0501\"}";
	}
	
	@Override
	public String queryAnalysisC0501(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0501(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0501(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public String addAnalysisC0502(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			analysisMapper.deleteAnalysisC0502(entityMap);
			
			analysisMapper.addAnalysisC0502(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0502\"}";
	}
	@Override
	public String queryAnalysisC0502(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0502(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0502(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	


	@Override
	public String addAnalysisC0503(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			analysisMapper.deleteAnalysisC0503(entityMap);
			
			analysisMapper.addAnalysisC0503(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0503\"}";
	}
	
	@Override
	public String queryAnalysisC0503(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0503(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0503(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String addAnalysisC0504(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
          try {
			
			analysisMapper.deleteAnalysisC0504(entityMap);
			
			analysisMapper.addAnalysisC0504(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0504\"}";
	}
	
	@Override
	public String queryAnalysisC0504(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0504(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0504(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	
	@Override
	public String addAnalysisC0505(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	     try {
				
				analysisMapper.deleteAnalysisC0505(entityMap);
				
				analysisMapper.addAnalysisC0505(entityMap);
				
				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
			}
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0505\"}";
	}

	@Override
	public String queryAnalysisC0505(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0505(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0505(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	

	@Override
	public String addAnalysisC0506(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
				analysisMapper.deleteAnalysisC0506(entityMap);
				
				analysisMapper.addAnalysisC0506(entityMap);
				
				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
			}
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0506\"}";
	}

	@Override
	public String queryAnalysisC0506(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0506(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0506(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	

	@Override
	public String addAnalysisC0507(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
				analysisMapper.deleteAnalysisC0507(entityMap);
				
				analysisMapper.addAnalysisC0507(entityMap);
				
				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
			}
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0507\"}";
	}
	
	@Override
	public String queryAnalysisC0507(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0507(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0507(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String addAnalysisC0508(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0508(entityMap);
			
			analysisMapper.addAnalysisC0508(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0508\"}";
	}

	@Override
	public String queryAnalysisC0508(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0508(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0508(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String addAnalysisC0509(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0509(entityMap);
			
			analysisMapper.addAnalysisC0509(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0509\"}";
	}

	@Override
	public String queryAnalysisC0509(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0509(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0509(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	

	@Override
	public String addAnalysisC0510(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0510(entityMap);
			
			analysisMapper.addAnalysisC0510(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0510\"}";
	}

	@Override
	public String queryAnalysisC0510(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0510(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0510(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	

	@Override
	public String addAnalysisC0511(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0511(entityMap);
			
			analysisMapper.addAnalysisC0511(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0511\"}";
	}

	@Override
	public String queryAnalysisC0511(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0511(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0511(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	

	@Override
	public String addAnalysisC0512(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0512(entityMap);
			
			analysisMapper.addAnalysisC0512(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0512\"}";
	}
	
	@Override
	public String queryAnalysisC0512(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0512(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0512(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	



	@Override
	public String addAnalysisC0513(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0513(entityMap);
			
			analysisMapper.addAnalysisC0513(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0513\"}";
	}

	
	@Override
	public String queryAnalysisC0513(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0513(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0513(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	

	@Override
	public String addAnalysisC0514(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0514(entityMap);
			
			analysisMapper.addAnalysisC0514(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0514\"}";
	}

	@Override
	public String queryAnalysisC0514(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0514(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0514(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	
	@Override
	public String addAnalysisC0515(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {
			
			analysisMapper.deleteAnalysisC0515(entityMap);
			
			analysisMapper.addAnalysisC0515(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0515\"}";
	}

	@Override
	public String queryAnalysisC0515(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0515(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0515(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	

	@Override
	public String addAnalysisC0516(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        try {

			analysisMapper.deleteAnalysisC0516(entityMap);
			
			analysisMapper.addAnalysisC0516(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 addAnalysisC0516\"}";
	}
	
	@Override
	public String queryAnalysisC0516(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0516(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0516(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryC0501Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0501Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0502Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0502Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0503Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0503Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0504Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0504Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0505Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0505Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0506Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0506Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0507Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0507Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0508Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0508Print(entityMap);
		
		return list;

	}
	
	@Override
	public List<Map<String, Object>> queryC0513Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0513Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0514Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0514Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0515Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0515Print(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryC0516Print(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=analysisMapper.queryC0516Print(entityMap);
		
		return list;

	}
	
}
