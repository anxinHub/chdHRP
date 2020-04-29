/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assdisposal.land;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 051001资产处置记录(土地)
 * @Table:
 * ASS_DISPOSAL_R_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDisposalRecordLandMapper extends SqlMapper{
	public Map<String,Object>  collectAssDisposalRecordLand(Map<String, Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String, Object>> listVo)throws DataAccessException;

	public List<Map<String, Object>> queryAssAllotOutLandByAssInNo(Map<String, Object> map);

	public List<Map<String, Object>> queryAssAllotOutLandDetailByAssInNo(Map<String, Object> map);

	public void addAssPlanDeptImportBid(Map<String, Object> planApplyMapvo);

	public void deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

	public List<String> queryAssDisposalRecordLandStates(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
