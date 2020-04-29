/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.common.BudgComTypeMapper;
import com.chd.hrp.budg.dao.common.BudgCountProcessMapper;
import com.chd.hrp.budg.dao.common.BudgFormulaSetMapper;
import com.chd.hrp.budg.service.common.BudgCountProcessService;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
import com.chd.hrp.budg.service.common.BudgFunProcessService;
import com.github.pagehelper.PageInfo;

 


@Service("budgCountProcessService")
public class BudgCountProcessServiceImpl implements BudgCountProcessService {

	private static Logger logger = Logger.getLogger(BudgCountProcessServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCountProcessMapper")
	private final BudgCountProcessMapper budgCountProcessMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgFormulaSetMapper")
	private final BudgFormulaSetMapper budgFormulaSetMapper = null;
	
	@Resource(name = "budgFormulaSetService") //计算过程 service
	private final BudgFormulaSetService budgFormulaSetService = null;
	
	@Resource(name = "budgFunProcessService") //函数取值过程 service
	private final BudgFunProcessService budgFunProcessService = null;
	
	
    /**
     * 计算过程查看 各计算项数据查询
     */
	@Override
	public String queryCountProcess(Map<String, Object> mapVo,List<String>  indexList) throws DataAccessException {
		
		/************** 以下 代码  校验用（  阻止 正在计算中的 计算项 循环调用  引起死循环） **************/
		
		if(mapVo.get("index_code") == null ){
			
			return "{\"error\":\"参数不足:没有引用该计算公式的指标参数.\"}";
			
		}else{
			
			if(mapVo.get("value_type_code") != null ){//递归调用时 用
				
				if( !indexList.contains(String.valueOf(mapVo.get("element_type_code"))+mapVo.get("index_code")+mapVo.get("element_level")+mapVo.get("value_type_code"))){
					
					indexList.add(String.valueOf(mapVo.get("element_type_code"))+mapVo.get("index_code")+mapVo.get("element_level")+mapVo.get("value_type_code"));
				}
			}
			
			
			
		}
		
		/************** 以上 代码  校验用（  阻止 正在计算中的 计算项 循环调用  引起死循环） **************/
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> list=  budgFormulaSetMapper.queryFormulaStack(mapVo);
		
		//循环遍历 计算公式元素栈数据 取各个计算项值
		for(Map<String,Object> item : list){
			
			if(indexList.contains(String.valueOf(item.get("element_type_code"))+ item.get("element_code")+item.get("element_level")+item.get("value_type_code"))){
				
				return "{\"error\":\"计算公式设置错误.\",\"state\":\"false\"}";
			}
			
			if(mapVo.get("year") != null){
				item.put("year", mapVo.get("year"));
			}else{
				item.put("year", SessionManager.getAcctYear());
			}
			
			item.put("index_code", item.get("element_code"));
			
			//存储 查询数据 Map
			Map<String,Object> basicMap = new HashMap<String,Object>();
			
			//基本指标 类 或 费用标准 类  计算项数据查询
			//  元素层次element_level 只有医院年度和科室年度两个层次   预算值类型value_type_code 只有本年预算，没有上年执行
			if("01".equals(item.get("element_type_code")) || "02".equals(item.get("element_type_code"))){
				
				//构建查询条件 Map
				Map<String,Object> temp = new HashMap<String,Object>();
				
				temp.put("group_id", item.get("group_id"));
				temp.put("hos_id", item.get("hos_id"));
				temp.put("copy_code", item.get("copy_code"));
				temp.put("index_code", item.get("element_code"));
				
				if(mapVo.get("year") != null){
					temp.put("year", mapVo.get("year"));
				}else{
					temp.put("year", SessionManager.getAcctYear());
				}
				
				temp.put("element_type_code", item.get("element_type_code"));//元素类型 01 基本指标 02费用标准 
				//医院年度 基本指标维护数据 或 费用标注维护数据 
				if("01".equals(item.get("element_level"))){
					
					if("01".equals(item.get("element_type_code"))){
						//  层次为医院年度是 基本指标 从 医院基本指标数据维护表 “BUDG_HOS_BASIC_INDEX_DATA”中取数
						temp.put("table", "BUDG_HOS_BASIC_INDEX_DATA");
					}else{
						//层次为医院年度是 费用标准  从 医院费用标准数据维护表 “BUDG_HOS_CHARGE_STAN”中取数
						temp.put("table", "BUDG_HOS_CHARGE_STAN");
					}
					// 查询 基本指标类 或 费用标注类  预算层次为 医院年度  的 计算项数据
					basicMap = budgCountProcessMapper.queryHosBasicIndex(temp);
					
					if(basicMap==null || basicMap.get("count_value") == null ){
						
						//当计算项 没有取到值时 调用 递归 取该计算项 值
						basicMap = recursiveValue(item, indexList);
						
						if(basicMap.get("error") != null ){
							
							return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
						}else{
							indexList.remove(indexList.size()-1);
						}
						
					}
				}
				//科室年度 基本指标维护数据 或 费用标注维护数据 
				if("03".equals(item.get("element_level"))){
					
					if(mapVo.get("dept_id") != null){
						temp.put("dept_id", mapVo.get("dept_id"));
					}else{
						return "{\"error\":\"参数不足:没有科室参数.\"}";
					}
					
					if("01".equals(item.get("element_type_code"))){
						//  层次为科室年度是 基本指标 从 科室基本指标数据维护表 “BUDG_DEPT_BASIC_INDEX_DATA”中取数
						temp.put("table", "BUDG_DEPT_BASIC_INDEX_DATA");
					}else{
						//层次为科室年度是 费用标准  从 科室费用标准数据维护表 “BUDG_DEPT_CHARGE_STAN”中取数
						temp.put("table", "BUDG_DEPT_CHARGE_STAN");
					}
					
					// 查询 基本指标类 或 费用标注类  预算层次为 科室年度  的 计算项数据
					basicMap = budgCountProcessMapper.queryDeptBasicIndex(temp);
					
					if(basicMap==null || basicMap.get("count_value") == null){
						
						//当计算项 没有取到值时 调用 递归 取该计算项 值
						basicMap = recursiveValue(item, indexList);
						
						if(basicMap.get("error") != null ){
							
							return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
						}else{
							indexList.remove(indexList.size()-1);
						}
					}
				}
				Map<String,Object> map = new HashMap<String,Object>();

				map.put("stack_seq_no", item.get("stack_seq_no"));
				map.put("group_id", basicMap.get("group_id"));
				map.put("hos_id", basicMap.get("hos_id"));
				map.put("copy_code", basicMap.get("copy_code"));
				
				map.put("code", basicMap.get("code"));
				if("01".equals(item.get("value_type_code"))){
					map.put("count_item", basicMap.get("count_item")+"【本年预算】");
				}else{
					map.put("count_item", basicMap.get("count_item")+"【上年执行】");
				}
				
				map.put("count_value", basicMap.get("count_value"));
				
				dataList.add(map);
			}
			
			//预算指标类  计算项数据查询  
			//		  元素层次element_level 有 01 医院年度、02 医院月份 、 03科室年度、04科室月份 四个层次 ，
			//       预算值类型value_type_code  既有 01本年预算 又有  02上年执行
			if("03".equals(item.get("element_type_code"))){
				
				//构建查询条件 Map
				Map<String,Object> temp = new HashMap<String,Object>();
				
				temp.put("group_id", item.get("group_id"));
				temp.put("hos_id", item.get("hos_id"));
				temp.put("copy_code", item.get("copy_code"));
				temp.put("index_code", item.get("element_code"));
				
				//预算值类型value_type_code   01本年预算   02上年执行 
				temp.put("value_type_code", item.get("value_type_code"));//区分 取预算值 还是执行数据
				
				if("01".equals(item.get("value_type_code"))){ //预算值类型value_type_code 为  01本年预算  时  
					
					//年度
					if(mapVo.get("year") != null ){
						temp.put("year", mapVo.get("year"));
					}else{
						temp.put("year", SessionManager.getAcctYear());
					}
					
					/*元素层次element_level 为  01 医院年度,预算值类型value_type_code 为  01本年预算  时   
					 从 医院年度业务预算表  BUDG_WORK_HOS_YEAR 中取数*/
					if("01".equals(item.get("element_level"))){
						
						temp.put("table", "BUDG_WORK_HOS_YEAR");
					}
						
					/*元素层次element_level 为  02 医院月份,预算值类型value_type_code 为  01本年预算  时
					从 医院月份业务预算表  BUDG_WORK_HOS_MONTH 中取数*/
					if("02".equals(item.get("element_level"))){
						
						temp.put("table", "BUDG_WORK_HOS_MONTH");
						
						if(mapVo.get("month") != null  ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
					}
					/*元素层次element_level 为  03 科室年度,预算值类型value_type_code 为  01本年预算  时
					从 科室年度业务预算表  BUDG_WORK_DEPT_YEAR 中取数*/
					if("03".equals(item.get("element_level"))){
						
						if(mapVo.get("year") != null ){
							temp.put("year", mapVo.get("year"));
						}else{
							temp.put("year", SessionManager.getAcctYear());
						}
						
						temp.put("table", "BUDG_WORK_DEPT_YEAR");
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
							item.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
					}
					/*元素层次element_level 为  04 科室月份,预算值类型value_type_code 为  01本年预算  时
					从 科室年度业务预算表  BUDG_WORK_DEPT_MONTH 中取数*/
					if("04".equals(item.get("element_level"))){
						
						if(mapVo.get("year") != null){
							temp.put("year", mapVo.get("year"));
						}else{
							temp.put("year", SessionManager.getAcctYear());
						}
						
						temp.put("table", "BUDG_WORK_DEPT_MONTH");
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
					}
				}else{  //预算值类型value_type_code 为  02上年执行  时
					/*元素层次element_level 为  01 医院年度,预算值类型value_type_code 为  02上年执行  时   
					 从 医院业务执行数据(年度）表  BUDG_WORK_HOS_EXECUTE_YEAR 中取数*/
					
					//年度 处理（有 year参数  取参数year减1 ，没有时取当前 会计年度减1）
					if(mapVo.get("year") != null){
						temp.put("year", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
					}else{
						temp.put("year", Integer.parseInt(String.valueOf(SessionManager.getAcctYear()))-1);
					}
					
					if("01".equals(item.get("element_level"))){
						
						temp.put("table", "BUDG_WORK_HOS_EXECUTE_YEAR");
					}
						
					/*元素层次element_level 为  02 医院月份,预算值类型value_type_code 为  02上年执行  时
					从 医院业务执行数据(月份）表  BUDG_WORK_HOS_MONTH 中取数*/
					if("02".equals(item.get("element_level"))){
						temp.put("table", "BUDG_WORK_HOS_EXECUTE");
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
					}
					/*元素层次element_level 为  03 科室年度,预算值类型value_type_code 为 02上年执行  时
					从 科室业务执行数据(年度)表   BUDG_WORK_DEPT_EXECUTE_YEAR 中取数*/
					if("03".equals(item.get("element_level"))){
						
						temp.put("table", "BUDG_WORK_DEPT_EXECUTE_YEAR");
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
					}
					/*元素层次element_level 为  04 科室月份,预算值类型value_type_code 为  02上年执行  时
					从 科室业务执行数据(月份)表  BUDG_WORK_DEPT_EXECUTE 中取数*/
					if("04".equals(item.get("element_level"))){
						
						temp.put("table", "BUDG_WORK_DEPT_EXECUTE");
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
					}
				}
				
				// 查询  预算指标类 计算项 数据
				basicMap = budgCountProcessMapper.queryBudgIndex(temp);
				
				if(basicMap==null || basicMap.get("count_value") == null){
					
					//当计算项 没有取到值时 调用 递归 取该计算项 值
					basicMap = recursiveValue(item, indexList);
					
					if(basicMap.get("error") != null ){
						
						return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
					}else{
						indexList.remove(indexList.size()-1);
					}
				}
				
				Map<String,Object> map = new HashMap<String,Object>();

				map.put("stack_seq_no", item.get("stack_seq_no"));
				map.put("group_id", basicMap.get("group_id"));
				map.put("hos_id", basicMap.get("hos_id"));
				map.put("copy_code", basicMap.get("copy_code"));
				
				map.put("code", basicMap.get("code"));
				if("01".equals(item.get("value_type_code"))){
					map.put("count_item", basicMap.get("count_item")+"【本年预算】");
				}else{
					map.put("count_item", basicMap.get("count_item")+"【上年执行】");
				}
				map.put("count_value", basicMap.get("count_value"));
				
				dataList.add(map);
			}
			
			// 收入科目类  计算项数据查询
			//		元素层次element_level 有 01 医院年度、02 医院月份 、 03科室年度、04科室月份 四个层次 ，
			//		预算值类型value_type_code  既有 01本年预算 又有  02上年执行
			if("04".equals(item.get("element_type_code"))){
				
				//构建查询条件 Map
				Map<String,Object> temp = new HashMap<String,Object>();
				
				temp.put("group_id", item.get("group_id"));
				temp.put("hos_id", item.get("hos_id"));
				temp.put("copy_code", item.get("copy_code"));
				temp.put("index_code", item.get("element_code"));
				
				
				if("01".equals(item.get("value_type_code"))){
					
					if(mapVo.get("year") != null ){
						temp.put("year", mapVo.get("year"));
					}else{
						temp.put("year", SessionManager.getAcctYear());
					}
					
					if("01".equals(item.get("element_level"))){
						
						// 查询  收入科目类 元素层次element_level 01 医院年度，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInHYBudg(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){
							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					if("02".equals(item.get("element_level"))){
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  收入科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInHMBudg(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("03".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						// 查询  收入科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInDYBudg(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){
							
							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("04".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  收入科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInDMBudg(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}

					
				}else{//上年执行
					
					//年度 处理（有 year参数  取参数year减1 ，没有时取当前 会计年度减1）
					if(mapVo.get("year") != null ){
						temp.put("year", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
					}else{
						temp.put("year", Integer.parseInt(String.valueOf(SessionManager.getAcctYear()))-1);
					}
					
					if("01".equals(item.get("element_level"))){
						
						// 查询  收入科目类 元素层次element_level 01 医院年度，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInHYExecute(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){
							
							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					if("02".equals(item.get("element_level"))){
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  收入科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInHMExecute(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("03".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						// 查询  收入科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInDYExecute(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("04".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  收入科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgInDMExecute(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
				}
				
				Map<String,Object> map = new HashMap<String,Object>();

				map.put("stack_seq_no", item.get("stack_seq_no"));
				map.put("group_id", basicMap.get("group_id"));
				map.put("hos_id", basicMap.get("hos_id"));
				map.put("copy_code", basicMap.get("copy_code"));
				
				map.put("code", basicMap.get("code"));
				if("01".equals(item.get("value_type_code"))){
					map.put("count_item", basicMap.get("count_item")+"【本年预算】");
				}else{
					map.put("count_item", basicMap.get("count_item")+"【上年执行】");
				}
				map.put("count_value", basicMap.get("count_value"));
				
				dataList.add(map);
			}
			
			// 支出科目类  计算项数据查询
			//		元素层次element_level 有 01 医院年度、02 医院月份 、 03科室年度、04科室月份 四个层次 ，
			//		预算值类型value_type_code  既有 01本年预算 又有  02上年执行
			if("05".equals(item.get("element_type_code"))){
				
				//构建查询条件 Map
				Map<String,Object> temp = new HashMap<String,Object>();
				
				temp.put("group_id", item.get("group_id"));
				temp.put("hos_id", item.get("hos_id"));
				temp.put("copy_code", item.get("copy_code"));
				temp.put("index_code", item.get("element_code"));
				
				if("01".equals(item.get("value_type_code"))){//预算值类型value_type_code 为 01本年预算 
					
					if(mapVo.get("year") != null ){
						temp.put("year", mapVo.get("year"));
					}else{
						temp.put("year", SessionManager.getAcctYear());
					}
					
					if("01".equals(item.get("element_level"))){
						
						// 查询  支出科目类 元素层次element_level 01 医院年度，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostHYBudg(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					if("02".equals(item.get("element_level"))){
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  支出科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostHMBudg(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("03".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						// 查询  支出科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostDYBudg(temp);
					
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("04".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  支出科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostDMBudg(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}

					
				}else{//预算值类型value_type_code 为 02上年执行
					
					//年度 处理（有 year参数  取参数year减1 ，没有时取当前 会计年度减1）
					if(mapVo.get("year") != null ){
						temp.put("year", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
					}else{
						temp.put("year", Integer.parseInt(String.valueOf(SessionManager.getAcctYear()))-1);
					}
					
					if("01".equals(item.get("element_level"))){
						
						// 查询  支出科目类 元素层次element_level 01 医院年度，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostHYExecute(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){

							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					if("02".equals(item.get("element_level"))){
						
						if( mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  支出科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostHMExecute(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){
							
							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("03".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						// 查询  支出科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostDYExecute(temp);
						
						if(basicMap==null || basicMap.get("count_value") == null){
							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
					if("04".equals(item.get("element_level"))){
						
						if(mapVo.get("dept_id") != null ){
							temp.put("dept_id", mapVo.get("dept_id"));
						}else{
							return "{\"error\":\"参数不足:没有科室参数.\"}";
						}
						
						if(mapVo.get("month") != null ){
							temp.put("month", mapVo.get("month"));
						}else{
							return "{\"error\":\"参数不足:没有月份参数.\"}";
						}
						
						// 查询  支出科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
						basicMap = budgCountProcessMapper.queryBudgCostDMExecute(temp);
					
						if(basicMap==null || basicMap.get("count_value") == null){
							
							//当计算项 没有取到值时 调用 递归 取该计算项 值
							basicMap = recursiveValue(item, indexList);
							
							if(basicMap.get("error") != null ){
								
								return "{\"error\":\"计算项取值错误:"+basicMap.get("error")+"\",\"state\":\"false\"}";
							}else{
								indexList.remove(indexList.size()-1);
							}
						}
					}
					
				}
				
				Map<String,Object> map = new HashMap<String,Object>();

				map.put("stack_seq_no", item.get("stack_seq_no"));
				map.put("group_id", basicMap.get("group_id"));
				map.put("hos_id", basicMap.get("hos_id"));
				map.put("copy_code", basicMap.get("copy_code"));
				
				map.put("code", basicMap.get("code"));
				if("01".equals(item.get("value_type_code"))){
					map.put("count_item", basicMap.get("count_item")+"【本年预算】");
				}else{
					map.put("count_item", basicMap.get("count_item")+"【上年执行】");
				}
				map.put("count_value", basicMap.get("count_value"));
				
				dataList.add(map);
			}
		}
		
		 return ChdJson.toJson(dataList);
	}
	
	/**
	 * 计算项取值  递归   
	 * @param map
	 * @param indexList
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> recursiveValue(Map<String, Object> map,List<String> indexList) throws DataAccessException {
		
		/************** 以下 代码  校验用（  阻止 正在计算中的 计算项 循环调用  引起死循环） **************/
		if(!indexList.contains(String.valueOf(map.get("element_type_code"))+map.get("element_code")+map.get("element_level")+map.get("value_type_code"))){ // 存放 正在计算的 指标
			
			indexList.add(String.valueOf(map.get("element_type_code"))+map.get("element_code")+map.get("element_level")+map.get("value_type_code"));
		}
		
		/************** 以下上代码  校验用（  阻止 正在计算中的 计算项 循环调用  引起死循环）**************/
		
		Map<String,Object> returnMap = new HashMap<String,Object>();//返回 MAP (包含 错误信息 或 计算结果)
		
		//element_type_code 计算元素类型 :  01 基本指标 02 费用指标 03 预算指标 04 收入科目 05 支出科 
		if("01".equals(map.get("element_type_code"))){
			// 计算项 类型为 01 基本指标时  从 BUDG_BASIC_INDEX_GET_WAY(基本指标取值方法)表中 查询其取值方法；
			
			// 根据 计算元素编码  查询 其取值方法
			Map<String,Object> basicMap = budgCountProcessMapper.queryBasicIndexGetWay(map) ;
			
			if (basicMap != null){
				basicMap.put("year", map.get("year"));
				
				basicMap.put("stack_seq_no", map.get("stack_seq_no"));
				
				basicMap.put("value_type_code", map.get("value_type_code"));
				
				basicMap.put("index_type_code", map.get("element_type_code"));//// 指标类型 （调用函数时 使用）
				
				returnMap = indexGetValue(basicMap,indexList);
			}else{
				returnMap.put("error", "计算项取值失败,需手动维护!无法计算.请核对该计算公式各计算项值是否存在");
				
			}
			
		}
		
		//element_type_code 计算元素类型 :  01 基本指标 02 费用指标 03 预算指标 04 收入科目 05 支出科 
		if("02".equals(map.get("element_type_code"))){ 
			
			// 计算项 类型为 02 费用指标时  从 BUDG_CHARGE_STAND_GET_WAY(费用标准取值方法)表中 查询其取值方法；
			// 根据 计算元素编码  查询 其取值方法
			Map<String,Object> basicMap = budgCountProcessMapper.queryChargeStanIndexGetWay(map) ;
			
			basicMap.put("year", map.get("year"));
			
			basicMap.put("stack_seq_no", map.get("stack_seq_no"));
			
			basicMap.put("value_type_code", map.get("value_type_code"));
			
			basicMap.put("index_type_code", map.get("element_type_code"));//// 指标类型 （调用函数时 使用）
			
			returnMap = indexGetValue(basicMap,indexList);
		}
		
		//element_type_code 计算元素类型 :  01 基本指标 02 费用指标 03 预算指标 04 收入科目 05 支出科 
		if("03".equals(map.get("element_type_code"))){
			
			// 计算项 类型为 03 预算指标时  从 BUDG_WORK_EDIT_PLAN(业务预算编制方案)表中 查询其取值方法；
			
			// 根据 计算元素编码  查询 其取值方法
			Map<String,Object> basicMap = budgCountProcessMapper.queryBudgIndexGetWay(map) ;
			
			basicMap.put("year", map.get("year"));
			
			basicMap.put("stack_seq_no", map.get("stack_seq_no"));
			
			basicMap.put("value_type_code", map.get("value_type_code"));
			
			basicMap.put("index_type_code", map.get("element_type_code"));//// 指标类型 （调用函数时 使用）
			
			basicMap.put("dept_id", map.get("dept_id"));
			
			returnMap = subjGetValue(basicMap,indexList);
		}
		
		//element_type_code 计算元素类型 :  01 基本指标 02 费用指标 03 预算指标 04 收入科目 05 支出科 
		if("04".equals(map.get("element_type_code"))){
			
			// 计算项 类型为 04 收入科目时  从 BUDG_MED_INCOME_EDIT_PLAN (医疗收入预算编制方案)表中 查询其取值方法；

			// 根据 计算元素编码  查询 其取值方法
			Map<String,Object> basicMap = budgCountProcessMapper.queryIncomeIndexGetWay(map) ;
			
			basicMap.put("year", map.get("year"));
			
			basicMap.put("stack_seq_no", map.get("stack_seq_no"));
			
			basicMap.put("value_type_code", map.get("value_type_code"));
			
			basicMap.put("index_type_code", map.get("element_type_code"));// 指标类型 （调用函数时 使用）
			
			returnMap = subjGetValue(basicMap,indexList);
			
		}
		//element_type_code 计算元素类型 :  01 基本指标 02 费用指标 03 预算指标 04 收入科目 05 支出科目 
		if("05".equals(map.get("element_type_code"))){
			
			// 计算项 类型为 05 支出科目时  从 BUDG_MED_INCOME_EDIT_PLAN (医疗收入预算编制方案)表中 查询其取值方法；
			
			// 根据 计算元素编码  查询 其取值方法
			//Map<String,Object> basicMap = budgCountProcessMapper.queryCostIndexGetWay(map) ;
			
			//basicMap.put("year", map.get("year"));
			
			//basicMap.put("stack_seq_no", map.get("stack_seq_no"));
			
			returnMap.put("error", "计算项取值失败,需手动维护!无法计算.请核对该计算公式各计算项值是否存在");
		}
		return returnMap;
		
	}
	
	/**
	 * element_type_code 计算元素类型 :  01 基本指标 02 费用指标   计算项 取值
	 * @param basicMap
	 * @param formulaID
	 * @return
	 */
	public Map<String,Object> indexGetValue(Map<String,Object> basicMap,List<String> indexList){
		
		Map<String,Object> returnMap = new HashMap<String,Object>();//返回 MAP (包含 错误信息 或 计算结果)
		
		// 取值方法  01手工录入 02取值函数 03计算公式
		
		if("01".equals(basicMap.get("get_value_way"))){// 01 手工录入时
			
			returnMap.put("error", "计算项取值方法为手工录入,需手动维护.");
			
		}else if("02".equals(basicMap.get("get_value_way"))){// 02 取值函数
			
			basicMap.put("fun_code", basicMap.get("fun_id"));
			
			//查询 函数相关信息
			Map<String,Object> funMap = JSONObject.parseObject(budgFunProcessService.queryFunProcess(basicMap));
			
			if(funMap.get("error") ==null){
				
				List<Map<String,Object>> data = JsonListMapUtil.getListMap(funMap.get("Rows").toString());
				
				returnMap.put("stack_seq_no", basicMap.get("stack_seq_no"));
				returnMap.put("group_id", basicMap.get("group_id"));
				returnMap.put("hos_id", basicMap.get("hos_id"));
				returnMap.put("copy_code", basicMap.get("copy_code"));
				
				returnMap.put("code", basicMap.get("index_code"));
				returnMap.put("count_item", basicMap.get("inex_name"));
				returnMap.put("count_value", data.get(0).get("count_value"));
				
			}else{
				
				returnMap.put("error", "指标【:"+basicMap.get("index_code")+" "+basicMap.get("index_name")+funMap.get("error"));
				
				
			}
			
		}else { // 03 计算公式
			
			if(basicMap.get("formula_id") == null){
				
				returnMap.put("error", "计算项取值方法为计算公式,未维护其计算公式.");
				
			}else{
				// 根据 计算公式ID 查询 计算公式信息  （主要解析 formula_en ,计算 计算值 ）
				Map<String,Object> formula =  budgFormulaSetService.queryByCode(basicMap);
				
				formula.put("year", basicMap.get("year"));
				
				formula.put("index_code", basicMap.get("index_code"));
				
				//查询 计算公式元素栈 数据   格式{"formula_stack" : "element_level@element_code@value_type_code@element_type_code;...."}
				// element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
				//element_code：元素编码
				//value_type_code: 元素值类型  01 本年预算 02 上年执行
				//element_type_code： 元素类型 01 基本指标 02 费用指标 03预算指标 04 收入科目 05支出科目
				Map<String,Object> stack = budgFormulaSetService.queryFormulaStack(formula);
				
				String[] element = stack.get("formula_stack").toString().split(";");
				
				//查询 每个计算项的值 
				Map<String,Object>  countItem = JSONObject.parseObject(queryCountProcess(formula,indexList));
				
				if(countItem.get("error") ==null  && countItem.get("Rows") != null){//计算公式 计算项取值没有错误,并成功取到值时
					//查询 计算公式 每个计算元素值
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
					
					Double value = 0.00 ;
					try {
						
						value = Double.parseDouble(String.valueOf( new ScriptEngineManager().getEngineByName("JavaScript").eval(formula_en)));
						
						//分解比例  保留  小数点后4位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(value);
						
						value =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						returnMap.put("stack_seq_no", basicMap.get("stack_seq_no"));
						returnMap.put("group_id", basicMap.get("group_id"));
						returnMap.put("hos_id", basicMap.get("hos_id"));
						returnMap.put("copy_code", basicMap.get("copy_code"));
						
						returnMap.put("code", basicMap.get("index_code"));
						returnMap.put("count_item", basicMap.get("inex_name"));
						returnMap.put("count_value", value);
					} catch (ScriptException e) {
						
						returnMap.put("error", "计算公式解析失败,无法计算.请核对该计算公式");
					}
				}else{
					
					returnMap.put("error", countItem.get("error"));
					
				}
			}
			
		}
		
		return returnMap ;
	}
	
	/**
	 * 计算项 element_type_code 计算元素类型 : 03预算指标  04 收入科目 05 支出科目  时 取值  
	 * @param basicMap
	 * @param formulaID
	 * @return
	 */
	public Map<String,Object> subjGetValue(Map<String,Object> basicMap,List<String> indexList){
		
		Map<String,Object> returnMap = new HashMap<String,Object>();//返回 MAP (包含 错误信息 或 计算结果)
		
		
		if("03".equals(basicMap.get("edit_method"))){ // 编制方法 01零基预算 02增量预算 03确定预算 04概率预算 (03 确定预算 取值方法对应 02 取值函数 03计算公式)

			
			if(basicMap.get("get_way") == null){ // 取值方法  01手工录入 02取值函数 03计算公式 04历史数据*增长比例 05历史数据+增长额
				
				returnMap.put("error", "计算项取值方法未维护.");
				
			}else if("02".equals(basicMap.get("get_way")) || "03".equals(basicMap.get("get_way"))){
				
				if("02".equals(basicMap.get("get_way"))){// 02 取值函数
					
					basicMap.put("fun_code", basicMap.get("fun_id"));
					
					//查询 函数相关信息
					Map<String,Object> funMap = JSONObject.parseObject(budgFunProcessService.queryFunProcess(basicMap));
					
					if(funMap.get("error") ==null){
						
						List<Map<String,Object>> data = JsonListMapUtil.getListMap(funMap.get("Rows").toString());
						
						returnMap.put("stack_seq_no", basicMap.get("stack_seq_no"));
						returnMap.put("group_id", basicMap.get("group_id"));
						returnMap.put("hos_id", basicMap.get("hos_id"));
						returnMap.put("copy_code", basicMap.get("copy_code"));
						
						returnMap.put("code", basicMap.get("index_code"));
						returnMap.put("count_item", basicMap.get("inex_name"));
						returnMap.put("count_value", data.get(0).get("count_value"));
						
					}else{
						
						returnMap.put("error", "指标【:"+basicMap.get("index_code")+" "+basicMap.get("index_name")+funMap.get("error"));
						
						
					}
					
				} else { // 03 计算公式
					if(indexList.contains(String.valueOf(basicMap.get("element_type_code"))+basicMap.get("element_code")+basicMap.get("element_level")+basicMap.get("value_type_code"))){
						
						returnMap.put("error", "计算公式设置错误.");
						
					}else{
						
						// 根据 计算公式ID 查询 计算公式信息  （主要解析 formula_en ,计算 计算值 ）
						Map<String,Object> formula =  budgFormulaSetService.queryByCode(basicMap);
						
						formula.put("year", basicMap.get("year"));
						
						formula.put("index_code", basicMap.get("index_code"));
						
						formula.put("dept_id", basicMap.get("dept_id"));
						
						//查询 计算公式元素栈 数据   格式{"formula_stack" : "element_level@element_code@value_type_code@element_type_code;...."}
						// element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
						//element_code：元素编码
						//value_type_code: 元素值类型  01 本年预算 02 上年执行
						//element_type_code： 元素类型 01 基本指标 02 费用指标 03预算指标 04 收入科目 05支出科目
						Map<String,Object> stack = budgFormulaSetService.queryFormulaStack(formula);
						
						String[] element = stack.get("formula_stack").toString().split(";");
						
						//查询 每个计算项的值 
						Map<String,Object>  countItem = JSONObject.parseObject(queryCountProcess(formula,indexList));
						
						if(countItem.get("error") ==null  && countItem.get("Rows") != null){//计算公式 计算项取值没有错误,并成功取到值时
							//查询 计算公式 每个计算元素值
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
							
							Double value = 0.00 ;
							try {
								
								value = Double.parseDouble(String.valueOf( new ScriptEngineManager().getEngineByName("JavaScript").eval(formula_en)));
								
								//分解比例  保留  小数点后4位数字 四舍五入		
								BigDecimal  b  =   new   BigDecimal(value);
								
								value =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();  
								
								returnMap.put("stack_seq_no", basicMap.get("stack_seq_no"));
								returnMap.put("group_id", basicMap.get("group_id"));
								returnMap.put("hos_id", basicMap.get("hos_id"));
								returnMap.put("copy_code", basicMap.get("copy_code"));
								
								returnMap.put("code", basicMap.get("index_code"));
								returnMap.put("count_item", basicMap.get("index_name"));
								returnMap.put("count_value", value);
								
							} catch (ScriptException e) {
								
								returnMap.put("error", "计算公式解析失败,无法计算.请核对该计算公式");
							}
						}else{
							
							returnMap.put("error", "计算公式计算元素取值失败,无法计算.请核对该计算公式各计算项值是否存在");
							
						}
					}
				}
				
			}else{
				
				returnMap.put("error", "计算项取值方法错误,请重新维护.");
			}
		}else{
			
			returnMap.put("error", "计算项取值需手动维护.");
		}
		return returnMap ;
	}
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
