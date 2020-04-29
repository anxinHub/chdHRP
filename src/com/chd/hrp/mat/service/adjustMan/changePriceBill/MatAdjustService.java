package com.chd.hrp.mat.service.adjustMan.changePriceBill;

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
public interface MatAdjustService extends SqlService{
	
	/**
	 * @Description 
	 * 调价单<BR>审核 修改状态
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateMatAdjustState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>按主表ID 查询明细表数据
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAdjustDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 *材料查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvList(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 调价单<BR>查询材料信息 用于导入
	 * @param entityMap
	 * @return map
	 * @throws DataAccessException
	*/
	public Map<String,Object> queryMatAdjustInvByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;

	public String queryChoiceInvBySupData(Map<String, Object> mapVo);

	public String queryMatChoiceInvBySup(Map<String, Object> page);

	public String updateCheckMatAdjustState(Map<String, Object> mapVo);
}
