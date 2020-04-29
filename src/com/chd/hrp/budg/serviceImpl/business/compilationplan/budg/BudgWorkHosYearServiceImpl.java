/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationplan.budg;

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
import com.chd.hrp.budg.dao.business.budgeworkhosrate.BudgWorkHosYearRateMapper;
import com.chd.hrp.budg.dao.business.compilationplan.budg.BudgWorkHosYearMapper;
import com.chd.hrp.budg.entity.BudgWorkHosYear;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosYearService;
import com.chd.hrp.budg.service.common.BudgCountProcessService;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
import com.chd.hrp.budg.service.common.BudgFunProcessService;
import com.chd.hrp.budg.service.common.BudgFunService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医院年度业务预算
 * @Table:
 * BUDG_WORK_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWorkHosYearService")
public class BudgWorkHosYearServiceImpl implements BudgWorkHosYearService {

	private static Logger logger = Logger.getLogger(BudgWorkHosYearServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWorkHosYearMapper")
	private final BudgWorkHosYearMapper budgWorkHosYearMapper = null;
	
	
	@Resource(name = "budgWorkHosYearRateMapper")
	private final BudgWorkHosYearRateMapper budgWorkHosYearRateMapper = null;
	
	
	@Resource(name = "budgFormulaSetService") //计算公式service
	private final BudgFormulaSetService budgFormulaSetService = null;
	
	@Resource(name = "budgCountProcessService") //计算过程 service
	private final BudgCountProcessService budgCountProcessService = null;
	
	@Resource(name = "budgFunProcessService") //函数取值过程 service
	private final BudgFunProcessService budgFunProcessService = null;
	
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();//医院年度业务概率预算  用List
		
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
				//批量 查询 添加数据是否已存在
				String  str = budgWorkHosYearMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgWorkHosYearMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = budgWorkHosYearMapper.updateBatch(updateList);
				
			}
			
			if(rate.size()>0){
				
				budgWorkHosYearRateMapper.deleteBatch(listVo);
				
				budgWorkHosYearRateMapper.addBatch(rate);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
    
	/**
	 * @Description 
	 * 添加医院年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医院年度业务预算
		int count = budgWorkHosYearMapper.queryDataExist(entityMap);

		if ( count > 0 ) {

			return "{\"message\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
			
		if(entityMap.get("rateList") != null){
			
			list = (List<Map<String, Object>>)entityMap.get("rateList");
			
		}
		
		try {
			
			if(list.size()>0){
				
				budgWorkHosYearRateMapper.addBatch(list);
			}
			
			int state = budgWorkHosYearMapper.add(entityMap);
			
			return "{\"message\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
						+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
						+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 ! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医院年度业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String str = "";
			
			List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item: entityList){
				
				if(item.get("rateList") != null){
					List<Map<String,Object>> list = (List<Map<String, Object>>)item.get("rateList");
					for(Map<String,Object> itemRate: list){
						rate.add(itemRate);
					}
				}
				
				//获取对象医院年度业务预算
				BudgWorkHosYear budgWorkHosYear = queryByCode(item);
				
				if(budgWorkHosYear != null ){
					str += item.get("year")+"年 指标编码:"+item.get("index_code")+";"	;
				}
			}
			
			if(str != ""){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"数据已存在.\",\"state\":\"false\"}";
			
			}else{
				if(rate.size()>0){
					
					budgWorkHosYearRateMapper.addBatch(rate);
				}
				
				budgWorkHosYearMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 更新医院年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgWorkHosYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 ! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医院年度业务预算<BR> 
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
				
				budgWorkHosYearRateMapper.addBatch(rate);
			}
			
			budgWorkHosYearMapper.updateBatch(entityList);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"修改失败\"}");
			//return "{\"error\":\"添加失败 ! 方法 addBatch\"}";

		}
		
	}
	/**
	 * @Description 
	 * 删除医院年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
    		
			int state = budgWorkHosYearMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 ! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医院年度业务预算<BR> 
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
				
				budgWorkHosYearRateMapper.deleteBatch(rate);
			}
			
			budgWorkHosYearMapper.deleteBatch(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加医院年度业务预算<BR> 
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
		//判断是否存在对象医院年度业务预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkHosYear> list = (List<BudgWorkHosYear>)budgWorkHosYearMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWorkHosYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWorkHosYearMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 ! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医院年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosYearMapper.query(entityMap);
			
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosYearMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医院年度业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkHosYearMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWorkHosYear
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkHosYearMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkHosYear>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkHosYearMapper.queryExists(entityMap);
	}
	
	@Override
	public String queryLastYearWork(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String, Object>  methodMap  = new HashMap<String,Object>();
		
		if(mapVo.get("edit_mehtod") != null){
			//从业务预算编制方案  BUDG_WORK_EDIT_PLAN 中查询 编码的取值方法
			methodMap = budgWorkHosYearMapper.queryGetWay(mapVo) ;
		}
		
		
		Map<String, Object>  map = budgWorkHosYearMapper.queryLastYearWork(mapVo) ;
		
		if(map != null ){
			
			map.putAll(methodMap);
			
			return ChdJson.toJson(map);
			
		}else{
			
			return ChdJson.toJson(methodMap);
		}
		
	}
	/**
	 * 生成运营尺度数据
	 */
	@Override
	public String queryProbBudgRate(Map<String, Object> entityMap) throws DataAccessException {
			
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosYearMapper.queryProbBudgRate(entityMap);
		
		return ChdJson.toJson(list);
			
	}
	@Override
	public String queryBudgWorkHosYear(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosYearMapper.queryBudgWorkHosYear(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosYearMapper.queryBudgWorkHosYear(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public Map<String, Object> queryGetWay(Map<String, Object> mapVo) {
		
		return budgWorkHosYearMapper.queryGetWay(mapVo);
	}
	
	/**
	 * 自下而上  年度业务预算  汇总
	 */
	@Override
	public String collectBudgWorkHosYearDown(Map<String, Object> mapVo)	throws DataAccessException {
		
		// 汇总科室年度数据
		List<Map<String,Object>> list = budgWorkHosYearMapper.queryCollectData(mapVo) ;
		
		//更新用List
		List<Map<String,Object>> updateList =  new ArrayList<Map<String,Object>>();
		
		//添加用List
		List<Map<String,Object>> addList =  new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : list ){
			
			
			if(item.get("count_value") == null ){
				item.put("count_value", "") ;
			}
				
			if(item.get("last_year_workload") == null ){
				item.put("last_year_workload", "") ;
			}
			
			item.put("remark", "汇总") ;
			
			item.put("grow_value", "") ;
			
			item.put("grow_rate", "") ;
			
			item.put("hos_suggest","");
			
			item.put("dept_suggest_sum", "");
			
			int count = budgWorkHosYearMapper.queryDataExist(item);
			
			if(count > 0){
				updateList.add(item);
			}else{
				addList.add(item);
			}
		}
		try {
			if(updateList.size() > 0 ){
				budgWorkHosYearMapper.updateBatch(updateList);
			}
			
			if(addList.size() > 0 ){
				budgWorkHosYearMapper.addBatch(addList);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败! 方法 collectBudgWorkHosYearDown\"}";
		}
			
	}
	/**
	 * 增量生成时 查询生成数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkHosYearMapper.queryData(mapVo);
	}
	/**
	 * 根据主键 查询数据是否已存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkHosYearMapper.queryDataExist(mapVo);
	}
	/**
	 * 科室意见汇总
	 */
	@Override
	public String sumDeptSuggest(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			String[] indexCodeArr = mapVo.get("index_codes").toString().split(",");
			mapVo.put("index_codes", indexCodeArr);
			// 汇总科室意见 数据
			// 1.汇总各指标科室年预算值，更新对应医院年预算值
			budgWorkHosYearMapper.sumDeptSuggest(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败! 方法 sumDeptSuggest\"}";
		}
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 预算指标下拉框
	 */
	@Override
	public String queryBudgIndex(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgWorkHosYearMapper.queryBudgIndex(entityMap, rowBounds));
	}
	
	@Override
	public String collectCertenHYBudgData(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgWorkHosYearMapper.query(mapVo);
			
			if(list.size()>0){
				for(Map<String,Object> item : list){
					//空值处理
					if(item.get("remark") == null ){
						item.put("remark", "");
					}
					
					if(item.get("budg_value") == null ){
						item.put("budg_value", "");
					}
					
					if(item.get("grow_rate") == null ){
						item.put("grow_rate", "");
					}
					if(item.get("grow_value") == null ){
						item.put("grow_value", "");
					}
					if(item.get("hos_suggest") == null ){
						item.put("hos_suggest", "");
					}
					
					if(item.get("last_year_workload") == null ){
						item.put("last_year_workload", "");
					}
					
					if(item.get("dept_suggest_sum") == null ){
						item.put("dept_suggest_sum", "");
					}
					
					item.put("budg_year", item.get("year")) ;//字段转换
					
					item.put("budg_level",mapVo.get("budg_level"));
					
					//根据 所传 指标编码 查询其取值方法
					Map<String, Object> map = budgWorkHosYearMapper.queryIndexGetWay(item);
					
					
					if(map != null){
						
						if(map.get("formula_id") != null){//计算公式取值
							
							map.put("year", item.get("year"));
							
							Map<String,Object> formula =  budgFormulaSetService.queryByCode(map);
							
							formula.put("year", item.get("year"));
							
							formula.put("index_code", item.get("index_code"));
							
							formula.put("element_type_code", "03");//元素类型 01 基本指标     02 费用指标 03预算指标 04 收入科目 05支出科目
							
							formula.put("element_level", "01");//element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
							
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
									
									item.put("budg_value", budg_value);
									item.put("count_value", budg_value);
									
								} catch (ScriptException e) {
									
									return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+"对应计算公式解析失败,无法计算.请核对该计算公式\",\"state\":\"false\"}";
								}
							}else{
								
								return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+countItem.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
						
						if(map.get("fun_id") != null){ //取值函数 取值
							
							map.put("year", item.get("year"));
							
							map.put("fun_code", map.get("fun_id"));
							
							map.put("index_type_code", "03");//指标类型  01基本指标 02费用标准 03预算指标 04收入科目
							
							map.put("budg_year", item.get("year")) ;//字段转换
							
							map.put("budg_level",mapVo.get("budg_level"));
							
							//查询 函数相关信息
							Map<String,Object> funMap = JSONObject.parseObject(budgFunProcessService.queryFunProcess(map));
							
							if(funMap.get("error") ==null){
								
								List<Map<String,Object>> data = JsonListMapUtil.getListMap(funMap.get("Rows").toString());
								
								item.put("budg_value", data.get(0).get("count_value"));
								item.put("count_value", data.get(0).get("count_value"));
								
							}else{
								
								return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+funMap.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
					}
				}
				
				budgWorkHosYearMapper.updateBatch(list);
				
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
	 * 增长比例获取
	 */
	@Override
	public String getGrowRate(Map<String, Object> mapVo) throws DataAccessException {
		
		List<Map<String, Object>>  list = budgWorkHosYearMapper.getGrowRate(mapVo) ;
		
		return ChdJson.toJson(list);
		
	}
	
	/**
	 * @Description 
	 * 医院年度业务预算增量预算  更新 增长比例 及相关数据数据
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateGrowRate(List<Map<String,Object>> listVo)throws DataAccessException{
		
		try {
			
		  int state = budgWorkHosYearMapper.updateGrowRate(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作成功\",\"state\":\"false\"}";

		}	
		
	}

	@Override
	public List<Map<String, Object>> getPrintData(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosYearMapper.queryBudgWorkHosYear(mapVo);
		return list;
	}
}
