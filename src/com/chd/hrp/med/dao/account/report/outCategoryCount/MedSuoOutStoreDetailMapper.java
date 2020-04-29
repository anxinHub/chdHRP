package com.chd.hrp.med.dao.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 出库业务汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedSuoOutStoreDetailMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 科室统计出库金额 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedSuoOutStoreDetail(Map<String,Object> entityMap) throws DataAccessException;
	
}
