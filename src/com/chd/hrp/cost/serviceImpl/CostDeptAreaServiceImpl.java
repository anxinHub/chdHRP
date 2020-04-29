/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.cost.dao.CostDeptAreaMapper;
import com.chd.hrp.cost.entity.CostClinicWork;
import com.chd.hrp.cost.entity.CostDeptArea;
import com.chd.hrp.cost.service.CostDeptAreaService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室面积表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costDeptAreaService")
public class CostDeptAreaServiceImpl implements CostDeptAreaService {

	private static Logger logger = Logger
			.getLogger(CostDeptAreaServiceImpl.class);

	@Resource(name = "costDeptAreaMapper")
	private final CostDeptAreaMapper costDeptAreaMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	/**
	 * @Description 科室面积表<BR>
	 *              添加CostDeptArea
	 * @param CostDeptArea
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostDeptArea(Map<String, Object> entityMap)
			throws DataAccessException {

		CostDeptArea costDeptArea = queryCostDeptAreaByCode(entityMap);

		if (costDeptArea != null) {

			// return "{\"error\":\"编码：" + entityMap.get("item_code").toString()
			// + "重复.\"}";
			return "{\"error\":\"" + costDeptArea.getAcc_year()
					+ costDeptArea.getAcc_month() + ","
					+ costDeptArea.getDept_name() + "重复添加.\"}";

		}

		try {

			costDeptAreaMapper.addCostDeptArea(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDeptArea\"}";

		}

	}

	/**
	 * @Description 科室面积表<BR>
	 *              批量添加CostDeptArea
	 * @param CostDeptArea
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostDeptArea(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try {

			costDeptAreaMapper.addBatchCostDeptArea(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDeptArea\"}";

		}
	}

	/**
	 * @Description 科室面积表<BR>
	 *              查询CostDeptArea分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostDeptArea(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<CostDeptArea> list = costDeptAreaMapper
					.queryCostDeptArea(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());
			List<CostDeptArea> list = costDeptAreaMapper.queryCostDeptArea(
					entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}

	@Override
	public List<Map<String, Object>> queryCostDeptAreaPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = costDeptAreaMapper
				.queryCostDeptAreaPrint(entityMap);

		return list;

	}

	/**
	 * @Description 科室面积表<BR>
	 *              查询CostDeptAreaByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostDeptArea queryCostDeptAreaByCode(Map<String, Object> entityMap)
			throws DataAccessException {

		return costDeptAreaMapper.queryCostDeptAreaByCode(entityMap);

	}

	/**
	 * @Description 科室面积表<BR>
	 *              批量删除CostDeptArea
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostDeptArea(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try {

			int state = costDeptAreaMapper.deleteBatchCostDeptArea(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDeptArea\"}";

		}

	}

	/**
	 * @Description 科室面积表<BR>
	 *              删除CostDeptArea
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostDeptArea(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			costDeptAreaMapper.deleteCostDeptArea(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDeptArea\"}";

		}
	}

	@Override
	public String deleteMonthlyCostDeptArea(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			costDeptAreaMapper.deleteMonthlyCostDeptArea(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			// TODO: handle exception
            logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	/**
	 * @Description 科室面积表<BR>
	 *              更新CostDeptArea
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostDeptArea(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			costDeptAreaMapper.updateCostDeptArea(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptArea\"}";

		}
	}

	/**
	 * @Description 科室面积表<BR>
	 *              批量更新CostDeptArea
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostDeptArea(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try {

			costDeptAreaMapper.updateBatchCostDeptArea(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptArea\"}";

		}

	}

	

	@Override
	public String extendCostDeptArea(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String b_year_month = entityMap.get("end_year").toString() + entityMap.get("end_month").toString();
			
			String e_year_month = entityMap.get("end_year").toString() + entityMap.get("end_month").toString();
			
			entityMap.put("b_year_month", b_year_month);
			
			entityMap.put("e_year_month", e_year_month);
			
			costDeptAreaMapper.deleteMonthlyCostDeptArea(entityMap);

			costDeptAreaMapper.extendCostDeptArea(entityMap);

			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  extendCostDeptArea\"}";

		}
	}
	
	
	/**
	 * 导入
	 * 
	 * @param mapVo
	 * @return
	 */
	public String readAssFinaDictFiles(Map<String, Object> map) {
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(map);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map2 : list) {
				if (map2.get("acc_year").get(1) == null
						|| map2.get("acc_year").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("acc_year").get(0)
							+ "，统计年度为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("acc_year").get(0) + "\"}";
				} else if (map2.get("acc_month").get(1) == null
						|| map2.get("acc_month").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("acc_month").get(0)
							+ "，统计月份为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("acc_month").get(0) + "\"}";
				} else if (map2.get("dept_id").get(1) == null
						|| map2.get("dept_id").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("dept_id").get(0)
							+ "，科室编码为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("dept_id").get(0) + "\"}";
				} else if (map2.get("arear").get(1) == null
						|| map2.get("arear").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("arear").get(0)
							+ "，面积为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("arear").get(0) + "\"}";
				}
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());

				mapVo1.put("acc_year", map2.get("acc_year").get(1));

				mapVo1.put(
						"acc_month",
						(map2.get("acc_month").get(1).length() == 1) ? ('0' + map2
								.get("acc_month").get(1).toString())
								: map2.get("acc_month").get(1).toString());

				mapVo1.put("dept_id", map2.get("dept_id").get(1));

				mapVo1.put("arear", map2.get("arear").get(1));

				// 判断执行科室是否存在
				Map<String, Object> mapVo_dept = new HashMap<String, Object>();
				mapVo_dept.put("group_id", mapVo1.get("group_id"));
				mapVo_dept.put("hos_id", mapVo1.get("hos_id"));
				mapVo_dept.put("copy_code", mapVo1.get("copy_code"));
				mapVo_dept.put("dept_code", mapVo1.get("dept_id"));

				DeptDict deptDict1 = deptDictMapper
						.queryDeptDictByCodeDept(mapVo_dept);

				if (deptDict1 != null) {

					mapVo1.put("dept_no", deptDict1.getDept_no());

					mapVo1.put("dept_id", deptDict1.getDept_id());

				} else {
					throw new SysException(map2.get("dept_id").get(0)
							+ ",科室不存在！");

				}
				costDeptAreaMapper.addCostDeptArea(mapVo1);

			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}
	}

}
