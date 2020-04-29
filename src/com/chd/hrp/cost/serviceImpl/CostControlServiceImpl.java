package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.cost.dao.CostControlMapper;
import com.chd.hrp.cost.dao.analysis.c06.C06Mapper;
import com.chd.hrp.cost.service.CostControlService;
import com.chd.hrp.cost.service.analysis.c06.C06Service;
import com.chd.hrp.cost.serviceImpl.analysis.c01.C01ServiceImpl;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 排序分析服务实现类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costControlService")
public class CostControlServiceImpl implements CostControlService{
	private static Logger logger = Logger.getLogger(CostControlServiceImpl.class);

	@Resource(name = "costControlMapper")
	private final CostControlMapper costControlMapper = null;

	@Override
	public String queryAnalysisC0601(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0601(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0601(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public String queryAnalysisC0602(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0602(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0602(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	
	@Override
	public String queryAnalysisC0603(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0603(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0603(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String queryAnalysisC0604(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0604(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0604(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	@Override
	public String queryAnalysisC0605(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0605(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0605(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	
	@Override
	public String queryAnalysisC0606(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0606(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0606(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	
	@Override
	public String queryAnalysisC0607(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0607(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0607(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	
	@Override
	public String queryAnalysisC0608(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0608(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0608(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	@Override
	public String queryAnalysisC0609(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0609(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0609(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	@Override
	public String queryAnalysisC0610(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0610(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0610(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0611(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0611(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0611(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryAnalysisC0612(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0612(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) costControlMapper.queryAnalysisC0612(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
