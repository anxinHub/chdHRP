/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.base;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 货位暂时不做管理内容，默认为 0
 * @Table:
 * MED_FIFO_BALANCE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedInvBalanceMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 根据药品查询表信息 
	 * @param  entityList
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryByInvList(List<Map<String,Object>> entityList) throws DataAccessException;
	
}
