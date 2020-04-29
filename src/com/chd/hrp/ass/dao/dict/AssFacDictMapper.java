/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.ass.dao.dict;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssFacDict;
import com.chd.hrp.sys.entity.FacDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AssFacDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加FacDict
	 * @param FacDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addFacDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加FacDict
	 * @param  FacDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchFacDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询FacDict分页
	 * @param  entityMap RowBounds
	 * @return List<FacDict>
	 * @throws DataAccessException
	*/
	public List<FacDict> queryFacDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询FacDict所有数据
	 * @param  entityMap
	 * @return List<FacDict>
	 * @throws DataAccessException
	*/
	public List<FacDict> queryFacDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询FacDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public FacDict queryFacDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除FacDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteFacDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除FacDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchFacDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新FacDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateFacDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新FacDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateFacDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新FacDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchFacDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 修改生产厂商 不保留历史记录是 修改生产厂商变更记录
	 * @param entityMap
	 */
	public int updateFacDict_Fac(Map<String, Object> entityMap);
	/**
	 * 生产厂商自增fac_no
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryFacNoSeq() throws DataAccessException;

	/**
	 * 编码或名称查询
	 * @param facMap
	 * @return
	 * @throws DataAccessException
	 */
	public AssFacDict queryAssFacDictByCodeOrName(Map<String, Object> facMap)throws DataAccessException;
}
