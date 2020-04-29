/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgcharge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.budg.dao.base.budgcharge.BudgChargeStandGetWayMapper;
import com.chd.hrp.budg.dao.common.BudgChargeStandStackMapper;
import com.chd.hrp.budg.entity.BudgChargeStandGetWay;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStandGetWayService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 
 * @Table:
 * BUDG_CHARGE_STAND_GET_WAY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgChargeStandGetWayService")
public class BudgChargeStandGetWayServiceImpl implements BudgChargeStandGetWayService {

	private static Logger logger = Logger.getLogger(BudgChargeStandGetWayServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgChargeStandGetWayMapper")
	private final BudgChargeStandGetWayMapper budgChargeStandGetWayMapper = null;
	
	@Resource(name = "budgChargeStandStackMapper")
	private final BudgChargeStandStackMapper budgChargeStandStackMapper = null;
	
	
    
	/**
	 * @Description 
	 * 添加取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象取值方法（GET_VALUE_WAY）01手工录入  02取值函数  03计算公式

		Map<String,Object> budgChargeStandGetWay = queryByCode(entityMap);

		if (budgChargeStandGetWay != null) {

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
			
			int state = budgChargeStandGetWayMapper.add(entityMap);
			
			// 保存 函数参数栈数据
			if(listVo.size() > 0){
				
				budgChargeStandStackMapper.deleteBatch(listVo);
				
				budgChargeStandStackMapper.addBatch(listVo);
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败!\",\"state\":\"false\"}") ;
			
			//return "{\"error\":\"添加失败   请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgChargeStandGetWayMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败  请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新取值方法（GET_VALUE_WAY）01手工录入  02取值函数 03计算公式 <BR> 
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
			
			int state = budgChargeStandGetWayMapper.update(entityMap);
			
			budgChargeStandStackMapper.delete(entityMap);
			
			// 保存 函数参数栈数据
			if(listVo.size() > 0){
				
				budgChargeStandStackMapper.addBatch(listVo);
				
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败！\",\"state\":\"false\"}");
			
			//return "{\"error\":\"更新失败   请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新取值方法（GET_VALUE_WAY）01手工录入  02取值函数 03计算公式 <BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgChargeStandGetWayMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败   请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除更新取值方法（GET_VALUE_WAY）01手工录入  02取值函数 03计算公式 <BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
		
			int state = budgChargeStandGetWayMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败  请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除 更新取值方法（GET_VALUE_WAY）01手工录入  02取值函数 03计算公式 <BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgChargeStandStackMapper.deleteBatch(entityList);
			
			budgChargeStandGetWayMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败！\",\"state\":\"false\"}");
			
			//return "{\"error\":\"删除失败  请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 
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
		//判断是否存在对象取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式

		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgChargeStandGetWay> list = (List<BudgChargeStandGetWay>)budgChargeStandGetWayMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgChargeStandGetWayMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgChargeStandGetWayMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败   请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgChargeStandGetWayMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgChargeStandGetWayMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgChargeStandGetWayMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 01手工录入 02取值函数 03计算公式<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgChargeStandGetWay
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgChargeStandGetWayMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取取值方法（GET_VALUE_WAY）01手工录入 02取值函数 03计算公式<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgChargeStandGetWay>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgChargeStandGetWayMapper.queryExists(entityMap);
	}
	
}
