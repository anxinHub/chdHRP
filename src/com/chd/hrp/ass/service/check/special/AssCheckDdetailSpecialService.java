/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.special;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface AssCheckDdetailSpecialService extends SqlService {
	/**
	 * @Description 
	 * 复制账面数量<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyAmount(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 复制账面数量<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String delCard(Map<String,Object> entityMap)throws DataAccessException;
	public List<String> queryAssInSpecialStates(Map<String, Object> mapVo);
	Map<String, Object> queryAssInSpecialByPrintTemlatePrints(Map<String, Object> entityMap) throws DataAccessException;
	public String saveAssCheckDDetailSpecial(Map<String, Object> mapVo) throws DataAccessException;
}
