/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostDeptNatureMapper;
import com.chd.hrp.cost.dao.CostItemDictMapper;
import com.chd.hrp.cost.entity.CostDeptNature;
import com.chd.hrp.cost.entity.CostItemDict;
import com.chd.hrp.cost.service.CostDeptNatureService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本习性(01 固定 02 变动)

<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costDeptNatureService")
public class CostDeptNatureServiceImpl implements CostDeptNatureService {

	private static Logger logger = Logger.getLogger(CostDeptNatureServiceImpl.class);
	
	@Resource(name = "costDeptNatureMapper")
	private final CostDeptNatureMapper costDeptNatureMapper = null;
	
	@Resource(name = "costItemDictMapper")
	private final CostItemDictMapper costItemDictMapper = null;
    
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 添加CostDeptNature
	 * @param CostDeptNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostDeptNature(Map<String,Object> entityMap)throws DataAccessException{
		
		CostDeptNature costDeptNature = queryCostDeptNatureByCode(entityMap);

		if (costDeptNature != null) {

			return "{\"error\":\"编码：" + entityMap.get("nature_code").toString() + "重复.\"}";

		}
		
		try {
			
			costDeptNatureMapper.addCostDeptNature(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDeptNature\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量添加CostDeptNature
	 * @param  CostDeptNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostDeptNature(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costDeptNatureMapper.addBatchCostDeptNature(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDeptNature\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 查询CostDeptNature分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostDeptNature(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostDeptNature> list = costDeptNatureMapper.queryCostDeptNature(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostDeptNature> list = costDeptNatureMapper.queryCostDeptNature(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 查询CostDeptNatureByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostDeptNature queryCostDeptNatureByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costDeptNatureMapper.queryCostDeptNatureByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量删除CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostDeptNature(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(int i = 0 ; i < entityList.size() ; i ++){
					Map<String,Object> map = entityList.get(i);
					CostItemDict costItemDict = costItemDictMapper.queryCostItemDictByCode(map);
					if(costItemDict != null){
						return "{\"error\":\"当前习性已被项目引用,不能删除.\"}";
					}
				}
			
				int state = costDeptNatureMapper.deleteBatchCostDeptNature(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDeptNature\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 删除CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostDeptNature(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costDeptNatureMapper.deleteCostDeptNature(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDeptNature\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 更新CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostDeptNature(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costDeptNatureMapper.updateCostDeptNature(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptNature\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量更新CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostDeptNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costDeptNatureMapper.updateBatchCostDeptNature(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptNature\"}";

		}
		
	}
}
