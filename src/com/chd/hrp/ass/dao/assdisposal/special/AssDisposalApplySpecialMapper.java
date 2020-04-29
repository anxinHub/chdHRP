/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assdisposal.special;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.assdisposal.special.AssDisposalApplySpecial;
/** 
 * 
 * @Description:
 * 051001资产处置申报(专用设备)
 * @Table:
 * ASS_DISPOSAL_A_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */ 
 

public interface AssDisposalApplySpecialMapper extends SqlMapper{     

	public Map<String,Object>  collectAssDisposalApplySpecial(Map<String, Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String, Object>> listVo)throws DataAccessException;
	
	public int updateBatchUnConfirm(List<Map<String, Object>> listVo)throws DataAccessException;

	public List<AssDisposalApplySpecial> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap);

	public List<AssDisposalApplySpecial> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap, RowBounds rowBounds);

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
	public Map<String, Object> queryAssDisApplySpecialPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	 
	/**
	 * 资产处置记录变动主表批量模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssDisApplySpecialPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产处置记录变动明细表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssDisApplySpecialPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	
}
