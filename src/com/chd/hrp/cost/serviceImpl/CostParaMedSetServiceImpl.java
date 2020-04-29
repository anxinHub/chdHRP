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
import com.chd.hrp.cost.dao.CostParaMedSetMapper;
import com.chd.hrp.cost.entity.CostParaAssSet;
import com.chd.hrp.cost.entity.CostParaManSet;
import com.chd.hrp.cost.entity.CostParaMedSet;
import com.chd.hrp.cost.service.CostParaMedSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 医技分摊设置<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costParaMedSetService")
public class CostParaMedSetServiceImpl implements CostParaMedSetService {

	private static Logger logger = Logger.getLogger(CostParaMedSetServiceImpl.class);
	
	@Resource(name = "costParaMedSetMapper")
	private final CostParaMedSetMapper costParaMedSetMapper = null;
    
	/**
	 * @Description 
	 * 医技分摊设置<BR> 添加CostParaMedSet
	 * @param CostParaMedSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("dept_id", entityMap.get("dept_id"));
		
		byCodeMap.put("server_dept_id", entityMap.get("server_dept_id"));
		
		byCodeMap.put("cost_item_id", entityMap.get("cost_item_id"));
			
		byCodeMap.put("acc_year", entityMap.get("acc_year"));
		
		byCodeMap.put("acc_month", entityMap.get("acc_month"));
		
		CostParaMedSet costParaMedSet = queryCostParaMedSetByCode(byCodeMap);

		if (costParaMedSet != null) {

			return "{\"error\":\"已存在此分摊配置.\"}";

		}
		
		try {
			
			costParaMedSetMapper.addCostParaMedSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostParaMedSet\"}";

		}

	}
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量添加CostParaMedSet
	 * @param  CostParaMedSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostParaMedSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costParaMedSetMapper.addBatchCostParaMedSet(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码 addBatchCostParaMedSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 查询CostParaMedSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostParaMedSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostParaMedSet> list = costParaMedSetMapper.queryCostParaMedSet(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostParaMedSet> list = costParaMedSetMapper.queryCostParaMedSet(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 查询CostParaMedSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostParaMedSet queryCostParaMedSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costParaMedSetMapper.queryCostParaMedSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量删除CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostParaMedSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costParaMedSetMapper.deleteBatchCostParaMedSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostParaMedSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 删除CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostParaMedSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costParaMedSetMapper.deleteCostParaMedSet(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostParaMedSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 更新CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costParaMedSetMapper.updateCostParaMedSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaMedSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量更新CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostParaMedSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costParaMedSetMapper.updateBatchCostParaMedSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaMedSet\"}";

		}
		
	}
	
	@Override
    public List<CostParaMedSet> queryCostParaMedSetNoPage(Map<String, Object> entityMap) throws DataAccessException {

	    return costParaMedSetMapper.queryCostParaMedSet(entityMap);
	    
    }
}
