/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.common.BudgResolveDataDictMapper;
import com.chd.hrp.budg.dao.common.BudgResolveDataDmMapper;
import com.chd.hrp.budg.dao.common.BudgResolveDataDyMapper;
import com.chd.hrp.budg.dao.common.BudgResolveDataHmMapper;
import com.chd.hrp.budg.entity.BudgFunPara;
import com.chd.hrp.budg.service.common.BudgResolveDataDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 自定义分解系数
 * @Table:
 * budg_resolve_data_dict
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Service("budgResolveDataDictService")
public class BudgResolveDataDictServiceImpl implements BudgResolveDataDictService {

	private static Logger logger = Logger.getLogger(BudgResolveDataDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgResolveDataDictMapper")
	private final BudgResolveDataDictMapper budgResolveDataDictMapper = null;
	
	@Resource(name = "budgResolveDataHmMapper")
	private final BudgResolveDataHmMapper budgResolveDataHmMapper = null;
	
	@Resource(name = "budgResolveDataDyMapper")
	private final BudgResolveDataDyMapper budgResolveDataDyMapper = null;
	
	@Resource(name = "budgResolveDataDmMapper")
	private final BudgResolveDataDmMapper budgResolveDataDmMapper = null;
    
	/**
	 * @Description 
	 * 添加自定义分解系数<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//校验编码是否存在
		int code = budgResolveDataDictMapper.queryCodeExist(entityMap);

		if (code > 0) {

			return "{\"error\":\"自定义分解系数编码重复,请重新添加.\"}";

		}
		
		//校验名称是否存在
		int name = budgResolveDataDictMapper.queryNameExist(entityMap);

		if (name > 0) {

			return "{\"error\":\"自定义分解系数名称重复,请重新添加.\"}";

		}
		
		String budg_level = String.valueOf(entityMap.get("budg_level"));
		
    	JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("detail")));
    	
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		Iterator it=json.iterator();
		while(it.hasNext()){
			JSONObject jsonObj = JSON.parseObject(it.next().toString());
			Map<String,Object> dataMap = new HashMap<String,Object>();
			
			dataMap.put("group_id", SessionManager.getGroupId());
			dataMap.put("hos_id", SessionManager.getHosId());
			dataMap.put("copy_code", SessionManager.getCopyCode());
			dataMap.put("resolve_data_code", entityMap.get("resolve_data_code"));
			if("02".equals(budg_level)  ||"04".equals(budg_level)){
				dataMap.put("month", jsonObj.get("month"));//月份
			}
			if("03".equals(budg_level)|| "04".equals(budg_level)){
				dataMap.put("dept_id",  Long.parseLong(String.valueOf(jsonObj.get("dept_id"))));//科室
			}
			dataMap.put("value",  Double.parseDouble(String.valueOf(jsonObj.get("value"))));//数值
			dataList.add(dataMap);
		}
		
		try {
			
			int state = budgResolveDataDictMapper.add(entityMap);
			
			if("02".equals(budg_level)){
				
				budgResolveDataHmMapper.addBatch(dataList);
				
			}else if("03".equals(budg_level)){
				
				budgResolveDataDyMapper.addBatch(dataList);
				
			}else if("04".equals(budg_level)){
				
				budgResolveDataDmMapper.addBatch(dataList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addFunPara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加自定义分解系数<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgResolveDataDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchFunPara\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新自定义分解系数<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			//校验编码是否存在
			int code = budgResolveDataDictMapper.queryCodeExist(entityMap);

			if (code > 0) {

				return "{\"error\":\"自定义分解系数编码重复,请重新添加.\"}";

			}
			
			//校验名称是否存在
			int name = budgResolveDataDictMapper.queryNameExist(entityMap);

			if (name > 0) {

				return "{\"error\":\"自定义分解系数名称重复,请重新添加.\"}";

			}
			
			String budg_level = String.valueOf(entityMap.get("budg_level"));
			
	    	JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("detail")));
	    	
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			
			Iterator it=json.iterator();
			while(it.hasNext()){
				JSONObject jsonObj = JSON.parseObject(it.next().toString());
				Map<String,Object> dataMap = new HashMap<String,Object>();
				
				dataMap.put("group_id", SessionManager.getGroupId());
				dataMap.put("hos_id", SessionManager.getHosId());
				dataMap.put("copy_code", SessionManager.getCopyCode());
				dataMap.put("resolve_data_code", entityMap.get("resolve_data_code"));
				if("02".equals(budg_level) || "04".equals(budg_level) ){
					dataMap.put("month", jsonObj.get("month"));//月份
				}
				if("03".equals(budg_level) || "04".equals(budg_level) ){
					dataMap.put("dept_id",  Long.parseLong(String.valueOf(jsonObj.get("dept_id"))));//科室
				}
				dataMap.put("value",  Double.parseDouble(String.valueOf(jsonObj.get("value"))));//数值
				dataList.add(dataMap);
			}
			
				
			int state = budgResolveDataDictMapper.update(entityMap);
			
			if("02".equals(budg_level)){
				
				budgResolveDataHmMapper.delete(entityMap);
				
				budgResolveDataHmMapper.addBatch(dataList);
				
			}else if("03".equals(budg_level)){
				
				budgResolveDataDyMapper.delete(entityMap);
				
				budgResolveDataDyMapper.addBatch(dataList);
				
			}else if("04".equals(budg_level)){
				
				budgResolveDataDmMapper.delete(entityMap);
				
				budgResolveDataDmMapper.addBatch(dataList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addFunPara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量更新自定义分解系数<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgResolveDataDictMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchFunPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除自定义分解系数<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
	    	//查询  自定义分解系数 是否被引用
			int count = budgResolveDataDictMapper.queryIsUsed(entityMap);
			
			if(count > 0){
				return "{\"error\":\"该数据已被业务数据引用,不能删除\",\"state\":\"false\"}";
			}
    		String budg_level = String.valueOf(entityMap.get("budg_level"));
    	
			if("02".equals(budg_level)){
				
				budgResolveDataHmMapper.delete(entityMap);
				
			}else if("03".equals(budg_level)){
				
				budgResolveDataDyMapper.delete(entityMap);
				
			}else if("04".equals(budg_level)){
				
				budgResolveDataDmMapper.delete(entityMap);
				
			}
			
			int state = budgResolveDataDictMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败.\",\"state\":\"false\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteFunPara\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 删除数据 自定义分解系数 明细数据(医院月、科室年、科室月)
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBudgResolveData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String budg_level = null;
			
			for(Map<String,Object> item: entityList){
				
				budg_level = String.valueOf(item.get("budg_level"));
				
				break ;
			} 	
			if("02".equals(budg_level)){
				
				budgResolveDataHmMapper.deleteBatch(entityList);
				
			}else if("03".equals(budg_level)){
				
				budgResolveDataDyMapper.deleteBatch(entityList);
				
			}else if("04".equals(budg_level)){
				
				budgResolveDataDmMapper.deleteBatch(entityList);
				
			}
    		
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败.\",\"state\":\"false\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchFunPara\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 批量删除自定义分解系数<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//批量查询  自定义分解系数 是否被引用
			String str = budgResolveDataDictMapper.queryIsUsedBatch(entityList);
			
			if(str != null && !"".equals(str)){
				return "{\"error\":\"以下数据:【"+str+"】已被业务数据引用,不能删除\",\"state\":\"false\"}";
			}
			
			for(Map<String,Object> item: entityList){
				
				String budg_level = String.valueOf(item.get("budg_level"));
		    	
				if("02".equals(budg_level)){
					
					budgResolveDataHmMapper.delete(item);
					
				}else if("03".equals(budg_level)){
					
					budgResolveDataDyMapper.delete(item);
					
				}else if("04".equals(budg_level)){
					
					budgResolveDataDmMapper.delete(item);
					
				}
			}
    		
			budgResolveDataDictMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败.\",\"state\":\"false\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchFunPara\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加自定义分解系数<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象自定义分解系数
		BudgFunPara funPara = budgResolveDataDictMapper.queryByCode(entityMap);

		if (funPara != null) {

			int state = budgResolveDataDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgResolveDataDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateFunPara\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 查询结果集自定义分解系数<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) budgResolveDataDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) budgResolveDataDictMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * @Description 
	 * 查询  自定义分解系数 明细数据 (医院月、科室年、科室月)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgResolveData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		String budg_level = String.valueOf(entityMap.get("budg_level")) ;
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list =  null;
			if("02".equals(budg_level)){
				//医院月
				list = (List<Map<String,Object>>) budgResolveDataHmMapper.query(entityMap);
			}else if("03".equals(budg_level)){
				//科室年
				list = (List<Map<String,Object>>) budgResolveDataDyMapper.query(entityMap);
				
			}else if("04".equals(budg_level)){
				//科室月
				list = (List<Map<String,Object>>) budgResolveDataDmMapper.query(entityMap);
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = null;
			
			if("02".equals(budg_level)){
				//医院月
				list = (List<Map<String,Object>>) budgResolveDataHmMapper.query(entityMap, rowBounds);
			}else if("03".equals(budg_level)){
				//科室年
				list = (List<Map<String,Object>>) budgResolveDataDyMapper.query(entityMap, rowBounds);
				
			}else if("04".equals(budg_level)){
				//科室月
				list = (List<Map<String,Object>>) budgResolveDataDmMapper.query(entityMap, rowBounds);
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象自定义分解系数<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgResolveDataDictMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取自定义分解系数<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return FunPara
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgResolveDataDictMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取自定义分解系数<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgFunPara>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgResolveDataDictMapper.queryExists(entityMap);
	}
}
