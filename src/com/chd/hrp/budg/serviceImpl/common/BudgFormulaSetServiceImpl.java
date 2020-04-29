/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.common.BudgFormulaSetMapper;
import com.chd.hrp.budg.entity.BudgFormulaSet;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
import com.chd.hrp.sys.entity.SupType;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 计算公式
 * @Table:
 * BUDG_FORMULA_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgFormulaSetService")
public class BudgFormulaSetServiceImpl implements BudgFormulaSetService {

	private static Logger logger = Logger.getLogger(BudgFormulaSetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFormulaSetMapper")
	private final BudgFormulaSetMapper budgFormulaSetMapper = null;
	
    
	/**
	 * @Description 
	 * 添加计算公式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		String str  = "" ;
		BudgFormulaSet formulaSet = queryByCode(entityMap);
		
		if( formulaSet != null){
			str += "公式编码:"+entityMap.get("formula_id")+";";
		}
		
		int count = budgFormulaSetMapper.queryNameExist(entityMap);

		if (count > 0 ) {
			
			str += "公式名称:"+entityMap.get("formula_name");
		}
		
		if( str != ""){
			return "{\"error\":\""+str+" 已被占用,请重新添加.\"}";
		}
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] para = String.valueOf(entityMap.get("formula_stack")).split(";");
		
		Map<String,Object> map = new HashMap<String,Object>();// 计算项除重
		
		for(int i = 0 ; i< para.length; i++){
			
			if(map.get(para[i]) == null){
				map.put(para[i], para[i]);
			}
			
		}
		
		int i = 0 ;
		
		for(String key : map.keySet()){
			
			++i;
			
			Map<String,Object> stackMap = new HashMap<String,Object>();
			
			stackMap.put("group_id", entityMap.get("group_id"));
			stackMap.put("hos_id", entityMap.get("hos_id"));
			stackMap.put("copy_code", entityMap.get("copy_code"));
			stackMap.put("formula_id", entityMap.get("formula_id"));
			String element_level =key.split("@")[0];
			String element_code =key.split("@")[1];
			String value_type_code = key.split("@")[2];
			String element_type_code = key.split("@")[3];
			stackMap.put("element_type_code", element_type_code);
			stackMap.put("element_level", element_level);
			stackMap.put("element_code", element_code);
			stackMap.put("value_type_code", value_type_code);
			stackMap.put("stack_seq_no", i);
			addList.add(stackMap);
		}
		
		try {
			
			int state = budgFormulaSetMapper.add(entityMap);
			
			//批量添加计算公式元素栈数据
			budgFormulaSetMapper.addBatchFormulaStack(addList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
			
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加计算公式<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFormulaSetMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新计算公式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			
			int count = budgFormulaSetMapper.queryNameExist(entityMap);

			if (count > 0 ) {
				
				return "{\"error\":\"公式名称:"+entityMap.get("formula_name")+" 已被占用,请重新添加.\"}";
			}
			
			//删除 计算公式元素栈 (先删后加)
			budgFormulaSetMapper.deleteFormulaStack(entityMap);
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> map = new HashMap<String,Object>();// 计算项除重
			
			String[] para = String.valueOf(entityMap.get("formula_stack")).split(";");
			
			for(int i = 0 ; i< para.length; i++){
				
				if(map.get(para[i]) == null){
					map.put(para[i], para[i]);
				}
				
			}
			
			int i = 0 ;
			
			for(String key : map.keySet()){
				
				++i;
			
				Map<String,Object> stackMap = new HashMap<String,Object>();
				
				stackMap.put("group_id", entityMap.get("group_id"));
				stackMap.put("hos_id", entityMap.get("hos_id"));
				stackMap.put("copy_code", entityMap.get("copy_code"));
				stackMap.put("formula_id", entityMap.get("formula_id"));
				String element_level =key.split("@")[0];
				String element_code =key.split("@")[1];
				String value_type_code = key.split("@")[2];
				String element_type_code = key.split("@")[3];
				stackMap.put("element_type_code", element_type_code);
				stackMap.put("element_level", element_level);
				stackMap.put("element_code", element_code);
				stackMap.put("value_type_code", value_type_code);
				stackMap.put("stack_seq_no", i);
				addList.add(stackMap);
			}
				
			int state = budgFormulaSetMapper.update(entityMap);
			//批量添加计算公式元素栈数据
			budgFormulaSetMapper.addBatchFormulaStack(addList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新计算公式<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgFormulaSetMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除计算公式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgFormulaSetMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除计算公式<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			for(Map<String,Object> item : entityList){
				budgFormulaSetMapper.deleteFormulaStack(item);
			}
			
			budgFormulaSetMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加计算公式<BR> 
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
		//判断是否存在对象计算公式
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgFormulaSet> list = (List<BudgFormulaSet>)budgFormulaSetMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgFormulaSetMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgFormulaSetMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集计算公式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgFormulaSet> list = (List<BudgFormulaSet>)budgFormulaSetMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFormulaSet> list = (List<BudgFormulaSet>)budgFormulaSetMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象计算公式<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgFormulaSetMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取计算公式<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgFormulaSet
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgFormulaSetMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取计算公式<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgFormulaSet>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgFormulaSetMapper.queryExists(entityMap);
	}
	
	/**
	 * 计算元素 树加载
	 */
	@Override
	public String queryElementTree(Map<String, Object> entityMap)throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if("01".equals(entityMap.get("element_type"))){
			//基本指标树加载
			list = budgFormulaSetMapper.queryBasicIndexTree(entityMap);
		}
		if("02".equals(entityMap.get("element_type"))){
			//费用标准树加载
			list = budgFormulaSetMapper.queryChargeStanTree(entityMap);
		}
		if("03".equals(entityMap.get("element_type"))){
			//预算指标树加载
			list = budgFormulaSetMapper.queryBudgIndexTree(entityMap);
		}
		if("04".equals(entityMap.get("element_type"))){
			//收入科目指标树加载
			list = budgFormulaSetMapper.queryIncomeSubjTree(entityMap);
		}
		if("05".equals(entityMap.get("element_type"))){
			//支出科目树加载
			list = budgFormulaSetMapper.queryCostSubjTree(entityMap);
		}
		
		if (list.size()>0) {
			for (Map<String,Object> item : list) {
				if("01".equals(item.get("nature")) || "00".equals(item.get("nature")) ){
					jsonResult.append("{pId:'01',");
					jsonResult.append("id:'01@" + item.get("code") + "',");
					jsonResult.append("name:'"+item.get("code")+" "+item.get("name")+ "'},");
					jsonResult.append("{pId:'01@"+item.get("code")+"',id:'01@"+item.get("code")+"@01',name:'"+item.get("name")+ "【本年预算】'"+"},");
					if(!"01".equals(entityMap.get("element_type")) && !"02".equals(entityMap.get("element_type"))){
						jsonResult.append("{pId:'01@"+item.get("code")+"',id:'01@"+item.get("code")+"@02',name:'"+item.get("name")+ "【上年执行】'"+"},");
						jsonResult.append("{pId:'02',");
						jsonResult.append("id:'02@" + item.get("code") + "',");
						jsonResult.append("name:'"+item.get("code")+" "+item.get("name")+ "'},");
						jsonResult.append("{pId:'02@"+item.get("code")+"',id:'02@"+item.get("code")+"@01',name:'"+item.get("name")+ "【本年预算】'"+"},");
						jsonResult.append("{pId:'02@"+item.get("code")+"',id:'02@"+item.get("code")+"@02',name:'"+item.get("name")+ "【上年执行】'"+"},");
					}
					
				}
				if("02".equals(item.get("nature")) || "00".equals(item.get("nature")) ){
					jsonResult.append("{pId:'03',");
					jsonResult.append("id:'03@" + item.get("code") + "',");
					jsonResult.append("name:'"+item.get("code")+" "+item.get("name")+ "'},");
					jsonResult.append("{pId:'03@"+item.get("code")+"',id:'03@"+item.get("code")+"@01',name:'"+item.get("name")+ "【本年预算】'"+"},");
					if(!"01".equals(entityMap.get("element_type")) && !"02".equals(entityMap.get("element_type"))){
						jsonResult.append("{pId:'03@"+item.get("code")+"',id:'03@"+item.get("code")+"@02',name:'"+item.get("name")+ "【上年执行】'"+"},");
						jsonResult.append("{pId:'04',");
						jsonResult.append("id:'04@" + item.get("code") + "',");
						jsonResult.append("name:'"+item.get("code")+" "+item.get("name")+ "'},");
						jsonResult.append("{pId:'04@"+item.get("code")+"',id:'04@"+item.get("code")+"@01',name:'"+item.get("name")+ "【本年预算】'"+"},");
						jsonResult.append("{pId:'04@"+item.get("code")+"',id:'04@"+item.get("code")+"@02',name:'"+item.get("name")+ "【上年执行】'"+"},");
					}
					
				}
			}
		}
		jsonResult.append("{pId:'0',id:'01',name:'医院年度'"+"},");
		jsonResult.append("{pId:'0',id:'03',name:'科室年度'"+"},");
		
		if( !"01".equals(entityMap.get("element_type")) && !"02".equals(entityMap.get("element_type"))){
			jsonResult.append("{pId:'0',id:'02',name:'医院月份'"+"},");
			jsonResult.append("{pId:'0',id:'04',name:'科室月份'"+"},");
		}
		
		jsonResult.append("]}");
		return jsonResult.toString();
	}
	
	/**
	 * 查询 计算公式元素栈 数据
	 */
	@Override
	public Map<String, Object> queryFormulaStack(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<Map<String,Object>> list=  budgFormulaSetMapper.queryFormulaStack(mapVo);
		String formula_stack = "";
		
		for(Map<String,Object> item : list){
			
			formula_stack += item.get("element_level") +"@" + item.get("element_code") +"@" 
						+ item.get("value_type_code") +"@" + item.get("element_type_code") + ";";
		}
		
		map.put("formula_stack", formula_stack.substring(0, formula_stack.length()-1));
		
		return map;

	}
	
}
