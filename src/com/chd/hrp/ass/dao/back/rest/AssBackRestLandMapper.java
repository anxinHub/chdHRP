/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.back.rest;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产其他退账单主表(土地)
 * @Table:
 * ASS_BACK_REST_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackRestLandMapper extends SqlMapper{
	public int updateBackMoney(Map<String,Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	public Map<String,Object> collectAssBackLand(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAssBackRestLandByAssBackNo(Map<String, Object> map);

	public List<Map<String, Object>> queryAssBackRestLandDetailByAssBackNo(Map<String, Object> map);

	public List<String> assBackRestLandMapper(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
