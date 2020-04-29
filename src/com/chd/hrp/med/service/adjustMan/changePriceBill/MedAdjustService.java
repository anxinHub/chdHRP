package com.chd.hrp.med.service.adjustMan.changePriceBill;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * @Description:
 * 04114 调价单
 * @Table:
 * MAT_Adjust_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAdjustService extends SqlService{
	
	/**
	 * @Description 
	 * 调价单<BR>审核 修改状态
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateMedAdjustState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>按主表ID 查询明细表数据
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAdjustDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 *材料查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInvList(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 调价单<BR>查询材料信息 用于导入
	 * @param entityMap
	 * @return map
	 * @throws DataAccessException
	*/
	public Map<String,Object> queryMedAdjustInvByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
