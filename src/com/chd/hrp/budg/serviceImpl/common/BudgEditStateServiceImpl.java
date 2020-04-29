/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.common.BudgEditStateMapper;
import com.chd.hrp.budg.entity.BudgComType;
import com.chd.hrp.budg.service.common.BudgEditStateService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 预算编辑状态管理表
 * @Table:
 * BUDG_EDIT_STATE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgEditStateService")
public class BudgEditStateServiceImpl implements BudgEditStateService {

	private static Logger logger = Logger.getLogger(BudgEditStateServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgEditStateMapper")
	private final BudgEditStateMapper budgEditStateMapper = null;
    
	/**
	 * @Description 
	 * 添加预算编辑状态管理表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象预算编辑状态管理表
		Map<String,Object> map = budgEditStateMapper.queryByCode(entityMap);

		if (map  != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgEditStateMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addComType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加预算编辑状态管理表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgEditStateMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatchComType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新预算编辑状态管理表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgEditStateMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 updateComType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新预算编辑状态管理表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgEditStateMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 updateBatchComType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除预算编辑状态管理表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgEditStateMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteComType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除预算编辑状态管理表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgEditStateMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteBatchComType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加预算编辑状态管理表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象预算编辑状态管理表
		Map<String,Object> map = budgEditStateMapper.queryByCode(entityMap);

		if (map != null) {

			int state = budgEditStateMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgEditStateMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addOrUpdateComType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集预算编辑状态管理表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) budgEditStateMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) budgEditStateMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象预算编辑状态管理表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgEditStateMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取预算编辑状态管理表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return ComType
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgEditStateMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String queryWorkFlag(Map<String, Object> mapVo) throws DataAccessException {
		
		String flag = budgEditStateMapper.queryWorkFlag(mapVo) ;
		
		if(flag == null){
			flag = "1";
		}
		
		return "{\"flag\":\""+flag+"\"}";
	}
	@Override
	public String queryMedIncomeFlag(Map<String, Object> mapVo)	throws DataAccessException {
		String flag = budgEditStateMapper.queryMedIncomeFlag(mapVo) ;
		
		if(flag == null){
			flag = "1";
		}
		
		return "{\"flag\":\""+flag+"\"}";
	}
	@Override
	public String queryElseIncomeFlag(Map<String, Object> mapVo) throws DataAccessException {
		
		String flag = budgEditStateMapper.queryElseIncomeFlag(mapVo) ;
		
		if(flag == null){
			flag = "1";
		}
		
		return "{\"flag\":\""+flag+"\"}";
	}
	@Override
	public String queryMedCostFlag(Map<String, Object> mapVo)throws DataAccessException {
		
		String flag = budgEditStateMapper.queryMedCostFlag(mapVo) ;
		
		if(flag == null){
			flag = "1";
		}
		
		return "{\"flag\":\""+flag+"\"}";
	}
	@Override
	public String queryElseCostFlag(Map<String, Object> mapVo) throws DataAccessException {
		
		String flag = budgEditStateMapper.queryElseCostFlag(mapVo) ;
		
		if(flag == null){
			flag = "1";
		}
		
		return "{\"flag\":\""+flag+"\"}";
	}
	@Override
	public String queryMatPurFlag(Map<String, Object> mapVo) throws DataAccessException {
		
		String flag = budgEditStateMapper.queryMatPurFlag(mapVo) ;
		
		if(flag == null){
			flag = "1";
		}
		
		return "{\"flag\":\""+flag+"\"}";
	}
	@Override
	public String queryMedPurFlag(Map<String, Object> mapVo)throws DataAccessException {
		
		String flag = budgEditStateMapper.queryMedPurFlag(mapVo) ;
		
		if(flag == null){
			flag = "1";
		}
		
		return "{\"flag\":\""+flag+"\"}";
	}
	
}
