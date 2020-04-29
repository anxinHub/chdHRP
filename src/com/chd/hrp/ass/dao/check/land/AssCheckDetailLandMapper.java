/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.land;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 051101 盘点单明细_土地
 * @Table:
 * ASS_CHECK_DETAIL_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckDetailLandMapper extends SqlMapper{
	/**
	 * @Description 
	 * 复制账面数量<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int copyAmount(Map<String,Object> entityMap)throws DataAccessException;

	public List<String> queryAssInSpecialStates(Map<String, Object> mapVo);

	public Map<String, Object> queryAssInSpecialPrintTemlateByMains(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssInSpecialPrintTemlateByDetails(Map<String, Object> entityMap);
}
