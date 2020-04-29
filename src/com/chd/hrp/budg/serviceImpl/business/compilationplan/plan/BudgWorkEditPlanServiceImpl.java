/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationplan.plan;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.budg.dao.business.compilationplan.plan.BudgWorkEditPlanMapper;
import com.chd.hrp.budg.dao.common.BudgIndexStackMapper;
import com.chd.hrp.budg.entity.BudgWorkEditPlan;
import com.chd.hrp.budg.service.business.compilationplan.plan.BudgWorkEditPlanService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * BUDG_WORK_EDIT_PLAN
 * @Table:
 * BUDG_WORK_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWorkEditPlanService")
public class BudgWorkEditPlanServiceImpl implements BudgWorkEditPlanService {

	private static Logger logger = Logger.getLogger(BudgWorkEditPlanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWorkEditPlanMapper")
	private final BudgWorkEditPlanMapper budgWorkEditPlanMapper = null;
	
	@Resource(name = "budgIndexStackMapper")
	private final BudgIndexStackMapper budgIndexStackMapper = null;
	
	
    
	/**
	 * @Description 
	 * 添加BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象BUDG_WORK_EDIT_PLAN
		BudgWorkEditPlan budgWorkEditPlan = queryByCode(entityMap);

		if (budgWorkEditPlan != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		
		if(entityMap.get("dataStack") != null ){
			
			 listVo= JsonListMapUtil.getListMap(entityMap.get("dataStack").toString());
			
			for ( Map<String,Object> map: listVo) {
				
				//表的主键
				map.put("group_id", SessionManager.getGroupId())   ;
				map.put("hos_id", SessionManager.getHosId())   ;
				map.put("copy_code", SessionManager.getCopyCode())   ;
		    }
		}
		
		try {
			
			int state = budgWorkEditPlanMapper.add(entityMap);
			
			// 保存 函数参数栈数据
				if(listVo.size() > 0){
					
					budgIndexStackMapper.deleteBatch(listVo);
					
					budgIndexStackMapper.addBatch(listVo);
					
				}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败！\",\"state\":\"false\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWorkEditPlanMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
			
			if(entityMap.get("dataStack") != null ){
				
				 listVo= JsonListMapUtil.getListMap(entityMap.get("dataStack").toString());
				
				for ( Map<String,Object> map: listVo) {
					
					//表的主键
					map.put("group_id", SessionManager.getGroupId())   ;
					map.put("hos_id", SessionManager.getHosId())   ;
					map.put("copy_code", SessionManager.getCopyCode())   ;
					
			    }
			}
			
			int state = budgWorkEditPlanMapper.update(entityMap);
			
			budgIndexStackMapper.delete(entityMap);
			
			// 保存 函数参数栈数据
			if(listVo.size() > 0){
				
				budgIndexStackMapper.addBatch(listVo);
				
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败！\",\"state\":\"false\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgWorkEditPlanMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgWorkEditPlanMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgIndexStackMapper.deleteBatch(entityList);
			
			budgWorkEditPlanMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败！\",\"state\":\"false\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加BUDG_WORK_EDIT_PLAN<BR> 
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
		//判断是否存在对象BUDG_WORK_EDIT_PLAN
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkEditPlan> list = (List<BudgWorkEditPlan>)budgWorkEditPlanMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWorkEditPlanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWorkEditPlanMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkEditPlanMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkEditPlanMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkEditPlanMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWorkEditPlan
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkEditPlanMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取BUDG_WORK_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkEditPlan>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkEditPlanMapper.queryExists(entityMap);
	}
	@Override
	public String queryBudgBuiltIn(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String, Object>  map = budgWorkEditPlanMapper.queryBudgBuiltIn(mapVo) ;
		
		return ChdJson.toJson(map);
	}
	
}
