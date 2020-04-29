/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.special;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.special.AssCheckDdetailSpecial;
/**
 * 
 * @Description:
 * 051101 科室盘点单明细_医用设备
 * @Table:
 * ASS_CHECK_D_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckDdetailSpecialMapper extends SqlMapper{
	/**
	 * @Description 
	 * 复制账面数量<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int copyAmount(Map<String,Object> entityMap)throws DataAccessException;

	public Map<String, Object> queryAssInSpecialPrintTemlateByMains(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssInSpecialPrintTemlateByDetails(Map<String, Object> entityMap);

	public List<String> queryAssInSpecialStates(Map<String, Object> mapVo);
	
	List<AssCheckDdetailSpecial> queryAll(Map<String,Object> entityMap) throws DataAccessException;
	
	List<AssCheckDdetailSpecial> queryAll(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
