package com.chd.hrp.acc.serviceImpl.commonbuilder;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccPermDataMapper;
import com.chd.hrp.acc.entity.AccPermData;
import com.chd.hrp.acc.entity.AccUserPermData;
import com.chd.hrp.acc.service.commonbuilder.AccPermDataService;
import com.github.pagehelper.PageInfo;
@Service("accPermDataService")
public class AccPermDataServiceImpl implements AccPermDataService{
	
	private static Logger logger = Logger.getLogger(AccPermDataServiceImpl.class);
	
	@Resource(name = "accPermDataMapper")
	private final AccPermDataMapper accPermDataMapper = null;

	@Override
	public String queryAccPermData(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccPermData> list = accPermDataMapper.queryAccPermData(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public List<AccUserPermData> queryAccUserPermData(
			Map<String, Object> entityMap)
			throws DataAccessException {
		return accPermDataMapper.queryAccUserPermData(entityMap);
	}

	@Override
	public List<AccUserPermData> queryAccRolePermData(
			Map<String, Object> entityMap) throws DataAccessException {
		return accPermDataMapper.queryAccRolePermData(entityMap);
	}

	@Override
	public String addAccUserPermData(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			accPermDataMapper.addAccUserPermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccUserPermData\"}";
		}
	}

	@Override
	public String addAccRolePermData(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			accPermDataMapper.addAccRolePermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccRolePermData\"}";
		}
	}

	@Override
	public String deleteAccRolePermData(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			accPermDataMapper.deleteAccRolePermData(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteAccRolePermData\"}";
		}
	}

	@Override
	public String deleteAccUserPermData(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			accPermDataMapper.deleteAccUserPermData(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteAccUserPermData\"}";
		}
	}

}
