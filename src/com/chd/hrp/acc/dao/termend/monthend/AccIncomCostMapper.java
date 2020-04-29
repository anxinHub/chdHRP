/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend.monthend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
 
/**
* @Title. @Description.
* 收支结转<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccIncomCostMapper extends SqlMapper{

	/**
	 * @Description 
	 * 收支结转<BR> 添加AccVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccIncomCostVouch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 收支结转<BR> 查询模板中财务收支科目账表本期发生列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryIncomCostSubjMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	// 科目全称(企业)
	public List<Map<String, Object>> querySubjAllFirm(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
