/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.cost.entity.CostDriDetail;

/**
* @Title. @Description.
* 科室成本明细数据表_平级分摊<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDriDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 添加CostDriDetail
	 * @param CostDriDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDriDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 一级分摊
	 * @param CostDriDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deptCostShareDirOneData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 二级分摊
	 * @param CostDriDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deptCostShareDirTwoData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 三级分摊
	 * @param CostDriDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deptCostShareDirThreeData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量添加CostDriDetail
	 * @param  CostDriDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDriDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 查询CostDriDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostDriDetail>
	 * @throws DataAccessException
	*/
	public List<CostDriDetail> queryCostDriDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 查询CostDriDetail所有数据
	 * @param  entityMap
	 * @return List<CostDriDetail>
	 * @throws DataAccessException
	*/
	public List<CostDriDetail> queryCostDriDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 查询CostDriDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDriDetail queryCostDriDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 删除CostDriDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDriDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量删除CostDriDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDriDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 更新CostDriDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostDriDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量更新CostDriDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostDriDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
}
