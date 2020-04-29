package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiDeptBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptKindMapper;
import com.chd.hrp.hpm.dao.AphiDeptKindTargetDataMapper;
import com.chd.hrp.hpm.dao.AphiDeptTargetDataMapper;
import com.chd.hrp.hpm.dao.AphiEmpTargetDataMapper;
import com.chd.hrp.hpm.dao.AphiFormulaMapper;
import com.chd.hrp.hpm.dao.AphiFormulaStackMapper;
import com.chd.hrp.hpm.dao.AphiFunMapper;
import com.chd.hrp.hpm.dao.AphiFunStackMapper;
import com.chd.hrp.hpm.dao.AphiHospTargetDataMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.dao.AphiSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiTargetMapper;
import com.chd.hrp.hpm.dao.AphiTargetMethodMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiDeptKindTargetData;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
import com.chd.hrp.hpm.entity.AphiEmpTargetData;
import com.chd.hrp.hpm.entity.AphiFormula;
import com.chd.hrp.hpm.entity.AphiFormulaStack;
import com.chd.hrp.hpm.entity.AphiFun;
import com.chd.hrp.hpm.entity.AphiFunStack;
import com.chd.hrp.hpm.entity.AphiHospTargetData;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.entity.AphiTargetMethod;
import com.chd.hrp.hpm.service.AphiDeptBonusDataService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptBonusDataService")
public class AphiDeptBonusDataServiceImpl implements AphiDeptBonusDataService {

	private static Logger logger = Logger.getLogger(AphiDeptBonusDataServiceImpl.class);

	@Resource(name = "aphiDeptBonusDataMapper")
	private final AphiDeptBonusDataMapper aphiDeptBonusDataMapper = null;

	@Resource(name = "aphiSchemeConfMapper")
	private final AphiSchemeConfMapper aphiSchemeConfMapper = null;

	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	@Resource(name = "aphiTargetMapper")
	private final AphiTargetMapper aphiTargetMapper = null;

	// ---------------------------------------------------------------------------------------------

	@Resource(name = "aphiHospTargetDataMapper")
	private final AphiHospTargetDataMapper aphiHospTargetDataMapper = null;

	@Resource(name = "aphiDeptTargetDataMapper")
	private final AphiDeptTargetDataMapper aphiDeptTargetDataMapper = null;

	@Resource(name = "aphiDeptKindTargetDataMapper")
	private final AphiDeptKindTargetDataMapper aphiDeptKindTargetDataMapper = null;

	@Resource(name = "aphiEmpTargetDataMapper")
	private final AphiEmpTargetDataMapper aphiEmpTargetDataMapper = null;

	@Resource(name = "aphiFunMapper")
	private final AphiFunMapper aphiFunMapper = null;

	@Resource(name = "aphiFunStackMapper")
	private final AphiFunStackMapper aphiFunStackMapper = null;

	@Resource(name = "aphiFormulaMapper")
	private final AphiFormulaMapper aphiFormulaMapper = null;

	@Resource(name = "aphiFormulaStackMapper")
	private final AphiFormulaStackMapper aphiFormulaStackMapper = null;

	@Resource(name = "aphiTargetMethodMapper")
	private final AphiTargetMethodMapper aphiTargetMethodMapper = null;
	
	@Resource(name = "aphiDeptKindMapper")
	private final AphiDeptKindMapper aphiDeptKindMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	
	// ------------------------------------变量区域---------------------------------------------------------
	
	private List<String> collectTargetList = null;

	private Map<String, Double> map01Value = null;

	private Map<String, AphiTarget> targetMap = null;

	private Map<String, AphiFormula> formulaMap = null;

	private Map<String, AphiFun> funMap = null;

	private Map<String, ArrayList<AphiFunStack>> funStackMap = null;

	private Map<String, ArrayList<String>> formulaStackMap = null;

	private Map<String, AphiTargetMethod> targetMethodMap = null;
	
	private Map<String,Object> globalValue = null;//存放全局变量 如所有的科室编码 名称 单位、集团、账套 等 计算开始的固定变量
	
	private static String HPM_APPCODE = null ;//执行代码
	
	private static String HPM_ERRTXT = null ;//错误信息

	// ---------------------------------------------------------------------------------------------

	@Override
	public String collectHpmDeptBonusData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("year_month");

		entityMap.put("acct_year", year_month.substring(0, 4));

		entityMap.put("acct_month", year_month.substring(4, 6));
		
		try {
			
			//初始化变量
			targetMethodMap = new HashMap<String, AphiTargetMethod>();

			map01Value = new HashMap<String, Double>();targetMap = new HashMap<String, AphiTarget>();

			formulaMap = new HashMap<String, AphiFormula>();funMap = new HashMap<String, AphiFun>();

			funStackMap = new HashMap<String, ArrayList<AphiFunStack>>();formulaStackMap = new HashMap<String, ArrayList<String>>();
			
			collectTargetList = new ArrayList<String>();globalValue=new HashMap<String,Object>();
			
			HPM_APPCODE = null ;HPM_ERRTXT = null ;//错误信息
			
			/*
			 * 0. 提取所有固定值用于程序替换
			 */
			
			globalValue.put("group_id", entityMap.get("group_id"));globalValue.put("hos_id", entityMap.get("hos_id"));
			
			globalValue.put("copy_code", entityMap.get("copy_code"));globalValue.put("acct_year", entityMap.get("acct_year"));
			
			globalValue.put("acct_month", entityMap.get("acct_month"));

			/**
			 * 1.根据年、月、单位、账套 查询方案序号
			 */

			AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
			
			if(sc == null){
				
				return "{\"warn\":\"当前期间没有核算方案.\",\"state\":\"false\"}";
				
			}

			entityMap.put("scheme_seq_no", sc.getScheme_seq_no());

			/**
			 * 2.查询是否已经发放或者审核
			 */

//			AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//			if (adba != null && adba.getIs_audit() == 1) {
//
//				return "{\"warn\":\"计算提示 ,当前状态已经审核 .\",\"state\":\"false\"}";
//
//			}
//
//			if (adba != null && adba.getIs_grant() == 1) {
//
//				return "{\"warn\":\"计算提示 ,当前状态已发放.\",\"state\":\"false\"}";
//
//			}

			/**
			 * 3. 根据年月提取需要计算的科室
			 */

			List<AphiDeptBonusData> deptBounsDataList = aphiDeptBonusDataMapper.queryDeptBonusData(entityMap);

			if (deptBounsDataList.size() == 0) {

				return "{\"warn\":\"计算提示.请生成当前月份下 需要计算的数据\",\"state\":\"true\"}";

			}

			/**
			 * 4.1提取手工录入数据
			 */
			List<AphiHospTargetData> hospList = aphiHospTargetDataMapper.getHospTargetValue(entityMap);// 院级指标

			for (AphiHospTargetData ahtd : hospList) {map01Value.put(ahtd.getTarget_code(), ahtd.getTarget_value());}

			
			List<AphiDeptKindTargetData> deptKindList = aphiDeptKindTargetDataMapper.getDeptKindTargetValue(entityMap);// 科室分类指标

			for (AphiDeptKindTargetData adktd : deptKindList) {map01Value.put(adktd.getTarget_code() + adktd.getDept_kind_code(), adktd.getTarget_value());}

			
			List<AphiDeptTargetData> deptList = aphiDeptTargetDataMapper.getDeptTargetValue(entityMap);// 科室指标

			for (AphiDeptTargetData adtd : deptList) {map01Value.put(adtd.getTarget_code() + adtd.getDept_id() + adtd.getDept_no(), adtd.getTarget_value());}

			
			List<AphiEmpTargetData> empList = aphiEmpTargetDataMapper.getEmpTargetValue(entityMap);// 员工指标

			for (AphiEmpTargetData aetd : empList) {map01Value.put(aetd.getTarget_code() + aetd.getEmp_id() + aetd.getEmp_no(), aetd.getTarget_value());}
			
			/**
			 * 4.2提取所有计算相关数据
			 */

			// 4.2.1提取所有指标
			List<AphiTarget> targetList = aphiTargetMapper.queryPrmTarget(entityMap);

			for (AphiTarget at : targetList) {targetMap.put(at.getTarget_code(), at);}

			// 4.2.2提取所有公式

			List<AphiFormula> formulaList = aphiFormulaMapper.queryPrmFormula(entityMap);

			for (AphiFormula af : formulaList) {formulaMap.put(af.getFormula_code(), af);}

			// 4.2.3提取所有取值函数

			List<AphiFun> funList = aphiFunMapper.queryPrmFun(entityMap);

			for (AphiFun af : funList) {funMap.put(af.getFun_code(), af);}

			// 4.2.4提取所有取值函数栈

			List<AphiFunStack> funStackList = aphiFunStackMapper.queryPrmFunStack(entityMap);

			for (AphiFunStack afs : funStackList) {

				if (funStackMap.get(afs.getTarget_code()) != null) {

					ArrayList<AphiFunStack> list = funStackMap.get(afs.getTarget_code());

					list.add(afs);

					funStackMap.put(afs.getTarget_code(), list);

				} else {

					ArrayList<AphiFunStack> list = new ArrayList<AphiFunStack>();

					list.add(afs);

					funStackMap.put(afs.getTarget_code(), list);

				}
			}

			// 4.2.5提取所有取值函数栈
			List<AphiFormulaStack> formulaStackList = aphiFormulaStackMapper.queryPrmFormulaStack(entityMap);

			for (AphiFormulaStack afs : formulaStackList) {

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
			List<AphiTargetMethod> targetMethodList = aphiTargetMethodMapper.queryPrmTargetMethod(entityMap);
			
			StringBuffer preTargetBuffer = new StringBuffer();

			for (AphiTargetMethod atm : targetMethodList) {
				
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

				List<AphiHospTargetData> hospPreList = aphiHospTargetDataMapper.getHospTargetValueByTarget(entityMap);// 院级指标

				for (AphiHospTargetData ahtd : hospPreList) {map01Value.put(ahtd.getTarget_code(), ahtd.getTarget_value());}

				List<AphiDeptKindTargetData> deptKindPreList = aphiDeptKindTargetDataMapper.getDeptKindTargetValueByTarget(entityMap);// 科室分类指标

				for (AphiDeptKindTargetData adktd : deptKindPreList) {map01Value.put(adktd.getTarget_code() + adktd.getDept_kind_code(), adktd.getTarget_value());}

				List<AphiDeptTargetData> deptPreList = aphiDeptTargetDataMapper.getDeptTargetValueByTarget(entityMap);// 科室指标

				for (AphiDeptTargetData adtd : deptPreList) {map01Value.put(adtd.getTarget_code() + adtd.getDept_id() + adtd.getDept_no(), adtd.getTarget_value());}
				
			}
			

			/*
			 * 6. 递归根据公式入口递归提取公式使用的指标、科室分类数据、科室数据
			 */
			Map<String, String> deptBounsDataFormulaMap = new LinkedHashMap<String, String>();// 提取入口计算公式

			for (AphiDeptBonusData adbd : deptBounsDataList) {

				deptBounsDataFormulaMap.put(adbd.getFormula_code(), adbd.getFormula_code());

			}
			
			Map<String, String> deptBounsDataDeptKindMap = new HashMap<String, String>();// 提取当前计算中所有可是分类编码

			for (AphiDeptBonusData adbd : deptBounsDataList) {

				deptBounsDataDeptKindMap.put(adbd.getDept_kind_code(), adbd.getDept_kind_name());

			}
			
			Map<String, AphiDeptBonusData> deptBounsDataDeptMap = new HashMap<String, AphiDeptBonusData>();// 提取当前科室中所有可是编码

			for (AphiDeptBonusData adbd : deptBounsDataList) {

				deptBounsDataDeptMap.put(adbd.getDept_id()+","+adbd.getDept_no(), adbd);

			}

			//----------------------------------基础数据整理完毕以下开始计算-----------------------------------------------------
			//递归公式入口 取对应的指标
			recursiveFormula(deptBounsDataFormulaMap);

//			//保存更改数据
//			List<Map<String,Object>> hospUpdateList = new ArrayList<Map<String,Object>>();
//			
//			List<Map<String,Object>> deptKindUpdateList = new ArrayList<Map<String,Object>>();
//			
//			List<Map<String,Object>> deptUpdateList = new ArrayList<Map<String,Object>>();
			
			Collections.reverse(collectTargetList);
			// 计算指标
			for (String target_code : collectTargetList) {

				AphiTarget at = targetMap.get(target_code);Map<String,Object> updateMap = null;
				
				AphiTargetMethod atm = targetMethodMap.get(target_code);

				if ("01".equals(at.getTarget_nature())) {
					
					updateMap = new HashMap<String,Object>();
					
					updateMap.put("method_code", atm.getMethod_code());updateMap.put("target_nature", at.getTarget_nature());
					
					updateMap.put("target_code", target_code);
					
					getTargetValue(updateMap);
					
					updateMap = null;
					
				}

				if ("02".equals(at.getTarget_nature())) {

					for (String key : deptBounsDataDeptKindMap.keySet()) {
						
						updateMap = new HashMap<String,Object>();updateMap.put("target_code", target_code);
						
						updateMap.put("method_code", atm.getMethod_code());updateMap.put("target_nature", at.getTarget_nature());
						
						updateMap.put("dept_kind_code", key);updateMap.put("dept_kind_name", deptBounsDataDeptKindMap.get(key));
						
						getTargetValue(updateMap);
						
						updateMap = null;	

					}

				}
				
				if("03".equals(at.getTarget_nature())){
					
					for (String key : deptBounsDataDeptMap.keySet()) {

						AphiDeptBonusData adbd = deptBounsDataDeptMap.get(key);
						
						updateMap = new HashMap<String,Object>();updateMap.put("target_code", target_code);
						
						updateMap.put("method_code", atm.getMethod_code());updateMap.put("target_nature", at.getTarget_nature());

						updateMap.put("dept_id", key.split(",")[0]);updateMap.put("dept_no", key.split(",")[1]);
						
						updateMap.put("dept_kind_code", adbd.getDept_kind_code());updateMap.put("dept_kind_name", adbd.getDept_kind_name());
						
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
			
			for(AphiDeptBonusData adbd :deptBounsDataList){
				
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", adbd.getGroup_id());map.put("hos_id", adbd.getHos_id());
				
				map.put("copy_code", adbd.getCopy_code());map.put("acct_year", adbd.getAcct_year());
				
				map.put("acct_month", adbd.getAcct_month());map.put("item_code", adbd.getItem_code());
				
				map.put("dept_id", adbd.getDept_id());map.put("dept_no", adbd.getDept_no());
				
				map.put("dept_kind_code", adbd.getDept_kind_code());map.put("dept_kind_name", adbd.getDept_kind_name());
				
				map.put("dept_code",adbd.getDept_code());map.put("dept_name", adbd.getDept_name());
				
				double targetValue = getFormulaValue(adbd.getFormula_code(),adbd.getDept_id()+adbd.getDept_no().toString(),map);
				
				map.put("bonus_money", targetValue);map.put("is_audit", 0);
				
				batchUpdate.add(map);
				
			}

			aphiDeptBonusDataMapper.updateBatchDeptBonusData(batchUpdate);
			
			//初始化变量
			targetMethodMap = null;map01Value = null;targetMap = null;formulaMap = null;funMap = null;

			funStackMap = null;formulaStackMap = null;collectTargetList = null;globalValue=null;
			
			HPM_APPCODE = null ;HPM_ERRTXT = null ;
			
			return "{\"msg\":\"计算完成.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\""+HPM_ERRTXT+"\"}");

		}

	}
	
	//计算数据 保存到map01Value 和对应的表中
	private void getTargetValue(Map<String,Object> updateMap){
		
		double targetValue = 0.0;
		
		if("02".equals(updateMap.get("method_code"))){
			
			if("01".equals(updateMap.get("target_nature"))){
				
				targetValue = getFormulaValue(updateMap.get("target_code").toString(),"",updateMap);
				
			}
			
			if("02".equals(updateMap.get("target_nature"))){
				
				targetValue = getFormulaValue(updateMap.get("target_code").toString(),updateMap.get("dept_kind_code").toString(),updateMap);
				
			}

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

	// 递归取指标 根据公式编码查询
	private void recursiveFormula(Map<String, String> map) throws DataAccessException {
		
		Map<String, String> targetMap = new HashMap<String, String>();

		// 解析公式 查出公式所用指标
		for (String key : map.keySet()) {
			
			logger.debug("正在提取指标"+key);
			if (key == null) {
				throw new SysException("提取指标失败，指标为空");
			}
			ArrayList<String> targetList = formulaStackMap.get(key);
			
			for (String target : targetList) {

				targetMap.put(target, target);

			}

		}

		Map<String, String> formulaMap = new LinkedHashMap<String, String>();
		
		// 解析指标所用的取值方法
		for (String key : targetMap.keySet()) {

			AphiTargetMethod tm = targetMethodMap.get(key);

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

			AphiTargetMethod tm = targetMethodMap.get(target);
			
			if (funMap.get(tm.getFun_code()) == null) {throw new SysException("{\"error\":\"指标 " + target +"未配置取值函数,请配置！\"}");}//如果指标取值方式为函数 但是没有找到该函数直接跳出
			
			AphiFun af = funMap.get(tm.getFun_code());
			
			if(af.getIs_pre() == 1){return;}//如果是预执行函数 跳出不需要解析

			ArrayList<AphiFunStack> list = funStackMap.get(target);

			for (AphiFunStack afs : list) {

				if (targetMap.get(afs.getFun_para_value()) == null) {continue;}

				AphiTargetMethod atm = targetMethodMap.get(afs.getFun_para_value());

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

	private Double getFormulaValue(String target, String str,Map<String,Object> updateMap) throws DataAccessException {
		
		//------------------------------------------------------------------------
		//初始化错误信息
		
		HPM_APPCODE = target;
		
		HPM_ERRTXT = " 提取公式值错误！";
		
		//-------------------------------------------------------------------------

		AphiTargetMethod atm = targetMethodMap.get(target);
		
		try {

			String formula_code = null;double targetValue = 0.0;
			
			if(atm != null){formula_code = atm.getFormula_code();}else{formula_code = target;}

			AphiFormula af = formulaMap.get(formula_code);

			String formulaMethodEng = af.getFormula_method_eng();
			
			if(formulaMethodEng.indexOf("#{")!=-1){
				
				formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室编码\\}", updateMap.get("dept_code").toString());
				
				formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室名称\\}", updateMap.get("dept_name").toString());
				
				formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室分类编码\\}",updateMap.get("dept_kind_code").toString());
				
				formulaMethodEng = formulaMethodEng.replaceAll("#\\{科室分类名称\\}", updateMap.get("dept_kind_name").toString());
				
			}

			ArrayList<String> list = formulaStackMap.get(formula_code);

			for (String key : list) {
				
				AphiTarget at = targetMap.get(key);
				
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

			if (formulaMethodEng.contains("/0") || formulaMethodEng.contains("/(0")) {

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

	@Transactional(rollbackFor = Exception.class)
	public Double getFunValue(String target,Map<String,Object> updateMap) throws DataAccessException {

		//------------------------------------------------------------------------
		//初始化错误信息
		
		HPM_APPCODE = target;
		
		HPM_ERRTXT = " 提取函数值错误！";
		
		//-------------------------------------------------------------------------
		
		AphiTargetMethod atm = targetMethodMap.get(target);
		
		if(atm == null){
			
			throw new SysException("指标："+target+" 未配置取值方法,请配置");
			
		}

		if (funMap.get(atm.getFun_code()) == null) {
			
			throw new SysException("指标："+target+" 调用函数编码为："+atm.getFun_code()+" 的函数未配置完整,请配置");

		}
		
		try{
			//如果是预执行函数 则已经计算过 直接提取值
			if(funMap.get(atm.getFun_code()).getIs_pre() == 1){
				
				AphiTarget at = targetMap.get(target);

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
				
				AphiFun af = funMap.get(atm.getFun_code());
				
				String fun_method_eng = af.getFun_method_eng();
				
				ArrayList<AphiFunStack> list = funStackMap.get(target);
				
				for (AphiFunStack afs : list) {

					String funValue = afs.getFun_para_value();

					if ("hpm_group_list".equals(afs.getFun_para_code()) && "group_id".equals(funValue)) {
						
						funValue = globalValue.get("group_id").toString();                                                                                  
						
					}

					if ("hpm_hos_list".equals(afs.getFun_para_code()) && "hos_id".equals(funValue)) {
						
						funValue = globalValue.get("hos_id").toString();
						
					}

					if ("hpm_copy_list".equals(afs.getFun_para_code()) && "copy_code".equals(funValue)) {
						
						funValue = globalValue.get("copy_code").toString();
					}

					if ("hpm_acct_year".equals(afs.getFun_para_code()) && "acct_year".equals(funValue)) {
						
						funValue = globalValue.get("acct_year").toString();
						
					}

					if ("hpm_acct_month".equals(afs.getFun_para_code()) && "acct_month".equals(funValue)) {

						funValue = globalValue.get("acct_month").toString();

					}

					if ("hpm_dept_kind_list".equals(afs.getFun_para_code())) {
						
						if ("dept_kind_code".equals(funValue)) {
							
							funValue = updateMap.get("dept_kind_code").toString();
							
						}else if(!String.valueOf(updateMap.get("dept_kind_code")).equals(funValue)){
							
							return 0.0;
						}
						
					}

					if ("hpm_dept_list".equals(afs.getFun_para_code())) {
		
						if ("dept_id,dept_no".equals(funValue)) {
							
							funValue = updateMap.get("dept_id")+","+updateMap.get("dept_no");
							
						}else if(!String.valueOf(updateMap.get("dept_id")+","+updateMap.get("dept_no")).equals(funValue)){
							
							return -0.9999999999999;//返回此数值 代表该科室计算
						}

					}
					
//					if(globalValue.get(funValue) != null){
//						
//						funValue = globalValue.get(funValue).toString();
//						
//					}
//					
//					if ("hpm_dept_kind_list".equals(afs.getFun_para_code())) {
//						
//						if("dept_kind_code".equals(funValue)){
//							
//							funValue = updateMap.get("dept_kind_code").toString();
//							
//						}else{return 0.0;}
//						
//						
//					}
//
//					if ("hpm_dept_list".equals(afs.getFun_para_code())) {
//		
//						if("dept_id,dept_no".equals(funValue)){
//							
//							funValue = updateMap.get("dept_id")+","+updateMap.get("dept_no");
//							
//						}else{return 0.0;}
//
//					}

					fun_method_eng = fun_method_eng.replaceAll(afs.getFun_para_code(), "'" + String.valueOf(funValue) + "'");

				}

				updateMap.put("funSql", fun_method_eng.substring(0, fun_method_eng.length()-1));
				
				//------------------------------------------------------------------------
				//处理错误消息
				HPM_ERRTXT = atm.getFun_code() + HPM_ERRTXT;
				//------------------------------------------------------------------------
				
				aphiFunMapper.collectFun(updateMap);
				
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
	
	@Transactional(rollbackFor = Exception.class)
	private void getPreFunValue(String target,Map<String,Object> updateMap) throws DataAccessException {

		//------------------------------------------------------------------------
		//初始化错误信息
		
		HPM_APPCODE = target;
		
		HPM_ERRTXT = "提取预执行指标错误！";
		
		//-------------------------------------------------------------------------
		
		AphiTargetMethod atm = targetMethodMap.get(target);
		
		try{

			AphiFun af = funMap.get(atm.getFun_code());
			
			String fun_method_eng = af.getFun_method_eng();
			
			ArrayList<AphiFunStack> list = funStackMap.get(target);
			
			for (AphiFunStack afs : list) {

				String funValue = afs.getFun_para_value();

				if ("hpm_group_list".equals(afs.getFun_para_code()) && "group_id".equals(funValue)) {
					
					funValue = globalValue.get("group_id").toString();
					
				}

				if ("hpm_hos_list".equals(afs.getFun_para_code()) && "hos_id".equals(funValue)) {
					
					funValue = globalValue.get("hos_id").toString();
					
				}

				if ("hpm_copy_list".equals(afs.getFun_para_code()) && "copy_code".equals(funValue)) {
					
					funValue = globalValue.get("copy_code").toString();
				}

				if ("hpm_acct_year".equals(afs.getFun_para_code()) && "acct_year".equals(funValue)) {
					
					funValue = globalValue.get("acct_year").toString();
					
				}

				if ("hpm_acct_month".equals(afs.getFun_para_code()) && "acct_month".equals(funValue)) {

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
			
			aphiFunMapper.collectPro(updateMap);
			
			//------------------------------------------------------------------------
			//处理错误消息
			HPM_ERRTXT = HPM_ERRTXT + " 错误消息 ："+ updateMap.get("hpm_errtxt");
			//------------------------------------------------------------------------

		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("指标编码为："+HPM_APPCODE+" "+  HPM_ERRTXT);

		}

	}
	
	private void addTargetData(Map<String, Object> updateMap,String str) throws DataAccessException {

		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("group_id", globalValue.get("group_id"));map.put("hos_id", globalValue.get("hos_id"));
		
		map.put("copy_code", globalValue.get("copy_code"));map.put("acct_year", globalValue.get("acct_year"));
		
		map.put("acct_month", globalValue.get("acct_month"));map.put("is_audit", "1");map.put("user_code", "");
		
		map.put("audit_time", "");
		
		updateMap.putAll(map);
		
		if("01".equals(str)){
			
			aphiHospTargetDataMapper.deleteHospTargetData(updateMap);

			aphiHospTargetDataMapper.addHospTargetData(updateMap);
		}
		
		if("02".equals(str)){
			
			aphiDeptKindTargetDataMapper.deleteDeptKindTargetData(updateMap);

			aphiDeptKindTargetDataMapper.addDeptKindTargetData(updateMap);
		}

		if("03".equals(str)){
			
			aphiDeptTargetDataMapper.deleteDeptTargetData(updateMap);

			aphiDeptTargetDataMapper.addDeptTargetData(updateMap);
		}
		
		updateMap = null;
	}

//	public void addTargetData(Map<String, List<Map<String, Object>>> map, Map<String, Object> entityMap) throws DataAccessException {
//
//		List<Map<String, Object>> hospUpdateList = map.get("hospUpdateList");
//
//		List<Map<String, Object>> deptKindUpdateList = map.get("deptKindUpdateList");
//
//		List<Map<String, Object>> deptUpdateList = map.get("deptUpdateList");
//
//		List<Map<String, Object>> empUpdateList = map.get("empUpdateList");
//
//		if (hospUpdateList.size() > 0) {
//
//			StringBuffer del01Sql = new StringBuffer();
//			del01Sql.append(" and target_code in (");
//
//			for (Map<String, Object> map01 : hospUpdateList) {
//
//				if (del01Sql.indexOf(map01.get("target_code").toString()) == -1) {
//
//					del01Sql.append("'" + map01.get("target_code") + "',");
//
//				}
//
//			}
//
//			del01Sql.setCharAt(del01Sql.length() - 1, ')');
//
//			entityMap.put("delSql", del01Sql);
//
//			aphiHospTargetDataMapper.deleteHospTargetData(entityMap);
//
//			aphiHospTargetDataMapper.addBatchHospTargetData(hospUpdateList);
//
//		}
//
//		if (deptKindUpdateList.size() > 0) {
//
//			StringBuffer del02Sql = new StringBuffer();
//			del02Sql.append(" and target_code in (");
//
//			for (Map<String, Object> map02 : deptKindUpdateList) {
//
//				if (del02Sql.indexOf(map02.get("target_code").toString()) == -1) {
//
//					del02Sql.append("'" + map02.get("target_code") + "',");
//
//				}
//
//			}
//
//			del02Sql.setCharAt(del02Sql.length() - 1, ')');
//
//			entityMap.put("delSql", del02Sql);
//
//			aphiDeptKindTargetDataMapper.deleteDeptKindTargetData(entityMap);
//
//			aphiDeptKindTargetDataMapper.addBatchDeptKindTargetData(deptKindUpdateList);
//		}
//
//		if (deptUpdateList.size() > 0) {
//
//			StringBuffer del03Sql = new StringBuffer();
//			del03Sql.append(" and target_code in (");
//
//			for (Map<String, Object> map03 : deptUpdateList) {
//
//				if (del03Sql.indexOf(map03.get("target_code").toString()) == -1) {
//
//					del03Sql.append("'" + map03.get("target_code") + "',");
//
//				}
//
//			}
//
//			del03Sql.setCharAt(del03Sql.length() - 1, ')');
//
//			entityMap.put("delSql", del03Sql);
//			
//			if (deptUpdateList.size() > 500) {
//
//				List<Map<String, Object>> batchUpateList = new ArrayList<Map<String, Object>>();
//
//				for (int i = 0; i < deptUpdateList.size(); i++) {
//
//					batchUpateList.add(deptUpdateList.get(i));
//
//					if (i !=0 && i % 500 == 0) {
//						
//						aphiDeptTargetDataMapper.deleteBatchDeptTargetData(batchUpateList);
//
//						aphiDeptTargetDataMapper.addBatchDeptTargetData(batchUpateList);
//
//						batchUpateList.removeAll(batchUpateList);
//					}
//
//				}
//				aphiDeptTargetDataMapper.deleteBatchDeptTargetData(batchUpateList);
//				
//				aphiDeptTargetDataMapper.addBatchDeptTargetData(batchUpateList);
//
//			}else{
//				
//				aphiDeptTargetDataMapper.deleteBatchDeptTargetData(deptUpdateList);
//				
//				aphiDeptTargetDataMapper.addBatchDeptTargetData(deptUpdateList);
//				
//			}
//
//		}
//
//	}


	public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
		
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				
				return (o1.getValue()).compareTo(o2.getValue());
				
			}
			
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		
		for (Map.Entry<K, V> entry : list) {
			
			result.put(entry.getKey(), entry.getValue());
			
		}
		
		return result;
	}

	/**
	 * 
	 */
	@Override
	public String queryHpmDeptBonusData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<AphiItem> itemList = getGridTitleMap(entityMap);

		if (itemList.size() == 0) {

			return "{\"error\":\"没有查询到核算的项目.\",\"state\":\"false\"}";

		}

		StringBuffer sql = new StringBuffer();

		StringBuffer sql_sum = new StringBuffer();

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql.append("sum(nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)) as  item_code" + item.getItem_code().toLowerCase() + ",");
			
			if("1".equals(String.valueOf(item.getIs_sum()))){
				
				sql_sum.append("nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)+");
			}
		}

		entityMap.put("sql", sql.toString());
		
		if(sql_sum.length() > 0){
			
			entityMap.put("sql_sum", "sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,");
		}


		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryDeptBonusForBonusMoney(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryDeptBonusForBonusMoney(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}

	/**
	 * 
	 */
	@Override
	public AphiDeptBonusData queryHpmDeptBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", year_month.substring(0, 4));

		entityMap.put("acct_month", year_month.substring(4, 6));

		// 1.根据年月查询方案序号
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);

		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());
		
		return aphiDeptBonusDataMapper.queryHpmDeptBonusDataByCode(entityMap);
	}

	@Override
	public String initHpmDeptBonusData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", year_month.substring(0, 4));

		entityMap.put("acct_month", year_month.substring(4, 6));

		try {

			// 1.根据年月查询方案序号
			AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);

			if(sc == null){
				
				return "{\"msg\":\"当前期间没有核算方案.\",\"state\":\"false\"}";
				
			}
			
			entityMap.put("scheme_seq_no", sc.getScheme_seq_no());

			// 2.查询是否已经发放或者审核

//			AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//			if (adba != null && adba.getIs_audit() == 1) {
//
//				return "{\"error\":\"生成失败 ,前状态已经审核.\",\"state\":\"false\"}";
//
//			}
//
//			if (adba != null && adba.getIs_grant() == 1) {
//
//				return "{\"error\":\"生成失败 ,前状态已发放.\",\"state\":\"false\"}";
//
//			}

			// 3.查询数据

			List<AphiDeptBonusData> deptList = aphiDeptBonusDataMapper.queryDeptSchemeSeqForDeptBonusData(entityMap);

			if (deptList.size() > 0) {

				aphiDeptBonusDataMapper.deleteDeptBonusData(entityMap);

				aphiDeptBonusDataMapper.addBatchDeptBonusData(deptList);

				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}

	}

	public List<AphiItem> getGridTitleMap(Map<String, Object> map) throws DataAccessException {

		//List<AphiItem> itemList = aphiItemMapper.qeuryItemMap(map);
		if (map.get("user_id") == null) {
			map.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptBonusDataMapper.queryAphiDeptSchemeItem(map);
		
		/*if (itemList.size() > 0) {

			return itemList;

		}
		return aphiItemMapper.qeuryItemMapGrid(map);*/
	}

	public String queryHpmDeptBonusDataGrid(Map<String, Object> entityMap) throws DataAccessException {

		List<AphiItem> queryItemDict = getGridTitleMap(entityMap);

		StringBuffer columns = new StringBuffer();

		columns.append("[");

		columns.append("{ display: \'核算年月\', name: \'acct_year\', align: \'left\',width:120," + "render: function (rowdata , rowindex , value){"
				+ "return 	rowdata.acct_year+rowdata.acct_month;" + "}" + "},");

		columns.append("{ display: \'科室编码\', name: \'dept_code\', align: \'left\',width:120},");

		columns.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:180},");

		for (int i = 0; i < queryItemDict.size(); i++) {

			AphiItem item = queryItemDict.get(i);

			columns.append("{display : \'" + item.getItem_name() + "\',name : \'item_code" + item.getItem_code().toLowerCase()
					+ "\',align : \'right\',formatter:\'###,##0.00\',width:160,totalSummary:{type: \'sum\'," + "render: function (suminf, column, cell){ "
					+ "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';" + "}" + "},"
					+ "render: function (rowdata, rowindex,value,col){" 
					+ "return \'<a href=\\'#\\' onclick=openFormula(\\'' + rowdata.dept_id + '\\',\\'' + rowdata.dept_no + '\\',\\'"+item.getItem_code()+"\\');>\'+formatNumber(rowdata[col.name] ==null ? 0 : rowdata[col.name],2,1);+\'</a>\'"
					+ "}"
					+ "},");

		}
		//
		//return '<a href=\'#\' onclick=openUpdate(\''+rowdata.acct_year+'\',\''+rowdata.acct_month+'\');>'+rowdata.acct_year+'</a>';
		columns.append("{ display: \'合计\', name: \'sum_money\', align: \'right\',width:160,formatter:\'###,##0.00\',totalSummary:{type: \'sum\',"
				+ "render: function (suminf, column, cell){ " + "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';" + "}"
				+ "}," + "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,2,1);" + "}"
				+ "}");

		columns.append("]");

		return columns.toString();
	}

	public String queryHpmDeptBonusDataForTargetGrid(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", year_month.substring(0, 4));

		entityMap.put("acct_month", year_month.substring(4, 6));

		// 1.根据年月查询方案序号
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		
		if(sc == null){
			
			return "{\"msg\":\"当前期间没有核算方案.\",\"state\":\"false\"}";
			
		}

		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());

		StringBuilder json = new StringBuilder();

		List<AphiDeptBonusData> queryTargetDict = aphiDeptBonusDataMapper.queryHpmDeptBonusDataForTargetGrid(entityMap);

		StringBuffer columns = new StringBuffer();

		Integer size = queryTargetDict.size();

		columns.append("[");

		columns.append("{ display: \'核算年月\', name: \'acct_year\', align: \'left\',width:120," + "render: function (rowdata , rowindex , value){"
				+ "return 	rowdata.acct_year+rowdata.acct_month;" + "}" + "},");

		columns.append("{ display: \'科室编码\', name: \'dept_code\', align: \'left\',width:120},");

		columns.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:180},");

		for (int i = 0; i < size; i++) {

			AphiDeptBonusData dbd = queryTargetDict.get(i);

			columns.append("{display : \'" + dbd.getTarget_name() + "\',name : \'target_code" + dbd.getTarget_code()
					+ "\',align : \'right\',width:160,totalSummary:{type: \'sum\'," + "render: function (suminf, column, cell){ "
					+ "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';" + "}" + "},"
					+ "render: function (rowdata, rowindex,value,col){" + "return formatNumber(rowdata[col.name] ==null ? 0 : rowdata[col.name],2,1);" + "}"
					+ "},");

		}

		columns.append("{ display: \'奖金额\', name: \'bonus_money\', align: \'right\',width:160,totalSummary:{type: \'sum\',"
				+ "render: function (suminf, column, cell){ " + "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}},"
				+ "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata.bonus_money ==null ? 0 : rowdata.bonus_money,2,1);" + "}"
				+ "}]");

		return columns.toString();
	}

	@Override
	public String queryHpmDeptBonusDataForTarget(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", year_month.substring(0, 4));

		entityMap.put("acct_month", year_month.substring(4, 6));

		// 1.根据年月查询方案序号
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		
		if(sc == null){
			
			return "{\"msg\":\"当前期间没有核算方案.\",\"state\":\"false\"}";
			
		}

		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());

		List<AphiDeptBonusData> queryTargetDict = aphiDeptBonusDataMapper.queryHpmDeptBonusDataForTargetGrid(entityMap);

		if (queryTargetDict.size() > 0) {

			StringBuffer sql_case = new StringBuffer();

			for (int i = 0; i < queryTargetDict.size(); i++) {

				AphiDeptBonusData target = (AphiDeptBonusData) queryTargetDict.get(i);

				sql_case.append("sum(isnull((case when tv.target_code = '" + target.getTarget_code() + "' then tv.target_value end),0)) as  target_code"
						+ target.getTarget_code() + ",");

			}

			entityMap.put("sql_case", sql_case);

		}

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryHpmDeptBonusDataForTarget(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryHpmDeptBonusDataForTarget(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryHpmDeptBonusDataForFormula(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			String year_month = (String) entityMap.get("acct_yearm");

			entityMap.put("acct_year", year_month.substring(0, 4));

			entityMap.put("acct_month", year_month.substring(4, 6));

			List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryHpmDeptBonusDataForFormula(entityMap);

			List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();

			if (list.size() > 0) {

				for (Map<String, Object> map : list) {

					Map viewMap = new HashMap();

					for (String key : map.keySet()) {

						if ("method_name".equals(key.toLowerCase())) {

							String formula_method_chs = String.valueOf(map.get(key));
							formula_method_chs = formula_method_chs.replaceAll("(\r\n|\r|\n|\n\r)", "");

							viewMap.put(key.toLowerCase(), formula_method_chs);

						} else {

							viewMap.put(key.toLowerCase(), map.get(key));

						}

					}

					viewList.add(viewMap);

				}

			}
			return ChdJson.toJson(viewList);
		} catch (Exception e) {
			// TODO: handle exception

			logger.error(e.getMessage(), e);

			throw new SysException("操作失败");
		}

	}

	@Override
	public List<Map<String, Object>> queryHpmDeptBonusDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if (entityMap.get("acct_yearm") != null && !"".equals(entityMap.get("acct_yearm"))) {

			entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));
			entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4,entityMap.get("acct_yearm").toString().length()));
		}
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		
		List<AphiItem> itemList = getGridTitleMap(entityMap);

		StringBuffer sql = new StringBuffer();
		StringBuffer sql_sum = new StringBuffer();

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql.append("sum(nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)) as  item_code" + item.getItem_code().toLowerCase() + ",");
			
			if("1".equals(String.valueOf(item.getIs_sum()))){
				
				sql_sum.append("nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)+");
			}
		}

		entityMap.put("sql", sql.toString());
		
		if(sql_sum.length() > 0){
			entityMap.put("sql_sum", "sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,");
		}

		List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryDeptBonusForBonusMoneyPrint(entityMap);

		return list;
	}

}
