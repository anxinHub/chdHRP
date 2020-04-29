package com.chd.hrp.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.PermQueryMapper;
import com.chd.hrp.sys.entity.PermData;
import com.chd.hrp.sys.entity.PermQuery;
import com.chd.hrp.sys.entity.UserPermData;
import com.chd.hrp.sys.service.PermDataService;
import com.chd.hrp.sys.service.PermQueryService;
import com.github.pagehelper.PageInfo;
@Service("permQueryService")
public class PermQueryServiceImpl implements PermQueryService{
	
	private static Logger logger = Logger.getLogger(PermQueryServiceImpl.class);
	
	@Resource(name = "permQueryMapper")
	private final PermQueryMapper permQueryMapper = null;

	@Override
	public String queryPermQuery(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<PermQuery> list = permQueryMapper.queryPermQuery(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public List<UserPermData> queryUserPermQuery(
			Map<String, Object> entityMap)
			throws DataAccessException {
		return permQueryMapper.queryUserPermQuery(entityMap);
	}

	@Override
	public List<UserPermData> queryRolePermQuery(
			Map<String, Object> entityMap) throws DataAccessException {
		return permQueryMapper.queryRolePermQuery(entityMap);
	}

	@Override
	public String addUserPermQuery(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			permQueryMapper.addUserPermQuery(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addUserPermData\"}";
		}
	}

	@Override
	public String addRolePermQuery(List<Map<String, Object>> entityMap)throws DataAccessException {
		
		try {
			
			permQueryMapper.addRolePermQuery(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addRolePermData\"}";
		}
	}

	@Override
	public String deleteRolePermQuery(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			permQueryMapper.deleteRolePermQuery(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteRolePermData\"}";
		}
	}

	@Override
	public String deleteUserPermQuery(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			permQueryMapper.deleteUserPermQuery(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteUserPermQuery\"}";
		}
	}

	@Override
	public String queryColumnIdByTableCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return permQueryMapper.queryColumnIdByTableCode(entityMap);
	}

	@Override
	public String addBatchUserPermQuery(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			permQueryMapper.addBatchUserPermQuery(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchUserPermQuery\"}";
		}
	}

	@Override
	public String addBatchRolePermQuery(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			permQueryMapper.addBatchRolePermQuery(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchRolePermData\"}";
		}
	}

}
