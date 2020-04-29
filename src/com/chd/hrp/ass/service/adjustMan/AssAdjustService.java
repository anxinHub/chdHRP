package com.chd.hrp.ass.service.adjustMan;

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
public interface AssAdjustService extends SqlService{

	public String queryDetails(Map<String, Object> page) throws DataAccessException;

	public String queryAssList(Map<String, Object> page) throws DataAccessException;

	public Long queryCurrentSequence() throws DataAccessException ;  

	public String queryAssAdjustDetailByCode(Map<String, Object> page) throws DataAccessException;

	public String updateAssAdjustState(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
}
