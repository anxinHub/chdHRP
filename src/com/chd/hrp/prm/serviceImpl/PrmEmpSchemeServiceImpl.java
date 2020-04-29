
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmEmpFunStackMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiAdMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiLedMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiObjMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiSectionMapper;
import com.chd.hrp.prm.dao.PrmEmpSchemeMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmEmpFunStack;
import com.chd.hrp.prm.entity.PrmEmpKpi;
import com.chd.hrp.prm.entity.PrmEmpKpiAd;
import com.chd.hrp.prm.entity.PrmEmpKpiLed;
import com.chd.hrp.prm.entity.PrmEmpKpiObj;
import com.chd.hrp.prm.entity.PrmEmpKpiSection;
import com.chd.hrp.prm.entity.PrmEmpScheme;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.service.PrmEmpSchemeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0403 职工绩效方案表
 * @Table: PRM_EMP_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmEmpSchemeService")
public class PrmEmpSchemeServiceImpl implements PrmEmpSchemeService {

	private static Logger logger = Logger.getLogger(PrmEmpSchemeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmEmpSchemeMapper")
	private final PrmEmpSchemeMapper prmEmpSchemeMapper = null;

	@Resource(name = "prmEmpKpiMapper")
	private final PrmEmpKpiMapper prmEmpKpiMapper = null;

	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;

	// 引入DAO操作
	@Resource(name = "prmEmpKpiObjMapper")
	private final PrmEmpKpiObjMapper prmEmpKpiObjMapper = null;

	// 引入DAO操作
	@Resource(name = "prmEmpKpiSectionMapper")
	private final PrmEmpKpiSectionMapper prmEmpKpiSectionMapper = null;

	// 引入DAO操作
	@Resource(name = "prmEmpKpiAdMapper")
	private final PrmEmpKpiAdMapper prmEmpKpiAdMapper = null;

	@Resource(name = "prmEmpKpiLedMapper")
	private final PrmEmpKpiLedMapper prmEmpKpiLedMapper = null;
	
	@Resource(name = "prmEmpFunStackMapper")
	private final PrmEmpFunStackMapper prmEmpFunStackMapper = null;

	/**
	 * @Description 添加0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmEmpScheme(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0303 科室绩效方案表
		PrmEmpScheme prmEmpScheme = queryPrmEmpSchemeByCode(entityMap);

		if (prmEmpScheme != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmEmpSchemeMapper.addPrmEmpScheme(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpScheme\"}";

		}

	}

	/**
	 * @Description 批量添加0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmEmpSchemeMapper.addBatchPrmEmpScheme(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpScheme\"}";

		}

	}

	/**
	 * @Description 更新0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmEmpScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmEmpSchemeMapper.updatePrmEmpScheme(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpScheme\"}";

		}

	}

	/**
	 * @Description 批量更新0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmEmpSchemeMapper.updateBatchPrmEmpScheme(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpScheme\"}";

		}

	}

	/**
	 * @Description 删除0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmEmpScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmEmpSchemeMapper.deletePrmEmpScheme(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpScheme\"}";

		}

	}

	/**
	 * @Description 批量删除0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmEmpSchemeMapper.deleteBatchPrmEmpScheme(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpScheme\"}";

		}
	}

	/**
	 * @Description 查询结果集0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmEmpScheme(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmEmpScheme> list = prmEmpSchemeMapper.queryPrmEmpScheme(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpScheme> list = prmEmpSchemeMapper.queryPrmEmpScheme(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmEmpScheme queryPrmEmpSchemeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmEmpSchemeMapper.queryPrmEmpSchemeByCode(entityMap);
	}

	@Override
	public PrmEmpScheme queryPrmEmpSchemeMethodByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return prmEmpSchemeMapper.queryPrmEmpSchemeMethodByCode(entityMap);
	}

	@Override
	public String queryPrmEmpSchemeLeftName(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmEmpScheme> list = prmEmpSchemeMapper.queryPrmEmpSchemeLeftName(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpScheme> list = prmEmpSchemeMapper.queryPrmEmpSchemeLeftName(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String createPrmEmpScheme(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		try {

			String rbtnl = (String) entityMap.get("rbtnl");

			List<Map<String, Object>> EmpSchemeList = new ArrayList<Map<String, Object>>();

			List<PrmEmpKpiObj> EmpKpiObjList = prmEmpKpiObjMapper.queryPrmEmpKpiObj(entityMap);

			if ("all".equals(rbtnl)) {// 覆盖生成

				for (PrmEmpKpiObj p : EmpKpiObjList) {

					Map<String, Object> map = getMap(p, entityMap);

					EmpSchemeList.add(map);

				}

				if (EmpSchemeList.size() > 0) {
					prmEmpKpiSectionMapper.deleteBatchPrmEmpKpiSection(EmpSchemeList);

					prmEmpKpiAdMapper.deleteBatchPrmEmpKpiAd(EmpSchemeList);

					prmEmpKpiLedMapper.deleteBatchPrmEmpKpiLed(EmpSchemeList);
					
					prmEmpFunStackMapper.deleteBatchPrmEmpFunStack(EmpSchemeList);

					prmEmpSchemeMapper.deleteBatchPrmEmpScheme(EmpSchemeList);
				}

			} else {

				Map<String, String> prmEmpchemeMap = new HashMap<String, String>();

				List<PrmEmpScheme> prmEmpSchemelist = prmEmpSchemeMapper.queryPrmEmpScheme(entityMap);

				for (PrmEmpScheme prmEmpSchemelist2 : prmEmpSchemelist) {

					prmEmpchemeMap.put(
							prmEmpSchemelist2.getKpi_code() + prmEmpSchemelist2.getEmp_no()
									+ prmEmpSchemelist2.getEmp_id(),
							prmEmpSchemelist2.getKpi_code() + prmEmpSchemelist2.getEmp_no()
									+ prmEmpSchemelist2.getEmp_id());

				}

				for (int i = 0; i < EmpKpiObjList.size(); i++) {

					PrmEmpKpiObj EmpSchemeList2 = EmpKpiObjList.get(i);

					if (prmEmpchemeMap.get(EmpSchemeList2.getKpi_code() + EmpSchemeList2.getEmp_no()
							+ EmpSchemeList2.getEmp_id()) == null) {

						Map<String, Object> addMap = new HashMap<String, Object>();

						addMap.put("kpi_code", EmpSchemeList2.getKpi_code());

						addMap.put("emp_no", EmpSchemeList2.getEmp_no());

						addMap.put("emp_id", EmpSchemeList2.getEmp_id());

						addMap.put("group_id", EmpSchemeList2.getGroup_id());

						addMap.put("hos_id", EmpSchemeList2.getHos_id());

						addMap.put("copy_code", EmpSchemeList2.getCopy_code());

						addMap.put("acc_year", EmpSchemeList2.getAcc_year());

						addMap.put("acc_month", entityMap.get("acc_month"));

						addMap.put("goal_code", EmpSchemeList2.getGoal_code());

						addMap.put("super_kpi_code", EmpSchemeList2.getSuper_kpi_code());

						addMap.put("kpi_level", EmpSchemeList2.getKpi_level());

						addMap.put("order_no", EmpSchemeList2.getOrder_no());

						addMap.put("is_last", EmpSchemeList2.getIs_last());

						addMap.put("ratio", "0");

						addMap.put("goal_value", "0");

						addMap.put("grade_meth_code", "");

						addMap.put("method_code", "");

						addMap.put("formula_code", "");

						addMap.put("fun_code", "");

						addMap.put("full_score", "0");

						EmpSchemeList.add(addMap);

					}

				}
			}

			if (EmpSchemeList.size() > 0) {

				prmEmpSchemeMapper.addBatchPrmEmpScheme(EmpSchemeList);

				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

			} else {

				return "{\"error\":\"没有数据生成.\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosScheme\"}";

		}
	}

	public Map<String, Object> getMap(PrmEmpKpiObj p, Map<String, Object> mapVo) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", p.getGroup_id());

		map.put("hos_id", p.getHos_id());

		map.put("copy_code", p.getCopy_code());

		map.put("acc_year", p.getAcc_year());

		map.put("acc_month", mapVo.get("acc_month"));

		map.put("goal_code", p.getGoal_code());

		map.put("kpi_code", p.getKpi_code());

		map.put("emp_no", p.getEmp_no());

		map.put("emp_id", p.getEmp_id());

		map.put("super_kpi_code", p.getSuper_kpi_code());

		map.put("kpi_level", p.getKpi_level());

		map.put("order_no", p.getOrder_no());

		map.put("is_last", p.getIs_last());

		map.put("ratio", "0");

		map.put("goal_value", "0");

		map.put("grade_meth_code", "");

		map.put("method_code", "");

		map.put("formula_code", "");

		map.put("fun_code", "");

		map.put("full_score", "0");

		return map;

	}

	@Override
	public String saveIntroduceEmpScheme(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		List<Map<String, Object>> hosEmpKpiList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> hosEmpKpiObjList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> hosSchemeList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> hosSection = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> prmHosKpiAdList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> prmHosKpiLedList = new ArrayList<Map<String, Object>>();// 指示灯
		
		List<Map<String, Object>> prmHosFunStackList = new ArrayList<Map<String, Object>>();//函数站

		Map<String, Object> mapScheme = new HashMap<String, Object>();

		mapScheme.put("group_id", SessionManager.getGroupId());

		mapScheme.put("hos_id", entityMap.get("hos_id"));

		mapScheme.put("copy_code", SessionManager.getCopyCode());

		mapScheme.put("acc_year", entityMap.get("acc_year"));

		mapScheme.put("acc_month", entityMap.get("acc_month"));

		mapScheme.put("goal_code", entityMap.get("goal_code"));

		mapScheme.put("emp_no", String.valueOf(entityMap.get("emp_no")));

		mapScheme.put("emp_id", String.valueOf(entityMap.get("emp_id")));

		Map<String, Object> isMapScheme = new HashMap<String, Object>();

		isMapScheme.put("group_id", SessionManager.getGroupId());

		isMapScheme.put("hos_id", entityMap.get("kpiHos_id"));

		isMapScheme.put("copy_code", SessionManager.getCopyCode());

		isMapScheme.put("acc_year", entityMap.get("kpiAcc_year"));

		isMapScheme.put("acc_month", entityMap.get("kpiAcc_month"));

		isMapScheme.put("goal_code", entityMap.get("kpiGoal_code"));

		isMapScheme.put("emp_no", String.valueOf(entityMap.get("kpiEmp_no")));

		isMapScheme.put("emp_id", String.valueOf(entityMap.get("kpiEmp_id")));

		String kpi_code = String.valueOf(entityMap.get("kpi_code"));

		String ratio = String.valueOf(entityMap.get("ratio"));

		String goal_value = String.valueOf(entityMap.get("goal_value"));

		String grade_meth_code = String.valueOf(entityMap.get("grade_meth_code"));

		String method_code = String.valueOf(entityMap.get("method_code"));

		String emp_led = String.valueOf(entityMap.get("emp_led"));

		String emp_full = String.valueOf(entityMap.get("emp_full"));

		List<PrmEmpScheme> prmEmpScheme = prmEmpSchemeMapper.queryPrmEmpSchemeLeftName(mapScheme);// 要引入的年月的数据

		List<PrmEmpKpiObj> prmEmpKpiObjList = prmEmpKpiObjMapper.queryPrmEmpKpiObj(mapScheme);

		List<PrmEmpKpi> prmEmpKpilist = prmEmpKpiMapper.queryPrmEmpKpi(mapScheme);

		/* 判断当前年度和战略是否有数据 */
		List<PrmEmpKpiObj> isPrmEmpKpiObjList = prmEmpKpiObjMapper.queryPrmEmpKpiObj(isMapScheme);

		List<PrmEmpKpi> isPrmEmpKpilist = prmEmpKpiMapper.queryPrmEmpKpi(isMapScheme);

		/* 第一种情况选择指标体系时INSERT */

		if (prmEmpScheme.size() > 0) {
			if (isPrmEmpKpilist.size() == 0) {
				for (PrmEmpKpi prmEmpKpi : prmEmpKpilist) {
					Map<String, Object> kpiMapVo = new HashMap<String, Object>();
					kpiMapVo.put("group_id", prmEmpKpi.getGroup_id());

					kpiMapVo.put("hos_id", Long.parseLong(entityMap.get("kpiHos_id").toString()));

					kpiMapVo.put("copy_code", prmEmpKpi.getCopy_code());

					kpiMapVo.put("acc_year", entityMap.get("kpiAcc_year"));

					kpiMapVo.put("goal_code", entityMap.get("kpiGoal_code"));

					kpiMapVo.put("kpi_code", prmEmpKpi.getKpi_code());

					kpiMapVo.put("kpi_name", prmEmpKpi.getKpi_name());

					kpiMapVo.put("dept_id", prmEmpKpi.getDept_id());

					kpiMapVo.put("dept_no", prmEmpKpi.getDept_no());

					kpiMapVo.put("dim_code", prmEmpKpi.getDim_code());

					kpiMapVo.put("order_no", prmEmpKpi.getOrder_no());

					kpiMapVo.put("kpi_level", prmEmpKpi.getKpi_level());

					kpiMapVo.put("nature_code", prmEmpKpi.getNature_code());

					kpiMapVo.put("super_kpi_code", prmEmpKpi.getSuper_kpi_code());

					kpiMapVo.put("spell_code", prmEmpKpi.getSpell_code());

					kpiMapVo.put("wbx_code", prmEmpKpi.getWbx_code());

					kpiMapVo.put("is_last", prmEmpKpi.getIs_last());

					hosEmpKpiList.add(kpiMapVo);
				}
			}

			if(isPrmEmpKpiObjList.size() == 0){
				for (PrmEmpKpiObj prmEmpKpiObj : prmEmpKpiObjList) {
					Map<String, Object> kpiObjMapVo = new HashMap<String, Object>();
					kpiObjMapVo.put("group_id", prmEmpKpiObj.getGroup_id());

					kpiObjMapVo.put("hos_id", entityMap.get("kpiHos_id"));

					kpiObjMapVo.put("copy_code", prmEmpKpiObj.getCopy_code());

					kpiObjMapVo.put("acc_year", entityMap.get("kpiAcc_year"));

					kpiObjMapVo.put("goal_code", entityMap.get("kpiGoal_code"));

					kpiObjMapVo.put("emp_no", String.valueOf(entityMap.get("kpiEmp_no")));

					kpiObjMapVo.put("emp_id", String.valueOf(entityMap.get("kpiEmp_id")));

					kpiObjMapVo.put("kpi_code", prmEmpKpiObj.getKpi_code());

					kpiObjMapVo.put("super_kpi_code", prmEmpKpiObj.getSuper_kpi_code());

					kpiObjMapVo.put("order_no", prmEmpKpiObj.getOrder_no() == 0 ? "0" : prmEmpKpiObj.getOrder_no());

					kpiObjMapVo.put("kpi_level", prmEmpKpiObj.getKpi_level() == 0 ? "0" : prmEmpKpiObj.getKpi_level());

					kpiObjMapVo.put("is_last", prmEmpKpiObj.getIs_last());

					hosEmpKpiObjList.add(kpiObjMapVo);

				}
			}
			

			for (PrmEmpScheme PrmEmpScheme2 : prmEmpScheme) {

				Map<String, Object> addMapVo = new HashMap<String, Object>();

				addMapVo.put("group_id", PrmEmpScheme2.getGroup_id());

				addMapVo.put("hos_id", entityMap.get("kpiHos_id"));

				addMapVo.put("copy_code", PrmEmpScheme2.getCopy_code());

				addMapVo.put("acc_year", entityMap.get("kpiAcc_year"));

				addMapVo.put("acc_month", entityMap.get("kpiAcc_month"));

				addMapVo.put("goal_code", entityMap.get("kpiGoal_code"));

				addMapVo.put("emp_no", String.valueOf(entityMap.get("kpiEmp_no")));

				addMapVo.put("emp_id", String.valueOf(entityMap.get("kpiEmp_id")));

				addMapVo.put("kpi_code", PrmEmpScheme2.getKpi_code());

				addMapVo.put("super_kpi_code", PrmEmpScheme2.getSuper_kpi_code());

				addMapVo.put("order_no", PrmEmpScheme2.getOrder_no() == 0 ? "0" : PrmEmpScheme2.getOrder_no());

				addMapVo.put("kpi_level", PrmEmpScheme2.getKpi_level() == 0 ? "0" : PrmEmpScheme2.getKpi_level());

				addMapVo.put("is_last", PrmEmpScheme2.getIs_last());

				// 权重
				if ("true".equals(ratio)) {

					addMapVo.put("ratio", String.valueOf(PrmEmpScheme2.getRatio()));

				} else {

					addMapVo.put("ratio", "0");
				}
				// 目标值
				if ("true".equals(goal_value)) {

					addMapVo.put("goal_value", PrmEmpScheme2.getGoal_value());

				} else {

					addMapVo.put("goal_value", "0");
				}

				// 评分方法
				if (("true").equals(grade_meth_code)) {

					if (PrmEmpScheme2.getGrade_meth_code() != null) {

						Map<String, Object> queryGradeMapVo = new HashMap<String, Object>();
						
						queryGradeMapVo = addMapVo;

						queryGradeMapVo.put("grade_meth_code", PrmEmpScheme2.getGrade_meth_code());
						queryGradeMapVo.put("acc_month", mapScheme.get("acc_month"));

						List<PrmEmpKpiSection> prmEmpKpiSection = prmEmpKpiSectionMapper
								.queryPrmEmpKpiSection(queryGradeMapVo);

						for (PrmEmpKpiSection PrmEmpKpiSection2 : prmEmpKpiSection) {

							Map<String, Object> addMapVoSection = new HashMap<String, Object>();

							addMapVoSection.put("group_id", PrmEmpKpiSection2.getGroup_id());

							addMapVoSection.put("hos_id", entityMap.get("kpiHos_id"));

							addMapVoSection.put("copy_code", PrmEmpKpiSection2.getCopy_code());

							addMapVoSection.put("acc_year", entityMap.get("kpiAcc_year"));

							addMapVoSection.put("acc_month", entityMap.get("kpiAcc_month"));

							addMapVoSection.put("goal_code", entityMap.get("kpiGoal_code"));

							addMapVoSection.put("emp_no", String.valueOf(entityMap.get("kpiEmp_no")));

							addMapVoSection.put("emp_id", String.valueOf(entityMap.get("kpiEmp_id")));

							addMapVoSection.put("section", PrmEmpKpiSection2.getSection());

							addMapVoSection.put("kpi_code", PrmEmpKpiSection2.getKpi_code());

							addMapVoSection.put("kpi_beg_value", PrmEmpKpiSection2.getKpi_beg_value());

							addMapVoSection.put("kpi_end_value", PrmEmpKpiSection2.getKpi_end_value());

							addMapVoSection.put("kpi_beg_score", PrmEmpKpiSection2.getKpi_beg_score());

							addMapVoSection.put("kpi_end_score", PrmEmpKpiSection2.getKpi_end_score());

							hosSection.add(addMapVoSection);

						}
						List<PrmEmpKpiAd> prmEmpKpiAd = prmEmpKpiAdMapper.queryPrmEmpKpiAd(queryGradeMapVo);

						for (PrmEmpKpiAd prmEmpKpiAd2 : prmEmpKpiAd) {

							Map<String, Object> addHosKpiAdMap = new HashMap<String, Object>();

							addHosKpiAdMap.put("group_id", prmEmpKpiAd2.getGroup_id());

							addHosKpiAdMap.put("hos_id", entityMap.get("kpiHos_id"));

							addHosKpiAdMap.put("copy_code", prmEmpKpiAd2.getCopy_code());

							addHosKpiAdMap.put("acc_year", entityMap.get("kpiAcc_year"));

							addHosKpiAdMap.put("acc_month", entityMap.get("kpiAcc_month"));

							addHosKpiAdMap.put("goal_code", entityMap.get("kpiGoal_code"));

							addHosKpiAdMap.put("kpi_code", prmEmpKpiAd2.getKpi_code());

							addHosKpiAdMap.put("emp_no", String.valueOf(entityMap.get("kpiEmp_no")));

							addHosKpiAdMap.put("emp_id", String.valueOf(entityMap.get("kpiEmp_id")));

							addHosKpiAdMap.put("kpi_range_value", prmEmpKpiAd2.getKpi_range_value() == null ? 0.0
									: prmEmpKpiAd2.getKpi_range_value());

							addHosKpiAdMap.put("kpi_range_score", prmEmpKpiAd2.getKpi_range_score() == null ? 0.0
									: prmEmpKpiAd2.getKpi_range_score());

							prmHosKpiAdList.add(addHosKpiAdMap);
						}
						addMapVo.put("grade_meth_code", PrmEmpScheme2.getGrade_meth_code());
					} else {
						addMapVo.put("grade_meth_code", "");
					}

				}

				// 满分标准
				if (("true").equals(emp_full)) {
					addMapVo.put("full_score",
							PrmEmpScheme2.getFull_score() != null ? PrmEmpScheme2.getFull_score() : "0");
				} else {
					addMapVo.put("full_score", "0");
				}

				// 指示灯
				if (("true").equals(emp_led)) {
					Map<String, Object> queryGradeMapVo = new HashMap<String, Object>();
					
					queryGradeMapVo = addMapVo;

					queryGradeMapVo.put("acc_month", mapScheme.get("acc_month"));
					List<PrmEmpKpiLed> prmEmpKpdLedList = prmEmpKpiLedMapper.queryPrmEmpKpiLed(queryGradeMapVo);
					for (PrmEmpKpiLed prmEmpKpiLed : prmEmpKpdLedList) {
						Map<String, Object> addMapVoLed = new HashMap<String, Object>();

						addMapVoLed.put("group_id", prmEmpKpiLed.getGroup_id());

						addMapVoLed.put("hos_id", entityMap.get("kpiHos_id"));

						addMapVoLed.put("copy_code", prmEmpKpiLed.getCopy_code());

						addMapVoLed.put("acc_year", entityMap.get("kpiAcc_year"));

						addMapVoLed.put("acc_month", entityMap.get("kpiAcc_month"));

						addMapVoLed.put("goal_code", entityMap.get("kpiGoal_code"));

						addMapVoLed.put("kpi_code", prmEmpKpiLed.getKpi_code());

						addMapVoLed.put("emp_no", String.valueOf(entityMap.get("kpiEmp_no")));

						addMapVoLed.put("emp_id", String.valueOf(entityMap.get("kpiEmp_id")));

						addMapVoLed.put("sec_code", prmEmpKpiLed.getSec_code());

						addMapVoLed.put("kpi_beg_score",
								prmEmpKpiLed.getKpi_beg_score() == null ? 0.0 : prmEmpKpiLed.getKpi_beg_score());

						addMapVoLed.put("kpi_end_score",
								prmEmpKpiLed.getKpi_end_score() == null ? 0.0 : prmEmpKpiLed.getKpi_end_score());

						prmHosKpiLedList.add(addMapVoLed);
					}

				}

				// 取值方法
				if ("true".equals(method_code)) {
					Map<String, Object> queryGradeMapVo = new HashMap<String, Object>();
					
					queryGradeMapVo = addMapVo;

					queryGradeMapVo.put("acc_month", mapScheme.get("acc_month"));
					List<PrmEmpFunStack> prmEmpFunStackList = prmEmpFunStackMapper.queryPrmEmpFunStack(queryGradeMapVo);
					for(PrmEmpFunStack prmDeptFunStack : prmEmpFunStackList){
						Map<String, Object> addMapVoFunStack = new HashMap<String, Object>();
						
						addMapVoFunStack.put("group_id", prmDeptFunStack.getGroup_id());

						addMapVoFunStack.put("hos_id", entityMap.get("kpiHos_id"));

						addMapVoFunStack.put("copy_code", prmDeptFunStack.getCopy_code());

						addMapVoFunStack.put("acc_year", entityMap.get("kpiAcc_year"));

						addMapVoFunStack.put("acc_month", entityMap.get("kpiAcc_month"));

						addMapVoFunStack.put("goal_code", entityMap.get("kpiGoal_code"));

						addMapVoFunStack.put("kpi_code", prmDeptFunStack.getKpi_code());

						addMapVoFunStack.put("emp_no", String.valueOf(entityMap.get("kpiEmp_no")));

						addMapVoFunStack.put("emp_id", String.valueOf(entityMap.get("kpiEmp_id")));

						addMapVoFunStack.put("fun_para_code", prmDeptFunStack.getFun_para_code());
						
						addMapVoFunStack.put("fun_para_value", prmDeptFunStack.getFun_para_value());

						prmHosFunStackList.add(addMapVoFunStack);
					}

					addMapVo.put("method_code",
							PrmEmpScheme2.getMethod_code() != null ? PrmEmpScheme2.getMethod_code() : "");

					addMapVo.put("formula_code",
							PrmEmpScheme2.getFormula_code() != null ? PrmEmpScheme2.getFormula_code() : "");

					addMapVo.put("fun_code", PrmEmpScheme2.getFun_code() != null ? PrmEmpScheme2.getFun_code() : "");

				} else {

					addMapVo.put("method_code", "");

					addMapVo.put("formula_code", "");

					addMapVo.put("fun_code", "");

				}

				addMapVo.put("acc_month", entityMap.get("kpiAcc_month"));
				hosSchemeList.add(addMapVo);

			}

			if (hosEmpKpiList.size() > 0) {
				prmEmpKpiMapper.deleteBatchPrmEmpKpi(hosEmpKpiList);
				prmEmpKpiMapper.addBatchPrmEmpKpi(hosEmpKpiList);
			}

			if (hosSchemeList.size() > 0) {
				prmEmpKpiSectionMapper.deleteBatchPrmEmpKpiSection(hosSchemeList);
				prmEmpKpiAdMapper.deleteBatchPrmEmpKpiAd(hosSchemeList);
				prmEmpKpiLedMapper.deleteBatchPrmEmpKpiLed(hosSchemeList);
				prmEmpFunStackMapper.deleteBatchPrmEmpFunStack(hosSchemeList);
				prmEmpSchemeMapper.deleteBatchPrmEmpSchemeByImport(hosSchemeList);
				if(hosEmpKpiObjList.size() > 0){
					prmEmpKpiObjMapper.deleteBatchPrmEmpKpiObj(hosEmpKpiObjList);
					prmEmpKpiObjMapper.addBatchPrmEmpKpiObj(hosEmpKpiObjList);
				}
				prmEmpSchemeMapper.addBatchPrmEmpScheme(hosSchemeList);
			}
			if (hosSection.size() > 0) {
				prmEmpKpiSectionMapper.addBatchPrmEmpKpiSection(hosSection);
			}
			if (prmHosKpiAdList.size() > 0) {
				prmEmpKpiAdMapper.addBatchPrmEmpKpiAd(prmHosKpiAdList);
			}
			if (prmHosKpiLedList.size() > 0) {
				prmEmpKpiLedMapper.addBatchPrmEmpKpiLed(prmHosKpiLedList);
			}
			if(prmHosFunStackList.size() > 0){
				prmEmpFunStackMapper.addBatchPrmEmpFunStack(prmHosFunStackList);
			}
			
			return "{\"msg\":\"引入成功.\",\"state\":\"true\"}";
		} else {
			return "{\"error\":\"引入年月没有数据.\"}";
		}

	}

	/**
	 * @Description 查询对象0201 医院绩效考核指标表 查出结果 存储树形结构<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosKpiTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder json = new StringBuilder();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		try {
			entityMap.put("user_id", SessionManager.getUserId());

			entityMap.put("mod_code", SessionManager.getModCode());

			entityMap.put("prem_data", "true");
			List<PrmEmpScheme> list_hos_kpi = prmEmpSchemeMapper.queryPrmEmpScheme(entityMap);

			List<PrmGoal> list_goal = prmGoalMapper.queryPrmGoalByTree(entityMap);

			if (list_hos_kpi.size() == 0 && list_goal.size() == 0) {

				json.append("{Rows:[]}");

				return json.toString();

			}
			json.append("{Rows:[");

			for (PrmGoal goal : list_goal) {
				json.append("{");

				json.append("\"pid\":\"-1\"," + "\"id\":\"" + goal.getGoal_code() + "goal" + "\"," + "\"text\":" + "\""
						+ goal.getGoal_name() + "\"");

				json.append("},");
				Map<String, Object> map = new HashMap<String, Object>();

				for (PrmEmpScheme p : list_hos_kpi) {

					if (p.getGoal_code().equals(goal.getGoal_code())) {

						String key = p.getKpi_code();

						if (map.get(key) == null) {// 去除重复维度

							if (p.getKpi_code() != null) {

								json.append("{");
								String pidStr = "";
								if (p.getSuper_kpi_code().equals("TOP")) {
									pidStr = goal.getGoal_code() + "goal";
								} else {
									pidStr = p.getSuper_kpi_code();
								}
								String ratioStr = "";
								if (p.getRatio() > 0) {
									String ratio = decimalFormat.format(p.getRatio() * 100);
									ratioStr = p.getKpi_name() + "(" + ratio + "%)";
								} else {
									ratioStr = p.getKpi_name();
								}

								json.append("\"pid\":\"" + pidStr + "\"," + "\"id\":\"" + p.getKpi_code() + "\","
										+ "\"text\":" + "\"" + ratioStr + "\"");

								json.append("},");

								map.put(key, key);
							}
						}
					}
				}
			}


			json.setCharAt(json.length() - 1, ']');

			json.append("}");

		} catch (Exception e) {

			json.append("{Rows:[]}");

			return json.toString();

		}

		return json.toString();
	}

	@Override
	public List<PrmEmpScheme> queryPrmEmpSchemeBySuperKpiCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<PrmEmpScheme> list = prmEmpSchemeMapper.queryPrmEmpSchemeBySuperKpiCode(entityMap);
		return list;
	}

}
