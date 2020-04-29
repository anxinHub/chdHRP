
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmEmpKpiValueCalculateMapper;
import com.chd.hrp.prm.dao.PrmEmpSchemeMapper;
import com.chd.hrp.prm.entity.PrmEmpKpiValueCalculate;
import com.chd.hrp.prm.entity.PrmEmpScheme;
import com.chd.hrp.prm.service.PrmEmpKpiValueCalculateService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0308 职工KPI指标数据准备表
 * @Table: PRM_EMP_KPI_VALUE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmEmpKpiValueCalculateService")
public class PrmEmpKpiValueCalculateServiceImpl implements PrmEmpKpiValueCalculateService {

	private static Logger logger = Logger.getLogger(PrmEmpKpiValueCalculateServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmEmpKpiValueCalculateMapper")
	private final PrmEmpKpiValueCalculateMapper prmEmpKpiValueCalculateMapper = null;

	// 引入DAO操作
	@Resource(name = "prmEmpSchemeMapper")
	private final PrmEmpSchemeMapper prmEmpSchemeMapper = null;

	/**
	 * @Description 添加0308 职工KPI指标数据准备表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmEmpKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0308 职工KPI指标数据准备表
		PrmEmpKpiValueCalculate prmEmpKpiValueCalculate = queryPrmEmpKpiValueCalculateByCode(entityMap);

		if (prmEmpKpiValueCalculate != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmEmpKpiValueCalculateMapper.addPrmEmpKpiValueCalculate(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiValue\"}";

		}

	}

	/**
	 * @Description 查询结果集0308 职工KPI指标数据准备表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmEmpKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmEmpKpiValueCalculate> list = prmEmpKpiValueCalculateMapper.queryPrmEmpKpiValueCalculate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpKpiValueCalculate> list = prmEmpKpiValueCalculateMapper.queryPrmEmpKpiValueCalculate(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0308 职工KPI指标数据准备表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmEmpKpiValueCalculate queryPrmEmpKpiValueCalculateByCode(Map<String, Object> entityMap)
			throws DataAccessException {

		return prmEmpKpiValueCalculateMapper.queryPrmEmpKpiValueCalculateByCode(entityMap);

	}

	@Override
	public String queryPrmEmpKpiValueSchemeCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmEmpKpiValueCalculate> list = prmEmpKpiValueCalculateMapper
					.queryPrmEmpKpiValueSchemeCalculate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpKpiValueCalculate> list = prmEmpKpiValueCalculateMapper
					.queryPrmEmpKpiValueSchemeCalculate(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String createPrmEmpTargetDataCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		int state = 0;

		entityMap.put("sql", "method_code in ('02','03')");

		List<PrmEmpScheme> PrmEmpScheme = prmEmpSchemeMapper.queryPrmEmpScheme(entityMap);

		prmEmpKpiValueCalculateMapper.cleanPrmEmpKpiValueCalculate(entityMap);

		Map<String, List<PrmEmpScheme>> PrmEmpSchemeMapVo = new HashMap<String, List<PrmEmpScheme>>();

		PrmEmpSchemeMapVo.put("PrmEmpScheme", PrmEmpScheme);

		for (Iterator<String> iterator = PrmEmpSchemeMapVo.keySet().iterator(); iterator.hasNext();) {

			String key = iterator.next();

			List<PrmEmpScheme> l = PrmEmpSchemeMapVo.get(key);

			for (int i = 0; i < l.size(); i++) {
				entityMap.put("group_id", l.get(i).getGroup_id());
				entityMap.put("hos_id", l.get(i).getHos_id());
				entityMap.put("copy_code", l.get(i).getCopy_code());
				entityMap.put("acc_year", l.get(i).getAcc_year());
				entityMap.put("acc_month", l.get(i).getAcc_month());
				entityMap.put("goal_code", l.get(i).getGoal_code());
				entityMap.put("kpi_code", l.get(i).getKpi_code());
				entityMap.put("emp_no", l.get(i).getEmp_no());
				entityMap.put("emp_id", l.get(i).getEmp_id());
				entityMap.put("kpi_value", "0");
				entityMap.put("is_audit", "0");
				state = prmEmpKpiValueCalculateMapper.addPrmEmpKpiValueCalculate(entityMap);

			}

		}

		if (state > 0) {
			return "{\"msg\":\"KPI指标数据生成成功.\",\"state\":\"true\"}";
		} else {
			return "{\"error\":\"KPI指标数据生成失败\"}";

		}
	}

	@Override
	public String auditPrmEmpKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int state = prmEmpKpiValueCalculateMapper.auditPrmEmpKpiValueCalculate(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"审核失败 数据库异常 请联系管理员!\"}";

		}
	}

	@Override
	public String reAuditPrmEmpKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int state = prmEmpKpiValueCalculateMapper.auditPrmEmpKpiValueCalculate(entityMap);

			return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"取消审核失败 数据库异常 请联系管理员!\"}";

		}
	}

	@Override
	public String updateBatchPrmEmpKpiValueCalculate(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			prmEmpKpiValueCalculateMapper.updateBatchPrmEmpKpiValueCalculate(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpiValueCalculate\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryPrmEmpKpiValueSchemeCalculatePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmEmpKpiValueCalculate> list = prmEmpKpiValueCalculateMapper.queryPrmEmpKpiValueSchemeCalculate(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}

}
