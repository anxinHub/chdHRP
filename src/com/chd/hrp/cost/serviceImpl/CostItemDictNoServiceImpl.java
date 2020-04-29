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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostItemDictMapper;
import com.chd.hrp.cost.dao.CostItemDictNoMapper;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本项目变更表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costItemDictNoService")
public class CostItemDictNoServiceImpl implements CostItemDictNoService {

	private static Logger logger = Logger.getLogger(CostItemDictNoServiceImpl.class);
	
	@Resource(name = "costItemDictNoMapper")
	private final CostItemDictNoMapper costItemDictNoMapper = null;
	
	@Resource(name = "costItemDictMapper")
	private final CostItemDictMapper costItemDictMapper = null;
    
	/**
	 * @Description 
	 * 成本项目变更表<BR> 添加CostItemDictNo
	 * @param HtcCostItemDictNo entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException{
		
		CostItemDictNo costItemDictNo = queryCostItemDictNoByCode(entityMap);

		if (costItemDictNo != null) {

			return "{\"error\":\"编码：" + costItemDictNo.getCost_item_code() + "重复.\"}";

		}
		
		try {
			if("0".endsWith(entityMap.get("dict_type").toString())){
				costItemDictMapper.updateCostItemDictByCode(entityMap);
			}else{
				costItemDictMapper.updateCostItemDictByName(entityMap);
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			
		    map.put("is_stop", 1);
		    
		    map.put("group_id", entityMap.get("group_id"));
		    
		    map.put("hos_id", entityMap.get("hos_id"));
		    
		    map.put("copy_code", entityMap.get("copy_code"));
		    
		    map.put("cost_item_id", entityMap.get("cost_item_id"));
		    
		    costItemDictNoMapper.updateCostItemDictNoState(map);
			    
			entityMap.put("user_code", SessionManager.getUserCode());
				
			entityMap.put("create_date", new Date());
			costItemDictNoMapper.addCostItemDictNo(entityMap);
			
			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostItemDictNo\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量添加CostItemDictNo
	 * @param  HtcCostItemDictNo entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostItemDictNo(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costItemDictNoMapper.addBatchCostItemDictNo(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostItemDictNo\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostItemDictNo(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostItemDictNo> list = costItemDictNoMapper.queryCostItemDictNo(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostItemDictNo> list = costItemDictNoMapper.queryCostItemDictNo(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNoByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostItemDictNo queryCostItemDictNoByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costItemDictNoMapper.queryCostItemDictNoByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量删除CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostItemDictNo(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costItemDictNoMapper.deleteBatchCostItemDictNo(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostItemDictNo\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 删除CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostItemDictNo(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costItemDictNoMapper.deleteCostItemDictNo(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostItemDictNo\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 更新CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costItemDictNoMapper.updateCostItemDictNo(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemDictNo\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量更新CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostItemDictNo(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costItemDictNoMapper.updateBatchCostItemDictNo(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemDictNo\"}";

		}
		
	}

	@Override
    public List<CostItemDictNo> queryItemDictNo(Map<String, Object> entityMap) throws DataAccessException {
	    // TODO Auto-generated method stub
	    return costItemDictNoMapper.queryItemDictNo(entityMap);
    }

	@Override
    public String updateCostItemNoBatch(Map<String, Object> entityMap) throws DataAccessException {
		try {

			costItemDictNoMapper.updateCostItemNoBatch(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemDictNo\"}";

		}
    }
}
