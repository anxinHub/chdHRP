package com.chd.hrp.hr.serviceImpl.base;

import java.util.HashMap;
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
import com.chd.hrp.hr.dao.base.HrRoleMapper;
import com.chd.hrp.hr.dao.base.HrUserMapper;
import com.chd.hrp.hr.dao.base.HrUserPermDataMapper;
import com.chd.hrp.hr.entity.base.HrRole;
import com.chd.hrp.hr.entity.base.HrUser;
import com.chd.hrp.hr.entity.base.HrUserPermData;
import com.chd.hrp.hr.entity.base.PermData;
import com.chd.hrp.hr.service.base.HrUserPermDataService;

import com.github.pagehelper.PageInfo;

@Service("hrUserPermDataService")
public class HrUserPermDataServiceImpl implements HrUserPermDataService{

	private static Logger logger = Logger.getLogger(HrUserPermDataServiceImpl.class);
	
	@Resource(name = "hrUserPermDataMapper")
	private final HrUserPermDataMapper hrUserPermDataMapper = null;
	
	

	@Resource(name = "hrUserMapper")
	private final HrUserMapper hrUserMapper = null;
	
	@Resource(name = "hrRoleMapper")
	private final HrRoleMapper hrRoleMapper = null;

	
	@Override
	public String queryPermData(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PermData> list = hrUserPermDataMapper.queryPermData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<PermData> list = hrUserPermDataMapper.queryPermData(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
		
	}
   
	/**
	 * 添加用户权限
	 */
	@Override
	public String addUserPermData(List<HrUserPermData> entityMap)
			throws DataAccessException {
		try {
			
			hrUserPermDataMapper.addUserPermData(entityMap);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
   /**
    * 添加角色权限
    */
	@Override
	public String addRolePermData(List<HrUserPermData> entityMap)throws DataAccessException {
		
		try {
			
			hrUserPermDataMapper.addRolePermData(entityMap);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
   /**
    * 删除角色权限
    */
	@Override
	public String deleteRolePermData(List<HrUserPermData> entityMap)
			throws DataAccessException {
		try {
			HrUserPermData hu = entityMap.get(0);
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("group_id", hu.getGroup_id());
			map.put("hos_id", hu.getHos_id());
			map.put("role_id", hu.getRole_id());
			map.put("table_code", "HR_TAB_STRUC");
			map.put("mod_code", hu.getMod_code());
			
			
			hrUserPermDataMapper.deleteRolePermData(map);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}


	@Override
	public String queryColumnIdByTableCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrUserPermDataMapper.queryColumnIdByTableCode(entityMap);
	}
     /**
      * 添加用户权限
      */
	@Override
	public String addBatchUserPermData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			hrUserPermDataMapper.addBatchUserPermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
   /**
    * 删除角色权限
    */
	@Override
	public String addBatchRolePermData(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			hrUserPermDataMapper.addBatchRolePermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	
	/**
	 * @Description 查询User分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryUser(Map<String, Object> entityMap) throws DataAccessException {

		
	SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrUser> list = hrUserMapper.queryUser(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrUser> list = hrUserMapper.queryUser(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
		
		

	}
	
	/**
	 * @Description 查询Role分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryRole(Map<String,Object> entityMap) throws DataAccessException{
		
	SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrRole> list = hrRoleMapper.queryRole(entityMap);

			return ChdJson.toJson(list);

		} else {

			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrRole> list = hrRoleMapper.queryRole(entityMap, rowBounds);
		
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}


   /**
    * 删除用户权限
    */
	@Override
	public String deleteUserPermData(List<HrUserPermData> listVo)
			throws DataAccessException {
		try {
			
			HrUserPermData hu = listVo.get(0);
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("group_id", hu.getGroup_id());
			map.put("hos_id", hu.getHos_id());
			map.put("user_id", hu.getUser_id());
			map.put("table_code", "HR_TAB_STRUC");
			map.put("mod_code", hu.getMod_code());
			
			hrUserPermDataMapper.deleteUserPermData(map);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
