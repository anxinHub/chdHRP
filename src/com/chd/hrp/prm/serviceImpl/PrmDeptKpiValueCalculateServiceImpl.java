/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmDeptKindTargetDataMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiValueCalculateMapper;
import com.chd.hrp.prm.dao.PrmDeptSchemeMapper;
import com.chd.hrp.prm.dao.PrmDeptTargetDataCalculateMapper;
import com.chd.hrp.prm.dao.PrmDeptTargetDataMapper;
import com.chd.hrp.prm.dao.PrmFormulaMapper;
import com.chd.hrp.prm.dao.PrmFormulaStackMapper;
import com.chd.hrp.prm.dao.PrmFunMapper;
import com.chd.hrp.prm.dao.PrmFunStackMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.dao.PrmTargetMethodMapper;
import com.chd.hrp.prm.entity.PrmDeptKindTargetData;
import com.chd.hrp.prm.entity.PrmDeptKpiValue;
import com.chd.hrp.prm.entity.PrmDeptKpiValueCalculate;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.entity.PrmDeptTargetDataCalculate;
import com.chd.hrp.prm.entity.PrmFormula;
import com.chd.hrp.prm.entity.PrmFormulaStack;
import com.chd.hrp.prm.entity.PrmFun;
import com.chd.hrp.prm.entity.PrmFunStack;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.entity.PrmTargetMethod;
import com.chd.hrp.prm.service.PrmDeptKpiValueCalculateService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0308 科室KPI指标数据准备表
 * @Table: PRM_DEPT_KPI_VALUE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptKpiValueCalculateService")
public class PrmDeptKpiValueCalculateServiceImpl implements PrmDeptKpiValueCalculateService {

	private static Logger logger = Logger.getLogger(PrmDeptKpiValueCalculateServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptKpiValueCalculateMapper")
	private final PrmDeptKpiValueCalculateMapper prmDeptKpiValueCalculateMapper = null;
	
	@Resource(name = "prmDeptTargetDataCalculateMapper")
	private final PrmDeptTargetDataCalculateMapper prmDeptTargetDataCalculateMapper = null;

	// 引入DAO操作
	@Resource(name = "prmDeptSchemeMapper")
	private final PrmDeptSchemeMapper prmDeptSchemeMapper = null;

	@Resource(name = "prmFormulaStackMapper")
	private final PrmFormulaStackMapper prmFormulaStackMapper = null;

	@Resource(name = "prmFormulaMapper")
	private final PrmFormulaMapper prmFormulaMapper = null;

	@Resource(name = "prmTargetMethodMapper")
	private final PrmTargetMethodMapper prmTargetMethodMapper = null;
	
	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper prmTargetMapper = null;
	
	@Resource(name = "prmFunMapper")
	private final PrmFunMapper prmFunMapper = null;
	
	@Resource(name = "prmFunStackMapper")
	private final PrmFunStackMapper prmFunStackMapper = null;  
	
	@Resource(name = "prmDeptKindTargetDataMapper")
	private final PrmDeptKindTargetDataMapper prmDeptKindTargetDataMapper = null;
	
	@Resource(name = "prmDeptTargetDataMapper")
	private final PrmDeptTargetDataMapper prmDeptTargetDataMapper = null;
	
	/*private LinkedHashMap<String, String> targetMap = new LinkedHashMap<String, String>();

	private LinkedHashMap<String, String> formulaMap = new LinkedHashMap<String, String>();

	private Map<String, ArrayList<String>> formulaStackMap = new HashMap<String, ArrayList<String>>();

	private Map<String, PrmFormula> prmFormulaMap = new HashMap<String, PrmFormula>();

	private Map<String, PrmTargetMethod> targetMethodMap = new HashMap<String, PrmTargetMethod>();

	Map<String, Double> map01Value = new HashMap<String, Double>();*///

	private Map<String, PrmFormula> prmFormulaMap = new HashMap<String, PrmFormula>();
	
	/***********************************变量区域************************/
	private List<String> collectTargetList = null;

	private Map<String, Double> map01Value = null;

	private Map<String, PrmTarget> targetMap = null;

	private Map<String, PrmFormula> formulaMap = null;

	private Map<String, PrmFun> funMap = null;

	private Map<String, ArrayList<PrmFunStack>> funStackMap = null;

	private Map<String, ArrayList<String>> formulaStackMap = null;

	private Map<String, PrmTargetMethod> targetMethodMap = null;
	
	private Map<String,Object> globalValue = null;//存放全局变量 如所有的科室编码 名称 单位、集团、账套 等 计算开始的固定变量
	
	private static String HPM_APPCODE = null ;//执行代码
	
	private static String HPM_ERRTXT = null ;//错误信息
	/***********************************************************/
	
	/**
	 * @Description 添加0308 科室KPI指标数据准备表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0308 科室KPI指标数据准备表
		PrmDeptKpiValue prmDeptKpiValue = queryPrmDeptKpiValueCalculateByCode(entityMap);

		if (prmDeptKpiValue != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptKpiValueCalculateMapper.addPrmDeptKpiValueCalculate(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKpiValue\"}";

		}

	}

	@Override
	public PrmDeptKpiValue queryPrmDeptKpiValueCalculateByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueCalculateByCode(entityMap);
	}

	@Override
	public String queryPrmDeptKpiValueSchemeCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptKpiValueCalculate> list = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptKpiValueCalculate> list = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String createDeptKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		try {

			entityMap.put("sql", "method_code in ('02','03')");

			List<PrmDeptScheme> prmHosScheme = prmDeptSchemeMapper.queryPrmDeptScheme(entityMap);

			if (prmHosScheme.size() == 0) {

				return "{\"msg\":\"没有需要生成的数据.\",\"state\":\"true\"}";

			}

			prmDeptKpiValueCalculateMapper.cleanPrmDeptKpiValueCalculate(entityMap);

			for (int i = 0; i < prmHosScheme.size(); i++) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("acc_year", entityMap.get("acc_year"));
				mapVo.put("acc_month", entityMap.get("acc_month"));
				mapVo.put("goal_code", prmHosScheme.get(i).getGoal_code());
				mapVo.put("kpi_code",prmHosScheme.get(i).getKpi_code());
				mapVo.put("dept_no", prmHosScheme.get(i).getDept_no());
				mapVo.put("dept_id", prmHosScheme.get(i).getDept_id());
				mapVo.put("kpi_value", 0);
				mapVo.put("is_audit", 0);
				mapVo.put("user_code","");
				mapVo.put("audit_date","");
				prmDeptKpiValueCalculateMapper.addPrmDeptKpiValueCalculate(mapVo);

			}

			return "{\"msg\":\"科室KPI指标数据生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
	}

	@Override
	public String auditPrmDeptKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int state = prmDeptKpiValueCalculateMapper.auditPrmDeptKpiValueCalculate(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"审核失败 数据库异常 请联系管理员!\"}";

		}
	}

	@Override
	public String reauditPrmDeptKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int state = prmDeptKpiValueCalculateMapper.auditPrmDeptKpiValueCalculate(entityMap);

			return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"取消审核失败 数据库异常 请联系管理员!\"}";

		}

	}

	@Override
	public String updateBatchPrmDeptKpiValueCalculate(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			prmDeptKpiValueCalculateMapper.updateBatchPrmDeptKpiValueCalculate(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新成功失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiValueCalculate\"}";

		}
	}

	
	@Transactional(rollbackFor = Exception.class)
	private void getPreFunValue(String target,Map<String,Object> updateMap) throws DataAccessException {

		//------------------------------------------------------------------------
		//初始化错误信息
		
		HPM_APPCODE = target;
		
		HPM_ERRTXT = "提取预执行指标错误！";
		
		//-------------------------------------------------------------------------
		
		PrmTargetMethod atm = targetMethodMap.get(target);
		
		try{

			PrmFun af = funMap.get(atm.getFun_code());
			
			String fun_method_eng = af.getFun_method_eng();
			
			ArrayList<PrmFunStack> list = funStackMap.get(target);
			
			for (PrmFunStack afs : list) {

				String funValue = afs.getFun_para_value();

				if ("prm_group_list".equals(afs.getFun_para_code()) && "group_id".equals(funValue)) {
					
					funValue = globalValue.get("group_id").toString();
					
				}

				if ("prm_hos_list".equals(afs.getFun_para_code()) && "hos_id".equals(funValue)) {
					
					funValue = globalValue.get("hos_id").toString();
					
				}

				if ("prm_copy_list".equals(afs.getFun_para_code()) && "copy_code".equals(funValue)) {
					
					funValue = globalValue.get("copy_code").toString();
				}

				if ("prm_acct_year".equals(afs.getFun_para_code()) && "acct_year".equals(funValue)) {
					
					funValue = globalValue.get("acct_year").toString();
					
				}

				if ("prm_acct_month".equals(afs.getFun_para_code()) && "acct_month".equals(funValue)) {

					funValue = globalValue.get("acct_month").toString();

				}

				if ("hpm_dept_kind_list".equals(afs.getFun_para_code()) && "dept_kind_code".equals(funValue)) {
					
					funValue = updateMap.get("dept_kind_code").toString();
					
				}

				if ("hpm_dept_list".equals(afs.getFun_para_code()) && "dept_id,dept_no".equals(funValue)) {
	
					funValue = updateMap.get("dept_id")+","+updateMap.get("dept_no");

				}

				fun_method_eng = fun_method_eng.replaceAll(afs.getFun_para_code(), "'" + funValue + "'");

			}

			updateMap.put("funSql", fun_method_eng.substring(0, fun_method_eng.length()-1));
			
			//------------------------------------------------------------------------
			//处理错误消息
			HPM_ERRTXT = atm.getFun_code() + HPM_ERRTXT;
			//------------------------------------------------------------------------
			
			prmFunMapper.collectPro(updateMap);
			
			//------------------------------------------------------------------------
			//处理错误消息
			HPM_ERRTXT = HPM_ERRTXT + " 错误消息 ："+ updateMap.get("hpm_errtxt");
			//------------------------------------------------------------------------

		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("指标编码为："+HPM_APPCODE+" "+  HPM_ERRTXT);

		}

	}
	
	// 递归取指标 根据公式编码查询
	private void recursiveFormula(Map<String, String> map) throws DataAccessException {
		
		Map<String, String> targetMap = new HashMap<String, String>();

		// 解析公式 查出公式所用指标
		for (String key : map.keySet()) {
			
			logger.debug("正在提取指标"+key);
			ArrayList<String> targetList = formulaStackMap.get(key);
			
			for (String target : targetList) {

				targetMap.put(target, target);

			}

		}

		Map<String, String> formulaMap = new LinkedHashMap<String, String>();
		
		// 解析指标所用的取值方法
		for (String key : targetMap.keySet()) {

			PrmTargetMethod tm = targetMethodMap.get(key);

			if (tm != null && !"01".equals(tm.getMethod_code())) {

				if ("02".equals(tm.getMethod_code())) {
					
					if(tm.getFormula_code() == null){throw new SysException("{\"error\":\"指标编码为： "+key +"未配置取值公式,请配置！\"}");}
					
					logger.debug("解析指标为："+key+" 解析公式编码为"+tm.getFormula_code());
					
					formulaMap.put(tm.getFormula_code(), tm.getFormula_code());

				} else if ("03".equals(tm.getMethod_code())) {
					
					recursiveFun(key);

				}
				
				collectTargetList.add(key);

			}

		}
		
		if (formulaMap.size() > 0) {recursiveFormula(formulaMap);}
		
		formulaMap = null;targetMap = null;//初始化变量

	}
	
	// 递归取指标 根据取值方法为函数取值
	private void recursiveFun(String target) throws DataAccessException {

		Map<String, String> formulaMap = new HashMap<String, String>();
		
		HPM_APPCODE = target;

		try {

			PrmTargetMethod tm = targetMethodMap.get(target);
			
			if (funMap.get(tm.getFun_code()) == null) {throw new SysException("{\"error\":\"指标 " + target +"未配置取值函数,请配置！\"}");}//如果指标取值方式为函数 但是没有找到该函数直接跳出
			
			PrmFun af = funMap.get(tm.getFun_code());
			
			if(af.getIs_pre() == 1){return;}//如果是预执行函数 跳出不需要解析

			ArrayList<PrmFunStack> list = funStackMap.get(target);

			for (PrmFunStack afs : list) {

				if (targetMap.get(afs.getFun_para_value()) == null) {continue;}

				PrmTargetMethod atm = targetMethodMap.get(afs.getFun_para_value());

				if (atm != null && !"01".equals(atm.getMethod_code())) {
					
					if ("02".equals(atm.getMethod_code())) {

						formulaMap.put(atm.getFormula_code(), atm.getFormula_code());

					} else if ("03".equals(atm.getMethod_code())) {
						
						if(target.equals(afs.getFun_para_value())){
							
							throw new SysException("{\"error\":\"指标编码为：" + HPM_APPCODE +"调用自身错误,请配置！\"}");
						
						}
						
						recursiveFun(afs.getFun_para_value());

					}
					
					collectTargetList.add(afs.getFun_para_value());
					
				}

			}
			
			if (formulaMap.size() > 0) {// 如果存在计算公式 调用递归接着查找
				
				recursiveFormula(formulaMap);
			
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"指标编码为：" +HPM_APPCODE +"调用自身错误,请配置！\"}");
			
		}

	}
		
	
	@Transactional(rollbackFor = Exception.class)
	public Double getFunValue(String target,Map<String,Object> updateMap) throws DataAccessException {

		//------------------------------------------------------------------------
		//初始化错误信息
		
		HPM_APPCODE = target;
		
		HPM_ERRTXT = " 提取函数值错误！";
		
		//-------------------------------------------------------------------------
		
		PrmTargetMethod atm = targetMethodMap.get(target);
		
		if(atm == null){
			
			throw new SysException("指标："+target+" 未配置取值方法,请配置");
			
		}

		if (funMap.get(atm.getFun_code()) == null) {
			
			throw new SysException("指标："+target+" 调用函数编码为："+atm.getFun_code()+" 的函数未配置完整,请配置");

		}
		
		try{
			//如果是预执行函数 则已经计算过 直接提取值
			if(funMap.get(atm.getFun_code()).getIs_pre() == 1){
				
				PrmTarget at = targetMap.get(target);

				if ("01".equals(at.getTarget_nature()) && map01Value.get(target) !=null) {
					
					return map01Value.get(target);
					
				}
				
				else if ("02".equals(at.getTarget_nature()) && map01Value.get(target+updateMap.get("dept_kind_code")) !=null) {
					
					return map01Value.get(target+updateMap.get("dept_kind_code"));
					
				}
				
				else if ("03".equals(at.getTarget_nature()) && map01Value.get(target+updateMap.get("dept_id")+updateMap.get("dept_no")) !=null) {
					
					return map01Value.get(target+updateMap.get("dept_id")+updateMap.get("dept_no"));
					
				}
				
				else {
					
					return 0.0;
					
				}
				
			}else{
				
				PrmFun af = funMap.get(atm.getFun_code());
				
				String fun_method_eng = af.getFun_method_eng();
				
				ArrayList<PrmFunStack> list = funStackMap.get(target);
				
				for (PrmFunStack afs : list) {

					String funValue = afs.getFun_para_value();

					if ("prm_group_list".equals(afs.getFun_para_code()) && "group_id".equals(funValue)) {
						
						funValue = globalValue.get("group_id").toString();                                                                                  
						
					}

					if ("prm_hos_list".equals(afs.getFun_para_code()) && "hos_id".equals(funValue)) {
						
						funValue = globalValue.get("hos_id").toString();
						
					}

					if ("prm_copy_list".equals(afs.getFun_para_code()) && "copy_code".equals(funValue)) {
						
						funValue = globalValue.get("copy_code").toString();
					}

					if ("prm_acct_year".equals(afs.getFun_para_code()) && "acct_year".equals(funValue)) {
						
						funValue = globalValue.get("acct_year").toString();
						
					}

					if ("prm_acct_month".equals(afs.getFun_para_code()) && "acct_month".equals(funValue)) {

						funValue = globalValue.get("acct_month").toString();

					}

					if ("prm_dept_kind_list".equals(afs.getFun_para_code())) {
						
						if ("dept_kind_code".equals(funValue)) {
							
							funValue = updateMap.get("dept_kind_code").toString();
							
						}else if(!String.valueOf(updateMap.get("dept_kind_code")).equals(funValue)){
							
							return 0.0;
						}
						
					}

					if ("prm_dept_list".equals(afs.getFun_para_code())) {
		
						if ("dept_id,dept_no".equals(funValue)) {
							
							funValue = updateMap.get("dept_id")+","+updateMap.get("dept_no");
							
						}else if(!String.valueOf(updateMap.get("dept_id")+","+updateMap.get("dept_no")).equals(funValue)){
							
							return -0.9999999999999;//返回此数值 代表该科室计算
						}

					}

					fun_method_eng = fun_method_eng.replaceAll(afs.getFun_para_code(), "'" + String.valueOf(funValue) + "'");

				}

				updateMap.put("funSql", fun_method_eng.substring(0, fun_method_eng.length()-1));
				
				//------------------------------------------------------------------------
				//处理错误消息
				HPM_ERRTXT = atm.getFun_code() + HPM_ERRTXT;
				//------------------------------------------------------------------------
				
				prmFunMapper.collectFun(updateMap);
				
				//------------------------------------------------------------------------
				//处理错误消息
				HPM_ERRTXT = HPM_ERRTXT + " 错误消息 ："+ updateMap.get("hpm_errtxt");
				//------------------------------------------------------------------------
				
				return Double.parseDouble(updateMap.get("hpm_result").toString());
				
			}

			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("指标编码为："+HPM_APPCODE+" "+ HPM_ERRTXT);

		}

	}
	private Double getFormulaValue(String target, String str,Map<String,Object> updateMap) throws DataAccessException {
		
		//------------------------------------------------------------------------
		//初始化错误信息
		
		HPM_APPCODE = target;
		
		HPM_ERRTXT = " 提取公式值错误！";
		
		//-------------------------------------------------------------------------

		PrmTargetMethod atm = targetMethodMap.get(target);
		
		try {

			String formula_code = null;double targetValue = 0.0;
			
			if(atm != null){formula_code = atm.getFormula_code();}else{formula_code = target;}

			PrmFormula af = formulaMap.get(formula_code);

			String formulaMethodEng = af.getFormula_method_eng();
			
			if(formulaMethodEng.indexOf("#{")!=-1){
				
				formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室编码\\}", updateMap.get("dept_code").toString());
				
				formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室名称\\}", updateMap.get("dept_name").toString());
				
				//formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室分类编码\\}",updateMap.get("dept_kind_code").toString());
				
				//formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室分类名称\\}", updateMap.get("dept_kind_name").toString());
				
			}

			ArrayList<String> list = formulaStackMap.get(formula_code);

			for (String key : list) {
				
				PrmTarget at = targetMap.get(key);
				
				if("01".equals(at.getTarget_nature()) && map01Value.get(key) != null){
					targetValue = map01Value.get(key);
				}else if(map01Value.get(key + str) != null) {
					targetValue = map01Value.get(key + str);
				}else{
					targetValue = 0.0;
				}

				formulaMethodEng = formulaMethodEng.replaceAll("\\{" + key + "\\}", "("+String.valueOf(targetValue)+")");
				
				targetValue = 0.0;
			}

			if (formulaMethodEng.contains("/0")) {

				formulaMethodEng = "0.0";

			}

			logger.debug("解析公式为："+formulaMethodEng);
			
			ExpressionCompiler compiler = new ExpressionCompiler(formulaMethodEng.replaceAll("(\r|\n|\t)", ""));
			
			CompiledExpression exp = compiler.compile();  
			
			Object formulaValue = MVEL.executeExpression(exp);  

			//Object formulaValue = MVEL.eval(formulaMethodEng.replaceAll("(\r|\n|\t|\\{)|\\}", ""));
			
			formula_code = null;formulaMethodEng=null;targetValue = 0.0;//初始化变量
			
			return Double.parseDouble(formulaValue.toString());

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"指标编码为：" +HPM_APPCODE +HPM_ERRTXT+"\"}");
		}

	}
		
	//计算数据 保存到map01Value 和对应的表中
	private void getTargetValue(Map<String,Object> updateMap){
		
		double targetValue = 0.0;
		
		if("02".equals(updateMap.get("method_code"))){

			if("03".equals(updateMap.get("target_nature"))){
				
				targetValue = getFormulaValue(updateMap.get("target_code").toString(),updateMap.get("dept_id").toString() + updateMap.get("dept_no"),updateMap);
				
			}
			
					
		}
		
		if("03".equals(updateMap.get("method_code"))){
			
			targetValue = getFunValue(updateMap.get("target_code").toString(),updateMap);
			
		}
		
		if(targetValue != -0.9999999999999){//返回此结果 不核算该数据
			
			if("01".equals(updateMap.get("target_nature"))){
				
				map01Value.put(updateMap.get("target_code").toString() , targetValue);
				
			}
			
			if("02".equals(updateMap.get("target_nature"))){
				
				map01Value.put(updateMap.get("target_code").toString() + updateMap.get("dept_kind_code"), targetValue);
				
			}
			
			if("03".equals(updateMap.get("target_nature"))){
				
				map01Value.put(updateMap.get("target_code").toString() + updateMap.get("dept_id") + updateMap.get("dept_no"), targetValue);
				
			}

			updateMap.put("target_value", targetValue);
			
			addTargetData(updateMap,updateMap.get("target_nature").toString());
		}
		
		
		targetValue = 0.0;//变量初始化
		
	}
	
	private void addTargetData(Map<String, Object> updateMap,String str) throws DataAccessException {

		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("group_id", globalValue.get("group_id"));map.put("hos_id", globalValue.get("hos_id"));
		
		map.put("copy_code", globalValue.get("copy_code"));map.put("acct_year", globalValue.get("acct_year"));
		
		map.put("acct_month", globalValue.get("acct_month"));map.put("is_audit", "1");map.put("user_code", "");
		
		map.put("audit_data", "");
		
		updateMap.putAll(map);
		
		
		if("03".equals(str)){
			
			prmDeptTargetDataMapper.deleteDeptTargetData(updateMap);

			prmDeptTargetDataMapper.addPrmDeptTargetData(updateMap);
		}
		
		updateMap = null;
	}
	
	@Override	
	public String collectDeptKpiValueCalculate(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			//初始化变量
			targetMethodMap = new HashMap<String, PrmTargetMethod>();
			
			map01Value = new HashMap<String, Double>();targetMap = new HashMap<String, PrmTarget>();
			
			formulaMap = new HashMap<String, PrmFormula>();funMap = new HashMap<String, PrmFun>();
			
			funStackMap = new HashMap<String, ArrayList<PrmFunStack>>();formulaStackMap = new HashMap<String, ArrayList<String>>();
			
			collectTargetList = new ArrayList<String>();globalValue=new HashMap<String,Object>();
			
			HPM_APPCODE = null ;HPM_ERRTXT = null ;//错误信息
			
		entityMap.put("sql", "");
		
		List<PrmDeptTargetDataCalculate> list01ValueTargetData = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);
		
		for(PrmDeptTargetDataCalculate pdtdc :list01ValueTargetData){
			
			map01Value.put(pdtdc.getTarget_code()+pdtdc.getDept_id()+pdtdc.getDept_no(), pdtdc.getTarget_value());
			
		}
		String year_month = (String) entityMap.get("acc_year");

		entityMap.put("acc_year", year_month.substring(0, 4));

		entityMap.put("acc_month", year_month.substring(4, 6));
		
		
			
		
		
		/*
		 * 0.提取所有固定值用于程序替换
		 */
		
		globalValue.put("group_id", entityMap.get("group_id"));globalValue.put("hos_id", entityMap.get("hos_id"));
		
		globalValue.put("copy_code", entityMap.get("copy_code"));globalValue.put("acc_year", entityMap.get("acc_year"));
		
		globalValue.put("acc_month", entityMap.get("acc_month"));
		
		/**
		 * 1. 查询是否有需要的计算
		 */
		
		
		
		entityMap.put("sql", " phs.method_Code <> '01' ");
		
		List<PrmDeptKpiValueCalculate> dataList = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);
		
			if (dataList.size() == 0) {
		
				return "{\"msg\":\"没有需要计算的数据 请生成.\",\"state\":\"true\"}";
		
			}
			
		entityMap.put("is_audit", "0");
			
		entityMap.put("sql", " phs.method_Code ='01' ");
	
		List<PrmDeptKpiValueCalculate> auditList = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);
	
			if (auditList.size() > 0) {
	
		return "{\"msg\":\"存在未审核的数据,请审核.\",\"state\":\"true\"}";

		}

		entityMap.put("is_audit", "");// 还原原变量
		
		/**
		 * 
		 * 3.提取取值方式为01的数据
		 * 
		 * */

		
		
		entityMap.put("sql", " phs.method_Code ='01' ");
		
		List<PrmDeptKpiValueCalculate> list01Value = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);

		for (PrmDeptKpiValueCalculate pdtdc : list01Value) {

			map01Value.put(pdtdc.getKpi_code() + pdtdc.getDept_id() + pdtdc.getDept_id(), pdtdc.getKpi_value());

		}
		
		/**
		 * 4.1 提取所有计算相关的数据
		 */
		
		// 4.2.1提取所有指标
		
		List<PrmTarget> targetList = prmTargetMapper.queryPrmTarget(entityMap);

		for (PrmTarget at : targetList) {targetMap.put(at.getTarget_code(), at);}
		
		// 4.2.2提取所有公式
		
		List<PrmFormula> formulaList = prmFormulaMapper.queryPrmFormula(entityMap);

		for (PrmFormula af : formulaList) {formulaMap.put(af.getFormula_code(), af);}

		// 4.2.3提取所有取值函数

		List<PrmFun> funList = prmFunMapper.queryPrmFun(entityMap);

		for (PrmFun af : funList) {funMap.put(af.getFun_code(), af);}
		
		
		// 4.2.4提取所有取值函数栈

		List<PrmFunStack> funStackList = prmFunStackMapper.queryPrmFunStack(entityMap);

		for (PrmFunStack afs : funStackList) {

			if (funStackMap.get(afs.getTarget_code()) != null) {

				ArrayList<PrmFunStack> list = funStackMap.get(afs.getTarget_code());

				list.add(afs);

				funStackMap.put(afs.getTarget_code(), list);

			} else {

				ArrayList<PrmFunStack> list = new ArrayList<PrmFunStack>();

				list.add(afs);

				funStackMap.put(afs.getTarget_code(), list);

			}
		}

		// 4.2.5提取所有取值函数栈
		List<PrmFormulaStack> formulaStackList = prmFormulaStackMapper.queryPrmFormulaStack(entityMap);

		for (PrmFormulaStack afs : formulaStackList) {

			if (formulaStackMap.get(afs.getFormula_code()) != null) {

				ArrayList<String> list = formulaStackMap.get(afs.getFormula_code());

				list.add(afs.getTarget_code());

				formulaStackMap.put(afs.getFormula_code(), list);

			} else {

				ArrayList<String> list = new ArrayList<String>();

				list.add(afs.getTarget_code());

				formulaStackMap.put(afs.getFormula_code(), list);

			}

		}

		// 4.2.6提取所有取值函数栈
		List<PrmTargetMethod> targetMethodList = prmTargetMethodMapper.queryPrmTargetMethod(entityMap);
		
		StringBuffer preTargetBuffer = new StringBuffer();

		for (PrmTargetMethod atm : targetMethodList) {
			
			targetMethodMap.put(atm.getTarget_code(), atm);
			
			if(!"03".equals(atm.getMethod_code())){continue;}

			//提取预执行指标值
			if (funMap.get(atm.getFun_code()) != null && funMap.get(atm.getFun_code()).getIs_pre() == 1) {
				
				getPreFunValue(atm.getTarget_code(),entityMap);//计算预执行指标 并插入数据库
				
				preTargetBuffer.append("'"+atm.getTarget_code()+"',");
			}
			
		}
		
		/**
		 * 4.2.6.1 如果存在预执行则提取预执行指标值
		 */
		if(preTargetBuffer.length() >0){
			
			entityMap.put("sql", " and a.target_code in ("+preTargetBuffer.substring(0, preTargetBuffer.toString().length()-1)+")");

			List<PrmDeptKindTargetData> deptKindPreList = prmDeptKindTargetDataMapper.getDeptKindTargetValueByTarget(entityMap);// 科室分类指标

			for (PrmDeptKindTargetData adktd : deptKindPreList) {map01Value.put(adktd.getTarget_code() + adktd.getDept_kind_code(), adktd.getTarget_value());}

			List<PrmDeptTargetData> deptPreList = prmDeptTargetDataMapper.getDeptTargetValueByTarget(entityMap);// 科室指标

			for (PrmDeptTargetData adtd : deptPreList) {map01Value.put(adtd.getTarget_code() + adtd.getDept_id() + adtd.getDept_no(), adtd.getTarget_value());}
			
		}
		
		/*
		 * 6. 递归根据公式入口递归提取公式使用的指标、科室分类数据、科室数据
		 */
		Map<String, String> deptBounsDataFormulaMap = new LinkedHashMap<String, String>();// 提取入口计算公式
		
		for (PrmDeptKpiValueCalculate adbd : dataList) {

			deptBounsDataFormulaMap.put(adbd.getFormula_code(), adbd.getFormula_code());

		}
		
		
		Map<String, PrmDeptKpiValueCalculate> deptBounsDataDeptMap = new HashMap<String, PrmDeptKpiValueCalculate>();// 提取当前科室中所有可是编码

		for (PrmDeptKpiValueCalculate adbd : dataList) {

			deptBounsDataDeptMap.put(adbd.getDept_id()+","+adbd.getDept_no(), adbd);

		}
		
		//----------------------------------基础数据整理完毕以下开始计算-----------------------------------------------------
		//递归公式入口 取对应的指标
		recursiveFormula(deptBounsDataFormulaMap);
		
		
		Collections.reverse(collectTargetList);
		
		// 计算指标
		for (String target_code : collectTargetList) {

			PrmTarget at = targetMap.get(target_code);Map<String,Object> updateMap = null;
			
			PrmTargetMethod atm = targetMethodMap.get(target_code);

			
			if("03".equals(at.getTarget_nature())){
				
				for (String key : deptBounsDataDeptMap.keySet()) {

					PrmDeptKpiValueCalculate adbd = deptBounsDataDeptMap.get(key);
					
					updateMap = new HashMap<String,Object>();updateMap.put("target_code", target_code);
					
					updateMap.put("method_code", atm.getMethod_code());updateMap.put("target_nature", at.getTarget_nature());

					updateMap.put("dept_id", key.split(",")[0]);updateMap.put("dept_no", key.split(",")[1]);
					
					updateMap.put("dept_code",adbd.getDept_code());updateMap.put("dept_name", adbd.getDept_name());
					
					getTargetValue(updateMap);
					
					updateMap = null;

				}

			}
		}
		/*
		 * 5. 递归根据公式入口递归提取公式使用的指标
		 */
		List<Map<String,Object>> batchUpdate = new ArrayList<Map<String,Object>>();
		
		for(PrmDeptKpiValueCalculate adbd :dataList){
			
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("group_id", adbd.getGroup_id());map.put("hos_id", adbd.getHos_id());
			
			map.put("copy_code", adbd.getCopy_code());map.put("acc_year", adbd.getAcc_year());
			
			map.put("acc_month", adbd.getAcc_month());
			
			map.put("dept_id", adbd.getDept_id());map.put("dept_no", adbd.getDept_no());
			
			map.put("dept_code",adbd.getDept_code());map.put("dept_name", adbd.getDept_name());
			
			map.put("goal_code", adbd.getGoal_code());
			
			map.put("kpi_code", adbd.getKpi_code());
			
			double targetValue = getFormulaValue(adbd.getFormula_code(),adbd.getDept_id()+adbd.getDept_no().toString(),map);
			
			map.put("kpi_value", targetValue);
			
			batchUpdate.add(map);
			
		}

		prmDeptKpiValueCalculateMapper.updateBatchPrmDeptKpiValueCalculate(batchUpdate);
		
		//初始化变量
		targetMethodMap = null;map01Value = null;targetMap = null;formulaMap = null;funMap = null;

		funStackMap = null;formulaStackMap = null;collectTargetList = null;globalValue=null;
		
		HPM_APPCODE = null ;HPM_ERRTXT = null ;
		
		return "{\"msg\":\"计算完成.\",\"state\":\"true\"}";
		
	} catch (Exception e) {

		logger.error(e.getMessage(), e);
		
		throw new SysException("{\"error\":\""+HPM_ERRTXT+"\"}");

	}
		
		
		
		
		
//		
//		//初始化变量
//		targetMap = new LinkedHashMap<String, String>();
//
//		formulaMap = new LinkedHashMap<String, String>();
//
//		formulaStackMap = new HashMap<String, ArrayList<String>>();
//
//		prmFormulaMap = new HashMap<String, PrmFormula>();
//
//		targetMethodMap = new HashMap<String, PrmTargetMethod>();
//
//		map01Value = new HashMap<String, Double>();//
//		
//		/**
//		 * 
//		 * 1.查询是否有需要计算的数据
//		 * 
//		 * */
//		
//		entityMap.put("sql", "");
//		
//		List<PrmDeptTargetDataCalculate> list01ValueTargetData = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);
//
//		for(PrmDeptTargetDataCalculate pdtdc :list01ValueTargetData){
//			
//			map01Value.put(pdtdc.getTarget_code()+pdtdc.getDept_id()+pdtdc.getDept_no(), pdtdc.getTarget_value());
//			
//		}
//		
//		String acc_year = entityMap.get("acc_year").toString();
//		
//		entityMap.put("acc_year", acc_year.substring(0, 4));
//		
//		entityMap.put("acc_month", acc_year.substring(4, 6));
//		
//		entityMap.put("sql", " phs.method_Code <> '01' ");
//
//		List<PrmDeptKpiValueCalculate> dataList = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);
//
//		if (dataList.size() == 0) {
//
//			return "{\"msg\":\"没有需要计算的数据 请生成.\",\"state\":\"true\"}";
//
//		}
//
//		/**
//		 * 
//		 * 2.查询01的指标值 是否审核并且维护完整
//		 * 
//		 * */
//
//		entityMap.put("is_audit", "0");
//
//		entityMap.put("sql", " phs.method_Code ='01' ");
//
//		List<PrmDeptKpiValueCalculate> auditList = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);
//
//		if (auditList.size() > 0) {
//
//			return "{\"msg\":\"存在未审核的数据,请审核.\",\"state\":\"true\"}";
//
//		}
//
//		entityMap.put("is_audit", "");// 还原原变量
//		
//		/**
//		 * 
//		 * 3.提取取值方式为01的数据
//		 * 
//		 * */
//
//		entityMap.put("sql", " phs.method_Code ='01' ");
//		
//		List<PrmDeptKpiValueCalculate> list01Value = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);
//
//		for (PrmDeptKpiValueCalculate pdtdc : list01Value) {
//
//			map01Value.put(pdtdc.getKpi_code() + pdtdc.getDept_id() + pdtdc.getDept_id(), pdtdc.getKpi_value());
//
//		}
//
//		/**
//		 * 
//		 * 4.查询公式栈 并组装公式栈
//		 * 
//		 * */
//
//		List<PrmFormulaStack> formulaStackList = prmFormulaStackMapper.queryPrmFormulaStack(entityMap);
//
//		for (PrmFormulaStack pfs : formulaStackList) {
//
//			String formula_code = pfs.getFormula_code();
//
//			if (formulaStackMap.get(formula_code) != null) {
//
//				ArrayList<String> al = formulaStackMap.get(formula_code);
//
//				al.add(pfs.getTarget_code());
//
//				formulaStackMap.put(formula_code, al);
//
//			} else {
//
//				ArrayList<String> al = new ArrayList<String>();
//
//				al.add(pfs.getTarget_code());
//
//				formulaStackMap.put(formula_code, al);
//			}
//
//		}
//		
//		/**
//		 * 
//		 * 5.1提取指标取值方法
//		 * 
//		 * */
//
//		List<PrmTargetMethod> prmTargetMethodList = prmTargetMethodMapper.queryPrmTargetMethod(entityMap);
//
//		for (PrmTargetMethod ptm : prmTargetMethodList) {
//
//			targetMethodMap.put(ptm.getTarget_code(), ptm);
//
//		}
//
//		/**
//		 * 
//		 * 5.2提取指标取值方法
//		 * 
//		 * */
//
//		List<PrmFormula> prmFormulaList = prmFormulaMapper.queryPrmFormula(entityMap);
//
//		for (PrmFormula pf : prmFormulaList) {
//
//			prmFormulaMap.put(pf.getFormula_code(), pf);
//
//		}
//		//
//
//		//entityMap.put("sql", " phs.method_Code <> '01' ");
//
//		//List<PrmDeptKpiValueCalculate> calculateList = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);
//
//		/**
//		 * 
//		 * 6.循环提取入口公式
//		 * 
//		 * */
//
//		StringBuffer nullFormula = new StringBuffer();// 计算为空 用来判断
//
//		for (PrmDeptKpiValueCalculate pdtdc : dataList) {
//
//			if ("02".equals(pdtdc.getMethod_code())) {
//
//				if (StringUtils.isNotEmpty(pdtdc.getFormula_code())) {
//
//					formulaMap.put(pdtdc.getKpi_code(), pdtdc.getFormula_code());
//
//				} else {
//
//					nullFormula.append(pdtdc.getKpi_code());
//
//				}
//
//			}
//
//			if ("03".equals(pdtdc.getMethod_code())) {
//
//				if (StringUtils.isNotEmpty(pdtdc.getFun_code())) {
//
//					targetMap.put(pdtdc.getKpi_code(), pdtdc.getKpi_code());
//
//				} else {
//
//					nullFormula.append(pdtdc.getKpi_code());
//
//				}
//
//			}
//
//		}
//
//		if (nullFormula.toString().length() > 0) {
//
//			return "{\"msg\":\"存在未设置的计算公式,请到取值方法维护中设置.\",\"state\":\"true\"}";
//
//		}
//
//		// 7.提取公式指标
//		recursiveFormula(formulaMap);
//
//		// 8.提取指标值
////		for (String key : sortMap(targetMap).keySet()) {
////
////			PrmTargetMethod ptm = targetMethodMap.get(key);//
////
////			if ("03".equals(ptm.getMethod_code())) {// 取值函数取值
////
////			}
////
////		}
//		
//		// 9.提取公式值
//		
//		for (PrmDeptKpiValueCalculate pdtdc : dataList) {
//
//			String dept = pdtdc.getDept_id().toString() + pdtdc.getDept_no();
//
//			String formulaMethodEng = get02FormulaEng(formulaMap.get(pdtdc.getKpi_code()), dept);
//
//			Object targetValue = MVEL.eval(formulaMethodEng);
//			
//			map01Value.put(pdtdc.getKpi_code() + pdtdc.getDept_id() + pdtdc.getDept_no(), Double.parseDouble(targetValue.toString()));
//
//		}
//
//		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
//
//		for (PrmDeptKpiValueCalculate pdtdc : dataList) {
//
//			Map<String, Object> map = new HashMap<String, Object>();
//
//			map.put("group_id", pdtdc.getGroup_id());map.put("hos_id", pdtdc.getHos_id());map.put("copy_code", pdtdc.getCopy_code());
//
//			map.put("acc_year", pdtdc.getAcc_year());map.put("acc_month", pdtdc.getAcc_month());
//
//			map.put("kpi_code", pdtdc.getKpi_code());
//
//			map.put("dept_no", pdtdc.getDept_no());map.put("dept_id", pdtdc.getDept_id());
//
//			Double kpi_value = map01Value.get(pdtdc.getKpi_code() + pdtdc.getDept_id() + pdtdc.getDept_no());
//
//			map.put("kpi_value", kpi_value);
//
//			updateList.add(map);
//
//		}
//
//		prmDeptKpiValueCalculateMapper.updateBatchPrmDeptKpiValueCalculate(updateList);
//
//		return "{\"msg\":\"计算完成.\",\"state\":\"true\"}";

	}

	public String get02FormulaEng(String formulaCode, String dept) throws DataAccessException {

		PrmFormula pf = prmFormulaMap.get(formulaCode);

		String formulaEng = pf.getFormula_method_eng();

		ArrayList<String> al = formulaStackMap.get(formulaCode);

		for (int i = 0; i < al.size(); i++) {

			PrmTargetMethod ptm = targetMethodMap.get(al.get(i));

			String targetValue = "0.0";
			
			if (map01Value.get(ptm.getTarget_code() + dept) != null) {

				targetValue = map01Value.get(ptm.getTarget_code() + dept).toString();

			}

			formulaEng = formulaEng.replaceAll("\\{" + ptm.getTarget_code() + "\\}", targetValue);

		}

		if (formulaEng.contains("/0")) {

			formulaEng = "0.0";

		}

		return formulaEng;

	}

	// 递归取指标 根据公式编码查询
	/*public void recursiveFormula(Map<String, String> map) throws DataAccessException {

		Map<String, String> reMap = new HashMap<String, String>();// 存放是否有需要递归的公式

		for (String key : map.keySet()) {

			for (String target_code : formulaStackMap.get(map.get(key))) {

				PrmTargetMethod ptm = targetMethodMap.get(target_code);// 根据公式编码查询

				if ("02".equals(ptm.getMethod_code())) {

					formulaMap.put(target_code, ptm.getFormula_code());

					reMap.put(target_code, ptm.getFormula_code());

				} else {

					targetMap.put(target_code, target_code);

				}

			}
		}

		if (reMap.size() > 0) {recursiveFormula(reMap);}// 如果存在计算公式 调用递归接着查找

	}*/

	private static LinkedHashMap<String, String> sortMap(LinkedHashMap<String, String> oldhMap) {

		LinkedHashMap<String, String> newMap = new LinkedHashMap<String, String>();

		ArrayList<String> arrayList = new ArrayList<String>();

		for (String key : oldhMap.keySet()) {

			arrayList.add(key);

		}

		Collections.reverse(arrayList);

		for (int i = 0; i < arrayList.size(); i++) {

			newMap.put(arrayList.get(i), oldhMap.get(arrayList.get(i)));

		}

		return newMap;
	}

	@Override
	public List<Map<String, Object>> queryPrmDeptKpiValueSchemeCalculatePrint( Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		
		entityMap.put("sql", "phs.method_code in ('02','03')");
		
		List<PrmDeptKpiValueCalculate> list = prmDeptKpiValueCalculateMapper.queryPrmDeptKpiValueSchemeCalculate(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}

}
