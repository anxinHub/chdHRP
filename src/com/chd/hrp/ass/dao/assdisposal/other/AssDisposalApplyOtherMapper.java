/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assdisposal.other;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
/**
 * 
 * @Description:
 * 051001资产处置申报(其他固定资产)
 * @Table:
 * ASS_DISPOSAL_A_Other
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
  

public interface AssDisposalApplyOtherMapper extends SqlMapper{     

	public Map<String,Object>  collectAssDisposalApplyOther(Map<String, Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String, Object>> listVo)throws DataAccessException;
	
	public int updateBatchUnConfirm(List<Map<String, Object>> listVo)throws DataAccessException;

	public List<AssApplyDept> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssApplyDept> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
	
	
	/**
	 * 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryState(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产处置记录变动主表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssDisApplyOtherPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	 
	/**
	 * 资产处置记录变动主表批量模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssDisApplyOtherPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产处置记录变动明细表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssDisApplyOtherPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	
}
