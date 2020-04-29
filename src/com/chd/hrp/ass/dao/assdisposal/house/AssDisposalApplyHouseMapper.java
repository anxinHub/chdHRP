/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assdisposal.house;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assdisposal.house.AssDisposalApplyHouse;
/**
 * 
 * @Description:
 * 051001资产处置申报(土地)
 * @Table:
 * ASS_DISPOSAL_A_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */ 
 

public interface AssDisposalApplyHouseMapper extends SqlMapper{

	public Map<String,Object>  collectAssDisposalApplyHouse(Map<String, Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String, Object>> listVo)throws DataAccessException;

	public List<AssDisposalApplyHouse> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap);

	public List<AssDisposalApplyHouse> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
	
}
