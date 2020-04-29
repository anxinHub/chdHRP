/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcash;

import java.util.*;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.budg.dao.budgcash.BudgCashBeginMapper;
import com.chd.hrp.budg.entity.BudgCashBegin;
import com.chd.hrp.budg.service.budgcash.BudgCashBeginService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 期初货币资金
 * @Table:
 * BUDG_CASH_BEGIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgCashBeginService")
public class BudgCashBeginServiceImpl implements BudgCashBeginService {

	private static Logger logger = Logger.getLogger(BudgCashBeginServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCashBeginMapper")
	private final BudgCashBeginMapper budgCashBeginMapper = null;
    
	/**
	 * @Description 
	 * 添加期初货币资金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgCashBegin(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象期初货币资金
		int count = budgCashBeginMapper.queryBudgCashBeginDataExist(entityMap);
		try {
			
			if (count > 0) {
				int state = budgCashBeginMapper.updateBudgCashBegin(entityMap);
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}else{
				
				//如果数据库不存在 则走添加方法  设置状态为 0 未记账
				entityMap.put("state", "0");
				
				int state = budgCashBeginMapper.addBudgCashBegin(entityMap);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage(),e);

		}
		
	}
	
	/**
	 * @Description 
	 * 批量添加 期初现金流量累计<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		
		try {
			
			for(Map<String,Object> item :entityList){
				int count  = budgCashBeginMapper.queryDataExist(item);
				
				if(count > 0 ){
					str += item.get("cash_item_code") +";";
				}
			}
			
			if(str != ""){
				
				return "{\"error\":\"添加失败！以下数据:"+str.substring(0, str.length()-1)+"已存在！\",\"state\":\"false\"}";
				
			}else{
				
				budgCashBeginMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败！\",\"state\":\"false\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 查询结果集期初货币资金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgCashBegin(Map<String,Object> entityMap) throws DataAccessException{
		
		Map<String, Object> budgCashBegin = budgCashBeginMapper.queryBudgCashBegin(entityMap);
		
		if(budgCashBegin == null){
			return "{\"msg\":\"数据未维护,请维护后查询!\"}";
		}
		return ChdJson.toJson(budgCashBegin);
	}
	
	/**
	 * @Description 
	 * 根据主键    查询期初货币资金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgCashBegin queryBudgCashBeginByCode(Map<String, Object> map) throws DataAccessException {
		return budgCashBeginMapper.queryBudgCashBeginByCode(map);
	}

	/**
	 *主页面跳转 根据账套  查询对象
	 * @param map
	 * @return
	 */
	@Override
	public String queryBudgCashFlowBegin(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = budgCashBeginMapper.queryBudgCashFlowBegin(mapVo);
		return ChdJson.toJson(list);
	}
	
	/**
	 *主页面跳转 根据主键  查询系统启用时间
	 * @param map
	 * @return
	 */
	@Override
	public String queryStartDate(Map<String, Object> mapVo) throws DataAccessException {
		String StartDate = budgCashBeginMapper.queryStartDate(mapVo);
		return ChdJson.toJson(StartDate);
	}

	/**
	 * @Description 
	 * 查询数据 现金流量项目下拉框
	 */
	@Override
	public String queryCashItem(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = budgCashBeginMapper.queryCashItem(mapVo);
		return JSON.toJSONString(list);
	}
	
	
	/**
	 * @Description 
	 * 换行添加数据 期初现金流量累计
	 */
	@Override
	public String addBudgCashFlowBegin(List<Map<String, Object>> listVo)throws DataAccessException{
		
		try {
			
			budgCashBeginMapper.addBatch(listVo);
			
		/*	//添加成功后  根据现金流量项目ID查询流向
			String cash_dire = budgCashBeginMapper.queryCashDire(entityMap);
			*/
			return "{\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage(),e);

		}
		
	}
	
	/**
	 * @Description 
	 * 添加或更新数据 期初现金流量累计
	 */
	@Override
	public String addOrUpdateBudgCashFlowBegin(List<Map<String, Object>> listVo,Map<String, Object> initMap)throws DataAccessException{
		
		//定义uadateList用于批量更新
		List<Map<String, Object>> updateList = new ArrayList<Map<String,Object>>();
		//定义addList用于批量添加
		List<Map<String, Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			
			for (Map<String, Object> map : listVo) {
				//查询数据是由已经存在  如果存在更新  否则 添加
				int count = budgCashBeginMapper.queryDataExist(map);
				
				if(count > 0 ){
					updateList.add(map);
				}else {
					addList.add(map);
				}
				
			}
			
			if(updateList.size() > 0){
				budgCashBeginMapper.updateBudgCashFlowBegin(updateList);
			}
			if(addList.size() > 0){
				budgCashBeginMapper.addBatch(addList);
			}
			//添加或修改数据后  更新期初货币资金
			addBudgCashBegin(initMap);
			
			return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(),e);
			
		}
		
	}
	/**
	 * 批量更新 期初现金流量累计
	 * 
	 */
	@Override
	public String updateBudgCashFlowBegin(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			
			    budgCashBeginMapper.updateBudgCashFlowBegin(listVo);
				
				return "{\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage(),e);
			}	
	}
	
	/**
	 *批量删除  期初现金流量累计
	 * 
	 */
	@Override
	public String deleteBudgCashFlowBegin(List<Map<String, Object>> listVo,Map<String, Object> initMap) throws DataAccessException {
		try {
			
			budgCashBeginMapper.deleteBudgCashFlowBegin(listVo);
			//删除数据后  更新期初货币资金
			addBudgCashBegin(initMap);
			
			return "{\"msg\":\"删除成功!\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(),e);
		}	
	}
	
	/**
	 *查询数据状态
	 * 
	 */
	@Override
	public String queryBudgCashBeginState(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			String state = budgCashBeginMapper.queryBudgCashBeginState(entityMap);
			
			if("1".equals(state)){
				return "{\"msg\":\"数据为记账状态,不能执行该操作.\",\"state\":\"false\"}";
			}
			
			return "{\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(),e);
		}	
	}

	/**
	 *  记账  更改数据状态
	 * @param mapVo
	 * @return
	 */
	@Override
	public String chargeBudgCashBeginState(Map<String, Object> mapVo) throws DataAccessException {
		String state = budgCashBeginMapper.queryBudgCashBeginState(mapVo);
		if("1".equals(state)){
			return "{\"msg\":\"数据为记账状态,不能重复记账!\",\"state\":\"false\"}";
		}
		
		//期初现金存量不为空。否则提示“记账前，请先设置期初现金存量”
		Double cash_begin = budgCashBeginMapper.queryCashBeginExist(mapVo);
		
		if(cash_begin == null){
			return "{\"msg\":\"记账前，请先设置期初现金存量!\",\"state\":\"false\"}";
		}
		
		try {
			
			mapVo.put("state", "1");
			budgCashBeginMapper.updateBudgCashBeginState(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(),e);
		}
		
	}
	
	/**
	 *  反记账  更改数据状态
	 * @param mapVo
	 * @return
	 */
	@Override
	public String unChargeBudgCashBeginState(Map<String, Object> mapVo) throws DataAccessException {
		String state = budgCashBeginMapper.queryBudgCashBeginState(mapVo);
		if("0".equals(state)){
			return "{\"msg\":\"数据为未记账状态,不能执行该操作.\",\"state\":\"false\"}";
		}
		
		int budgCash = budgCashBeginMapper.queryDataExistFromBudgCash(mapVo);
		int budgCashExe = budgCashBeginMapper.queryDataExistFromBudgCashExe(mapVo);
		
		if(budgCash > 0 || budgCashExe >0){
			
			return "{\"error\":\"该集团医院账套下已存在现金存量预算数据或现金存量执行数据,不可执行反记账操作!.\",\"state\":\"false\"}";
		}
		
		try {
			
			mapVo.put("state", "0");
			budgCashBeginMapper.updateBudgCashBeginState(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(),e);
		}
	}
	
	/**
	 *最新版导入
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String importBudgCashFlowBegin(Map<String, Object> map) throws DataAccessException {
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
			entityMap.put("is_stop", "0");// 选取未停用的现金流量项目
			
			// 查询现金流量项目表中所有未停用的现金流量项目id code等基本信息   匹配数据用
			List<Map<String,Object>> listDict = budgCashBeginMapper.queryBudgCashFlowBeginDict(entityMap);
			
			// 判断现金流量项目编码是否存在    用 map key为现金流量项目code ， value : 现金流量项目id、code等信息
			Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
			
			for(Map<String, Object> dict : listDict){
				if(dict.get("cash_item_code") != null){
					dictMap.put(dict.get("cash_item_code").toString(), dict);
				}
			}
			
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String,List<String>> item : liData ){
				
				
				List<String> cash_item_code = item.get("现金流量项目编码") ;
				
				List<String> amount = item.get("金额(元)") ;
				
				List<String> remark = item.get("说明(可空)") ;
				
				if(cash_item_code == null){
					
					return "{\"warn\":\"" + cash_item_code.get(0) + ",现金流量项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + cash_item_code.get(0) + "\"}";
					
				}
				
				if(amount == null){
					
					return "{\"warn\":\"" + amount.get(0) + ",金额(元)为空！\",\"state\":\"false\",\"row_cell\":\"" + amount.get(0) + "\"}";
					
				}else{
					
					 try{
						    Integer.parseInt(amount.get(1));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + amount.get(0) + ",金额(元)输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + amount.get(0) + "\"}";
					  }
					
				}
				
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("group_id", SessionManager.getGroupId());
				
				returnMap.put("hos_id", SessionManager.getHosId());
				
				returnMap.put("copy_code", SessionManager.getCopyCode());
				
				returnMap.putAll(dictMap.get(cash_item_code.get(1))); // 存放 现金流量项目id、code等基本信息
				
				returnMap.put("amount", amount.get(1));
				
				if(remark != null ){
					
					returnMap.put("remark", remark.get(1));
					
				}else{
					
					returnMap.put("remark", "");
				}
				returnList.add(returnMap);
					
			}
			
			StringBuffer err_sb = new StringBuffer();
			
			//校验 数据重复
			for( int i = 1; i < returnList.size(); i++ ){
				
				for(int j = i + 1 ; j < returnList.size(); j++){
					
					
					if(returnList.get(i).get("cash_item_code").equals(returnList.get(j).get("cash_item_code"))){
						
						err_sb.append(returnList.get(i).get("cash_item_code")+"现金流量项目编码");
					}
				}
				
			}
			
			if( err_sb.length() > 0){
				
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
				
			}else{
				
				String str = addBatch(returnList);
				
				if("false".equals(JSONObject.parseObject(str).get("state"))){
					
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
}
