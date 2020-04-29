/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.dura.balance;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description: 041306 耐用品科室结余表 
 * @Table: MAT_DURA_DEPT_SURPLUS 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MatDuraDeptSurplusMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 根据材料查询表信息 
	 * @param  entityList
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryByInvList(List<Map<String,Object>> entityList) throws DataAccessException;
	
}
