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
import com.chd.hrp.cost.dao.CostParaManSetMapper;
import com.chd.hrp.cost.entity.CostParaManSet;
import com.chd.hrp.cost.service.CostParaManSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 管理分摊设置<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costParaManSetService")
public class CostParaManSetServiceImpl implements CostParaManSetService {

	private static Logger logger = Logger.getLogger(CostParaManSetServiceImpl.class);
	
	@Resource(name = "costParaManSetMapper")
	private final CostParaManSetMapper costParaManSetMapper = null;
    
	/**
	 * @Description 
	 * 管理分摊设置<BR> 添加CostParaManSet
	 * @param CostParaManSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostParaManSet(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("dept_id", entityMap.get("dept_id"));
		
		byCodeMap.put("server_dept_id", entityMap.get("server_dept_id"));
		
		byCodeMap.put("cost_item_id", entityMap.get("cost_item_id"));
		
		byCodeMap.put("year_month", entityMap.get("year_month"));
		
		CostParaManSet costParaManSet = queryCostParaManSetByCode(byCodeMap);

		if (costParaManSet != null) {

			return "{\"error\":\"已存在此分摊配置.\"}";

		}
		
		try {
			
			costParaManSetMapper.addCostParaManSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostParaManSet\"}";

		}

	}
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量添加CostParaManSet
	 * @param  CostParaManSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostParaManSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costParaManSetMapper.addBatchCostParaManSet(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码 addBatchCostParaManSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询CostParaManSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostParaManSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostParaManSet> list = costParaManSetMapper.queryCostParaManSet(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostParaManSet> list = costParaManSetMapper.queryCostParaManSet(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询CostParaManSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostParaManSet queryCostParaManSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costParaManSetMapper.queryCostParaManSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量删除CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostParaManSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costParaManSetMapper.deleteBatchCostParaManSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostParaManSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 删除CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostParaManSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costParaManSetMapper.deleteCostParaManSet(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostParaManSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 更新CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostParaManSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costParaManSetMapper.updateCostParaManSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaManSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量更新CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostParaManSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costParaManSetMapper.updateBatchCostParaManSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaManSet\"}";

		}
		
	}

	@Override
    public List<CostParaManSet> queryCostParaManSetNoPage(Map<String, Object> entityMap) throws DataAccessException {

	    return costParaManSetMapper.queryCostParaManSet(entityMap);
	    
    }
}
