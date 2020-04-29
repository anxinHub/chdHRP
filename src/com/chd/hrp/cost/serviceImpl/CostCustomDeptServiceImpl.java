/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.cost.dao.CostCustomDeptMapper;
import com.chd.hrp.cost.service.CostCustomDeptService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.service.DeptService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costCustomDeptService")
public class CostCustomDeptServiceImpl implements CostCustomDeptService {

	private static Logger logger = Logger.getLogger(CostCustomDeptServiceImpl.class);

	@Resource(name = "costCustomDeptMapper")
	private final CostCustomDeptMapper costCustomDeptMapper = null;

	/**
	 * @Description 添加Dept
	 * @param Dept
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		List<Map<String, Object>> list = costCustomDeptMapper.queryDeptById(entityMap);

		if (list.size() > 0) {

			for (Map<String, Object> dept : list) {
				if (dept.get("dept_code").equals(entityMap.get("dept_code"))) {
					return "{\"error\":\"科室编码：" + dept.get("dept_code").toString() + "已存在.\"}";
				}
				if (dept.get("dept_name").equals(entityMap.get("dept_name"))) {
					return "{\"error\":\"：科室名称" + dept.get("dept_name").toString() + "已存在.\"}";
				}
			}
		}

		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("dept_name").toString()));

		try {

			int result = costCustomDeptMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 错误编码 add\"}";

		}

	}

	/**
	 * @Description 批量添加Dept
	 * @param Dept
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costCustomDeptMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 错误编码 addBatch\"}";

		}
	}

	/**
	 * @Description 查询Dept分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = (List<Map<String, Object>>) costCustomDeptMapper.query(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 查询DeptByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return costCustomDeptMapper.queryByCode(entityMap);

	}

	/**
	 * @Description 批量删除Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
	
			 costCustomDeptMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			costCustomDeptMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDept\"}";

		}
	}


	@Transactional(rollbackFor = Exception.class)
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		List<Map<String, Object>> list = costCustomDeptMapper.queryDeptById(entityMap);

		if (list.size() > 0) {

			for (Map<String, Object> dept : list) {
				if (dept.get("dept_code").equals(entityMap.get("dept_code"))) {
					return "{\"error\":\"科室编码：" + dept.get("dept_code").toString() + "已存在.\"}";
				}
				if (dept.get("dept_name").equals(entityMap.get("dept_name"))) {
					return "{\"error\":\"：科室名称" + dept.get("dept_name").toString() + "已存在.\"}";
				}
			}
		}
		try {
			costCustomDeptMapper.update(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败! 错误编码  update\"}";

		}
	}

	/**
	 * @Description 批量更新Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costCustomDeptMapper.updateBatch(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败! 错误编码  updateBatch\"}";

		}

	}

	/**
	 * @Description 导入Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importDept(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}


	@Override
	public String updateDeptCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			costCustomDeptMapper.updateDeptCode(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDept\"}";

		}
	}

	@Override
	public String updateDeptName(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("dept_name").toString()));
			costCustomDeptMapper.updateDeptName(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDept\"}";

		}
	}
	
	/**
	 * 导入时 校验  科室类别编码是否合法
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryDeptKindDate(Map<String, Object> map) {
		
		return costCustomDeptMapper.queryDeptKindDate(map);
	}

	public List<Map<String, Object>> queryDeptById(Map<String, Object> byCodeMap) {
		// TODO Auto-generated method stub
		return costCustomDeptMapper.queryDeptById(byCodeMap);
	}

}
