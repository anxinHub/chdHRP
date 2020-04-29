
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.prm.dao.PrmDeptFormuLaMethodMapper;
import com.chd.hrp.prm.dao.PrmDeptFunStackMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiAdMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiLedMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiObjMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiSectionMapper;
import com.chd.hrp.prm.dao.PrmDeptSchemeMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmDeptFormulaMethod;
import com.chd.hrp.prm.entity.PrmDeptFunStack;
import com.chd.hrp.prm.entity.PrmDeptKpiAd;
import com.chd.hrp.prm.entity.PrmDeptKpiLed;
import com.chd.hrp.prm.entity.PrmDeptKpiObj;
import com.chd.hrp.prm.entity.PrmDeptKpiSection;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.service.PrmDeptSchemeService;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @Description: 0303 科室绩效方案表
 * @Table: PRM_DEPT_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptSchemeService")
public class PrmDeptSchemeServiceImpl implements PrmDeptSchemeService {

	private static Logger logger = Logger.getLogger(PrmDeptSchemeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptSchemeMapper")
	private final PrmDeptSchemeMapper prmDeptSchemeMapper = null;

	@Resource(name = "prmDeptKpiMapper")
	private final PrmDeptKpiMapper prmDeptKpiMapper = null;

	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;

	// 引入DAO操作
	@Resource(name = "prmDeptKpiObjMapper")
	private final PrmDeptKpiObjMapper prmDeptKpiObjMapper = null;

	// 引入DAO操作
	@Resource(name = "prmDeptKpiSectionMapper")
	private final PrmDeptKpiSectionMapper prmDeptKpiSectionMapper = null;

	// 引入DAO操作
	@Resource(name = "prmDeptKpiAdMapper")
	private final PrmDeptKpiAdMapper prmDeptKpiAdMapper = null;

	@Resource(name = "prmDeptKpiLedMapper")
	private final PrmDeptKpiLedMapper prmDeptKpiLedMapper = null;

	@Resource(name = "prmDeptFunStackMapper")
	private final PrmDeptFunStackMapper prmDeptFunStackMapper = null;
	
	// 引入DAO操作
	@Resource(name = "prmDeptFormuLaMethodMapper")
	private final PrmDeptFormuLaMethodMapper prmDeptFormuLaMethodMapper = null;

	/**
	 * @Description 添加0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptScheme(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0303 科室绩效方案表
		PrmDeptScheme prmDeptScheme = queryPrmDeptSchemeByCode(entityMap);

		if (prmDeptScheme != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptSchemeMapper.addPrmDeptScheme(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptScheme\"}";

		}

	}

	/**
	 * @Description 批量添加0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptSchemeMapper.addBatchPrmDeptScheme(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptScheme\"}";

		}

	}

	/**
	 * @Description 更新0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptSchemeMapper.updatePrmDeptScheme(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptScheme\"}";

		}

	}

	/**
	 * @Description 批量更新0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptSchemeMapper.updateBatchPrmDeptScheme(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptScheme\"}";

		}

	}

	/**
	 * @Description 删除0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptSchemeMapper.deletePrmDeptScheme(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptScheme\"}";

		}

	}

	/**
	 * @Description 批量删除0303 科室绩效方案表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptSchemeMapper.deleteBatchPrmDeptScheme(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptScheme\"}";

		}
	}

	/**
	 * @Description 查询结果集0303 科室绩效方案表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptScheme(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptScheme> list = prmDeptSchemeMapper.queryPrmDeptScheme(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptScheme> list = prmDeptSchemeMapper.queryPrmDeptScheme(entityMap, rowBounds);

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
	public PrmDeptScheme queryPrmDeptSchemeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptSchemeMapper.queryPrmDeptSchemeByCode(entityMap);
	}

	@Override
	public PrmDeptScheme queryPrmDeptSchemeMethodByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return prmDeptSchemeMapper.queryPrmDeptSchemeMethodByCode(entityMap);
	}

	@Override
	public String queryPrmDeptSchemeLeftName(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptScheme> list = prmDeptSchemeMapper.queryPrmDeptSchemeLeftName(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptScheme> list = prmDeptSchemeMapper.queryPrmDeptSchemeLeftName(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String createPrmDeptScheme(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		try {

			String rbtnl = (String) entityMap.get("rbtnl");

			List<Map<String, Object>> DeptSchemeList = new ArrayList<Map<String, Object>>();

			List<PrmDeptKpiObj> deptKpiObjList = prmDeptKpiObjMapper.queryPrmDeptKpiObj(entityMap);

			if ("all".equals(rbtnl)) {// 覆盖生成

				for (PrmDeptKpiObj p : deptKpiObjList) {

					Map<String, Object> map = getMap(p, entityMap);

					DeptSchemeList.add(map);

				}
				if (DeptSchemeList.size() > 0) {
					prmDeptKpiSectionMapper.deleteBatchPrmDeptKpiSection(DeptSchemeList);

					prmDeptKpiAdMapper.deleteBatchPrmDeptKpiAd(DeptSchemeList);

					prmDeptKpiLedMapper.deleteBatchPrmDeptKpiLed(DeptSchemeList);

					prmDeptFunStackMapper.deleteBatchPrmDeptFunStack(DeptSchemeList);

					prmDeptSchemeMapper.deleteBatchPrmDeptScheme(DeptSchemeList);
					
					prmDeptFormuLaMethodMapper.deleteBatchDeptFormuLaMethod(DeptSchemeList);

				}
			} else {

				Map<String, String> prmDeptchemeMap = new HashMap<String, String>();

				List<PrmDeptScheme> prmDeptSchemelist = prmDeptSchemeMapper.queryPrmDeptScheme(entityMap);

				for (PrmDeptScheme prmDeptSchemelist2 : prmDeptSchemelist) {

					prmDeptchemeMap.put(
							prmDeptSchemelist2.getKpi_code() + prmDeptSchemelist2.getDept_no()
									+ prmDeptSchemelist2.getDept_id(),
							prmDeptSchemelist2.getKpi_code() + prmDeptSchemelist2.getDept_no()
									+ prmDeptSchemelist2.getDept_id());

				}

				for (int i = 0; i < deptKpiObjList.size(); i++) {

					PrmDeptKpiObj deptSchemeList2 = deptKpiObjList.get(i);

					if (prmDeptchemeMap.get(deptSchemeList2.getKpi_code() + deptSchemeList2.getDept_no()
							+ deptSchemeList2.getDept_id()) == null) {

						Map<String, Object> addMap = new HashMap<String, Object>();

						addMap.put("kpi_code", deptSchemeList2.getKpi_code());

						addMap.put("dept_no", deptSchemeList2.getDept_no());

						addMap.put("dept_id", deptSchemeList2.getDept_id());

						addMap.put("group_id", deptSchemeList2.getGroup_id());

						addMap.put("hos_id", deptSchemeList2.getHos_id());

						addMap.put("copy_code", deptSchemeList2.getCopy_code());

						addMap.put("acc_year", deptSchemeList2.getAcc_year());

						addMap.put("acc_month", entityMap.get("acc_month"));

						addMap.put("goal_code", deptSchemeList2.getGoal_code());

						addMap.put("super_kpi_code", deptSchemeList2.getSuper_kpi_code());

						addMap.put("kpi_level", deptSchemeList2.getKpi_level());

						addMap.put("order_no", deptSchemeList2.getOrder_no());

						addMap.put("is_last", deptSchemeList2.getIs_last());

						addMap.put("ratio", "0");

						addMap.put("goal_value", "0");

						addMap.put("grade_meth_code", "");

						addMap.put("method_code", "");

						addMap.put("formula_code", "");

						addMap.put("fun_code", "");

						addMap.put("full_score", "0");

						DeptSchemeList.add(addMap);

					}

				}
			}

			if (DeptSchemeList.size() > 0) {

				prmDeptSchemeMapper.addBatchPrmDeptScheme(DeptSchemeList);

				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

			} else {

				return "{\"error\":\"没有数据生成.\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosScheme\"}";

		}
	}

	public Map<String, Object> getMap(PrmDeptKpiObj p, Map<String, Object> mapVo) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", p.getGroup_id());

		map.put("hos_id", p.getHos_id());

		map.put("copy_code", p.getCopy_code());

		map.put("acc_year", p.getAcc_year());

		map.put("acc_month", mapVo.get("acc_month"));

		map.put("goal_code", p.getGoal_code());

		map.put("kpi_code", p.getKpi_code());

		map.put("dept_no", p.getDept_no());

		map.put("dept_id", p.getDept_id());

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
	public String saveIntroduceDeptScheme(Map<String, Object> entityMap) throws DataAccessException {

		/**
		当前写法未考虑 账套之间 医院之间引入数据 
		如果需要账套和医院之间引入数据 怎需要考虑KPI指标是否存在 已经考核对象是否存在KPI数据 两个表
		*/

		//第一步 组织目标查询条件Map
		Map<String, Object> metaMap = new HashMap<String, Object>();//目标查询条件Map

		metaMap.put("group_id", entityMap.get("group_id"));metaMap.put("hos_id", entityMap.get("hos_id"));

		metaMap.put("copy_code", entityMap.get("copy_code"));metaMap.put("acc_year", entityMap.get("acc_year"));

		metaMap.put("acc_month", entityMap.get("acc_month"));metaMap.put("goal_code", entityMap.get("goal_code"));

		metaMap.put("dept_no", entityMap.get("dept_no"));metaMap.put("dept_id", entityMap.get("dept_id"));
		
		//第二部 组织源查询条件metaMap

		Map<String, Object> goalMap = new HashMap<String, Object>();//源查询条件Map

		goalMap.put("group_id", entityMap.get("group_id"));goalMap.put("hos_id", entityMap.get("kpiHos_id"));goalMap.put("copy_code", entityMap.get("copy_code"));
		
		goalMap.put("acc_year", entityMap.get("goal_acct_year_to").toString().substring(0,4));//goalMap.put("acc_month", entityMap.get("kpiAcc_month"));

		goalMap.put("goal_code", entityMap.get("kpiGoal_code"));

		List<String> accYearMonthList = DateUtil.getMonthList(entityMap.get("goal_acct_year_from").toString(),entityMap.get("goal_acct_year_to").toString());
		
		//第三部 声明引入体系
		/**
		kpi_code          :指标体系
		ratio             :权重
		goal_value        :目标值
		grade_meth_code   :评分标准
		method_code       :取值方法
		dept_led          :指示灯
		dept_full         :满分标准
		 */		
		String kpi_code = entityMap.get("kpi_code").toString();String ratio = entityMap.get("ratio").toString();

		String goal_value = entityMap.get("goal_value").toString();String grade_meth_code = entityMap.get("grade_meth_code").toString();

		String method_code =entityMap.get("method_code").toString();String dept_led = entityMap.get("dept_led").toString();

		String dept_full = entityMap.get("dept_full").toString();
		
		//第四部 判断当前年度和战略是否有数据
		
		List<PrmDeptKpiObj> deptMetaObjList = prmDeptKpiObjMapper.queryPrmDeptKpiObj(metaMap);
		
		if(deptMetaObjList.size() == 0){
			
			return "{\"msg\":\"源科室考核对象未设定.\",\"state\":\"true\"}";
		}
		
		StringBuffer deptSqlBuffer = new StringBuffer();
		
		deptSqlBuffer.append(" dept_id in (");
		
		String kpiDept = entityMap.get("kpiDept").toString();
		
		String[] kpiDeptSplit = kpiDept.split(";");
		
		for(String str : kpiDeptSplit) {
			
			deptSqlBuffer.append(str.split("\\.")[0]+",");
			
		}
		deptSqlBuffer.deleteCharAt(deptSqlBuffer.length() -1).append(")");
		
		goalMap.put("detp_sql", deptSqlBuffer.toString());
		
		StringBuffer yearMonthSqlBuffer = new StringBuffer();
		
		yearMonthSqlBuffer.append(" acc_year||acc_month in (");

		for(String str : accYearMonthList) {
			
			yearMonthSqlBuffer.append("'"+str + "',");
			
		}
		yearMonthSqlBuffer.deleteCharAt(yearMonthSqlBuffer.length() -1).append(")");
		
		goalMap.put("year_month_sql", yearMonthSqlBuffer.toString());
		
		List<PrmDeptKpiObj> deptGoalObjList = prmDeptKpiObjMapper.queryPrmDeptKpiObj(goalMap);
		
		if(deptGoalObjList.size() == 0){
			
			return "{\"msg\":\"目标科室考核对象未设定.\",\"state\":\"true\"}";
		}
		
		//第五部 组织目标考核对象的Map 存放KPI_CODE 用来判断目标KPI_CODE 才插入数据
		
		Map<String,String> objMap = new HashMap<String,String>();
		
		for(PrmDeptKpiObj pdko :deptGoalObjList){
			
			objMap.put(pdko.getDept_id()+pdko.getKpi_code(), pdko.getKpi_code());
			
		}
		
		//第五部 声明List
		
		List<Map<String, Object>> deptGoalSchemeList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> deptGoalSection = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> deptGoalKpiAdList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> deptGoalKpiFMList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> deptGoalKpiLedList = new ArrayList<Map<String, Object>>();// 指示灯

		List<Map<String, Object>> deptGoalFunStackList = new ArrayList<Map<String, Object>>();// 函数站
		
		try {
			
			List<PrmDeptScheme> deptMetaScheme = prmDeptSchemeMapper.queryPrmDeptSchemeLeftName(metaMap);
			
			for(PrmDeptScheme pds: deptMetaScheme){
				
				for(String dept : kpiDeptSplit) {
					
					goalMap.put("dept_no", dept.split("\\.")[1]);goalMap.put("dept_id", dept.split("\\.")[0]);
					
					for(String yearMonth : accYearMonthList) {
						
						goalMap.put("acc_year", yearMonth.substring(0, 4));goalMap.put("acc_month", yearMonth.substring(4, 6));
						
						if(objMap.get(dept.split("\\.")[0]+pds.getKpi_code()) != null){

							Map<String, Object> metaSchemeMap = new HashMap<String, Object>();
							
							metaSchemeMap.putAll(goalMap);

							metaSchemeMap.put("kpi_code", pds.getKpi_code());metaSchemeMap.put("super_kpi_code", pds.getSuper_kpi_code());

							metaSchemeMap.put("order_no", pds.getOrder_no());metaSchemeMap.put("kpi_level", pds.getKpi_level());
							
							metaSchemeMap.put("is_last", pds.getIs_last());
							
							metaSchemeMap.put("ratio", "true".equals(ratio) ? pds.getRatio(): "0");
							
							metaSchemeMap.put("goal_value", "true".equals(goal_value) ? pds.getGoal_value(): "0");
							
							metaSchemeMap.put("full_score", "true".equals(dept_full) ? pds.getFull_score(): "0");
							
							// 评分方法
							if (("true").equals(grade_meth_code)) {

								if (pds.getGrade_meth_code() != null) {
									
									metaSchemeMap.put("grade_meth_code", pds.getGrade_meth_code());

									Map<String, Object> map = new HashMap<String, Object>();

									map.putAll(metaMap);
									
									map.put("kpi_code", pds.getKpi_code());

									List<PrmDeptKpiSection> deptMetaKpiSectionList = prmDeptKpiSectionMapper.queryPrmDeptKpiSection(map);

									for (PrmDeptKpiSection pdks : deptMetaKpiSectionList) {

										Map<String, Object> metaKpiSectionMap = new HashMap<String, Object>();
										
										metaKpiSectionMap.putAll(goalMap);

										metaKpiSectionMap.put("section", pdks.getSection());

										metaKpiSectionMap.put("kpi_code", pdks.getKpi_code());

										metaKpiSectionMap.put("kpi_beg_value", pdks.getKpi_beg_value().toString());

										metaKpiSectionMap.put("kpi_end_value", pdks.getKpi_end_value().toString());

										metaKpiSectionMap.put("kpi_beg_score", pdks.getKpi_beg_score().toString());

										metaKpiSectionMap.put("kpi_end_score", pdks.getKpi_end_score().toString());

										deptGoalSection.add(metaKpiSectionMap);

									}
									
									List<PrmDeptKpiAd> deptMetaKpiAdList = prmDeptKpiAdMapper.queryPrmDeptKpiAd(map);

									for (PrmDeptKpiAd pdka : deptMetaKpiAdList) {

										Map<String, Object> metaKpiAdMap = new HashMap<String, Object>();
										
										metaKpiAdMap.putAll(goalMap);

										metaKpiAdMap.put("kpi_code", pdka.getKpi_code());

										metaKpiAdMap.put("kpi_range_value", pdka.getKpi_range_value() == null ? 0.0: pdka.getKpi_range_value());

										metaKpiAdMap.put("kpi_range_score", pdka.getKpi_range_score() == null ? 0.0: pdka.getKpi_range_score());

										deptGoalKpiAdList.add(metaKpiAdMap);
									}
									
									List<PrmDeptFormulaMethod> prmDeptFormulaMethod = prmDeptFormuLaMethodMapper.queryDeptFormulaMethod(map);

									for (PrmDeptFormulaMethod pdf : prmDeptFormulaMethod) {

										Map<String, Object> metaKpiMFMap = new HashMap<String, Object>();
										
										metaKpiMFMap.putAll(goalMap);

										metaKpiMFMap.put("kpi_code", pdf.getKpi_code());

										metaKpiMFMap.put("formula_method_chs", pdf.getFormula_method_chs());
                                     
										if (pdf.getFormula_method_eng() != null) {
											
											metaKpiMFMap.put("formula_method_eng",pdf.getFormula_method_eng());

										}

										deptGoalKpiFMList.add(metaKpiMFMap);
									}

								} else {
									
									metaSchemeMap.put("grade_meth_code", "");
								}

							}else{
								
								metaSchemeMap.put("grade_meth_code", "");
								
							}
							
							// 指示灯
							if (("true").equals(dept_led)) {

								Map<String, Object> map = new HashMap<String, Object>();

								map.putAll(metaMap);
								
								map.put("kpi_code", pds.getKpi_code());

								List<PrmDeptKpiLed> deptMetaKpdLedList = prmDeptKpiLedMapper.queryPrmDeptKpiLed(map);
								
								for (PrmDeptKpiLed pdkl : deptMetaKpdLedList) {
									
									Map<String, Object> metaKpiLedMap = new HashMap<String, Object>();

									metaKpiLedMap.putAll(goalMap);

									metaKpiLedMap.put("kpi_code", pdkl.getKpi_code());

									metaKpiLedMap.put("sec_code", pdkl.getSec_code());

									metaKpiLedMap.put("kpi_beg_score",pdkl.getKpi_beg_score() == null ? 0.0 : pdkl.getKpi_beg_score());

									metaKpiLedMap.put("kpi_end_score",pdkl.getKpi_end_score() == null ? 0.0 : pdkl.getKpi_end_score());
									
									deptGoalKpiLedList.add(metaKpiLedMap);
								}

							}
							//取值方法
							if ("true".equals(method_code)) {
								
								Map<String, Object> map = new HashMap<String, Object>();

								map.putAll(metaMap);
								
								map.put("kpi_code", pds.getKpi_code());

								List<PrmDeptFunStack> deptMetaFunStackList = prmDeptFunStackMapper.queryPrmDeptFunStack(map);
								
								for (PrmDeptFunStack pdfs : deptMetaFunStackList) {
									
									Map<String, Object> metaFunStackMap = new HashMap<String, Object>();

									metaFunStackMap.put("kpi_code", pdfs.getKpi_code());

									metaFunStackMap.put("fun_para_code", pdfs.getFun_para_code());

									metaFunStackMap.put("fun_para_value", pdfs.getFun_para_value());

									deptGoalFunStackList.add(metaFunStackMap);
								}

								metaSchemeMap.put("method_code", pds.getMethod_code() != null ? pds.getMethod_code() : "");

								metaSchemeMap.put("formula_code", pds.getFormula_code() != null ? pds.getFormula_code() : "");

								metaSchemeMap.put("fun_code", pds.getFun_code() != null ? pds.getFun_code() : "");

							} else {

								metaSchemeMap.put("method_code", "");metaSchemeMap.put("formula_code", "");metaSchemeMap.put("fun_code", "");

							}

							deptGoalSchemeList.add(metaSchemeMap);

						}
						
					}

				}

			}
			
			/** 方法顶部注释 未考虑的两个条件
			if (hosDeptKpiList.size() > 0) {
			prmDeptKpiMapper.deleteBatchPrmDeptKpi(hosDeptKpiList);
			prmDeptKpiMapper.addBatchPrmDeptKpi(hosDeptKpiList);
			}
			if (hosDeptKpiObjList.size() > 0) {
				prmDeptKpiObjMapper.deleteBatchPrmDeptKpiObj(hosDeptKpiObjList);
				prmDeptKpiObjMapper.addBatchPrmDeptKpiObj(hosDeptKpiObjList);
			}*/
			

			//保存数据
			if (deptGoalSchemeList.size() > 0) {
				
				prmDeptKpiSectionMapper.deletePrmDeptKpiSection(goalMap);
				
				prmDeptKpiAdMapper.deletePrmDeptKpiAd(goalMap);
				
				prmDeptKpiLedMapper.deletePrmDeptKpiLed(goalMap);
				
				prmDeptFormuLaMethodMapper.deleteDeptFormuLaMethod(goalMap);
				
				prmDeptFunStackMapper.deletePrmDeptFunStack(goalMap);
				
				prmDeptSchemeMapper.deletePrmDeptScheme(goalMap);

				prmDeptSchemeMapper.addBatchPrmDeptScheme(deptGoalSchemeList);
				
			}
			
			if (deptGoalSection.size() > 0) {prmDeptKpiSectionMapper.addBatchPrmDeptKpiSection(deptGoalSection);}
			
			if (deptGoalKpiAdList.size() > 0) {prmDeptKpiAdMapper.addBatchPrmDeptKpiAd(deptGoalKpiAdList);}
			
			if(deptGoalKpiFMList.size()>0){prmDeptFormuLaMethodMapper.addBatchDeptFormuLaMethod(deptGoalKpiFMList);}
			
			if (deptGoalKpiLedList.size() > 0) {prmDeptKpiLedMapper.addBatchPrmDeptKpiLed(deptGoalKpiLedList);}
			
			if (deptGoalFunStackList.size() > 0) {prmDeptFunStackMapper.addBatchPrmDeptFunStack(deptGoalFunStackList);}

			return "{\"msg\":\"引入成功.\",\"state\":\"true\"}";
			
		} catch (NumberFormatException e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
			
		}catch (DataAccessException e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
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
			List<PrmDeptScheme> list_hos_kpi = prmDeptSchemeMapper.queryPrmDeptScheme(entityMap);

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

				for (PrmDeptScheme p : list_hos_kpi) {

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
	public List<PrmDeptScheme> queryPrmDeptSchemeBySuperKpiCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<PrmDeptScheme> list = prmDeptSchemeMapper.queryPrmDeptSchemeBySuperKpiCode(entityMap);
		return list;
	}

	
}
