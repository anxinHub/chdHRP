/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.storage.special;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatSpecialAffiOutRela;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_OUT_RESOURCE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatSpecialAffiOutRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询专购品与代销对应关系
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public MatSpecialAffiOutRela queryMatSpecialAffiOutRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询专购品与代销对应关系
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<MatSpecialAffiOutRela> queryMatSpecialAffiOutRelaList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询专购品与代销对应关系
	 * @param  entityList
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<MatSpecialAffiOutRela> queryMatSpecialAffiOutRelaList(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 插入专购品与代销对应关系
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatSpecialAffiOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 删除专购品与代销对应关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatSpecialAffiOutRela(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 批量删除专购品与代销对应关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatSpecialAffiOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 批量判断单据是否可删除、修改
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatSpecialAffiOutRelaStateBatch(List<Map<String,Object>> entityList) throws DataAccessException;
}
