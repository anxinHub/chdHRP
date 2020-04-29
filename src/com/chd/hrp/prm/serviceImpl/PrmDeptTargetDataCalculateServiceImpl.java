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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mvel2.MVEL;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmDeptDictMapper;
import com.chd.hrp.prm.dao.PrmDeptTargetDataCalculateMapper;
import com.chd.hrp.prm.dao.PrmFormulaMapper;
import com.chd.hrp.prm.dao.PrmFormulaStackMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.dao.PrmTargetMethodMapper;
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.entity.PrmDeptTargetDataCalculate;
import com.chd.hrp.prm.entity.PrmFormula;
import com.chd.hrp.prm.entity.PrmFormulaStack;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.entity.PrmTargetMethod;
import com.chd.hrp.prm.service.PrmDeptTargetDataCalculateService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0312 科室绩效指标数据表
 * @Table: PRM_DEPT_TARGET_DATA
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptTargetDataCalculateService")
public class PrmDeptTargetDataCalculateServiceImpl implements PrmDeptTargetDataCalculateService {

	private static Logger logger = Logger.getLogger(PrmDeptTargetDataCalculateServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptTargetDataCalculateMapper")
	private final PrmDeptTargetDataCalculateMapper prmDeptTargetDataCalculateMapper = null;

	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper prmTargetMapper = null;

	@Resource(name = "prmDeptDictMapper")
	private final PrmDeptDictMapper prmDeptDictMapper = null;
	
	@Resource(name = "prmFormulaStackMapper")
	private final PrmFormulaStackMapper prmFormulaStackMapper = null;
	
	@Resource(name = "prmFormulaMapper")
	private final PrmFormulaMapper prmFormulaMapper = null;
	
	@Resource(name = "prmTargetMethodMapper")
	private final PrmTargetMethodMapper prmTargetMethodMapper = null;
	
	private LinkedHashMap<String,String> targetMap = new LinkedHashMap<String,String>();

	private LinkedHashMap<String,String> formulaMap = new LinkedHashMap<String,String>();
	
	private Map<String, ArrayList<String>> formulaStackMap = new HashMap<String, ArrayList<String>>();
	
	private Map<String, PrmFormula> prmFormulaMap = new HashMap<String, PrmFormula>();
	
	private Map<String, PrmTargetMethod> targetMethodMap = new HashMap<String, PrmTargetMethod>();
	
	Map<String, Double> map01Value = new HashMap<String, Double>();//

	/**
	 * @Description 添加0312 科室绩效指标数据表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0312 科室绩效指标数据表
		PrmDeptTargetData prmDeptTargetData = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetDataByCode(entityMap);

		if (prmDeptTargetData != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptTargetDataCalculateMapper.addPrmDeptTargetData(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptTargetData\"}";

		}

	}

	@Override
	public String queryPrmDeptTargetPrmTargetDataCalculate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		entityMap.put("sql", " ptm.Method_Code !='01' ");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptTargetDataCalculate> list = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptTargetDataCalculate> list = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String createPrmDeptTargetDataCalculate(Map<String, Object> entityMap, String paramVo) throws DataAccessException {
		// TODO Auto-generated method stub

		try {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}

			mapVo.put("acc_year", paramVo.substring(0, 4));
			mapVo.put("acc_month", paramVo.substring(4, 6));
			

			
			entityMap.put("target_nature", "03");// 03科室
			List<PrmTarget> prmTargetList = prmTargetMapper.queryPrmTargetNatureCreateCalculate(entityMap);
			
			if(prmTargetList.size() == 0){
				
				return "{\"msg\":\"未找到指标!\"}";
			}
			
			prmDeptTargetDataCalculateMapper.cleanPrmDeptTargetDataCalculate(mapVo);


			entityMap.put("is_stop", 0);

			List<PrmDeptDict> aphiDeptDict = prmDeptDictMapper.queryPrmDeptDict(entityMap);

			for (PrmDeptDict AphiDeptDict2 : aphiDeptDict) {

					for (int i = 0; i < prmTargetList.size(); i++) {

						mapVo.put("target_code", prmTargetList.get(i).getTarget_code());
						mapVo.put("dept_id", AphiDeptDict2.getDept_id());
						mapVo.put("dept_no", AphiDeptDict2.getDept_no());
						mapVo.put("target_value", 0);
						mapVo.put("is_audit", 0);
						mapVo.put("user_code", "");
						mapVo.put("audit_date", "");
						
						prmDeptTargetDataCalculateMapper.addPrmDeptTargetData(mapVo);

					}
			}
			
			return "{\"msg\":\"科室指标数据生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"科室指标数据生成失败!\"}");
		}
	}

	@Override
	public String updateBatchPrmDeptTargetDataCalculate(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			prmDeptTargetDataCalculateMapper.updateBatchPrmDeptTargetDataCalculate(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptTargetDataCalculate\"}";

		}

	}

	@Override
	public String collectPrmDeptTargetDataCalculate(Map<String, Object> entityMap) throws DataAccessException {
		
		targetMap = new LinkedHashMap<String, String>();

		formulaMap = new LinkedHashMap<String, String>();

		formulaStackMap = new HashMap<String, ArrayList<String>>();

		prmFormulaMap = new HashMap<String, PrmFormula>();

		targetMethodMap = new HashMap<String, PrmTargetMethod>();
		//1.查询是否有需要计算的数据
		
		List<PrmDeptTargetDataCalculate> dataList = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);
		
		if(dataList.size() == 0){
			
			return "{\"msg\":\"没有需要计算的数据 请生成.\",\"state\":\"true\"}";
			
		}
		
		//2.查询01的指标值 是否审核并且维护完整
		entityMap.put("is_audit", "0");
		
		entityMap.put("sql", " ptm.method_Code ='01' ");
		
		List<PrmDeptTargetDataCalculate> auditList = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);
		
		if(auditList.size() > 0){
			
			return "{\"msg\":\"存在未审核的数据,请审核.\",\"state\":\"true\"}";
			
		}
		
		entityMap.put("is_audit", "");//还原原变量
		
		//3.提取取值方式为01的数据
		
		List<PrmDeptTargetDataCalculate> list01Value = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);

		for(PrmDeptTargetDataCalculate pdtdc :list01Value){
			
			map01Value.put(pdtdc.getTarget_code()+pdtdc.getDept_id()+pdtdc.getDept_id(), pdtdc.getTarget_value());
			
		}
		
		//4.查询公式栈 并组装公式栈
		List<PrmFormulaStack> formulaStackList = prmFormulaStackMapper.queryPrmFormulaStack(entityMap);
		
		for(PrmFormulaStack pfs : formulaStackList){
			
			String formula_code = pfs.getFormula_code();
			
			if(formulaStackMap.get(formula_code) != null){
				
				ArrayList<String> al = formulaStackMap.get(formula_code);
				
				al.add(pfs.getTarget_code());
				
				formulaStackMap.put(formula_code, al);
				
			}else{
				
				ArrayList<String> al = new ArrayList<String>();
				
				al.add(pfs.getTarget_code());
				
				formulaStackMap.put(formula_code, al);
			}

		}
		
		//5.1提取指标取值方法
		
		List<PrmTargetMethod> prmTargetMethodList = prmTargetMethodMapper.queryPrmTargetMethod(entityMap);
		
		for(PrmTargetMethod ptm : prmTargetMethodList){
			
			targetMethodMap.put(ptm.getTarget_code(), ptm);
			
		}
		
		//5.1提取指标取值方法

		List<PrmFormula> prmFormulaList = prmFormulaMapper.queryPrmFormula(entityMap);

		for (PrmFormula pf : prmFormulaList) {

			prmFormulaMap.put(pf.getFormula_code(), pf);

		}
		//
		
		entityMap.put("sql", " ptm.method_Code <> '01' ");
		
		List<PrmDeptTargetDataCalculate> calculateList = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);
		
		//6.循环提取入口公式
		
		StringBuffer nullFormula = new StringBuffer();//计算为空 用来判断
		
		for(PrmDeptTargetDataCalculate pdtdc : calculateList){
			
			if("02".equals(pdtdc.getMethod_code())){
				
				if(StringUtils.isNotEmpty(pdtdc.getFormula_code())){

					formulaMap.put(pdtdc.getTarget_code(), pdtdc.getFormula_code());
					
				}else{
					
					nullFormula.append(pdtdc.getTarget_code());
					
				}

			}
			
			if("03".equals(pdtdc.getMethod_code())){
				
				if(StringUtils.isNotEmpty(pdtdc.getFun_code())){

					targetMap.put(pdtdc.getTarget_code(), pdtdc.getTarget_code());
					
				}else{
					
					nullFormula.append(pdtdc.getTarget_code());
					
				}

			}
			
		}
		
		if(nullFormula.toString().length() > 0){
			
			return "{\"msg\":\"存在未设置的计算公式,请到取值方法维护中设置.\",\"state\":\"true\"}";
			
		}
		
		//7.提取公式指标
		recursiveFormula(formulaMap);
		
		//8.提取指标值
		for (String key : sortMap(targetMap).keySet()) {
			
			PrmTargetMethod ptm = targetMethodMap.get(key);//
			
			if("03".equals(ptm.getMethod_code())){//取值函数取值
				
				
			}
			
		}
		
		//9.提取公式值
		for (String key : sortMap(formulaMap).keySet()) {

			PrmTargetMethod ptm = targetMethodMap.get(key);

			for(PrmDeptTargetDataCalculate pdtdc : calculateList){
				
				String dept = pdtdc.getDept_id().toString()+pdtdc.getDept_no();
				
				String formulaMethodEng = get02FormulaEng(formulaMap.get(key),dept,map01Value);
				
				Object targetValue = MVEL.eval(formulaMethodEng);
				
				//System.out.println(pdtdc.getTarget_code()+pdtdc.getDept_id()+pdtdc.getDept_no());

				map01Value.put(pdtdc.getTarget_code()+pdtdc.getDept_id()+pdtdc.getDept_no(), Double.parseDouble(targetValue.toString()));
				
			}

		}
		
		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();

		for(PrmDeptTargetDataCalculate pdtdc : calculateList){

			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("group_id", pdtdc.getGroup_id());map.put("hos_id", pdtdc.getHos_id());map.put("copy_code", pdtdc.getCopy_code());
			
			map.put("acc_year", pdtdc.getAcc_year());map.put("acc_month", pdtdc.getAcc_month());
			
			map.put("target_code", pdtdc.getTarget_code());
			
			map.put("dept_no", pdtdc.getDept_no());map.put("dept_id", pdtdc.getDept_id());
			
			Double target_value = map01Value.get(pdtdc.getTarget_code()+pdtdc.getDept_id()+pdtdc.getDept_no());

			map.put("target_value", target_value);
			
			updateList.add(map);
		
		}

		prmDeptTargetDataCalculateMapper.updateBatchPrmDeptTargetDataCalculate(updateList);
		
		return "{\"msg\":\"计算完成.\",\"state\":\"true\"}";
	}
	
	public String get02FormulaEng(String formulaCode,String dept,Map<String, Double> map01Value) throws DataAccessException{
		
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
	public void recursiveFormula(Map<String,String> map) throws DataAccessException {

		Map<String,String> reMap = new HashMap<String,String>();// 存放是否有需要递归的公式
		
		for(String key : map.keySet()){
			
			for(String target_code : formulaStackMap.get(map.get(key))){
				
				PrmTargetMethod ptm = targetMethodMap.get(target_code);//根据公式编码查询
				
				if("02".equals(ptm.getMethod_code())){
					
					formulaMap.put(target_code,ptm.getFormula_code());
					
					reMap.put(target_code,ptm.getFormula_code());
					
				}else{
					
					targetMap.put(target_code,target_code);
					
				} 
				
			}
		}

		if (reMap.size() > 0) {recursiveFormula(reMap);}// 如果存在计算公式 调用递归接着查找

	}
	
    private static LinkedHashMap<String, String> sortMap(LinkedHashMap<String, String> oldhMap) {  
    	
    	LinkedHashMap<String, String> newMap = new LinkedHashMap<String, String>();
    	
    	ArrayList<String> arrayList = new ArrayList<String>();
    	
    	for (String key : oldhMap.keySet()) {

    		arrayList.add(key);
    		
    	}
    	
    	Collections.reverse(arrayList);
    	
    	for(int i=0;i<arrayList.size();i++){
    		
    		newMap.put(arrayList.get(i), oldhMap.get(arrayList.get(i)));
    		
    	}
  
        return newMap;
    }

    
	@Override
	public List<Map<String, Object>> queryPrmTargetDataCalculatePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		entityMap.put("sql", " ptm.Method_Code !='01' ");
		
		List<PrmDeptTargetDataCalculate> list = prmDeptTargetDataCalculateMapper.queryPrmDeptTargetPrmTargetDataCalculate(entityMap);

		return JsonListMapUtil.beanToListMap(list);
	
	}

}
