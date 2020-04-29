/** 
F * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.adjust;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.budg.dao.project.adjust.BudgProjAdjDetailMapper;
import com.chd.hrp.budg.dao.project.adjust.BudgProjAdjMapper;
import com.chd.hrp.budg.entity.BudgProjAdj;
import com.chd.hrp.budg.entity.BudgProjAdjDetail;
import com.chd.hrp.budg.entity.BudgProjDetailYear;
import com.chd.hrp.budg.service.project.adjust.BudgProjAdjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 状态（STATE），取自系统字典表
 * “01新建、02已提交、03已审核”
 * @Table:
 * BUDG_PROJ_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjAdjService")
public class BudgProjAdjServiceImpl implements BudgProjAdjService {

	private static Logger logger = Logger.getLogger(BudgProjAdjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjAdjMapper")
	private final BudgProjAdjMapper budgProjAdjMapper = null;
	
	@Resource(name = "budgProjAdjDetailMapper")
	private final BudgProjAdjDetailMapper budgProjAdjDetailMapper = null;
	
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;
	
    
	/**
	 * @Description 
	 * 添加状态（STATE），取自系统字典表
     * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
				
		try {
			
			String str = "";
			
			//BUDG_PROJ_adj_detail 项目预算申调整明细  添加用List
			List<Map<String,Object>> adjList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("adjData")));
			
			//定义map用于接收json中的source_id和adj_amount   用于判断同资金来源下  调整额总和是否为0  是  执行添加  不是  则返回错误信息
			Map<String, Object> map  = new HashMap<String, Object>();
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				//获取jsonObj中的source_id (key)和 source_name,adj_amount (value)   不存在  添加       存在 则adj_amount相加保存
				if(map.get(String.valueOf(jsonObj.get("source_id")))==null){
					map.put(String.valueOf(jsonObj.get("source_id")), jsonObj.get("source_name")+","+jsonObj.get("adj_amount"));
				}else{
					Double adjAmount = Double.parseDouble(String.valueOf(jsonObj.get("adj_amount")));
					Double mapValue = Double.parseDouble(String.valueOf(map.get(String.valueOf(jsonObj.get("source_id")))).split(",")[1]);
					Double value = adjAmount + mapValue;
					//将资金来源名称 和 计算后的数值再次组装后  存入map
					map.put(String.valueOf(jsonObj.get("source_id")), String.valueOf(map.get(String.valueOf(jsonObj.get("source_id")))).split(",")[0]+","+value);
				}
				
				//封装调整明细 数据信息
				Map<String,Object> adjMap = new HashMap<String,Object>();
				
				adjMap.put("group_id", String.valueOf(entityMap.get("group_id")));
				adjMap.put("hos_id", String.valueOf(entityMap.get("hos_id")));
				adjMap.put("copy_code", String.valueOf(entityMap.get("copy_code")));
				adjMap.put("budg_year",String.valueOf(entityMap.get("budg_year")));//年度
				adjMap.put("adj_code", String.valueOf(entityMap.get("adj_code")));//预算申报单号
				adjMap.put("proj_id",String.valueOf(entityMap.get("proj_id")));//项目ID
				adjMap.put("source_id",String.valueOf(jsonObj.get("source_id")));//资金来源ID
				adjMap.put("payment_item_id",String.valueOf(jsonObj.get("payment_item_id")).split(",")[1]);//支出项目ID
				adjMap.put("payment_item_no",String.valueOf(jsonObj.get("payment_item_id")).split(",")[0]);//支出项目变更id
				adjMap.put("adj_amount",  String.valueOf(jsonObj.get("adj_amount")));//调整金额
				adjList.add(adjMap);
			}	
			
			for (String key : map.keySet()) {
				//切割map中的value值  获取得到的调整余额总数
				Double sumAdjAmount = Double.parseDouble(String.valueOf(map.get(key)).split(",")[1]);
				String sourceName = String.valueOf(map.get(key)).split(",")[0];
				if(sumAdjAmount < -0.000001 || sumAdjAmount > 0.000001){
					str += sourceName;
				}
			}
			if(!"".equals(str)){
				return "{\"error\":\"资金来源:"+str+"下各支出项目调整额总和不为0,请检查后操作\",\"state\":\"false\"}";
			}
			
			 budgProjAdjMapper.add(entityMap);
			 
			 budgProjAdjDetailMapper.addBatch(adjList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加状态（STATE），取自系统字典表
     * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjAdjMapper.addBatch(entityList);
			
			budgProjAdjDetailMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败.\",\"state\":\"false\"}"); 
			
		}
		
	}
	
		/**
	 * @Description 
	 * 更新状态（STATE），取自系统字典表
     * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			String str = "";
			
			//BUDG_PROJ_adj_detail 项目预算申调整明细  添加用List
			List<Map<String,Object>> adjList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("adjData")));
			
			//定义map用于接收json中的source_id和adj_amount   用于判断同资金来源下  调整额总和是否为0  是  执行添加  不是  则返回错误信息
			Map<String, Object> map  = new HashMap<String, Object>();
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				//获取jsonObj中的source_id (key)和 source_name,adj_amount (value)   不存在  添加       存在 则adj_amount相加保存
				if(map.get(String.valueOf(jsonObj.get("source_id")))==null){
					map.put(String.valueOf(jsonObj.get("source_id")), jsonObj.get("source_name")+","+jsonObj.get("adj_amount"));
				}else{
					Double adjAmount = Double.parseDouble(String.valueOf(jsonObj.get("adj_amount")));
					Double mapValue = Double.parseDouble(String.valueOf(map.get(String.valueOf(jsonObj.get("source_id")))).split(",")[1]);
					Double value = adjAmount + mapValue;
					
					//将资金来源名称 和 计算后的数值再次组装后  存入map
					map.put(String.valueOf(jsonObj.get("source_id")), String.valueOf(map.get(String.valueOf(jsonObj.get("source_id")))).split(",")[0]+","+value);
				}
				
				
				Map<String,Object> adjMap = new HashMap<String,Object>();
				
				adjMap.put("group_id", String.valueOf(entityMap.get("group_id")));
				adjMap.put("hos_id", String.valueOf(entityMap.get("hos_id")));
				adjMap.put("copy_code", String.valueOf(entityMap.get("copy_code")));
				adjMap.put("budg_year",String.valueOf(entityMap.get("budg_year")));//年度
				adjMap.put("adj_code", String.valueOf(entityMap.get("adj_code")));//预算申报单号
				adjMap.put("proj_id",String.valueOf(entityMap.get("proj_id")));//项目ID
				adjMap.put("source_id",String.valueOf(jsonObj.get("source_id")));//资金来源ID
				adjMap.put("payment_item_id",String.valueOf(jsonObj.get("payment_item_id")).split(",")[1]);//支出项目ID
				adjMap.put("payment_item_no",String.valueOf(jsonObj.get("payment_item_id")).split(",")[0]);//支出项目变更id
				adjMap.put("adj_amount",  String.valueOf(jsonObj.get("adj_amount")));//调整金额
				adjList.add(adjMap);
			}	
			
			for (String key : map.keySet()) {
				//切割map中的value值  获取得到的调整余额总数
				Double sumAdjAmount = Double.parseDouble(String.valueOf(map.get(key)).split(",")[1]);
				String sourceName = String.valueOf(map.get(key)).split(",")[0];
				//floa或double：最大负数是：-0.000001 最小正数：+0.000001         只要(sub > -0.000001 && sub < +0.000001),那么这个数就是0了
				if(sumAdjAmount < -0.000001 || sumAdjAmount > 0.000001 ){
					str += sourceName;
				}
			}
			if(!"".equals(str)){
				return "{\"error\":\"添加失败! 资金来源:"+str+"下各支出项目调整额总和不为0,请检查后操作\",\"state\":\"false\"}";
			}
			
			budgProjAdjMapper.update(entityMap);
			//先删除   后添加
			budgProjAdjDetailMapper.delete(entityMap);
			
			budgProjAdjDetailMapper.addBatch(adjList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}") ;

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新状态（STATE），取自系统字典表
     * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgProjAdjMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除状态（STATE），取自系统字典表
     * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgProjAdjMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除状态（STATE），取自系统字典表
     * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjAdjDetailMapper.deleteBatch(entityList);
			
			budgProjAdjMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException( "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}" );

		}	
	}
	
	/**
	 * @Description 
	 * 添加状态（STATE），取自系统字典表
	 * “01新建、02已提交、03已审核”<BR> 
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
		//判断是否存在对象状态（STATE），取自系统字典表
			//“01新建、02已提交、03已审核”
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgProjAdj> list = (List<BudgProjAdj>)budgProjAdjMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgProjAdjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgProjAdjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集状态（STATE），取自系统字典表
     * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjAdjMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjAdjMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
		
	/**
	 * @Description 
	 * 获取对象状态（STATE），取自系统字典表
	 * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjAdjMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取状态（STATE），取自系统字典表
	 * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgProjAdj
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjAdjMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取状态（STATE），取自系统字典表
	 * “01新建、02已提交、03已审核”<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgProjAdj>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjAdjMapper.queryExists(entityMap);
	}
	
	/**
	 * @Description 
	 * 生成 预算调整单号
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String setBudgApplyCode(Map<String,Object> mapVo) throws DataAccessException{
		
		mapVo.put("month", "");
		mapVo.put("table_code", "BUDG_PROJ_ADJ") ;
		mapVo.put("table_name", "项目预算调整表") ;
		mapVo.put("prefixe", "XMYSTZ") ;
		mapVo.put("seq_no", 6);
		
		BudgNoManager budgNoManager = budgNoManagerMapper.queryBudgNoManagerByCode(mapVo);
		
		String pref = "XMYSTZ";

		String max_no = "1";
		
		String no = "1" ;
		if(budgNoManager == null){
			
			mapVo.put("max_no", 1);
			
			budgNoManagerMapper.addBudgNoManager(mapVo);
			
		}else{
			
			max_no = String.valueOf(budgNoManager.getMax_no());
			
			no = max_no ;
			
			budgNoManagerMapper.updateBudgNoManagerMaxNo(mapVo);
		}
		
		for(int i= 0 ; i< 6- no.length() ; i++){
			
			max_no = "0"+ max_no ;
			
		}
		
		return pref + mapVo.get("year") + max_no;
	}
	
	/**
	 * 提交
	 */
	@Override
	public String updateSubmitState(List<Map<String, Object>> entityList)
			throws DataAccessException {
		
		try {
			//定义字符串  拼接错误信息
			StringBuilder str = new StringBuilder();
			
			for (Map<String, Object> map : entityList) {
				
				//获取单号  用作拼接错误信息 返回前台
				String adjCode = String.valueOf(map.get("adj_code"));
				
				String state = budgProjAdjMapper.queryState(map);
				if(!"01".equals(state)){
					
					str.append(adjCode+" ");
					
				}else{
					
					//提交时  更改制单人  制单日期为提交人  提交日期
					map.put("maker", SessionManager.getUserId());
					//获取当前日期  Date类型
					Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
					map.put("make_date", date);
					
					//提交状态时  更改状态为已提交
					map.put("state", "02");
				}
				
			}
			if(str.length() != 0){
				return "{\"error\":\"调整单号:"+str+"的数据状态不是新建状态 , 不能执行提交操作!\"}";
			}
			
			budgProjAdjMapper.updateSubmitState(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员! \"}");

		}	
		
	}
	
	/**
	 * 撤回提交
	 */
	@Override
	public String updateRecallState(List<Map<String, Object>> entityList)
			throws DataAccessException {
		
		try {
			//定义字符串  拼接错误信息
			StringBuilder str = new StringBuilder();
			
			for (Map<String, Object> map : entityList) {
				
				//获取单号 
				String adjCode = String.valueOf(map.get("adj_code"));
				
				String state = budgProjAdjMapper.queryState(map);
				if(!"02".equals(state)){
					
					str.append(adjCode+" ");
					
				}else{
					
					//撤回提交时  更改提交人  提交日期 为 ""
					map.put("maker", "");
					
					map.put("make_date", "");
					
					//提交状态时  更改状态为已提交
					map.put("state", "01");
				}
				
			}
			if(str.length() != 0){
				return "{\"error\":\"调整单号:"+str+"的数据状态不是提交状态 , 不能执行撤回提交操作!\"}";
			}
			
			budgProjAdjMapper.updateSubmitState(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员! \"}");
		}	
		
	}
	/**
	 * 审核  销审 更新状态
	 */
	@Override
	public String updateReviewState(List<Map<String, Object>> entityList)
			throws DataAccessException {
		try {
			
			//添加 年度项目预算明细用List
			List<Map<String,Object>> addYearDetailList = new ArrayList<Map<String,Object>>();
			
			//修改 年度项目预算明细用List
			List<Map<String,Object>> upadateYearDetailList = new ArrayList<Map<String,Object>>();
			
			//定义字符串  拼接错误信息
			String str = "";
			 
			for (Map<String, Object> map : entityList) {
				
				//根据项目调整主表 主键  查询项目调整明细集合
				List<Map<String, Object>> adjDetailList  = budgProjAdjDetailMapper.queryAdjDetailByCode(map);
				
				//遍历项目调整明细集合   根据主键 查询 年度明细  并修改数据
				for (Map<String, Object> mapVo : adjDetailList) {
					
					//查询出年度数据     更改数据
				Map<String, Object> yearData = budgProjAdjMapper.queryYearDetailByCode(mapVo);
					
					//从 项目预算调整明细中  查询出  对应的调整金额
					Double adjAmount = Double.parseDouble(String.valueOf(mapVo.get("adj_amount")));
					
					if(yearData != null){
						
						//本期预算金额  = 原本期预算金额 + 调整金额 ; BUDG_AMOUNT
						Double newBudgAmount = Double.parseDouble(String.valueOf(yearData.get("budg_amount"))) + adjAmount;
						
						//更新本期预算金额
						yearData.put("budg_amount", newBudgAmount);
						
						//预算金额累计 = 原预算金额累计 + 调整金额 ; Y_BUDG_AMOUNT
						Double newYBudgAmount = Double.parseDouble(String.valueOf(yearData.get("y_budg_amount"))) + adjAmount;
						
						//更新预算金额累计
						yearData.put("y_budg_amount", newYBudgAmount);
						
						//预算余额  =  原预算余额 + 调整金额 ;  REMAIN_AMOUN
						Double newRemainAmoun = Double.parseDouble(String.valueOf(yearData.get("remain_amoun"))) + adjAmount;
						
						//更新预算余额
						yearData.put("remain_amoun", newRemainAmoun);
						
						//本年执行进度 = 本期支出金额 / 新本期预算金额 ; RATE
						Double newRate = Double.parseDouble(String.valueOf(yearData.get("cost_amount"))) / newBudgAmount ; 
						
						//更新预算余额
						yearData.put("rate", newRate);
						
						//累计执行进度 = 累计支出金额 / 新累计预算金额 ; T_RATE
						Double newTRate = Double.parseDouble(String.valueOf(yearData.get("y_cost_amount"))) / newYBudgAmount ; 
						
						//更新预算余额
						yearData.put("t_rate", newTRate);
						
						//将更新后的数据 加入更新集合中  传入sql
						upadateYearDetailList.add(yearData) ; 	
						
					}else{//数据不存在  则将所有需要添加调整金额的字段  置为0.00+调整金额  即将该数据置为 调整金额
						
						//定义添加集合  用于添加数据 插入数据库
						Map<String,Object> addMap = new HashMap<String,Object>();
						
						//将对应的项目明细数据添加到  年度明细添加集合中  主要目的是封装主键
						addMap.putAll(mapVo);
						
						//本期预算金额
						addMap.put("budg_amount", adjAmount);
						
						//预算金额累计
						addMap.put("y_budg_amount", adjAmount);
						
						//预算余额
						addMap.put("remain_amoun", adjAmount);
						
						//执行进度
						addMap.put("rate", 0.00);
						
						//累计执行进度
						addMap.put("t_rate", 0.00);
						//期初预算金额
						addMap.put("b_budg_amount", 0.00);
						//期初支出金额
						addMap.put("b_cost_amount", 0.00);
						//期初预算余额
						addMap.put("b_remain_amoun", 0.00);
						//本期支出金额
						addMap.put("cost_amount", 0.00);
						//支出金额累计
						addMap.put("y_cost_amount", 0.00);
						
						/*//添加父表字段
						addMap.put("b_in_amount", 0.00);
						addMap.put("b_usable_amount", 0.00);
						addMap.put("in_amount", 0.00);
						addMap.put("t_in_amount", 0.00);
						addMap.put("t_cost_amount", 0.00);
						addMap.put("t_budg_amount", 0.00);
						addMap.put("remain_amount", 0.00);
						addMap.put("usable_amount", 0.00);
						addMap.put("remain_adj", 0.00);
						*/
						
						//将添加后的数据 加入添加集合中  传入sql
						addYearDetailList.add(addMap) ;
					}
					
				}
				
				if(addYearDetailList.size() > 0){
					//添加数据
					budgProjAdjMapper.addYearDetailBatch(addYearDetailList);
				}
				
				if(upadateYearDetailList.size() > 0){
					//更新年度明细数据
					budgProjAdjMapper.updateYearDetailBatch(upadateYearDetailList);
				}
				
				//更新年度明细后  更改自身数据状态 为已审核
				String adjCode = String.valueOf(map.get("adj_code"));//获取单号 
				
				String state = budgProjAdjMapper.queryState(map);
				//02 提交状态  下允许审核
				if(!"02".equals(state)){
					
					str += adjCode+" ";
					
				}else{
					//审核时  更改审核人  审核日期 为 当前操作人  操作日期
					map.put("checker", SessionManager.getUserId());
					
					//获取当前日期  Date类型
					Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
					map.put("check_date", date);
					
					//提交状态时  更改状态为已审核
					map.put("state", "03");
				}
				
			}
			if(str.length() != 0){
				return "{\"error\":\"调整单号:"+str+"的数据状态不是已提交状态 , 不能执行审核操作!\"}";
			}
			
			budgProjAdjMapper.updateReviewState(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员! \"}");

		}	
		
	}
	
	
	/**
	 * 销审    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateCancelBatch(List<Map<String, Object>> entityList)
			throws DataAccessException {
			try {
				//修改 年度项目预算明细用List
				List<Map<String,Object>> upadateYearDetailList = new ArrayList<Map<String,Object>>();
				
				//定义字符串  拼接错误信息
				StringBuilder str = new StringBuilder();
				 
				for (Map<String, Object> map : entityList) {
					
					//根据项目调整主表 主键  查询项目调整明细集合
					List<Map<String, Object>> adjDetailList  = budgProjAdjDetailMapper.queryAdjDetailByCode(map);
					
					//遍历项目调整明细集合   根据主键 查询 年度明细  并修改数据
					for (Map<String, Object> mapVo : adjDetailList) {
						
						//查询出年度数据     更改数据
						Map<String,Object> yearData = budgProjAdjMapper.queryYearDetailByCode(mapVo);
						
						//从 项目预算调整明细中  查询出  对应的调整金额
						Double adjAmount = Double.parseDouble(String.valueOf(mapVo.get("adj_amount")));
						
						//本期预算金额  = 原本期预算金额 + 调整金额 ; BUDG_AMOUNT
						Double newBudgAmount = Double.parseDouble(String.valueOf(yearData.get("budg_amount"))) - adjAmount;
						
						//更新本期预算金额
						yearData.put("budg_amount", newBudgAmount);
						
						//预算金额累计 = 原预算金额累计 + 调整金额 ; Y_BUDG_AMOUNT
						Double newYBudgAmount = Double.parseDouble(String.valueOf(yearData.get("y_budg_amount"))) - adjAmount;
						
						//更新预算金额累计
						yearData.put("y_budg_amount", newYBudgAmount);
						
						//预算余额  =  原预算余额 + 调整金额 ;  REMAIN_AMOUN
						Double newRemainAmoun = Double.parseDouble(String.valueOf(yearData.get("remain_amoun"))) - adjAmount;
						
						//更新预算余额
						yearData.put("remain_amoun", newRemainAmoun);
						
						//本年执行进度 = 本期支出金额 / 新本期预算金额 ; RATE
						if(newBudgAmount > 0){
							Double newRate = Double.parseDouble(String.valueOf(yearData.get("cost_amount"))) / newBudgAmount ; 
							//更新预算余额
							yearData.put("rate", newRate);
						}else{
							yearData.put("rate", 0.00);
						}
						
						//累计执行进度 = 累计支出金额 / 新累计预算金额 ; T_RATE
						if(newYBudgAmount > 0 ){
							Double newTRate = Double.parseDouble(String.valueOf(yearData.get("y_cost_amount"))) / newYBudgAmount ; 
							//更新预算余额
							yearData.put("t_rate", newTRate);
						}else{
							yearData.put("t_rate", 0.00);
						}
						
						//将更新后的数据 加入更新集合中  传入sql
						upadateYearDetailList.add(yearData) ; 	
					}
					
					//更新年度明细数据
					budgProjAdjMapper.updateYearDetailBatch(upadateYearDetailList);
					
					//更新年度明细后  更改自身数据状态 为已提交
					//获取单号 
					String adjCode = String.valueOf(map.get("adj_code"));
					
					String state = budgProjAdjMapper.queryState(map);
					//03 审核状态  下允许销审
					if(!"03".equals(state)){
						
						str.append(adjCode+" ");
						
					}else{
						//销审时  更改审核人  审核日期 为 ""
						map.put("checker","");
						
						map.put("check_date", "");
						
						//销审时  更改状态为已审核
						map.put("state", "02");
					}
					
				}
				if(str.length() != 0){
					return "{\"error\":\"调整单号:"+str+"的数据状态不是审核状态 , 不能执行销审操作!\"}";
				}
				
				budgProjAdjMapper.updateReviewState(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	
			}catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员!\"}");
	
			}	
	}
	
	/**
	 * 修改查询  查询明细表    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgProjAdjDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgProjAdjMapper.queryBudgProjAdjDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgProjAdjMapper.queryBudgProjAdjDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 添加页面查询 
	 */
	@Override
	public String queryAdjAdd(Map<String, Object> entityMap) throws DataAccessException {
		
		return ChdJson.toJson(budgProjAdjMapper.queryAdjAdd(entityMap));
	}
	
}
