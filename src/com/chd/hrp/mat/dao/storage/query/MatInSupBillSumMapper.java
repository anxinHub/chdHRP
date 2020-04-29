/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.storage.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 入库明细查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatInSupBillSumMapper extends SqlMapper{


	
	
	/**
	 * 供应商入库汇总查询
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatInSupBillSum(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatInSupBillSum(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
