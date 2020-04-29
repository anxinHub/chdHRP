/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmHosKpiValueCalculateMapper;
import com.chd.hrp.prm.dao.PrmHosSchemeMapper;
import com.chd.hrp.prm.entity.PrmHosKpiValue;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmHosKpiValueCalculateService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0208 医院KPI指标数据准备表
 * @Table: PRM_HOS_KPI_VALUE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmHosKpiValueCalculateService")
public class PrmHosKpiValueCalculateServiceImpl implements PrmHosKpiValueCalculateService {

	private static Logger logger = Logger.getLogger(PrmHosKpiValueCalculateServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmHosKpiValueCalculateMapper")
	private final PrmHosKpiValueCalculateMapper prmHosKpiValueCalculateMapper = null;

	// 引入DAO操作
	@Resource(name = "prmHosSchemeMapper")
	private final PrmHosSchemeMapper prmHosSchemeMapper = null;

	/**
	 * @Description 添加0208 医院KPI指标数据准备表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmHosKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0208 医院KPI指标数据准备表
		PrmHosKpiValue prmHosKpiValue = queryPrmHosKpiValueCalculateByCode(entityMap);

		if (prmHosKpiValue != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmHosKpiValueCalculateMapper.addPrmHosKpiValueCalculate(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpiValue\"}";

		}

	}

	@Override
	public String queryPrmHosKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmHosKpiValue> list = prmHosKpiValueCalculateMapper.queryPrmHosKpiValueCalculate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosKpiValue> list = prmHosKpiValueCalculateMapper.queryPrmHosKpiValueCalculate(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0208 医院KPI指标数据准备表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmHosKpiValue queryPrmHosKpiValueCalculateByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmHosKpiValueCalculateMapper.queryPrmHosKpiValueCalculateByCode(entityMap);
	}

	@Override
	public String queryPrmHosKpiValueSchemeCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmHosKpiValue> list = prmHosKpiValueCalculateMapper.queryPrmHosKpiValueSchemeCalculate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosKpiValue> list = prmHosKpiValueCalculateMapper.queryPrmHosKpiValueSchemeCalculate(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String createPrmHosTargetDataCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		int state = 0;

		entityMap.put("sql", "method_code in ('02','03')");

		prmHosKpiValueCalculateMapper.cleanPrmHosKpiValueCalculate(entityMap);

		List<PrmHosScheme> PrmHosScheme = prmHosSchemeMapper.queryPrmHosScheme(entityMap);

		Map<String, List<PrmHosScheme>> PrmHosSchemetMapVo = new HashMap<String, List<PrmHosScheme>>();

		PrmHosSchemetMapVo.put("PrmHosScheme", PrmHosScheme);

		for (Iterator<String> iterator = PrmHosSchemetMapVo.keySet().iterator(); iterator.hasNext();) {

			String key = iterator.next();

			List<PrmHosScheme> l = PrmHosSchemetMapVo.get(key);

			for (int i = 0; i < l.size(); i++) {

				entityMap.put("goal_code", l.get(i).getGoal_code());
				entityMap.put("kpi_code", l.get(i).getKpi_code());
				entityMap.put("check_hos_id", l.get(i).getCheck_hos_id());
				entityMap.put("kpi_value", 0);
				entityMap.put("is_audit", 0);
				entityMap.put("user_code", "");
				entityMap.put("audit_date", "");
				state = prmHosKpiValueCalculateMapper.addPrmHosKpiValueCalculate(entityMap);

			}

		}

		if (state > 0) {
			return "{\"msg\":\"KPI指标数据生成成功.\",\"state\":\"true\"}";
		} else {
			return "{\"error\":\"KPI指标数据生成失败\"}";

		}

	}

	@Override
	public String auditPrmHosKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int state = prmHosKpiValueCalculateMapper.auditPrmHosKpiValueCalculate(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"审核失败 数据库异常 请联系管理员!\"}";

		}
	}

	@Override
	public String reauditPrmHosKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int state = prmHosKpiValueCalculateMapper.auditPrmHosKpiValueCalculate(entityMap);

			return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"取消审核失败 数据库异常 请联系管理员!\"}";

		}
	}

	@Override
	public String updateBatchPrmHosKpiValueCalculate(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		try {

			prmHosKpiValueCalculateMapper.updateBatchPrmHosKpiValueCalculate(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiValue\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryPrmHosKpiValueSchemeCalculatePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmHosKpiValue> list = prmHosKpiValueCalculateMapper.queryPrmHosKpiValueSchemeCalculate(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}

}
