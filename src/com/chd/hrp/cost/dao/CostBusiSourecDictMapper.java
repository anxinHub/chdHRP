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
import com.chd.hrp.cost.entity.CostBusiSourecDict;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostBusiSourecDictMapper extends SqlMapper {
	
    public List<CostBusiSourecDict> queryCostBusiSourecDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<CostBusiSourecDict> queryCostBusiSourecDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int addCostBusiSourecDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteBatchCostBusiSourecDict(List<Map<String, Object>> list)throws DataAccessException;
	
	public CostBusiSourecDict queryCostBusiSourecDictByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateCostBusiSourecDict(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
