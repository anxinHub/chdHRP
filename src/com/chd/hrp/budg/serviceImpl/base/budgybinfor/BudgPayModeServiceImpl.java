
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.serviceImpl.base.budgybinfor;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgybinfor.BudgPayModeMapper;
import com.chd.hrp.budg.dao.base.budgybinfor.BudgYBTypeMapper;
import com.chd.hrp.budg.service.base.budgybinfor.BudgPayModeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 付费机制
 * @Table:
 * HEALTH_INSURANCE_PAY_MODE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgPayModeService")
public class BudgPayModeServiceImpl implements BudgPayModeService {

	private static Logger logger = Logger.getLogger(BudgPayModeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgPayModeMapper")
	private final BudgPayModeMapper budgPayModeMapper = null;
	
	@Resource(name = "budgYBTypeMapper")
	private final BudgYBTypeMapper budgYBTypeMapper = null;
	/**
	 * @Description 
	 * 添加付费机制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象付费机制
		Map<String,Object> mapVo = queryByCode(entityMap);

		if (mapVo != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		
		try {	
			
			budgPayModeMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
	}
	/**
	 * @Description 
	 * 批量添加付费机制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			//先删除当前集团、医院、账套下的  付费机制设置信息  再添加
			Map<String,Object> entityMap = new HashMap<String,Object>();
			entityMap.put("group_id",  SessionManager.getGroupId());
			entityMap.put("hos_id",  SessionManager.getHosId());
			entityMap.put("copy_code",  SessionManager.getCopyCode());
			//删除当前集团、医院、账套下的  付费机制设置信息
			budgPayModeMapper.delete(entityMap);
			
			//批量添加设置当前集团、医院、账套下的  付费机制设置信息
			budgPayModeMapper.addBatch(entityList);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败  请联系管理员! 方法 addBatch\"}";
		}
		
	}
	
	/**
	 * @Description 
	 * 更新付费机制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			budgPayModeMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新付费机制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			budgPayModeMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 删除付费机制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {	
		try {
			
			budgPayModeMapper.delete(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败  请联系管理员! 方法 delete\"}";
		}	
	
	}
    
	/**
	 * @Description 
	 * 批量删除付费机制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {	
			budgPayModeMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集付费机制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");	
		if (sysPage.getTotal()==-1){		
			List<?> list = budgPayModeMapper.query(entityMap);
			return ChdJson.toJson(list);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = budgPayModeMapper.query(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgPayModeMapper.queryByCode(entityMap);
	}
	
	
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 生成
	 * @param mapVo
	 * @return
	 */
	public String resetBudgePayMode(Map<String, Object> mapVo) throws DataAccessException{
		try {
			//查询所选年度的99自费以外未停用的医保类型
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgYBTypeMapper.queryBudgYBTypeNew(mapVo);
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> item : list ){
				Map<String,Object>  map = new HashMap<String,Object>();
				
				map.put("group_id",item.get("group_id"));
				map.put("hos_id",item.get("hos_id"));
				map.put("copy_code",item.get("copy_code"));
				map.put("budg_year",mapVo.get("budg_year"));
				map.put("insurance_code",item.get("insurance_code"));
				map.put("is_total_amount_pay", "0");
				map.put("is_single_disease_pay", "0");
				map.put("is_person_number_pay", "0");
				
				dataList.add(map);
			}
			
			//查询所选年度的医保付费机制数据
			List<Map<String,Object>> restList = (List<Map<String, Object>>) budgPayModeMapper.queryByYear(mapVo);
			int count = 0;
			if(restList.size() >0 ){
				budgPayModeMapper.delete(mapVo);
				budgPayModeMapper.addBatch(dataList);
			}else{
				budgPayModeMapper.addBatch(dataList);
			}
				
			return "{\"msg\":\"生成成功，共生成"+dataList.size()+"条记录，请做相应维护.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
	}
	@Override
	public int queryInsCodeExist(Map<String, Object> entityMap)	throws DataAccessException {
		return budgPayModeMapper.queryInsCodeExist(entityMap);
	}
	@Override
	public int queryPayModCodeExist(Map<String, Object> entityMap)	throws DataAccessException {
		return budgPayModeMapper.queryPayModCodeExist(entityMap);
	}
	
}
