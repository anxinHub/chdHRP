/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.business.invdisburse;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.invdisburse.BudgMatMapper;
import com.chd.hrp.budg.entity.BudgMat;
import com.chd.hrp.budg.service.business.invdisburse.BudgMatService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 科室材料支出预算
 * @Table: BUDG_MAT
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgMatService")
public class BudgMatServiceImpl implements BudgMatService {

	private static Logger logger = Logger.getLogger(BudgMatServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgMatMapper")
	private final BudgMatMapper budgMatMapper = null;

	/**
	 * @Description 添加科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象科室材料支出预算
		BudgMat budgMat = queryByCode(entityMap);
		if (budgMat != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			budgMatMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}

	}

	/**
	 * @Description 批量添加科室材料支出预算<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgMatMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}

	}

	/**
	 * @Description 更新科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgMatMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}

	}

	/**
	 * @Description 批量更新科室材料支出预算<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgMatMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}

	}

	/**
	 * @Description 删除科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgMatMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}

	}

	/**
	 * @Description 批量删除科室材料支出预算<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			budgMatMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	/**
	 * @Description 添加科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象科室材料支出预算

		List<BudgMat> list = (List<BudgMat>) budgMatMapper.queryExists(entityMap);

		if (list.size() > 0) {
			budgMatMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}

		try {
			budgMatMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}

	}

	/**
	 * @Description 查询结果集科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgMat> list = (List<BudgMat>) budgMatMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgMat> list = (List<BudgMat>) budgMatMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象科室材料支出预算<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgMatMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取科室材料支出预算<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return BudgMat
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgMatMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取科室材料支出预算<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<BudgMat>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgMatMapper.queryExists(entityMap);
	}

	@Override
	public String queryHosDeptDict(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgMatMapper.queryHosDeptDict(mapVo));
	}

	@Override
	public String queryBudgMatTypeSubj(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgMatMapper.queryBudgMatTypeSubj(mapVo));
	}

	@Override
	public String collectBudgMat(Map<String, Object> mapVo) throws DataAccessException {
		//List<Map<String,Object>> list = budgMatMapper.getCollectData(mapVo);
		try {
			budgMatMapper.saveOrUpdateBatch(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
		
	}
	

	/**
	 * 查询数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> map) throws DataAccessException {
		
		return budgMatMapper.queryDataExist(map);
		
	}

}
