/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.common.BudgFunParaMethodMapper;
import com.chd.hrp.budg.entity.BudgFunParaMethod;
import com.chd.hrp.budg.service.common.BudgFunParaMethodService;
import com.chd.hrp.prm.entity.PrmFunParaMethod;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 函数参数取值表
 * @Table:
 * FUN_PARA_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("funParaMethodService")
public class BudgFunParaMethodServiceImpl implements BudgFunParaMethodService {

	private static Logger logger = Logger.getLogger(BudgFunParaMethodServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFunParaMethodMapper")
	private final BudgFunParaMethodMapper budgFunParaMethodMapper = null;
    
	/**
	 * @Description 
	 * 添加函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		String str ="";
		//获取对象函数参数取值表
		BudgFunParaMethod funParaMethod = budgFunParaMethodMapper.queryByCode(entityMap);

		if (funParaMethod != null) {

			str +="参数代码:"+entityMap.get("para_code")+";";

		}
		
		int count = budgFunParaMethodMapper.queryNameExist(entityMap);
		
		if(count > 0 ){
			
			str +="参数名称:"+entityMap.get("para_name")+";";
		}
		
		if( str != ""){
			return "{\"error\":\""+str+"已被占用,请重新添加.\"}";
		}
		
		try {
			
			int state = budgFunParaMethodMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addFunParaMethod\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunParaMethodMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchFunParaMethod\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int count = budgFunParaMethodMapper.queryNameExist(entityMap);
			
			if(count > 0 ){
				
				return "{\"error\":\"参数名称:"+entityMap.get("para_name")+"已被占用,请重新添加.\"}";
			}
			int state = budgFunParaMethodMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateFunParaMethod\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgFunParaMethodMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchFunParaMethod\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgFunParaMethodMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteFunParaMethod\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunParaMethodMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchFunParaMethod\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象函数参数取值表
		BudgFunParaMethod funParaMethod = budgFunParaMethodMapper.queryByCode(entityMap);

		if (funParaMethod != null) {

			int state = budgFunParaMethodMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgFunParaMethodMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateFunParaMethod\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgFunParaMethod> list = (List<BudgFunParaMethod>) budgFunParaMethodMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFunParaMethod> list = (List<BudgFunParaMethod>) budgFunParaMethodMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象函数参数取值表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunParaMethodMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取函数参数取值表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return FunParaMethod
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunParaMethodMapper.queryByUniqueness(entityMap);
	}
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryFunParaByDict(Map<String, Object> mapVo) throws DataAccessException{
		
		BudgFunParaMethod pfpm= budgFunParaMethodMapper.queryByCode(mapVo);
		
		if (pfpm!=null){
			
			mapVo.put("sql", pfpm.getPara_sql().replace("{", "#{"));
			 
			
			return JSON.toJSONString(budgFunParaMethodMapper.queryFunParaByDict(mapVo));
		}
		else {
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
			
		}
	}
	
	/**
	 * @Description 
	 * 获取函数参数取值表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgFunParaMethod>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap) throws DataAccessException{
		return budgFunParaMethodMapper.queryExists(entityMap);
	}
	
	@Override
	public int queryNameExist(Map<String, Object> mapVo) {
		return budgFunParaMethodMapper.queryNameExist(mapVo);
	}
}
