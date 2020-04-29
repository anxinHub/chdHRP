/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.dura.init;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:  耐用品期初记账
 * @Table: MAT_DURA_(STORE/DEPT)_REG
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatDuraInitChargeService{

	/**
	 * @Description 
	 * 获取左侧树形结构<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryTree(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * charge<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String save(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityList
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException;
}
