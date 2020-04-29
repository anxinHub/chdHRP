/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.serviceImpl.requrie.collectquery;

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
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.requrie.MedRequireDetailMapper;
import com.chd.hrp.med.dao.requrie.MedRequireMainMapper;
import com.chd.hrp.med.dao.requrie.collectquery.MedRequireCollectQueryMapper;
import com.chd.hrp.med.entity.MedRequireMain;
import com.chd.hrp.med.service.requrie.collectquery.MedRequireCollectQueryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: MED_REQUIRE_MAIN
 * @Table: MED_REQUIRE_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Transactional
@Service("medRequireCollectQueryService")
public class MedRequireCollectQueryServiceImpl implements MedRequireCollectQueryService {

	private static Logger logger = Logger.getLogger(MedRequireCollectQueryServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "medRequireMainMapper")
	private final MedRequireMainMapper medRequireMainMapper = null;

	@Resource(name = "medRequireDetailMapper")
	private final MedRequireDetailMapper medRequireDetailMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
	@Resource(name = "medRequireCollectQueryMapper")
	private final MedRequireCollectQueryMapper medRequireCollectQueryMapper = null;
	
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
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedRequireMain\"}";
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList)	throws DataAccessException {
		try {
			medRequireMainMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		
		// 获取对象MED_REQUIRE_MAIN
		MedRequireMain medRequireMain = queryByCode(entityMap);
		if (medRequireMain != null) {
			medRequireMainMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		try {
			medRequireMainMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedRequireMain\"}";
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
		return (T) medRequireMainMapper.queryByCode(entityMap);
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
			List<?> list = medRequireCollectQueryMapper.queryCollectQ(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<?> list = medRequireCollectQueryMapper.queryCollectQ(entityMap, rowBounds);
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
		List<Map<String,Object>> list = (List<Map<String,Object>>) medRequireCollectQueryMapper.queryCollectStore(entityMap);
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
		List<Map<String,Object>> list = (List<Map<String,Object>>) medRequireCollectQueryMapper.queryCollectDept(entityMap);
		return ChdJson.toJson(list);
	}
	
	
}
