/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.books.subjaccount;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.chd.hrp.acc.dao.books.subjaccount.AccMultiPlanMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccMultiPlanSubjMapper;
import com.chd.hrp.acc.entity.AccMultiPlan;
import com.chd.hrp.acc.entity.AccMultiPlanSubj;
import com.chd.hrp.acc.service.books.subjaccount.AccMultiPlanService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 多栏方案<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accMultiPlanService")
public class AccMultiPlanServiceImpl implements AccMultiPlanService {

	private static Logger logger = Logger.getLogger(AccMultiPlanServiceImpl.class);

	@Resource(name = "accMultiPlanMapper")
	private final AccMultiPlanMapper accMultiPlanMapper = null;

	@Resource(name = "accMultiPlanSubjMapper")
	private final AccMultiPlanSubjMapper accMultiPlanSubjMapper = null;

	/**
	 * @Description 多栏方案<BR>
	 *              添加AccMultiPlan
	 * @param AccMultiPlan
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccMultiPlan(Map<String, Object> entityMap) throws DataAccessException {

		List<AccMultiPlan> list = accMultiPlanMapper.queryAccMultiPlan(entityMap);
		if (!list.isEmpty()) {
			return "{\"error\":\"添加失败 名称重复!\"}";
		}
		
		List<Map<String, Object>> subjList = new ArrayList<Map<String, Object>>();
		String MultiPlan = accMultiPlanMapper.quertMaxMulti(entityMap);
		if (MultiPlan == null) {
			entityMap.put("plan_code", 001);
		} else {
			entityMap.put("plan_code", Integer.parseInt(MultiPlan) + 1);
		}
		try {

			if ("1".equals(entityMap.get("analy_type"))) {

				String[] subj_id = entityMap.get("debitList").toString().split(",");

				for (int i = 0; i < subj_id.length; i++) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("subj_code", subj_id[i]);

					map.put("subj_dire", "0");

					map.put("group_id", entityMap.get("group_id"));

					map.put("hos_id", entityMap.get("hos_id"));

					map.put("copy_code", entityMap.get("copy_code"));

					map.put("plan_code", entityMap.get("plan_code"));

					subjList.add(map);

				}

			} else if ("2".equals(entityMap.get("analy_type"))) {

				String[] subj_id = entityMap.get("creditList").toString().split(",");

				for (int i = 0; i < subj_id.length; i++) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("subj_code", subj_id[i]);

					map.put("subj_dire", "1");

					map.put("group_id", entityMap.get("group_id"));

					map.put("hos_id", entityMap.get("hos_id"));

					map.put("copy_code", entityMap.get("copy_code"));

					map.put("plan_code", entityMap.get("plan_code"));

					subjList.add(map);

				}

			} else {

				String[] subj_id = entityMap.get("debitList").toString().split(",");

				for (int i = 0; i < subj_id.length; i++) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("subj_code", subj_id[i]);

					map.put("subj_dire", "0");

					map.put("group_id", entityMap.get("group_id"));

					map.put("hos_id", entityMap.get("hos_id"));

					map.put("copy_code", entityMap.get("copy_code"));

					map.put("plan_code", entityMap.get("plan_code"));

					subjList.add(map);

				}

				String[] acc_subj = entityMap.get("creditList").toString().split(",");

				for (int i = 0; i < acc_subj.length; i++) {

					Map<String, Object> subjMap = new HashMap<String, Object>();

					subjMap.put("subj_code", acc_subj[i]);

					subjMap.put("subj_dire", "1");

					subjMap.put("group_id", entityMap.get("group_id"));

					subjMap.put("hos_id", entityMap.get("hos_id"));

					subjMap.put("copy_code", entityMap.get("copy_code"));

					subjMap.put("plan_code", entityMap.get("plan_code"));

					subjList.add(subjMap);

				}

			}

			accMultiPlanMapper.addAccMultiPlan(entityMap);

			accMultiPlanSubjMapper.addBatchAccMultiPlanSubj(subjList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			throw new SysException();

		}

	}

	/**
	 * @Description 多栏方案<BR>
	 *              批量添加AccMultiPlan
	 * @param AccMultiPlan
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccMultiPlan(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accMultiPlanMapper.addBatchAccMultiPlan(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMultiPlan\"}";

		}
	}

	/**
	 * @Description 多栏方案<BR>
	 *              查询AccMultiPlan分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccMultiPlan(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AccMultiPlan> list = accMultiPlanMapper.queryAccMultiPlan(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AccMultiPlan> list = accMultiPlanMapper.queryAccMultiPlan(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}

	}

	/**
	 * @Description 多栏方案<BR>
	 *              查询AccMultiPlanByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccMultiPlan queryAccMultiPlanByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accMultiPlanMapper.queryAccMultiPlanByCode(entityMap);

	}

	/**
	 * @Description 多栏方案<BR>
	 *              批量删除AccMultiPlan
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccMultiPlan(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = accMultiPlanMapper.deleteBatchAccMultiPlan(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMultiPlan\"}";

		}

	}

	/**
	 * @Description 多栏方案<BR>
	 *              删除AccMultiPlan
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccMultiPlan(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accMultiPlanMapper.deleteAccMultiPlan(entityMap);

			accMultiPlanSubjMapper.deleteAccMultiPlanSubj(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMultiPlan\"}";

		}
	}

	/**
	 * @Description 多栏方案<BR>
	 *              更新AccMultiPlan
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccMultiPlan(Map<String, Object> entityMap) throws DataAccessException {
		List<AccMultiPlan> list = accMultiPlanMapper.queryAccMultiPlan(entityMap);
		if (!list.isEmpty()) {
			if(!list.get(0).getPlan_code().equals(entityMap.get("plan_code"))) {
				return "{\"error\":\"修改失败 名称重复!\"}";
			}
		}
		
		List<Map<String, Object>> subjList = new ArrayList<Map<String, Object>>();
		try {
			if ("1".equals(entityMap.get("analy_type"))) {
				String[] subj_id = entityMap.get("debitList").toString().split(",");
				for (int i = 0; i < subj_id.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("subj_code", subj_id[i]);
					map.put("subj_dire", "0");
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("plan_code", entityMap.get("plan_code"));
					subjList.add(map);
				}
			} else if ("2".equals(entityMap.get("analy_type"))) {
				String[] subj_id = entityMap.get("creditList").toString().split(",");
				for (int i = 0; i < subj_id.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("subj_code", subj_id[i]);
					map.put("subj_dire", "1");
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("plan_code", entityMap.get("plan_code"));
					subjList.add(map);
				}
			} else {
				String[] subj_id = entityMap.get("debitList").toString().split(",");
				for (int i = 0; i < subj_id.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("subj_code", subj_id[i]);
					map.put("subj_dire", "0");
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("plan_code", entityMap.get("plan_code"));
					subjList.add(map);
				}
				
				String[] acc_subj = entityMap.get("creditList").toString().split(",");
				for (int i = 0; i < acc_subj.length; i++) {
					Map<String, Object> subjMap = new HashMap<String, Object>();
					subjMap.put("subj_code", acc_subj[i]);
					subjMap.put("subj_dire", "1");
					subjMap.put("group_id", entityMap.get("group_id"));
					subjMap.put("hos_id", entityMap.get("hos_id"));
					subjMap.put("copy_code", entityMap.get("copy_code"));
					subjMap.put("plan_code", entityMap.get("plan_code"));
					subjList.add(subjMap);
				}
			}
			accMultiPlanMapper.updateAccMultiPlan(entityMap);

			accMultiPlanSubjMapper.deleteBatchAccMultiPlanSubj(subjList);
			accMultiPlanSubjMapper.addBatchAccMultiPlanSubj(subjList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMultiPlan\"}";
		}
	}

	/**
	 * @Description 多栏方案<BR>
	 *              批量更新AccMultiPlan
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccMultiPlan(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			accMultiPlanMapper.updateBatchAccMultiPlan(entityList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMultiPlan\"}";
		}
	}

	@Override
	public String queryAccMultiPlanTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder jsonResult = new StringBuilder();

		jsonResult.append("{Rows:[");

		jsonResult.append("{'id':'0','name':'多栏方案'},");

		List<AccMultiPlan> accMultiPlanList = accMultiPlanMapper.queryAccMultiPlan(entityMap);

		for (int i = 0; i < accMultiPlanList.size(); i++) {

			AccMultiPlan accMultiPlan = accMultiPlanList.get(i);

			if ((i + 1) == accMultiPlanList.size()) {

				jsonResult.append("{'id':'" + accMultiPlan.getPlan_code() + "','pId':'0','name':'"
						+ accMultiPlan.getPlan_code() + " " + accMultiPlan.getPlan_name() + "'}");

			} else {

				jsonResult.append("{'id':'" + accMultiPlan.getPlan_code() + "','pId':'0','name':'"
						+ accMultiPlan.getPlan_code() + " " + accMultiPlan.getPlan_name() + "'},");

			}

		}

		jsonResult.append("]}");

		return jsonResult.toString();
	}

	@Override
	public String queryAccSubjList(Map<String, Object> entityMap) throws DataAccessException {

		return JSON.toJSONString(accMultiPlanMapper.queryAccSubjList(entityMap));
	}

	public String getAccMultiPlanDate(Map<String, Object> entityMap) {
		Map<String, Object> mapVo = accMultiPlanMapper.queryByCode(entityMap);
		List<Map<String, Object>> ListVo = accMultiPlanSubjMapper.queryByCodeMap(mapVo);
		StringBuffer debitList = new StringBuffer();
		StringBuffer creditList = new StringBuffer();
		for (Map<String, Object> map : ListVo) {
			if (Integer.parseInt(map.get("subj_dire").toString()) == 1) {
				creditList.append(map.get("subj_code") + ",");
			} else {
				debitList.append(map.get("subj_code") + ",");
			}

		}
		mapVo.put("debitList", debitList);
		mapVo.put("creditList", creditList);
		return ChdJson.toJson(mapVo);
	}

}
