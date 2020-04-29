/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationbasic.deptstandard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.budg.dao.business.compilationbasic.deptstandard.BudgDeptChargeStanMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.dpetexecute.BudgWorkDeptYearExecuteMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.hosstandard.BudgHosChargeStanMapper;
import com.chd.hrp.budg.entity.BudgDeptChargeStan;
import com.chd.hrp.budg.service.business.compilationbasic.deptstandard.BudgDeptChargeStanService;
import com.chd.hrp.budg.service.common.BudgCountProcessService;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
import com.chd.hrp.budg.service.common.BudgFunProcessService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室费用标准维护
 * @Table:
 * BUDG_DEPT_CHARGE_STAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgDeptChargeStanService")
public class BudgDeptChargeStanServiceImpl implements BudgDeptChargeStanService {

	private static Logger logger = Logger.getLogger(BudgDeptChargeStanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDeptChargeStanMapper")
	private final BudgDeptChargeStanMapper budgDeptChargeStanMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgHosChargeStanMapper")
	private final BudgHosChargeStanMapper budgHosChargeStanMapper = null;
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	//引入DAO操作
	@Resource(name = "budgWorkDeptYearExecuteMapper")
	private final BudgWorkDeptYearExecuteMapper budgWorkDeptYearExecuteMapper = null;
	
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
		
		List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();//科室年度业务概率预算  用List
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
			
		}
		
		try {
			
			if(addList.size()>0){
				//批量 查询 添加数据是否已存在
				String  str = budgDeptChargeStanMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgDeptChargeStanMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				
				int state = budgDeptChargeStanMapper.updateBatch(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 添加科室费用标准维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室费用标准维护
		BudgDeptChargeStan budgDeptChargeStan = queryByCode(entityMap);

		if (budgDeptChargeStan != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgDeptChargeStanMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室费用标准维护<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		
		try {
			
			for(Map<String,Object> item:entityList){
				int count = budgDeptChargeStanMapper.queryDeptChargeStanExist(item);
				
				if(count > 0){
					str += item.get("year") +"年度"+item.get("dept_name") +"科室 "+item.get("charge_stan_code") +"费用标准；" ;
				}
			}
			
			if(str != ""){
				
				return "{\"error\":\"添加失败！以下:【"+str+"】数据已存在！\"}";
			}else{
				
				budgDeptChargeStanMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			
		

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室费用标准维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgDeptChargeStanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室费用标准维护<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgDeptChargeStanMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室费用标准维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgDeptChargeStanMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室费用标准维护<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDeptChargeStanMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室费用标准维护<BR> 
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
		//判断是否存在对象科室费用标准维护
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgDeptChargeStan> list = (List<BudgDeptChargeStan>)budgDeptChargeStanMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgDeptChargeStanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgDeptChargeStanMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室费用标准维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDeptChargeStanMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDeptChargeStanMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室费用标准维护<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptChargeStanMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室费用标准维护<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgDeptChargeStan
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptChargeStanMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室费用标准维护<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgDeptChargeStan>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgDeptChargeStanMapper.queryExists(entityMap);
		
	}
	/**
	 * 校验 费用标准 是否存在
	 */
	@Override
	public int queryChargeStanCodeExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgDeptChargeStanMapper.queryChargeStanCodeExist(mapVo);
		
	}
	
	/**
	 * 生成时 查询 生成数据
	 */
	@Override
	public List<Map<String, Object>> queryBudgDeptChargeStanData(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgDeptChargeStanMapper.queryBudgDeptChargeStanData(mapVo);
	}
	
	/**
	 * 查询 科室费用标准维护数据 是否已存在
	 */
	@Override
	public int queryDeptChargeStanExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgDeptChargeStanMapper.queryDeptChargeStanExist(mapVo);
		
	}
	
	/**
	 * 根据 费用标准编码 科室编码 查询  预算科室ID
	 */
	@Override
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgDeptChargeStanMapper.queryDeptID(mapVo);
	}
	@Override
	public String budgDeptChargeStanImport(Map<String, Object> map) throws DataAccessException {
		 try{
				
				String content=map.get("content").toString();
				
				List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
				
				if(liData==null || liData.size()==0){
					return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
				}
				
				Map<String, Object> entityMap=new HashMap<String,Object>();
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				
				//查询指标编码
				List<Map<String,Object>> listSourceIdDict = budgHosChargeStanMapper.queryBudgHosChargeStanCode(entityMap);		
				// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
				Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
			
				for(Map<String, Object> dict : listSourceIdDict){
					if(dict.get("charge_stan_name") != null){
						dictMap.put(dict.get("charge_stan_name").toString(), dict);//改成name
						
					}
				}
				
				//查询科室编码
				List<Map<String,Object>> listDeptIdDict = budgWorkDeptYearExecuteMapper.queryBudgDeptId(entityMap);		
				// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
				Map<String, Map<String, Object>> dictDeptMap = new HashMap<String, Map<String, Object>>();
			
				for(Map<String, Object> dict : listDeptIdDict){
					if(dict.get("dept_name") != null){
						dictDeptMap.put(dict.get("dept_name").toString(), dict);//改成name
						
					}
				}
				List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
				
				
				for(Map<String,List<String>> item : liData ){
			       
		
					List<String> budg_year = item.get("年度") ;
					
					List<String> charge_stan_name = item.get("费用标准名称") ;
					
					List<String> budg_value = item.get("预算值") ;
					
					List<String> remark = item.get("说明") ;
					
					List<String> dept_name = item.get("科室名称") ;
					
					if(budg_year == null){
						
						return "{\"warn\":\"" + budg_year.get(0) + ",年度为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_year.get(0) + "\"}";
						
					}
					
					if(charge_stan_name == null){
						//get(0)获取坐标
						return "{\"warn\":\"" + charge_stan_name.get(0) + ",费用标准名称！\",\"state\":\"false\",\"row_cell\":\"" + charge_stan_name.get(0) + "\"}";
					
					}else{
						
						if(dictMap.get(charge_stan_name.get(1)) == null){
							//get(1)获取单元格的值
							return "{\"warn\":\"" + charge_stan_name.get(0) + ",费用标准名称！\",\"state\":\"false\",\"row_cell\":\"" + charge_stan_name.get(0) + "\"}";
						}
					}
					if(dept_name == null){
						//get(0)获取坐标
						return "{\"warn\":\"" + dept_name.get(0) + ",部门名称！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					
					}else{
						
						if(dictDeptMap.get(dept_name.get(1)) == null){
							//get(1)获取单元格的值
							return "{\"warn\":\"" + dept_name.get(0) + ",部门名称！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if(budg_value == null){
						
						return "{\"warn\":\"" + budg_value.get(0) + ",预算值为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_value.get(0) + "\"}";
						
					}else{
						
						 try{
							    Double.parseDouble((budg_value.get(1)));
							   
						 }catch(NumberFormatException e){
							 
							 return "{\"warn\":\"" + budg_value.get(0) + ",预算值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + budg_value.get(0) + "\"}";
						  }
						
					}
	                
	               
					
					//存放 正确信息用Map
					Map<String,Object> returnMap = new HashMap<String,Object>();
					
					returnMap.put("group_id", SessionManager.getGroupId());
					
					returnMap.put("hos_id", SessionManager.getHosId());
					
					returnMap.put("copy_code", SessionManager.getCopyCode());
					
					returnMap.put("remark", remark.get(1));
								
					returnMap.put("year", budg_year.get(1));
					
					returnMap.putAll(dictDeptMap.get(dept_name.get(1)));
					
					returnMap.put("budg_value", budg_value.get(1));
					
					returnMap.putAll(dictMap.get(charge_stan_name.get(1)));
					
					returnList.add(returnMap);
					
				}
				StringBuffer err_sb = new StringBuffer();
				
				//校验 数据重复
				for( int i = 0; i < returnList.size(); i++ ){
					
					for(int j = i + 1 ; j < returnList.size(); j++){
						
						
						if(returnList.get(i).get("year").equals(returnList.get(j).get("year")) && returnList.get(i).get("charge_stan_name").equals(returnList.get(j).get("charge_stan_name"))
								&& returnList.get(i).get("dept_name").equals(returnList.get(j).get("dept_name"))
						){	
							err_sb.append(returnList.get(i).get("year")+"年度,"+returnList.get(i).get("charge_stan_name")+"费用标准名称,"+returnList.get(i).get("dept_name")+"科室名称");  
						}
					}
					
				}
				
				if( err_sb.length() > 0){
					
					 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
					
				}else{
				
					String str = addBatch(returnList);
					
					if(!"".equals(JSONObject.parseObject(str).get("error"))){
						
						return str ;
						
					}else{
						
						return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
					}
					
				}
		 }catch(Exception e){
				e.printStackTrace();
				logger.debug(e.getMessage());
				throw new SysException(e.getMessage());
		 }

	}
	
	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String collectBudgDeptChargeStan(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgDeptChargeStanMapper.query(mapVo);
			
			if(list.size()>0){
				for(Map<String,Object> item : list){
					//空值处理
					if(item.get("remark") == null ){
						item.put("remark", "");
					}
					
					if(item.get("budg_value") == null ){
						item.put("budg_value", "");
					}
					
					//根据 所传费用指标编码 查询其取值方法
					Map<String, Object> map = budgHosChargeStanMapper.queryIndexGetWay(item);
					
					
					if(map != null){
						
						if(map.get("formula_id") != null){//计算公式取值
							
							map.put("year", item.get("year"));
							
							Map<String,Object> formula =  budgFormulaSetService.queryByCode(map);
							
							formula.put("year", item.get("year"));
							
							formula.put("index_code", item.get("charge_stan_code"));
							
							formula.put("dept_id", item.get("dept_id"));
							
							formula.put("element_type_code", "02");//元素类型 01 基本指标     02 费用指标 03预算指标 04 收入科目 05支出科目
							
							formula.put("element_level", "01");//element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
							
							formula.put("value_type_code", "01");//value_type_code:元素值类型  01 本年预算 02 上年执行
							
							List<String> indexList = new ArrayList<String>();
							
							indexList.add(String.valueOf(formula.get("element_type_code"))+item.get("charge_stan_code")+formula.get("element_level")+"01");//递归校验用
							
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
									
								} catch (ScriptException e) {
									
									return "{\"error\":\"指标【:"+item.get("charge_stan_code")+" "+item.get("charge_stan_name")+"对应计算公式解析失败,无法计算.请核对该计算公式\",\"state\":\"false\"}";
								}
							}else{
								
								return "{\"error\":\"指标【:"+item.get("charge_stan_code")+" "+item.get("charge_stan_name")+countItem.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
						
						if(map.get("fun_id") != null){ //取值函数 取值
							
							map.put("year", item.get("year"));
							
							map.put("fun_code", map.get("fun_id"));
							
							map.put("index_type_code", "02");//指标类型  01基本指标 02费用标准 03预算指标 04收入科目
							
							//查询 函数相关信息
							Map<String,Object> funMap = JSONObject.parseObject(budgFunProcessService.queryFunProcess(map));
							
							if(funMap.get("error") ==null){
								
								List<Map<String,Object>> data = JsonListMapUtil.getListMap(funMap.get("Rows").toString());
								
								item.put("budg_value", data.get(0).get("count_value"));
								
							}else{
								
								return "{\"error\":\"指标【:"+item.get("charge_stan_code")+" "+item.get("charge_stan_name")+funMap.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
					}
				}
				
				budgDeptChargeStanMapper.updateBatch(list);
				
				return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"error\":\"没有计算数据.\",\"state\":\"false\"}";
				
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
		
	}
	
}
