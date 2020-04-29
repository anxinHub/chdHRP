/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostParaAssSetMapper;
import com.chd.hrp.cost.entity.CostParaAssSet;
import com.chd.hrp.cost.entity.CostParaManSet;
import com.chd.hrp.cost.entity.CostParaMedSet;
import com.chd.hrp.cost.service.CostParaAssSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 医辅分摊设置<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costParaAssSetService")
public class CostParaAssSetServiceImpl implements CostParaAssSetService {

	private static Logger logger = Logger.getLogger(CostParaAssSetServiceImpl.class);
	
	@Resource(name = "costParaAssSetMapper")
	private final CostParaAssSetMapper costParaAssSetMapper = null;
    
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 添加CostParaAssSet
	 * @param CostParaAssSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("dept_id", entityMap.get("dept_id"));
		
		byCodeMap.put("server_dept_id", entityMap.get("server_dept_id"));
		
		byCodeMap.put("cost_item_id", entityMap.get("cost_item_id"));
		
		byCodeMap.put("year_month", entityMap.get("year_month"));
		
		CostParaAssSet costParaAssSet = queryCostParaAssSetByCode(byCodeMap);

		if (costParaAssSet != null) {

			return "{\"error\":\"已存在此分摊配置.\"}";

		}
		
		try {
			
			costParaAssSetMapper.addCostParaAssSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostParaAssSet\"}";

		}

	}
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量添加CostParaAssSet
	 * @param  CostParaAssSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostParaAssSet(List<Map<String,Object>> entityList)throws DataAccessException{
              
		     if(entityList.size() == 0){
			
		       	return "{\"error\":\"没有数据无法继承\",\"state\":\"true\"}";
	       	}
		     
		     for (Map<String, Object> m : entityList)  
		     {  
		 		
		    	 CostParaAssSet data_exc_extis = costParaAssSetMapper.queryCostParaAssSetByCode(m);
		    	 
		    		if (data_exc_extis != null) {

						return "{\"msg\":\"已存在此分摊配置.\",\"state\":\"true\"}";
					}
		     }
		     
		try {
			
			costParaAssSetMapper.addBatchCostParaAssSet(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码 addBatchCostParaAssSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 查询CostParaAssSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostParaAssSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostParaAssSet> list = costParaAssSetMapper.queryCostParaAssSet(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostParaAssSet> list = costParaAssSetMapper.queryCostParaAssSet(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 查询CostParaAssSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostParaAssSet queryCostParaAssSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costParaAssSetMapper.queryCostParaAssSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量删除CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostParaAssSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costParaAssSetMapper.deleteBatchCostParaAssSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostParaAssSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 删除CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostParaAssSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costParaAssSetMapper.deleteCostParaAssSet(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostParaAssSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 更新CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costParaAssSetMapper.updateCostParaAssSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaAssSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量更新CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostParaAssSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costParaAssSetMapper.updateBatchCostParaAssSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaAssSet\"}";

		}
		
	}
	
	@Override
    public List<CostParaAssSet> queryCostParaAssSetNoPage(Map<String, Object> entityMap) throws DataAccessException {

	    return costParaAssSetMapper.queryCostParaAssSet(entityMap);
	    
    }
}
