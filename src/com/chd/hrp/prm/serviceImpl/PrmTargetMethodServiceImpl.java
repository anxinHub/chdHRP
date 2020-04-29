
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;
import javax.script.ScriptException;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmFunStackMapper;
import com.chd.hrp.prm.dao.PrmTargetMethodMapper;
import com.chd.hrp.prm.entity.PrmFunStack;
import com.chd.hrp.prm.entity.PrmTargetMethod;
import com.chd.hrp.prm.service.PrmTargetMethodService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 9904 绩效指标取值方法配置表
 * @Table: PRM_TARGET_METHOD
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmTargetMethodService")
public class PrmTargetMethodServiceImpl implements PrmTargetMethodService {

	private static Logger logger = Logger.getLogger(PrmTargetMethodServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmTargetMethodMapper")
	private final PrmTargetMethodMapper prmTargetMethodMapper = null;

	@Resource(name = "prmFunStackMapper")
	private final PrmFunStackMapper prmFunStackMapper = null;

	private String collect_formula;// 最终计算的计算公式

	List<Map<String, Object>> listStack = new ArrayList<Map<String, Object>>();

	LinkedHashMap<String, String> target_formula_map = new LinkedHashMap<String, String>();// 存放所有用到的计算公式key

	/**
	 * @Description 添加9904 绩效指标取值方法配置表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmTargetMethod(Map<String, Object> entityMap) throws DataAccessException {

		PrmTargetMethod ptm = queryPrmTargetMethodByCode(entityMap);

		if (ptm != null) {

			return "{\"error\":\"该指标已经配置过 \"}";

		}
		try {
			List<Map<String, Object>> stackList = new ArrayList<Map<String, Object>>();
			String method_code = (String) entityMap.get("method_code");

			prmTargetMethodMapper.addPrmTargetMethod(entityMap);

			if ("03".equals(method_code)) {
				if (!entityMap.get("fun_stack_data").toString().equals("undefined")) {
					prmFunStackMapper.deletePrmFunStack(entityMap);
					List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("fun_stack_data").toString());

					for (Map<String, Object> detailVo : detail) {

						detailVo.put("group_id", SessionManager.getGroupId());

						detailVo.put("hos_id", SessionManager.getHosId());

						detailVo.put("copy_code", SessionManager.getCopyCode());

						stackList.add(detailVo);
					}
					prmFunStackMapper.addBatchPrmFunStack(stackList);
				}
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addTargetMethod\"}";

		}

	}

	/**
	 * @Description 批量添加9904 绩效指标取值方法配置表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmTargetMethod(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmTargetMethodMapper.addBatchPrmTargetMethod(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmTargetMethod\"}";

		}

	}

	/**
	 * @Description 更新9904 绩效指标取值方法配置表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmTargetMethod(Map<String, Object> entityMap) throws DataAccessException {

		try {

			List<Map<String, Object>> stackList = new ArrayList<Map<String, Object>>();

			String method_code = (String) entityMap.get("method_code");

			if ("03".equals(method_code)) {
				if (!entityMap.get("fun_stack_data").toString().equals("undefined")) {
					prmFunStackMapper.deletePrmFunStack(entityMap);
					List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("fun_stack_data").toString());

					for (Map<String, Object> detailVo : detail) {

						detailVo.put("group_id", SessionManager.getGroupId());

						detailVo.put("hos_id", SessionManager.getHosId());

						detailVo.put("copy_code", SessionManager.getCopyCode());

						stackList.add(detailVo);
					}
					prmFunStackMapper.addBatchPrmFunStack(stackList);
				}
			}
			prmTargetMethodMapper.deletePrmTargetMethod(entityMap);

			prmTargetMethodMapper.addPrmTargetMethod(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateTargetMethod\"}";

		}

	}

	/**
	 * @Description 批量更新9904 绩效指标取值方法配置表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmTargetMethod(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmTargetMethodMapper.updateBatchPrmTargetMethod(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmTargetMethod\"}";

		}

	}

	/**
	 * @Description 删除9904 绩效指标取值方法配置表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmTargetMethod(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmTargetMethodMapper.deletePrmTargetMethod(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmTargetMethod\"}";

		}

	}

	/**
	 * @Description 批量删除9904 绩效指标取值方法配置表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmTargetMethod(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmTargetMethodMapper.deleteBatchPrmTargetMethod(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmTargetMethod\"}";

		}
	}

	/**
	 * @Description 查询结果集9904 绩效指标取值方法配置表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmTargetMethod(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmTargetMethod> list = prmTargetMethodMapper.queryPrmTargetMethod(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmTargetMethod> list = prmTargetMethodMapper.queryPrmTargetMethod(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象9904 绩效指标取值方法配置表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmTargetMethod queryPrmTargetMethodByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmTargetMethodMapper.queryPrmTargetMethodByCode(entityMap);
	}

	/**
	 * @Description 查询结果集9903 指标取值方法参数表<BR>
	 *              关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmTargetMethodNature(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmTargetMethod> list = prmTargetMethodMapper.queryPrmTargetMethodNature(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmTargetMethod> list = prmTargetMethodMapper.queryPrmTargetMethodNature(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public PrmTargetMethod queryPrmTargetMethodNatureByCode(Map<String, Object> entityMap) throws DataAccessException {

		PrmTargetMethod htm = prmTargetMethodMapper.queryPrmTargetMethodNatureByCode(entityMap);

		StringBuffer json = new StringBuffer();

		if ("03".equals(htm.getMethod_code())) {

			List list = prmFunStackMapper.queryPrmFunStack(entityMap);

			if (list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {

					PrmFunStack hfs = (PrmFunStack) list.get(i);

					json.append(hfs.getFun_para_code() + ";" + hfs.getFun_para_value() + "#");

				}

				htm.setF_p_v(json.toString().substring(0, json.toString().length() - 1));

			} else {

				htm.setF_p_v("");

			}

		}

		return htm;

	}

	@Override
	public Object target_value(Map<String, Object> entityMap) throws DataAccessException, ScriptException {
		// TODO Auto-generated method stub

		collect_formula = null;

		target_formula_map = new LinkedHashMap<String, String>();

		listStack = new ArrayList<Map<String, Object>>();

		List list = prmTargetMethodMapper.queryPrmTargetMethod(entityMap);

		for (int i = 0; i < list.size(); i++) {

			PrmTargetMethod prmTargetMethod = (PrmTargetMethod) list.get(i);

			if (collect_formula == null || collect_formula == "") {

				collect_formula = prmTargetMethod.getFormula_method_eng().replaceAll("%", "/100");

			}

			if ("02".equals(prmTargetMethod.getMethod_code())) {

				entityMap.put("formula_code", prmTargetMethod.getFormula_code());

				target_formula_map.put(prmTargetMethod.getFormula_code(), prmTargetMethod.getTarget_code());

			} else if ("01".equals(prmTargetMethod.getMethod_code())) {

			} else if ("03".equals(prmTargetMethod.getMethod_code())) {

			}

		}

		return null;
	}

	@Override
	public List<Map<String, Object>> queryPrmTargetMethodNaturePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmTargetMethod> list = prmTargetMethodMapper.queryPrmTargetMethodNature(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}

}
