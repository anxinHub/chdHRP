/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.downtotop.deptyearbudg;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.budg.dao.budgincome.budgmedincomedyrate.BudgMedIncomeDyRateMapper;
import com.chd.hrp.budg.dao.budgincome.budgmedincomehyrate.BudgMedIncomeHyRateMapper;
import com.chd.hrp.budg.dao.budgincome.downtotop.deptyearbudg.BudgDeptIndependentSubjMapper;
import com.chd.hrp.budg.dao.budgincome.downtotop.deptyearbudg.BudgMedIncomeDeptYearMapper;
import com.chd.hrp.budg.dao.budgincome.toptodown.hosyearinbudg.BudgHosIndependentSubjMapper;
import com.chd.hrp.budg.dao.budgincome.toptodown.hosyearinbudg.BudgMedIncomeHosYearMapper;
import com.chd.hrp.budg.entity.BudgDeptIndependentSubj;
import com.chd.hrp.budg.entity.BudgHosIndependentSubj;
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgDeptIndependentSubjService;
import com.chd.hrp.budg.service.common.BudgCountProcessService;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
import com.chd.hrp.budg.service.common.BudgFunProcessService;
import com.chd.hrp.sys.entity.Dept;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室年度医疗收入独立核算科目预算
 * @Table:
 * BUDG_DEPT_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("budgDeptIndependentSubjService")
public class BudgDeptIndependentSubjServiceImpl implements BudgDeptIndependentSubjService {

	private static Logger logger = Logger.getLogger(BudgDeptIndependentSubjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDeptIndependentSubjMapper")
	private final BudgDeptIndependentSubjMapper budgDeptIndependentSubjMapper = null;
    
	@Resource(name = "budgMedIncomeDeptYearMapper")
	private final BudgMedIncomeDeptYearMapper budgMedIncomeDeptYearMapper = null;
	
	@Resource(name = "budgMedIncomeDyRateMapper")
	private final BudgMedIncomeDyRateMapper budgMedIncomeDyRateMapper = null;
	
	@Resource(name = "budgFormulaSetService") //计算公式service
	private final BudgFormulaSetService budgFormulaSetService = null;
	
	@Resource(name = "budgCountProcessService") //计算过程 service
	private final BudgCountProcessService budgCountProcessService = null;
	
	@Resource(name = "budgFunProcessService") //函数取值过程 service
	private final BudgFunProcessService budgFunProcessService = null;
	
	@Resource(name = "budgHosIndependentSubjMapper") //函数取值过程 service
	private final BudgHosIndependentSubjMapper budgHosIndependentSubjMapper = null;
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();//医院年度医疗收入概率预算 用List
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
			
			if(item.get("rateList") == null){
				continue ;
			}else{
				List<Map<String,Object>> list = (List<Map<String, Object>>)item.get("rateList");
				for(Map<String,Object> itemRate: list){
					rate.add(itemRate);
				}
			}
		}
		
		try {
			
			if(addList.size()>0){
				//查询添加数据是否已存在
				String  str = budgDeptIndependentSubjMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					budgDeptIndependentSubjMapper.addBatch(addList);
					
					budgMedIncomeDeptYearMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				budgDeptIndependentSubjMapper.updateBatch(updateList);
				
				budgMedIncomeDeptYearMapper.updateBatch(updateList);
			}
			
			if(rate.size()>0){
				
				budgMedIncomeDyRateMapper.deleteBatch(listVo);
				
				budgMedIncomeDyRateMapper.addBatch(rate);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}
    
	/**
	 * @Description 
	 * 添加医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医院年度医疗收入独立核算科目预算
		BudgHosIndependentSubj budgHosIndependentSubj = queryByCode(entityMap);

		if (budgHosIndependentSubj != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgDeptIndependentSubjMapper.add(entityMap);
			
			budgMedIncomeDeptYearMapper.add(entityMap) ;
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//定义错误信息字符串  用于拼接错误信息
			String str = "";
			List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> item : entityList){
				
				if(item.get("rateList") == null){
					break ;
				}else{
					List<Map<String,Object>> list = (List<Map<String, Object>>)item.get("rateList");
					for(Map<String,Object> itemRate: list){
						rate.add(itemRate);
					}
				}
				
				//查询科目是否已存在
				Map<String,Object> map = budgDeptIndependentSubjMapper.queryByCode(item);
				
				if(map != null){
					str += item.get("subj_code")+";";
				}
			}
			
			if(str != ""){
				return "{\"error\":\"所选年以下度科目编码:"+str+"数据已存在.\",\"state\":\"false\"}";
			}else{
				
				if(rate.size()>0){
					budgMedIncomeDyRateMapper.addBatch(rate);
				
				}
				budgDeptIndependentSubjMapper.deleteBatch(entityList);
				
				budgDeptIndependentSubjMapper.addBatch(entityList);
				
				budgMedIncomeDeptYearMapper.deleteBatch(entityList);
				
				budgMedIncomeDeptYearMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgDeptIndependentSubjMapper.update(entityMap);
		  
		  budgMedIncomeDeptYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item: entityList){
				
				if(item.get("rateList") == null){
					break ;
				}else{
					List<Map<String,Object>> list = (List<Map<String, Object>>)item.get("rateList");
					for(Map<String,Object> itemRate: list){
						rate.add(itemRate);
					}
				}
			}
			
			if(rate.size()>0){
				
				budgMedIncomeDyRateMapper.addBatch(rate);
			}
			
			budgDeptIndependentSubjMapper.updateBatch(entityList);
			
			budgMedIncomeDeptYearMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 删除医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgDeptIndependentSubjMapper.delete(entityMap);
			
			budgMedIncomeDeptYearMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item: entityList){
				
				if(!"1".equals(item.get("is_prob"))){
					break ;
				}else{
					rate.add(item);
				}
			}
			
			if(rate.size()>0){
				
				budgMedIncomeDyRateMapper.deleteBatch(rate);
			}
			
			budgDeptIndependentSubjMapper.deleteBatch(entityList);
			
			budgMedIncomeDeptYearMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象医院年度医疗收入独立核算科目预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgHosIndependentSubj> list = (List<BudgHosIndependentSubj>)budgDeptIndependentSubjMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgDeptIndependentSubjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgDeptIndependentSubjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgHosIndependentSubj> list = (List<BudgHosIndependentSubj>)budgDeptIndependentSubjMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgHosIndependentSubj> list = (List<BudgHosIndependentSubj>)budgDeptIndependentSubjMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptIndependentSubjMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgHosIndependentSubj
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptIndependentSubjMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度医疗收入独立核算科目预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgHosIndependentSubj>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptIndependentSubjMapper.queryExists(entityMap);
	}
	
	/**
	 * 查询上年收入及编制方法下的取值方法
	 */
	@Override
	public String queryLastYearIncome(Map<String, Object> entityMap) {
		//查询上年收入
		Map<String, Object> mapVo=budgDeptIndependentSubjMapper.queryLastYearIncome(entityMap);
		if(entityMap.get("edit_method") != null){
			//查询编制方法下的取值方法
			Map<String, Object> map = budgDeptIndependentSubjMapper.queryGetWay(entityMap);
			if(map != null){
				if(mapVo!=null){
					map.putAll(mapVo);
				}
				return ChdJson.toJson(map);
			}else{
				
				return ChdJson.toJson(mapVo);
			}
			
		}
		
		return ChdJson.toJson(mapVo);
	}
	
	//查询数据是否已经存在  
	@Override
	public int queryBudgMedIncomeEditPlanByCode(Map<String, Object> mapVo) {
		
		return budgDeptIndependentSubjMapper.queryBudgMedIncomeEditPlanByCode(mapVo);
	}
	
	//根据封装条件  查询取值方法 
	@Override
	public String queryGetWayFromBudgMedIncomeEditPlan(Map<String, Object> mapVo) {
		
		return budgDeptIndependentSubjMapper.queryGetWayFromBudgMedIncomeEditPlan(mapVo);
	}
	
	//查询科室下拉框
	@Override
	public String queryBudgDept(Map<String, Object> mapVo) {
		RowBounds rowBoundsALL = new RowBounds(0, 20);
		
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgDeptIndependentSubjMapper.queryBudgDept(mapVo, rowBounds));
	}
	
	@Override
	public String collectCertenDYBudgData(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgDeptIndependentSubjMapper.query(mapVo);
			
			if(list.size()>0){
				for(Map<String,Object> item : list){
					//空值处理
					if(item.get("remark") == null ){
						item.put("remark", "");
					}
					
					if(item.get("budg_value") == null ){
						item.put("budg_value", "");
					}
					
					if(item.get("count_value") == null ){
						item.put("count_value", "");
					}
					if(item.get("grow_rate") == null ){
						item.put("grow_rate", "");
					}
					if(item.get("grow_value") == null ){
						item.put("grow_value", "");
					}
					
					if(item.get("resolve_rate") == null ){
						item.put("resolve_rate", "");
					}
					
					if(item.get("last_year_income") == null ){
						item.put("last_year_income", "");
					}
					if(item.get("state") == null ){
						item.put("state", "");
					}
					if(item.get("reason") == null ){
						item.put("reason", "");
					}
					
					if(item.get("refer_value") == null ){
						item.put("refer_value", "");
					}
					
					item.put("budg_year", item.get("year")) ;//字段转换
					
					item.put("budg_level",mapVo.get("budg_level"));
					
					//根据 所传 收入科目编码 查询其取值方法
					Map<String, Object> map = budgHosIndependentSubjMapper.queryIndexGetWay(item);
					
					
					if(map != null){
						
						if(map.get("formula_id") != null){//计算公式取值
							
							Map<String,Object> formula =  budgFormulaSetService.queryByCode(map);
							
							formula.put("year", item.get("year"));
							
							formula.put("index_code", item.get("subj_code"));
							
							formula.put("element_type_code", "04");//元素类型 01 基本指标     02 费用指标 03预算指标 04 收入科目 05支出科目
							
							formula.put("element_level", "03");//element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
							
							formula.put("value_type_code", "01");//value_type_code:元素值类型  01 本年预算 02 上年执行
							
							List<String> indexList = new ArrayList<String>();
							
							indexList.add(String.valueOf(formula.get("element_type_code"))+item.get("index_code")+formula.get("element_level")+"01");//递归校验用
							
							//查询 计算公式元素栈 数据   格式{"formula_stack" : "element_level@element_code@value_type_code@element_type_code;...."}
							// element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
							//element_code：元素编码
							//value_type_code: 元素值类型  01 本年预算 02 上年执行
							//element_type_code： 元素类型 01 基本指标 02 费用指标 03预算指标 04 收入科目 05支出科目
							Map<String,Object> stack = budgFormulaSetService.queryFormulaStack(map);
							
							String[] element = stack.get("formula_stack").toString().split(";");
							
							 
							Map<String,Object>  countItem = JSONObject.parseObject(budgCountProcessService.queryCountProcess(formula,indexList));
							
							if(countItem.get("error") ==null){//计算公式 计算项取值没有错误时
								// 计算公式 每个计算元素值List
								List<Map<String,Object>> data = JsonListMapUtil.getListMap(countItem.get("Rows").toString());
								
								// 存放 key：格式element_level@element_code@value_type_code@element_type_code(解析公式 替换  每个计算元素值用)；  value：值
								Map<String,Object>  replaceMap = new HashMap<String,Object>();
								
								for(int i= 0 ; i<element.length;i++){
									for(Map<String,Object> val : data){
										if(String.valueOf(i+1).equals(String.valueOf(val.get("stack_seq_no")))){
											replaceMap.put(element[i], val.get("count_value"));
										}
									}
									
								}
								
								String formula_en = formula.get("formula_en").toString();//要解析的计算公式字符串
								
								formula_en = formula_en.replace("%", "/100");//百分号 解析替换
								
								for(String str: replaceMap.keySet()){
									
									formula_en = formula_en.replace(str, replaceMap.get(str).toString());//将每个计算元素替换为对应的值
									
									
								}
								
								Double budg_value = 0.00 ;
								try {
									
									budg_value = Double.parseDouble(String.valueOf( new ScriptEngineManager().getEngineByName("JavaScript").eval(formula_en)));
									
									//分解比例  保留  小数点后4位数字 四舍五入		
									BigDecimal  b  =   new   BigDecimal(budg_value);
									
									budg_value =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();  
									
									item.put("count_value", budg_value);
									item.put("budg_value", budg_value);
									
								} catch (ScriptException e) {
									
									return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+"对应计算公式解析失败,无法计算.请核对该计算公式\",\"state\":\"false\"}";
								}
							}else{
								
								return "{\"error\":\"指标【:"+item.get("subj_code")+" "+item.get("subj_name")+countItem.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
						
						if(map.get("fun_id") != null){ //取值函数 取值
							
							map.put("year", item.get("year"));
							
							map.put("fun_code", map.get("fun_id"));
							
							map.put("index_type_code", "04");//指标类型  01基本指标 02费用标准 03预算指标 04收入科目
							
							map.put("budg_year", item.get("year")) ;//字段转换
							
							map.put("budg_level",mapVo.get("budg_level"));
							
							//查询 函数相关信息
							Map<String,Object> funMap = JSONObject.parseObject(budgFunProcessService.queryFunProcess(map));
							
							if(funMap.get("error") ==null){
								
								List<Map<String,Object>> data = JsonListMapUtil.getListMap(funMap.get("Rows").toString());
								
								item.put("count_value", data.get(0).get("count_value"));
								
								item.put("budg_value", data.get(0).get("count_value"));
								
							}else{
								
								return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+funMap.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
					}
				}
				
				budgDeptIndependentSubjMapper.updateBatch(list);
				budgMedIncomeDeptYearMapper.updateBatch(list);
				
				return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"error\":\"没有计算数据.\",\"state\":\"false\"}";
				
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	
	/**
	 * 查询要生成的数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgDeptIndependentSubjMapper.queryData(mapVo);
	}
	
	/**
	 * 增长比例获取
	 */
	@Override
	public String getGrowRate(Map<String, Object> mapVo) throws DataAccessException {
		
		List<Map<String, Object>>  list = budgDeptIndependentSubjMapper.getGrowRate(mapVo) ;
		
		return ChdJson.toJson(list);
		
	}
	
	/**
	 * @Description 
	 * 科室年度医疗收入预算增量预算  更新 增长比例 及相关数据数据
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateGrowRate(List<Map<String,Object>> listVo)throws DataAccessException{
		
		try {
			
			//科室年度医疗收入独立核算科目预算  更新 增长比例 及相关数据数据
			budgDeptIndependentSubjMapper.updateGrowRate(listVo);
			//科室年度医疗收入预算  更新 计算值、预算值
			budgMedIncomeDeptYearMapper.updateGrowRate(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}"); 
			//return "{\"error\":\"操作成功\",\"state\":\"false\"}";

		}	
		
	}

	@Override
	public List<Map<String, Object>> queryDeptId(Map<String, Object> mapVo)
			throws DataAccessException {
List<Map<String, Object>>  list = budgDeptIndependentSubjMapper.queryDeptId(mapVo) ;
		
		return list;
		
	}

	@Override
	public List<Map<String, Object>> queryDataDeptList(Map<String, Object> mapVo) {
		
		return budgDeptIndependentSubjMapper.queryDataDeptList(mapVo);
	}
}
