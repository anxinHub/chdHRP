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
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.dao.PrmHosFunStackMapper;
import com.chd.hrp.prm.dao.PrmHosKpiAdMapper;
import com.chd.hrp.prm.dao.PrmHosKpiLedMapper;
import com.chd.hrp.prm.dao.PrmHosKpiMapper;
import com.chd.hrp.prm.dao.PrmHosKpiObjMapper;
import com.chd.hrp.prm.dao.PrmHosKpiSectionMapper;
import com.chd.hrp.prm.dao.PrmHosSchemeMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosFunStack;
import com.chd.hrp.prm.entity.PrmHosKpi;
import com.chd.hrp.prm.entity.PrmHosKpiAd;
import com.chd.hrp.prm.entity.PrmHosKpiLed;
import com.chd.hrp.prm.entity.PrmHosKpiObj;
import com.chd.hrp.prm.entity.PrmHosKpiSection;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmHosSchemeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0203 医院绩效方案表
 * @Table: PRM_HOS_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmHosSchemeService")
public class PrmHosSchemeServiceImpl implements PrmHosSchemeService {

	private static Logger logger = Logger.getLogger(PrmHosSchemeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmHosSchemeMapper")
	private final PrmHosSchemeMapper prmHosSchemeMapper = null;
	
	@Resource(name = "prmHosKpiMapper")
	private final PrmHosKpiMapper prmHosKpiMapper = null;

	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;

	// 引入DAO操作
	@Resource(name = "prmHosKpiObjMapper")
	private final PrmHosKpiObjMapper prmHosKpiObjMapper = null;

	// 引入DAO操作
	@Resource(name = "prmHosKpiSectionMapper")
	private final PrmHosKpiSectionMapper prmHosKpiSectionMapper = null;

	// 引入DAO操作
	@Resource(name = "prmHosKpiAdMapper")
	private final PrmHosKpiAdMapper prmHosKpiAdMapper = null;
	
	@Resource(name = "prmHosKpiLedMapper")
	private final PrmHosKpiLedMapper prmHosKpiLedMapper = null;
	
	@Resource(name = "prmHosFunStackMapper")
	private final PrmHosFunStackMapper prmHosFunStackMapper = null;

	/**
	 * @Description 添加0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmHosScheme(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0303 科室绩效方案表
		PrmHosScheme prmHosScheme = queryPrmHosSchemeByCode(entityMap);

		if (prmHosScheme != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmHosSchemeMapper.addPrmHosScheme(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosScheme\"}";

		}

	}

	/**
	 * @Description 批量添加0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmHosScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosSchemeMapper.addBatchPrmHosScheme(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosScheme\"}";

		}

	}

	/**
	 * @Description 更新0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmHosScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmHosSchemeMapper.updatePrmHosScheme(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosScheme\"}";

		}

	}

	/**
	 * @Description 批量更新0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmHosScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosSchemeMapper.updateBatchPrmHosScheme(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosScheme\"}";

		}

	}

	/**
	 * @Description 删除0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmHosScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmHosSchemeMapper.deletePrmHosScheme(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosScheme\"}";

		}

	}

	/**
	 * @Description 批量删除0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmHosScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosSchemeMapper.deleteBatchPrmHosScheme(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosScheme\"}";

		}
	}

	/**
	 * @Description 查询结果集0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosScheme(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmHosScheme> list = prmHosSchemeMapper.queryPrmHosScheme(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosScheme> list = prmHosSchemeMapper.queryPrmHosScheme(entityMap, rowBounds);

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
	public PrmHosScheme queryPrmHosSchemeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmHosSchemeMapper.queryPrmHosSchemeByCode(entityMap);
	}

	@Override
	public PrmHosScheme queryPrmHosSchemeMethodByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return prmHosSchemeMapper.queryPrmHosSchemeMethodByCode(entityMap);
	}

	@Override
	public String queryPrmHosSchemeLeftName(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmHosScheme> list = prmHosSchemeMapper.queryPrmHosSchemeLeftName(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosScheme> list = prmHosSchemeMapper.queryPrmHosSchemeLeftName(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String createPrmHosScheme(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		try {

			String rbtnl = (String) entityMap.get("rbtnl");

			List<Map<String, Object>> HosSchemeList = new ArrayList<Map<String, Object>>();

			List<PrmHosKpiObj> HosKpiObjList = prmHosKpiObjMapper.queryPrmHosKpiObj(entityMap);

			if ("all".equals(rbtnl)) {// 覆盖生成

				for (PrmHosKpiObj p : HosKpiObjList) {

					Map<String, Object> map = getMap(p, entityMap);

					HosSchemeList.add(map);

				}
				if(HosSchemeList.size() > 0){
					prmHosKpiSectionMapper.deleteBatchPrmHosKpiSection(HosSchemeList);
	
					prmHosKpiAdMapper.deleteBatchPrmHosKpiAd(HosSchemeList);
					
					prmHosKpiLedMapper.deleteBatchPrmHosKpiLed(HosSchemeList);
					
					prmHosFunStackMapper.deleteBatchPrmHosFunStack(HosSchemeList);
	
					prmHosSchemeMapper.deleteBatchPrmHosScheme(HosSchemeList);
				}
			} else {

				Map<String, String> prmHoschemeMap = new HashMap<String, String>();

				List<PrmHosScheme> prmHosSchemelist = prmHosSchemeMapper.queryPrmHosScheme(entityMap);

				for (PrmHosScheme prmHosSchemelist2 : prmHosSchemelist) {

					prmHoschemeMap.put(
							prmHosSchemelist2.getKpi_code() +  prmHosSchemelist2.getCheck_hos_id(),
							prmHosSchemelist2.getKpi_code() +  prmHosSchemelist2.getCheck_hos_id());

				}

				for (int i = 0; i < HosKpiObjList.size(); i++) {

					PrmHosKpiObj HosSchemeList2 = HosKpiObjList.get(i);

					if (prmHoschemeMap.get(HosSchemeList2.getKpi_code() + HosSchemeList2.getCheck_hos_id()) == null) {

						Map<String, Object> addMap = new HashMap<String, Object>();

						addMap.put("kpi_code", HosSchemeList2.getKpi_code());

						addMap.put("check_hos_id", HosSchemeList2.getCheck_hos_id());

						addMap.put("group_id", HosSchemeList2.getGroup_id());

						addMap.put("hos_id", HosSchemeList2.getHos_id());

						addMap.put("copy_code", HosSchemeList2.getCopy_code());

						addMap.put("acc_year", HosSchemeList2.getAcc_year());

						addMap.put("acc_month", entityMap.get("acc_month"));

						addMap.put("goal_code", HosSchemeList2.getGoal_code());

						addMap.put("super_kpi_code", HosSchemeList2.getSuper_kpi_code());

						addMap.put("kpi_level", HosSchemeList2.getKpi_level());

						addMap.put("order_no", HosSchemeList2.getOrder_no());

						addMap.put("is_last", HosSchemeList2.getIs_last());

						addMap.put("ratio", "0");

						addMap.put("goal_value", "0");

						addMap.put("grade_meth_code", "");

						addMap.put("method_code", "");

						addMap.put("formula_code", "");

						addMap.put("fun_code", "");

						addMap.put("full_score", "0");

						HosSchemeList.add(addMap);

					}

				}
			}

			if (HosSchemeList.size() > 0) {

				prmHosSchemeMapper.addBatchPrmHosScheme(HosSchemeList);

				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

			} else {

				return "{\"error\":\"没有数据生成.\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosScheme\"}";

		}
	}

	public Map<String, Object> getMap(PrmHosKpiObj p, Map<String, Object> mapVo) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", p.getGroup_id());

		map.put("hos_id", p.getHos_id());

		map.put("copy_code", p.getCopy_code());

		map.put("acc_year", p.getAcc_year());

		map.put("acc_month", mapVo.get("acc_month"));

		map.put("goal_code", p.getGoal_code());

		map.put("kpi_code", p.getKpi_code());

		map.put("check_hos_id", p.getCheck_hos_id());

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
	public String saveIntroduceHosScheme(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> hosHosKpiList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> hosHosKpiObjList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> hosSchemeList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> hosSection = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> prmHosKpiAdList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> prmHosKpiLedList = new ArrayList<Map<String, Object>>();// 指示灯
		
		List<Map<String, Object>> prmHosFunStackList = new ArrayList<Map<String, Object>>();//函数站
		

		Map<String, Object> mapScheme = new HashMap<String, Object>();

		mapScheme.put("group_id", SessionManager.getGroupId());

		mapScheme.put("hos_id",entityMap.get("hos_id"));

		mapScheme.put("copy_code", SessionManager.getCopyCode());

		mapScheme.put("acc_year", entityMap.get("acc_year"));

		mapScheme.put("acc_month", entityMap.get("acc_month"));

		mapScheme.put("goal_code", entityMap.get("goal_code"));

		mapScheme.put("check_hos_id", String.valueOf(entityMap.get("check_hos_id")));
		
		
		Map<String, Object> isMapScheme = new HashMap<String, Object>();

		isMapScheme.put("group_id", SessionManager.getGroupId());

		isMapScheme.put("hos_id",entityMap.get("kpiHos_id"));

		isMapScheme.put("copy_code", SessionManager.getCopyCode());

		isMapScheme.put("acc_year", entityMap.get("kpiAcc_year"));
		
		isMapScheme.put("acc_month", entityMap.get("kpiAcc_month"));

		isMapScheme.put("goal_code", entityMap.get("kpiGoal_code"));

		isMapScheme.put("check_hos_id", String.valueOf(entityMap.get("kpiCheck_hos_id")));
		

		String kpi_code = String.valueOf(entityMap.get("kpi_code"));

		String ratio = String.valueOf(entityMap.get("ratio"));

		String goal_value = String.valueOf(entityMap.get("goal_value"));

		String grade_meth_code = String.valueOf(entityMap.get("grade_meth_code"));

		String method_code = String.valueOf(entityMap.get("method_code"));

		String hos_led = String.valueOf(entityMap.get("hos_led"));
		
		String hos_full = String.valueOf(entityMap.get("hos_full"));

		List<PrmHosScheme> prmHosScheme = prmHosSchemeMapper.queryPrmHosSchemeLeftName(mapScheme);//要引入的年月的数据
		
		List<PrmHosKpiObj> prmHosKpiObjList = prmHosKpiObjMapper.queryPrmHosKpiObj(mapScheme);
		
		List<PrmHosKpi> prmHosKpilist = prmHosKpiMapper.queryPrmHosKpi(mapScheme);
		
		
		/*判断当前年度和战略是否有数据*/
		List<PrmHosKpiObj> isPrmHosKpiObjList = prmHosKpiObjMapper.queryPrmHosKpiObj(isMapScheme);
		
		List<PrmHosKpi> isPrmHosKpilist = prmHosKpiMapper.queryPrmHosKpi(isMapScheme);
		

		/* 第一种情况选择指标体系时INSERT */
		

			if (prmHosScheme.size() > 0) {
				
				if(isPrmHosKpilist.size() == 0){
					for(PrmHosKpi prmHosKpi : prmHosKpilist){
						Map<String, Object> kpiMapVo = new HashMap<String, Object>();
						kpiMapVo.put("group_id", prmHosKpi.getGroup_id());

						kpiMapVo.put("hos_id", Long.parseLong(entityMap.get("kpiHos_id").toString()));

						kpiMapVo.put("copy_code", prmHosKpi.getCopy_code());

						kpiMapVo.put("acc_year",  entityMap.get("kpiAcc_year"));

						kpiMapVo.put("goal_code", entityMap.get("kpiGoal_code"));

						kpiMapVo.put("kpi_code", prmHosKpi.getKpi_code());
						
						kpiMapVo.put("kpi_name", prmHosKpi.getKpi_name());
						
						kpiMapVo.put("dim_code", prmHosKpi.getDim_code());
						
						kpiMapVo.put("order_no", prmHosKpi.getOrder_no());

						kpiMapVo.put("kpi_level", prmHosKpi.getKpi_level());
						
						kpiMapVo.put("nature_code", prmHosKpi.getNature_code());

						kpiMapVo.put("super_kpi_code", prmHosKpi.getSuper_kpi_code());

						kpiMapVo.put("spell_code", prmHosKpi.getSpell_code());

						kpiMapVo.put("wbx_code", prmHosKpi.getWbx_code());

						kpiMapVo.put("is_last", prmHosKpi.getIs_last());
						
						hosHosKpiList.add(kpiMapVo);
					}
				}
				
				if(isPrmHosKpiObjList.size() == 0){
					for(PrmHosKpiObj prmHosKpiObj : prmHosKpiObjList){
						Map<String, Object> kpiObjMapVo = new HashMap<String, Object>();
						kpiObjMapVo.put("group_id", prmHosKpiObj.getGroup_id());

						kpiObjMapVo.put("hos_id", entityMap.get("kpiHos_id"));

						kpiObjMapVo.put("copy_code", prmHosKpiObj.getCopy_code());

						kpiObjMapVo.put("acc_year",  entityMap.get("kpiAcc_year"));

						kpiObjMapVo.put("goal_code", entityMap.get("kpiGoal_code"));

						kpiObjMapVo.put("check_hos_id", String.valueOf(entityMap.get("kpiCheck_hos_id")));

						kpiObjMapVo.put("kpi_code", prmHosKpiObj.getKpi_code());

						kpiObjMapVo.put("super_kpi_code", prmHosKpiObj.getSuper_kpi_code());

						kpiObjMapVo.put("order_no", prmHosKpiObj.getOrder_no());

						kpiObjMapVo.put("kpi_level", prmHosKpiObj.getKpi_level());

						kpiObjMapVo.put("is_last", prmHosKpiObj.getIs_last());
						
						hosHosKpiObjList.add(kpiObjMapVo);
					}
				}
				

				for (PrmHosScheme PrmHosScheme2 : prmHosScheme) {

					Map<String, Object> addMapVo = new HashMap<String, Object>();

					addMapVo.put("group_id", PrmHosScheme2.getGroup_id());

					addMapVo.put("hos_id", entityMap.get("kpiHos_id"));

					addMapVo.put("copy_code", PrmHosScheme2.getCopy_code());

					addMapVo.put("acc_year",  entityMap.get("kpiAcc_year"));

					addMapVo.put("acc_month", entityMap.get("kpiAcc_month"));

					addMapVo.put("goal_code", entityMap.get("kpiGoal_code"));

					addMapVo.put("check_hos_id", String.valueOf(entityMap.get("kpiCheck_hos_id")));

					addMapVo.put("kpi_code", PrmHosScheme2.getKpi_code());

					addMapVo.put("super_kpi_code", PrmHosScheme2.getSuper_kpi_code());

					addMapVo.put("order_no", PrmHosScheme2.getOrder_no());

					addMapVo.put("kpi_level", PrmHosScheme2.getKpi_level());

					addMapVo.put("is_last", PrmHosScheme2.getIs_last());

					// 权重
					if ("true".equals(ratio)) {

						addMapVo.put("ratio", String.valueOf(PrmHosScheme2.getRatio()));

					} else {

						addMapVo.put("ratio", "0");
					}
					// 目标值
					if ("true".equals(goal_value)) {

						addMapVo.put("goal_value", PrmHosScheme2.getGoal_value());

					} else {

						addMapVo.put("goal_value", "0");
					}

					// 评分方法
					if (("true").equals(grade_meth_code)) {

						if (PrmHosScheme2.getGrade_meth_code() != null) {

							Map<String, Object> queryGradeMapVo = new HashMap<String, Object>();
							
							queryGradeMapVo = addMapVo;

							queryGradeMapVo.put("grade_meth_code", PrmHosScheme2.getGrade_meth_code());
							queryGradeMapVo.put("acc_month", mapScheme.get("acc_month"));

							List<PrmHosKpiSection> prmHosKpiSection = prmHosKpiSectionMapper
									.queryPrmHosKpiSection(queryGradeMapVo);

							for (PrmHosKpiSection PrmHosKpiSection2 : prmHosKpiSection) {

								Map<String, Object> addMapVoSection = new HashMap<String, Object>();

								addMapVoSection.put("group_id", PrmHosKpiSection2.getGroup_id());

								addMapVoSection.put("hos_id", entityMap.get("kpiHos_id"));

								addMapVoSection.put("copy_code", PrmHosKpiSection2.getCopy_code());

								addMapVoSection.put("acc_year", entityMap.get("kpiAcc_year"));

								addMapVoSection.put("acc_month", entityMap.get("kpiAcc_month"));

								addMapVoSection.put("goal_code", entityMap.get("kpiGoal_code"));

								addMapVoSection.put("check_hos_id", String.valueOf(entityMap.get("kpiCheck_hos_id")));

								addMapVoSection.put("section", PrmHosKpiSection2.getSection());

								addMapVoSection.put("kpi_code", PrmHosKpiSection2.getKpi_code());

								addMapVoSection.put("kpi_beg_value", PrmHosKpiSection2.getKpi_beg_value());

								addMapVoSection.put("kpi_end_value", PrmHosKpiSection2.getKpi_end_value());

								addMapVoSection.put("kpi_beg_score", PrmHosKpiSection2.getKpi_beg_score());

								addMapVoSection.put("kpi_end_score", PrmHosKpiSection2.getKpi_end_score());

								hosSection.add(addMapVoSection);

							}
							List<PrmHosKpiAd> prmHosKpiAd = prmHosKpiAdMapper.queryPrmHosKpiAd(queryGradeMapVo);

							for (PrmHosKpiAd prmHosKpiAd2 : prmHosKpiAd) {

								Map<String, Object> addHosKpiAdMap = new HashMap<String, Object>();

								addHosKpiAdMap.put("group_id", prmHosKpiAd2.getGroup_id());

								addHosKpiAdMap.put("hos_id", entityMap.get("kpiHos_id"));

								addHosKpiAdMap.put("copy_code", prmHosKpiAd2.getCopy_code());

								addHosKpiAdMap.put("acc_year", entityMap.get("kpiAcc_year"));

								addHosKpiAdMap.put("acc_month", entityMap.get("kpiAcc_month"));

								addHosKpiAdMap.put("goal_code", entityMap.get("kpiGoal_code"));

								addHosKpiAdMap.put("kpi_code", prmHosKpiAd2.getKpi_code());

								addHosKpiAdMap.put("check_hos_id", String.valueOf(entityMap.get("kpiCheck_hos_id")));

								addHosKpiAdMap.put("kpi_range_value", prmHosKpiAd2.getKpi_range_value() == null?0.0:prmHosKpiAd2.getKpi_range_value());

								addHosKpiAdMap.put("kpi_range_score", prmHosKpiAd2.getKpi_range_score() == null?0.0:prmHosKpiAd2.getKpi_range_score());

								prmHosKpiAdList.add(addHosKpiAdMap);
							}
							addMapVo.put("grade_meth_code", PrmHosScheme2.getGrade_meth_code());
						} else {
							addMapVo.put("grade_meth_code", "");
						}

					} 
					
					//满分标准
					if(("true").equals(hos_full)){
						addMapVo.put("full_score",
								PrmHosScheme2.getFull_score() != null ? PrmHosScheme2.getFull_score() : "0");
					}else{
						addMapVo.put("full_score","0");
					}
					
					//指示灯
					if(("true").equals(hos_led)){
						Map<String, Object> queryGradeMapVo = new HashMap<String, Object>();
						
						queryGradeMapVo = addMapVo;

						queryGradeMapVo.put("acc_month", mapScheme.get("acc_month"));
						
						List<PrmHosKpiLed> prmHosKpdLedList = prmHosKpiLedMapper.queryPrmHosKpiLed(queryGradeMapVo);
						for(PrmHosKpiLed prmHosKpiLed : prmHosKpdLedList){
							Map<String, Object> addMapVoLed = new HashMap<String, Object>();
							
							addMapVoLed.put("group_id", prmHosKpiLed.getGroup_id());

							addMapVoLed.put("hos_id", entityMap.get("kpiHos_id"));

							addMapVoLed.put("copy_code", prmHosKpiLed.getCopy_code());

							addMapVoLed.put("acc_year", entityMap.get("kpiAcc_year"));

							addMapVoLed.put("acc_month", entityMap.get("kpiAcc_month"));

							addMapVoLed.put("goal_code", entityMap.get("kpiGoal_code"));

							addMapVoLed.put("kpi_code", prmHosKpiLed.getKpi_code());

							addMapVoLed.put("check_hos_id", String.valueOf(entityMap.get("kpiCheck_hos_id")));

							addMapVoLed.put("sec_code", prmHosKpiLed.getSec_code());

							addMapVoLed.put("kpi_beg_score", prmHosKpiLed.getKpi_beg_score() == null?0.0:prmHosKpiLed.getKpi_beg_score());
							
							addMapVoLed.put("kpi_end_score", prmHosKpiLed.getKpi_end_score() == null?0.0:prmHosKpiLed.getKpi_end_score());

							prmHosKpiLedList.add(addMapVoLed);
						}
					}


					// 取值方法
					if ("true".equals(method_code)) {
						Map<String, Object> queryGradeMapVo = new HashMap<String, Object>();
						
						queryGradeMapVo = addMapVo;

						queryGradeMapVo.put("acc_month", mapScheme.get("acc_month"));
						List<PrmHosFunStack> prmFunStackList = prmHosFunStackMapper.queryPrmHosFunStack(queryGradeMapVo);
						for(PrmHosFunStack prmDeptFunStack : prmFunStackList){
							Map<String, Object> addMapVoFunStack = new HashMap<String, Object>();
							
							addMapVoFunStack.put("group_id", prmDeptFunStack.getGroup_id());

							addMapVoFunStack.put("hos_id", entityMap.get("kpiHos_id"));

							addMapVoFunStack.put("copy_code", prmDeptFunStack.getCopy_code());

							addMapVoFunStack.put("acc_year", entityMap.get("kpiAcc_year"));

							addMapVoFunStack.put("acc_month", entityMap.get("kpiAcc_month"));

							addMapVoFunStack.put("goal_code", entityMap.get("kpiGoal_code"));

							addMapVoFunStack.put("kpi_code", prmDeptFunStack.getKpi_code());

							addMapVoFunStack.put("check_hos_id", String.valueOf(entityMap.get("kpiCheck_hos_id")));

							addMapVoFunStack.put("fun_para_code", prmDeptFunStack.getFun_para_code());
							
							addMapVoFunStack.put("fun_para_value", prmDeptFunStack.getFun_para_value());

							prmHosFunStackList.add(addMapVoFunStack);
						}

						addMapVo.put("method_code",
								PrmHosScheme2.getMethod_code() != null ? PrmHosScheme2.getMethod_code() : "");

						addMapVo.put("formula_code",
								PrmHosScheme2.getFormula_code() != null ? PrmHosScheme2.getFormula_code() : "");

						addMapVo.put("fun_code",
								PrmHosScheme2.getFun_code() != null ? PrmHosScheme2.getFun_code() : "");

					} else {

						addMapVo.put("method_code", "");

						addMapVo.put("formula_code", "");

						addMapVo.put("fun_code", "");

					}

					addMapVo.put("acc_month", entityMap.get("kpiAcc_month"));
					hosSchemeList.add(addMapVo);

				}

				
				if(hosHosKpiList.size() > 0){
					prmHosKpiMapper.deleteBatchPrmHosKpi(hosHosKpiList);
					prmHosKpiMapper.addBatchPrmHosKpi(hosHosKpiList);
				}

				if (hosSchemeList.size() > 0) {
					prmHosKpiSectionMapper.deleteBatchPrmHosKpiSection(hosSchemeList);
					prmHosKpiAdMapper.deleteBatchPrmHosKpiAd(hosSchemeList);
					prmHosKpiLedMapper.deleteBatchPrmHosKpiLed(hosSchemeList);
					prmHosFunStackMapper.deleteBatchPrmHosFunStack(hosSchemeList);
					prmHosSchemeMapper.deleteBatchPrmHosSchemeByImport(hosSchemeList);
					if(hosHosKpiObjList.size() > 0){
						prmHosKpiObjMapper.deleteBatchPrmHosKpiObj(hosHosKpiObjList);
						prmHosKpiObjMapper.addBatchPrmHosKpiObj(hosHosKpiObjList);
					}
					prmHosSchemeMapper.addBatchPrmHosScheme(hosSchemeList);
				}
				if (hosSection.size() > 0) {
					prmHosKpiSectionMapper.addBatchPrmHosKpiSection(hosSection);
				}
				if (prmHosKpiAdList.size() > 0) {
					prmHosKpiAdMapper.addBatchPrmHosKpiAd(prmHosKpiAdList);
				}
				if (prmHosKpiLedList.size() > 0) {
					prmHosKpiLedMapper.addBatchPrmHosKpiLed(prmHosKpiLedList);
				}
				if(prmHosFunStackList.size() > 0){
					prmHosFunStackMapper.addBatchPrmHosFunStack(prmHosFunStackList);
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
			List<PrmHosScheme> list_hos_kpi = prmHosSchemeMapper.queryPrmHosScheme(entityMap);

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

				for (PrmHosScheme p : list_hos_kpi) {

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
			
			e.printStackTrace();

			return json.toString();

		}

		return json.toString();
	}

	@Override
	public List<PrmHosScheme> queryPrmHosSchemeBySuperKpiCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<PrmHosScheme> list = prmHosSchemeMapper.queryPrmHosSchemeBySuperKpiCode(entityMap);
		return list;
	}
}
