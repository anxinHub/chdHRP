/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.serviceImpl.requrie.collectquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.requrie.MatRequireDetailMapper;
import com.chd.hrp.mat.dao.requrie.MatRequireMainMapper;
import com.chd.hrp.mat.dao.requrie.collectquery.MatRequireCollectQueryMapper;
import com.chd.hrp.mat.entity.MatRequireMain;
import com.chd.hrp.mat.service.requrie.collectquery.MatRequireCollectQueryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: MAT_REQUIRE_MAIN
 * @Table: MAT_REQUIRE_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Transactional
@Service("matRequireCollectQueryService")
public class MatRequireCollectQueryServiceImpl implements MatRequireCollectQueryService {

	private static Logger logger = Logger.getLogger(MatRequireCollectQueryServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matRequireMainMapper")
	private final MatRequireMainMapper matRequireMainMapper = null;

	@Resource(name = "matRequireDetailMapper")
	private final MatRequireDetailMapper matRequireDetailMapper = null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	@Resource(name = "matRequireCollectQueryMapper")
	private final MatRequireCollectQueryMapper matRequireCollectQueryMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 update\"}";
		}
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {	
		try {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatRequireMain\"}";
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList)	throws DataAccessException {
		try {
			matRequireMainMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		
		// 获取对象MAT_REQUIRE_MAIN
		MatRequireMain matRequireMain = queryByCode(entityMap);
		if (matRequireMain != null) {
			matRequireMainMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		try {
			matRequireMainMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatRequireMain\"}";
		}

	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return (T) queryByCode(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return (T) matRequireMainMapper.queryByCode(entityMap);
	}

	
	/**
	 * 科室汇总页面查询--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCollectQ(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<?> list = matRequireCollectQueryMapper.queryCollectQ(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<?> list = matRequireCollectQueryMapper.queryCollectQ(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 汇总查询   汇总单号查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCollectStore(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>) matRequireCollectQueryMapper.queryCollectStore(entityMap);
		return ChdJson.toJson(list);
	}
	/**
	 * 汇总查询--明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCollectDept(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>) matRequireCollectQueryMapper.queryCollectDept(entityMap);
		return ChdJson.toJson(list);
	}
	
	
}
